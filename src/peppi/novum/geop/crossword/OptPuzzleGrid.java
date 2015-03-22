package peppi.novum.geop.crossword;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import peppi.novum.geop.util.GeopWord;

public class OptPuzzleGrid {

	int width = 20;
	List<Word> words = new ArrayList<Word>();
	int maxWords = 0;
	HashMap<Integer, ArrayList<Character>> rows = new HashMap<Integer, ArrayList<Character>>();
	HashMap<String, String> definitions = new HashMap<String, String>();
	int crossings = 0;

	@SuppressWarnings("unchecked")
	public OptPuzzleGrid() {
		List<Character> row = new ArrayList<Character>();

		for (int i = 0; i < width; i++) {
			row.add(' ');
		}

		for (int i = 0; i < 10; i++) {
			rows.put(i, (ArrayList<Character>) ((ArrayList<Character>) row).clone());
		}
	}

	public void generate(List<GeopWord> inputwords) {
		HashMap<String, String> definitions = new HashMap<String, String>();

		for (GeopWord gw : inputwords) {
			definitions.put(gw.getPuzzleWord(), gw.getDefinition());
		}

		List<String> words = new ArrayList<String>();
		this.definitions = definitions;

		for (String word : definitions.keySet()) {
			word = word.toLowerCase();
			word = word.replace(" ", "");
			words.add(word);
		}

		maxWords = words.size();

		if (maxWords > 0) {
			Collections.shuffle(words);

			placeFirst(words.get(0));
			words.remove(0);

			addWords(words);
		}
	}

	public void placeFirst(String word) {
		placeWord(word, 10, 5, 10 + word.length(), 5);
		words.add(new Word(word, definitions.get(word), 1, new Point(10, 5), new Point(10 + word.length(), 5), 0));
	}

	public void placeWord(String word, int startX, int startY, int endX, int endY) {
		if (startY != endY && startX != endX) {
			return;
		}

		if (startX > width || endX > width) {
			expandWidth(endX);
		}

		if (startY > rows.size() || endY > rows.size()) {
			expandHeight(endY - 1);
		}

		if (startY == endY) {
			int j = 0;
			for (int i = startX; i < endX; i++) {
				rows.get(startY).set(i, word.charAt(j));
				j++;
			}
		} else {
			int j = 0;
			for (int i = startY; i < endY; i++) {
				rows.get(i).set(startX, word.charAt(j));
				j++;
			}
		}
	}

	public void addWords(List<String> words) {
		long start = System.currentTimeMillis();
		while (words.size() != 0) {
			long current = System.currentTimeMillis();

			for (int i = 0; i < words.size(); i++) {
				String word = words.get(i);
				if (addWord(word)) {
					words.remove(word);
				}
			}

			if (current - start > 10) {
				break;
			}
		}
	}

	public boolean addWord(String word) {
		word = word.toLowerCase();
		List<WordPoint> points = getPossiblePoints(word);

		HashMap<WordPoint, Integer> possibilities = new HashMap<WordPoint, Integer>();

		for (WordPoint p : points) {
			List<Integer> directions = getValidDirections(p.getPoint());

			if (directions.isEmpty()) {
				continue;
			}

			int dir = chooseDirection(directions, p.getPoint());

			if (!wordPointFits(p, dir)) {
				continue;
			}

			if (conflictsWithOthers(p, dir)) {
				continue;
			}

			if (hasSurroundingWords(p, dir)) {
				continue;
			}

			possibilities.put(p, dir);
		}

		if (!possibilities.isEmpty()) {
			WordPoint chosen = null;

			while (chosen == null) {
				int highest = 0;
				for (WordPoint p : possibilities.keySet()) {
					if (p.getIntersections().size() > highest) {
						highest = p.intersections.size();
						chosen = p;
					} else if (p.getIntersections().size() == highest) {
						int i = new Random().nextInt(2);

						if (i == 0) {
							chosen = p;
						}
					}
				}
			}

			int dir = possibilities.get(chosen);
			Word w;

			if (dir == 0) {
				placeWord(word, chosen.getPoint().getX() - chosen.getIndex(), chosen.getPoint().getY(), chosen.getPoint().getX() - chosen.getIndex() + chosen.getLength(), chosen.getPoint().getY());
				w = new Word(word, definitions.get(word), words.size() + 1, new Point(chosen.getPoint().getX() - chosen.getIndex(), chosen.getPoint().getY()), new Point(chosen.getPoint().getX() - chosen.getIndex() + chosen.getLength(), chosen.getPoint().getY()), dir);
			} else {
				placeWord(word, chosen.getPoint().getX(), chosen.getPoint().getY() - chosen.getIndex(), chosen.getPoint().getX(), chosen.getPoint().getY() - chosen.getIndex() + chosen.getLength());
				w = new Word(word, definitions.get(word), words.size() + 1, new Point(chosen.getPoint().getX(), chosen.getPoint().getY() - chosen.getIndex()), new Point(chosen.getPoint().getX(), chosen.getPoint().getY() - chosen.getIndex() + chosen.getLength()), dir);
			}

			w.setWordPoint(chosen);

			crossings += chosen.getIntersections().size();

			words.add(w);
			return true;
		} else {
			return false;
		}
	}

	public boolean hasSurroundingWords(WordPoint point, int dir) {
		if (dir == 0) {
			for (WordPoint wp : getAllWordPoints(point, dir)) {
				int firstX = wp.getPoint().getX() - wp.getIndex();
				int lastX = firstX + wp.getLength();
				int y = wp.getPoint().getY();

				if (firstX > 0 && rows.get(y).get(firstX - 1) != ' ') {
					return true;
				}

				if (lastX < rows.get(y).size() && rows.get(y).get(lastX) != ' ') {
					return true;
				}

				for (int x = firstX; x < lastX; x++) {
					boolean skip = false;
					for (Point p : wp.getIntersections()) {
						if (p.getX() == x && p.getY() == y) {
							skip = true;
						}
					}

					if (skip) {
						continue;
					}

					if (y > 0) {
						if (rows.get(y - 1).get(x) != ' ') {
							return true;
						}
					}
					if (y < rows.size()) {
						if (rows.get(y + 1).get(x) != ' ') {
							return true;
						}
					}
				}
			}
		} else {
			for (WordPoint wp : getAllWordPoints(point, dir)) {
				int firstY = wp.getPoint().getY() - wp.getIndex();
				int lastY = firstY + wp.getLength();
				int x = wp.getPoint().getX();

				if (firstY > 0 && rows.get(firstY - 1).get(x) != ' ') {
					return true;
				}

				if (lastY < rows.size() && rows.get(lastY).get(x) != ' ') {
					return true;
				}

				for (int y = firstY; y < lastY; y++) {
					boolean skip = false;
					for (Point p : wp.getIntersections()) {
						if (p.getY() == y && p.getX() == x) {
							skip = true;
						}
					}

					if (skip) {
						continue;
					}

					if (x > 0) {
						if (rows.get(y).get(x - 1) != ' ') {
							return true;
						}
					}
					if (x < rows.get(y).size()) {
						if (rows.get(y).get(x + 1) != ' ') {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean wordPointFits(WordPoint p, int dir) {
		if (dir == 0) {
			if (p.getPoint().getX() - p.getIndex() < 0) {
				return false;
			} else {
				return true;
			}
		} else {
			if (p.getPoint().getY() - p.getIndex() < 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	public boolean conflictsWithOthers(WordPoint p, int dir) {
		for (WordPoint wp : getAllWordPoints(p, dir)) {
			while (rows.get(wp.getPoint().getY()) == null) {
				expandHeight(rows.size() + 1);
			}
			while (p.getPoint().getX() + p.getLength() - p.getIndex() > rows.get(wp.getPoint().getY()).size()) {
				expandWidth(width + 1);
			}

			char c = rows.get(wp.getPoint().getY()).get(wp.getPoint().getX());

			if (c != ' ') {
				if (c != wp.getWord().charAt(wp.index)) {
					return true;
				} else {
					for (Word w : getWordsAt(wp.getPoint().getX(), wp.getPoint().getY())) {
						if (w.getWord().contains(wp.getWord()) || wp.getWord().contains(w.getWord())) {
							return true;
						}
					}

					p.getIntersections().add(wp.getPoint());
				}
			}

		}

		return false;
	}

	private List<WordPoint> getAllWordPoints(WordPoint point, int dir) {
		int start;

		if (dir == 0) {
			start = point.getPoint().getX() - point.getIndex();
		} else {
			start = point.getPoint().getY() - point.getIndex();
		}

		List<WordPoint> result = new ArrayList<WordPoint>();

		for (int i = 0; i < point.getWord().length(); i++) {
			Point p;
			if (dir == 0) {
				p = new Point(start + i, point.getPoint().getY());
			} else {
				p = new Point(point.getPoint().getX(), start + i);
			}

			WordPoint wp = new WordPoint(point.getWord(), p, i, point.getLength());
			wp.setIntersections(point.getIntersections());

			result.add(wp);
		}

		return result;
	}

	public List<Integer> getValidDirections(Point p) {
		List<Integer> result = new ArrayList<Integer>();

		// East
		if (!(p.getX() + 1 < rows.get(p.getY()).size())) {
			expandWidth(p.getX() + 2);
		}
		if (rows.get(p.getY()).get(p.getX() + 1) == ' ') {
			result.add(0);
		}

		// South
		if (rows.get(p.getY() + 1) == null) {
			expandHeight(p.getY() + 2);
		}
		if (rows.get(p.getY() + 1).get(p.getX()) == ' ') {
			result.add(1);
		}

		return result;
	}

	public int chooseDirection(List<Integer> directions, Point p) {
		while (directions.size() > 1) {
			int dir1 = directions.get(0);
			int dir2 = directions.get(1);

			if (dir1 == 0) {
				if (p.getX() > p.getY()) {
					directions.remove(dir1);
				} else if (p.getX() == p.getY()) {
					directions.remove(new Random().nextInt(1));
				} else {
					directions.remove(dir2);
				}
			} else {
				if (p.getX() > p.getY()) {
					directions.remove(dir2);
				} else if (p.getX() == p.getY()) {
					directions.remove(new Random().nextInt(1));
				} else {
					directions.remove(dir1);
				}
			}
		}

		return directions.get(0);
	}

	@SuppressWarnings("unchecked")
	public void expandWidth(int newSize) {
		for (Integer i : ((HashMap<Integer, List<Character>>) rows.clone()).keySet()) {
			ArrayList<Character> row = rows.get(i);

			while (row.size() < newSize) {
				row.add(' ');
			}

			rows.remove(i);
			rows.put(i, row);
		}

		width = newSize;
	}

	public void expandHeight(int newSize) {
		ArrayList<Character> row = new ArrayList<Character>();

		while (row.size() < width) {
			row.add(' ');
		}

		while (rows.size() < newSize) {
			rows.put(rows.size(), row);
		}
	}

	public List<WordPoint> getPossiblePoints(String word) {
		List<WordPoint> result = new ArrayList<WordPoint>();

		for (Integer i : rows.keySet()) {
			List<Character> row = rows.get(i);
			char[] letters = word.toCharArray();

			int y = i;

			for (int j = 0; j < letters.length; j++) {
				char c = letters[j];

				for (int k = 0; k < row.size(); k++) {
					char c2 = row.get(k);
					if (c2 == c) {
						Point p = new Point(k, y);
						WordPoint wp = new WordPoint(word, p, j, letters.length);
						result.add(wp);
					}
				}
			}
		}

		return result;
	}

	public HashMap<Integer, ArrayList<Character>> getRows() {
		return rows;
	}

	public List<Word> getWords() {
		return words;
	}

	public int getMaxWords() {
		return maxWords;
	}

	public int getCrossings() {
		return crossings;
	}

	public List<Word> getWordsAt(int x, int y) {
		List<Word> result = new ArrayList<Word>();

		for (Word w : words) {
			if (w.getDirection() == 0) {
				if (w.getStart().getY() == y && w.getStart().getX() <= x && w.getEnd().getX() >= x) {
					result.add(w);
				}
			} else {
				if (w.getStart().getX() == x && w.getStart().getY() <= y && w.getEnd().getY() >= y) {
					result.add(w);
				}
			}
		}

		return result;
	}
}

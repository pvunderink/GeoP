package peppi.novum.geop.crossword;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import peppi.novum.geop.Main;
import peppi.novum.geop.gui.MessageWindow;
import peppi.novum.geop.gui.puzzle.PuzzlePane;
import peppi.novum.geop.util.GeopWord;
import peppi.novum.geop.util.SaveReader;

public class CrosswordPuzzle {

	public static boolean ACTIVE = false;
	public static PuzzleGrid GRID;
	public static PuzzlePane PANE;
	public static File LAST_FILE;
	public static HashMap<Word, List<Point>> HINTS;
	public static int HINTS_USED;

	public static void loadPuzzle(final File file) {
		if (file == null || !file.exists()) {
			return;
		}
		new Thread("GeneratePuzzle") {
			public void run() {
				try {
					List<GeopWord> list = SaveReader.readWordList(file).getWords();

					List<OptPuzzleGrid> possibilities = new ArrayList<OptPuzzleGrid>();

					OptPuzzleGrid best = null;

					for (int i = 0; i < 250; i++) {
						OptPuzzleGrid og = new OptPuzzleGrid();
						og.generate(list);
						possibilities.add(og);
					}

					for (OptPuzzleGrid og : possibilities) {
						if (best == null) {
							best = og;
						} else if (og.getWords().size() > best.getWords().size() && og.getCrossings() > best.getCrossings()) {
							best = og;
						} else if (og.getWords().size() == best.getWords().size() && og.getCrossings() >= best.getCrossings()) {
							int r = new Random().nextInt(1);

							if (r == 0) {
								best = og;
							}
						}
					}

					System.out.println("Words placed: " + (best.getWords().size()) + ", Intersections: " + best.getCrossings());

					GRID = new PuzzleGrid(best, best.getWords());

					PANE = new PuzzlePane(GRID);
					Main.MAIN_WINDOW.loadArea(PANE, PANE);

					LAST_FILE = file;

					HINTS = new HashMap<Word, List<Point>>();
					HINTS_USED = 0;

					ACTIVE = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void reset() {
		GRID = null;
		PANE = null;
		HINTS = null;
		HINTS_USED = 0;
		ACTIVE = false;

		Main.MAIN_WINDOW.clearArea();
	}

	public static int checkAnswers() {
		if (!ACTIVE) {
			return 0;
		}

		List<Word> wrong = new ArrayList<Word>();

		JLayeredPane board = PANE.getBoard();

		for (int i = 0; i < board.getComponents().length; i++) {
			if (board.getComponent(i) instanceof JTextField) {
				JTextField field = (JTextField) board.getComponent(i);

				int x = ((GridBagLayout) board.getLayout()).getConstraints(field).gridx;
				int y = ((GridBagLayout) board.getLayout()).getConstraints(field).gridy;

				if (!field.getText().isEmpty()) {
					char c = field.getText().charAt(0);
					if (GRID.getRows().get(y).get(x) == c) {
						field.setBackground(Color.GREEN);
					} else {
						field.setText("" + GRID.getRows().get(y).get(x));
						field.setForeground(Color.ORANGE);
						field.setBackground(Color.RED);

						for (Word w : GRID.getOptionalGrid().getWordsAt(x, y)) {
							if (!wrong.contains(w)) {
								wrong.add(w);
							}
						}
					}
				} else {
					field.setText("" + GRID.getRows().get(y).get(x));
					field.setForeground(Color.ORANGE);
					field.setBackground(Color.RED);

					for (Word w : GRID.getOptionalGrid().getWordsAt(x, y)) {
						if (!wrong.contains(w)) {
							wrong.add(w);
						}
					}
				}
			}
		}

		return wrong.size();
	}

	public static void loadHint(Word word) {
		if (!ACTIVE) {
			return;
		}

		JLayeredPane board = PANE.getBoard();

		if (!HINTS.containsKey(word)) {
			HINTS.put(word, new ArrayList<Point>());
		} else {
			if (HINTS.get(word).size() >= word.getLength()) {
				new MessageWindow("No more hints available", Color.BLACK);
				return;
			}
		}

		HINTS_USED += 1;

		int total = word.getLength() / 3;

		if (total > word.getLength() - HINTS.get(word).size()) {
			total = word.getLength() - HINTS.get(word).size();
		}

		if (total == 0) {
			total = 1;
		}

		int count = 0;

		while (count < total) {
			for (int i = 0; i < board.getComponents().length; i++) {
				if (board.getComponent(i) instanceof JTextField) {
					JTextField field = (JTextField) board.getComponent(i);

					int x = ((GridBagLayout) board.getLayout()).getConstraints(field).gridx;
					int y = ((GridBagLayout) board.getLayout()).getConstraints(field).gridy;

					Point p = new Point(x, y);

					for (Word w : GRID.getOptionalGrid().getWordsAt(x, y)) {
						if (word.getWord().equals(w.getWord())) {
							boolean b = true;
							for (Point p1 : HINTS.get(word)) {
								if (p1.toString().equals(p.toString())) {
									b = false;
									break;
								}
							}

							if (b) {
								int r = new Random().nextInt(8);

								if (r == 0) {
									field.setText("" + GRID.getRows().get(y).get(x));
									field.setBackground(Color.GREEN);
									HINTS.get(word).add(p);

									count++;
								}
							}
						}
					}
				}
			}
		}
	}
}

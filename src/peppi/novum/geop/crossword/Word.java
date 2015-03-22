package peppi.novum.geop.crossword;

public class Word {

	WordPoint wp;
	String word;
	String definition;
	int number;

	Point start;
	Point end;

	int dir;

	int length;

	public Word(String word, String definition, int number, Point start, Point end, int dir) {
		this.word = word;
		this.definition = definition;
		this.number = number;

		this.start = start;
		this.end = end;
		this.length = word.length();
		this.dir = dir;
	}

	public int getLength() {
		return length;
	}

	public String getWord() {
		return word;
	}

	public String getDefinition() {
		return definition;
	}

	public char[] getCharArray() {
		return word.toCharArray();
	}

	public int getDirection() {
		return dir;
	}

	public int getNumber() {
		return number;
	}

	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}

	public void setWordPoint(WordPoint wp) {
		this.wp = wp;
	}

	public WordPoint getWordPoint() {
		return wp;
	}

}

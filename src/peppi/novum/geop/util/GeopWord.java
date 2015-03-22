package peppi.novum.geop.util;

public class GeopWord {
	
	String puzzle;
	String practice;
	String definition;
	
	public GeopWord(String word1, String word2, String definition) {
		this.puzzle = word1;
		this.practice = word2;
		this.definition = definition;
	}

	public String getPuzzleWord() {
		return puzzle;
	}

	public String getPracticeWord() {
		return practice;
	}

	public String getDefinition() {
		return definition;
	}

}

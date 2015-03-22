package peppi.novum.geop.crossword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PuzzleGrid {

	int width = 0;
	HashMap<Integer, ArrayList<Character>> rows = new HashMap<Integer, ArrayList<Character>>();
	List<Word> words = new ArrayList<Word>();
	
	OptPuzzleGrid opg;

	public PuzzleGrid(OptPuzzleGrid grid, List<Word> words) {
		this.rows = grid.getRows();
		width = grid.width;
		this.words = words;
		this.opg = grid;

	}

	public HashMap<Integer, ArrayList<Character>> getRows() {
		return rows;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return rows.size();
	}

	public List<Word> getWords() {
		return words;
	}
	
	public OptPuzzleGrid getOptionalGrid() {
		return opg;
	}

}

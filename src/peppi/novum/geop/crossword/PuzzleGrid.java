/*
 * Copyright (C) 2015 Pepijn Vunderink <pj.vunderink@gmail.com>
 *
 * GeoP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
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

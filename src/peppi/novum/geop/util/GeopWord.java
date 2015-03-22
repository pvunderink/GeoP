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

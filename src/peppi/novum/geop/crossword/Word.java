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

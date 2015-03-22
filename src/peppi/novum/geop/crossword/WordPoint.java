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
import java.util.List;

public class WordPoint {

	String word;
	Point point;
	int index;
	int length;

	List<Point> intersections = new ArrayList<Point>();

	public WordPoint(String word, Point point, int index, int length) {
		this.word = word;
		this.point = point;
		this.index = index;
		this.length = length;
	}

	public String getWord() {
		return word;
	}

	public Point getPoint() {
		return point;
	}

	public int getIndex() {
		return index;
	}

	public int getLength() {
		return length;
	}

	@Override
	public String toString() {
		return point.toString() + "(" + index + ", " + length + ")";
	}

	public List<Point> getIntersections() {
		return intersections;
	}
	
	public void setIntersections(List<Point> i) {
		intersections = i;
	}
	
	

}

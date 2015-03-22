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

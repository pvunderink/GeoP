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

import java.util.List;

public class WordList {

	List<GeopWord> words;

	boolean default_dir;
	
	String label0;
	String label1;

	boolean dir0_1;
	boolean dir1_0;
	boolean case0_1;
	boolean case1_0;
	int mode0_1;
	int mode1_0;

	public WordList(List<GeopWord> words, boolean default_dir, String label0, String label1, boolean dir0_1, boolean dir1_0, boolean case0_1, boolean case1_0, int mode0_1, int mode1_0) {
		this.words = words;
		this.default_dir = default_dir;
		this.label0 = label0;
		this.label1 = label1;
		this.dir0_1 = dir0_1;
		this.dir1_0 = dir1_0;
		this.case0_1 = case0_1;
		this.case1_0 = case1_0;
		this.mode0_1 = mode0_1;
		this.mode1_0 = mode1_0;
	}

	public List<GeopWord> getWords() {
		return words;
	}
	
	public boolean defaultDir() {
		return default_dir;
	}

	public String getLabel0() {
		return label0;
	}

	public String getLabel1() {
		return label1;
	}

	public boolean getDir0() {
		return dir0_1;
	}

	public boolean getDir1() {
		return dir1_0;
	}
	
	public boolean getCase0() {
		return case0_1;
	}

	public boolean getCase1() {
		return case1_0;
	}
	
	public int getMode0() {
		return mode0_1;
	}

	public int getMode1() {
		return mode1_0;
	}

}

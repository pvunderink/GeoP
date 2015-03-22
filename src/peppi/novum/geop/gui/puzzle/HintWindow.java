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
package peppi.novum.geop.gui.puzzle;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import peppi.novum.geop.crossword.CrosswordPuzzle;

public class HintWindow extends JFrame {

	private static final long serialVersionUID = -3459111580547975970L;
	int width = 240, height = 350;

	JPanel pane = new JPanel();

	public HintWindow() {
		setTitle("Puzzle - Hint");
		setResizable(false);
		setBounds(new Rectangle(width, height));
		setLocationRelativeTo(null);

		pane.setLayout(null);
		pane.setBackground(new Color(75, 75, 75));
		setContentPane(pane);

		JLabel chooseLabel = new JLabel("Choose a word:");
		chooseLabel.setHorizontalAlignment(JLabel.CENTER);
		chooseLabel.setForeground(new Color(230, 230, 230));
		chooseLabel.setBounds(width / 2 - 100 / 2, 70, 100, 20);
		pane.add(chooseLabel);

		final JComboBox<String> wordsBox = new JComboBox<String>();
		wordsBox.setBounds(width / 2 - 100 / 2, 100, 100, 30);
		pane.add(wordsBox);

		for (int i = 0; i < CrosswordPuzzle.GRID.getWords().size(); i++) {
			wordsBox.addItem("" + (i + 1));
		}

		JButton hintButton = new JButton("Hint");
		hintButton.setBounds(width / 2 - 100 / 2, 190, 100, 30);
		pane.add(hintButton);
		hintButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Integer i = Integer.parseInt(wordsBox.getSelectedItem().toString()) - 1;
				if (CrosswordPuzzle.ACTIVE)
					CrosswordPuzzle.loadHint(CrosswordPuzzle.GRID.getWords().get(i));

				dispose();
			}
		});

		JButton closeButton = new JButton("Close");
		closeButton.setBounds(width / 2 - 100 / 2, 225, 100, 30);
		pane.add(closeButton);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});

		setVisible(true);
	}

}

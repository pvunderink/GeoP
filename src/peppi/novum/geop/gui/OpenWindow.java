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
package peppi.novum.geop.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import peppi.novum.geop.Main;
import peppi.novum.geop.crossword.CrosswordPuzzle;
import peppi.novum.geop.gui.practicemode.SettingsPane;
import peppi.novum.geop.util.SaveReader;

public class OpenWindow extends JFrame {

	private static final long serialVersionUID = -3459111580547975970L;
	int width = 200, height = 200;

	JPanel pane = new JPanel();

	public OpenWindow(final File file) {
		setTitle("Open");
		setResizable(false);
		setBounds(new Rectangle(width, height));
		setLocationRelativeTo(null);

		pane.setLayout(null);
		pane.setBackground(new Color(75, 75, 75));
		setContentPane(pane);

		JButton practiceButton = new JButton("Practice Mode");
		practiceButton.setHorizontalAlignment(JLabel.CENTER);
		practiceButton.setBounds(width / 2 - 175 / 2, height / 2 - 80, 175, 30);
		pane.add(practiceButton);
		practiceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsPane settings;
				try {
					settings = new SettingsPane(SaveReader.readWordList(file));
					Main.MAIN_WINDOW.loadArea(settings, settings);
					dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton puzzleButton = new JButton("Crossword Puzzle");
		puzzleButton.setHorizontalAlignment(JLabel.CENTER);
		puzzleButton.setBounds(width / 2 - 175 / 2, height / 2 - 25, 175, 30);
		pane.add(puzzleButton);
		puzzleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CrosswordPuzzle.loadPuzzle(file);
				dispose();
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(width / 2 - 100 / 2, height / 2 + 30, 100, 30);
		pane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});

		setVisible(true);
	}

}

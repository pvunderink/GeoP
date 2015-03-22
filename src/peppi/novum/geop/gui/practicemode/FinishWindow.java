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
package peppi.novum.geop.gui.practicemode;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import peppi.novum.geop.Main;
import peppi.novum.geop.practicemode.PracticeMode;
import peppi.novum.geop.practicemode.PracticeWord;

public class FinishWindow extends JFrame {

	private static final long serialVersionUID = 4795487656196020135L;

	int width = 240, height = 200;

	JPanel pane = new JPanel();

	public FinishWindow(int total, HashMap<Integer, List<PracticeWord>> wrongList) {
		setTitle("Practice - Results");
		setResizable(false);
		setBounds(new Rectangle(width, height));
		setLocationRelativeTo(null);

		pane.setLayout(null);
		pane.setBackground(new Color(75, 75, 75));
		setContentPane(pane);

		NumberFormat formatter = new DecimalFormat("#0.0");

		double grade = 10.0;

		for (int i : wrongList.keySet()) {
			if (i == 0) {
				grade -= ((double) ((double) wrongList.get(i).size() / (double) total)) * 10;
			} else {
				grade -= (((double) ((double) wrongList.get(i).size() / (double) total)) * 10) / 3;
			}
		}

		if (grade < 0) {
			grade = 0.0;
		} else if (grade > 10) {
			grade = 10;
		}

		JLabel gradeLabel = new JLabel("Grade: " + formatter.format(grade));
		gradeLabel.setHorizontalAlignment(JLabel.CENTER);
		gradeLabel.setForeground(new Color(230, 230, 230));
		gradeLabel.setBounds(width / 2 - 200 / 2, 35, 200, 20);
		pane.add(gradeLabel);

		JButton retryButton = new JButton("Retry");
		retryButton.setBounds(width / 2 - 100 / 2, 100, 100, 30);
		pane.add(retryButton);
		retryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
				PracticeMode.reset();
				SettingsPane settings = new SettingsPane(PracticeMode.WORD_LIST);
				Main.MAIN_WINDOW.loadArea(settings, settings);
			}
		});

		JButton doneButton = new JButton("Close");
		doneButton.setBounds(width / 2 - 100 / 2, 125, 100, 30);
		pane.add(doneButton);
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
				PracticeMode.reset();
			}
		});
		setVisible(true);

		addWindowListener(new WindowListener() {

			public void windowOpened(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
				PracticeMode.reset();
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowActivated(WindowEvent e) {
			}
		});
	}

}

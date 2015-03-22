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

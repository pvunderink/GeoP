package peppi.novum.geop.gui.puzzle;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import peppi.novum.geop.crossword.CrosswordPuzzle;

public class FinishWindow extends JFrame {

	private static final long serialVersionUID = 4795403056196020135L;

	int width = 240, height = 350;

	JPanel pane = new JPanel();

	public FinishWindow(int correct, int wrong) {
		setTitle("Puzzle - Results");
		setResizable(false);
		setBounds(new Rectangle(width, height));
		setLocationRelativeTo(null);

		pane.setLayout(null);
		pane.setBackground(new Color(75, 75, 75));
		setContentPane(pane);

		JLabel correctLabel = new JLabel("Correct: " + correct);
		correctLabel.setHorizontalAlignment(JLabel.CENTER);
		correctLabel.setForeground(Color.GREEN);
		correctLabel.setBounds(width / 2 - 100 / 2, 45, 100, 20);
		pane.add(correctLabel);

		JLabel wrongLabel = new JLabel("Wrong: " + wrong);
		wrongLabel.setHorizontalAlignment(JLabel.CENTER);
		wrongLabel.setForeground(Color.RED);
		wrongLabel.setBounds(width / 2 - 100 / 2, 75, 100, 20);
		pane.add(wrongLabel);

		int hints = CrosswordPuzzle.HINTS_USED;

		JLabel hintsUsed = new JLabel("Hints Used: " + hints);
		hintsUsed.setHorizontalAlignment(JLabel.CENTER);
		hintsUsed.setForeground(new Color(230, 230, 230));
		hintsUsed.setBounds(width / 2 - 100 / 2, 105, 100, 20);
		pane.add(hintsUsed);

		NumberFormat formatter = new DecimalFormat("#0.00");
		double correctD = (double) correct;
		double wrongD = (double) wrong;
		double percentage = (correctD / (correctD + wrongD)) * 100;

		JLabel percentageLabel = new JLabel("Percentage Correct: " + formatter.format(percentage) + "%");
		percentageLabel.setHorizontalAlignment(JLabel.CENTER);
		percentageLabel.setForeground(new Color(230, 230, 230));
		percentageLabel.setBounds(width / 2 - 200 / 2, 135, 200, 20);
		pane.add(percentageLabel);

		NumberFormat formatter2 = new DecimalFormat("#0.0");
		double percentageHint = ((double) hints / (correctD + wrongD)) * 100;
		double grade = (percentage - (percentageHint / 3)) / 10;
		
		if (grade < 0) {
			grade = 0.0;
		} else if (grade > 10) {
			grade = 10;
		}

		JLabel gradeLabel = new JLabel("Grade: " + formatter2.format(grade));
		gradeLabel.setHorizontalAlignment(JLabel.CENTER);
		gradeLabel.setForeground(new Color(230, 230, 230));
		gradeLabel.setBounds(width / 2 - 200 / 2, 165, 200, 20);
		pane.add(gradeLabel);

		JButton retryButton = new JButton("Retry");
		retryButton.setBounds(width / 2 - 100 / 2, 215, 100, 30);
		pane.add(retryButton);
		retryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
				CrosswordPuzzle.loadPuzzle(CrosswordPuzzle.LAST_FILE);
			}
		});

		JButton doneButton = new JButton("Close");
		doneButton.setBounds(width / 2 - 100 / 2, 250, 100, 30);
		pane.add(doneButton);
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
				CrosswordPuzzle.reset();
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
				CrosswordPuzzle.reset();
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowActivated(WindowEvent e) {
			}
		});
	}

}

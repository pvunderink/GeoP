package peppi.novum.geop.gui.practicemode;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import peppi.novum.geop.gui.GeoPane;
import peppi.novum.geop.practicemode.PracticeMode;

public class PracticePane extends JPanel implements GeoPane {

	private static final long serialVersionUID = 8148263539840241094L;
	private GridBagLayout layout = new GridBagLayout();

	private JPanel board = new JPanel();
	private JScrollPane boardScroll = new JScrollPane(board);

	private JTable stats = new JTable();
	private JScrollPane statsScroll = new JScrollPane(stats);

	private JLabel text = new JLabel();
	private JTextField input = new JTextField(20);
	private JButton commit = new JButton("Ok");
	private JLabel correct = new JLabel();
	private JLabel wordsLeft = new JLabel();

	private JButton showAnswer = new JButton("Antwoord");
	private JLabel answerLabel = new JLabel();
	private JButton wrong = new JButton("Fout");
	private JButton right = new JButton("Goed");

	boolean calculating = false;

	public void load(int standardWidth, int standardHeight) {
		layout.columnWidths = new int[] { getWidth() / 10 * 8, standardWidth / 10 * 2 };
		layout.rowHeights = new int[] { getHeight() / 20 * 16, standardHeight / 20, standardHeight / 20, standardHeight / 20, standardHeight / 20 };
		layout.columnWeights = new double[] { 1.0, 0 };
		layout.rowWeights = new double[] { 1.0, 0, 0, 0, 0 };
		setBackground(new Color(175, 175, 175));
		setLayout(layout);

		board.setBackground(new Color(255, 255, 255));

		setVisible(true);

		GridBagConstraints gbc_board = new GridBagConstraints();
		gbc_board.insets = new Insets(0, 0, 0, 0);
		gbc_board.fill = GridBagConstraints.BOTH;
		gbc_board.gridx = 0;
		gbc_board.gridy = 0;
		gbc_board.gridwidth = 2;
		gbc_board.gridheight = 1;
		add(boardScroll, gbc_board);

		setVisible(true);

		boardScroll.getVerticalScrollBar().setUnitIncrement(getHeight() / 17);
		boardScroll.getHorizontalScrollBar().setUnitIncrement(getWidth() / 17);
		boardScroll.setViewportBorder(new LineBorder(new Color(150, 150, 150)));

		stats.getTableHeader().setReorderingAllowed(false);

		GridBagConstraints gbc_stats = new GridBagConstraints();
		gbc_stats.insets = new Insets(0, 0, 0, 0);
		gbc_stats.fill = GridBagConstraints.BOTH;
		gbc_stats.gridx = 0;
		gbc_stats.gridy = 1;
		gbc_stats.gridwidth = 1;
		gbc_stats.gridheight = 4;
		add(statsScroll, gbc_stats);
		statsScroll.setViewportBorder(new LineBorder(new Color(150, 150, 150)));

		setVisible(true);

		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { board.getWidth() };
		layout.rowHeights = new int[] { board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20 };
		layout.columnWeights = new double[] { 1.0 };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		board.setLayout(layout);

		GridBagConstraints gbc_text = new GridBagConstraints();
		gbc_text.anchor = GridBagConstraints.CENTER;
		gbc_text.gridx = 0;
		gbc_text.gridy = 1;
		gbc_text.gridheight = 7;
		board.add(text, gbc_text);
		text.setFont(new Font("Verdana", Font.PLAIN, 18));

		GridBagConstraints gbc_wordsleft = new GridBagConstraints();
		gbc_wordsleft.anchor = GridBagConstraints.WEST;
		gbc_wordsleft.gridx = 0;
		gbc_wordsleft.gridy = 19;
		board.add(wordsLeft, gbc_wordsleft);
		wordsLeft.setFont(new Font("Verdana", Font.PLAIN, 12));

		GridBagConstraints gbc_correct = new GridBagConstraints();
		gbc_correct.anchor = GridBagConstraints.CENTER;
		gbc_correct.gridx = 0;
		gbc_correct.gridy = 11;
		board.add(correct, gbc_correct);
		correct.setFont(new Font("Verdana", Font.PLAIN, 18));
		correct.setForeground(Color.GREEN);

		if (PracticeMode.MODE == 0) {
			GridBagConstraints gbc_input = new GridBagConstraints();
			gbc_input.anchor = GridBagConstraints.CENTER;
			gbc_input.gridx = 0;
			gbc_input.gridy = 8;
			board.add(input, gbc_input);

			GridBagConstraints gbc_commit = new GridBagConstraints();
			gbc_commit.anchor = GridBagConstraints.CENTER;
			gbc_commit.gridx = 0;
			gbc_commit.gridy = 9;
			board.add(commit, gbc_commit);

			input.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
				}

				public void keyReleased(KeyEvent e) {
				}

				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						PracticeMode.click(input.getText());
					}
				}
			});

			commit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					if (!input.getText().isEmpty()) {
						PracticeMode.click(input.getText());
					}
				}
			});
		} else if (PracticeMode.MODE == 1) {
			GridBagConstraints gbc_showanswer = new GridBagConstraints();
			gbc_showanswer.anchor = GridBagConstraints.CENTER;
			gbc_showanswer.gridx = 0;
			gbc_showanswer.gridy = 8;
			board.add(showAnswer, gbc_showanswer);

			GridBagConstraints gbc_answerlabel = new GridBagConstraints();
			gbc_answerlabel.anchor = GridBagConstraints.CENTER;
			gbc_answerlabel.gridx = 0;
			gbc_answerlabel.gridy = 8;
			board.add(answerLabel, gbc_answerlabel);

			GridBagConstraints gbc_right = new GridBagConstraints();
			gbc_right.insets = new Insets(0, 0, 0, 100);
			gbc_right.anchor = GridBagConstraints.CENTER;
			gbc_right.gridx = 0;
			gbc_right.gridy = 9;
			board.add(right, gbc_right);

			GridBagConstraints gbc_wrong = new GridBagConstraints();
			gbc_wrong.insets = new Insets(0, 100, 0, 0);
			gbc_wrong.anchor = GridBagConstraints.CENTER;
			gbc_wrong.gridx = 0;
			gbc_wrong.gridy = 9;
			board.add(wrong, gbc_wrong);

			showWrongRight(false);

			showAnswer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!calculating) {
						setCalculating(true);
						PracticeMode.showAnswer();
					}
				}
			});

			right.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!calculating) {
						setCalculating(true);
						PracticeMode.right();
					}
				}
			});

			wrong.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!calculating) {
						setCalculating(true);
						PracticeMode.wrong();
					}
				}
			});
		}

		setVisible(true);
	}

	public void setText(String newText) {
		this.text.setText(makeFancy(newText));
	}

	public void setInputForeground(Color color) {
		this.input.setForeground(color);
	}

	public void setInputText(String newText) {
		this.input.setText(newText);
	}

	public void setInputEditable(boolean bool) {
		this.input.setEditable(bool);
	}

	public void setAnswerText(String newText) {
		this.correct.setText(makeFancy(newText));
	}

	public void showWrongRight(boolean bool) {
		if (bool) {
			wrong.setVisible(true);
			right.setVisible(true);
		} else {
			wrong.setVisible(false);
			right.setVisible(false);
		}
	}

	public void setAnswer(String answer) {
		answerLabel.setText(makeFancy(answer));
	}

	public void showAnswer(boolean bool) {
		if (bool) {
			answerLabel.setVisible(true);
			showAnswer.setVisible(false);
		} else {
			answerLabel.setVisible(false);
			showAnswer.setVisible(true);
		}
	}

	public void setWordsLeft(int i) {
		String text = String.format("Nog %s woord%s te gaan", i, i == 1 ? "" : "en");
		this.wordsLeft.setText(text);
	}

	public void setCalculating(boolean bool) {
		this.calculating = bool;
	}

	public void updateStats() {
		String[] columns = { "Ronde", "Goed", "Fout" };
		Object[][] data = new Object[Math.max(PracticeMode.RIGHT.size(), PracticeMode.WRONG.size())][3];

		Iterator<Integer> it = PracticeMode.RIGHT.keySet().iterator();

		while (it.hasNext()) {
			int i = it.next();
			int right = PracticeMode.RIGHT.get(i).size();

			data[i][0] = "" + (i + 1);
			data[i][1] = "" + right;
		}

		Iterator<Integer> it1 = PracticeMode.WRONG.keySet().iterator();

		while (it1.hasNext()) {
			int i = it1.next();
			int wrong = PracticeMode.WRONG.get(i).size();

			data[i][0] = "" + (i + 1);
			data[i][2] = "" + wrong;
		}

		TableModel model = new DefaultTableModel(data, columns) {

			private static final long serialVersionUID = -2199204233229711842L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		stats.setModel(model);

		remove(statsScroll);

		statsScroll = new JScrollPane(stats);
		GridBagConstraints gbc_statsScroll = new GridBagConstraints();
		gbc_statsScroll.insets = new Insets(0, 0, 0, 0);
		gbc_statsScroll.fill = GridBagConstraints.BOTH;
		gbc_statsScroll.gridx = 0;
		gbc_statsScroll.gridy = 1;
		gbc_statsScroll.gridwidth = 1;
		gbc_statsScroll.gridheight = 4;
		add(statsScroll, gbc_statsScroll);
		statsScroll.setViewportBorder(new LineBorder(new Color(150, 150, 150)));

		validate();
	}

	private String makeFancy(String string) {
		String output = "";
		List<String> parts = new ArrayList<String>();

		if (string.length() > 35) {

			int index = string.indexOf(" ", 30);
			boolean change = true;

			while (string.length() > 35) {
				if (change) {
					index = string.indexOf(" ", 30);
				}
				change = true;
				
				if (index > 0) {
					String line = "<center>" + string.substring(0, index).trim() + "</center>" + "<br>";
					string = string.substring(index, string.length()).trim();

					parts.add(line);
				} else {
					int current = 29;
					int index2 = string.indexOf(" ", current);

					while (index2 < 0) {
						current--;
						index2 = string.indexOf(" ", current);
					}

					change = false;
					index = index2;
					continue;
				}
			}

			parts.add("<center>" + string + "</center>");

			output += "<html>";

			for (String s : parts) {
				output += s;
			}

			output += "</html>";
		} else {
			output = "<html>" + string + "</html>";
		}

		return output;
	}
}

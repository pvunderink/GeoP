package peppi.novum.geop.gui.puzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import peppi.novum.geop.crossword.CrosswordPuzzle;
import peppi.novum.geop.crossword.PuzzleGrid;
import peppi.novum.geop.crossword.Word;
import peppi.novum.geop.gui.GeoPane;

public class PuzzlePane extends JPanel implements GeoPane {

	private static final long serialVersionUID = -1894960548193357637L;

	private PuzzleGrid grid;

	private GridBagLayout layout = new GridBagLayout();

	private JLayeredPane board = new JLayeredPane();
	private JScrollPane boardScroll = new JScrollPane(board);

	private JTextArea hints = new JTextArea();
	private JScrollPane hintsScroll = new JScrollPane(hints);

	private JButton hint = new JButton("Hint");
	private JButton finish = new JButton("Finish");
	private JButton close = new JButton("Close");

	public PuzzlePane(PuzzleGrid grid) {
		this.grid = grid;
	}

	public void load(int standardWidth, int standardHeight) {
		layout.columnWidths = new int[] { getWidth() / 10 * 8, standardWidth / 10 * 2 };
		layout.rowHeights = new int[] { getHeight() / 20 * 16, standardHeight / 20, standardHeight / 20, standardHeight / 20, standardHeight / 20 };
		layout.columnWeights = new double[] { 1.0, 0 };
		layout.rowWeights = new double[] { 1.0, 0, 0, 0, 0 };
		setBackground(new Color(175, 175, 175));
		setLayout(layout);

		board.setBackground(new Color(255, 255, 255));
		hints.setEditable(false);

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

		GridBagConstraints gbc_hints = new GridBagConstraints();
		gbc_hints.insets = new Insets(0, 0, 0, 0);
		gbc_hints.fill = GridBagConstraints.BOTH;
		gbc_hints.gridx = 0;
		gbc_hints.gridy = 1;
		gbc_hints.gridwidth = 1;
		gbc_hints.gridheight = 4;
		add(hintsScroll, gbc_hints);
		hintsScroll.setViewportBorder(new LineBorder(new Color(150, 150, 150)));

		GridBagConstraints gbc_hint = new GridBagConstraints();
		gbc_hint.insets = new Insets(0, 0, 0, 0);
		gbc_hint.fill = GridBagConstraints.BOTH;
		gbc_hint.gridx = 1;
		gbc_hint.gridy = 1;
		gbc_hint.gridwidth = 1;
		gbc_hint.gridheight = 1;
		add(hint, gbc_hint);
		hint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				new HintWindow();
			}
		});
		
		GridBagConstraints gbc_finish = new GridBagConstraints();
		gbc_finish.insets = new Insets(0, 0, 0, 0);
		gbc_finish.fill = GridBagConstraints.BOTH;
		gbc_finish.gridx = 1;
		gbc_finish.gridy = 2;
		gbc_finish.gridwidth = 1;
		gbc_finish.gridheight = 1;
		add(finish, gbc_finish);
		finish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				int wrong = CrosswordPuzzle.checkAnswers();
				int correct = CrosswordPuzzle.GRID.getWords().size() - wrong;

				new FinishWindow(correct, wrong);
			}
		});
		
		GridBagConstraints gbc_close = new GridBagConstraints();
		gbc_close.insets = new Insets(0, 0, 0, 0);
		gbc_close.fill = GridBagConstraints.BOTH;
		gbc_close.gridx = 1;
		gbc_close.gridy = 4;
		gbc_close.gridwidth = 1;
		gbc_close.gridheight = 1;
		add(close, gbc_close);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				CrosswordPuzzle.reset();
			}
		});

		drawGrid(grid.getWidth(), grid.getHeight());
		loadHints();

		setVisible(true);
	}

	public void drawGrid(int slotsX, int slotsY) {
		final GridBagLayout boardgrid = new GridBagLayout();
		boardgrid.columnWidths = createGridX(slotsX);
		boardgrid.rowHeights = createGridY(slotsY);
		boardgrid.columnWeights = createGridWeightsX(slotsX);
		boardgrid.rowWeights = createGridWeightsY(slotsY);
		board.setLayout(boardgrid);

		for (int y = 0; y < boardgrid.rowHeights.length; y++) {
			for (int x = 0; x < boardgrid.columnWidths.length; x++) {
				if (grid.getRows().get(y).get(x) == ' ') {
					continue;
				}

				JTextField field = new JTextField(1);
				field.setBackground(new Color(230, 230, 230));

				field.setHorizontalAlignment(JTextField.CENTER);

				GridBagConstraints gbc_field = new GridBagConstraints();
				gbc_field.fill = GridBagConstraints.BOTH;
				gbc_field.gridx = x;
				gbc_field.gridy = y;
				board.setLayer(field, 0);
				board.add(field, gbc_field);

				field.addKeyListener(new KeyListener() {
					@Override
					public void keyPressed(KeyEvent ev) {
						JTextField field = (JTextField) ev.getComponent();
						if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
							board.requestFocus();

							String text = field.getText();
							field.setBackground(new Color(230, 230, 230));

							if (text.length() > 1) {
								field.setText(text.substring(0, 1));

								int x = boardgrid.getConstraints(field).gridx;
								int y = boardgrid.getConstraints(field).gridy;

								List<Word> words = grid.getOptionalGrid().getWordsAt(x, y);

								for (int i = 0; i < board.getComponents().length; i++) {
									if (board.getComponent(i) instanceof JTextField) {
										JTextField field2 = (JTextField) board.getComponent(i);

										int x2 = boardgrid.getConstraints(field2).gridx;
										int y2 = boardgrid.getConstraints(field2).gridy;

										List<Word> words2 = grid.getOptionalGrid().getWordsAt(x2, y2);

										for (Word w : words2) {
											if (w.getDirection() == 0 && words.contains(w)) {
												int startindex = x - w.getStart().getX();
												int index = (x2 - w.getStart().getX()) - startindex;

												if (index >= 0 && text.length() > index) {
													char letter = text.charAt(index);

													field2.setText("" + letter);
													field2.setBackground(new Color(230, 230, 230));
												}
											}
											if (words.size() == 1 && w.getDirection() == 1 && words.contains(w)) {
												int startindex = y - w.getStart().getY();
												int index = (y2 - w.getStart().getY()) - startindex;

												if (index >= 0 && text.length() > index) {
													char letter = text.charAt(index);

													field2.setText("" + letter);
													field2.setBackground(new Color(230, 230, 230));
												}
											}
										}
									}
								}
							}
						}
					}

					public void keyReleased(KeyEvent arg0) {

					}

					public void keyTyped(KeyEvent arg0) {
					}

				});

				field.addFocusListener(new FocusListener() {

					public void focusLost(FocusEvent ev) {
						JTextField field = (JTextField) ev.getComponent();

						String text = field.getText();

						if (text.length() > 1) {
							field.setText(text.substring(0, 1));
						}
					}

					public void focusGained(FocusEvent e) {
					}
				});

				for (Word w : grid.getWords()) {
					if (w.getStart().getX() == x && w.getStart().getY() == y) {
						JLabel number = new JLabel("" + w.getNumber());

						number.setFont(new Font("Verdana", Font.PLAIN, 9));
						number.setForeground(new Color(100, 100, 100));

						GridBagConstraints gbc_number = new GridBagConstraints();
						if (w.getDirection() == 0) {
							gbc_number.anchor = GridBagConstraints.NORTHWEST;
							gbc_number.insets = new Insets(2, 4, 0, 0);
						} else {
							gbc_number.anchor = GridBagConstraints.SOUTHWEST;
							gbc_number.insets = new Insets(0, 4, 2, 0);
						}
						gbc_number.gridx = x;
						gbc_number.gridy = y;
						board.setLayer(number, 1);
						board.add(number, gbc_number);
					}
				}
			}
		}
	}

	public void loadHints() {
		List<Word> horizontal = new ArrayList<Word>();
		List<Word> vertical = new ArrayList<Word>();

		for (Word w : grid.getWords()) {
			if (w.getDirection() == 0) {
				horizontal.add(w);
			} else {
				vertical.add(w);
			}
		}

		hints.setText("Horizontal:\n\n");

		for (Word w : horizontal) {
			hints.setText(hints.getText() + w.getNumber() + ". " + w.getDefinition() + "\n");
		}

		hints.setText(hints.getText() + "\nVertical:\n\n");

		for (Word w : vertical) {
			hints.setText(hints.getText() + w.getNumber() + ". " + w.getDefinition() + "\n");
		}
	}

	public int[] createGridX(int number) {
		int[] widths = new int[number];

		for (int i = 0; i < widths.length; i++) {
			widths[i] = 33;
		}

		return widths;
	}

	public double[] createGridWeightsX(int width) {
		double[] weights = new double[width];

		for (int i = 0; i < weights.length; i++) {
			weights[i] = 0;
		}

		return weights;
	}

	public int[] createGridY(int number) {
		int[] heights = new int[number];

		for (int i = 0; i < heights.length; i++) {
			heights[i] = 33;
		}

		return heights;
	}

	public double[] createGridWeightsY(int height) {
		double[] weights = new double[height];

		for (int i = 0; i < weights.length; i++) {
			weights[i] = 0;
		}

		return weights;
	}

	public JLayeredPane getBoard() {
		return board;
	}
}

package peppi.novum.geop.gui.practicemode;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import peppi.novum.geop.gui.GeoPane;
import peppi.novum.geop.practicemode.PracticeMode;
import peppi.novum.geop.util.WordList;

public class SettingsPane extends JPanel implements GeoPane {

	private static final long serialVersionUID = 8449841303274086618L;
	private GridBagLayout layout = new GridBagLayout();

	WordList wordList;

	private JPanel board = new JPanel();

	JLabel order = new JLabel("Volgorde");
	JRadioButton orderNormal;
	JRadioButton orderInverse;

	JLabel mode = new JLabel("Modus");
	JRadioButton modeNormal = new JRadioButton("Normaal");
	JRadioButton modeMind = new JRadioButton("In gedachten");

	JLabel extra = new JLabel("Extra");
	JCheckBox caseSensitive;

	JButton ok = new JButton("Ok");

	public SettingsPane(WordList wordList) {
		this.wordList = wordList;

		if (wordList.defaultDir()) {
			caseSensitive = new JCheckBox("Hoofdlettergevoelig", wordList.getCase0());
		} else {
			caseSensitive = new JCheckBox("Hoofdlettergevoelig", wordList.getCase1());
		}
		orderNormal = new JRadioButton(wordList.getLabel0() + "-" + wordList.getLabel1());
		orderInverse = new JRadioButton(wordList.getLabel1() + "-" + wordList.getLabel0());
	}

	public void load(int standardWidth, int standardHeight) {
		layout.columnWidths = new int[] { getWidth() };
		layout.rowHeights = new int[] { getHeight() };
		layout.columnWeights = new double[] { 1.0 };
		layout.rowWeights = new double[] { 1.0 };
		setBackground(new Color(175, 175, 175));
		setLayout(layout);

		setVisible(true);

		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { board.getWidth() };
		layout.rowHeights = new int[] { board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20, board.getHeight() / 20 };
		layout.columnWeights = new double[] { 1.0 };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		board.setLayout(layout);
		board.setBackground(new Color(175, 175, 175));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(board, gbc);

		setVisible(true);

		GridBagConstraints gbc_order = new GridBagConstraints();
		gbc_order.anchor = GridBagConstraints.WEST;
		gbc_order.gridx = 0;
		gbc_order.gridy = 5;
		order.setFont(new Font("Verdana", Font.PLAIN, 16));
		board.add(order, gbc_order);

		GridBagConstraints gbc_orderNormal = new GridBagConstraints();
		gbc_orderNormal.anchor = GridBagConstraints.WEST;
		gbc_orderNormal.gridx = 0;
		gbc_orderNormal.gridy = 6;
		orderNormal.setFont(new Font("Verdana", Font.PLAIN, 14));
		board.add(orderNormal, gbc_orderNormal);
		orderNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (orderNormal.isSelected()) {
					if (wordList.getCase0()) {
						caseSensitive.setSelected(true);
					} else {
						caseSensitive.setSelected(false);
					}
					
					if (wordList.getMode0() == 0) {
						modeNormal.setSelected(true);
						modeMind.setSelected(false);
					} else {
						modeMind.setSelected(true);
						modeNormal.setSelected(false);
					}
					orderInverse.setSelected(false);
				} else {
					if (wordList.getCase1()) {
						caseSensitive.setSelected(true);
					} else {
						caseSensitive.setSelected(false);
					}
					
					if (wordList.getMode1() == 0) {
						modeNormal.setSelected(true);
						modeMind.setSelected(false);
					} else {
						modeMind.setSelected(true);
						modeNormal.setSelected(false);
					}
					orderInverse.setSelected(true);
				}
			}
		});

		GridBagConstraints gbc_orderInverse = new GridBagConstraints();
		gbc_orderInverse.anchor = GridBagConstraints.WEST;
		gbc_orderInverse.gridx = 0;
		gbc_orderInverse.gridy = 7;
		orderInverse.setFont(new Font("Verdana", Font.PLAIN, 14));
		board.add(orderInverse, gbc_orderInverse);
		orderInverse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (orderInverse.isSelected()) {
					if (wordList.getCase1()) {
						caseSensitive.setSelected(true);
					} else {
						caseSensitive.setSelected(false);
					}
					
					if (wordList.getMode1() == 0) {
						modeNormal.setSelected(true);
						modeMind.setSelected(false);
					} else {
						modeMind.setSelected(true);
						modeNormal.setSelected(false);
					}
					orderNormal.setSelected(false);
				} else {
					if (wordList.getCase0()) {
						caseSensitive.setSelected(true);
					} else {
						caseSensitive.setSelected(false);
					}
					
					if (wordList.getMode0() == 0) {
						modeNormal.setSelected(true);
						modeMind.setSelected(false);
					} else {
						modeMind.setSelected(true);
						modeNormal.setSelected(false);
					}
					orderNormal.setSelected(true);
				}
			}
		});

		GridBagConstraints gbc_mode = new GridBagConstraints();
		gbc_mode.anchor = GridBagConstraints.WEST;
		gbc_mode.gridx = 0;
		gbc_mode.gridy = 9;
		mode.setFont(new Font("Verdana", Font.PLAIN, 16));
		board.add(mode, gbc_mode);

		GridBagConstraints gbc_modeNormal = new GridBagConstraints();
		gbc_modeNormal.anchor = GridBagConstraints.WEST;
		gbc_modeNormal.gridx = 0;
		gbc_modeNormal.gridy = 10;
		modeNormal.setFont(new Font("Verdana", Font.PLAIN, 14));
		board.add(modeNormal, gbc_modeNormal);
		modeNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modeNormal.isSelected()) {
					modeMind.setSelected(false);
				} else {
					modeMind.setSelected(true);
				}
			}
		});

		GridBagConstraints gbc_modeMind = new GridBagConstraints();
		gbc_modeMind.anchor = GridBagConstraints.WEST;
		gbc_modeMind.gridx = 0;
		gbc_modeMind.gridy = 11;
		modeMind.setFont(new Font("Verdana", Font.PLAIN, 14));
		board.add(modeMind, gbc_modeMind);
		modeMind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modeMind.isSelected()) {
					modeNormal.setSelected(false);
				} else {
					modeNormal.setSelected(true);
				}
			}
		});
		
		if (wordList.defaultDir()) {
			if (wordList.getMode0() == 0) {
				modeNormal.setSelected(true);
			} else {
				modeMind.setSelected(true);
			}
		} else {
			if (wordList.getMode1() == 0) {
				modeNormal.setSelected(true);
			} else {
				modeMind.setSelected(true);
			}
		}
		
		GridBagConstraints gbc_extra = new GridBagConstraints();
		gbc_extra.anchor = GridBagConstraints.WEST;
		gbc_extra.gridx = 0;
		gbc_extra.gridy = 13;
		extra.setFont(new Font("Verdana", Font.PLAIN, 16));
		board.add(extra, gbc_extra);

		GridBagConstraints gbc_case = new GridBagConstraints();
		gbc_case.anchor = GridBagConstraints.WEST;
		gbc_case.gridx = 0;
		gbc_case.gridy = 14;
		caseSensitive.setFont(new Font("Verdana", Font.PLAIN, 14));
		board.add(caseSensitive, gbc_case);
		
		if (wordList.defaultDir()) {
			orderNormal.setSelected(true);

			if (wordList.getCase0()) {
				caseSensitive.setSelected(true);
			} else {
				caseSensitive.setSelected(false);
			}
		} else {
			orderInverse.setSelected(true);

			if (wordList.getCase1()) {
				caseSensitive.setSelected(true);
			} else {
				caseSensitive.setSelected(false);
			}
		}

		GridBagConstraints gbc_ok = new GridBagConstraints();
		gbc_ok.anchor = GridBagConstraints.WEST;
		gbc_ok.gridx = 0;
		gbc_ok.gridy = 17;
		ok.setFont(new Font("Verdana", Font.PLAIN, 14));
		board.add(ok, gbc_ok);
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int MODE = 0;
				int ORDER = 0;
				boolean CASE_SENSITIVE = true;

				if (modeMind.isSelected()) {
					MODE = 1;
				}
				if (orderInverse.isSelected()) {
					ORDER = 1;
				}
				if (!caseSensitive.isSelected()) {
					CASE_SENSITIVE = false;
				}
				PracticeMode.load(wordList, MODE, ORDER, CASE_SENSITIVE);
			}
		});

		setVisible(true);
	}

}

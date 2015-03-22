package peppi.novum.geop.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageWindow extends JFrame {
	
	private static final long serialVersionUID = -3459111580547975970L;
	int width = 350, height = 200;

	JPanel pane = new JPanel();
	
	public MessageWindow(String message, Color color) {
		setTitle("Info");
		setResizable(false);
		setBounds(new Rectangle(width, height));
		setLocationRelativeTo(null);

		pane.setLayout(null);
		pane.setBackground(new Color(75, 75, 75));
		setContentPane(pane);

		JLabel chooseLabel = new JLabel(message);
		chooseLabel.setHorizontalAlignment(JLabel.CENTER);
		chooseLabel.setForeground(color);
		chooseLabel.setBounds(width / 2 - 350 / 2, height / 2 - 60, 350, 30);
		pane.add(chooseLabel);

		JButton okButton = new JButton("Ok");
		okButton.setBounds(width / 2 - 100 / 2, height / 2 + 10, 100, 30);
		pane.add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});

		setVisible(true);
	}

}

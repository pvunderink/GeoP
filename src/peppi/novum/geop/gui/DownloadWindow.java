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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import peppi.novum.geop.Main;
import peppi.novum.geop.network.NetworkManager;

public class DownloadWindow extends JFrame {

	private static final long serialVersionUID = -3423111534247975970L;
	int width = 250, height = 400;
	JPanel pane = new JPanel();
	
	Charset cs = Charset.forName("UTF-8");

	String selected = "";

	public DownloadWindow() {
		setTitle("Download");
		setResizable(false);
		setBounds(new Rectangle(width, height));
		setLocationRelativeTo(null);

		JPanel pane = new JPanel();
		pane.setLayout(null);
		pane.setBackground(new Color(75, 75, 75));
		setContentPane(pane);

		setVisible(true);
		setResizable(false);

		JLabel label = new JLabel("Connecting...");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBounds(getWidth() / 2 - 100 / 2, 150, 100, 20);
		label.setForeground(new Color(230, 230, 230));
		pane.add(label);

		JButton button = new JButton("Cancel");
		button.setBounds(getWidth() / 2 - 100 / 2, 250, 100, 30);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pane.add(button);

		if (Main.NETWORK == null) {
			try {
				Main.NETWORK = new NetworkManager(InetAddress.getByName("86.82.78.195"), 8753);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		if (Main.NETWORK != null) {
			Main.NETWORK.openConnection(this);
		}

		setVisible(true);
	}

	public void load(HashMap<String, List<String>> files) {
		setResizable(false);
		
		GridBagLayout layout = new GridBagLayout();

		layout.columnWidths = new int[] { getWidth() / 2, getWidth() / 2 };
		layout.rowHeights = new int[] { getHeight() / 20 * 19, getHeight() / 20 * 1 };
		layout.columnWeights = new double[] { 1.0, 1.0 };
		layout.rowWeights = new double[] { 1.0, 0 };

		pane.setLayout(layout);
		pane.setBackground(new Color(75, 75, 75));
		setContentPane(pane);

		setVisible(true);
		
		loadTree(files);

		JButton downloadButton = new JButton("Download");
		GridBagConstraints gbc_download = new GridBagConstraints();
		gbc_download.fill = GridBagConstraints.CENTER;
		gbc_download.insets = new Insets(5, 5, 5, 5);
		gbc_download.gridx = 0;
		gbc_download.gridy = 1;
		pane.add(downloadButton, gbc_download);
		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (!selected.endsWith(".gop")) {
					new MessageWindow("Try opening the folder first ;)", Color.RED);
					return;
				}
								
				if (Main.NETWORK != null) {
					Main.NETWORK.requestDownload(selected);
					dispose();
				}
			}
		});

		JButton closeButton = new JButton("Close");
		GridBagConstraints gbc_close = new GridBagConstraints();
		gbc_close.fill = GridBagConstraints.CENTER;
		gbc_close.insets = new Insets(5, 5, 5, 5);
		gbc_close.gridx = 1;
		gbc_close.gridy = 1;
		pane.add(closeButton, gbc_close);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});

		setVisible(true);
	}
	
	public void loadTree(HashMap<String, List<String>> files) {
		JTree tree = new JTree();
		JScrollPane scrollTree = new JScrollPane(tree);
		scrollTree.setVisible(false);
		remove(scrollTree);

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Lists");

		for (String dir : files.keySet()) {
			DefaultMutableTreeNode subroot = new DefaultMutableTreeNode(dir);

			for (String file : files.get(dir)) {
				DefaultMutableTreeNode subfile = new DefaultMutableTreeNode(file.substring(0, file.lastIndexOf(".gop")));
				subroot.add(subfile);
			}

			root.add(subroot);
		}

		tree = new JTree(root);
		scrollTree = new JScrollPane(tree);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				String path = new String("".getBytes(cs), cs);
				String treePath = new String(e.getPath().toString().getBytes(cs), cs);
				treePath = treePath.substring(1, treePath.length() - 1);

				String[] parts = treePath.split(", ");
				for (int i = 0; i < parts.length; i++) {
					String s = parts[i];
					if (i < 2) {
						path += s + "/";
					} else {
						path += s + ".gop";
					}
				}
				selected = path;
			}
		});

		GridBagConstraints gbc_tree = new GridBagConstraints();
		gbc_tree.fill = GridBagConstraints.BOTH;
		gbc_tree.insets = new Insets(5, 5, 5, 5);
		gbc_tree.gridx = 0;
		gbc_tree.gridy = 0;
		gbc_tree.gridwidth = 2;
		pane.add(scrollTree, gbc_tree);
		this.pane.add(scrollTree, gbc_tree);

		setVisible(true);
	}

}

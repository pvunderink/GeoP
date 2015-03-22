package peppi.novum.geop.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import peppi.novum.geop.Main;
import peppi.novum.geop.util.SaveReader;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -416338914700822399L;
	private int width, height;

	private JPanel startPane = new JPanel();
	private JPanel pane = new JPanel();
	private GridBagLayout layout = new GridBagLayout();

	private JLabel welcomeText = new JLabel("GeoPractice", SwingConstants.CENTER);
	private JLabel welcomeText2 = new JLabel(Main.VERSION, SwingConstants.CENTER);

	private JTree tree = new JTree(new DefaultMutableTreeNode("Root"));
	private JScrollPane scrollTree = new JScrollPane(tree);
	private JButton refresh = new JButton("Refresh");
	private JButton open = new JButton("Open");

	private JButton download = new JButton("Download Lists");
	private JButton settings = new JButton("Settings");

	private JPanel area = new JPanel();

	private String selectedPath = "";

	private int areaStandardWidth, areaStandardHeight;

	public MainWindow() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		width = (int) (gd.getDisplayMode().getWidth() / 1.7);
		height = (int) (gd.getDisplayMode().getHeight() / 1.7);

		setTitle(Main.TITLE + " - " + Main.VERSION);
		setBounds(new Rectangle(width, height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension((int) (width / 1.2), (int) (height / 1.2)));
		setResizable(true);

		// Startup screen
		startPane.setLayout(null);
		welcomeText.setBounds(getWidth() / 2 - 150, 200, 300, 60);
		welcomeText.setFont(new Font(welcomeText.getFont().getName(), Font.PLAIN, 48));
		startPane.add(welcomeText);

		welcomeText2.setBounds(getWidth() / 2 - 150, 240, 300, 60);
		welcomeText2.setFont(new Font(welcomeText2.getFont().getName(), Font.PLAIN, 24));
		startPane.add(welcomeText2);

		setContentPane(startPane);
		setVisible(true);
		setResizable(false);

		long old = System.currentTimeMillis();
		while (System.currentTimeMillis() - old < 2000) {
			// Wait
		}

		setResizable(true);
		setVisible(true);

		layout.columnWidths = new int[] { getWidth() / 10 * 2, getWidth() / 10 * 8 };
		layout.rowHeights = new int[] { getHeight() / 20, getHeight() / 20, getHeight() / 20 * 16, getHeight() / 20, getHeight() / 20 };
		layout.columnWeights = new double[] { 0, 1.0 };
		layout.rowWeights = new double[] { 0, 0, 1.0, 0, 0 };

		pane.setLayout(layout);
		pane.setBackground(new Color(95, 95, 95));
		setContentPane(pane);

		loadTree();

		GridBagConstraints gbc_area = new GridBagConstraints();
		gbc_area.insets = new Insets(7, 5, 5, 5);
		gbc_area.fill = GridBagConstraints.BOTH;
		gbc_area.gridx = 1;
		gbc_area.gridy = 0;
		gbc_area.gridwidth = 1;
		gbc_area.gridheight = 5;
		pane.add(area, gbc_area);
		area.setBackground(new Color(175, 175, 175));
		setVisible(true);
		areaStandardWidth = area.getWidth();
		areaStandardHeight = area.getHeight();

		GridBagConstraints gbc_refresh = new GridBagConstraints();
		gbc_refresh.insets = new Insets(5, 5, 0, 0);
		gbc_refresh.fill = GridBagConstraints.HORIZONTAL;
		gbc_refresh.gridx = 0;
		gbc_refresh.gridy = 0;
		gbc_refresh.gridwidth = 1;
		gbc_refresh.gridheight = 1;
		pane.add(refresh, gbc_refresh);
		refresh.setBackground(new Color(230, 230, 230));
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				loadTree();
			}
		});

		GridBagConstraints gbc_open = new GridBagConstraints();
		gbc_open.insets = new Insets(0, 5, 0, 0);
		gbc_open.fill = GridBagConstraints.HORIZONTAL;
		gbc_open.gridx = 0;
		gbc_open.gridy = 1;
		gbc_open.gridwidth = 1;
		gbc_open.gridheight = 1;
		pane.add(open, gbc_open);
		open.setBackground(new Color(230, 230, 230));
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (!selectedPath.isEmpty()) {
					if (selectedPath.endsWith(".gop")) {
						File f = new File(Main.FILE_PATH + selectedPath);

						new OpenWindow(f);
					} else {
						new MessageWindow("Try opening the folder first ;)", Color.RED);
					}
				}
			}
		});

		GridBagConstraints gbc_download = new GridBagConstraints();
		gbc_download.insets = new Insets(0, 5, 0, 0);
		gbc_download.fill = GridBagConstraints.HORIZONTAL;
		gbc_download.gridx = 0;
		gbc_download.gridy = 3;
		gbc_download.gridwidth = 1;
		gbc_download.gridheight = 1;
		pane.add(download, gbc_download);
		download.setBackground(new Color(230, 230, 230));
		download.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				new DownloadWindow();
			}
		});

		GridBagConstraints gbc_settings = new GridBagConstraints();
		gbc_settings.insets = new Insets(0, 5, 0, 0);
		gbc_settings.fill = GridBagConstraints.HORIZONTAL;
		gbc_settings.gridx = 0;
		gbc_settings.gridy = 4;
		gbc_settings.gridwidth = 1;
		gbc_settings.gridheight = 1;
		pane.add(settings, gbc_settings);
		settings.setBackground(new Color(230, 230, 230));

		setVisible(true);
	}

	public void loadTree() {
		scrollTree.setVisible(false);
		remove(scrollTree);

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Lists");

		HashMap<File, List<File>> lists = SaveReader.loadLists();
		for (File file : lists.keySet()) {
			DefaultMutableTreeNode subroot = new DefaultMutableTreeNode(file.getName());

			for (File f : lists.get(file)) {
				DefaultMutableTreeNode subfile = new DefaultMutableTreeNode(f.getName().substring(0, f.getName().lastIndexOf(".gop")));
				subroot.add(subfile);
			}

			root.add(subroot);
		}

		tree = new JTree(root);
		scrollTree = new JScrollPane(tree);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				String path = "";
				String treePath = e.getPath().toString();
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
				selectedPath = path;
			}
		});

		GridBagConstraints gbc_tree = new GridBagConstraints();
		gbc_tree.insets = new Insets(0, 5, 0, 0);
		gbc_tree.fill = GridBagConstraints.BOTH;
		gbc_tree.gridx = 0;
		gbc_tree.gridy = 2;
		gbc_tree.gridwidth = 1;
		gbc_tree.gridheight = 1;
		this.pane.add(scrollTree, gbc_tree);

		setVisible(true);
	}

	public void loadArea(JPanel pane, GeoPane board) {
		setResizable(false);
		area.setVisible(false);
		this.pane.remove(area);
		area = pane;

		GridBagConstraints gbc_area = new GridBagConstraints();
		gbc_area.insets = new Insets(7, 5, 5, 5);
		gbc_area.fill = GridBagConstraints.BOTH;
		gbc_area.gridx = 1;
		gbc_area.gridy = 0;
		gbc_area.gridwidth = 1;
		gbc_area.gridheight = 5;
		this.pane.add(area, gbc_area);

		setVisible(true);

		board.load(areaStandardWidth, areaStandardHeight);

		setVisible(true);
		setResizable(true);
	}

	public void clearArea() {
		setResizable(false);
		area.setVisible(false);
		pane.remove(area);
		area = new JPanel();
		area.setBackground(new Color(175, 175, 175));

		GridBagConstraints gbc_area = new GridBagConstraints();
		gbc_area.insets = new Insets(7, 5, 5, 5);
		gbc_area.fill = GridBagConstraints.BOTH;
		gbc_area.gridx = 1;
		gbc_area.gridy = 0;
		gbc_area.gridwidth = 1;
		gbc_area.gridheight = 5;
		this.pane.add(area, gbc_area);

		setVisible(true);
		setResizable(true);
	}

}

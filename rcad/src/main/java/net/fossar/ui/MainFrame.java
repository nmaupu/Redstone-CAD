package net.fossar.ui;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.fossar.core.DataGrid;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final String VERSION="0.1";
	public static final String APPNAME="Redstone CAD";
	public static final int DEFAULT_WIDTH=800;
	public static final int DEFAULT_HEIGHT=600;
	private DataGrid grid = new DataGrid(20, 30, 10);
	private Viewport viewport = new Viewport(grid);
	public static final MainToolBar mainToolBar = new MainToolBar();
	
	public MainFrame() {
		super(APPNAME+" - v"+VERSION);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container pane = super.getContentPane();
		pane.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		BoxLayout layout = new BoxLayout(pane, BoxLayout.Y_AXIS);
		
		// Set and organize main layout
		pane.setLayout(layout);
		
		// Viewport
		JPanel p = new JPanel();
		JScrollPane sp = new JScrollPane(p);
		p.add(viewport);
		pane.add(sp);
		
		pane.add(mainToolBar);
		
		super.pack();
		super.setLocationByPlatform(true);
		super.setVisible(false);
	}
	
	public DataGrid getGrid() {
		return grid;
	}
	
	public Viewport getViewport() {
		return viewport;
	}
}

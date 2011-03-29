package net.fossar.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(MainFrame.class);
	
	public static final String VERSION="0.1";
	public static final String APPNAME="Redstone CAD";
	public static final int DEFAULT_WIDTH=800;
	public static final int DEFAULT_HEIGHT=600;
	private List<Viewport> viewports = new ArrayList<Viewport>();
	public final MainToolBar mainToolBar = new MainToolBar();
	
	private ViewportStack viewportStack = new ViewportStack(this);
	
	public MainFrame() {
		super(APPNAME+" - v"+VERSION);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container pane = super.getContentPane();
		pane.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		BoxLayout layout = new BoxLayout(pane, BoxLayout.Y_AXIS);
		
		// Set and organize main layout
		pane.setLayout(layout);
		
		// Viewports
		JPanel p = new JPanel();
		p.add(viewportStack);
		JScrollPane scrollPaneViewport = new JScrollPane(p);
		viewportStack.firstLayer();
		
		pane.add(scrollPaneViewport);
		pane.add(mainToolBar);
		
		super.pack();
		super.setLocationByPlatform(true);
		super.setVisible(false);
	}
	
	public List<Viewport> getViewports() {
		return viewports;
	}
}

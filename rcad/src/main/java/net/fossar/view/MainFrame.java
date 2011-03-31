package net.fossar.view;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.fossar.view.toolbar.MainToolBar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements IMainFrame {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(MainFrame.class);
	
	public static final String VERSION="0.1";
	public static final String APPNAME="Redstone CAD";
	public static final int DEFAULT_WIDTH=800;
	public static final int DEFAULT_HEIGHT=600;
	
	
	private MainToolBar mainToolBar;
	private ViewportStack viewportStack;
	
	public MainFrame(MainToolBar mtb, ViewportStack vps) {
		this.mainToolBar = mtb;
		this.viewportStack = vps;
		doInit();
	}
	
	@Override
	public void doInit() {
		super.setTitle(APPNAME+" - v"+VERSION);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//viewportStack = (ViewportStack)presenter.getView(Director.VIEWPORT_STACK);
		
		
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
		super.setVisible(true);
	}
	
	public ViewportStack getViewportStack() {
		return viewportStack;
	}
}

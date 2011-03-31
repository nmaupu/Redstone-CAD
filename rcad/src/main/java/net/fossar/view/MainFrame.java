package net.fossar.view;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.fossar.presenter.Director;
import net.fossar.presenter.Presenter;
import net.fossar.view.toolbar.MainToolBar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements View {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(MainFrame.class);
	
	private Presenter presenter;
	
	public static final String VERSION="0.1";
	public static final String APPNAME="Redstone CAD";
	public static final int DEFAULT_WIDTH=800;
	public static final int DEFAULT_HEIGHT=600;
	private MainToolBar mainToolBar = null;
	private ViewportStack viewportStack = null;
	
	public MainFrame(Presenter presenter) {
		super(APPNAME+" - v"+VERSION);
		this.presenter = presenter;
		mainToolBar = (MainToolBar)presenter.getView(Director.MAIN_TOOL_BAR);
		viewportStack = (ViewportStack)presenter.getView(Director.VIEWPORT_STACK);
		
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
		super.setVisible(true);
	}
	
	public ViewportStack getViewportStack() {
		return viewportStack;
	}
}

package net.fossar.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class UIBuilder {
	private static final Logger logger = LoggerFactory.getLogger(UIBuilder.class);
	private static final MainFrame mainFrame = new MainFrame();
	
	public static void main(String args[]) {
		logger.info("Creating main frame");
		mainFrame.setVisible(true);
	}
}

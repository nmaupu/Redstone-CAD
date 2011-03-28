package net.fossar.ui;

public abstract class UIBuilder {
	public static MainFrame mainFrame = new MainFrame();
	
	public static void main(String args[]) {
		mainFrame.setVisible(true);
	}
}

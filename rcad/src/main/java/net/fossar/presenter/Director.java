package net.fossar.presenter;

import net.fossar.view.IMainFrame;
import net.fossar.view.MainFrame;
import net.fossar.view.ViewportStack;
import net.fossar.view.toolbar.MainToolBar;

public abstract class Director {
	public static final DataGridController dataGridController = new DataGridController();
	protected static final GridViewEventController gridViewEventController = new GridViewEventController();
	protected static final ToolBarActionController toolBarActionController = new ToolBarActionController();
	protected static final MainToolBar mainToolBar = new MainToolBar(toolBarActionController);
	protected static final ViewportStack viewportStack = new ViewportStack(
			dataGridController.getDataGrid().getRows(),
			dataGridController.getDataGrid().getCols(),
			dataGridController.getDataGrid().getLayers());
	protected static IMainFrame mainFrame = new MainFrame(mainToolBar, viewportStack);
	
	
	public static void doInit() {
		// Subscriptions to views
		viewportStack.addGridViewEventListener(gridViewEventController);
		
		// Show up application
		mainFrame.setVisible(true);
	}
	
	public static void main(String args[]) {
		Director.doInit();
	}
}

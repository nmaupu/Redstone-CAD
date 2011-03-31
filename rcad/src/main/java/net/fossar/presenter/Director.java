package net.fossar.presenter;

import net.fossar.model.DataGrid;
import net.fossar.view.MainFrame;
import net.fossar.view.ViewportStack;
import net.fossar.view.toolbar.MainToolBar;

public abstract class Director {
	public static int DEFAULT_GRID_ROWS=20;
	public static int DEFAULT_GRID_COLS=30;
	public static int DEFAULT_GRID_LAYERS=10;
	
	public static final String MAIN_FRAME="mainFrame";
	public static final String MAIN_TOOL_BAR="mainToolBar";
	public static final String VIEWPORT_STACK="viewportStack";
	public static final String DATAGRID="dataGrid";
	static Presenter mainPresenter;
	
	public static void doInit() {
		mainPresenter = new MainPresenter();
		
		// Models
		mainPresenter.addModel(Director.DATAGRID, new DataGrid(DEFAULT_GRID_ROWS, DEFAULT_GRID_COLS, DEFAULT_GRID_LAYERS));
		
		// Views
		mainPresenter.addView(Director.MAIN_TOOL_BAR, new MainToolBar(mainPresenter));
		mainPresenter.addView(Director.VIEWPORT_STACK, new ViewportStack(mainPresenter));
		mainPresenter.addView(Director.MAIN_FRAME, new MainFrame(mainPresenter));
	}
	
	public static void main(String args[]) {
		Director.doInit();
	}
}

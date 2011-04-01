package net.fossar.presenter;

import net.fossar.model.core.DataGrid;
import net.fossar.model.core.IDataGrid;

public class DataGridController implements Controller {
	private static int DEFAULT_GRID_ROWS=20;
	private static int DEFAULT_GRID_COLS=30;
	private static int DEFAULT_GRID_LAYERS=10;
	
	IDataGrid dataGrid = new DataGrid(DEFAULT_GRID_ROWS, DEFAULT_GRID_COLS, DEFAULT_GRID_LAYERS);
	
	public DataGridController() {
	}
	
	public IDataGrid getDataGrid() {
		return dataGrid;
	}
}

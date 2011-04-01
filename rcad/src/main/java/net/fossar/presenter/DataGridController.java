/*
 * RCad is a software to help manipulating minecraft's redstone.
 * Copyright (C) 2011. mathieu_dot_grenonville_at_gmail_dot_com - nmaupu_at_gmail_dot_com
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.fossar.presenter;

import net.fossar.model.core.DataGrid;
import net.fossar.model.core.IDataGrid;

public class DataGridController implements IController {
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

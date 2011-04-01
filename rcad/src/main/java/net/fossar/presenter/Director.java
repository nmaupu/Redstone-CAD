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

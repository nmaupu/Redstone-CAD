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
import net.fossar.view.scenery.grid2d.FrontViewport;
import net.fossar.view.scenery.grid2d.TopViewport;
import net.fossar.view.scenery.grid2d.ViewportStack;
import net.fossar.view.toolbar.MainToolBar;

import java.util.ArrayList;
import java.util.List;

public abstract class Director {
	public static final DataGridController dataGridController = new DataGridController();
	protected static final GridViewEventController gridViewEventController = new GridViewEventController();
	protected static final ToolBarActionController toolBarActionController = new ToolBarActionController();
	protected static final MainToolBar mainToolBar = new MainToolBar(toolBarActionController);
	protected static final ViewportStack topViewportStack = new ViewportStack();
    protected static final ViewportStack frontViewportStack = new ViewportStack();
	protected static IMainFrame mainFrame = new MainFrame(mainToolBar, topViewportStack, frontViewportStack);
    protected static List<ViewportStack> viewStacks = new ArrayList<ViewportStack>();
	
	
	public static void doInit() {
        /* Top viewports creation */
        for(int i=0; i<dataGridController.getDataGrid().getLayers(); i++) {
            topViewportStack.addViewport(new TopViewport(dataGridController.getDataGrid().getRows(),
                    dataGridController.getDataGrid().getCols(), i));
        }

        /* Front viewports creation */
        for(int i=0; i<dataGridController.getDataGrid().getCols(); i++) {
            frontViewportStack.addViewport(new FrontViewport(dataGridController.getDataGrid().getRows(),
                    dataGridController.getDataGrid().getLayers(), i));
        }

        viewStacks.add(topViewportStack);
        viewStacks.add(frontViewportStack);

		// Subscriptions to views
		topViewportStack.getGridViewEventManager().addPresenterListener(gridViewEventController);
        frontViewportStack.getGridViewEventManager().addPresenterListener(gridViewEventController);
		
		// Show up application
		mainFrame.setVisible(true);
	}
	
	public static void main(String args[]) {
		Director.doInit();
	}
}

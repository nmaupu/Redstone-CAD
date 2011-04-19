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
import net.fossar.model.core.block.DataBlock;
import net.fossar.presenter.event.IPresenterListener;
import net.fossar.presenter.event.PresenterEvent;
import net.fossar.view.scenery.grid2d.ViewportStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataGridController implements IController, IPresenterListener {
    private Logger logger = LoggerFactory.getLogger(DataGridController.class);
	private static int DEFAULT_GRID_ROWS=20;
	private static int DEFAULT_GRID_COLS=30;
	private static int DEFAULT_GRID_LAYERS=10;
	private DataGrid dataGrid = new DataGrid(DEFAULT_GRID_ROWS, DEFAULT_GRID_COLS, DEFAULT_GRID_LAYERS, this);
	
	public DataGridController() {
	}

	public IDataGrid getDataGrid() {
		return dataGrid;
	}

    @Override
    public void presenterEventFired(PresenterEvent e) {
        // If here, a block is modified
        DataBlock blk = (DataBlock)e.getSource();
        logger.debug("Firing Event : a block is modified - "+blk);

        // Send an event to repaint corresponding views
        for(ViewportStack v : Director.viewStacks) {
            v.repaintBlock(blk);
        }
    }
}

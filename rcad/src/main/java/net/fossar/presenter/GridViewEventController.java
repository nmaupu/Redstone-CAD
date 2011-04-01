/*
 * <one line to give the program's name and a brief idea of what it does.>
 *     Copyright (C) 2011.  <name of author>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.fossar.presenter;

import net.fossar.model.core.IDataGrid;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.presenter.event.GridViewEvent;
import net.fossar.presenter.event.GridViewEventListener;
import net.fossar.view.IDataGridDisplayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class catching current view events
 * @author nmaupu
 */
public class GridViewEventController implements IController, GridViewEventListener {
	private Logger logger = LoggerFactory.getLogger(GridViewEvent.class);

	@Override
	public void gridViewEventFired(GridViewEvent e) {
		/*
		 * Receiving an event from a grid view
		 * Updating model with current selected block
		 */
		logger.debug("Receiving event - src=" + e.getSource());
		
		int r = e.getRow();
		int c = e.getCol();
		int l = e.getLay();
		IDataGrid dg = Director.dataGridController.getDataGrid();
		
		if (r >= 0 && r <= dg.getRows()-1 && c >= 0 && c <= dg.getCols()-1) {
			AbstractBlock newBlock = Director.toolBarActionController.createInstanceOfCurrentSelectedBlock();
			dg.setBlock(newBlock, r, c, l);
		}
		
		// Fire event to redraw this label and its adjacent blocks
		IDataGridDisplayer displayer = (IDataGridDisplayer)e.getSource();
		
		displayer.drawBlock(dg.getDataBlock(r, c, l));
		if (r>0)
			displayer.drawBlock(dg.getDataBlock(r-1, c, l));
		if (r<dg.getRows() - 1)
			displayer.drawBlock(dg.getDataBlock(r+1, c, l));
		if (c>0)
			displayer.drawBlock(dg.getDataBlock(r, c-1, l));
		if (c<dg.getCols() - 1)
			displayer.drawBlock(dg.getDataBlock(r, c+1, l));
	}
}

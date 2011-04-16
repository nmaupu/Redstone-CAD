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

import net.fossar.model.core.IDataGrid;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.presenter.event.EventType;
import net.fossar.presenter.event.GridViewEvent;
import net.fossar.presenter.event.GridViewEventListener;
import net.fossar.presenter.event.PresenterEvent;
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
	public void presenterEventFired(PresenterEvent e) {
		/*
		 * Receiving an event from a grid2d view
		 * Updating model with current selected block
		 */
		logger.debug("Receiving event - src=" + e.getSource());

        GridViewEvent event = (GridViewEvent)e;

		int r = event.getRow();
		int c = event.getCol();
		int l = event.getLay();
		IDataGrid dg = Director.dataGridController.getDataGrid();
		
		if (r >= 0 && r < dg.getRows() && c >= 0 && c < dg.getCols() && l >= 0 && l < dg.getLayers()) {
            if(event.getEventType() == EventType.INSERT) {
                AbstractBlock newBlock = Director.toolBarActionController.createInstanceOfCurrentSelectedBlock();
                logger.info("Inserting block type={} at (r,c,l)=({},{},{})", new Object[] {newBlock, r, c, l});
                dg.setBlock(newBlock, r, c, l);
            }

            // Fire event to redraw this label and its adjacent blocks
            IDataGridDisplayer displayer = (IDataGridDisplayer)event.getSource();

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
}

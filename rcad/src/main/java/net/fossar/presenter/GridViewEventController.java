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

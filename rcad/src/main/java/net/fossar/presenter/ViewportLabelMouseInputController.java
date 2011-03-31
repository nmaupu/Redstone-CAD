package net.fossar.presenter;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

import javax.swing.event.MouseInputAdapter;

import net.fossar.model.Direction;
import net.fossar.model.IDataGrid;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.DataBlock;
import net.fossar.model.core.block.PassiveBlock;
import net.fossar.view.Viewport;
import net.fossar.view.ViewportLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewportLabelMouseInputController extends MouseInputAdapter implements IViewportLabelMouseInputController {
	private Logger logger = LoggerFactory.getLogger(ViewportLabelMouseInputController.class);
	
	@Override
	public void mousePressed(MouseEvent e) {
		Object src = e.getSource();
		Point p = e.getPoint();
		
		logger.debug("Mouse event received - src=" + src);
				
		if(src instanceof Viewport)
			updateViewportLabelState(p.getX(), p.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		updateViewportLabelState(p.getX(), p.getY());
	}
	
	
	private void updateViewportLabelState(double dx, double dy) {
		List<Viewport> viewports = Director.viewportStack.getViewports();
		int currentLayer = Director.viewportStack.getCurrentLayer();
		IDataGrid dg = Director.dataGridController.getDataGrid();
		
		Dimension dim = viewports.get(currentLayer).getViewportLabel(0, 0).getSize();
		int labelW = (int)dim.getWidth();
		int labelH = (int)dim.getHeight();
		int x = (int)dx;
		int y = (int)dy;
		
		int r = (int)(y / labelH);
		int c = (int)(x / labelW);
		
		if (r >= 0 && r <= dg.getRows()-1 && c >= 0 && c <= dg.getCols()-1) {
			ViewportLabel label = viewports.get(currentLayer).getViewportLabel(r, c);
			AbstractBlock newBlock = Director.toolBarActionController.createInstanceOfCurrentSelectedBlock();
			
			dg.setBlock(newBlock,r, c, currentLayer);
			Map<Direction,DataBlock> adj = dg.getAdjacentStatesDirection(dg.getDataBlock(r, c, currentLayer));
			// Normally, never change when already set ...
			label.setDataBlock(dg.getDataBlock(r, c, currentLayer));
			label.setAdjacentBlocks(adj);
			
			// Reset all passive blocks
			/*resetPassiveBlocks(adj);
			Director.dataGridController.getDataGrid().tick();
			Director.dataGridController.getDataGrid().doUpdate();
			*/
			
			// Repaint to display properly
			repaintLabelAndAdjacents(label);
		}
	}
	
	private void resetPassiveBlocks(Map<Direction, DataBlock> blocks) {
		for(Map.Entry<Direction, DataBlock> entry : blocks.entrySet()) {
			AbstractBlock b = entry.getValue().getBlock();
			if(b instanceof PassiveBlock) {
				((PassiveBlock)b).resetBlockPower();
			}
		}
	}
	
	
	private void repaintLabelAndAdjacents(ViewportLabel label) {
		IDataGrid dg = Director.dataGridController.getDataGrid();
		List<Viewport> viewports = Director.viewportStack.getViewports();
		
		if (dg == null || label == null)
			return;
		
		int r = label.getRow();
		int c = label.getCol();
		int l = label.getLay();
		
				
		label.repaint();
		if (r>0)
			viewports.get(l).getViewportLabel(r-1, c).repaint();
		if (r<dg.getRows() - 1)
			viewports.get(l).getViewportLabel(r+1, c).repaint();
		if (c>0)
			viewports.get(l).getViewportLabel(r, c-1).repaint();
		if (c<dg.getCols() - 1)
			viewports.get(l).getViewportLabel(r, c+1).repaint();
	}
}

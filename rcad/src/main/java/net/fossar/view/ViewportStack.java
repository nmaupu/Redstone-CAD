package net.fossar.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import net.fossar.model.DataGrid;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.presenter.Director;
import net.fossar.presenter.Presenter;
import net.fossar.view.action.BlockChanger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewportStack extends JPanel implements View, MouseInputListener {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(ViewportStack.class);
	
	private Presenter presenter;
	
	private List<Viewport> viewports = null;
	private DataGrid dataGrid = null;
	private CardLayout layout = new CardLayout();
	private int currentLayer = 0;
	
	public ViewportStack(Presenter presenter) {
		super();
		this.presenter = presenter;
		
		dataGrid = (DataGrid) presenter.getModel(Director.DATAGRID);
		
		super.setLayout(layout);
		
		viewports = new ArrayList<Viewport>();
		
		int layers = dataGrid.getLayers();
		for (int i=0; i<layers; i++) {
			Viewport v = new Viewport(presenter, i);
			viewports.add(v);
			super.add(v, String.valueOf(i));
			v.addMouseListener(this);
			v.addMouseMotionListener(this);
		}
	}
	
	public void setLayer(int idx) {
		layout.show(this, String.valueOf(idx));
		currentLayer = idx;
	}
	
	public void nextLayer() {
		layout.next(this);
		currentLayer++;
	}
	
	public void previousLayer() {
		layout.previous(this);
		currentLayer--;
	}
	
	public void firstLayer() {
		layout.first(this);
		currentLayer=0;
	}
	
	public void lastLayer() {
		layout.last(this);
		currentLayer=dataGrid.getLayers()-1;
	}

	public List<Viewport> getViewports() {
		return viewports;
	}

	public DataGrid getDataGrid() {
		return dataGrid;
	}
	
	private void updateViewportLabelState(double dx, double dy) {
		Dimension dim = viewports.get(currentLayer).getViewportLabel(0, 0).getSize();
		int labelW = (int)dim.getWidth();
		int labelH = (int)dim.getHeight();
		int x = (int)dx;
		int y = (int)dy;
		
		int r = (int)(y / labelH);
		int c = (int)(x / labelW);
		
		if (r >= 0 && r <= dataGrid.getRows()-1 && c >= 0 && c <= dataGrid.getCols()-1) {
			ViewportLabel label = viewports.get(currentLayer).getViewportLabel(r, c);
			AbstractBlock newBlock = BlockChanger.getBlock();
			dataGrid.getBlock(r, c, currentLayer).setBlock(newBlock);
			// TODO Repaint by strategy ?
			label.repaint();
			this.repaintAdjacents(label);
		}
	}
	
	/*
	 * TODO Use strategy to repaint a component
	 */
	public void repaintAdjacents(ViewportLabel label) {
		if (dataGrid == null)
			return;
		
		int r = label.getDataBlock().getRow();
		int c = label.getDataBlock().getCol();
		int l = label.getDataBlock().getLay();
		
		if (r>0)
			viewports.get(l).getViewportLabel(r-1, c).repaint();
		
		if (r<dataGrid.getRows() - 1)
			viewports.get(l).getViewportLabel(r+1, c).repaint();
		
		if (c>0)
			viewports.get(l).getViewportLabel(r, c-1).repaint();
		
		if (c<dataGrid.getCols() - 1)
			viewports.get(l).getViewportLabel(r, c+1).repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Object src = e.getSource();
		Point p = e.getPoint();
		
		logger.debug("src="+src);
		
		if(src instanceof Viewport)
			updateViewportLabelState(p.getX(), p.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		updateViewportLabelState(p.getX(), p.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {}
}

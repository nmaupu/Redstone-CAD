package net.fossar.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import net.fossar.model.core.block.DataBlock;
import net.fossar.presenter.event.GridViewEvent;
import net.fossar.presenter.event.GridViewEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewportStack extends JPanel implements IDataGridDisplayer, MouseInputListener {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(ViewportStack.class);
		
	private List<Viewport> viewports = null;
	private CardLayout layout = new CardLayout();
	private int currentLayer = 0;
	private int rows, cols, layers;
	private ArrayList<GridViewEventListener> presenterListeners = new ArrayList<GridViewEventListener>();
	
	public ViewportStack(int rows, int cols, int layers) {
		super();
		super.setLayout(layout);
		this.rows = rows;
		this.cols = cols;
		this.layers = layers;
		
		viewports = new ArrayList<Viewport>();
		for (int i=0; i<layers; i++) {
			Viewport v = new Viewport(rows, cols, i);
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
		currentLayer=layers-1;
	}
	
	public int getCurrentLayer() {
		return currentLayer;
	}

	public List<Viewport> getViewports() {
		return viewports;
	}
	
	public void addGridViewEventListener(GridViewEventListener listener) {
		presenterListeners.add(listener);
	}
	
	public void removeGridViewEventListener(GridViewEventListener listener) {
		presenterListeners.remove(listener);
	}
	
	public void notifyPresenterListeners(GridViewEvent e) {
		for(GridViewEventListener l : presenterListeners)
			l.gridViewEventFired(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int r = getRowFromPoint(e.getPoint());
		int c = getColFromPoint(e.getPoint());
		
		notifyPresenterListeners(new GridViewEvent(e.getSource(), r, c, currentLayer));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int r = getRowFromPoint(e.getPoint());
		int c = getColFromPoint(e.getPoint());
		
		notifyPresenterListeners(new GridViewEvent(e.getSource(), r, c, currentLayer));
	}

	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	private int getRowFromPoint(Point p) {
		Dimension dim = viewports.get(currentLayer).getViewportLabel(0,0).getSize();
		int labelH = (int)dim.getHeight();
		int y = (int)p.getY();
		int r = (int)(y / labelH);
		
		return r;
	}
	
	private int getColFromPoint(Point p) {
		Dimension dim = viewports.get(currentLayer).getViewportLabel(0,0).getSize();
		int labelW = (int)dim.getWidth();
		int x = (int)p.getX();
		int c = (int)(x / labelW);
		
		return c;
	}

	@Override
	public void drawBlock(final DataBlock dataBlock) {
		// Draw block in specified location (given by dataBlock)
		ViewportLabel label = viewports.get(dataBlock.getLay()).getViewportLabel(dataBlock.getRow(), dataBlock.getCol());
		label.setDataBlock(dataBlock);
		label.repaint();
	}
}

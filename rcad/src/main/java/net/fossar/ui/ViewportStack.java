package net.fossar.ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import net.fossar.core.DataGrid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewportStack extends JPanel implements MouseInputListener {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(ViewportStack.class);
	
	public static int DEFAULT_ROWS=20;
	public static int DEFAULT_COLS=30;
	public static int DEFAULT_LAYERS=10;
	
	private List<Viewport> viewports = null;
	private DataGrid grid = new DataGrid(DEFAULT_ROWS, DEFAULT_COLS, DEFAULT_LAYERS);
	private CardLayout layout = new CardLayout();
	private int currentLayer = 0;
	
	private MainFrame parent;
	
	public ViewportStack(MainFrame parent) {
		super();
		super.setLayout(layout);
		this.parent = parent;
		
		viewports = new ArrayList<Viewport>();
		
		int layers = grid.getLayers();
		for (int i=0; i<layers; i++) {
			Viewport v = new Viewport(grid, i);
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
		currentLayer=grid.getLayers()-1;
	}

	public List<Viewport> getViewports() {
		return viewports;
	}

	public DataGrid getGrid() {
		return grid;
	}
	
	private void updateViewportLabelState(double dx, double dy) {
		Dimension dim = grid.getLabels()[0][0][currentLayer].getSize();
		int labelW = (int)dim.getWidth();
		int labelH = (int)dim.getHeight();
		int x = (int)dx;
		int y = (int)dy;
		
		int r = (int)(y / labelH);
		int c = (int)(x / labelW);
		
		if (r >= 0 && r <= grid.getRows()-1 && c >= 0 && c <= grid.getCols()-1) {
			ViewportLabel label = grid.getLabels()[r][c][currentLayer];
			label.setState(parent.mainToolBar.getActionPerformer().getBlockType());
			label.repaint();
			label.repaintAdjacents();
		}
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

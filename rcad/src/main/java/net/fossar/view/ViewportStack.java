package net.fossar.view;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.fossar.presenter.IViewportLabelMouseInputController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewportStack extends JPanel implements View {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(ViewportStack.class);
		
	private List<Viewport> viewports = null;
	private CardLayout layout = new CardLayout();
	private int currentLayer = 0;
	private int rows, cols, layers;
	private IViewportLabelMouseInputController mouseInputController;
	
	public ViewportStack(int rows, int cols, int layers, IViewportLabelMouseInputController mouseInputController) {
		super();
		super.setLayout(layout);
		this.rows = rows;
		this.cols = cols;
		this.layers = layers;
		this.mouseInputController = mouseInputController;
		
		viewports = new ArrayList<Viewport>();
		for (int i=0; i<layers; i++) {
			Viewport v = new Viewport(rows, cols, i);
			viewports.add(v);
			super.add(v, String.valueOf(i));
			v.addMouseListener(mouseInputController);
			v.addMouseMotionListener(mouseInputController);
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
}

package net.fossar.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import net.fossar.core.DataGrid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Viewport extends JPanel {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(Viewport.class);
	
	private DataGrid grid;
	private ViewportLabel[][] labels = null;
	private int layerIdx = 0;
	
	public Viewport(DataGrid grid, int layerIdx) {
		this.grid = grid;
		this.layerIdx = layerIdx;
		
		super.setLayout(new GridLayout(grid.getRows(), grid.getCols()));
		initLabels();
	}
	
	private void initLabels() {
		if (labels == null)
			labels = new ViewportLabel[grid.getRows()][grid.getCols()];
		
		for(int i=0; i<grid.getRows(); i++) {
			for(int j=0; j<grid.getCols(); j++) {
				labels[i][j] = grid.getLabels()[i][j][layerIdx];
				super.add(labels[i][j]);
			}
		}
	}
	
	/*
	public void setDisplayedLayer(int layer) {
		super.removeAll();
		
		if (labels == null)
			labels = new ViewportLabel[grid.getRows()][grid.getCols()];
		
		for(int i=0; i<grid.getRows(); i++) {
			for(int j=0; j<grid.getCols(); j++) {
				labels[i][j] = grid.getLabels()[i][j][layer];
				super.add(labels[i][j]);
			}
		}
	}
	*/
	
	public int getLayerId() {
		return layerIdx;
	}
}

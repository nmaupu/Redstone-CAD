package net.fossar.ui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.fossar.core.DataGrid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Viewport extends JPanel {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(Viewport.class);
	private DataGrid grid;
	private ViewportLabel[][] labels;
	private int displayedLayer;
	
	public Viewport(DataGrid grid) {
		this.grid = grid;
		this.displayedLayer = 0;
		
		super.setLayout(new GridLayout(grid.getRows(), grid.getCols()));
		super.setPreferredSize(new Dimension(
									grid.getCols()*ViewportLabel.LABEL_WIDTH, 
									grid.getRows()*ViewportLabel.LABEL_WIDTH));
		super.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		setDisplayedLayer(displayedLayer);
	}
	
	public void repaintViewport() {
		for(int i=0; i<grid.getRows(); i++)
			for(int j=0; j<grid.getCols(); j++)
				grid.getLabels()[i][j][displayedLayer].repaint();
	}
	
	public int getDisplayedLayer() {
		return displayedLayer;
	}
	
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
}

package net.fossar.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import net.fossar.model.Direction;
import net.fossar.presenter.Director;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Viewport extends JPanel implements IViewport {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(Viewport.class);
	
	private ViewportLabel[][] labels = null;
	private int rows;
	private int cols;
	private final int layerIdx;
	
	public Viewport(int rows, int cols, int layerIdx) {
		this.layerIdx = layerIdx;
		this.rows = rows;
		this.cols = cols;
		
		super.setLayout(new GridLayout(rows, cols));
		initLabels();
	}
	
	private void initLabels() {
		if (labels == null)
			labels = new ViewportLabel[rows][cols];
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				labels[i][j] = new ViewportLabel(this, Director.dataGridController.getDataGrid().getDataBlock(i,j,layerIdx));
				super.add(labels[i][j]);
			}
		}
	}
	
	public ViewportLabel getViewportLabel(int r, int c) {
		return labels[r][c];
	}
	
	@Override
	public int getLayerIdx() {
		return layerIdx;
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getCols() {
		return cols;
	}	
}

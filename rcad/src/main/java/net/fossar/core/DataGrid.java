package net.fossar.core;

import net.fossar.ui.ViewportLabel;

public class DataGrid {
	private int rows;
	private int cols;
	private int layers;
	private ViewportLabel labels[][][];
	
	public DataGrid(int rows, int cols, int layers) {
		this.rows   = rows;
		this.cols   = cols;
		this.layers = layers;
		labels = new ViewportLabel[rows][cols][layers];
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				for(int k=0; k<layers; k++) {
					labels[i][j][k] = new ViewportLabel(i, j, k, this);
				}
			}
		}
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public int getLayers() {
		return layers;
	}

	public ViewportLabel[][][] getLabels() {
		return labels;
	}
	
	public Block getState(int r, int c, int l) {
		return labels[r][c][l].getState();
	}

	/*
	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public void setLayers(int layers) {
		this.layers = layers;
	}
	*/
}

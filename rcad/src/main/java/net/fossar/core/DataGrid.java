package net.fossar.core;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

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
	
	/**
	 * Indicates if given coordinates data has an adjacent data corresponding to a given Block
	 * @param grid
	 * @param x
	 * @param y
	 * @param z
	 * @param state
	 * @return True if there is an adjacent state corresponding to a given Block, false otherwise
	 */
	public boolean hasAdjacentState(ViewportLabel label, EnumSet<Block> states) {
		Map<Direction, Block> res = getAdjacentStatesDirection(label, states);
		return res != null && res.size() > 0;
	}
	
	/**
	 * Get all adjacent states for given blocks
	 * @param r
	 * @param c
	 * @param l
	 * @param states
	 * @return a list of adjacents states and their corresponding directions
	 */
	public Map<Direction,Block> getAdjacentStatesDirection(ViewportLabel label, EnumSet<Block> states) {
		int r = label.getRow();
		int c = label.getCol();
		int l = label.getLay();
		
		Map<Direction,Block> res = new HashMap<Direction, Block>();
		
		if (r < getRows()-1 && states.contains(getState(r+1, c, l)))
			res.put(Direction.DOWN, getState(r+1, c, l));
		if (r > 0 && states.contains(getState(r-1, c, l)))
			res.put(Direction.UP, getState(r-1, c, l));
		if (c < getCols()-1 && states.contains(getState(r, c+1, l)))
			res.put(Direction.RIGHT, getState(r, c+1, l));
		if (c > 0 && states.contains(getState(r, c-1, l)))
			res.put(Direction.LEFT, getState(r, c-1, l));
		if (l < getLayers()-1 && states.contains(getState(r, c, l+1)))
			res.put(Direction.ABOVE, getState(r, c, l+1));
		if (l > 0 && states.contains(getState(r, c, l-1)))
			res.put(Direction.BELOW, getState(r, c, l-1));
		
		return res.size() == 0 ? null : res;
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

package net.fossar.core.grid;

import java.util.HashMap;
import java.util.Map;

import net.fossar.core.Direction;
import net.fossar.core.block.AbstractBlock;
import net.fossar.core.block.Air;

public class DataGrid {
	private int rows;
	private int cols;
	private int layers;
	private AbstractBlock blocks[][][];

	public DataGrid(int rows, int cols, int layers) {
		this.rows = rows;
		this.cols = cols;
		this.layers = layers;
		blocks = new AbstractBlock[rows][cols][layers];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < layers; k++) {
					blocks[i][j][k] = Air.INSTANCE;
				}
			}
		}
	}

	public void addBlock(AbstractBlock block, int row, int col, int layer) {
		block.setCol(col);
		block.setRow(row);
		block.setLayer(layer);
		blocks[row][col][layer] = block;
	}

	public AbstractBlock getBlock(int row, int col, int layer) {
		return blocks[row][col][layer];
	}

	/**
	 * Get all adjacent states for given blocks
	 * 
	 * @param r
	 * @param c
	 * @param l
	 * @param states
	 * @return a list of adjacents states and their corresponding directions
	 */
	public Map<Direction, AbstractBlock> getAdjacentStatesDirection(AbstractBlock label) {
		int r = label.getRow();
		int c = label.getCol();
		int l = label.getLayer();

		Map<Direction, AbstractBlock> res = new HashMap<Direction, AbstractBlock>();

		if (r < getRows() - 1)
			res.put(Direction.DOWN, getBlock(r + 1, c, l));
		if (r > 0)
			res.put(Direction.UP, getBlock(r - 1, c, l));
		if (c < getCols() - 1)
			res.put(Direction.RIGHT, getBlock(r, c + 1, l));
		if (c > 0)
			res.put(Direction.LEFT, getBlock(r, c - 1, l));
		if (l < getLayers() - 1)
			res.put(Direction.ABOVE, getBlock(r, c, l + 1));
		if (l > 0)
			res.put(Direction.BELOW, getBlock(r, c, l - 1));

		return res;
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

}

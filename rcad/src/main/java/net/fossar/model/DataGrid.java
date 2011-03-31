package net.fossar.model;

import java.util.HashMap;
import java.util.Map;

import net.fossar.model.core.block.Air;
import net.fossar.model.core.block.DataBlock;

public class DataGrid implements Model {
	private int rows;
	private int cols;
	private int layers;
	private DataBlock blocks[][][];
	
	public DataGrid(int rows, int cols, int layers) {
		this.rows   = rows;
		this.cols   = cols;
		this.layers = layers;
		
		// All blocks is default to Air
		blocks = new DataBlock[rows][cols][layers];
		for(int i=0; i<rows; i++)
			for(int j=0; j<cols; j++)
				for(int k=0; k<layers; k++)
					blocks[i][j][k] = new DataBlock(i, j, k, Air.INSTANCE);
	}
	
	/**
	 * Get all adjacent blocks for a given block
	 * @param block
	 * @return A map indicating adjacent blocks
	 */
	public Map<Direction, DataBlock> getAdjacentStatesDirection(DataBlock block) {
		int r = block.getRow();
		int c = block.getCol();
		int l = block.getLay();
		
		Map<Direction, DataBlock> res = new HashMap<Direction, DataBlock>();
		
		if (r < getRows()-1)
			res.put(Direction.DOWN, getBlock(r+1, c, l));
		if (r > 0)
			res.put(Direction.UP, getBlock(r-1, c, l));
		if (c < getCols()-1)
			res.put(Direction.RIGHT, getBlock(r, c+1, l));
		if (c > 0)
			res.put(Direction.LEFT, getBlock(r, c-1, l));
		if (l < getLayers()-1)
			res.put(Direction.ABOVE, getBlock(r, c, l+1));
		if (l > 0)
			res.put(Direction.BELOW, getBlock(r, c, l-1));
		
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

	public DataBlock[][][] getBlocks() {
		return blocks;
	}
	
	public DataBlock getBlock(int r, int c, int l) {
		return blocks[r][c][l];
	}
}

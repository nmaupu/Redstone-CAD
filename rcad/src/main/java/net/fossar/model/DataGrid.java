package net.fossar.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.Air;
import net.fossar.model.core.block.BlockType;
import net.fossar.model.core.block.DataBlock;

public class DataGrid implements IDataGrid {
	private int rows;
	private int cols;
	private int layers;
	private DataBlock dataBlocks[][][];
	
	public DataGrid(int rows, int cols, int layers) {
		this.rows   = rows;
		this.cols   = cols;
		this.layers = layers;
		
		// All blocks is default to Air
		dataBlocks = new DataBlock[rows][cols][layers];
		for(int i=0; i<rows; i++)
			for(int j=0; j<cols; j++)
				for(int k=0; k<layers; k++)
					dataBlocks[i][j][k] = new DataBlock(i, j, k, new Air());
	}
	
	/**
	 * Get all adjacent blocks for a given block
	 * @param block
	 * @return A map indicating adjacent blocks
	 */
	@Override
	public Map<Direction, DataBlock> getAdjacentStatesDirection(DataBlock block) {
		int r = block.getRow();
		int c = block.getCol();
		int l = block.getLay();
		
		Map<Direction, DataBlock> res = new HashMap<Direction, DataBlock>();
		
		if (r < getRows()-1)
			res.put(Direction.DOWN, getDataBlock(r+1, c, l));
		if (r > 0)
			res.put(Direction.UP, getDataBlock(r-1, c, l));
		if (c < getCols()-1)
			res.put(Direction.RIGHT, getDataBlock(r, c+1, l));
		if (c > 0)
			res.put(Direction.LEFT, getDataBlock(r, c-1, l));
		if (l < getLayers()-1)
			res.put(Direction.ABOVE, getDataBlock(r, c, l+1));
		if (l > 0)
			res.put(Direction.BELOW, getDataBlock(r, c, l-1));
		
		return res.size() == 0 ? null : res;
	}
	
	@Override
	public Map<Direction, BlockType> getAdjacentTypeDirection(DataBlock block) {
		Map<Direction, DataBlock> list = getAdjacentStatesDirection(block);
		Map<Direction, BlockType> res = new HashMap<Direction, BlockType>();
		
		for(Entry<Direction, DataBlock> entry : list.entrySet()) {
			res.put(entry.getKey(), BlockType.getBlockType(entry.getValue().getBlock()));
		}
		
		return res;
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getCols() {
		return cols;
	}

	@Override
	public int getLayers() {
		return layers;
	}

	public DataBlock[][][] getDataBlocks() {
		return dataBlocks;
	}
	
	@Override
	public DataBlock getDataBlock(int r, int c, int l) {
		return dataBlocks[r][c][l];
	}

	@Override
	public void setBlock(AbstractBlock block, int r, int c, int l) {
		getDataBlock(r, c, l).setBlock(block);
	}
}

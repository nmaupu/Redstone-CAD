package net.fossar.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.fossar.model.core.AdjacentBlocks;
import net.fossar.model.core.block.*;
import net.fossar.model.core.clock.Clock;

public class DataGrid implements Model {
	private int rows;
	private int cols;
	private int layers;
	private DataBlock blocks[][][];

    private ArrayList<DataBlock> activeBlocks = new ArrayList<DataBlock>();
    private ArrayList<DataBlock> inactiveBlocks = new ArrayList<DataBlock>();


	
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
		
		return res;
	}

    public void add(DataBlock datablock) {

        blocks[datablock.getRow()][datablock.getCol()][datablock.getLay()] = datablock;
        AbstractBlock block = datablock.getBlock();
        if (block instanceof ActiveBlock) {
            activeBlocks.add(datablock);
        } else if (block instanceof Block) {
            inactiveBlocks.add(datablock);
        }
    }

    public void doUpdate() {
        AdjacentBlocks adjacentBlocks = new AdjacentBlocks(this);
        for (DataBlock block : activeBlocks) {
            block.getBlock().doUpdate(adjacentBlocks.getAdjacents(block));
        }

        for (DataBlock block : inactiveBlocks) {
            block.getBlock().doUpdate(adjacentBlocks.getAdjacents(block));
        }

    }

    public void tick(){
        Clock.next();
		Clock.next();
    }

    public void addBlock(AbstractBlock block, int row, int col, int layer) {
        add(new DataBlock(row, col, layer, block));
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

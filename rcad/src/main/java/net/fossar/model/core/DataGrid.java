/*
 * <one line to give the program's name and a brief idea of what it does.>
 *     Copyright (C) 2011.  <name of author>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.fossar.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.fossar.model.Direction;
import net.fossar.model.core.block.*;
import net.fossar.model.core.clock.Clock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataGrid implements IDataGrid {
	private Logger logger = LoggerFactory.getLogger(DataGrid.class);
	private int rows;
	private int cols;
	private int layers;
	private DataBlock dataBlocks[][][];	
    
    private ArrayList<DataBlock> activeBlocks = new ArrayList<DataBlock>();
    private ArrayList<DataBlock> wires = new ArrayList<DataBlock>();
    private ArrayList<DataBlock> inactiveBlocks = new ArrayList<DataBlock>();

    
	public DataGrid(int rows, int cols, int layers) {
		this.rows   = rows;
		this.cols   = cols;
		this.layers = layers;
		
		// All blocks is default to Air
		dataBlocks = new DataBlock[rows][cols][layers];
		for(int i=0; i<rows; i++)
			for(int j=0; j<cols; j++)
				for(int k=0; k<layers; k++)
					dataBlocks[i][j][k] = new DataBlock(i, j, k, Air.INSTANCE);
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
			res.put(Direction.DOWN, getDataBlock(r + 1, c, l));
		if (r > 0)
			res.put(Direction.UP, getDataBlock(r - 1, c, l));
		if (c < getCols()-1)
			res.put(Direction.RIGHT, getDataBlock(r, c + 1, l));
		if (c > 0)
			res.put(Direction.LEFT, getDataBlock(r, c - 1, l));
		if (l < getLayers()-1)
			res.put(Direction.ABOVE, getDataBlock(r, c, l + 1));
		if (l > 0)
			res.put(Direction.BELOW, getDataBlock(r, c, l - 1));
		
		return res;
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
        DataBlock dataBlock = getDataBlock(r, c, l);
        activeBlocks.remove(dataBlock);
        inactiveBlocks.remove(dataBlock);
        wires.remove(dataBlock);

        dataBlock.setBlock(block);
        updateDataBlockLists(dataBlock);


	}
    
    public void add(DataBlock dataBlock) {
        dataBlocks[dataBlock.getRow()][dataBlock.getCol()][dataBlock.getLay()] = dataBlock;
        updateDataBlockLists(dataBlock);
    }
    
    private void updateDataBlockLists(DataBlock dataBlock) {
    	AbstractBlock block = dataBlock.getBlock();
    	if (block instanceof ActiveBlock) {
            activeBlocks.add(dataBlock);
        } else if (block instanceof Block) {
            inactiveBlocks.add(dataBlock);
        }else if(block instanceof Wire) {
            wires.add(dataBlock);
        }
        for(DataBlock wire: wires){
            ((Wire) wire.getBlock()).updateDirection(new AdjacentBlocks(this, wire));
        }
    }

    @Override
    public void doUpdate() {
    	logger.debug("doUpdate");
    	
        for (DataBlock wire : wires) {
            wire.getBlock().setInput(AbstractBlock.POWER_OFF);
        }
        for (DataBlock block : activeBlocks) {
            block.getBlock().doUpdate(new AdjacentBlocks(this, block));
        }

        for (DataBlock block : inactiveBlocks) {
            block.getBlock().doUpdate(new AdjacentBlocks(this, block));
        }

    }

    @Override
    public void tick(){
        Clock.next();
		Clock.next();
    }

    public void addBlock(AbstractBlock block, int row, int col, int layer) {
        add(new DataBlock(row, col, layer, block));
	}
}

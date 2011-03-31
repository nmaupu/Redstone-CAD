package net.fossar.core.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.fossar.core.AdjacentBlocks;
import net.fossar.core.block.ActiveBlock;

import net.fossar.model.Direction;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.Air;
import net.fossar.model.core.block.Block;
import net.fossar.model.core.clock.Clock;

public class DataGrid {
	private int rows;
	private int cols;
	private int layers;
	private AbstractBlock blocks[][][];

    private List<AbstractBlock> activeBlocks = new ArrayList<AbstractBlock>();
    private List<AbstractBlock> inactiveBlocks = new ArrayList<AbstractBlock>();


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

        if (block instanceof ActiveBlock) {
            activeBlocks.add(block);
        } else if (block instanceof Block) {
            inactiveBlocks.add(block);

        }
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
	public AdjacentBlocks getAdjacentStatesDirection(AbstractBlock label) {
        
		int r = label.getRow();
		int c = label.getCol();
		int l = label.getLayer();

        AdjacentBlocks res = new AdjacentBlocks(this);

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

    public void doUpdate() {
        for (AbstractBlock block : activeBlocks) {
            block.doUpdate(getAdjacentStatesDirection(block));
        }

        for (AbstractBlock block : inactiveBlocks) {
            block.doUpdate(getAdjacentStatesDirection(block));
        }

    }

    public void tick(){
        Clock.next();
		Clock.next();
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

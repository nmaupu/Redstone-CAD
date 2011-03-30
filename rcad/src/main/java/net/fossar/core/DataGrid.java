package net.fossar.core;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	public boolean hasAdjacentState(ViewportLabel label, EnumSet<BlockType> states) {
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
	public Map<Direction,Block> getAdjacentStatesDirection(ViewportLabel label, EnumSet<BlockType> states) {
		int r = label.getRow();
		int c = label.getCol();
		int l = label.getLay();
		
		Map<Direction,Block> res = new HashMap<Direction, Block>();
		
		if (r < getRows()-1 && states.contains(getBlock(r+1, c, l).getType()))
			res.put(Direction.DOWN, getBlock(r+1, c, l));
		if (r > 0 && states.contains(getBlock(r-1, c, l).getType()))
			res.put(Direction.UP, getBlock(r-1, c, l));
		if (c < getCols()-1 && states.contains(getBlock(r, c+1, l).getType()))
			res.put(Direction.RIGHT, getBlock(r, c+1, l));
		if (c > 0 && states.contains(getBlock(r, c-1, l).getType()))
			res.put(Direction.LEFT, getBlock(r, c-1, l));
		if (l < getLayers()-1 && states.contains(getBlock(r, c, l+1).getType()))
			res.put(Direction.ABOVE, getBlock(r, c, l+1));
		if (l > 0 && states.contains(getBlock(r, c, l-1).getType()))
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

	public ViewportLabel[][][] getLabels() {
		return labels;
	}
	
	public Block getBlock(int r, int c, int l) {
		return labels[r][c][l].getBlock();
	}

	/**
	 * Update circuit logic status
	 */
	public synchronized void updateCircuitrySources() {
		for(int r=0; r<getRows(); r++) {
			for(int c=0; c<getCols(); c++) {
				for(int l=0; l<getLayers(); l++) {
					Block currentBlock = getBlock(r, c, l);
					ViewportLabel currentLabel = getLabels()[r][c][l];
					
					// if this is a torch, determining its powering state
					if (currentBlock.getType() == BlockType.TORCH) {
						// Default : power on torch
						currentBlock.poweron();
						
						/* 
						 * Analyzing power state of block on which torch is planted
						 * Torch is pointing to currentBlock.direction
						 */
						switch(currentBlock.getDirection()) {
						case UP:
							if(getBlock(r+1, c, l).getPower() > 0)
								currentBlock.poweroff();
							break;
						case DOWN:
							if(getBlock(r-1, c, l).getPower() > 0)
								currentBlock.poweroff();
							break;
						case LEFT:
							if(getBlock(r, c+1, l).getPower() > 0)
								currentBlock.poweroff();
							break;
						case RIGHT:
							if(getBlock(r, c-1, l).getPower() > 0)
								currentBlock.poweroff();
							break;
						case ABOVE:
							if(l>0 && getBlock(r, c, l-1).getPower() > 0)
								currentBlock.poweroff();
							break;
						}
					} else if (currentBlock.getType() == BlockType.WIRE) {
						// Reset wire
						currentBlock.poweroff();
						int leastPower = 0;
						
						Map<Direction, Block> dirs = getAdjacentStatesDirection(currentLabel, EnumSet.of(BlockType.TORCH, BlockType.WIRE));
						if(dirs != null && dirs.size() > 0) {
							// Walk through dirs and obtain information about power source of torches
							int maxPower = 0;
							for(Map.Entry<Direction, Block> entry : dirs.entrySet()) {
								leastPower = entry.getValue().getPower();
								if(leastPower > maxPower)
									maxPower = leastPower;
								if(leastPower == BlockConstant.POWER_SOURCE)
									break;
							}	
						} //if
						
						currentBlock.setPower( (leastPower > 0 ? leastPower - 1 : 0) );
						
					} else if (currentBlock.getType() == BlockType.BLOCK) {
						currentBlock.poweroff();
						// Get wire attach to it to determine block's power state
						Map<Direction, Block> dirs = getAdjacentStatesDirection(currentLabel, EnumSet.of(BlockType.WIRE));
						if(dirs != null && dirs.size() > 0) {
							for(Map.Entry<Direction, Block> entry : dirs.entrySet()) {
								Block b = entry.getValue();
								if(b.getType() == BlockType.WIRE && b.getPower() > 0) {
									currentBlock.poweron();
								}
							}
						}
					}
					
					
					currentLabel.repaint();
				} // for
			} // for
		} // for
	} // func
}

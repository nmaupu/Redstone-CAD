package net.fossar.model.core;

import java.util.HashMap;
import java.util.Map;

import net.fossar.model.DataGrid;
import net.fossar.model.Direction;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.DataBlock;

/**
 */
public class AdjacentBlocks extends HashMap<Direction, DataBlock> {
	protected DataGrid datagrid;

	public AdjacentBlocks(DataGrid datagrid) {
		this.datagrid = datagrid;
	}

	public AdjacentBlocks getAdjacents(DataBlock block) {
        Map<Direction,DataBlock> adjacentStatesDirection = datagrid.getAdjacentStatesDirection(block);
        AdjacentBlocks adjacentBlocks = new AdjacentBlocks(datagrid);
        adjacentBlocks.putAll(adjacentStatesDirection);
        return adjacentBlocks;

	}
}

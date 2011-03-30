package net.fossar.core;

import java.util.HashMap;
import java.util.Map;

import net.fossar.core.block.AbstractBlock;
import net.fossar.core.grid.DataGrid;

/**
 */
public class AdjacentBlocks extends HashMap<Direction, AbstractBlock> {
	protected DataGrid datagrid;

	public AdjacentBlocks(DataGrid datagrid) {
		this.datagrid = datagrid;
	}

	public AdjacentBlocks getAdjacents(AbstractBlock block) {
		Map<Direction, AbstractBlock> adjacentStatesDirection = datagrid.getAdjacentStatesDirection(block);
		AdjacentBlocks result = new AdjacentBlocks(datagrid);
		result.putAll(adjacentStatesDirection);

		return result;
	}
}

package net.fossar.core;

import java.util.HashMap;
import java.util.Map;

import net.fossar.core.grid.DataGrid;
import net.fossar.model.Direction;
import net.fossar.model.core.block.AbstractBlock;

/**
 */
public class AdjacentBlocks extends HashMap<Direction, AbstractBlock> {
	protected DataGrid datagrid;

	public AdjacentBlocks(DataGrid datagrid) {
		this.datagrid = datagrid;
	}

	public AdjacentBlocks getAdjacents(AbstractBlock block) {
		return datagrid.getAdjacentStatesDirection(block);
		
	}
}

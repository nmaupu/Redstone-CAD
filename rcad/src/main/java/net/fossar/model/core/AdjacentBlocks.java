package net.fossar.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.fossar.model.DataGrid;
import net.fossar.model.Direction;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.Block;
import net.fossar.model.core.block.DataBlock;

/**
 */
public class AdjacentBlocks extends HashMap<Direction, DataBlock> {
	protected DataGrid datagrid;

    protected List<DataBlock> alreadyProcessed;

    protected DataBlock centralBlock;

    public AdjacentBlocks(DataGrid datagrid, DataBlock centralBlock) {
		this.datagrid = datagrid;
        this.centralBlock = centralBlock;
        this.putAll(getAdjacents(centralBlock, null));
        alreadyProcessed = new ArrayList<DataBlock>();
        alreadyProcessed.add(centralBlock);
    }
    
    public AdjacentBlocks(AdjacentBlocks block, DataBlock centralBlock) {
		this.datagrid = block.datagrid;
        this.centralBlock = centralBlock;
        this.alreadyProcessed = block.alreadyProcessed;
        alreadyProcessed.add(centralBlock);
        this.putAll(getAdjacents(centralBlock, null));
    }

    public AdjacentBlocks(AdjacentBlocks block, DataBlock centralBlock, boolean forRecursive) {
		this.datagrid = block.datagrid;
        this.centralBlock = centralBlock;
        this.alreadyProcessed = block.alreadyProcessed;
        alreadyProcessed.add(centralBlock);
        this.putAll(getAdjacents(centralBlock, block.centralBlock));
    }


    public List<DataBlock> getAlreadyProcessed() {
        return alreadyProcessed;
    }

    protected Map<Direction,DataBlock> getAdjacents(DataBlock block, DataBlock fromBlock) {
        Map<Direction,DataBlock> adjacentStatesDirection = datagrid.getAdjacentStatesDirection(block);
        Map<Direction,DataBlock> adjacentBlocks = new HashMap<Direction, DataBlock>();

        for (Map.Entry<Direction, DataBlock> entry : adjacentStatesDirection.entrySet()) {
            if(fromBlock == null || !entry.getValue().isAt(fromBlock.getRow(),fromBlock.getCol(), fromBlock.getLay())) {
                adjacentBlocks.put(entry.getKey(), entry.getValue());
            }
        }

        return adjacentBlocks;
    }
}

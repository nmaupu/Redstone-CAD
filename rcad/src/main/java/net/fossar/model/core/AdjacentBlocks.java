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
import java.util.List;
import java.util.Map;

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

    public Map<Direction, DataBlock> getUpperBlocks(){
        return datagrid.getAdjacentBlockDirection(centralBlock,  1);
    }
        public Map<Direction, DataBlock> getDownBlocks(){
        return datagrid.getAdjacentBlockDirection(centralBlock,  -1);
    }
}

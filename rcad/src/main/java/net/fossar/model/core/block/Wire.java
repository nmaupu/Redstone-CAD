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

package net.fossar.model.core.block;

import net.fossar.model.Direction;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import net.fossar.model.core.AdjacentBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wire extends AbstractBlock implements PassiveBlock, DirectedBlock {
    protected static Logger logger = LoggerFactory.getLogger(Wire.class);
    
    protected static Set<Direction> horizontalPropagation = Collections.synchronizedSet(
            EnumSet.of(Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN));

    public Wire() {
        super(POWER_OFF, 0);
    }

    @Override
    public int getOutput() {
        return super.getOutput() == 0 ? 0 : super.getOutput() - 1;
    }

    @Override
    public void doUpdate(AdjacentBlocks adjacentBlocks) {
        for (Map.Entry<Direction, DataBlock> entry : adjacentBlocks.entrySet()) {
            DataBlock value = entry.getValue();
            Direction direction = entry.getKey();
            AbstractBlock block = value.getBlock();


            if (block instanceof Wire && horizontalPropagation.contains(direction)) {
                logger.debug("dealing with " + value);
                updateAdjacentWire(adjacentBlocks, value);
            }
            followUpperWire(adjacentBlocks, direction, value);
        }
    }

    /**
     * Follows upper wires.
     * A upper wire can only be followed when the block above the wire is an Air block
     *
     * @param adjacentBlocks
     * @param direction
     * @param value
     */
    private void followUpperWire(AdjacentBlocks adjacentBlocks, Direction direction, DataBlock value) {

        AbstractBlock block = value.getBlock();
        if (!(direction == Direction.ABOVE && BlockType.getBlockType(block) == BlockType.AIR)) {
            //There cannot be upper wire in this case.
            return;
        }
        
        Map<Direction, DataBlock> upperBlocks = adjacentBlocks.getUpperBlocks();
        // Iterate over the 4 possible abstractBlocks (UP, DOWN, LEFT, RIGHT) above the current wire.
        for (Map.Entry<Direction, DataBlock> upperBlock : upperBlocks.entrySet()) {
            if (upperBlock.getValue().getBlock() instanceof Wire && adjacentBlocks.get(
                    upperBlock.getKey()).getBlock() instanceof Block) {
                //this is the case where wire is over a block and connected to current wire.
                updateAdjacentWire(adjacentBlocks, upperBlock.getValue());
            }
        }


    }

    private void updateAdjacentWire(AdjacentBlocks adjacentBlocks, DataBlock value) {
        AdjacentBlocks adjacents = new AdjacentBlocks(adjacentBlocks, value, true);
        AbstractBlock block = value.getBlock();
        if (!adjacents.getAlreadyProcessed().contains(
                value) || ((block.getOutput() <= getOutput() - 1) && getOutput() != 0)) {
            logger.debug("i'm  " + getOutput() + " going to " + block.getOutput());
            block.setInput(getOutput());
            block.doUpdate(adjacents);
            logger.debug("destination : " + value + " is now " + block.getOutput());
        }
    }

    @Override
    public void updateDirection(AdjacentBlocks dirs) {
        initDirections();
        for (Map.Entry<Direction, DataBlock> entry : dirs.entrySet()) {
            AbstractBlock block = entry.getValue().getBlock();
            if (block instanceof Torch || block instanceof Wire) {
                addDirection(entry.getKey());
            }
        }
        if (direction.isEmpty()) {
            direction.add(Direction.LEFT);
            direction.add(Direction.RIGHT);
            direction.add(Direction.UP);
            direction.add(Direction.DOWN);
        }
    }

    public void resetBlockPower() {
        super.setPower(POWER_OFF);
    }
}

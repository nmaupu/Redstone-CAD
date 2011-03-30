package net.fossar.model.core.block;

import net.fossar.model.Direction;

import java.util.Map;

public class Lever extends AbstractBlock {

    public Lever() {
        super(POWER_OFF, 0);
    }

    public void powerOn() {
        super.setInput(POWER_SOURCE);
    }

    public void powerOff() {
        super.setInput(POWER_OFF);
    }

    @Override
    public void doUpdate(Map<Direction, AbstractBlock> adjacentBlocks) {
        for (Map.Entry<Direction, AbstractBlock> entry : adjacentBlocks.entrySet()) {
            AbstractBlock block = entry.getValue();
            if (block instanceof Wire) {
                block.setInput(getOutput());
//                block.doUpdate(adjacentBlocks.getAdjacents(block));
            }
        }
    }

}

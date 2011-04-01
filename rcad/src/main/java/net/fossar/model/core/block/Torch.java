package net.fossar.model.core.block;

import net.fossar.model.Direction;

import java.util.Map;

import net.fossar.model.core.AdjacentBlocks;

public class Torch extends AbstractBlock implements ActiveBlock {

	public Torch() {
		super(POWER_SOURCE, 0);
		powerOn();
	}

	@Override
	public void powerOn() {
		setInput(AbstractBlock.POWER_SOURCE);
	}

	@Override
	public void powerOff() {
		setInput(AbstractBlock.POWER_OFF);
	}

	@Override
	public void doUpdate(AdjacentBlocks adjacentBlocks) {
		for (Map.Entry<Direction, DataBlock> entry : adjacentBlocks.entrySet()) {
            DataBlock dataBlock = entry.getValue();
            AbstractBlock block = dataBlock.getBlock();
			if (block instanceof Block) {
				if (block.isPowered()) {
					this.powerOff();
				} else {
					this.powerOn();
				}
			}
		}
        for (Map.Entry<Direction, DataBlock> entry : adjacentBlocks.entrySet()) {
            DataBlock dataBlock = entry.getValue();
            AbstractBlock block = dataBlock.getBlock();
			if (block instanceof Wire){
                block.setInput(getOutput());
                block.doUpdate(new AdjacentBlocks(adjacentBlocks, dataBlock));
            }
		}


	}
}

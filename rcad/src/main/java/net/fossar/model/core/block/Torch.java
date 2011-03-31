package net.fossar.model.core.block;

import net.fossar.core.block.ActiveBlock;
import net.fossar.model.Direction;

import java.util.Map;

import net.fossar.core.AdjacentBlocks;

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
		for (Map.Entry<Direction, AbstractBlock> entry : adjacentBlocks.entrySet()) {
			AbstractBlock block = entry.getValue();
			if (block instanceof Block) {
				if (block.isPowered()) {
					this.powerOff();
				} else {
					this.powerOn();
				}
			}
		}

	}
}

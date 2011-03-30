package net.fossar.core.block;

import net.fossar.core.AdjacentBlocks;

public class Torch extends AbstractBlock implements ActiveBlock {

	public Torch() {
		super(POWER_SOURCE, 0);
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
		// TODO Auto-generated method stub

	}
}

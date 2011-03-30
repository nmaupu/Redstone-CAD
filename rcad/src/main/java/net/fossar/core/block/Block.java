package net.fossar.core.block;

import net.fossar.core.Direction;

import java.util.Map;

public class Block extends AbstractBlock {
	
	public Block() {
		super(POWER_OFF, PROPAGATION_DELAY);
	}

	@Override
	public void doUpdate(Map<Direction, AbstractBlock> adjacentBlocks) {
		// TODO Auto-generated method stub
		
	}

}

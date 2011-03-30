package net.fossar.model.core.block;

import net.fossar.model.Direction;

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

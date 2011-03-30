package net.fossar.core.block;

import net.fossar.core.Direction;

import java.util.Map;

public class Torch extends AbstractBlock {

	public Torch() {
		super(POWER_SOURCE, 0);
	}
	
	@Override
	public void doUpdate(Map<Direction, Block> adjacentBlocks) {
		// TODO Auto-generated method stub
		
	}

}

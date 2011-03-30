package net.fossar.model.core.block;

import net.fossar.model.Direction;

import java.util.Map;

public class Torch extends AbstractBlock {

	public Torch() {
		super(POWER_SOURCE, 0);
	}
	
	@Override
	public void doUpdate(Map<Direction, AbstractBlock> adjacentBlocks) {
		// TODO Auto-generated method stub
		
	}

}

package net.fossar.model.core.block;

import java.util.Map;

import net.fossar.model.Direction;

public class Air extends AbstractBlock {

	public Air() {
		super(POWER_OFF, 0);
	}
	
	@Override
	public void doUpdate(Map<Direction, AbstractBlock> adjacentBlocks) {
		// TODO Auto-generated method stub
		
	}

}

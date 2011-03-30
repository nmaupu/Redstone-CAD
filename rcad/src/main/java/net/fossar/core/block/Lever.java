package net.fossar.core.block;

import net.fossar.core.Direction;

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
            block.setInput(getOutput());
            if(block instanceof Wire){

            }
            
            
        }

	}

}

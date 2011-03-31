package net.fossar.model.core.block;

import net.fossar.model.Direction;

import java.util.Map;

import net.fossar.model.core.AdjacentBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wire extends AbstractBlock implements PassiveBlock {
    protected static Logger logger = LoggerFactory.getLogger(Wire.class);

	public Wire() {
		super(POWER_OFF, 0);
	}

	@Override
	public int getOutput() {
		return super.getOutput() == 0 ? 0 : super.getOutput() - 1;
	}

	@Override
	public void doUpdate(AdjacentBlocks adjacentBlocks) {
		for (Map.Entry<Direction, DataBlock> entry : adjacentBlocks.entrySet()) {
            DataBlock value = entry.getValue();
            AbstractBlock block = value.getBlock();
            if (block instanceof Wire) {
                logger.debug("dealing with " + value);
                AdjacentBlocks adjacents = new AdjacentBlocks(adjacentBlocks, value, true);

                if(!adjacents.getAlreadyProcessed().contains(value) || ((block.getOutput() <= getOutput() -1) && getOutput() != 0)) {
                    logger.debug("i'm  " + getOutput() + " going to "+ block.getOutput());
                    block.setInput(getOutput());
                    block.doUpdate(adjacents);
                    logger.debug("destination : " + value + " is now " + block.getOutput());
                }
			}
		}
	}

	@Override
	public void resetBlockPower() {
		super.setPower(POWER_OFF);
	}

}

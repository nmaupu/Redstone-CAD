package net.fossar.core.block;

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
	public void doUpdate() {
		// TODO Auto-generated method stub
		
	}

}

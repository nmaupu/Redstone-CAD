package net.fossar.core.block;

public class Wire extends AbstractBlock {

    public Wire() {
        super(POWER_OFF, 0);
    }

    @Override
    public int getOutput() {
        return super.getOutput() == 0 ? 0 : super.getOutput() - 1;
    }

    @Override
    public void doUpdate() {
        // TODO Auto-generated method stub

    }

}

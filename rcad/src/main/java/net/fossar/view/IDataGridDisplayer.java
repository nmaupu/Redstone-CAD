package net.fossar.view;

import net.fossar.model.core.block.DataBlock;

public interface IDataGridDisplayer extends IView {
	public void drawBlock(final DataBlock dataBlock);
}

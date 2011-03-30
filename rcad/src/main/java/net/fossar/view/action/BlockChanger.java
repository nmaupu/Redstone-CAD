package net.fossar.view.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.Air;
import net.fossar.model.core.block.Block;
import net.fossar.model.core.block.Torch;
import net.fossar.model.core.block.Wire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class BlockChanger extends AbstractAction {
	private static Logger logger = LoggerFactory.getLogger(BlockChanger.class);
	
	public static final String ACTION_BLOCK = "block";
	public static final String ACTION_AIR   = "air";
	public static final String ACTION_WIRE  = "wire";
	public static final String ACTION_TORCH = "torch";
	
	private static String currentBlock = ACTION_AIR;
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s instanceof JButton) {
			JButton b = (JButton)s;
			String cmd = b.getActionCommand();
			currentBlock = cmd;
			logger.debug("ActionPerformed - cmd="+cmd);
		}
	}
	
	public static AbstractBlock getBlock() {
		if(currentBlock.equals(ACTION_BLOCK))
			return new Block();
		if(currentBlock.equals(ACTION_TORCH))
			return new Torch();
		if(currentBlock.equals(ACTION_WIRE))
			return new Wire();
		
		return new Air();
	}
}

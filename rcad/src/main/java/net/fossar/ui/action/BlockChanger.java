package net.fossar.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import net.fossar.core.Block;
import net.fossar.core.BlockType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class BlockChanger extends AbstractAction {
	private static Logger logger = LoggerFactory.getLogger(BlockChanger.class);
	private BlockType blockType = BlockType.AIR;
	
	public static final String ACTION_BLOCK = "block";
	public static final String ACTION_AIR   = "air";
	public static final String ACTION_WIRE  = "wire";
	public static final String ACTION_TORCH = "torch";
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s instanceof JButton) {
			JButton b = (JButton)s;
			String cmd = b.getActionCommand();
			
			if (cmd.equals(ACTION_AIR))
				blockType = BlockType.AIR;
			else if (cmd.equals(ACTION_BLOCK))
				blockType = BlockType.BLOCK;
			else if (cmd.equals(ACTION_WIRE))
				blockType = BlockType.WIRE;
			else if (cmd.equals(ACTION_TORCH))
				blockType = BlockType.TORCH;
			
			logger.debug("ActionPerformed - cmd="+cmd);
		}
	}
	
	public BlockType getBlockType() {
		return blockType;
	}
}

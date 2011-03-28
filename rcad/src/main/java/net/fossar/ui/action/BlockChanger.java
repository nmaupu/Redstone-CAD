package net.fossar.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import net.fossar.core.Block;

@SuppressWarnings("serial")
public class BlockChanger extends AbstractAction {
	private Block blockType = Block.AIR;
	
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
				blockType = Block.AIR;
			else if (cmd.equals(ACTION_BLOCK))
				blockType = Block.BLOCK;
			else if (cmd.equals(ACTION_WIRE))
				blockType = Block.WIRE;
			else if (cmd.equals(ACTION_TORCH))
				blockType = Block.TORCH;
			
			System.out.println(cmd);
		}
	}
	
	public Block getBlockType() {
		return blockType;
	}
}

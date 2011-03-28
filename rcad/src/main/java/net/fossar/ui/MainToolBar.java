package net.fossar.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import net.fossar.ui.action.BlockChanger;

@SuppressWarnings("serial")
public class MainToolBar extends JToolBar implements ActionListener {
	private BlockChanger actionPerformer = new BlockChanger();
	private JButton blockButton = new JButton(actionPerformer);
	private JButton airButton   = new JButton(actionPerformer);
	private JButton wireButton  = new JButton(actionPerformer);
	private JButton torchButton = new JButton(actionPerformer);
	private JButton updateButton = new JButton();
	
	public MainToolBar() {
		super("Main tool bar");
		
		// Text
		blockButton.setText("Block");
		airButton.setText("Air");
		wireButton.setText("Wire");
		torchButton.setText("Torch");
		updateButton.setText("Update");
		
		// Action type
		blockButton.setActionCommand(BlockChanger.ACTION_BLOCK);
		airButton.setActionCommand(BlockChanger.ACTION_AIR);
		wireButton.setActionCommand(BlockChanger.ACTION_WIRE);
		torchButton.setActionCommand(BlockChanger.ACTION_TORCH);
		
		updateButton.setActionCommand("update");
		updateButton.addActionListener(this);
		
		this.add(updateButton);
		this.add(airButton);
		this.add(blockButton);
		this.add(wireButton);
		this.add(torchButton);
	}
	
	public BlockChanger getActionPerformer() {
		return actionPerformer;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(! (e.getSource() instanceof JButton))
			return;
		
		JButton b = (JButton)e.getSource();
		if(b.getActionCommand().equals("update")) {
			// update circuitry
			System.out.println("updating circuitry ...");
		}
			
	}
}

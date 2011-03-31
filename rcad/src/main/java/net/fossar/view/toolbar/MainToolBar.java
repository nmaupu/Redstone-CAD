package net.fossar.view.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import net.fossar.presenter.Presenter;
import net.fossar.view.View;
import net.fossar.view.action.BlockChanger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MainToolBar extends JToolBar implements ActionListener, View {
	private Logger logger = LoggerFactory.getLogger(MainToolBar.class);
	
	private Presenter presenter;
	
	private BlockChanger actionPerformer = new BlockChanger();
	private JButton blockButton = new JButton(actionPerformer);
	private JButton airButton   = new JButton(actionPerformer);
	private JButton wireButton  = new JButton(actionPerformer);
	private JButton torchButton = new JButton(actionPerformer);
	private JButton updateButton = new JButton();
	
	public MainToolBar(Presenter presenter) {
		super("Main tool bar");
		
		this.presenter = presenter;
		
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
			logger.info("Updating circuitry");
			//UIBuilder.mainFrame.getViewportStack().getDataGrid().updateCircuitrySources();
		}
			
	}
}

package net.fossar.view.toolbar;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JToolBar;

import net.fossar.presenter.ToolBarActionController;
import net.fossar.view.IView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MainToolBar extends JToolBar implements IView {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(MainToolBar.class);
	
	AbstractAction toolBarActionController = null;
	private JButton blockButton  = new JButton();
	private JButton airButton    = new JButton();
	private JButton wireButton   = new JButton();
	private JButton torchButton  = new JButton();
	private JButton updateButton = new JButton();
	
	public MainToolBar(AbstractAction aa) {
		super("Main tool bar");
		
		toolBarActionController = aa;
		
		blockButton.setAction(toolBarActionController);
		airButton.setAction(toolBarActionController);
		wireButton.setAction(toolBarActionController);
		torchButton.setAction(toolBarActionController);
		updateButton.setAction(toolBarActionController);
				
		// Text
		blockButton.setText("Block");
		airButton.setText("Air");
		wireButton.setText("Wire");
		torchButton.setText("Torch");
		updateButton.setText("Update");
		
		// Action type
		blockButton.setActionCommand(ToolBarActionController.ACTION_BLOCK);
		airButton.setActionCommand(ToolBarActionController.ACTION_AIR);
		wireButton.setActionCommand(ToolBarActionController.ACTION_WIRE);
		torchButton.setActionCommand(ToolBarActionController.ACTION_TORCH);
		updateButton.setActionCommand(ToolBarActionController.ACTION_UPDATE);
		
		this.add(updateButton);
		this.add(airButton);
		this.add(blockButton);
		this.add(wireButton);
		this.add(torchButton);
	}
}

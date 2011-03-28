package net.fossar.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.fossar.core.DataGrid;

@SuppressWarnings("serial")
public class Viewport extends JPanel {
	private DataGrid grid;
	private ViewportLabel[][] labels;
	private static final int BORDER_WIDTH = 1;
	public int displayedLayer = 0;
	
	public Viewport(DataGrid grid) {
		this.grid = grid;
		
		super.setLayout(new GridLayout(grid.getRows(), grid.getCols()));
		super.setPreferredSize(new Dimension(
									grid.getCols()*ViewportLabel.LABEL_WIDTH, 
									grid.getRows()*ViewportLabel.LABEL_WIDTH));
		super.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		init(displayedLayer);
	}
	
	private void init(int layer) {
		labels = new ViewportLabel[grid.getRows()][grid.getCols()];
		for (int i = 0; i<grid.getRows(); i++) {
			for (int j = 0; j<grid.getCols(); j++) {
				ViewportLabel l = grid.getLabels()[i][j][layer];
				labels[i][j] = l;
				
				int top    = BORDER_WIDTH;
				int left   = BORDER_WIDTH;
				int bottom = BORDER_WIDTH;
				int right  = BORDER_WIDTH;
				
				if (i == 0)
					top    = BORDER_WIDTH*2;
				else if (i == grid.getRows()-1)
					bottom = BORDER_WIDTH*2;
				if(j==0)
					left   = BORDER_WIDTH*2;
				else if (j == grid.getCols()-1)
					right  = BORDER_WIDTH*2;
				
				l.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.GRAY));
				super.add(l);
			}
		}
	}
	
	public void repaintViewport() {
		for(int i=0; i<grid.getRows(); i++)
			for(int j=0; j<grid.getCols(); j++)
				for(int k=0; k<grid.getLayers(); k++)
					grid.getLabels()[i][j][k].repaint();
	}
}

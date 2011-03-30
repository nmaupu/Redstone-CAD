package net.fossar.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import net.fossar.model.DataGrid;
import net.fossar.presenter.Director;
import net.fossar.presenter.Presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Viewport extends JPanel implements View {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(Viewport.class);
	
	private Presenter presenter;
	private DataGrid grid;
	private ViewportLabel[][] labels = null;
	private int layerIdx = 0;
	
	public Viewport(Presenter presenter, int layerIdx) {
		this.presenter = presenter;
		this.grid = (DataGrid) presenter.getModel(Director.DATAGRID);
		this.layerIdx = layerIdx;
		
		super.setLayout(new GridLayout(grid.getRows(), grid.getCols()));
		initLabels();
	}
	
	private void initLabels() {
		if (labels == null)
			labels = new ViewportLabel[grid.getRows()][grid.getCols()];
		
		for(int i=0; i<grid.getRows(); i++) {
			for(int j=0; j<grid.getCols(); j++) {
				ViewportLabel vl = new ViewportLabel(presenter, grid.getBlock(i, j, layerIdx)); 
				labels[i][j] = vl;
				super.add(labels[i][j]);
			}
		}
	}
	
	public int getLayerId() {
		return layerIdx;
	}
	
	public ViewportLabel getViewportLabel(int r, int c) {
		return labels[r][c];
	}
}

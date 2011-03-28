package net.fossar.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;

import net.fossar.core.Block;
import net.fossar.core.DataGrid;

@SuppressWarnings("serial")
public class ViewportLabel extends JLabel implements MouseListener, MouseMotionListener {
	public static final Color HOVER_COLOR      = Color.ORANGE;
	public static final int   LABEL_WIDTH      = 25;
	private Block state;
	private boolean drag = false;
	private int row, col, lay;
	private DataGrid grid;
	
	public ViewportLabel(int row, int col, int lay, DataGrid grid) {
		super();
		this.row = row;
		this.col = col;
		this.lay = lay;
		this.grid = grid;
		
		this.setSize(LABEL_WIDTH, LABEL_WIDTH);
		this.setBounds(0, 0, LABEL_WIDTH, LABEL_WIDTH);
				
		this.setOpaque(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		state = Block.AIR;
	}
	
	public void setState(Block b) {
		state = b;
		setBackground(b.getBackgroundColor());
	}
	
	public Block getState() {
		return state;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("Entered");
		ViewportLabel l = (ViewportLabel)e.getSource();
		if (!drag)
			l.setBackground(ViewportLabel.HOVER_COLOR);
		else
			l.setState(MainFrame.mainToolBar.getActionPerformer().getBlockType());
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("Exited");
		ViewportLabel l = (ViewportLabel)e.getSource();
		l.setState(l.getState());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//ViewportLabel l = (ViewportLabel)e.getSource();
		this.setState(MainFrame.mainToolBar.getActionPerformer().getBlockType());
		
		this.repaint();
		this.repaintAdjacents();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		drag = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//ViewportLabel l =(ViewportLabel) e.getSource();
		System.out.println("Dragged");
		drag = true;
		mousePressed(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		state.paint(this, grid, g1);
	}
	
	public void repaintAdjacents() {
		if (grid == null)
			return;
		
		int r = this.getRow();
		int c = this.getCol();
		int l = this.getLay();
		
		if (r>0)
			grid.getLabels()[r-1][c][l].repaint();
		
		if (r<grid.getRows() - 1)
			grid.getLabels()[r+1][c][l].repaint();
		
		if (c>0)
			grid.getLabels()[r][c-1][l].repaint();
		
		if (c<grid.getCols() - 1)
			grid.getLabels()[r][c+1][l].repaint();
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getLay() {
		return lay;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void setLay(int lay) {
		this.lay = lay;
	}
}

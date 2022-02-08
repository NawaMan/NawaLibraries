package net.nawaman.swing.textpanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class TextPanel extends JPanel {

	private static final long serialVersionUID = -4468049388316382206L;

	public TextPanel() {
		super(new TextFlowLayout());

		this.myBorder = new BlockBorder(this.getBackground());
		this.setBorder(this.myBorder);
		
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
        		if(     e.getID() == MouseEvent.MOUSE_ENTERED) mouseon = true;
        		else if(e.getID() == MouseEvent.MOUSE_EXITED ) mouseon = false;
        		else return;

        		ensureBorder();
            }
        });
	}

	public TextBlock newBlock(String initText) {
		TextBlock newTB = new TextBlock(initText);
		this.add(newTB);
		newTB.setFont(this.getFont());
		return newTB;
	}
	
	public TextBlock addBlock(TextBlock TB) {
		this.add(TB);
		TB.setFont(this.getFont());
		return TB;
	}

	protected BlockBorder myBorder = null;
	protected boolean mouseon    = false;
	
	@Override public void setBackground(Color color) {
		super.setBackground(color);

		Component[] Comps = this.getComponents();
		for(Component Comp : Comps) {
			if(Comp instanceof TextBlock) {
				TextBlock TB = (TextBlock)Comp;
				TB.setBackground(color);
			}
		}
	}

	@Override protected void processMouseEvent(MouseEvent e) {
		super.processMouseEvent(e);

		if(     e.getID() == MouseEvent.MOUSE_ENTERED) this.mouseon = true;
		else if(e.getID() == MouseEvent.MOUSE_EXITED ) this.mouseon = false;
		else return;

		this.ensureBorder();
	}
	
	protected void ensureBorder() {
		if(this.myBorder == null) return;

		if(this.mouseon) this.myBorder.setBorderColor(Color.red);
		else             this.myBorder.setBorderColor(this.getBackground());
		//else             this.myBorder.setLineColor(Color.gray);//this.getBackground());

		this.repaint();
	}	
}
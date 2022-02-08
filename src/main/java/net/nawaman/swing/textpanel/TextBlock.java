package net.nawaman.swing.textpanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;

public class TextBlock extends JTextField {

	private static final long serialVersionUID = 3621978154991099408L;

	static final private Font font = new Font("Monospaced", Font.PLAIN, 12);

	protected BlockBorder myBorder = null;

	protected boolean IsToExpand = false;
	protected boolean focused    = false;
	protected boolean mouseon    = false;

	public TextBlock(String initString) {
		this(initString, false);
	}
	public TextBlock(String initString, boolean pIsToExpand) {
		super(initString);

		this.setFont(font);

		this.myBorder = new BlockBorder(this.getBackground());
		this.setBorder(this.myBorder);
		// TODO - Think what to do with IsToExpand
		this.IsToExpand = pIsToExpand;
	}

	@Override public void setText(String aText) {
		super.setText(aText);
		this.ensureWidth();
	}

	public void setToExpand()    { this.IsToExpand =  true; }
	public void setNotToExpand() { this.IsToExpand = false; }

	@Override protected void fireCaretUpdate(CaretEvent e) {
		super.fireCaretUpdate(e);
		this.ensureWidth();
	}

	@Override protected void processFocusEvent(FocusEvent e) {
		super.processFocusEvent(e);
		if(     e.getID() == FocusEvent.FOCUS_GAINED) this.focused = true;
		else if(e.getID() == FocusEvent.FOCUS_LOST  ) this.focused = false;
		else return;

		this.ensureBorder();
	}

	@Override protected void processMouseEvent(MouseEvent e) {
		super.processMouseEvent(e);

		if(     e.getID() == MouseEvent.MOUSE_ENTERED) this.mouseon =  true;
		else if(e.getID() == MouseEvent.MOUSE_EXITED ) this.mouseon = false;
		else return;

		this.ensureBorder();		
	}
	/*
	@Override public void paint(Graphics g) {
		super.paint(g);
		Dimension D = this.getSize();
		//System.out.println(D);
		//System.out.println(this.getBaseline(D.width, D.height));
		int bLine = this.getBaseline(D.width, D.height);
		g.drawLine(0, bLine, D.width, bLine);
	}*/

	protected void ensureWidth() {
		int width = 0;
		int height= 0;

		int bWidth = 0;
		Insets aInsets = null;

		//int tWidth = 0;

		Graphics2D g2 = (Graphics2D)this.getGraphics();
		if(g2 == null) return;
		Rectangle2D rect = this.getFont().getStringBounds(
		                                    this.getText(),
		                                    g2.getFontRenderContext()
		                                  );

		width  = this.getBounds().width;
		height = this.getBounds().height;

		aInsets = this.getInsets();
		bWidth  = aInsets.left + aInsets.right;

		width = (int)Math.floor(rect.getWidth());
		this.setSize(width + bWidth + 20, height);

		((JPanel)this.getParent()).revalidate();
	}

	protected void ensureBorder() {
		if(this.myBorder == null) return;

		if(     this.focused) this.myBorder.setBorderColor(Color.blue);
		else if(this.mouseon) this.myBorder.setBorderColor(Color.red);
		else                  this.myBorder.setBorderColor(this.getBackground());
		//else                  this.myBorder.setLineColor(Color.gray);//this.getBackground());

		this.repaint();
	}
}
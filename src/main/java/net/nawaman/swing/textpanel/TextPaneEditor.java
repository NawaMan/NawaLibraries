package net.nawaman.swing.textpanel;

import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.*;

public class TextPaneEditor extends JFrame implements ImageObserver {

	private static final long serialVersionUID = -881349363467630184L;
	
	static int VERTICAL_SCROLLBAR_AS_NEEDED   = 20;
    static int VERTICAL_SCROLLBAR_NEVER       = 21;
    static int VERTICAL_SCROLLBAR_ALWAYS      = 22;
    static int HORIZONTAL_SCROLLBAR_AS_NEEDED = 30;
    static int HORIZONTAL_SCROLLBAR_NEVER     = 31;
    static int HORIZONTAL_SCROLLBAR_ALWAYS    = 32;
    
    TextPaneEditor(String pTitle) { super(pTitle); }
    
	public static void main(String[] args) {

		try {
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException ex) {
			System.out.println("Unable to load native look and feel");
		} catch(Exception E) {
			System.out.println("Other Problems");
		}

		TextPaneEditor f = new TextPaneEditor("TextLayoutPanel v1.0");

		JScrollPane SP = new JScrollPane(VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_ALWAYS);
		f.add(SP);

		SP.getVerticalScrollBar().setUnitIncrement(10);
		SP.getHorizontalScrollBar().setUnitIncrement(10);

		TextPanel p = new TextPanel();
		SP.setViewportView(p);

		p.setPreferredSize(new Dimension(100, 100));
		p.setBackground(Color.white);
		p.setBorder(new BlockBorder(Color.blue));

		TextBlock TB = null;
		SimpleLineSetting aLS = SimpleLineSetting.newDefault();
		aLS.Indent = 0;
		p.add(aLS.clone());
		TB = p.newBlock("Text# 1-" +  0);
		TB = p.newBlock("Text# 1-" +  1);
		TB = p.newBlock("Text# 1-" +  2);
		TB = p.newBlock("Text# 1-" +  3);
		TB = p.newBlock("Text# 1-" +  4);
		TB = p.newBlock("Text# 1-" +  5);
		TB = p.newBlock("Text# 1-" +  6);
		TextPanel TP = new TextPanel();
		p.add(TP);
		//TP.setMinimumSize(new Dimension(100, 30));
		//TP.setMaximumSize(new Dimension(100, 30));
		TP.newBlock("Inside");
		//TB.IsToExpand = true;

		aLS.isWrap = true;
		p.add(aLS.clone());
		TB = p.newBlock("Text# 2-" +  0);
		TB = p.newBlock("Text# 2-" +  1);
		TB = p.newBlock("Text# 2-" +  2);
		TB = p.newBlock("Text# 2-" +  3);
		TB = p.newBlock("Text# 2-" +  4);
		TB = p.newBlock("Text# 2-" +  5);
		TB = p.newBlock("Text# 2-" +  6);

		TB = p.newBlock("Text# 1-" +  7);
		TB = p.newBlock("Text# 1-" +  8);
		TB = p.newBlock("Text# 1-" +  9);
		TB = p.newBlock("Text# 1-" + 10);
		TB = p.newBlock("Text# 1-" + 11);
		TB = p.newBlock("Text# 1-" + 12);
		TB = p.newBlock("Text# 1-" + 13);
		TB = p.newBlock("Text# 1-" + 14);
		TB = p.newBlock("Text# 1-" + 15);
		TB = p.newBlock("Text# 1-" + 16);
		TB = p.newBlock("Text# 1-" + 17);
		TB = p.newBlock("Text# 1-" + 18);
		TB = p.newBlock("Text# 1-" + 19);
		TB = p.newBlock("Text# 1-" + 20);
		TB = p.newBlock("Text# 1-" + 21);
		TB = p.newBlock("Text# 1-" + 22);
		TB = p.newBlock("Text# 1-" + 23);
		TB = p.newBlock("Text# 1-" + 24);
		TB = p.newBlock("Text# 1-" + 25);
		TB = p.newBlock("Text# 1-" + 26);
		TB = p.newBlock("Text# 1-" + 27);
		TB = p.newBlock("Text# 1-" + 28);
		TB = p.newBlock("Text# 1-" + 29);
		TB = p.newBlock("Text# 1-" + 30);
		TB = p.newBlock("Text# 1-" + 31);
		TB = p.newBlock("Text# 1-" + 32);
		
		aLS.isWrap = false;
		aLS.VAlign = VAlignment.BASELINE;
		p.add(aLS.clone());
		System.out.println("Count = " + p.getComponentCount());
		TB = p.newBlock("Text-# 2-" + 1); TB.setFont(new Font("Monospaced", Font.PLAIN, 24));
		TB = p.newBlock("Text-# 2-" + 2); TB.setFont(new Font("Monospaced", Font.PLAIN, 10));
		TB = p.newBlock("Text-# 2-" + 3); TB.setFont(new Font("Monospaced", Font.PLAIN, 24));

		aLS.HAlign = HAlignment.LEFT;
		aLS.VAlign = VAlignment.TOP;
		p.add(aLS.clone());
		System.out.println("Count = " + p.getComponentCount());
		TB = p.newBlock("Text# 3-" + 1);
		TB = p.newBlock("Text# 3-" + 2);  TB.setFont(new Font("Monospaced", Font.PLAIN, 40));
		TB = p.newBlock("Text# 3-" + 3);


		aLS.HAlign = HAlignment.CENTER;

		aLS.VAlign = VAlignment.BOTTOM;
		p.add(aLS.clone());
		System.out.println("Count = " + p.getComponentCount());
		TB = p.newBlock("Text# 1-" + 0);
		TB = p.newBlock("Text# 1-" + 1);
		TB = p.newBlock("Text# 1-" + 2); TB.setFont(new Font("Monospaced", Font.PLAIN, 24));
		TB = p.newBlock("Text# 1-" + 3);

		aLS.VAlign = VAlignment.CENTER;
		p.add(aLS.clone());
		System.out.println("Count = " + p.getComponentCount());
		TB = p.newBlock("Text# 2-" + 1); TB.setFont(new Font("Monospaced", Font.PLAIN, 24));
		TB = p.newBlock("Text# 2-" + 2); TB.setFont(new Font("Monospaced", Font.PLAIN, 10));
		TB = p.newBlock("Text# 2-" + 3); TB.setFont(new Font("Monospaced", Font.PLAIN, 24));

		aLS.VAlign = VAlignment.TOP;
		p.add(aLS.clone());
		System.out.println("Count = " + p.getComponentCount());
		TB = p.newBlock("Text# 3-" + 1);
		TB = p.newBlock("Text# 3-" + 2);  TB.setFont(new Font("Monospaced", Font.PLAIN, 40));
		TB = p.newBlock("Text# 3-" + 3);
		p.add(aLS.clone());
		TB = p.newBlock("Text# 4-" + 4);
		TB.IsToExpand = true;

		aLS.HAlign = HAlignment.RIGHT;

		aLS.VAlign = VAlignment.BOTTOM;
		p.add(aLS.clone());
		System.out.println("Count = " + p.getComponentCount());
		TB = p.newBlock("Text# 1-" + 0);
		TB = p.newBlock("Text# 1-" + 1);
		TB = p.newBlock("Text# 1-" + 2); TB.setFont(new Font("Monospaced", Font.PLAIN, 24));
		TB = p.newBlock("Text# 1-" + 3);

		aLS.VAlign = VAlignment.CENTER;
		p.add(aLS.clone());
		System.out.println("Count = " + p.getComponentCount());
		TB = p.newBlock("Text# 2-" + 1); TB.setFont(new Font("Monospaced", Font.PLAIN, 24));
		TB = p.newBlock("Text# 2-" + 2); TB.setFont(new Font("Monospaced", Font.PLAIN, 10));
		TB = p.newBlock("Text# 2-" + 3); TB.setFont(new Font("Monospaced", Font.PLAIN, 24));

		aLS.VAlign = VAlignment.TOP;
		p.add(aLS.clone());
		System.out.println("Count = " + p.getComponentCount());
		TB = p.newBlock("Text# 3-" + 1);
		TB = p.newBlock("Text# 3-" + 2);  TB.setFont(new Font("Monospaced", Font.PLAIN, 40));
		TB = p.newBlock("Text# 3-" + 3);
/*
		int num = 1000;
		for(int i = 1; i < num; i++) {
			if((i % 10) == 0) {
				TextPanel supP = new TextPanel();
				supP.setBackground(Color.gray);
				p.add(supP);
				int supNum = 5;
				for(int si = 1; si < supNum; si++) {
					supP.newBlock("T_sub_" + si);
				}
			} else p.newBlock("Text-" + i);
		}*/
		//p.newBlock("T" + 0);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(10, 10, 600, 400);
		f.setVisible(true);
	}
}
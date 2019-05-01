/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (C) 2008-2019 Nawapunth Manusitthipol. Implements with and for Sun Java 1.6 JDK.
 *----------------------------------------------------------------------------------------------------------------------
 * LICENSE:
 * 
 * This file is part of Nawa's Utilities.
 * 
 * The project is a free software; you can redistribute it and/or modify it under the SIMILAR terms of the GNU General
 * Public License as published by the Free Software Foundation; either version 2 of the License, or any later version.
 * You are only required to inform me about your modification and redistribution as or as part of commercial software
 * package. You can inform me via nawaman<at>gmail<dot>com.
 * 
 * The project is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 * ---------------------------------------------------------------------------------------------------------------------
 */

package net.nawaman.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

// Noted - Copied from blitzcoderide on GoogleCode but edit a lot.

/**
 * Text editor with line number
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 */
public class LineNumberedTextComponentPanel extends FixedPanel {

	private static final long serialVersionUID = 6583636270580377500L;

	/** Empty border to make space for the line number */
	static public class InnerLineNumberBorder extends javax.swing.border.EmptyBorder {
		
		private static final long serialVersionUID = 6842218756057874658L;

		public InnerLineNumberBorder(int pLeft) {
	    	super(0, pLeft, 0, 0);
	    }
		
		/** Set the left of the border making the room accordingly */
	    public void setLeft(int pLeft) {
	    	this.left = pLeft;
	    }
	}
	
	/** The compound border that allow the text panel to simulate single border */
	static public class LineNumberBorder extends CompoundBorder {
		
		private static final long serialVersionUID = -388289806256012618L;
		
		public LineNumberBorder(int pLeft) {
			super(null, new InnerLineNumberBorder(pLeft));
		}
		
		/** Set the left of the inside border */
	    public void setLeft(int pLeft) {
	    	((InnerLineNumberBorder)this.getInsideBorder()).setLeft(pLeft);
	    }
		
		/** Set the outside border */
	    public void setOutsideBorder(Border B) {
			this.outsideBorder = B;
		}
		/** Set the inside border */
		void setInsideBorder( Border B) {
			this.insideBorder  = B;
		}
	}
	
	public LineNumberedTextComponentPanel() {
		this(new JTextArea());
		//((JTextArea)this.JTComponent).setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 15));
        ((JTextArea)this.JTComponent).setFont(new java.awt.Font("Monospaced", 0, 15));
		((JTextArea)this.JTComponent).setTabSize(4);

		// Set the position of the JTextCoomponent. so that there is enough space for it.
		int Left = this.getConfiguration().getLineNumberDigit() *
		           this.getFontMetrics(this.JTComponent.getFont()).stringWidth("0") +
				   LEFT_MARGIN;
		this.getLineNumberBorder().setLeft(Left);
	}
	
	public LineNumberedTextComponentPanel(JTextComponent pPane) {
		super();
		final LineNumberedTextComponentPanel This = this;
		
		this.JTComponent = pPane;
		this.setLayout(new BorderLayout());
		
		this.add(this.JTComponent, BorderLayout.CENTER);
		this.setFixed(true);
		
		this.prepareTextComponent();
		
		// Set the position of the JTextCoomponent. so that there is enough space for it.
		int Left = this.getConfiguration().getLineNumberDigit() *
		           this.getFontMetrics(this.JTComponent.getFont()).stringWidth("0") +
				   LEFT_MARGIN; 
		super.setBorder(new LineNumberBorder(Left));
		
		// Redraw when update.
		this.JTComponent.getDocument().addDocumentListener(new DocumentListener() {
	        public void insertUpdate(DocumentEvent e)  { this.update(); This.notifiedDocumentInsertUpdate(e); }
	        public void removeUpdate(DocumentEvent e)  { this.update(); This.notifiedDocumentRemoveUpdate(e); }
	        public void changedUpdate(DocumentEvent e) { this.update(); This.notifiedDocumentChangeUpdate(e); }
	        
	        private void update() {
	        	if(This.TheChangeHighlight != null) {
		        	Object ComponentToHighlight = This.JTComponent;
		        	Object Parent               = This.getParent();
		    		if((Parent instanceof JComponent) && (((JComponent)This.getParent()).getParent() instanceof JScrollPane))
		    			ComponentToHighlight = ((JComponent)This.getParent()).getParent();
		    		
		    		if(ComponentToHighlight instanceof JComponent) {
						String PT = This.TheChangeHighlight.PrevText;
						String NT = This.JTComponent.getText();
			        	if(PT == null) This.TheChangeHighlight.PrevText = (PT = NT);
						
			            if((PT.length() == NT.length()) && PT.equals(NT))
			            	 ((JComponent)ComponentToHighlight).setBorder(This.TheChangeHighlight.Border_Normal);
			            else ((JComponent)ComponentToHighlight).setBorder(ChangeHighlight.BORDER_BLUE);
		    		}
	        	}
	        	
	        	repaint();
				fireChangeEvent();
	        }
		});
		this.JTComponent.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				This.notifiedCaretUpdate(e);
			}
		});
		
		// Add cut, copy and paste
		final JPopupMenu PopupManu = new JPopupMenu();
		final JMenuItem  MI_Cut    = new JMenuItem();
		final JMenuItem  MI_Copy   = new JMenuItem();
		final JMenuItem  MI_Paste  = new JMenuItem();
		final JMenuItem  MI_Undo   = new JMenuItem();
		final JMenuItem  MI_Redo   = new JMenuItem();

		MI_Cut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_X,
				java.awt.event.InputEvent.CTRL_MASK));
		MI_Cut.setMnemonic('t');
		MI_Cut.setText("Cut");
		MI_Cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) { This.doCopy(); }
		});
		PopupManu.add(MI_Cut);

		MI_Copy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_C,
				java.awt.event.InputEvent.CTRL_MASK));
		MI_Copy.setMnemonic('C');
		MI_Copy.setText("Copy                 ");
		MI_Copy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) { This.doCopy(); }
		});
		PopupManu.add(MI_Copy);

		MI_Paste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_V,
				java.awt.event.InputEvent.CTRL_MASK));
		MI_Paste.setMnemonic('P');
		MI_Paste.setText("Paste");
		MI_Paste.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) { This.doPaste(); }
		});
		PopupManu.add(MI_Paste);
		PopupManu.add(new JSeparator());

		MI_Undo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_Z,
				java.awt.event.InputEvent.CTRL_MASK));
		MI_Undo.setMnemonic('U');
		MI_Undo.setText("Undo");
		MI_Undo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) { This.doUndo(); }
		});
		PopupManu.add(MI_Undo);

		MI_Redo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_Y,
				java.awt.event.InputEvent.CTRL_MASK));
		MI_Redo.setMnemonic('R');
		MI_Redo.setText("Redo");
		MI_Redo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) { This.doRedo(); }
		});
		PopupManu.add(MI_Redo);
		
        this.JTComponent.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent evt) {
            	if(evt.isPopupTrigger()) {
            		PopupManu.show(evt.getComponent(), evt.getX(), evt.getY());
            		MI_Undo.setEnabled(This.UndoManager.canUndo());
            		MI_Redo.setEnabled(This.UndoManager.canRedo());
					
					MI_Cut  .setEnabled(This.JTComponent.isEditable());
					MI_Paste.setEnabled(This.JTComponent.isEditable());
            	}
            }
        });
        this.JTComponent.addKeyListener(new KeyAdapter() {
        	@Override public void keyReleased(KeyEvent evt) {
        		if(evt.getKeyCode() == KeyEvent.VK_HOME) {
        			Component Parent = getParent().getParent();
        			if(!(Parent instanceof JScrollPane)) return;
        			
        			// Scroll all to the left when home is pressed
        			JScrollPane JSP = (JScrollPane)This.getParent().getParent();
        			JSP.getHorizontalScrollBar().setValue(0);
        		}
            }
        });
		
		// Add Undo and Redo
		// -------------------------------------------------------------------------------------------
        Document Doc_OriginalText = this.JTComponent.getDocument();

        // Listen for undo and redo events
        Doc_OriginalText.addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent evt) {
            	if(This.IsHistoryEnabled) This.UndoManager.addEdit(evt.getEdit());
            }
        });
        // Create an undo action and add it to the text component
        this.JTComponent.getActionMap().put("Undo",
            new AbstractAction("Undo") {
				private static final long serialVersionUID = -8935927392904945443L;
				public void actionPerformed(ActionEvent evt) { This.doUndo(); }
			}
        );
        // Bind the undo action to ctl-Z
        this.JTComponent.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
        
        // Create a redo action and add it to the text component
        this.JTComponent.getActionMap().put("Redo",
            new AbstractAction("Redo") {
				private static final long serialVersionUID = 4290822409012711184L;
				public void actionPerformed(ActionEvent evt) { This.doRedo(); }
			}
        );
        // Bind the redo action to ctl-Y
        this.JTComponent.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");
		
		// Draw
        this.repaint();
	}
	
	/** This method is for the sub class to prepare the text component before this class manipulat it */
	protected void prepareTextComponent() {}
	
	// Border ----------------------------------------------------------------------------------------------------------
	
	/** Returns the inner border of the line number border */
	protected InnerLineNumberBorder getInnerLineNumberBorder() {
		return (InnerLineNumberBorder)(((LineNumberBorder)super.getBorder()).getInsideBorder());
	}
	/** Changes the inner border of the line number border */
	protected void setInnerLineNumberBorder(InnerLineNumberBorder B) {
		this.getLineNumberBorder().setInsideBorder(B);
	}
	/** Returns the line number border */
	protected LineNumberBorder getLineNumberBorder() {
		return (LineNumberBorder)super.getBorder();
	}
	
	/**{@inherDoc}*/ @Override final public void   setBorder(Border B) {
		if(this.getLineNumberBorder() == null) return;
		this.getLineNumberBorder().setOutsideBorder(B);
	}
	
	/**{@inherDoc}*/ @Override final public Border getBorder()  {
		if(this.getLineNumberBorder() == null) return null;
		return this.getLineNumberBorder().getOutsideBorder();
	}
	
	/**{@inheritDoc}*/ @Override
	public void setFont(Font F) {
		if(this.JTComponent == null) return;
		this.JTComponent.setFont(F);

		// Set the position of the JTextCoomponent. so that there is enough space for it.
		int Left = this.getConfiguration().getLineNumberDigit() *
		           this.getFontMetrics(this.JTComponent.getFont()).stringWidth("0") +
				   LEFT_MARGIN;
		this.getLineNumberBorder().setLeft(Left);
	}
	
	// Layout ----------------------------------------------------------------------------------------------------------

	// Only BorderLayout is allowed here
	/**{@inheritDoc}*/ @Override
	final public void setLayout(LayoutManager pLayout) {
		if(!(pLayout instanceof BorderLayout)) return;
		super.setLayout(pLayout);
	} 
	
	// Notification ----------------------------------------------------------------------------------------------------
	
	/** Get notified when an insert action is performed to the document */
	protected void notifiedDocumentInsertUpdate(DocumentEvent e) { notifiedDocumentUpdate(); }
	/** Get notified when a remove action is performed to the document */
	protected void notifiedDocumentRemoveUpdate(DocumentEvent e) { notifiedDocumentUpdate(); }
	/** Get notified when a change action is performed to the document */
	protected void notifiedDocumentChangeUpdate(DocumentEvent e) { notifiedDocumentUpdate(); }
	/** Get notified when an action is performed to the document */
	protected void notifiedDocumentUpdate() {}
	
	/** Get notified when an caret action is performed to the document */
	protected void notifiedCaretUpdate(CaretEvent evt) { }
	
	// Clipboard -------------------------------------------------------------------------------------------------------
	
	protected void doCopy() {
		this.JTComponent.copy();
	}
	protected void doCut() {
		this.JTComponent.cut();
	}
	protected void doPaste() {
		this.JTComponent.paste();
	}
	
	public void copy() {
		this.doCopy();
	}
	public void cut() {
		this.doCut();
	}
	public void paste() {
		this.doPaste();
	}

	// History ---------------------------------------------------------------------------------------------------------

	private UndoManager UndoManager      = new UndoManager();
	private boolean     IsHistoryEnabled = true;
	
	/** Performed undo */ protected void doUndo() { try { if (this.IsHistoryEnabled && UndoManager.canUndo()) UndoManager.undo(); } catch (CannotUndoException e) {} }
	/** Performed redo */ protected void doRedo() { try { if (this.IsHistoryEnabled && UndoManager.canRedo()) UndoManager.redo(); } catch (CannotRedoException e) {} }
	
	/** Clear the history */
	protected void clearHistory() {
		this.UndoManager.discardAllEdits();
	}
	
	/** Set the history to be enabled or disabled */
	protected void setHistoryEnabled(boolean pIsHistoryEnabled) {
		this.IsHistoryEnabled = pIsHistoryEnabled;
	}
	
	/** Checks if the history is enabled */
	public boolean isHistoryEnabled() { return this.IsHistoryEnabled; }
	
	// Text manipulation -----------------------------------------------------------------------------------------------
	
	private JTextComponent JTComponent;
	/** Returns the text component that this is holding. */
	public JTextComponent getTextComponent() { return this.JTComponent; }
	
	/** get text of the text component */
	public String getText() {
		return this.getTextComponent().getText();
	}
	/** Set text of this Text component */
	public void setText(String pText) {
		this.getTextComponent().setText(pText);
	}
	
	HashSet<ChangeListener> ChangeListeners = null;
	
	public void addChangeListener(ChangeListener pChangeListener) {
		if(pChangeListener == null) return;
		if(this.ChangeListeners == null) this.ChangeListeners = new HashSet<ChangeListener>();
		this.ChangeListeners.add(pChangeListener);
	}
	public void removeChangeListener(ChangeListener pChangeListener) {
		if(pChangeListener == null) return;
		if(this.ChangeListeners == null) return;
		if(this.ChangeListeners.contains(pChangeListener)) this.ChangeListeners.remove(pChangeListener);
	}
	
	protected void fireChangeEvent() {
		if(this.ChangeListeners == null) return;
		ChangeEvent CE = new ChangeEvent(this);
		for(ChangeListener CL : this.ChangeListeners) {
			if(CL == null) continue;
			CL.stateChanged(CE);
		}
	}
	
	// Configuration ---------------------------------------------------------------------------------------------------
	
	/** Spare at least 3 digit for the line number */
	static public final int LINE_NUMBER_DIGIT =   3;
	/** Maximum line number without having to add more digit */
	static public final int LINE_NUMBER_MAX   = 999;
	/** A margin in pixels from the line number to the text */
	static public final int LEFT_MARGIN       =   10;
	/** The position where the right line will be drawn */
	static public final int RIGHT_LIMIT       = 120;
	
	/** Configuration for LineNumberedTextAreaPanel */
	static public class Config {
	
		private int LineNumberDigit = LINE_NUMBER_DIGIT;
		private int LeftMargin      = LINE_NUMBER_MAX;
		private int RightLimit      = RIGHT_LIMIT;

		private int LineNumberMax = (int)Math.pow(10, LineNumberDigit);
		
		private Color LineColor = Color.BLUE;
		
		public int   getLineNumberDigit() { return this.LineNumberDigit; }
		public int   getLineNumberMax()   { return this.LineNumberMax;   }
		public int   getLeftMargin()      { return this.LeftMargin;      }
		public int   getRightLimit()      { return this.RightLimit;      }
		public Color getLineColor()       { return this.LineColor;       }

		public void setLineNumberDigit(int   pLineNumberDigit) {
			this.LineNumberDigit = pLineNumberDigit;
			this.LineNumberMax   = (pLineNumberDigit == 0)?0:(int)Math.pow(10, LineNumberDigit);
		}
		public void setLineNumberMax(  int   pLineNumberMax)   { this.LineNumberMax   = pLineNumberMax;   }
		public void setLeftMargin(     int   pLeftMargin)      { this.LeftMargin      = pLeftMargin;      }
		public void setRightLimit(     int   pRightLimit)      { this.RightLimit      = pRightLimit;      }
		public void setLineColor(      Color pLineColor)       { this.LineColor       = pLineColor;       }
	}
	
	/** Default configuration */
	static final Config DEFAULT_CONFIGURATION = new Config() {
		
		@Override public void setLineNumberDigit(int   pLineNumberDigit) { this.error(); }
		@Override public void setLineNumberMax(  int   pLineNumberMax)   { this.error(); }
		@Override public void setLeftMargin(     int   pLeftMargin)      { this.error(); }
		@Override public void setRightLimit(     int   pRightLimit)      { this.error(); }
		@Override public void setLineColor(      Color pLineColor)       { this.error(); }
		
		private void error() { throw new RuntimeException("Default configuration is immutable"); }
		
	};
	
	private Config Config = null;
	
	public void setConfig(Config pConfig) {
		this.Config = pConfig;
		// Set the position of the JTextCoomponent. so that there is enough space for it.
		this.getLineNumberBorder().setLeft(
				this.getConfiguration().getLineNumberDigit() *
						this.getFontMetrics(this.JTComponent.getFont()).stringWidth("0") + LEFT_MARGIN);
		this.repaint();
	}
	public Config getConfig(){
		return this.Config;
	}
	
	protected Config getConfiguration() { return (this.Config == null)?DEFAULT_CONFIGURATION:this.Config; }
	
	// Change highlight ------------------------------------------------------------------------------------------------

	/** Highlight the change of this code */
	static public class ChangeHighlight {

		static LineBorder BORDER_RED  = new LineBorder(Color.RED,  3);
		static LineBorder BORDER_BLUE = new LineBorder(Color.BLUE, 3);
		
		String PrevText      = null;
		Border Border_Normal = null;
		
	}
	
	ChangeHighlight TheChangeHighlight = null;
	
	/** Highlight the panel if change are made */
	public void useChangeHighlight() {
		if(this.TheChangeHighlight == null) {
			this.TheChangeHighlight          = new ChangeHighlight();
			this.TheChangeHighlight.PrevText = this.JTComponent.getText();

        	if(TheChangeHighlight != null) {
	        	Object ComponentToHighlight = this.JTComponent;
	        	Object Parent               = this.getParent();
	    		if((Parent instanceof JComponent) && (((JComponent)Parent).getParent() instanceof JScrollPane))
	    			ComponentToHighlight = ((JComponent)Parent).getParent();
	    		
	    		if(ComponentToHighlight instanceof JComponent)
	    			TheChangeHighlight.Border_Normal = ((JComponent)ComponentToHighlight).getBorder();
        	}
		}
	}
	
	public boolean isChanged() {
		if(TheChangeHighlight != null) {
			Object ComponentToHighlight = this.JTComponent;
			Object Parent               = this.getParent();
			if((Parent instanceof JComponent) && (((JComponent)Parent).getParent() instanceof JScrollPane))
				ComponentToHighlight = ((JComponent)Parent).getParent();
		    		
			if(ComponentToHighlight instanceof JComponent) {
				if(TheChangeHighlight.PrevText == null) TheChangeHighlight.PrevText = JTComponent.getText();
				if(TheChangeHighlight.PrevText.length() != JTComponent.getText().length()) return true;
				if(!TheChangeHighlight.PrevText.equals(JTComponent.getText()))             return true;
			}
		}
				
		return false;
	}
	
	public void setChangeHighlightToBlue() {
		if(this.TheChangeHighlight != null) {
			this.TheChangeHighlight.PrevText = this.JTComponent.getText();

        	Object ComponentToHighlight = this.JTComponent;
        	Object Parent               = this.getParent();
    		if((Parent instanceof JComponent) && (((JComponent)Parent).getParent() instanceof JScrollPane))
    			ComponentToHighlight = ((JComponent)Parent).getParent();
	    		
    		if(ComponentToHighlight instanceof JComponent)
    			((JComponent)ComponentToHighlight).setBorder(ChangeHighlight.BORDER_BLUE);
		}
	}
	
	public void setChangeHighlightToRed() {
		if(this.TheChangeHighlight != null) {
			this.TheChangeHighlight.PrevText = this.JTComponent.getText();

        	Object ComponentToHighlight = this.JTComponent;
        	Object Parent               = this.getParent();
    		if((Parent instanceof JComponent) && (((JComponent)Parent).getParent() instanceof JScrollPane))
    			ComponentToHighlight = ((JComponent)Parent).getParent();
	    		
    		if(ComponentToHighlight instanceof JComponent)
    			((JComponent)ComponentToHighlight).setBorder(ChangeHighlight.BORDER_RED);
		}
	}
	
	/** Change (clear the change) */
	public void save() {
		if(this.TheChangeHighlight != null) {
			this.TheChangeHighlight.PrevText = this.JTComponent.getText();

        	Object ComponentToHighlight = this.JTComponent;
        	Object Parent               = this.getParent();
    		if((Parent instanceof JComponent) && (((JComponent)Parent).getParent() instanceof JScrollPane))
    			ComponentToHighlight = ((JComponent)Parent).getParent();
	    		
    		if(ComponentToHighlight instanceof JComponent)
    			((JComponent)ComponentToHighlight).setBorder(TheChangeHighlight.Border_Normal);
		}
	}
	
	// LineConfig ------------------------------------------------------------------------------------------------------
	
	/** Line restarting and skipping */
	static public interface RestartLineConfig {
		
		public boolean isSkipFirstLine();
		public boolean isRestart(      int I, LineNumberedTextComponentPanel pSource);
		
		/** Line restarting and skipping */
		static public class Simple implements RestartLineConfig {
		
			/** Should the first (the line to be restarting) be skipped (the line number is not shown) */
			public boolean IsSkipTheFisrtLine = true;

			SortedSet<Integer> LineNumbers = null;

			/** The original line numbers to be restarted */
			public SortedSet<Integer> getLineNumbers() {
				if(this.LineNumbers == null) this.LineNumbers = new TreeSet<Integer>();
				return this.LineNumbers;
			}
			
			@Override public boolean isSkipFirstLine() {
				return this.IsSkipTheFisrtLine;
			}

			@Override public boolean isRestart(int I, LineNumberedTextComponentPanel pSource) {
				if(this.LineNumbers == null) return false;
				return this.LineNumbers.contains(I);
			}
		}
	}
	
	RestartLineConfig RestartLineConfig = null;
	
	public void              setRestartLineConfig(RestartLineConfig pConfig) { this.RestartLineConfig = pConfig; }
	public RestartLineConfig getRestartLineConfig()                          { return this.RestartLineConfig;    }
	
	protected boolean IsEnableLineNumberDrawing = true;
	public void    setEnableLineNumberDrawing(boolean isDrawLineNumber) { this.IsEnableLineNumberDrawing = isDrawLineNumber; }
	public boolean isEnableLineNumberDrawing()                          { return this.IsEnableLineNumberDrawing; }
	
	protected boolean IsEnableLineNumberBarDrawing = true;
	public boolean isEnableLineNumberBarDrawing() { return this.IsEnableLineNumberBarDrawing; }
	public void    setEnableLineNumberBarDrawing(boolean isDrawLineNumberBar) {
		this.IsEnableLineNumberBarDrawing = isDrawLineNumberBar;
		if(!isDrawLineNumberBar) this.getLineNumberBorder().setLeft(0);
		else                     this.repaint();
	}
	
	// Sizes -----------------------------------------------------------------------------------------------------------
	
	/**{@inheritDoc}*/ @Override public Dimension getPreferredSize() {
		Insets    I = this.getInsets();
		Dimension M = this.getMinimumSize();
		Dimension D = this.JTComponent.getPreferredSize();
		return new Dimension(Math.max(M.width, D.width + I.left + I.right), Math.max(M.height, D.height + I.top + I.bottom));
	}
	
	// The paint -------------------------------------------------------------------------------------------------------
	
	/** {@inheritDoc} */
	@Override public void paint(Graphics G) {
		super.paint(G);
		
		if(!this.isEnableLineNumberBarDrawing()) return;
		
		Graphics2D g = (Graphics2D)G;

		// Prepare font info and the document
		Font        CurrentFont        = this.JTComponent.getFont();
		Document    CurrentDoc         = this.JTComponent.getDocument();
		FontMetrics CurrentFontMetrics = this.getFontMetrics(CurrentFont);
		int         CFMDescent         = CurrentFontMetrics.getDescent();
		int         CFMWidth           = CurrentFontMetrics.stringWidth("0");
		int         CFMHeight          = CurrentFontMetrics.getHeight();
				
		g.setFont(this.JTComponent.getFont());
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
		
		int StartLine = 0;
		int EndLine   = CurrentDoc.getDefaultRootElement().getElementCount();
		
		Component Parent = this.getParent().getParent();
		if(Parent instanceof JScrollPane) {
			// If this is contained within a JScrollPane, we can reduce amount of the drawing by limit the start and
			//    the ending of the line number to be drawn
			
			JScrollPane JSP = (JScrollPane)Parent;
			Point       Pos = JSP.getViewport().getViewPosition();
			
			JSP.getHorizontalScrollBar().setUnitIncrement(CFMHeight);
			JSP.getVerticalScrollBar().setUnitIncrement(CFMHeight);

			int Start = this.JTComponent.viewToModel(Pos);
			int End   = this.JTComponent.viewToModel(new Point(Pos.x + JSP.getWidth(), Pos.y + JSP.getHeight()));
			
			StartLine = CurrentDoc.getDefaultRootElement().getElementIndex(Start);
			EndLine   = CurrentDoc.getDefaultRootElement().getElementIndex(End);
			
			// + 1 to include the start line and the current line (kind of strange but true)
			EndLine += 1;
		}
		
		// Ensure enough space
		int LNDigit = ((EndLine + 1) <= this.getConfiguration().getLineNumberMax())
						?this.getConfiguration().getLineNumberDigit()
						:("" + EndLine).length();
		if(this.getConfiguration().getLineNumberDigit() == 0) LNDigit = 0;
		
		// This works with the assumption that the font has constant width.
		this.getLineNumberBorder().setLeft(LNDigit * CFMWidth + LEFT_MARGIN);
		
		if(this.getConfiguration().getLineNumberDigit() != 0)  {
			
			boolean doSkip = (this.RestartLineConfig != null);
			boolean isSkip = doSkip && this.RestartLineConfig.isSkipFirstLine();

			int LN = StartLine;
			int YPos = ((StartLine + 1) * CFMHeight) - CFMDescent + 1;	// - Descent to make the them the same baseline
			for(int LineNumber = doSkip?0:StartLine; LineNumber < EndLine; LineNumber++, LN++) {
				
				if(doSkip && this.RestartLineConfig.isRestart(LineNumber, this)) LN = isSkip?-1:0;
				if(LineNumber < StartLine) continue;
				
				// Prepare the text and do right justify
				String LNStr  = (!this.isEnableLineNumberDrawing() || (LN < 0))?"":Integer.toString(LN + 1);
				if(this.isEnableLineNumberDrawing()) while(LNStr.length() < LNDigit) LNStr = " " + LNStr;
				// Draw the text
				g.drawString(LNStr, 2, YPos);
				
				// Update the position
				YPos += CFMHeight;
			}
			
			// Draw the line highlight
			//Point CPos = this.JTComponent.getCaret().getMagicCaretPosition();
		}

		// Draw the left and the right lines
		if(this.getConfiguration().getRightLimit() != 0) {
			Color C = g.getColor();
			try {
				Rectangle Rect = this.JTComponent.getVisibleRect();

				// Draw the right line
				int Y = (int)Rect.getY();
				int H = (int)Rect.getHeight();
				int Left = this.JTComponent.getX() - 3;	// - 3 to give some space for the line
				
				// Draw two pixel of background color (of this.JTComponent) to create an illusion that there is a space
				//    between JTComponent and the line number
				g.setColor(this.JTComponent.getBackground());
				g.drawLine(Left + 1, Y, Left + 1, Y + H);
				
				g.setColor(this.getConfiguration().getLineColor());
				g.drawLine(Left, Y, Left, Y + H);
				// Draw the right line
				Left += this.getConfiguration().getRightLimit() * CFMWidth + 3;	// + 3 to compensate the -3 above
				g.drawLine(Left, Y, Left, Y + H);

			} finally {
				g.setColor(C);
			}
		}
	}

	static public void main(String[] args) {
		JFrame f = new JFrame("UsingLineNumberBorder");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final LineNumberedTextComponentPanel JP = new LineNumberedTextComponentPanel();
		JP.setVisible(true);
		JP.useChangeHighlight();

		JP.getTextComponent().addKeyListener(new java.awt.event.KeyAdapter() {
			@Override public void keyReleased(KeyEvent evt) {
				if(evt.isControlDown() && (evt.getKeyCode() == KeyEvent.VK_ENTER)) {
					JP.save();				
				}
			}
        });
		
		JScrollPane SP = new JScrollPane(JP);
		SP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		SP.setVerticalScrollBarPolicy(  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		SP.getHorizontalScrollBar().setUnitIncrement(10);
		SP.getVerticalScrollBar().setUnitIncrement(10);
		f.getContentPane().add(SP);
		f.setSize(400, 300);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}

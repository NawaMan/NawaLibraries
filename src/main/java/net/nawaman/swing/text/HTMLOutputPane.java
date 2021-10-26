package net.nawaman.swing.text;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.HTML;

import net.nawaman.io.TypeFileFilter;
import net.nawaman.swing.Clipboard;
import net.nawaman.swing.FixedPanel;
import net.nawaman.swing.TextWrapPanel;
import net.nawaman.swing.text.HTMLOutputComponent.HTMLOutputDocument;
import net.nawaman.util.UString;

// Implementation Note:
// Simulating tab using JPanel is inspired by the code in forum. In the post, a button is created when custom tag 'button'
//    is used.
// URL: http://www.velocityreviews.com/forums/t135107-how-to-use-custom-tag-in-htmldocument-in-swings-jtextpane-.html
// 
// To simulate tab, a JPanel is created when a custom tag 'tab' is use. this JPanel is also customized to adjust its
//    size based on its left position.   

// DEBUGGING NOTE!!!!!!!!
// Use System.out and System.err carefully!!!
// One of the use of HTMLOutputPane is to simulate console in Swing and that means replacing System.out
// and System.in with PrintStream of a HTMLOutputPane. In such case, if write(...) or flush(...) writes to
// System.out or System.err, recursion will occurs.

/**
 * HTMLOutputPane is a panel that allows the end developer to use it to show output in HTML formated text as well as
 *    HTML friendly plain-text. It is only ready-only as it will be much more complicated to do the writable one.
 *    HTMLOutputPane allows the output via print stream which may or may not be pre-formated. This class is most
 *    suitable to be used as simulated console output scenario.   
 *
 * NOTE on HTML Styling:
 *   1. When color of the font is to changed, use &lt;font color='...'%gt;...&lt;/font%gt; instead of
 *          &lt;span ...%gt;...&lt;/span%gt;. Java is capable of displaying the type with span but the style will not be
 *          copied to the clipboard.
 *   2. It is very recommended not to change the font family often as multiple width text does not look good with the
 *          current way of tab stopping. 
 *
 * Known Issue:
 *   1. The line that has only tab character (which will be replaced with a tab entry) is not displayed. Still don't
 *          know why and how to solve.
 *   
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 **/
public class HTMLOutputPane extends FixedPanel implements Appendable {
	
	private static final long serialVersionUID = 9099621213342739672L;

	static public HTML.Tag Tab = new HTML.UnknownTag(HTMLOutputPane.TabTagName);
	
	/** The default font */
	static public final Font DefaultFont =
								//new Font("Bitstream Vera Sans Mono", 0, 15);
								new Font("Monospaced", 0, 15);
	
	/** The tag name for the tag */
	static public final String TabTagName = "tab";

	/** The tag for the tag */
	static public final String TabTag = String.format("<%s/>", TabTagName);
	
	/** The default flush period in millisecond */
	static public final int DefaultFlushPeriod = 200;
	
	/** The Minimum value of the flush period */
	static public final int MinimumFlushPeriod = 10;
	
	/** The Maximum value of the flush period */
	static public final int MaximumFlushPeriod = 10000;
	
	/** System output */
	static public final java.io.PrintStream SystemOUT = System.out;
	/** System error */
	static public final java.io.PrintStream SystemERR = System.err;
	
	static final String[] TypeFileExt_Text  = new String[] { ".txt" };
	static final String   TypeFileDesc_Text = "Text file (*.txt)";
	
	static final String[] TypeFileExt_HTML  = new String[] { ".html" };
	static final String   TypeFileDesc_HTML = "HTML file (*.html)";
	
	static final String[] TypeFileExt_Image  = new String[] { ".png" };
	static final String   TypeFileDesc_Image = "Image file (*.png)";
	
	/** Constructs an output pane */
	public HTMLOutputPane() {
		final HTMLOutputPane TheOutputPane = this;
		
		// Create the print stream
		this.ThePrintStream = new HTMLPrintStream(this);
		
		// Creates the component --------------------------------------------------------
		this.TheTextComponent = new HTMLOutputComponent(this);
		this.TheTextWrapPanel = new TextWrapPanel(  this.TheTextComponent);
		this.TheTextDocument  = (HTMLOutputDocument)this.TheTextComponent.getDocument();
		
		// Turn internal thread on/off
		this.addAncestorListener(new AncestorListener() {
		    public void ancestorMoved(AncestorEvent event) {}
		    public void ancestorAdded(AncestorEvent event) {
		    	// Start force redraw and flushing threads
		    	TheOutputPane .restartForceRedrawThread();
		    	ThePrintStream.restartUpdateThread();
		    }
		    public void ancestorRemoved(AncestorEvent event) {
		    	// Stop redraw and flushing threads
		    	TheOutputPane .stopForceRedrawThread();
		    	ThePrintStream.stopUpdateThread();
		    }
		});
		
		// Configure text component -----------------------------------------------------
		
		// Scroll all to the left when home is pressed
        this.TheTextComponent.addKeyListener(new KeyAdapter() {
        	/**{@inheritDoc}*/ @Override
        	public void keyReleased(KeyEvent evt) {
        		if(evt.getKeyCode() == KeyEvent.VK_HOME)
        			TheOutputPane.TheTextWrapPanel.getHorizontalScrollBar().setValue(0);
            }
        });
        // Add mouse wheel support.
        this.TheTextComponent.addMouseWheelListener(new MouseWheelListener() {
			/**{@inheritDoc}*/ @Override
		    public void mouseWheelMoved(MouseWheelEvent e) {
				int F = 150*(e.isControlDown()?3:1)*(e.isShiftDown()?3:1);
				int V = TheOutputPane.TheTextWrapPanel.getVerticalScrollBar().getValue();
				TheOutputPane.TheTextWrapPanel.getVerticalScrollBar().setValue(V + e.getWheelRotation()*F);
			}
		});
        
		// Configure the ScrollPane -----------------------------------------------------
		this.TheTextWrapPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.TheTextWrapPanel.getHorizontalScrollBar().setUnitIncrement(10);
		this.TheTextWrapPanel.getVerticalScrollBar()  .setUnitIncrement(10);
		AdjustmentListener AL = new AdjustmentListener() {
			/**{@inheritDoc}*/ @Override
		    public void adjustmentValueChanged(AdjustmentEvent e) {
				// Force redraw (workaround for the drawing problem)
				TheOutputPane.ToForceRedraw = true;
			}
		};
		this.TheTextWrapPanel.getHorizontalScrollBar().addAdjustmentListener(AL);
		this.TheTextWrapPanel.getVerticalScrollBar().addAdjustmentListener(AL);

		// Add to this ------------------------------------------------------------------
		super.setLayout(new BorderLayout());
		this.add(this.TheTextWrapPanel, BorderLayout.CENTER);
		this.setFixed(true);
		
		// Pop-up menu ------------------------------------------------------------------
		this.ThePopupManu = new JPopupMenu();
		
		// Copy as plain text -----------------------------------------------------------
		final JMenuItem  MI_CopyAsPlainText = new JMenuItem();
		MI_CopyAsPlainText.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		MI_CopyAsPlainText.setMnemonic('C');
		MI_CopyAsPlainText.setText("Copy                 ");
		MI_CopyAsPlainText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				TheOutputPane.copyAsPlainText();
			}
		});
		this.ThePopupManu.add(MI_CopyAsPlainText);
		
		// Copy as HTML -----------------------------------------------------------------
		final JMenuItem  MI_CopyAsHTML = new JMenuItem();
		MI_CopyAsHTML.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
		MI_CopyAsHTML.setMnemonic('H');
		MI_CopyAsHTML.setText("Copy as HTML         ");
		MI_CopyAsHTML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				TheOutputPane.copyAsHTML();
			}
		});
		this.ThePopupManu.add(MI_CopyAsHTML);
		
		// Add the  --------------------------------------------------------------------- 
		this.ThePopupManu.addSeparator();
		
		// Save as PlainText ------------------------------------------------------------
		final JMenuItem  MI_SaveAsPlainText = new JMenuItem();
		MI_SaveAsPlainText.setText("Save as PlainText    ");
		MI_SaveAsPlainText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//Create a file chooser
				final JFileChooser FC = new JFileChooser();
				FC.addChoosableFileFilter(new TypeFileFilter(TypeFileExt_Text, TypeFileDesc_Text));
				if(FC.showSaveDialog(TheOutputPane) == JFileChooser.CANCEL_OPTION) return;

				String TextKind = "the text";
				String TheText  = null;
				try {
					if(TheOutputPane.IsPreviouslySelected) {
						// There is a selection so save as selection
						TextKind = "the selection";
						TheText  = TheOutputPane.getSelectionPlainText();
					} else {
						TextKind = "the text";
						TheText  = TheOutputPane.getPlainText();
					}
					
					UString.saveTextToFile(FC.getSelectedFile(), TheText);
				} catch (IOException E) {
					String Message = "There is a problem saving " + TextKind + " as plain text: \n" + E.toString();
					JOptionPane.showMessageDialog(
							TheOutputPane,
							Message, "Problem savig " + TextKind,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.ThePopupManu.add(MI_SaveAsPlainText);
		
		// Save as HTML -----------------------------------------------------------------
		final JMenuItem  MI_SaveAsHTML = new JMenuItem();
		MI_SaveAsHTML.setText("Save as HTML       ");
		MI_SaveAsHTML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//Create a file chooser
				final JFileChooser FC = new JFileChooser();
				FC.addChoosableFileFilter(new TypeFileFilter(TypeFileExt_HTML, TypeFileDesc_HTML));
				if(FC.showSaveDialog(TheOutputPane) == JFileChooser.CANCEL_OPTION) return;

				String TextKind = "the text";
				String TheText  = null;
				try {
					if(TheOutputPane.IsPreviouslySelected) {
						// There is a selection so save as selection
						TextKind = "the selection";
						TheText  = TheOutputPane.getSelectionHTML();
					} else {
						TextKind = "the text";
						TheText  = TheOutputPane.getHTML();
					}
					
					UString.saveTextToFile(FC.getSelectedFile(), TheText);
				} catch (IOException E) {
					String Message = "There is a problem saving " + TextKind + " as html: \n" + E.toString();
					JOptionPane.showMessageDialog(
							TheOutputPane,
							Message, "Problem savig " + TextKind,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.ThePopupManu.add(MI_SaveAsHTML);
		
		// Save as Image ------------------------------------------------------------
		final JMenuItem  MI_SaveAsImage = new JMenuItem();
		MI_SaveAsImage.setText("Save as Image        ");
		MI_SaveAsImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//Create a file chooser
				final JFileChooser FC = new JFileChooser();
				FC.addChoosableFileFilter(new TypeFileFilter(TypeFileExt_Image, TypeFileDesc_Image));
				if(FC.showSaveDialog(TheOutputPane) == JFileChooser.CANCEL_OPTION) return;

				String TextKind = "the text";
				try {
					if(TheOutputPane.IsPreviouslySelected) {
						// There is a selection so save as selection
						TextKind = "the selection";
						TheOutputPane.saveSelectionAsImage(FC.getSelectedFile());
					} else {
						TextKind = "the text";
						TheOutputPane.saveTextAsImage(FC.getSelectedFile());
					}
				} catch (IOException E) {
					String Message = "There is a problem saving " + TextKind + " as image: \n" + E.toString();
					JOptionPane.showMessageDialog(
							TheOutputPane,
							Message, "Problem savig " + TextKind,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.ThePopupManu.add(MI_SaveAsImage);
		
		
		// Enable the menu
        this.TheTextComponent.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent evt) {
            	if(TheOutputPane.IsPopupManuEnabled && (TheOutputPane.ThePopupManu != null) && evt.isPopupTrigger()) {
            		TheOutputPane.ThePopupManu.show(evt.getComponent(), evt.getX(), evt.getY());
            	}
            }
        });
        
		// Fix the problem of having caret moved out of the document length
		this.getTextComponent().addCaretListener(new CaretListener() {
			/** {@inheritDoc} */ @Override 
		    public void caretUpdate(CaretEvent e) {
				if(e.getDot() == e.getMark()) {
					if(!IsPreviouslySelected) return;
					MI_SaveAsPlainText.setText("Save as Text");
					MI_SaveAsHTML     .setText("Save as HTML");
					MI_SaveAsImage    .setText("Save as Image");
					IsPreviouslySelected = false;
					return;
				}

				if(IsPreviouslySelected) return;
				MI_SaveAsImage    .setText("Save selection as Image");
				MI_SaveAsHTML     .setText("Save selection as HTML");
				MI_SaveAsPlainText.setText("Save selection as Text");
				IsPreviouslySelected = true;
			}
		});
		
		// Default setting --------------------------------------------------------------
		this.setFont(HTMLOutputPane.DefaultFont);
		this.setTextWrap(false);
		this.setTabSize(4);
		this.setPopupMenuEnabled(true);

		// Start the timer
		this.restartForceRedrawThread();
		
		// Draw
        this.repaint();
	}
	
	// Sub-Components --------------------------------------------------------------------------------------- 

	private TextWrapPanel       TheTextWrapPanel;
	private HTMLOutputComponent TheTextComponent;
	private HTMLOutputDocument  TheTextDocument;

	private boolean    IsPopupManuEnabled = true;
	private JPopupMenu ThePopupManu       = null;
	
	private boolean IsPreviouslySelected = false;
	
	/** Returns the text component that this is holding. */
	public HTMLOutputComponent getTextComponent() {
		return this.TheTextComponent;
	}
	
	/** Returns the text document that this is holding. */
	protected Document getTextDocument() {
		return this.TheTextDocument;
	}

	/** Returns the scroll panel */
    public JScrollPane getScrollPane() {
    	return this.TheTextWrapPanel;
    }
	/** Returns the horizontal scroll bar */
    public JScrollBar getHorizontalScrollBar() {
    	return this.TheTextWrapPanel.getHorizontalScrollBar();
    }
	/** Returns the vertical scroll bar */
    public JScrollBar getVerticalScrollBar() {
    	return this.TheTextWrapPanel.getVerticalScrollBar();
    }
    
    /** Changes Enable/Disable flag of the pop-up menu */
    protected void setPopupMenuEnabled(boolean pToEnabled) {
    	this.IsPopupManuEnabled = pToEnabled;
    }
    
    /** Returns the pop-up menu */
    protected JPopupMenu getPopupMenu() {
    	return this.ThePopupManu;
    }
    
    // Input and Output  ------------------------------------------------------------------------------------

	private HTMLPrintStream ThePrintStream;
	
	/** Set the Text value of the output pane */
	public void setText(String pText) {
		this.clearText();
		this.print(pText);
	}
	/** Clears the text buffer */
	public void clearText() {
		this.TheTextComponent.setTextRaw("");
	}

	/** Returns the source HTML of the component */
	public String getText() {
		try { return this.getText(0, this.getTextDocument().getLength()); }
		catch (BadLocationException e) { return ""; }
	}
	/** Returns the source HTML of the component */
	public String getText(int Offset, int Length) throws BadLocationException {
		return this.TheTextComponent.getHTML(Offset, Length);
	}
	/** Returns the plain text of the component text */
	public String getSelectionText() {
		try {
			int S = this.TheTextComponent.getSelectionStart();
			int E = this.TheTextComponent.getSelectionEnd();
			if(S <= 0) S = 1;
			return this.getText(S - 1, E - S + 1);
		} catch (Exception E) {
			return "";
		}
	}
	
	/** Returns the plain text of the component text */
	public String getPlainText() {
		try { return HTMLOutputComponent.getPlainTextFromHTMLDocument(this.TheTextDocument); }
		catch(Exception E) { throw new RuntimeException(E); }
	}
	/** Returns the plain text of the component text */
	public String getPlainText(int Offset, int Length) throws BadLocationException {
		return HTMLOutputComponent.getPlainTextFromHTMLDocument(this.TheTextDocument, Offset, Length);
	}
	/** Returns the plain text of the component text */
	public String getSelectionPlainText() {
		try {
			int S = this.TheTextComponent.getSelectionStart();
			int E = this.TheTextComponent.getSelectionEnd();
			if(S <= 0) S = 1;
			return this.getPlainText(S - 1, E - S + 1);
		} catch (Exception E) {
			return "";
		}
	}

	/** Returns the HTML code of the document */
	public String getHTML() {
		try { return this.getHTML(0, this.getTextDocument().getLength()); }
		catch (BadLocationException e) { return ""; }
	}
	/** Returns the plain text of the component text */
	public String getHTML(int Offset, int Length)  throws BadLocationException{
		return this.TheTextComponent.getHTML(Offset, Length);
	}
	/** Returns the plain text of the component text */
	public String getSelectionHTML() {
		try {
			int S = this.TheTextComponent.getSelectionStart();
			int E = this.TheTextComponent.getSelectionEnd();
			if(S <= 0) S = 1;
			return this.getHTML(S - 1, E - S + 1);
		} catch (Exception E) {
			return "";
		}
	}
	
	// Save as image --------------------------------------------------------------------
	
	/** Save the text as an image */
	public void saveTextAsImage(File F) throws IOException {
		int OrgS = this.TheTextComponent.getSelectionStart();
		int OrgE = this.TheTextComponent.getSelectionEnd();
		try {
			this.TheTextComponent.select(-1, -1);

			int W = this.TheTextComponent.getPreferredSize().width;
			int H = this.TheTextComponent.getPreferredSize().height;
			
			BufferedImage BI = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
			Graphics2D    G2 = (Graphics2D)BI.getGraphics();
			this.TheTextComponent.paint(G2);
			ImageIO.write(BI, "png", F);
			
		} catch (IOException E) {
			throw E;
		} catch (Exception E) {
			
		} finally {
			this.TheTextComponent.select(OrgS, OrgE);
		}
	}
	
	/** Save the text as an image */
	public void saveTextAsImage(int Offset, int Length, File F) throws IOException {
		int OrgS = this.TheTextComponent.getSelectionStart();
		int OrgE = this.TheTextComponent.getSelectionEnd();
		try {
			this.TheTextComponent.select(-1, -1);
			
			int S = Offset;
			int E = Offset + Length;
			
			var R0 = TheTextComponent.modelToView2D(S);
			var R1 = TheTextComponent.modelToView2D(E);

			int W = this.TheTextComponent.getPreferredSize().width;
			int H = (int)(R1.getY() + R1.getHeight() - R0.getY());
			
			BufferedImage BI = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
			Graphics2D    G2 = (Graphics2D)BI.getGraphics();
			G2.translate(0, -R0.getY());
			this.TheTextComponent.paint(G2);
			ImageIO.write(BI, "png", F);
			
		} catch (IOException E) {
			throw E;
		} catch (Exception E) {
			
		} finally {
			this.TheTextComponent.select(OrgS, OrgE);
		}
	}
	
	/** Save the selected text as an image */
	public void saveSelectionAsImage(File F) throws IOException {
		int OrgS = this.TheTextComponent.getSelectionStart();
		int OrgE = this.TheTextComponent.getSelectionEnd();
		try {
			this.TheTextComponent.select(-1, -1);
			
			int S = Math.min(OrgS, OrgE);
			int E = Math.max(OrgS, OrgE);
			
			var R0 = TheTextComponent.modelToView2D(S);
			var R1 = TheTextComponent.modelToView2D(E);

			int W = this.TheTextComponent.getPreferredSize().width;
			int H = (int)(R1.getY() + R1.getHeight() - R0.getY());
			
			BufferedImage BI = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
			Graphics2D    G2 = (Graphics2D)BI.getGraphics();
			G2.translate(0, -R0.getY());
			this.TheTextComponent.paint(G2);
			ImageIO.write(BI, "png", F);
			
		} catch (IOException E) {
			throw E;
		} catch (Exception E) {
			
		} finally {
			this.TheTextComponent.select(OrgS, OrgE);
		}
	}
	
	// Default PrintStream ------------------------------------------------------------------------

	private PrintStream TheSimpleFormatterPrintStream;

	/**
	 * Creates and returns a new default simple format print string.
	 * 
	 * For customization, only to be called by HTMLOutputPane.getSimpleFormatterPrintStream().
	 **/
	protected PrintStream newDefaultSimpleFormatterPrintStream() {
		return this.newSimpleFormatterPrintStream();
	}
	
	/** Returns print stream to output to this text component */
	public java.io.PrintStream getPlainTextPrintStream() {
		return this.ThePrintStream;
	}
	/** Returns print stream to output to this text component */
	public PrintStream getSimpleFormatterPrintStream() {
		if(this.TheSimpleFormatterPrintStream == null)
			this.TheSimpleFormatterPrintStream = this.newDefaultSimpleFormatterPrintStream();
		return this.TheSimpleFormatterPrintStream;
	}
	/** Returns print stream to output to this text component */
	public PrintStream getPrintStream() {
		return this.getSimpleFormatterPrintStream();
	}

	/** Write the specified byte to this stream. */
	final public void write(int x) {
		this.getPrintStream().write(x);
	}	
	/** Write pLength bytes from the specified byte array starting at offset off to this stream. */ 
	final public void write(byte[] pBytes, int pOffset, int pLength) {
		this.getPrintStream().write(pBytes, pOffset, pLength);
	}
	
	/** Print a boolean value. */
	final public void print(boolean b) {
		this.getPrintStream().print(b);
	}
	/** Print a character. */
	final public void print(char c) {
		this.getPrintStream().print(c);
	}
	/** Print an integer. */
	final public void print(int i) {
		this.getPrintStream().print(i);
	}
	/** Print a long integer. */
	final public void print(long l) {
		this.getPrintStream().print(l);
	}
	/** Print a floating-point number */
	final public void print(float f) {
		this.getPrintStream().print(f);
	}
	/** Print a double-precision floating-point number. */
	final public void print(double d) {
		this.getPrintStream().print(d);
	}
	/** Print an array of characters. */
	final public void print(char s[]) {
		this.getPrintStream().print(s);
	}
	/** Print a string. */
	final public void print(String s) {
		this.getPrintStream().print(s);
	}
	/** Print an object. */
	final public void print(Object obj) {
		this.getPrintStream().print(obj);
	}

	/** Terminate the current line by writing the line separator string. */
	final public void println() {
		this.getPrintStream().println();
	}
	/** Print a boolean value and then terminate the line. */
	final public void println(boolean b) {
		this.getPrintStream().println(b);
	}
	/** Print a character and then terminate the line. */
	final public void println(char c) {
		this.getPrintStream().println(c);
	}
	/** Print an integer and then terminate the line. */
	final public void println(int i) {
		this.getPrintStream().println(i);
	}
	/** Print a long integer and then terminate the line. */
	final public void println(long l) {
		this.getPrintStream().println(l);
	}
	/** Print a floating-point number and then terminate the line. */
	final public void println(float f) {
		this.getPrintStream().println(f);
	}
	/** Print a double-precision floating-point number and then terminate the line. */
	final public void println(double d) {
		this.getPrintStream().println(d);
	}
	/** Print an array of characters and then terminate the line */
	final public void println(char s[]) {
		this.getPrintStream().println(s);
	}
	/** Print a string and then terminate the line. */
	final public void println(String s) {
		this.getPrintStream().println(s);
	}
	/** Print an object and then terminate the line. */
	final public void println(Object obj) {
		this.getPrintStream().println(obj);
	}

    /** Appends the specified character sequence to this <tt>Appendable</tt>. */
    public java.io.PrintStream append(CharSequence csq) throws IOException {
    	return this.getPrintStream().append(csq);
    }

    /** Appends a subsequence of the specified character sequence to this <tt>Appendable</tt>. */
    public java.io.PrintStream append(CharSequence csq, int start, int end) throws IOException {
    	return this.getPrintStream().append(csq, start, end);
    }

    /** Appends the specified character to this <tt>Appendable</tt>. */
    public java.io.PrintStream append(char c) throws IOException {
    	return this.getPrintStream().append(c);
    }
	
	// Others ----------------------------------------------------------------------------------------------------------
	
	/** Flush the stream and check its error state. */
	final public boolean checkError()  {
		return this.getPrintStream().checkError();
	}
	/** Close the stream. */
	final public void close()  {
		this.getPrintStream().close();
	}
	/**  Flush the stream. */
	final public void flush() {
		this.getPrintStream().flush();
	}
	
	// Format PrintStream -------------------------------------------------------------------------

	/** Returns print stream to output to this text component */
	public PrintStream newSimpleFormatterPrintStream() {
		return this.newSimpleFormatterPrintStream((java.io.PrintStream)null);
	}
	/** Returns print stream to output to this text component */
	public PrintStream newSimpleFormatterPrintStream(Formatter pFormatter) {
		return this.newSimpleFormatterPrintStream((java.io.PrintStream)null, pFormatter);
	}
	/** Returns print stream to output to this text component */
	public PrintStream newSimpleFormatterPrintStream(String pPattern) {
		return this.newSimpleFormatterPrintStream((java.io.PrintStream)null, pPattern);
	}
	/** Returns print stream to output to this text component */
	public PrintStream newSimpleFormatterPrintStream(String pPattern, boolean pIsCoverNewLine) {
		return this.newSimpleFormatterPrintStream((java.io.PrintStream)null, pPattern, pIsCoverNewLine);
	}

	/** Returns print stream to output to this text component */
	public PrintStream newSimpleFormatterPrintStream(java.io.PrintStream pPS) {
		return new PrintStream(this, pPS);
	}
	/** Returns print stream to output to this text component */
	public PrintStream newSimpleFormatterPrintStream(java.io.PrintStream pPS, Formatter pFormatter) {
		PrintStream PSS = new PrintStream(this, pPS);
		PSS.TheFormatter = pFormatter;
		return PSS;
	}
	/** Returns print stream to output to this text component */
	public PrintStream newSimpleFormatterPrintStream(java.io.PrintStream pPS, String pPattern) {
		PrintStream PSS = new PrintStream(this, pPS);
		PSS.setPattern(pPattern);
		return PSS;
	}
	/** Returns print stream to output to this text component */
	public PrintStream newSimpleFormatterPrintStream(java.io.PrintStream pPS, String pPattern, boolean pIsCoverNewLine) {
		PrintStream PSS = new PrintStream(this, pPS);
		PSS.setPattern(pPattern, pIsCoverNewLine);
		return PSS;
	}
	
	// Utility Methods ----------------------------------------------------------------------------
	
	/** Checks if this format is to cover across paragraphs */
	public boolean isCoverNewLine() {
		if(this.TheSimpleFormatterPrintStream == null) return false;
		return this.getSimpleFormatterPrintStream().isCoverNewLine();
	}
	
	/** Changes the format pattern */
	public HTMLOutputPane setPattern(String pPattern) {
		this.getSimpleFormatterPrintStream().setPattern(pPattern);
		return this;
	}
	/** Changes the pattern */
	public HTMLOutputPane setPattern(String pPattern, boolean pIsToCoverNewLine) {
		this.getSimpleFormatterPrintStream().setPattern(pPattern, pIsToCoverNewLine);
		return this;
	}
	
	/** Returns the pattern */
	public String getPattern() {
		return this.getSimpleFormatterPrintStream().getPattern();
	}
	
	/** Appends the format */
	public HTMLOutputPane addPattern(String pPattern) {
		this.getSimpleFormatterPrintStream().addPattern(pPattern);
		return this;
	}
	
	/** Changes the format pattern */
	public HTMLOutputPane clearPattern() {
		this.getSimpleFormatterPrintStream().clearPattern();
		return this;
	}
	
	// Pre-define -----------------------------------------------------------------------------
	
	/** Add bold font style to the formatter */
	public HTMLOutputPane addBold() {
		this.getSimpleFormatterPrintStream().addBold();
		return this;
	}
	/** Add italic font style to the formatter */
	public HTMLOutputPane addItalic() {
		this.getSimpleFormatterPrintStream().addItalic();
		return this;
	}
	/** Add italic font style to the formatter */
	public HTMLOutputPane addUnderline() {
		this.getSimpleFormatterPrintStream().addUnderline();
		return this;
	}
	/** Add pre-formatted style to the formatter */
	public HTMLOutputPane addPreformatted() {
		this.getSimpleFormatterPrintStream().addPreformatted();
		return this;
	}
	/** Add pre-formatted style to the formatter */
	public HTMLOutputPane changeColor(Color C) {
		this.getSimpleFormatterPrintStream().changeColor(C);
		return this;
	}
	/** Add relative font size */
	public HTMLOutputPane changeAbsoluteFontSize(int pSize) {
		this.getSimpleFormatterPrintStream().changeAbsoluteFontSize(pSize);
		return this;
	}
	/** Add relative font size e.g. +1 or -1 */
	public HTMLOutputPane changeRelativeFontSize(int pSize) {
		this.getSimpleFormatterPrintStream().changeRelativeFontSize(pSize);
		return this;
	}
    
    // Customized -------------------------------------------------------------------------------------------
    
	/**{@inheritDoc}*/ @Override
	final public void setLayout(LayoutManager pLayout) {
		if(!(pLayout instanceof BorderLayout)) return;
		super.setLayout(pLayout);
	}	
	
	// Setting ----------------------------------------------------------------------------------------------
	
	// Visual -------------------------------------------------------------------------------------

	private int TabSize = 4;
	private int TabWidth;
	
	/**{@inheritDoc}*/ @Override
	public void setFont(Font F) {
		if(this.TheTextComponent == null) return;
		
		if(F == null) F = HTMLOutputPane.DefaultFont;
		
		// Change the font of the component
		this.TheTextComponent.setFont(F);

		// Change the font of the document
		this.TheTextDocument.getStyleSheet().addRule(String.format(
				"body { font-family: %s; font-size: %dpt; }", F.getName(), F.getSize()
			));

		// Update the tab width
		this.updateTabWidth();
	}
	
	/** Set the text wrap behavior */
	public void setTextWrap(boolean pIsToWrap) {
		this.TheTextWrapPanel.setTextWrap(pIsToWrap);
	}
	
	/** Checks if text wrap is enabled */
	public boolean isTextWrapped() {
		return this.TheTextWrapPanel.isTextWrapped();
	}
	
	/** Updates the tab width (the product of TabSize and FontWidth) */
	final protected void updateTabWidth() {
		JTextComponent JTC = this.TheTextComponent;
		this.TabWidth  = this.TabSize * JTC.getFontMetrics(JTC.getFont()).stringWidth("0");
	}
	
	/**
	 * Set the tab size (the number of character used to representing a tab)
	 *
	 * The minimum tab size is 1;
	 **/
	public void setTabSize(int pTabSize) {
		if(pTabSize < 1) pTabSize = 1;
		this.TabSize = pTabSize;
		
		this.updateTabWidth();
	}
	
	/** Returns the tab size of this component */
	public int getTabSize() {
		return this.TabSize;
	}
	
	/** Returns the tab width of the tab element to be display */
	public int getTabWidth() {
		return this.TabWidth;
	}
	
	// Flushing -----------------------------------------------------------------------------------
	
	private int FlushPeriod = HTMLOutputPane.DefaultFlushPeriod;
	
	/**
	 * Change the flush period.
	 * The flush period dictate how long before the appending text will be added into the text document. The lower the
	 *   number, the more often the flushing occurs. Changing flush period affects the performance.   
	 * 
	 *  @param pFlushPeriod The flush value. This value will clip by the minimum possible value
	 *                          <code>HTMLOutputPane.MinimumFlushPeriod</code> (10 milliseconds) and the maximum
	 *                          possible value <code>HTMLOutputPane.MaximumFlushPeriod</code> (10 seconds).
	 **/
	protected void setFlushPeriod(int pFlushPeriod) {
		this.FlushPeriod = pFlushPeriod;
		if(this.FlushPeriod < HTMLOutputPane.MinimumFlushPeriod) 
			this.FlushPeriod = HTMLOutputPane.MinimumFlushPeriod;

		if(this.FlushPeriod > HTMLOutputPane.MaximumFlushPeriod)
			this.FlushPeriod = HTMLOutputPane.MaximumFlushPeriod;
	}
	
	/** Returns the flush period */
	public int getFlushPeriod() {
		return this.FlushPeriod;
	}
	
	// Ensure update (by forcing the redraw) ----------------------------------------------------------------
	// When a TabPanel change its size, sometime this effects other TabPanel position and size. This mechanism allows
	//    the TabPanel to flag if it has changed its size. And every a curtain period of time, the component may need to
	//    be repainted.
	
	volatile         boolean ToForceRedraw     = false;
	volatile private boolean isForceRedrawStop = false;
	         private Thread  Timer             = null;
	
	/** Restarts the force redraw thread */
	private void restartForceRedrawThread() {
		// Ensure that it was previously stopped
		this.stopForceRedrawThread();
		while(!this.isForceRedrawStop);
		
		// Create a new one
		this.Timer = new Thread() {
			/**{@inheritDoc}*/ @Override
			public void run() {
				isForceRedrawStop = false;
				while(true) {
					try {
						Thread.sleep(1000);
						if(isForceRedrawStop) break;
					} catch(InterruptedException IE) { break; }
					if(ToForceRedraw) TheTextComponent.repaint();
				}
				isForceRedrawStop = false;
			}
		};
		
		// Start
		this.Timer.start();
	}
	/** Stop the update thread */
	private void stopForceRedrawThread() {
		this.isForceRedrawStop = true;
	}
	
	// Clipboard operations ---------------------------------------------------------------------------------

	/** Invoked when a key has been released. */
	protected void processKeyPressed(KeyEvent Evt) {		
	}
	/** Invoked when a key has been released. */
	protected void processKeyReleased(KeyEvent Evt) {
		// Adjust the clip-board (to flatten the HTML)
		if(!Evt.isAltDown() && (Evt.getKeyCode() == 'C') && Evt.isControlDown()) {
			if(Evt.isShiftDown()) this.copyAsHTML();
			else                  this.copyAsPlainText();
		}
    }
	
	/** Performs the copy operation from the selected text to the clipboard. */
	final protected void doCopy() {
		if(this.isCopiedAsHTML)
			this.TheTextComponent.copy();
		else
			// Get the select text as plain text
			// This will call a special function in Util
			Clipboard.copy(this.getSelectionPlainText());
	}
	
	protected boolean isCopiedAsHTML = true;
	
	/** Set if the copy is to be done as HTML or a plain text */
	public void setCopiedAsHTML(boolean pIsCopiedAsHTML) {
		this.isCopiedAsHTML = pIsCopiedAsHTML;
	}
	/** Set if the copy is to be done as HTML and not a plain text */
	public void toBeCopiedAsHTML() {
		this.isCopiedAsHTML = true;
	}
	/** Set if the copy is to be done as plain text and not a HTML */
	public void toBeCopiedAsPlainText() {
		this.isCopiedAsHTML = false;
	}
	/** Set if the copy is to be done as HTML */
	public boolean isCopiedAsHTML() {
		return this.isCopiedAsHTML;
	}

	/** Copies the selected text as plain text */
	public void copyAsPlainText() {
		boolean savedFlag = this.isCopiedAsHTML;
		this.toBeCopiedAsPlainText();
		this.doCopy();
		this.isCopiedAsHTML = savedFlag;
	}
	/** Copies the selected text as HTML */
	public void copyAsHTML() {
		boolean savedFlag = this.isCopiedAsHTML;
		this.toBeCopiedAsHTML();
		this.doCopy();
		this.isCopiedAsHTML = savedFlag;
	}

	// Helping classes -------------------------------------------------------------------------------------------------

	
	// Repeat this so referencing it from other project is more direct
	/** Print stream to be used to out text to HTMLOutputPane */
	static public class PrintStream extends FormatterPrintStream {

		public PrintStream(HTMLOutputPane pHOP) {
			super(pHOP);
		}
		public PrintStream(HTMLOutputPane pHOP, OutputStream pOut) {
			super(pHOP, pOut);
		}
		public PrintStream(HTMLOutputPane pHOP, OutputStream pOut, boolean pIsAutoFlush) {
			super(pHOP, pOut, pIsAutoFlush);
		}
		public PrintStream(HTMLOutputPane pHOP, OutputStream pOut, boolean pIsAutoFlush,
				java.io.PrintStream ... pTargets) {
			super(pHOP, pOut, pIsAutoFlush, pTargets);
		}
		
		public PrintStream(HTMLOutputPane pHOP, Formatter pFormat) {
			super(pHOP, pFormat);
		}
		public PrintStream(HTMLOutputPane pHOP, Formatter pFormat, OutputStream pOut) {
			super(pHOP, pFormat, pOut);
		}
		public PrintStream(HTMLOutputPane pHOP, Formatter pFormat, OutputStream pOut, boolean pIsAutoFlush) {
			super(pHOP, pFormat, pOut, pIsAutoFlush);
		}
		public PrintStream(HTMLOutputPane pHOP, Formatter pFormat, OutputStream pOut, boolean pIsAutoFlush,
				java.io.PrintStream ... pTargets) {
			super(pHOP, pOut, pIsAutoFlush, pTargets);
		}
	}
	
	// Simple test -----------------------------------------------------------------------------------------------------
	
	static void Sleep(long Time) {
		try { Thread.sleep(Time); } catch (Exception e) {}
	}
	static void Delay() {
		Sleep(500);
	}
	
	static public void main(String ... Args) {
		/* */
		JFrame f = new JFrame("Test HTMLOutoutPane");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final HTMLOutputPane HOP = new HTMLOutputPane();
		HOP.setVisible(true);
		
		f.getContentPane().add(HOP);
		f.setSize(800, 600);
		f.setLocationRelativeTo(null);
		f.setVisible(true);

		(new Thread() {
			@Override 
			public void run() {
				PrintStream POut = HOP. getSimpleFormatterPrintStream();
				try (var PErr = new PrintStream(HOP, new Formatter("<font color='red'>%s</font>", false))) {
    				
    				HOP.clearPattern().addBold().addItalic().changeRelativeFontSize(1);
    				POut.println("Hello");
    				Delay();
    				
    				PErr.println("Error");
    				Delay();
    				
    				POut.print("W\no\n\n\n\trl\tk\nd");
    				Delay();
    				
    				POut.print("a");
    				Delay();
    				
    				HOP.changeColor(Color.RED);
    				POut.println("b");
    				Delay();
    				
    				HOP.changeColor(Color.GREEN);
    				POut.print("c");
    				Delay();
    				
    				HOP.changeColor(Color.BLUE);
    				POut.println("s");
    				Delay();
    
    				HOP.changeColor(Color.orange);
    				POut.println("Orange");
    				Delay();
    
    				HOP.changeColor(Color.GRAY);
    				POut.println("Grey");
    				Delay();
				}
			}
		}).start();
		
		Sleep(10000);
		
		//System.exit(0);
	} 
	
}

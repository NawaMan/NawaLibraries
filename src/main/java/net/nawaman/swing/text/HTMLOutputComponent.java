package net.nawaman.swing.text;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.ComponentView;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.Segment;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLWriter;
import javax.swing.text.html.MinimalHTMLWriter;
import javax.swing.text.html.StyleSheet;
import javax.swing.text.html.HTMLEditorKit.HTMLFactory;

import net.nawaman.swing.NonEditableTextComponentCaret;

/**
 * The read-only text component that display the HTML-styled text.
 *
 * There are number of parties here need to be satisfied.
 * 1. The component must show a styled text with correct tab stop display.
 * 2. The component must be able to produce the result as plain text, original code and HTML3.2 compatible code while
 *     display tab correctly.
 **/
public class HTMLOutputComponent extends JTextPane {

	private static final long serialVersionUID = 4453043887336318575L;
	
	final HTMLOutputPane HOP;
	
	HTMLOutputComponent(HTMLOutputPane pHOP) {		
		this.HOP = pHOP;
		
		// Make this component 'read-only'
		super.setEnabled(true);
		super.setEditable(false);
		super.setCaret(new NonEditableTextComponentCaret());

		super.setContentType("text/html");
		super.setEditorKit(new HTMLOutputEditorKit(this.HOP));
		
		// Set and enforce the default font
		this.setFont(HTMLOutputPane.DefaultFont);
		this.putClientProperty(JTextPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		
		// Add key
		this.addKeyListener(new KeyListener() {
			/** {@inheritDoc} */ @Override public void keyTyped(KeyEvent e)    { }
			/** {@inheritDoc} */ @Override public void keyPressed(KeyEvent e)  { HOP.processKeyPressed(e);  }
			/** {@inheritDoc} */ @Override public void keyReleased(KeyEvent e) { HOP.processKeyReleased(e); }	
		});
		
		// WORKAROUND
		// Fix the problem of having caret moved out of the document length
		this.addCaretListener(new CaretListener() {
			/** {@inheritDoc} */ @Override 
		    public void caretUpdate(CaretEvent e) {
				int L = HOP.getPlainText().length() + 1;
				int D = e.getDot();
				int M = e.getMark();
				if(D != M) return;
				if(D > L) HOP.getTextComponent().getCaret().setDot(D - 1);
				if(M > L) HOP.getTextComponent().getCaret().setDot(M - 1);
			}
		});
	}

	/** Sets the text value directly */
	void setTextRaw(String pText) {
		super.setText(pText);
	}
	
	/**{@inheritDoc}*/ @Override
	public void setText(String pText) {
		this.HOP.setText(pText);
	}
	
	private HTMLEditorKit         HJEK = null;	// The Java one
	private HTMLExternalEditorKit HEEK = null;  // The One for the external (use "&nbsp;")
    
	// Get Text returns the original HTML source with "<tab>" to represent tab.
	// This is achieve by using the original Java's HTMLEditorKit
	
	/**{@inheritDoc}*/ @Override
	public String getText() {
		try { return this.getText(0, this.getDocument().getLength()); }
		catch (Exception e) { return ""; }
	}
    
	/**{@inheritDoc}*/ @Override
	public String getText(int Offset, int Length) throws BadLocationException {
		if(HJEK == null) HJEK = new HTMLEditorKit();
		
		StringWriter SW = new StringWriter();
		try { HJEK.write(SW, this.getDocument(), Offset, Length); }
		catch (IOException e) {}
		return SW.toString();
	}
	
	// Get HTML returns the HTML code that can be display by other (use '&nbsp;' to represent tab)
	// This is achieve by using the original Java's HTMLEditorKit
    
	/** Returns the HTML value (for other display like on WebBrowser) */
	public String getHTML() {
		try { return this.getHTML(0, this.getDocument().getLength()); }
		catch (Exception e) { return ""; }
	}
	/** Returns the HTML value (for other display like on WebBrowser) */
	public String getHTML(int Offset, int Length) throws BadLocationException {
		if(HEEK == null) HEEK = new HTMLExternalEditorKit(this.HOP);
		
		StringWriter SW = new StringWriter();
		try { HEEK.write(SW, this.getDocument(), Offset, Length); }
		catch (IOException e) {}
		return SW.toString();
	}
	
	// Get Plain Text returns the plain text representation of the document (use '\t' to represent tab)
	
	/** Returns the plain text of the component text */
	public String getPlainText() {
		try { return HTMLOutputComponent.getPlainTextFromHTMLDocument((HTMLDocument)this.getDocument()); }
		catch(Exception E) { throw new RuntimeException(E); }
	}
	
	/** Returns the plain text of the component text */
	public String getPlainText(int Offset, int Length) throws BadLocationException {
		return HTMLOutputComponent.getPlainTextFromHTMLDocument((HTMLDocument)this.getDocument(), Offset, Length);
	}
	
	// Customized ---------------------------------------------------------------------------------------

	/**{@inheritDoc}*/ @Override
	public void setEditable(boolean b) {
		if(b) return;
		super.setEditable(b);
	}

	/**{@inheritDoc}*/ @Override
    public void setCaret(Caret c) {
		if(!(c instanceof NonEditableTextComponentCaret)) return;
		super.setCaret(c);
	}
	
	// Static services -------------------------------------------------------------------------------------------------

	/** Extracts plain text from a HTMLDocument */
	static public String getPlainTextFromHTMLDocument(HTMLDocument Doc) throws BadLocationException {
		return getPlainTextFromHTMLDocument(Doc, 0, Doc.getLength());
	}
	/** Extracts plain text from a HTMLDocument */
	static public String getPlainTextFromHTMLDocument(final HTMLDocument Doc, final int pos, final int len)
			throws BadLocationException {
		
		Segment	Segment = new Segment();
		Doc.getText(pos, len, Segment);
		
		String S = Segment.toString();
		final StringBuilder SB = new StringBuilder(S);

		// Change all tab component to tab character
		ElementIterator it = new ElementIterator(Doc);
		Element Current = it.first();
		while(Current != null) {
			int I = Current.getStartOffset() - pos;
			if(I >= 0) {
				HTML.Tag Tag = (HTML.Tag)(Current.getAttributes().getAttribute(StyleConstants.NameAttribute));
				// Replace '<tab>' with with tab '\t'
				if(HTMLOutputPane.Tab.equals(Tag)) {
					if(I <= SB.length()) SB.setCharAt(I, '\t');
				}
			}
			Current = it.next();
		}
		if(SB.length() == 0) return "";

		// Change all non-break space to space
		for(int i = SB.length(); --i >= 0; ) {
			if(SB.charAt(i) == '\u00A0') SB.setCharAt(i, ' ');
		}
		
		// Returns the trimmed (one the back another in the font)
		return SB.substring(1, S.length());
	}

	// Helping class ---------------------------------------------------------------------------------------------------

	/** Document for HTML Output - register the 'tab' tag. */
	static class HTMLOutputDocument extends HTMLDocument {
		
		private static final long serialVersionUID = 4727421199151526425L;

		HTMLOutputDocument(StyleSheet pStyleSheet) {
			super(pStyleSheet);
		}		
		/**{@inheritDoc}*/ @Override
		public HTMLEditorKit.ParserCallback getReader(int pos) {
			return new HTMLReader(pos);
		}

		/** Document Reader for HTML Output */
		class HTMLReader extends HTMLDocument.HTMLReader {
			public HTMLReader(int offset) {
				super(offset);
				// Register a tag "tab"
				this.registerTag(HTMLOutputPane.Tab, new FormAction());
			}
		}
	}
		
	/** Factory for HTML Output elements - ensures that TabPanel is create when the tag 'tab' is found. **/
	static class HTMLOutputFactory extends HTMLFactory implements ViewFactory {
		/**{@inheritDoc}*/ @Override
		public View create(javax.swing.text.Element pElement) {
			HTML.Tag Tag     = (HTML.Tag) (pElement.getAttributes().getAttribute(StyleConstants.NameAttribute));
			String   TagName = pElement.getName();

			if((Tag instanceof HTML.UnknownTag) && TagName.equals(HTMLOutputPane.TabTagName)) {
				return new ComponentView(pElement) {
					/**{@inheritDoc}*/ @Override
					protected Component createComponent() {
						// Create tab panel
						return new TabElement();
					}
				};
			}
			return super.create(pElement);
		}
	}

	/**
	 * EditorKit for HTML Output - ensure HTMLOutputFactory and the default document is used
	 * 
	 * This EditorKit create documents that use TabPanel to simulate the present of tab in HTML document.
	 * This EditorKit write a text of the document by using spaces to simulate Tab.
	 **/
	static class HTMLTabEditorKit extends HTMLEditorKit {
		
		private static final long serialVersionUID = 4447499183128889228L;
		
		final HTMLOutputPane HOP;
		final String         SpaceToReplaceTab;
		
		HTMLTabEditorKit(HTMLOutputPane pHOP, String pSpaceToReplaceTab) {
			this.HOP               = pHOP;
			this.SpaceToReplaceTab = pSpaceToReplaceTab;
		}
		
		/**{@inheritDoc}*/ @Override
		public ViewFactory getViewFactory() {
			return new HTMLOutputFactory();
		}
		/**{@inheritDoc}*/ @Override
		public Document createDefaultDocument() {
			// Set appropriate margin for paragraph
			this.getStyleSheet().addRule("p { margin-top:0; margin-left:0; align: left; } ");
			
			// Create the document
			HTMLOutputDocument Doc = new HTMLOutputDocument(this.getStyleSheet());
			// Set some value (don't know what)
			Doc.setAsynchronousLoadPriority(4);
			Doc.setTokenThreshold(100);
			return Doc;
		}
		
		/**{@inheritDoc}*/ @Override
	    public void write(Writer out, Document doc, int pos, int len) throws IOException, BadLocationException {
			if (doc instanceof HTMLDocument) {
				HTMLWriter w = new HTMLWriter(out, (HTMLDocument)doc, pos, len) {
					// The offset of the latest tab
					int LastNoIndexOffset = -1;
					/**{@inheritDoc}*/ @Override
				    protected void indent() throws IOException {
						// Ignore the first indent right after a tab
						if(this.LastNoIndexOffset != this.getWriter().toString().length())
							super.indent();
					}
					/**{@inheritDoc}*/ @Override
				    protected void emptyTag(Element elem) throws BadLocationException, IOException {

						HTML.Tag Tag     = (HTML.Tag)(elem.getAttributes().getAttribute(StyleConstants.NameAttribute));
						String   TagName = elem.getName();

						if((Tag instanceof HTML.UnknownTag) && TagName.equals(HTMLOutputPane.TabTagName)) {
							String PT = HOP.getPlainText();
							
							int EndPos = elem.getStartOffset();
							int Index  = PT.lastIndexOf("\n", EndPos - 1) + 1;
							int TSize  = HOP.getTabSize();
							
							// Write non-break space based on the TabSize
							int Col = 0;
							int W   = 0;
							for(int i = Index; i < EndPos; i++) {
								if(PT.charAt(i) == '\t') {
									W = ((Col/TSize) + 1)*TSize - Col;
									Col += W;
								} else Col++;
							}
							
							// Add the non-break space for the current tab of the line 
							for(int c = 0; c < W; c++)
								this.write(SpaceToReplaceTab);
							
							this.LastNoIndexOffset = this.getWriter().toString().length();
						} else super.emptyTag(elem);
				    }
				};
				w.write();
			} else if (doc instanceof StyledDocument) {
				MinimalHTMLWriter w = new MinimalHTMLWriter(out, (StyledDocument)doc, pos, len);
				w.write();
			} else {
				super.write(out, doc, pos, len);
			}
	    }
	}

	/**
	 * EditorKit for HTML Output - ensure HTMLOutputFactory and the default document is used
	 * 
	 * This EditorKit writes write '\u00A0' (non-break space) to form a tab (used when copy out). 
	 **/
	static class HTMLOutputEditorKit extends HTMLTabEditorKit {

		private static final long serialVersionUID = 8124927876897984874L;
		
		HTMLOutputEditorKit(HTMLOutputPane pHOP) {
			super(pHOP, "\u00A0");
		}
	}

	/**
	 * EditorKit for creating HTML representation of the HTMLDocument that is compatible with HTML3.2 standards and can
	 *    be displayed by other browser.
	 * 
	 * This EditorKit writes write '&nbps;' (non-break space) to form a tab (used when copy out). 
	 **/
	static class HTMLExternalEditorKit extends HTMLTabEditorKit {

		private static final long serialVersionUID = 3663643435235651870L;

		HTMLExternalEditorKit(HTMLOutputPane pHOP) {
			super(pHOP, "&nbsp;");
		}
	}
}
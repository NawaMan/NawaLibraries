package net.nawaman.swing.text;

import net.nawaman.io.FilteredPrintStream;
import net.nawaman.io.NullOutputStream;

/**
 * Filtered PrintStream that converts the printed text into a HTML code.
 * For example: <code>println("<b>Bold</b>")</code> will be converted to <code>"&lt;b&gt;Bold&lt;/b&gt;<br>"</code>
 * 
 \* @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 */
class HTMLPrintStream extends FilteredPrintStream {

	private HTMLOutputPane HOP = null;
	
	HTMLPrintStream(HTMLOutputPane pHOP) {
		super(new NullOutputStream(), true, (java.io.PrintStream[])null);
		
		if(pHOP == null) throw new NullPointerException("HTMLPrintStream requires a HTMLOutputPane");
		this.HOP = pHOP;

		this.restartUpdateThread();
	}
	
	// Format and filtering ---------------------------------------------------------

	private Formatter Format           = null;
	private boolean   IsToCoverNewLine = false;
	
	/** Changes the format */
	void setFormat(Formatter pFormat) {
		this.Format = pFormat;
		this.IsToCoverNewLine = (this.Format == null)?false:this.Format.IsToCoverNewLine;
	}
	
	/** Returns the format */
	Formatter getFormat() {
		return this.Format;
	}

	// Constants for detecting empty body
	static final String EmptyBody         = "<p style=\"margin-top: 0\">\n      \n    </p>\n  </body>\n</html>\n";
	static final String EmptyDocument     = "<body>\n    " + EmptyBody;
	static final int    EmptyBodyPosition = EmptyBody.length();

	/** Convert plain text line into a HTML friendly text */
	String filterLine(String Line) {
		StringBuilder SB = new StringBuilder();
		for(int l = 0; l < Line.length(); l++) {
			char C = Line.charAt(l);
			switch(C) {
				case '<' : SB.append("&lt;");                break;
				case '>' : SB.append("&gt;");                break;
				case ' ' : SB.append("&nbsp;");              break;
				case '\t': SB.append(HTMLOutputPane.TabTag); break;
				default:   SB.append(C);                     break;
			}
		}
		return SB.toString();
	}
	
	/**{@inheritDoc}*/ @Override
	protected String filter(String S) {
		String  NewS           = "~"+S+"~";
		boolean HasNewLine     = (NewS.indexOf("\n") != -1);
		boolean IsCoverNewLine = this.IsToCoverNewLine;
		boolean IsSplitPattern = !IsCoverNewLine && (this.Format != null);
		
		// If have new line - we need to process the new line
		if(HasNewLine) {
			String[] Ss = NewS.split("\n");
			
			int      L  = Ss.length - 1;
			Ss[0] = Ss[0].substring(1, Ss[0].length());
			Ss[L] = Ss[L].substring(0, Ss[L].length() - 1);			

			StringBuilder SB        = new StringBuilder();
			String        Separator = IsCoverNewLine?"<br>":"</p><p>";
			// Spiting the paragraphs use '</p><p>'
			// Cover the new line so use '<br>'
			
			// Filter each line and combine them
			for(int i = 0; i < Ss.length; i++) {
				if(i != 0) SB.append(Separator);
				// Filter each line
				Ss[i] = this.filterLine(Ss[i]);
				// If split - Applied separately 
				if(IsSplitPattern && (Ss[i].length() != 0)) Ss[i] = this.Format.formarLine(Ss[i]);
				// Append the string
				SB.append(Ss[i]);
			}
			S = SB.toString();
			
		} else S = this.filterLine(S);

		// Do the formating as a whole - No new line or not split
		if((this.Format != null) && (!HasNewLine || !IsSplitPattern) && (S.length() != 0))
			S = this.Format.formarLine(S);
		
		// Return it
		return S;
	}
	
	// Plain text ---------------------------------------------------------------------------------------
	// Since the correct plain text cannot be obtain directly from either the Component or Document, we need to
	//    gather that value before it is convert to HTML document (before filtering). Also, the appending text must
	//    be insert into the right place in the document to avoid unnecessary parsing (by JTextPane's
	//    HTMLEditorKit). The insertion, however, still requires some work; Thus, both seems to be inefficient.
	// To avoid performance problem, HTMLOuput cache the appending text using three StringBuffers.  
	
	volatile private boolean IsLock     = false;
	volatile private boolean IsAppended = false;
	
	private StringBuffer BeforeNewLine = new StringBuffer();
	private StringBuffer WithNewLines  = new StringBuffer();
	private StringBuffer AfterNewLine  = new StringBuffer();
	
	/** Write to this PrintStream with a format */
	synchronized void writeWithFormat(String S, boolean isNewLine, Formatter pFormat) {
		Formatter TempFormat = this.getFormat();
		if(TempFormat == pFormat) this.write(S, isNewLine);
		else {
			try {
				this.setFormat(pFormat);
				this.write(S, isNewLine);
			} finally {
				this.setFormat(TempFormat);
			}
		}
	}

	/**{@inheritDoc}*/ @Override
	protected void write(String S, boolean isNewLine) {
		try {
			// Ensure this method is synchronized
			try { while(this.IsLock) Thread.sleep(10); this.IsLock = true; }
			catch(Throwable E) { return; }
			
			/* DEBUG * /
			if((S != null) && (S.equals("a")))
				System.out.println("NOW");
			/* */
			
			if((S.length() == 0) && !isNewLine) return;
			
			String Begin  = "";	// Those to be added the leftover part of the last time
			String Middle = "";	// Those that are self contain (surrounded with <p>...</p>)
			String End    = ""; // Those to be a new leftover for the next time
			
			boolean IsFormatted = S.startsWith("<html>");
			if(IsFormatted) {
				// No need to filter it
				S = S.substring("<html>".length());
				if(isNewLine) { S += "<br>"; isNewLine = false; }
				Begin = S;
				
			} else {
				// NOTE: filter should be responsible to separate each paragraph
				
				// Append the new line
				if(isNewLine) { S += "\n"; isNewLine = false; }
				
				int NLIndex  = S.indexOf('\n');
				int LNLIndex = -1;
				if((NLIndex == -1) || this.IsToCoverNewLine) {
					// No new line in side the text
					Begin  = this.filter(S);
					
				} else {
					// The part before the first new line
					Begin = this.filter(S.substring(0, NLIndex));

					// The part after the first and before the last
					LNLIndex = S.lastIndexOf('\n');
					if(LNLIndex == -1) LNLIndex = NLIndex;
					else if(LNLIndex != NLIndex) {
						Middle = this.filter(S.substring(NLIndex + 1, LNLIndex));
						if(Middle.length() != 0) Middle = "<p>" + Middle + "</p>";
					}

					// The part after the last
					if(LNLIndex == (S.length() - 1)) Middle += "<p><br></p>";
					else                             End = this.filter(S.substring(LNLIndex + 1));
					
				}
			}
				
			/* DEBUG * /
			System.out.println("=================================================================================");
			System.out.println("S: " + S);
			System.out.println("B: " + this.BeforeNewLine);
			System.out.println("M: " + this.WithNewLines);
			System.out.println("A: " + this.AfterNewLine);
			System.out.println("Begin:  " + Begin);
			System.out.println("Middle: " + Middle);
			System.out.println("End:    " + End);
			/* */
			
			// Append it to the cache
			
			StringBuffer ToBeAppended;

			int Len = this.WithNewLines.length();
			if(Len == 0) {
				// Without WithNewLines so append to the one before NewLine
				(ToBeAppended = this.BeforeNewLine).append(Begin);
				
			} else {
				// With WithNewLines so append Begin to the After NewLine before all of them append to WithNewLines
				(ToBeAppended = this.AfterNewLine).append(Begin);
			}
			
			boolean HasMiddle = (Middle.length() != 0);
			boolean HasEnd    = (End.length()    != 0);
			// Append the middle part and the last part
			if(IsFormatted || HasMiddle || HasEnd) {
				if(Len != 0) {
					// There is a middle part so the leftover is now closed so appends to the WitheNewLines
					int TailCount = "</p>".length();
					// The last paragraph is an empty one, so eliminate the last (dummy) newline.
					if(this.WithNewLines.toString().endsWith("<p><br></p>")) TailCount += "<br>".length();

					this.WithNewLines.delete(Len - TailCount, Len);
					this.WithNewLines.append(ToBeAppended.toString());
					this.WithNewLines.append("</p>");
					
					ToBeAppended.delete(0, ToBeAppended.length());
				}
				
				// Add the middle
				if(HasMiddle) this.WithNewLines.append(Middle);
				// Add the left over
				if(HasEnd)    this.AfterNewLine.append(End);
			}
			/* DEBUG * /
			System.out.println("B: " + this.BeforeNewLine);
			System.out.println("M: " + this.WithNewLines);
			System.out.println("A: " + this.AfterNewLine);
			System.out.println();
			/* */
			// TOTEST
			//
			
		} finally {
			this.IsAppended = true;
			this.IsLock     = false;
		}
	}
	
	// Flushing -----------------------------------------------------------------------------------------

	private boolean isFlushingStop = false;
	private Thread  Timer;
	
	/** Restarts the update thread */
	void restartUpdateThread() {
		// Ensure that it was previously stopped
		this.stopUpdateThread();
		while(!this.isFlushingStop);
		
		// Starts the flushing mechanism
		@SuppressWarnings("resource")
        final HTMLPrintStream This = this;
		
		// Create a new one
		this.Timer = new Thread() {
			/**{@inheritDoc}*/ @Override
			public void run() {
				isFlushingStop = false;
				while(true) {
					try {
						Thread.sleep(HOP.getFlushPeriod());
						if(This.IsAppended) This.performFlush();
						if(isFlushingStop)  break;
					} catch(InterruptedException IE) { break; }
				}
				isFlushingStop = false;
			}
		};
		
		// Start
		this.Timer.start();
	}
	/** Stop the update thread */
	void stopUpdateThread() {
		this.isFlushingStop = true;
	}
	
	/** Flush the text - this is for better performance */
	synchronized private void performFlush() {
		if(!this.IsAppended) return;
		
		boolean IsAdded = true;
		try {
			// Ensure this method is synchronized
			try {
				int I = 0;
				while(this.IsLock) {
					Thread.sleep(10);
					// Wait too long, may be there is a dead lock so give up first
					if(I++ == 10) return;
				}
			} catch(Throwable E) { return; }

			if(!this.IsAppended) return;
			
			String T = this.HOP.getTextComponent().getText();
			
			// Determine where to insert the new text.
			// There are 3 possible case
			//  1. Empty document   - The document is first populated   - We need to insert before and default paragraph.
			//  2. Hanging line     - The document ends with a new line - The new line is substituted with '<p><br></p>.
			//  3. Append Paragraph - The last paragraph is not ended   - Appends at the ends of the last paragraph 

			// The parsing is done in this fashion to avoid the problem of having the tag inside string or comment.
			// This parsing depends HEAVILY on how the HTMLDocument is formed; hence, if JTextPane's HTMLEditorKit
			//   have been modified, there is a very chance of breaking this. Despise the risk, this seems to be the
			//   only reasonable efficient and balance-to-effort way to implement it.
			
			// NOTE: All the whitespace in the parsing is needed as JTextPane's HTMLEditorKit use it.
			/* DEBUG * /
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("B: " + this.BeforeNewLine);
			System.out.println("M: " + this.WithNewLines);
			System.out.println("A: " + this.AfterNewLine);
			/* */
			int TailIndex;
			
			// Case 1: Insert at the body
			boolean     IsEmpty   = T.endsWith(EmptyDocument);
			if(IsEmpty) TailIndex = T.length() - EmptyBodyPosition;
			
			else {
				// Find the last paragraph
				TailIndex = T.lastIndexOf("\n    </p>");
				
				// The last paragraph should always be found as the T is always be a well-formed HTML
				// So no need to checks it it exist or not
				
				// Checks if the previous document ends with a hanging line
				// Case: 2
				int BeforeHanging = T.lastIndexOf("\n      <br>", TailIndex);
				if(BeforeHanging != -1) TailIndex = BeforeHanging;
				
				// Case: 3 - Otherwise, the end of the pas paragraph is found
			}

			StringBuilder SB = new StringBuilder(T.substring(0, TailIndex));
			
			// Add Before
			if(this.BeforeNewLine.length() != 0) {
				if(IsEmpty) SB.append("<p>");
				SB.append(this.BeforeNewLine);
				SB.append("</p>");
			}
			// Add WithNewLines
			SB.append(this.WithNewLines);
			// Add After
			if(this.AfterNewLine.length() != 0) {
				SB.append("<p>");
				SB.append(this.AfterNewLine);
				SB.append("</p>");
			}
			
			// Add the rest
			SB.append("</body></html>");
			T = SB.toString();
			
			// TODO Clip the old one
			
			this.HOP.getTextComponent().setTextRaw(T);
			this.writeRaw(T);
			IsAdded = false;
			
			this.BeforeNewLine.delete(0, this.BeforeNewLine.length());
			this.WithNewLines .delete(0, this.WithNewLines .length());
			this.AfterNewLine .delete(0, this.AfterNewLine .length());

			/* DEBUG * /
			System.out.println("-----------------------------------------------------");
			//System.out.println("T: " + T);
			//System.out.println("_____________________________________________________");
			//System.out.println("Text: " + this.HOP.TheTextComponent.getText());
			System.out.println();
			/* */

		} finally {
			this.IsAppended = IsAdded;
			this.IsLock     = false;
		}
	}
}
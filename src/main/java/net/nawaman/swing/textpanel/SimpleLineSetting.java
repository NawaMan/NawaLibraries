package net.nawaman.swing.textpanel;

import java.awt.*;

/** A simple line setting */
public class SimpleLineSetting extends Component implements LineSetting, Cloneable {

	private static final long serialVersionUID = 1183089520672298657L;

	/** The parent setting */
	public LineSetting ParentSetting = null;

	/** A predefine default setting */
	static final protected SimpleLineSetting theDefault = new SimpleLineSetting();

	/** Horizontal align */
	public HAlignment HAlign    = HAlignment.LEFT;
	/** Vertical align */
	public VAlignment VAlign    = VAlignment.BOTTOM;
	/** Indentation of each line */
	public int        Indent    =     0;
	/** Hanging og the wrapped line */
	public int        Hanging   =    40;
	/** Space between each wrapped line */
	public int        LineSpace =     0;
	/** Horizontal gap (between each visible component) */
	public int        HGap      =     0;
	/** Vertical gap between each line  */
	public int        VGap      =     0;
	/** Flag to indicate of line wrapping is enable */
	public Boolean    isWrap    = false;

	/** Create a simple line wrapping */
	protected SimpleLineSetting() {
		super();
		this.setVisible(false);
	}

	/** Create a simple line setting with a parent */
	protected SimpleLineSetting(LineSetting pParentLineSetting) {
		this.ParentSetting = pParentLineSetting;
	}

	/** Get an instance of a default simple line setting */
	static public SimpleLineSetting newDefault() { return SimpleLineSetting.theDefault.clone(); }

	/** Create a new empty line setting (the line setting that derive all its properties from its parent) */
	static public SimpleLineSetting newEmpty(LineSetting pParentLineSetting) {
		SimpleLineSetting LS = new SimpleLineSetting(pParentLineSetting);
		LS.HAlign    = null;
		LS.VAlign    = null;
		LS.Indent    =   -1;
		LS.Hanging   =   -1;
		LS.LineSpace =   -1;
		LS.HGap      =   -1;
		LS.VGap      =   -1;
		LS.isWrap    = null;
		return LS;
	}

	/** Create a new simple line setting */
	static public SimpleLineSetting newSimpleLineSetting (LineSetting pParentLineSetting,
	         HAlignment pHAlign, VAlignment pVAlign, int pIndent, int pHanging, int pLineSpace,
	         int pHGap, int pVGap, boolean pIsWrap) {
		SimpleLineSetting LS = new SimpleLineSetting(pParentLineSetting);
		LS.HAlign    =    pHAlign;
		LS.VAlign    =    pVAlign;
		LS.Indent    =    pIndent;
		LS.Hanging   =   pHanging;
		LS.LineSpace = pLineSpace;
		LS.HGap      =      pHGap;
		LS.VGap      =      pVGap;
		LS.isWrap    =    pIsWrap;
		return LS;
	}

	/** Create a new simple line setting that has no parent */
	static public SimpleLineSetting newSimpleLineSetting (HAlignment pHAlign, VAlignment pVAlign,
			int pIndent, int pHanging, int pLineSpace, int pHGap, int pVGap, boolean pIsWrap) {
		return newSimpleLineSetting(pHAlign, pVAlign, pIndent, pHanging, pLineSpace, pHGap, pVGap, pIsWrap);
	}

	/** Determine toString of a line setting */
	static public String toString(LineSetting aLS) {
		if(aLS == null) return "No parent";
		StringBuffer SB = new StringBuffer();
		SB.append("");
		SB.append("[");
		SB.append("{").append(SimpleLineSetting.toString(aLS.getParentSetting())).append("}");
		SB.append(", Align = (").append(aLS.getHAlignment()).append(", ").append(aLS.getVAlignment()).append(")");
		SB.append(", Gap = (").append(aLS.getHGap()).append(", ").append(aLS.getVGap()).append(")");
		SB.append(", Inden = ").append(aLS.getIndentation());
		SB.append(", Hanging = ").append(aLS.getHanging());
		SB.append(", LineSpace = ").append(aLS.getLineSpace());
		if(aLS.isWordWrapped()) SB.append(", Wrapped");
		else                    SB.append(", Not wrapped");
		SB.append("]");
		return SB.toString();
	}

	/** Create a clone instance of this simple line setting */
	@Override public SimpleLineSetting clone() {
		try {
			return (SimpleLineSetting)super.clone();
		} catch (CloneNotSupportedException CMSE) {
			return null;
		}
	}

	/** Returns the parent setting */
	public LineSetting getParentSetting() {
		return this.ParentSetting;
	}

	/** Returns the horizontal alignment */
	public HAlignment getHAlignment() {
		if(this.HAlign        != null) return this.HAlign;
		if(this.ParentSetting != null) return this.ParentSetting.getHAlignment();
		return SimpleLineSetting.theDefault.getHAlignment();
	}

	/** Returns the vertical alignment */
	public VAlignment getVAlignment() {
		if(this.VAlign        != null) return this.VAlign;
		if(this.ParentSetting != null) return this.ParentSetting.getVAlignment();
		return SimpleLineSetting.theDefault.getVAlignment();
	}

	/** Returns the indentation of each line */
	public int getIndentation() {
		if(this.Indent   !=   -1) return this.Indent;
		if(this.ParentSetting != null) return this.ParentSetting.getIndentation();
		return SimpleLineSetting.theDefault.getIndentation();
	}

	/** Returns the line hanging of a wrapped line */
	public int getHanging() {
		if(this.Hanging  !=   -1) return this.Hanging;
		if(this.ParentSetting != null) return this.ParentSetting.getHanging();
		return SimpleLineSetting.theDefault.getHanging();
	}
	
	/** Returns the line space between each wrapped line */
	public int getLineSpace() {
		if(this.LineSpace !=   -1) return this.LineSpace;
		if(this.ParentSetting  != null) return this.ParentSetting.getLineSpace();
		return SimpleLineSetting.theDefault.getLineSpace();
	}

	/** Horizontal gap (between each visible component) */
	public int getHGap() {
		if(this.HGap     !=   -1) return this.HGap;
		if(this.ParentSetting != null) return this.ParentSetting.getHGap();
		return SimpleLineSetting.theDefault.getHGap();
	}

	/** Vertical gap between each line  */
	public int getVGap() {
		if(this.VGap     !=   -1) return this.VGap;
		if(this.ParentSetting != null) return this.ParentSetting.getVGap();
		return SimpleLineSetting.theDefault.getVGap();
	}

	/** Returns the flag that indicates of line wrapping is enable */
	public boolean isWordWrapped() {
		if(this.isWrap    != null) return this.isWrap;
		if(this.ParentSetting != null) return this.ParentSetting.isWordWrapped();
		return SimpleLineSetting.theDefault.isWordWrapped();
	}

	/** Returns the toString of this line setting */
	@Override public String toString() {
		return SimpleLineSetting.toString(this);
	}

}
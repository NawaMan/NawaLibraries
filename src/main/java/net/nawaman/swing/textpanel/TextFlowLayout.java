package net.nawaman.swing.textpanel;

import java.awt.*;

// Adapted from FlowLayout - Sun Inc.
/** Layout manager as a text flow */
public class TextFlowLayout implements LayoutManager {

	/** Default Line Setting */
	LineSetting DefaultLineSetting = null;

	/** Create a new text-flow layout */
    public TextFlowLayout() { this(null); }

    /** Create a new text-flow layout with a default line setting */
    public TextFlowLayout(LineSetting pDefaultLineSetting) {
		this.DefaultLineSetting = pDefaultLineSetting;
		if(this.DefaultLineSetting == null)
			this.DefaultLineSetting = SimpleLineSetting.newDefault();
    }

    /** Returns the default line setting */
    public LineSetting getDefaultLineSetting() {
		return this.DefaultLineSetting;
    }

    /** Set the default line setting */
    public void setDefaultLineSetting(LineSetting pDefaultLineSetting) {
		this.DefaultLineSetting = pDefaultLineSetting;
		if(this.DefaultLineSetting == null)
			this.DefaultLineSetting = SimpleLineSetting.newDefault();
    }

    /** Adds a layout component (do nothing) */
    public void addLayoutComponent(String name, Component comp) {}
    /** Removes a layout component */
    public void removeLayoutComponent(Component comp)           {}

    /**
     * Determine the preferred layout size of this container. The preferred size if the size in which
     *    no line wrapping is performed and all the size of the all component is its perferred size.
     **/
	public Dimension preferredLayoutSize(Container pContainer) {
		synchronized (pContainer.getTreeLock()) {
			Dimension Size = new Dimension(0, 0);
			int Width  = 0;
			int Height = 0;

			// Get all the components
			Component[] Components = pContainer.getComponents();
			boolean firstVisibleComponent = true;
			boolean noLine                = true;

			// Set the default line setting
			LineSetting lSetting = this.DefaultLineSetting;

			// Loop all the components
			for(Component C : Components) {
				// Look for a new line setting
				if(C instanceof LineSetting) {
					// Width of the being layouted component is the maximum width of each line
					Size.width = Math.max(Size.width, Width);
					// Height of the being layouted component is the sumation of the height of each line
					Size.height += Height + lSetting.getVGap();

					// Reset the visible component flag
					firstVisibleComponent = true;
					// Change to the new line setting.
					lSetting = (LineSetting)C;

					// Reset the new width and height
					Width  = 0;
					Height = 0;

					// Reset no line flag
					noLine = false;
				}
				// Consider only the visible component 
				if (C.isVisible()) {
					// Get the preferred size of the component
					Dimension comp = C.getPreferredSize();

					// The height of each line is the maximum height of all the component in the line
					Height = Math.max(Height, comp.height);
					// If this is the first component, mark it
					if (firstVisibleComponent) {
						firstVisibleComponent = false;
					} else {
						// If this is not the first component add HGap
						Width += lSetting.getHGap();
					}
					// Sum all the width
					Width += comp.width;
				}
			}
			// Finish all component so add the effect of the decoration
			Insets insets = pContainer.getInsets();
			Size.width  += insets.left + insets.right;	// Add the insets (border and other decoration)
			Size.height += insets.top  + insets.bottom;	// Add the insets (border and other decoration)

			// If no line, cancel the VGap
			if(!noLine) Size.height -= lSetting.getVGap();

			// Return the size
			return Size;
		}
	}
	
    /**
     * Determine the minimum layout size of this container. The minimum size if the size in which
     *    no line wrapping is performed and all the size of the all component is its minimum size.
     **/
	public Dimension minimumLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			Dimension Size = new Dimension(0, 0);
			int Width  = 0;
			int Height = 0;

			// Get all component
			Component[] Components = target.getComponents();
			boolean firstVisibleComponent = true;
			boolean noLine                = true;

			// Set the default line setting
			LineSetting lSetting = this.DefaultLineSetting;

			// Loop all the components
			for (Component C : Components) {
				// Look for a new line setting
				if(C instanceof LineSetting) {
					// Width of the being layouted component is the maximum width of each line
					Size.width = Math.max(Size.width, Width);
					// Height of the being layouted component is the sumation of the height of each line
					Size.height += Height + lSetting.getVGap();

					// Reset the first component flag
					firstVisibleComponent = true;
					// Use this new line setting as the line setting
					lSetting = (LineSetting)C;

					// reset the size
					Width  = 0;
					Height = 0;

					// Unmarked the no line flag
					noLine = false;
				}
				// Consider only the visible component 
				if (C.isVisible()) {
					// Get the minimum size of the component
					Dimension comp = C.getMinimumSize();

					// The height of each line is the maximum height of all the component in the line
					Height = Math.max(Height, comp.height);
					// If this is the first component, mark it
					if (firstVisibleComponent) {
						firstVisibleComponent = false;
					} else {
						// If this is not the first component add HGap
						Width += lSetting.getHGap();
					}
					// Sum all the width
					Width += comp.width;
				}
			}
			// Finish all component so add the effect of the decoration
			Insets insets = target.getInsets();
			Size.width  += insets.left + insets.right;	// Add the insets (border and other decoration)
			Size.height += insets.top  + insets.bottom;	// Add the insets (border and other decoration)

			// If no line, cancel the VGap
			if(!noLine) Size.height -= lSetting.getVGap();

			// Return the size
			return Size;
		}
    }

	// This method will be called after getPreferred size so its size will be already determined.
	/** Layout the container. **/
	public void layoutContainer(Container pContainer) {
		synchronized (pContainer.getTreeLock()) {
			// Left to right is not yet supported
			if(!pContainer.getComponentOrientation().isLeftToRight()) {
				System.out.println("Right to Left is not yet supported!");
				return;
			}

			// Get the decoration
			Insets insets = pContainer.getInsets();

			// Get the start position
			int y = insets.top;
			int startLine = 0;
			int endLine   = 0;
			Dimension Size = new Dimension(0, 0);

			// Get the component number
			int nMembers = pContainer.getComponentCount();

			// Get the first line setting
			LineSetting lSetting = this.DefaultLineSetting;

			// Loop all the component looking for line setting
			for (int i = 0 ; i < nMembers ; i++) {
				Component comp = pContainer.getComponent(i);
				if (comp instanceof LineSetting) {
					endLine = i;
					// Layout each line
					Dimension lineSize = this.layoutLine(pContainer, lSetting, startLine, endLine, y);
					// Sum all the line's height
					y += lineSize.height;
					// and get the maximum width
					Size.width = Math.max(Size.width, lineSize.width);

					// Set the new line setting
					lSetting = (LineSetting)comp;
					// Start a new line
					startLine = endLine;
				}
			}
			// In case that the last line doesn't end with a new line (LineSetting)
			// Layout the last line
			Dimension lineSize = this.layoutLine(pContainer, lSetting, startLine, nMembers, y);
			// Add the height of the last line
			y += lineSize.height;
			// Get the width
			Size.width = Math.max(Size.width, lineSize.width);

			// Finish all component so add the effect of the decoration
			Size.width  +=     insets.left + insets.right;
			Size.height =  y + insets.top  + insets.bottom;

			// Ensure the minimum size and the maximumsize (which means that cliping may be needed)
			Dimension MinimumSize = pContainer.getMinimumSize();
			Dimension MaximumSize = pContainer.getMaximumSize();
			if(Size.width  < MinimumSize.width)  Size.width  = MinimumSize.width;
			if(Size.width  > MaximumSize.width)  Size.width  = MaximumSize.width;
			if(Size.height < MinimumSize.height) Size.height = MinimumSize.height;
			if(Size.height > MaximumSize.height) Size.height = MaximumSize.height;

			pContainer.setPreferredSize(Size);
		}
	}
	
	/** Layout a line */
	public Dimension layoutLine(Container pContainer, LineSetting aLSetting, int startLine, int endLine, int y) {
		synchronized (pContainer.getTreeLock()) {
			// Get the start line
			int startY = y;

			// Get the maximum (the largest possible width)
			Insets aInsets = pContainer.getInsets();
			int maxWidth   = pContainer.getWidth() - (aInsets.left + aInsets.right);

			// Flow layout to a row
			// Find a row and calculate MaxHeight
			int rowHeight = 0;
			int startRow  = startLine;
			int endRow    = startLine;

			// Wrapping is not needed
			boolean isWrapped = false;

			// Start with and indentation
			int x = aLSetting.getIndentation();
			// And the current width is 0
			int width = 0;

			// Loop from start to end
			// TODO - Right to Left language is currently not support but perhaps reversing this loop will do
			for (int i = startLine ; i < endLine ; i++) {
				Component comp = pContainer.getComponent(i);
				// Look only for the visible component
				if (comp.isVisible()) {
					// Set the size of each component to the preferred size
					Dimension dim = comp.getPreferredSize();
					comp.setSize(dim.width, dim.height);

					// Each row height is the maximum all the component height
					rowHeight = Math.max(rowHeight, dim.height);
					// Sum up all the width
					x += dim.width;

					endRow = i;
					// If the width is exceed the maxWidth and word-wrapped is enabled.
					if((x >= maxWidth) && aLSetting.isWordWrapped()) {
						// Cancel the latest component width (the one that make width exceed the maxWidth)
						//     so that the total width does not exceed the maxWidth 
						width = x - (dim.width + aLSetting.getHGap());
						// Layout each low
						this.layoutRow(pContainer, aLSetting, isWrapped, y, width, rowHeight, startRow, endRow);

						// Set the wrapping flag
						isWrapped = true;
						// The start X is the indentation + hanging and the revious width (that was
						//     previously cancel from the lastest line)
						x = aLSetting.getIndentation() + aLSetting.getHanging() + dim.width;
						// Add the height and the VGap
						y += rowHeight + aLSetting.getLineSpace();
						// The initial rowHight is this component's height
						rowHeight = dim.height;
						// The new start height
						startRow = endRow;
					}
					// Add HGap()
					x += aLSetting.getHGap();
				}
			}
			// Layout the rest of the line
			width = x - aLSetting.getHGap();
			// Layout the last row
			this.layoutRow(pContainer, aLSetting, isWrapped, y, width, rowHeight, startRow, endLine);
			// Add the vertical gap
			y += rowHeight + aLSetting.getVGap();

			// Make the size object
			Dimension Size = new Dimension(x, y - startY);
			// Set the width to maxWidth if this is a wrapping line
			if(aLSetting.isWordWrapped()) Size.width = maxWidth;

			// Returns the size
			return Size;
		}
	}

	/** Layout a row of components */
	private void layoutRow(Container pContainer, LineSetting aLSetting, boolean isWrapped,
	                            int y, int width, int rowHeight, int startRow, int endRow) {
		synchronized (pContainer.getTreeLock()) {
			// Determine the maximum width from the container size and the insets size
			Insets aInsets = pContainer.getInsets();
			int maxWidth   = pContainer.getWidth() - (aInsets.left + aInsets.right);

			// The start X is the indentation value
			int x = aLSetting.getIndentation();
			// plus the hanging if this is a wrapped line.
			if(isWrapped) x += aLSetting.getHanging();

			// Start X also differ by the horizontal alignment
			switch(aLSetting.getHAlignment()) {
				case LEFT:
					// Normal X for LEFT
					x = aInsets.left + x;
					break;
				case CENTER:
					// Add haft of the leftover space in case of CENTER
					x = aInsets.left + ((maxWidth - width) / 2);
					break;
				case RIGHT:
					// Add the leftover space in case of RIGHT
					x = aInsets.left + (maxWidth - width);
					break;
			}
			
			// Find the largest baseline
			int MaxBaseLine = -1;
			if(aLSetting.getVAlignment() == VAlignment.BASELINE) {
				for(int i = startRow ; i < endRow ; i++) {
					Component comp = pContainer.getComponent(i);
					// Only look for the visible component
					if (comp.isVisible()) {
						Dimension D = comp.getSize();
						if(D.height <= MaxBaseLine) continue;
						// Get the baseline
						int bLine = comp.getBaseline(D.width, D.height);
						// And record the maximum base line
						if(bLine > MaxBaseLine) MaxBaseLine = bLine;
					}
				}
			}
			
			// Get the hGap
			int hGap = aLSetting.getHGap();

			// Loop all the component
			// TODO - Right to Left language is currently not support but perhaps reversing this loop will do
			for (int i = startRow ; i < endRow ; i++) {
				Component comp = pContainer.getComponent(i);
				// Only look for the visible component
				if (comp.isVisible()) { 
					// Different vertical position based on the alignment
					switch(aLSetting.getVAlignment()) {
						case TOP:
							comp.setLocation(x, y);
							break;
						case CENTER:
							comp.setLocation(x, y + (rowHeight - comp.getHeight()) / 2);
							break;
						case BOTTOM:
							comp.setLocation(x, y + (rowHeight - comp.getHeight()));
							break;
						case BASELINE:
							Dimension D = comp.getSize();
							int bLine = comp.getBaseline(D.width, D.height);
							comp.setLocation(x, y + MaxBaseLine - bLine);
							break;
					}
					// Add the width of the component and the gap
					x += comp.getWidth() + hGap;
					
					// TODO - Should change this to a special Token
					// In case of the last component is to expand
					if((i == (endRow - 1)) && (comp instanceof TextBlock) && ((TextBlock)comp).IsToExpand) {
						Dimension dim = comp.getPreferredSize();
						comp.setSize(maxWidth - comp.getLocation().x, dim.height);
					}
				}
			}
		}
	}

}
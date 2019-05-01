package net.nawaman.swing;

import java.awt.*;
import javax.swing.*;

public class BorderPanel extends JPanel {
	
	private static final long serialVersionUID = 3825553286567627926L;
	
	BorderLayout Layout = null;
	
	public BorderPanel() {
		this.Layout = new BorderLayout();
		this.setLayout(this.Layout);
	}
	public BorderPanel(Component pCenter, Component pNorth, Component pSouth, Component pEast, Component pWest) {
		this();
		this.setCenter(pCenter);
		this.setCenter(pNorth);
		this.setCenter(pSouth);
		this.setCenter(pEast);
		this.setCenter(pWest);
	}
	
	public void setCenter(Component pComp) { this.add(pComp, BorderLayout.CENTER); }
	public void setNorth( Component pComp) { this.add(pComp, BorderLayout.NORTH);  }
	public void setSounth(Component pComp) { this.add(pComp, BorderLayout.SOUTH);  }
	public void setEast(  Component pComp) { this.add(pComp, BorderLayout.EAST);   }
	public void setWest(  Component pComp) { this.add(pComp, BorderLayout.WEST);   }
	
}
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

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 * Understands usage of the system clip-board.
 *
 * @author Alex Ruiz
 */
public final class Clipboard {
	public static String text() {
		try {
			Object data = clipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
			if (data instanceof String) return (String)data;
		} catch(Exception E) {}
		return null;
	}
	
	public static void clear() {
		try {
			clipboard().setContents(new Transferable() {
				public DataFlavor[] getTransferDataFlavors() {
					return new DataFlavor[0];
				}
				
				public boolean isDataFlavorSupported(DataFlavor flavor) {
					return false;
				}
				
				public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
					throw new UnsupportedFlavorException(flavor);
				}
			}, null);
		} catch (IllegalStateException e) {}
	}
	
	public static void copy(String toCopy) {
		clipboard().setContents(new StringSelection(toCopy), null);
	}
	
	private static java.awt.datatransfer.Clipboard clipboard() {
		return Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	private Clipboard() {}
}

package net.nawaman.io;

import java.io.File;

/** File filter base on the files extension (file type) */
public class TypeFileFilter extends javax.swing.filechooser.FileFilter {

	public TypeFileFilter(String[] pExts, String pDesc) {
		this.Exts  = pExts;
		this.Desc = pDesc;
	}

	private String[] Exts = null;
	private String   Desc = null;

	/**{@inheritDoc}*/ @Override
	public boolean accept(File f) {
		if (f == null)
			return false;
		if (f.isDirectory())
			return true;
		if(this.Exts == null) return true;
		for(int i = this.Exts.length; --i >= 0; ) {
			String Ext = this.Exts[i];
			if(Ext == null) continue;
			if(f.toString().endsWith(Ext)) return true;
		}
		return false;
	}

	/**{@inheritDoc}*/ @Override
	public String getDescription() {
		return this.Desc;
	}

	/** Returns the file extension of a given file object */
	public String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}
}
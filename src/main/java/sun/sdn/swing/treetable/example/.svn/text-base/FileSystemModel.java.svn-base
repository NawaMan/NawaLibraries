package sun.sdn.swing.treetable.example;

/*
 * %W% %E%
 *
 * Copyright 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer. 
 *   
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution. 
 *   
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.  
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE 
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,   
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER  
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF 
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS 
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

import java.io.*;
import java.util.*;

import sun.sdn.swing.treetable.*;

/**
 * FileSystemModel is a TreeTableModel representing a hierarchical file system.
 * 
 * Nodes in the FileSystemModel are FileNodes which, when they are directory nodes, cache their
 * children to avoid repeatedly querying the real file system.
 * 
 * @version %I% %G%
 * 
 * @author Philip Milne
 * @author Scott Violet
 */

public class FileSystemModel extends AbstractTreeTableModel {
    
    // Names of the columns.
    static protected String[] ColumnNames = {
            "Name",
            "Size",
            "Type",
            "Modified"
        };
    
    //Types of the columns.
    static protected Class<?>[] ColumnTypes = {
            TreeTableModel.class,
            Integer.class,
            String.class,
            Date.class
        };
    
    // The the returned file length for directories.
    public static final Integer ZERO = Integer.valueOf(0);
    
    public FileSystemModel() {
        super(new FileNode(new File(File.separator)));
    }
    
    //
    // Some convenience methods.
    //
    
    protected File getFile(final Object pNode) {
        final FileNode aFileNode = ((FileNode)pNode);
        return aFileNode.getFile();
    }
    
    protected Object[] getChildren(final Object pNode) {
        final FileNode aFileNode = ((FileNode)pNode);
        return aFileNode.getChildren();
    }
    
    //
    // The TreeModel interface
    //
    
    public int getChildCount(final Object pNode) {
        final Object[] aChildren = this.getChildren(pNode);
        return (aChildren == null) ? 0 : aChildren.length;
    }
    
    public Object getChild(
            final Object pNode,
            final int    pIndex) {
        return this.getChildren(pNode)[pIndex];
    }
    
    // The superclass's implementation would work, but this is more efficient.
    @Override
    public boolean isLeaf(final Object pNode) {
        return this.getFile(pNode).isFile();
    }
    
    //
    // The TreeTableNode interface.
    //
    
    public int getColumnCount() {
        return ColumnNames.length;
    }
    
    public String getColumnName(final int pColumn) {
        return ColumnNames[pColumn];
    }
    
    @Override
    public Class<?> getColumnClass(final int pColumn) {
        return ColumnTypes[pColumn];
    }
    
    public Object getValueAt(Object node, int column) {
        final File aFile = getFile(node);
        try {
            switch (column) {
            case 0:
                return aFile.getName();
            case 1:
                return aFile.isFile() ? Integer.valueOf((int) aFile.length()) : ZERO;
            case 2:
                return aFile.isFile() ? "File" : "Directory";
            case 3:
                return new Date(aFile.lastModified());
            }
        } catch (SecurityException se) {
        }
        
        return null;
    }
}

/*
 * A FileNode is a derivative of the File class - though we delegate to the File object rather than
 * sub-classing it. It is used to maintain a cache of a directory's children and therefore avoid
 * repeated access to the underlying file system during rendering.
 */
class FileNode {
    final File File;
    
    Object[] Children;
    
    public FileNode(final File pFile) {
        this.File = pFile;
    }
    
    // Used to sort the file names.
    static private MergeSort FileMS = new MergeSort() {
        @Override
        public int compareElementsAt(
                final int pA,
                final int pB) {
            return ((String)this.ToSort[pA]).compareTo((String) this.ToSort[pB]);
        }
    };
    
    /**
     * Returns the the string to be used to display this leaf in the JTree.
     */
    @Override
    public String toString() {
        return this.File.getName();
    }
    
    public File getFile() {
        return this.File;
    }
    
    /**
     * Loads the children, caching the results in the children ivar.
     */
    protected Object[] getChildren() {
        if (this.Children != null) {
            return this.Children;
        }
        try {
            final String[] aFiles = this.File.list();
            if (aFiles != null) {
                FileNode.FileMS.sort(aFiles);
                this.Children = new FileNode[aFiles.length];
                
                final String aPath = this.File.getPath();
                for (int i = 0; i < aFiles.length; i++) {
                    File childFile = new File(aPath, aFiles[i]);
                    this.Children[i] = new FileNode(childFile);
                }
            }
        } catch (SecurityException se) {
        }
        return this.Children;
    }
}

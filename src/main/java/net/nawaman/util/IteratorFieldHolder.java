package net.nawaman.util;

import java.util.*;

/** Iterator of FieldHolder  */
public class IteratorFieldHolder implements Iterator<Object>, Resetable {
	
	/** Construct field holder iterate. */
	public IteratorFieldHolder(net.nawaman.util.FieldHolder pFieldHolder) { this.FieldHolder = pFieldHolder; }

	int                   Index       =   -1;
	net.nawaman.util.FieldHolder FieldHolder = null;
	
	/** Resets the counting of the iterator. */
	public boolean reset() { this.Index = -1; return true; }
	
	/** Checks if there is next value of the iterator. */
    public boolean hasNext() { return (this.Index < (this.FieldHolder.getFieldCount() - 1)); }
    /** Returns the next value of the iterator and continue the counting. */
    public Object next() {
    	if(!this.hasNext()) return null;
    	this.Index++;
    	return this.FieldHolder.getData(this.FieldHolder.getFieldNames()[this.Index]);
    }
    
    /** Remove the current element if the iterator - No supported */
    public void remove() {}
    
    /** Returns the string representation of this iterator. */
    @Override
    public String toString() {
		StringBuffer SB = new StringBuffer();
		SB.append("[");
		for(int i = 0; i < this.FieldHolder.getFieldCount(); i++) {
			if(i != 0) SB.append(", ");
			SB.append(this.FieldHolder.getData(this.FieldHolder.getFieldNames()[this.Index]));
		}
		SB.append("]");
		return SB.toString();
    }
}
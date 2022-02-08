package net.nawaman.util;

import java.lang.reflect.*;

/** FieldHolder wrapper of an object. All public fields are wrapped by this wrapper. */
public class FieldHolderObject implements FieldHolder, Objectable {
	
	/** Constructor of FieldHolderObject */
	public FieldHolderObject(Object pO) {
		this.Obj = pO;
		if(this.Obj != null) {
			this.Fields = this.Obj.getClass().getFields();
			this.FieldNames = new String[this.Fields.length];
			for(int i = 0; i < this.Fields.length; i++) this.FieldNames[i] = this.Fields[i].getName();
		} else {
			this.Fields     = new Field[0];
			this.FieldNames = new String[0];
		}
	}
	
	       IteratorFieldHolder Iterator = null;
	/** Returns the iterator of this field-holder object. This iterator are persistent and reusable. */
	public IteratorFieldHolder iterator() {
		if(this.Iterator == null) this.Iterator = this.newIterator();
		else                      this.Iterator.reset();
		return this.Iterator;
	}
	/** Returns an iterator that can iterate field value */
	public IteratorFieldHolder newIterator() {
		return this.Iterator = new IteratorFieldHolder(this);
	}
	
	/** The wrapped object. */
	protected Object   Obj         = null;
	/** Fields of the object. */
	protected Field[]  Fields      = null;
	/** Names of the fields. */
	protected String[] FieldNames  = null;
	
	/** Returns the number of fields of the object. */
	public int      getFieldCount() { return this.Fields.length; }
	/** Returns the names of fields of the object. */
	public String[] getFieldNames() { return this.FieldNames;    }
	
	/** Returns the class data of the field. */
	public Class<?> getDataClass(String pName) {
		for(int i = 0; i < this.Fields.length; i++) {
			if(!UString.equal(pName, this.FieldNames[i])) continue;
			Field F = this.Fields[i];
			return F.getType();
		}
		return null;
	}
	
	/** Set pData into the field named pName and return if that has success. */
	public Object setData(String pName, Object pData) {
		for(int i = 0; i < this.Fields.length; i++) {
			if(!UObject.equal(pName, this.FieldNames[i])) continue;
			Field F = this.Fields[i];
			
			try { F.set(this.Obj, pData); return pData; }
			catch(IllegalAccessException E) { throw new RuntimeException("There is an error setting the value of `"+pName+"` with '"+pData+"'.", E); }
			catch(NullPointerException E)   { throw new RuntimeException("There is an error setting the value of `"+pName+"` with '"+pData+"'.", E); }
		}
		throw new RuntimeException("Unable to find the field `"+pName+"`.");
	}
	/** Returns the value of the field named pName */
	public Object getData(String pName) {
		for(int i = 0; i < this.Fields.length; i++) {
			if(!UObject.equal(pName, this.FieldNames[i])) continue;
			Field F = this.Fields[i];

			try { return F.get(this.Obj); }
			catch(IllegalAccessException E) { }
			catch(NullPointerException E)   { } 
			return null;
		}
		return false;
	}

	/** Returns the integer representation of the object. */
	@Override public int     hash()           { return UObject.hash(this);     }
	/** Returns the short string representation of the object. */
	@Override public String  toString()       { return UObject.toString(this); }
	/** Returns the long string representation of the object. */
	          public String  toDetail()       { return UObject.toDetail(this); }
	/** Checks if O is the same or consider to be the same object with this object. */
	          public boolean is(Object O)     { return this == O; }
	/** Checks if O equals to this object. */
	@Override public boolean equals(Object O) { if(O instanceof FieldHolder) return false; return this.hash() == UObject.hash(O); }
	
}
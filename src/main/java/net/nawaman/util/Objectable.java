package net.nawaman.util;

/**
 * Objectable define necessary methods for an object.
 *  
 * @author	Nawapunth Manusitthipol
 */
public interface Objectable {

	/** Returns the short string representation of the object. */
	public String toString();

	/** Returns the long string representation of the object. */
	public String toDetail();

	/**
	 * Checks if O is the same or consider to be the same object with this object.
	 * 		In most case, the implementation of this method is <br />
	 *      <pre>
	 *      <code>
	 *      	return (this == O);
	 *      </code>
	 *      </pre>
	 *      
	 * @param	O	the object to be compared
	 * @return	true if O is the same or consider to be the same with this object. 
	 */	
	public boolean is(Object O);

	/**
	 * Checks if O equals to this object.
	 * 		In most case, the implementation of this method is <br />
	 *      <pre>
	 *      <code>
	 *      	if(O == null)  return false;
	 *      	if(this.is(O)) return  true;
	 *      	return (this.hash() == O.hash());
	 *      </code>
	 *      </pre>
	 * @param	O	the object to be compared
	 * @return	true if O is the same or consider to be the same with this object. 
	 */	
	public boolean equals(Object O);

	/** Returns the integer representation of the object. */
	public int hash();

}

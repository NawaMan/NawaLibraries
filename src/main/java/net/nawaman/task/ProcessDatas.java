package net.nawaman.task;

/** Container of Process Data */
public interface ProcessDatas {
	
	/** Checks if this process data contains the data referred by the ref  */
	public boolean contains(DataRef pRef);
	
	/** Returns the data at the reference */
	public Object  getData(DataRef pRef);
	/** Change the data at the reference - Returns if that success */
	public Object  setData(DataRef pRef, Object pValue);

	/** Freeze the whole container  - Returns if success */
	public boolean freeze();
	
	/** Freeze that data - Returns if success */
	public boolean freezeData(DataRef pRef);
	
}

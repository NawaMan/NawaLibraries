package net.nawaman.task;

/** A task */
public interface Task {
	
	/** Returns the number of input needed by this tasks */
	public int      getInputCount();
	/** Returns the DataType of the input */
	public Class<?> getInputType(int I);
	/** Returns the default value if the input is missing */
	public Object   getInputDefault(int I);

	/** Returns the number of output needed by this tasks */
	public int      getOutputCount();
	/** Returns the DataType of the output */
	public Class<?> getOutputType(int I);
	
	/** Performs the task */
	public Object[] doTask(ProcessContext pContext, TaskEntry pTE, TaskOptions pOptions, Object[] pIns);
	
}

package net.nawaman.task;

/** Task entry for process */
abstract public class TaskEntry {
	
	/** Returns the number of input needed by this tasks */
	final public int getInputCount() {
		return this.getTask().getInputCount();
	}
	/** Returns the DataRef of the input  */
	abstract public DataRef getInputRef(int I);
	/** Returns the DataType of the input */
	final public Class<?> getInputType(int I) {
		return this.getTask().getInputType(I);
	}
	/** Returns the default value if the input is missing */
	final public Object   getInputDefault(int I) {
		return this.getTask().getInputDefault(I);
	}
	
	final public int getOutputCount() {
		return this.getTask().getOutputCount();
	}
	/** Returns the DataRef of the output */
	abstract public DataRef getOutputRef(int I);
	/** Returns the DataType of the output */
	final public Class<?> getOutputType(int I) {
		return this.getTask().getOutputType(I);
	}
	
	/** Returns the task to perform this entry */
	abstract public Task getTask();
	
	/** Performs the task */
	final public Object[] doTask(ProcessContext pContext, TaskEntry pTE, TaskOptions pOptions, Object[] pIns) {
		Task T = this.getTask();
		return T.doTask(pContext, pTE, pOptions, pIns);
	}

}

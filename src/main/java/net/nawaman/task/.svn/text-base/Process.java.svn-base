package net.nawaman.task;

/** A task processor - Process run a task */
abstract public class Process {
	
	/** Process that contain tasks */
	static abstract public class SelfContain extends Process {
		
		/** Creates a new Option - for using in doTask */
		abstract protected TaskOptions newOptions();
		
		/** Creates a new Context - for using in doTask */
		abstract protected ProcessContext newContext(TaskOptions pOpts);
		
		/** Creates a new DataContainer - for using in doTask */
		abstract protected ProcessDatas newDataContainer(ProcessContext pContext, TaskOptions pOpts);
		
		/** Creates a new set of tasks */
		abstract protected TaskEntry[] newTaskEntries();
		
		/** Perform the tasks */
		final protected boolean doTasks() {
			TaskOptions    Opts    = this.newOptions();
			ProcessContext Context = this.newContext(Opts);
			ProcessDatas   Datas   = this.newDataContainer(Context, Opts);
			TaskEntry[]    Tasks   = this.newTaskEntries();
			return this.doTasks(Context, Datas, Tasks, Opts);
		}
		
	}
	
	// Notifying -------------------------------------------------------------------------------------------------------
	// TODO - Implement this
	/** Get notify that the tasks is null */
	protected boolean notifyEmptyTasks(ProcessContext pContext, ProcessDatas pDatas, TaskOptions pOpts) {
		return true;
	}
	
	/** Get notify that at least of given task is null */
	protected boolean notifyNullTask(ProcessContext pContext, ProcessDatas pDatas, TaskOptions pOpts) {
		return true;
	}
	
	/** Get notify that an input of a task is missing */
	protected boolean notifyMissingInput(ProcessContext pContext, ProcessDatas pDatas, TaskOptions pOpts, DataRef pIRef) {
		return true;
	}
	
	/** Get notify that outputs of a task is missing */
	protected boolean notifyMissingOutputs(ProcessContext pContext, ProcessDatas pDatas, TaskOptions pOpts) {
		return true;
	}
	
	/** Get notify that an output of a task is not compatible with its specification */
	protected boolean notifyInputIncompatible(ProcessContext pContext, ProcessDatas pDatas, TaskOptions pOpts,
			TaskEntry pTaskEntry, DataRef pRef, Object pIn) {
		return true;
	}
	
	/** Get notify that an output of a task is not compatible with its specification */
	protected boolean notifyOutputsIncompatible(ProcessContext pContext, ProcessDatas pDatas, TaskOptions pOpts,
			TaskEntry pTaskEntry, Object[] pOuts) {
		return true;
	}
	
	/** Get notify that an output of a task is not compatible with its specification */
	protected boolean notifyOutputIncompatible(ProcessContext pContext, ProcessDatas pDatas, TaskOptions pOpts,
			TaskEntry pTaskEntry, int pOutputIndex) {
		return true;
	}
	
	/** Get notify that an output of a task is not compatible with its specification */
	protected boolean notifyOutputIncompatible(ProcessContext pContext, ProcessDatas pDatas, TaskOptions pOpts,
			TaskEntry pTaskEntry, DataRef pRef, Object pOut) {
		return true;
	}
	
	/** Get notify that an output of a task is not accepted by the Data Container */
	protected boolean notifyOutputNotAccepted(ProcessContext pContext, ProcessDatas pDatas, TaskOptions pOpts,
			DataRef pIRef) {
		return true;
	}
	
	/** Get notify that the tasks is null */
	protected void notifyJustBeforeTask(TaskEntry TE, Object[] Ins, ProcessContext pContext, ProcessDatas pDatas,
			TaskOptions pOpts) {
	}
	
	/** Get notify that the tasks is null */
	protected void notifyJustAfterTask(TaskEntry TE, Object[] Ins, Object[] Outs, ProcessContext pContext,
			ProcessDatas pDatas, TaskOptions pOpts) {
	}
	
	// Perform task ----------------------------------------------------------------------------------------------------
	
	/** Perform a task */
	final protected boolean doTask(ProcessContext pContext, ProcessDatas pDatas, TaskEntry pTaskEntry,
			TaskOptions pOpts) {
		TaskEntry TE = pTaskEntry;
		if(TE == null) {
			// Notify null task
			if(!this.notifyNullTask(pContext, pDatas, pOpts)) return false;
			return true;
		}
		
		// Get the inputs
		Object[] Ins = new Object[TE.getInputCount()];
		for(int i = 0; i < Ins.length; i++) {
			DataRef InRef = TE.getInputRef(i);
			Object O = pDatas.getData(InRef);
			if(O == null) {
				// Check that if it is null because the container does not contains or becuase of it is null.
				if(!pDatas.contains(InRef)) {
					// Notify missing input - If return null, returns as false. Else, as the task entry for default.
					if(!this.notifyMissingInput(pContext, pDatas, pOpts, InRef)) return false;
					O = TE.getInputDefault(i);
				}
			}
			// Checks the class type
			Class<?> C = TE.getInputType(i);
			if((C != null) && !C.isInstance(O)) {
				if(!this.notifyInputIncompatible(pContext, pDatas, pOpts, TE, InRef, O)) return false;
			}  
			
			Ins[i] = O;
		}

		// Notify just before
		this.notifyJustBeforeTask(TE, Ins, pContext, pDatas, pOpts);
		
		// Ask the TaskEntry to perform the ask
		Object[] Outs = TE.doTask(pContext, TE, pOpts, Ins);
		
		// Notify just after
		this.notifyJustAfterTask(TE, Ins, Outs, pContext, pDatas, pOpts);
		
		if(Outs == null) {
			if(TE.getOutputCount() == 0) return true;	// Done here
			// Notify missing outputs
			return !this.notifyMissingOutputs(pContext, pDatas, pOpts);
		}
		int OCount = TE.getInputCount(); 
		if(Outs.length != OCount) {
			// Notify incompatible outputs
			if(!this.notifyOutputsIncompatible(pContext, pDatas, pOpts, TE, Outs)) return false;
		}
		// Ensure that we will not over index 
		if(OCount > Outs.length) OCount = Outs.length;
		
		for(int o = 0; o < Outs.length; o++) {
			DataRef OutRef = TE.getOutputRef(o);
			if(OutRef == null) continue;
			
			// Check output type compatibility
			Class<?> C = TE.getOutputType(o);
			if((C != null) && !C.isInstance(Outs[o])) {
				if(!this.notifyOutputIncompatible(pContext, pDatas, pOpts, TE, OutRef, Outs[o])) return false;
			} 
			
			try {
				pDatas.setData(OutRef, Outs[o]);
			} catch(Exception E) {
				// Notify output not accepted.
				if(!this.notifyOutputNotAccepted(pContext, pDatas, pOpts, OutRef)) return false;
			}
		}
		return true;
	}
	
	/** Perform the tasks */
	final protected boolean doTasks(ProcessContext pContext, ProcessDatas pDatas, TaskEntry[] pTaskEntries,
			TaskOptions pOpts) {
		if(pDatas == null) throw new NullPointerException();
		if(pTaskEntries == null) {
			// Notify Empty tasks
			return this.notifyEmptyTasks(pContext, pDatas, pOpts);
		}
		
		for(int t = 0; t < pTaskEntries.length; t++) {
			if(!this.doTask(pContext, pDatas, pTaskEntries[t], pOpts))return false;
		}
		
		return true;
	}

}

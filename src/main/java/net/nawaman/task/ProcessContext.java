package net.nawaman.task;

/** Context of Process */
public interface ProcessContext {
	
	/** Report Warning */
	public void reportWarning(String pMessage, Throwable pCause);
	/** Report Error */
	public void reportError(String pMessage, Throwable pCause);
	/** Report Fatal Error */
	public void reportFatalError(String pMessage, Throwable pCause);

}

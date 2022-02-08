package net.nawaman.util;

public interface CanBeImmutable extends MightBeImmutable {
	
	/** Make this object a immutable one. */
	public boolean toImmutable();
	
}

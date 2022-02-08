package net.nawaman.util;

public interface IsAHost {
	
	/** Checks if this host is a host of the given object */
	public boolean isHostOf(     HasHost pHasHost);
	/** Returns the name of the client as refered by this host */
	public String  getClientName(HasHost pHasHost);

}

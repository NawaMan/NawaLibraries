package net.nawaman.util;

import java.io.*;

public class UFile {
	
	/** Loads objects from a file */
	static public Serializable[] loadObjectsFromFile(String pFileName)  throws IOException {
		return UFile.loadObjectsFromFile(new File(pFileName));
	}
	
	/** Loads objects from a file */
	static public Serializable[] loadObjectsFromFile(File pFile) throws IOException {
		FileInputStream FIS = null;
		try {
			Object O = null;
			FIS = new FileInputStream(pFile);
			ObjectInputStream OIS = new ObjectInputStream(FIS);
			// Check Signature of the file
			O = OIS.readObject();
			if(!(O instanceof Serializable[]))
				throw new RuntimeException("File Loading Error: The file does not contain an array of serilizables.");
			
			return (Serializable[])O;
		} catch(IOException IOE) {
			throw IOE;
		} catch(Exception E) {
			throw new RuntimeException("File Loading Error: There is error while tring to load objects from file '"
					+ pFile.getAbsolutePath() + File.pathSeparator + pFile.getName()+"'.", E);
		} finally {
			if(FIS != null) FIS.close();
		}
	}
	
	/** Saves objects to a file */
	static public void saveObjectsToFile(String pFileName, Serializable[] pObjects)  throws IOException {
		UFile.saveObjectsToFile(new File(pFileName), pObjects);
	}
	
	/** Saves objects to a file */
	static public void saveObjectsToFile(File pFile, Serializable[] pObjects) throws IOException {
		// Save it to the file
		FileOutputStream FOS = null;
		try {
			FOS = new FileOutputStream(pFile);
			ObjectOutputStream OOS = new ObjectOutputStream(FOS);
			OOS.writeObject(pObjects);
		} finally {
			if(FOS != null) FOS.close();
		}
	}

}

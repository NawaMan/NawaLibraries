package net.nawaman.util;

import java.io.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import net.nawaman.util.datastructure.AppendableDataArray;
import net.nawaman.util.datastructure.FixedLengthDataArray;

/**
 * Class utility
 *
 * @author	Nawapunth Manusitthipol
 **/
public class UClass {
	
	/** Class array of 0 member. This is used to reduce unnecessary object creation. */
	static public final Class<?>[] EmptyClassArray = new Class<?>[0];
	
	protected UClass() {}
	
	/** Returns TYPE counterpart of the class Cls or itself (if no any) **/
	static public Class<?> getTYPE(Class<?> Cls) {
		if(Cls.isPrimitive())  return Cls;
		if(Cls.isEnum())       return Cls;
		if(Cls.isAnnotation()) return Cls;
		
		// There is no need for 'else'
		if(Cls ==   Integer.class) return   Integer.TYPE;
		if(Cls ==   Boolean.class) return   Boolean.TYPE;
		if(Cls ==    Double.class) return    Double.TYPE;
		if(Cls ==      Byte.class) return      Byte.TYPE;
		if(Cls ==      Long.class) return      Long.TYPE;
		if(Cls == Character.class) return Character.TYPE;
		if(Cls ==     Float.class) return     Float.TYPE;
		if(Cls ==     Short.class) return     Short.TYPE;
		return Cls;
	}
	
	/** Returns Class counterpart of the TYPE Cls or itself (if no any) **/
	static public Class<?> getCLASS(Class<?> Cls) {
		if(!Cls.isPrimitive()) return Cls;

		// There is no need for 'else'
		if(Cls ==   Integer.TYPE) return   Integer.class;
		if(Cls ==   Boolean.TYPE) return   Boolean.class;
		if(Cls ==    Double.TYPE) return    Double.class;
		if(Cls ==      Byte.TYPE) return      Byte.class;
		if(Cls ==      Long.TYPE) return      Long.class;
		if(Cls == Character.TYPE) return Character.class;
		if(Cls ==     Float.TYPE) return     Float.class;
		if(Cls ==     Short.TYPE) return     Short.class;
		return Cls;
	}
	
	/**
	 * Returns TYPE counterpart of the class Cls or itself (if no any).<br/>
	 * This method support array. for example getTYPE_Deep(Integer[].class) is int[].
	 **/
	static public Class<?> getTYPE_Deep(Class<?> Cls) {
		if(Cls.isPrimitive())  return Cls;
		if(Cls.isArray())      return Array.newInstance(getTYPE(Cls.getComponentType()), 0).getClass();
		if(Cls.isEnum())       return Cls;
		if(Cls.isAnnotation()) return Cls;
		
		// There is no need for 'else'
		if(Cls ==   Integer.class) return   Integer.TYPE;
		if(Cls ==   Boolean.class) return   Boolean.TYPE;
		if(Cls ==    Double.class) return    Double.TYPE;
		if(Cls ==      Byte.class) return      Byte.TYPE;
		if(Cls ==      Long.class) return      Long.TYPE;
		if(Cls == Character.class) return Character.TYPE;
		if(Cls ==     Float.class) return     Float.TYPE;
		if(Cls ==     Short.class) return     Short.TYPE;
		return Cls;
	}
	
	/**
	 * Returns Class counterpart of the TYPE Cls or itself (if no any)<br/>
	 * This method support array. for example getTYPE_Deep(int[].class) is Integer[].
	 **/
	static public Class<?> getCLASS_Deep(Class<?> Cls) {
		if(Cls.isArray())      return Array.newInstance(getCLASS(Cls.getComponentType()), 0).getClass();
		if(!Cls.isPrimitive()) return Cls;

		// There is no need for 'else'
		if(Cls ==   Integer.TYPE) return   Integer.class;
		if(Cls ==   Boolean.TYPE) return   Boolean.class;
		if(Cls ==    Double.TYPE) return    Double.class;
		if(Cls ==      Byte.TYPE) return      Byte.class;
		if(Cls ==      Long.TYPE) return      Long.class;
		if(Cls == Character.TYPE) return Character.class;
		if(Cls ==     Float.TYPE) return     Float.class;
		if(Cls ==     Short.TYPE) return     Short.class;
		return Cls;
	}
	
	/** Returns default value of the Cls. */
	static public Object getDefaultValue(Class<?> Cls) {
		if(!Cls.isArray()) {
			Class<?> T = getTYPE(Cls);
			if(T.isPrimitive()) {
				if     (T ==     int.class) return          0;
				else if(T == boolean.class) return      false;
				else if(T ==  double.class) return        0.0;
				else if(T ==    char.class) return  (char)  0;
				else if(T ==    byte.class) return  (byte)  0;
				else if(T ==    long.class) return  (long)  0;
				else if(T ==   float.class) return (float)0.0;
				else if(T ==   short.class) return (short)  0;
			}
			//if(T == String.class)     return "";
			if(T == BigInteger.class) return BigInteger.ZERO;
			if(T == BigDecimal.class) return BigDecimal.ZERO;
		}
		return null;
	}
	
	/** Checks if A can be assigned by an instance of class B */ 
	static public boolean canA_BeAssignedByInstanceOf_B(Class<?> A,Class<?> B) {
		if(A == B) return (A != Void.class) && (A != Void.TYPE);
		if((A == null) || (B == null)) return false;
		
		Class<?> CB = getCLASS(B);
		if(A == CB) return true;
		
		Class<?> TB = getTYPE(B);
		if(A == CB) return true;
		
		return A.isAssignableFrom(CB) || A.isAssignableFrom(TB);
	}
	
	/** Returns the short name of the class */
	static public String getClassShortName(Class<?> pCls) {
		if(pCls == null) return null;
		String S = pCls.getCanonicalName();
		int I = S.lastIndexOf(".");
		return (I == -1)?S:S.substring(I + 1);
	}
	
	/** Returns the class by name and return null if error occurs */
	static public Class<?> getClassByName(String Name) {
		if(Name == null) return null;
		return getClassByName(Name, null);
	}
	/** Returns the class by name and return null if error occurs */
	static public Class<?> getClassByName(String Name, ClassLoader CL) {
		if(Name == null) return null;
		try { return getClassByName_WithException(Name, CL); } catch (Exception E) { return null; }
	}
	/** Returns the class by name and throw error or exception when error occurs */
	static public Class<?> getClassByName_WithException(String Name) throws ClassNotFoundException {
		if(Name == null) throw new NullPointerException();
		return getClassByName_WithException(Name, null);
	}
	/**
	 * Returns the class by its name and throws exception if not found.
	 * 
     * @exception LinkageError                 if the linkage fails
     * @exception ExceptionInInitializerError  if the initialization provoked by this method fails
     * @exception ClassNotFoundException       if the class cannot be located
	 **/
	static public Class<?> getClassByName_WithException(String Name, ClassLoader CL) throws ClassNotFoundException {
		String OrgName = Name;
		// Try to extract the class name from the signature
		String SubClassName = "";
		while(true) {
			Class<?> Cls = null;
			try {
				// Get class by the normal mean
				Cls = (CL == null)?Class.forName(Name):Class.forName(Name, true, CL);
			} catch(ClassNotFoundException  E) {
				// When not found, try to see if the class is declared in side other class.
				// By triming the string after the last "."
				int Ind = Name.lastIndexOf('.');
				// If there is no more ".", the class is not found.
				if(Ind == -1) throw new ClassNotFoundException(OrgName);
				String S = Name.substring(Ind + 1);
				if(SubClassName.length() == 0) SubClassName = S;
				else                           SubClassName = S + "." + SubClassName; 
				Name = Name.substring(0, Ind);
				// The continue, until forName(String) return a class
				continue;
			}

			// After get the base class, get the sub class if any
			if(SubClassName.length() == 0) return Cls;
			String[] SNs = SubClassName.split("\\.");
			// Loop to get each level of sub-class.
			for(int i = 0; i < SNs.length; i++) {
				Class<?>[] SubClasses = Cls.getDeclaredClasses();
				boolean IsFound = false;
				for(int j = SubClasses.length; --j >= 0; ) {
					if(SubClasses[j].getCanonicalName().equals(Cls.getCanonicalName() + "." + SNs[i])) {
						IsFound = true;
						Cls = SubClasses[j];
						break;
					}
				}
				// Throw an error if not found
				if(!IsFound) { throw new ClassNotFoundException(OrgName); }
			} 
			return Cls;
		}
	}
	
	/** Check if the class  is static. */
	static public boolean isClassStatic(Class<?> pClass) {
		return ((pClass.getModifiers() & Modifier.STATIC) == 0);
	}
	
	/** Check if the class member pMember is static. */
	static public boolean isMemberStatic(Member pMember) {
		return ((pMember.getModifiers() & Modifier.STATIC) != 0);
	}
	
	/** Check if the class member pMember is final. */
	static public boolean isMemberFinal(Member pMember) {
		return ((pMember.getModifiers() & Modifier.FINAL) != 0);
	}
	
	/** Check if the class member pMember is abstract. */
	static public boolean isMemberAbstract(Member pMember) {
		return ((pMember.getModifiers() & Modifier.ABSTRACT) != 0);
	}
	
	/**
	 * Returns an array of class in the same package of the caller (that is not this class).
	 *
	 * @param  FilterName     - Regular expression to match the desired classes' name (null if no filtering needed)
	 * @param  FileterClass   - The super class of the desired classes (null if no filtering needed)
	 * 
	 * @return                - The array of matched classes, null if there is a problem.
	 * 
	 * @author The rest       - Nawaman  http://nawaman.net
	 * @author Package as Dir - Jon Peck http://jonpeck.com (adapted from http://www.javaworld.com/javaworld/javatips/jw-javatip113.html)
	 */
	@SuppressWarnings("unchecked")
	static <T> Class<? extends T>[] getClassesFromThisPackage(String FilterName, Class<T> FileterClass) {
		// NOTE - There are duplicate code, may refactor it later
		File    PkgPath   = null;
		String  PkgName   = null;
		String  PkgAsPath = null;
		Pattern ClsNamePattern = (FilterName == null)?null:Pattern.compile(FilterName);
		
		try {
			Class<?> Caller = null;
			// Find this class and run it
			try { int i = 5; i = i/0; } catch(Exception E) {
				try {
					StackTraceElement[] STEs = E.getStackTrace();
					Class<?>            This = Class.forName(STEs[0].getClassName());
					
					// Find the first class up the stack that is not this one
					for(int i = 1; i < STEs.length; i++) {
						Caller = Class.forName(STEs[i].getClassName());
						if(Caller != This) break;
					}
				}
				catch(Exception CCE) { return null; }
			}
			
			PkgPath   = new File (Caller.getProtectionDomain().getCodeSource().getLocation().toURI());
			PkgName   = Caller.getPackage().getName();
			PkgAsPath = PkgName.replace('.', '/') + '/';
		}
		catch (URISyntaxException URISE) {}
		catch (Throwable e)              {}	

		if(PkgPath == null) return null;

		ArrayList<Class<? extends T>> Classes = new ArrayList<Class<? extends T>>();
		if(PkgPath.isFile()) {
			if(!PkgPath.getAbsoluteFile().toString().endsWith(".jar")) return null;
			try {
				JarFile JF = new JarFile(PkgPath);
				Enumeration<JarEntry> JEs = JF.entries();
				while(JEs.hasMoreElements()) {
					JarEntry JE     = JEs.nextElement();
					String   JEName = JE.getName();
					
					// Only select the one that is a class
					if(!JEName.startsWith(PkgAsPath) || !JEName.endsWith(".class")) continue;

					// Get the class name and filter it
					String CName = JEName.substring(0, JEName.length()-6).replace('/', '.');
					if(CName.substring(PkgAsPath.length()).contains(".")) continue;
					if((ClsNamePattern != null) && !ClsNamePattern.matcher(CName).matches()) continue;
					
					// Get the class and filter it
					Class<?> C = null;
					try { C = Class.forName(CName); } catch (ClassNotFoundException e) { continue; }
					if((FileterClass != null) && !FileterClass.isAssignableFrom(C)) continue;
					
					Classes.add(C.asSubclass(FileterClass));
				}
				JF.close();
			} catch(IOException e) {}

			
		} else if(PkgPath.isDirectory()) {
			PkgPath = new File(PkgPath.getAbsoluteFile().toString() + '/' + PkgName.replace('.', '/'));
			if(!PkgPath.exists() || !PkgPath.isDirectory()) return null;
				
			// Get the list of the files contained in the package
			String[] files = PkgPath.list();
			for(int i = 0; i < files.length; i++) {
				// we are only interested in .class files
				if(!files[i].endsWith(".class")) continue;

				// Get the class name and filter it
				String CName = PkgName+'.'+files[i].substring(0, files[i].length()-6);
				if((ClsNamePattern != null) && !ClsNamePattern.matcher(CName).matches()) continue;

				// Get the class and filter it
				Class<?> C = null;
				try { C = Class.forName(CName); } catch (ClassNotFoundException e) { continue; }
				if((FileterClass != null) && !FileterClass.isAssignableFrom(C)) continue;

				Classes.add(C.asSubclass(FileterClass));
			}
		}
		
		return Classes.toArray((Class<? extends T>[])(new Class[Classes.size()]));
	}
	
	// Internal Services ---------------------------------------------------------------------------
	
	/** Result of member seach */
	static protected class SearchResult {
		static public final int ExactMatch =  0;
		static public final int NotMatch   = -1;
		
		private int      Score;
		private Object   SearchObject;
		private Object[] AdjustedParams;
		
		/** Constructs a SearchResult with score = NotMatch. */
		public SearchResult() { this.Score = NotMatch; }
		/** Constructs a SearchResult with score = ExactMatch, SearchObject and adjusted parameters. */
		public SearchResult(Object SO, Object[] pAParams) {
			this.Score          = ExactMatch;
			this.SearchObject   =         SO;
			this.AdjustedParams =   pAParams;
		}
		/** Constructs a SearchResult with score, search object and adjusted parameters = null. */
		public SearchResult(int pScore, Object SO) {
			this.Score          = pScore;
			this.SearchObject   =     SO;
			this.AdjustedParams =   null;
		}
		/** Constructs a SearchResult with score, search object and adjusted parameters. */
		public SearchResult(int pScore, Object SO, Object[] pAParams) {
			this.Score          =   pScore;
			this.SearchObject   =       SO;
			this.AdjustedParams = pAParams;
		}
		
		/** Returns the score */
		public int      getScore()        { return this.Score; }
		/** Returns the search object */
		public Object   getSearchObject() { return this.SearchObject; }
		/** Returns the adjust parameters */
		public Object[] getAdjustParams() { return this.AdjustedParams; }
		
		/** Returns the string representation of this search result. */
		@Override public String toString() {
			String SO = ((Method.class.isInstance(this.SearchObject))?"Method" + ((Method)this.SearchObject).getName():"new");
			Class<?>[] Cls = null;
			if(Method.class.isInstance(this.SearchObject))      Cls = ((Method)this.SearchObject).getParameterTypes();
			if(Constructor.class.isInstance(this.SearchObject)) Cls = ((Constructor<?>)this.SearchObject).getParameterTypes();
			if(Cls == null) SO = "null";
			else            SO += UArray.toString(Cls, "(", ")", ",");
			
			return UObject.toString(this.getClass().getDeclaringClass()) +
					"SearchResult {" +
						"Score: " + this.Score +
						"SearchObject: " + SO + "; " +
						"AdjustParameters: " + ((this.AdjustedParams == null)?"null":UArray.toString(SO, "{", "}", ", ")) +
					"}";
		}
	}
	
	/**
	 * Determine the score of the comparison of class A and B.<br/>
	 * The score can be calculated as:
	 *   &nbsp;&nbsp; -1 (SearchResult.NotMatch) when A has no relationship with B <br/>
	 *   &nbsp;&nbsp;  0 (SearchResult.ExactMatch) when A is B <br/>
	 *   &nbsp;&nbsp;  1 when an instance of B can be assigned to the a variable of type A except(A is Object).
	 *   &nbsp;&nbsp;  2 when B is an equivalent array form of A (both are array)<br/> 
	 *   &nbsp;&nbsp;  3 when both array and one of them is DataArray<br/>
	 *   &nbsp;&nbsp;  4 when B is null and A is primitive but not any<br/>
	 *   &nbsp;&nbsp;  5 when B is null and A is not any and not a primitive<br/>
	 *   &nbsp;&nbsp; 10 when A is Object.
	 *   &nbsp;&nbsp;  Other positive value when B is equivalent to A in deeper relationship<br/>
	 *   
	 *   &nbsp;&nbsp;Example:<br/>
	 *   &nbsp;&nbsp;&nbsp;&nbsp;compare(int.class,  int.class      ) is 0.
	 *   &nbsp;&nbsp;&nbsp;&nbsp;compare(int.class,  Integer.class  ) is 1.
	 *   &nbsp;&nbsp;&nbsp;&nbsp;compare(int[].class,Integer[].class) is 2.
	 *   &nbsp;&nbsp;&nbsp;&nbsp;compare(int[].class,DataArray<?>.class) is 3.
	 **/
	static public int compareClasses(Class<?> A, Class<?> B) {
		if(A == B) return SearchResult.ExactMatch;
			
		if(A == null) {
			if(B == void.class) return SearchResult.ExactMatch;
			return SearchResult.NotMatch;
		}
			
		if(B == null) {
			if(A == void.class)   return SearchResult.NotMatch;
			if(A == Object.class) return SearchResult.ExactMatch;
			try { if(A.isPrimitive()) return 4; }
			catch(Exception E) {}
			return 5;
		}
		
		if((A == void.class) || (B == void.class) ||
		   (A == Void.class) || (B == Void.class)) return SearchResult.NotMatch;
		
		if(A.isAssignableFrom(B)) {
			if(A != Object.class) return 1; 
			return 10;
		}
		
		if(A.isPrimitive() || B.isPrimitive()) {
			if(((A ==     int.class) || (A ==   Integer.class)) && ((B ==     int.class) || (B ==   Integer.class))) return 1;
			if(((A == boolean.class) || (A ==   Boolean.class)) && ((B == boolean.class) || (B ==   Boolean.class))) return 1;
			if(((A ==  double.class) || (A ==    Double.class)) && ((B ==  double.class) || (B ==    Double.class))) return 1;
			if(((A ==    char.class) || (A == Character.class)) && ((B ==    char.class) || (B == Character.class))) return 1;
			if(((A ==    byte.class) || (A ==      Byte.class)) && ((B ==    byte.class) || (B ==      Byte.class))) return 1;
			if(((A ==    long.class) || (A ==      Long.class)) && ((B ==    long.class) || (B ==      Long.class))) return 1;
			if(((A ==   short.class) || (A ==     Short.class)) && ((B ==   short.class) || (B ==     Short.class))) return 1;
			if(((A ==   float.class) || (A ==     Float.class)) && ((B ==   float.class) || (B ==     Float.class))) return 1;
		}
		
		boolean IsA_DA = DataArray.class.isAssignableFrom(A);
		boolean IsB_DA = DataArray.class.isAssignableFrom(B);
		
		if((IsA_DA || A.isArray()) && (IsB_DA || B.isArray())) {
			if(IsA_DA || IsB_DA) return 3;
			A = A.getComponentType();
			B = B.getComponentType();
			int Score = compareClasses(A, B);
			if(Score >= 0) return Score + 2; 
			 
		}
		return SearchResult.NotMatch;
	}
	/**
	 * Determine the score of the comparison of class A and object B.<br/>
	 * This method use compareClasses(Class,Class) to calculate the score except that, in case
	 * that A is an array and B is a DataArray, the type of component of B will be compared with the
	 * component type of A using compareClasses(Class,Class).
	 **/
	static public int compareClassAndObject(Class<?> A, Object B) {
		if((A == null) || (A == void.class) || (A == Void.class)) return SearchResult.NotMatch;
		if(B == null) return A.isPrimitive() ? 2 : 1;
		
		int S = compareClasses(A, B.getClass());
		
		if(A.isArray() && (B instanceof DataArray<?>)) {
			A = A.getComponentType();
			B = ((DataArray<?>)B).getComponentClass();
			
			return S + compareClasses(A, (Class<?>)B);
		}
		
		return S;
	}
	
	/**
	 * Checks if the classes of the parameter is compatible with the given parameter class array.
	 *    This method already consider DataArray as an array.
	 **/
	static protected SearchResult checkMemberByParamTypes(Object SO, Class<?>[] pPClass, Class<?>[] pParamClass) {
		if(pPClass     == null) pPClass     = UClass.EmptyClassArray;
		if(pParamClass == null) pParamClass = UClass.EmptyClassArray;
				
		boolean IsVarArgs = false;
		if(SO instanceof Constructor<?>) IsVarArgs = ((Constructor<?>)SO).isVarArgs();
		if(SO instanceof Method)         IsVarArgs = ((Method)     SO).isVarArgs();
		
		boolean IsSameLength = (pPClass.length == pParamClass.length);
		if(!IsVarArgs && !IsSameLength) return null;

		int Count  = pPClass.length;
		int LIndex = Count - 1;
		
		// Unmatch length
		if(LIndex >  pParamClass.length) return null;
		if(LIndex == pParamClass.length) {	
			Class<?>[] NewClasses = new Class<?>[pParamClass.length + 1];
			System.arraycopy(pParamClass, 0, NewClasses, 0, pParamClass.length);
			NewClasses[pParamClass.length] = pPClass[pParamClass.length];
			pParamClass = NewClasses;
			
			IsSameLength = true;
		}
		
		int Score = 0;

		// Compare all the param class before the last one
		for(int i = LIndex; --i >= 0; ) {
			int S = compareClasses(pPClass[i], pParamClass[i]);
			if(S < 0) return null; // Not match
			
			Score += S;
		}
		
		// Check in the case of VarArgs
		while((pPClass.length != 0) && (IsSameLength || IsVarArgs)) { // Use as BREAKABLE IF
			Class<?> LPClass = pPClass[LIndex];
			if(IsSameLength) {
				// Try to see if the last param is match
				int S = compareClasses(LPClass, pParamClass[LIndex]);
				if(S >= 0) {
					Score += S;
					// They match somehow, so get out if this BREAKABLE IF
					break;
				}
			}
			// The last one is not match so it may be VarArgs
			if(UArray.isArrayType(LPClass)) {
				// Compare the less of the param with the array component of the last method's param class
				LPClass = UArray.getComponentType_OfType(LPClass);
				for(int i = pParamClass.length; --i >= LIndex; ) {
					int S = compareClasses(LPClass, pParamClass[i]);
					// Not match
					if(S < 0) return null;
					
					Score += S;
				}
				// They match somehow, so get out if this BREAKABLE IF
				break;
			}
			return null;
		}
		// Only the match one will be here
		return new SearchResult(Score, SO);
	}
	
	/**
	 * Checks if the classes of the parameter is compatible with the given parameters.<br />
	 * It make use of checkMemberByParams(Object, Class<?>[], Object[], boolean) so see it.
	 * @param	pParams		 The parameter array - it must be any array, any kind of array including DataArray.
	 * @param	pIsToAdjust	 The flag indicating if an adjust parameter array should be created.
	 **/
	static protected SearchResult checkMemberByParams(Object SO, Class<?>[] pPClass, Object pParams, boolean pIsToAdjust) {
		
		if((pParams != null) && (UArray.isDataArrayInstance(pParams) || UArray.isArrayTypeOfPrimitive(pParams.getClass()))) {
			// Check it to object array
			pParams = UArray.getObjectArray(pParams);
		}
			
		return checkMemberByParams(SO, pPClass, (Object[])pParams, pIsToAdjust);
	}
	
	/** Checks if the classes of the parameter is compatible with the given parameters. **/
	@SuppressWarnings("unchecked")
	static protected SearchResult checkMemberByParams(Object SO, Class<?>[] pPClass, Object[] pParams, boolean pIsToAdjust) {
		if(pPClass == null) return null;
		
		//if((pPClass.length == 0) || (pParams        == null)) return new SearchResult(SO, UObject.EmptyObjectArray);
		//if((pPClass.length == 0) || (pParams.length ==    0)) return new SearchResult(SO, pParams);
		if(pParams == null) pParams = UObject.EmptyObjectArray;
		
		boolean IsVarArgs = false;
		if(SO instanceof Constructor) IsVarArgs = ((Constructor)SO).isVarArgs();
		if(SO instanceof Method)      IsVarArgs = ((Method)     SO).isVarArgs();
		
		boolean IsSameLength = (pPClass.length == pParams.length);
		if(!IsVarArgs && !IsSameLength) return null;
		
		int Count  = pPClass.length;
		int LIndex = Count - 1;
		
		// Unmatch length
		if(LIndex >  pParams.length) return null;
		if(LIndex == pParams.length) {
			Object[] NewParams = new Object[pParams.length + 1];
			System.arraycopy(pParams, 0, NewParams, 0, pParams.length);
			NewParams[pParams.length] = UObject.EmptyObjectArray;
			pParams = NewParams;
			
			IsSameLength = true;
		}
		
		Object[] Params = null;
		int[]    PFlags = null;
		
		// Flag help imitate the conversion of data after we are sure that the param match
		// Do the conversion based on the flag
		// 1 = P = FixedDataArray.newInstance(pPClass[i], getLength(P));
		// 2 = P = UArray.convertArray(P, pPClass[i]);	- P was set to any array that class match
		// 3 = P = ((DataArray)P).toArray();			- P was set to DataArray and pPClass[i] is an array (both component types are exact)
		
		int Score = 0;

		// Compare all the param class before the last one
		for(int i = LIndex; --i >= 0; ) {
			Object   P  = pParams[i];
			Class<?> PC = pPClass[i];
			int S = compareClassAndObject(PC, P);
			if(S < 0) {
				if(P instanceof NonNative) P = ((NonNative)P).getAsNative();
				S = compareClassAndObject(PC, P);
				if(S < 0) return null; // Not match
				pParams[i] = P;
			}
			Score += S;
			
			if(pIsToAdjust) {
				Class<?> PCT = getCLASS(PC);
				
				// Create the array param - if not yet
				if(Params == null) Params = new Object[Count];
				
				if(P == null) P = getDefaultValue(PCT);
				else if(PCT.isInstance(P)) {}	// Exact match ... so nothing
				// From here they are not exact match
				else if(DataArray.class.isAssignableFrom(PC)) {
					if(P.getClass().isArray()) {
						if(PFlags == null) PFlags = new int[Count];
						PFlags[i] = 1;	// Create DataArray from an array
					} // else Its DataArray to DataArray (no conversion)
				} else if(PC.isArray()) {
					if(PFlags == null) PFlags = new int[Count];
					if(P.getClass().isArray()) {
						// They both array but but not exact match
						PFlags[i] = 2;	// Convert array
					} else {
						// That means P is a DataArray
						PFlags[i] = 3;	// Create Array from a DataArray
					}
				}
				// Not match, so return null - This should never happen
				else throw new UnknownError("Unexceped error.");
				Params[i] = P;
			}
		}
		
		// Check in the case of VarArgs
		while((pPClass.length != 0) && (IsSameLength || IsVarArgs)) { // Use as BREAKABLE IF
			Class<?> LPClass = pPClass[LIndex];
			Object   P       = pParams[LIndex];
			
			boolean IsAssigned = false;
			if(IsSameLength) {
				// Try to see if the last param is match
				int S = compareClassAndObject(LPClass, P);
				if(S < 0) {
					if(P instanceof NonNative) P = ((NonNative)P).getAsNative();
					S = compareClassAndObject(LPClass, P);
					if(S >= 0) pParams[LIndex] = P; // Not match
				}
				if(S >= 0) {
					Score      +=    S;
					IsAssigned =  true;
					
					if(pIsToAdjust) {
						// Prepare the tail
						// Note that since we already know that the rest of the parameters are match
						//   so there is no need to use Tail and TFlags variable
						Class<?> PC  = LPClass;
						Class<?> PCT = getCLASS(PC);

						// Create the array param - if not yet
						if(Params == null) Params = new Object[Count];
						
						if(P == null) P = getDefaultValue(PCT);
						else if(PCT.isInstance(P)) {}	// Exact match ... so nothing
						// From here they are not exact match
						else if(DataArray.class.isAssignableFrom(PC)) {
							if(P.getClass().isArray()) {
								// Create DataArray from an array
								P =  FixedLengthDataArray.newInstance(P.getClass().getComponentType(), UArray.getLength(P));	
							} // else Its DataArray to DataArray (no conversion)
						} else if(PC.isArray()) {
							if(PFlags == null) PFlags = new int[Count];
							if(P.getClass().isArray()) {
								// They both array but but not exact match
								// Convert array
								P = UArray.convertArray(P, PC);
							} else {
								// That means P is a DataArray
								P = UArray.convertArray(P, PC); // Create Array from a DataArray
							}
						}
						// Not match, so return null - This should never happen
						else throw new UnknownError("Unexceped error.");						
						Params[LIndex] = P;
					}
					
					// They match somehow, so get out if this BREAKABLE IF
					break;
				}
			}
			// The last one is not match so it may be VarArgs and the last parameter must be an array
			if(!IsAssigned && IsVarArgs && UArray.isArrayType(LPClass)) {
				// Compare the less of the param with the array component of the last method's param class
				LPClass                = UArray.getComponentType_OfType(LPClass);
				Class<?> PC            = LPClass;
				Object   Tail          = null; 
				int[]    TFlags        = null;
				
				Class<?> PCT           = pIsToAdjust?getCLASS(PC):PC;
				Object   Default       = pIsToAdjust?getDefaultValue(PCT):null;
				
				for(int i = pParams.length; --i >= LIndex; ) {
					P     = pParams[i];
					int S = compareClassAndObject(LPClass, P);
					// Not match
					if(S < 0) {
						if(P instanceof NonNative) P = ((NonNative)P).getAsNative();
						S = compareClassAndObject(LPClass, P);
						if(S >= 0) pParams[LIndex] = P; // Not match
					}
					
					Score += S;
					
					if(pIsToAdjust) {
						int TailIndex = i - LIndex;
						
						if(Tail == null) Tail = Array.newInstance(LPClass, pParams.length - LIndex);
						if(P == null) P = Default;
						else if(PCT.isInstance(P)) {}	// Exact match ... so nothing
						// From here they are not exact match
						else if(DataArray.class.isAssignableFrom(PC)) {
							if(P.getClass().isArray()) {
								if(TFlags == null) TFlags = new int[Count];
								// Create DataArray from an array
								TFlags[TailIndex] = 1;	
							} // else Its DataArray to DataArray (no conversion)
						} else if(PC.isArray()) {
							if(TFlags == null) TFlags = new int[Count];
							if(P.getClass().isArray()) {
								// They both array but but not exact match
								// Convert array
								TFlags[TailIndex] = 2;	
							} else {
								// That means P is a DataArray
								// Create Array from a DataArray
								TFlags[TailIndex] = 3;	
							}
						}
						// Not match, so return null - This should never happen
						else throw new UnknownError("Unexceped error.");
						
						// Assign the array - Tail can be array primitive that Java will not convert
						Array.set(Tail, TailIndex, P);
					}
				}
				// Adjust Tail
				if(TFlags != null) {
					P = null;
					for(int i = pParams.length - LIndex; --i >= 0; ) {
						switch(TFlags[i]) {
							case(1): {
								// Create DataArray from an array
								P = Array.get(Tail, i);
								P = FixedLengthDataArray.newInstance(P.getClass().getComponentType(), Array.getLength(P));
								break;
							}
							case(2): {
								// Convert array
								P = Array.get(Tail, i);
								P = UArray.convertArray(P, PC, false);
								break;
							}
							case(3): {
								// Create Array from a DataArray
								P = Array.get(Tail, i);
								//P = ((DataArray)P).toArrayOf(PC.getComponentType());
								P = UArray.convertArray(P, PC);
								break;
								
							}
							default: continue;
						}
						
						// Assign the array - Tail can be array primitive that Java will not convert
						Array.set(Tail, i, P);
						
						P = null;
					}
				}

				// Create the array param - if not yet
				if(Params == null) Params = new Object[Count];
				
				Params[LIndex] = Tail;
				
				// They match somehow, so adjust the tailnad get out if this BREAKABLE IF
				break;
			}
			return null;
		}
		
		// Actually adjust the param
		if(pIsToAdjust && (PFlags != null) && (Params != null)) {
			Object P = null;
			for(int i = LIndex; --i >= 0; ) {
				switch(PFlags[i]) {
					case(1): {
						P = Params[i];
						P = FixedLengthDataArray.newInstance(P.getClass().getComponentType(), Array.getLength(P));
						break;
					}
					case(2): {
						P = Params[i];
						P = UArray.convertArray(P, pPClass[i], false);
						break;
					}
					case(3): {
						P = Params[i];
						//P = ((DataArray)P).toArrayOf(pPClass[i]);
						P = UArray.convertArray(P, pPClass[i]);
						break;
						
					}
					default: continue;
				}
				Params[i] = P;
			}
		}
		
		// Only the match one will be here
		return new SearchResult(Score, SO, Params);
	}
	
	/** Search for constructor by the param classes. */
	static private SearchResult searchConstructorByParamClasses(Class<?> pClass, Class<?>[] pParamClass) {
		// Not found for sure.
		if(pClass == null)       return null;
		if(pClass.isInterface()) return null;
		
		Constructor<?>[] Csts = pClass.getConstructors();
		if((Csts == null) || (Csts.length == 0)) return null;
		
		if((pParamClass == null) || (pParamClass.length == 0)) {
			for(Constructor<?> C : Csts) {
				if(C.getParameterTypes().length == 0)
					return new SearchResult(C, null);
			}
		}
		
		SearchResult MinSR = null;
		for(Constructor<?> Cst : Csts) {
			Class<?>[] CstPClass = Cst.getParameterTypes();
			SearchResult SR = checkMemberByParamTypes(Cst, CstPClass, pParamClass);
			if((SR == null) || (SR.Score == -1)) continue;
			if(SR.Score == 0) return SR;
			if((MinSR == null) || (SR.Score < MinSR.Score)) MinSR = SR;
		}

		// Found it easily so return.
		if((MinSR != null) && (MinSR.Score != SearchResult.NotMatch)) return MinSR;
		return null;
	}
	
	/** Search for constructor by the params. */
	static private SearchResult searchConstructorByParams(Class<?> pClass, Object pParams, boolean IsToAdjust) {
		// Not found for sure.
		if(pClass == null)       return null;
		if(pClass.isInterface()) return null;
		
		Constructor<?>[] Csts = pClass.getConstructors();
		if((Csts == null) || (Csts.length == 0)) return null;

		if((pParams != null) && !UArray.isArrayInstance(pParams))
			throw new IllegalArgumentException("Invalid input parameter ("+ UObject.toDetail(pParams) +").");
		
		int PLength = UArray.getLength(pParams);
		Object[] TheParams = new Object[PLength];
		
		Class<?>[] ParamClass = null;
		if((pParams != null) && (PLength != 0)) {
			// Prepare parameter class array
			ParamClass = new Class<?>[PLength];
			for(int i = PLength; --i >= 0; ) {
				Object P = UArray.get(pParams, i);
				if(P == null) continue;
				ParamClass[i] = P.getClass();
				TheParams[i]  = P;
			}
		}

		SearchResult MinSR = null;
		
		if(!IsToAdjust) {
			MinSR = searchConstructorByParamClasses(pClass, ParamClass);
	
			// Found it easily so return.
			if(!((MinSR == null) || (MinSR.Score == SearchResult.NotMatch))) return MinSR;
		}

		// Search by parameter class array
		MinSR = null;
		for(Constructor<?> Cst : Csts) {
			Class<?>[] CstPClass = Cst.getParameterTypes();
			SearchResult SR = checkMemberByParams(Cst, CstPClass, TheParams, true);
			if((SR == null) || (SR.Score == -1)) continue;
			
			if(SR.AdjustedParams == null) SR.AdjustedParams = TheParams;
			if(SR.Score == 0) return SR;
			
			if((MinSR == null) || (SR.Score < MinSR.Score)) MinSR = SR;
		}

		// Found it easily so return.
		if((MinSR != null) && (MinSR.Score != SearchResult.NotMatch)) return MinSR;
		return null;
	}
	
	/** Search for constructor by the param classes. */
	static protected SearchResult searchMethodByParamClasses(Class<?> pClass, String pName, Class<?>[] pParamClass,
			boolean IsStatic) {
		// Not found for sure.
		if(pClass == null) return null;
		if(pName  == null) return null;
		
		Method[] Mtds = pClass.getMethods();
		if((Mtds == null) || (Mtds.length == 0)) return null;
		
		// Filter the name
		{
			Vector<Method> LMtds = new Vector<Method>();
			for(int i = Mtds.length; --i >= 0; ){
				Method Mtd = Mtds[i];
				if(!pName.equals(Mtd.getName())) continue;
				if(IsStatic == isMemberStatic(Mtd))
					LMtds.add(Mtd);
			}
			int LMthSize = LMtds.size();
			if(LMthSize == 0) return null;
			if(Mtds.length != LMthSize) Mtds = LMtds.toArray(new Method[0]);
		}
		
		if((pParamClass == null) || (pParamClass.length == 0)) {
			for(Method M : Mtds) {
				if(M.getParameterTypes().length == 0)
					return new SearchResult(M, null);
			}
		}
		
		SearchResult MinSR = null;
		for(Method Mtd : Mtds) {
			Class<?>[] CstPClass = Mtd.getParameterTypes();
			SearchResult SR = checkMemberByParamTypes(Mtd, CstPClass, pParamClass);
			if((SR == null) || (SR.Score == -1)) continue;
			
			if(SR.Score == 0) return SR;
			
			if((MinSR == null) || (SR.Score < MinSR.Score)) MinSR = SR;
		}
		// Found it easily so return.
		if((MinSR != null) && (MinSR.Score != SearchResult.NotMatch)) return MinSR;
		return null;	
	}
	
	/** Search for constructor by the params. */
	static private SearchResult searchMethodByParams(Class<?> pClass, String pName, Object pParams,
			                        boolean IsStatic, boolean IsToAdjust) {
		// Not found for sure.
		if(pClass == null) return null;

		if((pParams != null) && !UArray.isArrayInstance(pParams))
			throw new IllegalArgumentException("Invalid input parameter ("+ UObject.toDetail(pParams) +").");
		
		Method[] Mtds = pClass.getMethods();
		if((Mtds == null) || (Mtds.length == 0)) return null;
		
		// Filter the name
		{
			Vector<Method> LMtds = new Vector<Method>();
			for(int i = Mtds.length; --i >= 0; ){
				Method Mtd = Mtds[i];
				if(pName.equals(Mtd.getName()) && (isMemberStatic(Mtd) || !IsStatic)) LMtds.add(Mtd);
			}
			int LMthSize = LMtds.size();
			if(LMthSize == 0) return null;
			if(Mtds.length != LMthSize) Mtds = LMtds.toArray(new Method[0]);
		}
		
		int PLength = UArray.getLength(pParams);
		
		if((pParams == null) || (PLength == 0)) {
			for(Method M : Mtds) {
				if(M.getParameterTypes().length == 0)
					return new SearchResult(M, null);
			}
		}
		
		Class<?>[] ParamClass = new Class<?>[PLength];
		Object[]   TheParams  = new Object[PLength];
		
		// Prepare parameter class array
		for(int i = PLength; --i >= 0; ) {
			Object P = UArray.get(pParams, i);
			if(P == null) continue;
			ParamClass[i] = P.getClass();
			TheParams[i]  = P;
		}

		SearchResult MinSR = null;
		if(!IsToAdjust) {	// If there is a need to adjust the param, go the checkMemberByParams
			//Search by parameter class array
			for(Method Mtd : Mtds) {
				Class<?>[] CstPClass = Mtd.getParameterTypes();
				SearchResult SR = checkMemberByParamTypes(Mtd, CstPClass, ParamClass);
				if((SR == null) || (SR.Score == -1)) continue;
				if(SR.AdjustedParams == null) SR.AdjustedParams = TheParams;
				if(SR.Score == 0) return SR;
				if((MinSR == null) || (SR.Score < MinSR.Score)) MinSR = SR;
			}
	
			// Found it easily so return.		
			if((MinSR != null) && (MinSR.Score != SearchResult.NotMatch)) return MinSR;
		}
		
		// Search by parameter array
		MinSR = null;
		for(Method Mtd : Mtds) {
			Class<?>[] CstPClass = Mtd.getParameterTypes();
			SearchResult SR = checkMemberByParams(Mtd, CstPClass, TheParams, true);
			if((SR == null) || (SR.Score == -1)) continue;
			
			if(SR.AdjustedParams == null) SR.AdjustedParams = TheParams;
			if(SR.Score == 0) return SR;
			
			if((MinSR == null) || (SR.Score < MinSR.Score)) MinSR = SR;
		}

		// Found it easily so return.
		if((MinSR != null) && (MinSR.Score != SearchResult.NotMatch)) return MinSR;
		return null;
	}
	
	// Constructors --------------------------------------------------------------------------------
	/**  Returns the constructor of the class Class by the array of the parameter classes. */
	static public Constructor<?> getConstructorByParamClasses(Class<?> pClass, Class<?>[] pParamClass) {

		if(pClass == null) return null;
		
		SearchResult SR = searchConstructorByParamClasses(pClass, pParamClass);
		if(SR != null) return (Constructor<?>)SR.SearchObject;
		
		return null;
	}
	
	/**  Returns the constructor of the class Class that can be invoked with the parameter Params. */
	static public Constructor<?> getConstructorByParams(Class<?> pClass, Object pParams) {

		if(pClass == null) return null;

		if((pParams != null) && !UArray.isArrayInstance(pParams))
			throw new IllegalArgumentException("Invalid input parameter new "+UObject.toString(pClass)+"("+ UObject.toDetail(pParams) +").");
		
		SearchResult SR = searchConstructorByParams(pClass, pParams, false);
		if(SR != null) return (Constructor<?>)SR.SearchObject;
		
		return null;
	}
	
	/**
	 * Creates a new instance using the constructor pConstructor and the parameter Params.<br />
	 * <b>Usage Note:</b> Since there is over computation for searching the constructor, use this 
	 *   method when the constructor is expected to be reused for more than a few times.
	 * 
     * @exception InvocationTargetException    if the underlying constructor throws an exception.
     * @exception IllegalArgumentException     if the number of actual and formal parameters differ; 
     *                                           if an unwrapping conversion for primitive arguments 
     *                                           fails; or if, after possible unwrapping, a
     *                                           parameter value cannot be converted to the
     *                                           corresponding formal parameter type by a method
     *                                           invocation conversion; if this constructor pertains
     *                                           to an enum type.
     * @exception IllegalAccessException       if this <code>Constructor</code> object enforces Java
     *                                           language access control and the underlying
     *                                           constructor is inaccessible.
     * @exception InstantiationException       if the class that declares the underlying constructor 
     *                                           represents an abstract class.
     * @exception ExceptionInInitializerError  if the initialization provoked by this method fails.
	 **/
	static public <T> T newInstance(Constructor<T> pConstructor, Object pParams) throws
	                       InvocationTargetException, IllegalAccessException, InstantiationException {
		if(pConstructor == null) throw new NullPointerException();
		
		if((pParams != null) && !UArray.isArrayInstance(pParams))
			throw new IllegalArgumentException("Invalid input parameter new "+UObject.toString(pConstructor.getDeclaringClass())+"("+ UObject.toDetail(pParams) +").");
		
		Constructor<T> C = pConstructor;
		
		SearchResult SR = checkMemberByParams(C, C.getParameterTypes(), pParams, true);
		if((SR == null) || (SR.SearchObject != C)) {
			// Something wrong
			throw new IllegalArgumentException(
					"Invalid input parameter for "+UObject.toDetail(C.getDeclaringClass()) + "." +
					C.getName()+"("+ UObject.toDetail(pParams) +").");
		}
		
		return C.newInstance(SR.AdjustedParams);
	}
	/**
	 * Creates a new instance of the class Cls with the parameter Params.<br />
	 * <b>Usage Note:</b> This method should be used when the instantiation will be done only a few
	 * times. This because there is a computational cost of adjusting parameter array that is done
	 * for both searching and execution. The method simple reuse the adjustment.
	 * 
     * @exception InvocationTargetException    if the underlying constructor throws an exception.
     * @exception IllegalArgumentException     if the number of actual and formal parameters differ; 
     *                                           if an unwrapping conversion for primitive arguments 
     *                                           fails; or if, after possible unwrapping, a
     *                                           parameter value cannot be converted to the
     *                                           corresponding formal parameter type by a method
     *                                           invocation conversion; if this constructor pertains
     *                                           to an enum type.
     * @exception IllegalAccessException       if this <code>Constructor</code> object enforces Java
     *                                           language access control and the underlying
     *                                           constructor is inaccessible.
     * @exception InstantiationException       if the class that declares the underlying constructor 
     *                                           represents an abstract class.
     * @exception ExceptionInInitializerError  if the initialization provoked by this method fails.
	 **/
	@SuppressWarnings("unchecked")
	static public <T> T newInstance(Class<T> Cls, Object Params)
	                       throws InvocationTargetException, IllegalAccessException,
	                              InstantiationException, NoSuchMethodException {
		if(Cls == null) throw new NullPointerException();
		
		if((Params != null) && !UArray.isArrayInstance(Params))
			throw new IllegalArgumentException("Invalid input parameter new "+UObject.toString(Cls)+"("+ UObject.toDetail(Params) +").");
		
		SearchResult SR = searchConstructorByParams(Cls, Params, true);
		
		if((SR == null) || (SR.SearchObject == null))	// Not found
			throw new NoSuchMethodException (Cls + "(" + UArray.toString(Params, "", "", ",") + ")");
		
		return ((Constructor<T>)SR.SearchObject).newInstance(SR.AdjustedParams);
	}
	
	// Method --------------------------------------------------------------------------------------
	
	/**
	 * Returns the method of the class pClass that can be invoked with the parameter Params.
	 * The parameter IsStatic indicates that if the required method should be static.
	 * 
	 * @exception IllegalArgumentException will be thrown if the paremeter is not a valid array. 
	 **/
	static public Method getMethod(Class<?> pClass, String pMName, boolean pIsStatic, Object pParams) {
		if((pClass == null) || (pMName == null)) throw new NullPointerException();
		
		if((pParams != null) && !UArray.isArrayInstance(pParams))
			throw new IllegalArgumentException("Invalid input parameter new "+UObject.toString(pClass)+"("+ UObject.toDetail(pParams) +").");
		
		SearchResult SR = searchMethodByParams(pClass, pMName, pParams, pIsStatic, false);	// NoAdjust
		if(SR != null) return (Method)SR.SearchObject;
		
		return null;
	}

	/**
	 * Returns the method of the class pClass that can be invoked with the parameter of Param classs.
	 * The parameter IsStatic indicates that if the required method should be static.
	 * 
	 * @exception IllegalArgumentException will be thrown when the class is abstract. 
	 **/
	static public Method getMethodByParamClasses(Class<?> pClass, String pMName, boolean pIsStatic, Class<?>[] pPClasses) {
		if((pClass == null) || (pMName == null)) throw new NullPointerException();
		
		SearchResult SR = searchMethodByParamClasses(pClass, pMName, pPClasses, pIsStatic);	// NoAdjust
		if(SR != null) return (Method)SR.SearchObject;
		
		return null;
	}
	
	/**
	 * Returns the object method of the class pClass that can be invoked with the parameter Params.
	 * 
	 * @exception IllegalArgumentException will be thrown when the class is abstract. 
	 **/
	static public Method getObjectMethod(Class<?> pClass, String pMName, Object pParams) {
		return getMethod(pClass, pMName, false, pParams);
	}
	
	/**
	 * Returns the object method of the class pClass that can be invoked with the parameter Params.
	 * 
	 * @exception IllegalArgumentException will be thrown when the class is abstract. 
	 **/
	static public Method getObjectMethodByParamClasses(Class<?> pClass, String pMName, Class<?>[] pPClasses) {
		return getMethodByParamClasses(pClass, pMName, false, pPClasses);
	}
	
	/**
	 * Returns the static method of the class pClass that can be invoked with the parameter Params.
	 * The parameter IsStatic indicates that if the required method should be static.
	 * 
	 * @exception IllegalArgumentException will be thrown when the class is abstract. 
	 **/
	static public Method getStaticMethod(Class<?> pClass, String pMName, Object pParams) {
		return getMethod(pClass, pMName, true, pParams);
	}
	
	/**
	 * Returns the static method of the class pClass that can be invoked with the parameter Params.
	 * The parameter IsStatic indicates that if the required method should be static.
	 * 
	 * @exception IllegalArgumentException will be thrown when the class is abstract. 
	 **/
	static public Method getStaticMethodByParamClasses(Class<?> pClass, String pMName, Class<?>[] pPClasses) {
		return getMethodByParamClasses(pClass, pMName, true, pPClasses);
	}
	
	/**
	 * Invokes the method pMethod to the object pObject (null if the method is static) with
	 *   parameters Params. Throws appropriate exception if any error occurs.<br />
	 * <b>Usage Note:</b> Since there is over computation for searching the method, use this method 
	 *   when the method is expected to be reused for more than a few times.
	 * 
	 * @exception InvocationTargetException	when the method cannot be invoked on pObject
	 * @exception IllegalAccessException	when access permission is not adequate
	 **/
	static public Object invokeMethod(Method pMethod, Object pObject, Object pParams)
	                        throws InvocationTargetException, IllegalAccessException {
		if(pMethod == null) throw new NullPointerException();
		Method Mtd = pMethod;
		
		if((pParams != null) && !UArray.isArrayInstance(pParams))
			throw new IllegalArgumentException("Invalid input parameter new "+UObject.toString(Mtd.getDeclaringClass())+"("+ UObject.toDetail(pParams) +").");
		
		// Do this to adjust the parameter
		SearchResult SR = checkMemberByParams(Mtd, Mtd.getParameterTypes(), pParams, true);
		if((SR == null) || (SR.SearchObject != Mtd)) {
			// Something wrong
			throw new IllegalArgumentException(
					"Invalid input parameter for "+UObject.toDetail(Mtd.getDeclaringClass()) + "." +
					Mtd.getName()+"("+ UObject.toDetail(pParams) +").");
		}
		
		Object Result = Mtd.invoke(pObject, SR.AdjustedParams);
		
		// Ensure that the result is in the correct type
		if((Result != null) && Mtd.getReturnType().isArray()) {
			Object O = UArray.convertArray(Result, Mtd.getReturnType());
			if(O != null) Result = O; 
		}
		
		return Result;
	}

	/**
	 * Invokes the method named pMName of the class pClass to the object pObject (null if the method 
	 *   is static) with parameters Params. Throws appropriate exception if any error occurs. The
	 *   parameter IsStatic indicates that if the required method should be static.<br />
	 * <b>Usage Note:</b> This method should be used when the invocation will be done only a few
	 *   times. This because there is a computational cost of adjusting parameter array that is done
	 *   for both searching and execution. The method simple reuse the adjustment.
	 * 
	 * @exception NoSuchMethodException        if there is no method with the name or parameter
     * @exception IllegalAccessException       if this <code>Method</code> object enforces Java
     *                                           language access control and the underlying method
     *                                           is inaccessible.
     * @exception IllegalArgumentException     if the method is an instance method and the specified
     *                                           object argument is not an instance of the class or
     *                                           interface declaring the underlying method (or of a
     *                                           subclass or implementor thereof); if the number of
     *                                           actual and formal parameters differ; if an
     *                                           unwrapping conversion for primitive arguments
     *                                           fails; or if, after possible unwrapping, a
     *                                           parameter value cannot be converted to the
     *                                           corresponding formal parameter type by a method
     *                                           invocation conversion.
     * @exception InvocationTargetException    if the underlying method throws an exception.
     * @exception NullPointerException         if the specified object is null and the method is an
     *                                           instance method.
     * @exception ExceptionInInitializerError  if the initialization provoked by this method fails.
	 **/
	static public Object invokeMethod(Class<?> pClass, String pMName, boolean IsStatic, Object pObject,
			                Object pParams) throws InvocationTargetException,
			                IllegalAccessException, NoSuchMethodException {
		if((pClass == null) || (pMName == null)) throw new NullPointerException();
		
		if((pParams != null) && !UArray.isArrayInstance(pParams))
			throw new IllegalArgumentException("Invalid input parameter new "+UObject.toString(pClass)+"("+ UObject.toDetail(pParams) +").");
		
		SearchResult SR = searchMethodByParams(pClass, pMName, pParams, IsStatic, true);
		if(SR == null)	// Not found
			throw new NoSuchMethodException(UObject.toDetail(pClass) + "." + pMName + UArray.toString(pParams, "", "", ",") );
		
		Method Mtd = (Method)SR.SearchObject;
		
		Object Result = Mtd.invoke(pObject, SR.AdjustedParams);
		
		// Ensure that the result is in the correct type
		if((Result != null) && Mtd.getReturnType().isArray()) {
			Object O = UArray.convertArray(Result, Mtd.getReturnType());
			if(O != null) Result = O; 
		}
		
		return Result;
	}
	
	/**
	 * Invokes the object method named pMName of the class pClass to the object pObject (null if the  
	 *   method is static) with parameters Params. Throws appropriate exception if any error occurs.
	 *    The parameter IsStatic indicates that if the required method should be static.<br />
	 * <b>Usage Note:</b> This method should be used when the invocation will be done only a few
	 *   times. This because there is a computational cost of adjusting parameter array that is done
	 *   for both searching and execution. The method simple reuse the adjustment.
	 * 
	 * @exception NoSuchMethodException        if there is no method with the name or parameter
     * @exception IllegalAccessException       if this <code>Method</code> object enforces Java
     *                                           language access control and the underlying method
     *                                           is inaccessible.
     * @exception IllegalArgumentException     if the method is an instance method and the specified
     *                                           object argument is not an instance of the class or
     *                                           interface declaring the underlying method (or of a
     *                                           subclass or implementor thereof); if the number of
     *                                           actual and formal parameters differ; if an
     *                                           unwrapping conversion for primitive arguments
     *                                           fails; or if, after possible unwrapping, a
     *                                           parameter value cannot be converted to the
     *                                           corresponding formal parameter type by a method
     *                                           invocation conversion.
     * @exception InvocationTargetException    if the underlying method throws an exception.
     * @exception NullPointerException         if the specified object is null and the method is an
     *                                           instance method.
     * @exception ExceptionInInitializerError  if the initialization provoked by this method fails.
	 **/
	static public Object invokeObjectMethod(Object pObject, String pMName, Object pParams)
	                        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		return invokeMethod(pObject.getClass(), pMName, false, pObject, pParams);
	}
	
	/**
	 * Invokes the static method named pMName of the class pClass with parameters Params. Throws
	 * appropriate exception if any error occurs.
	 *    The parameter IsStatic indicates that if the required method should be static.<br />
	 * <b>Usage Note:</b> This method should be used when the invocation will be done only a few
	 *   times. This because there is a computational cost of adjusting parameter array that is done
	 *   for both searching and execution. The method simple reuse the adjustment.
	 * 
	 * @exception NoSuchMethodException        if there is no method with the name or parameter
     * @exception IllegalAccessException       if this <code>Method</code> object enforces Java
     *                                           language access control and the underlying method
     *                                           is inaccessible.
     * @exception IllegalArgumentException     if the method is an instance method and the specified
     *                                           object argument is not an instance of the class or
     *                                           interface declaring the underlying method (or of a
     *                                           subclass or implementor thereof); if the number of
     *                                           actual and formal parameters differ; if an
     *                                           unwrapping conversion for primitive arguments
     *                                           fails; or if, after possible unwrapping, a
     *                                           parameter value cannot be converted to the
     *                                           corresponding formal parameter type by a method
     *                                           invocation conversion.
     * @exception InvocationTargetException    if the underlying method throws an exception.
     * @exception NullPointerException         if the specified object is null and the method is an
     *                                           instance method.
     * @exception ExceptionInInitializerError  if the initialization provoked by this method fails.
	 **/
	static public Object invokeStaticMethod(Class<?> pClass, String pMName, Object pParams)
	                        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		return invokeMethod(pClass, pMName, true, null, pParams);
	}
	
	// Fields --------------------------------------------------------------------------------------

	/**
	 * Returns the field of the given class pClass that is associated with the name pFieldName. The
	 * flag IsStatic indicates that the required field should be static.
	 **/
	static public Field getField(Class<?> pClass, String pFieldName, boolean pIsStatic) {
		if(pClass == null) return null;
		
		try {
			Field F = pClass.getField(pFieldName);				
			if(pIsStatic != isMemberStatic(F)) return null;
			return F;
		} catch(NoSuchFieldException E) {
			return null;
		}
	}
	
	/**
	 * Returns the object field of the given class pClass that is associated with the name 
	 * pFieldName.
	 **/
	static public Field getObjectField(Class<?> pClass, String pFieldName) {
		return getField(pClass, pFieldName, false);
	}
	
	/**
	 * Returns the static object field of the given class pClass that is associated with the name 
	 * pFieldName.
	 **/
	static public Field getStaticField(Class<?> pClass, String pFieldName) {
		return getField(pClass, pFieldName, true);
	}
	
	/**
	 * Set the field pField of the object pObject (null is static) by the value pValue. Throws
	 * appropriate exception if any error occurs.<br />
	 * <b>Usage Note:</b> Since there is over computation for searching the field, use this field 
	 *   when the field is expected to be reused for more than a few times.
	 **/
	static public Object setFieldValue(Field pField, Object pObject, Object pValue)
	                         throws IllegalAccessException {
		if(pField == null) throw new NullPointerException();
		
		if(pField.getType().isArray()) {
			// Convert array type if necessary
			Object NewValue = UArray.convertArray(pValue, pField.getType());
			if(NewValue != null) pValue = NewValue;
		}
		
		pField.set(pObject, pValue);
		return pValue;
	}

	/**
	 * Set the pField of the object pObject (null is static) by the value pValue. Throws appropriate
	 * exception if any error occurs.<br />
	 * <b>Usage Note:</b> Since there is over computation for searching the field, use this field 
	 *   when the field is expected to be reused for more than a few times.
	 **/
	static public Object setFieldValue(Class<?> pClass, String pFieldName, Object pObject, boolean pIsStatic, Object pValue)
	                         throws IllegalAccessException, NoSuchFieldException {
		
		Field F = getField(pClass, pFieldName, pIsStatic);
		if(F == null) throw new NoSuchFieldException(UObject.toString(pClass) + "." + pFieldName);
		
		return setFieldValue(F, pObject, pValue);
	}

	/**
	 * Set the object field pField of the object pObject (null is static) by the value pValue. Throws
	 * appropriate exception if any error occurs.<br />
	 * <b>Usage Note:</b> Since there is over computation for searching the field, use this field 
	 *   when the field is expected to be reused for more than a few times.
	 **/
	static public Object setObjectFieldValue(Object pObject, String pFieldName, Object pValue)
	                         throws IllegalAccessException, NoSuchFieldException {
		Field F = getField(pObject.getClass(), pFieldName, false);
		if(F == null) throw new NoSuchFieldException(UObject.toString(pObject.getClass()) + "." + pFieldName);
		
		return setFieldValue(F, pObject, pValue);
	}

	/**
	 * Set the static field named pFieldName of the class pClass, Throws appropriate exception if
	 * any error occurs.<br />
	 * <b>Usage Note:</b> Since there is over computation for searching the field, use this field 
	 *   when the field is expected to be reused for more than a few times.
	 **/
	static public Object setStaticFieldValue(Class<?> pClass, String pFieldName, Object pValue)
	                         throws IllegalAccessException, NoSuchFieldException {
		
		Field F = getField(pClass, pFieldName, true);
		if(F == null) throw new NoSuchFieldException(UObject.toString(pClass) + "." + pFieldName);
		
		return setFieldValue(F, null, pValue);
	}
	
	// Get field value ----------------------------------------------
	
	/**
	 * Get the field pField of the object pObject (null is static). Throws appropriate exception if
	 * any error occurs.<br />
	 * <b>Usage Note:</b> Since there is over computation for searching the field, use this field 
	 *   when the field is expected to be reused for more than a few times. 
	 **/
	static public Object getFieldValue(Field pField, Object pObject) throws IllegalAccessException {
		if(pField == null) throw new NullPointerException();
		else               return pField.get(pObject);
	}
	
	/**
	 * Get the field pField of the object pObject (null is static). Throws appropriate exception if
	 * any error occurs.<br />
	 * <b>Usage Note:</b> Since there is over computation for searching the field, use this field 
	 *   when the field is expected to be reused for more than a few times. 
	 **/
	static public Object getFieldValue(Class<?> pClass, String pFieldName, Object pObject, boolean pIsStatic)
	                        throws IllegalAccessException, NoSuchFieldException {

		Field F = getField(pClass, pFieldName, pIsStatic);
		if(F == null) throw new NoSuchFieldException(UObject.toString(pClass) + "." + pFieldName);
		
		return getFieldValue(F, pObject);
	}
	
	/**
	 * Get the object field pField of the object pObject. Throws appropriate exception if any error
	 * occurs.<br />
	 * <b>Usage Note:</b> Since there is over computation for searching the field, use this field 
	 *   when the field is expected to be reused for more than a few times. 
	 **/
	static public Object getObjectFieldValue(Object pObject, String pFieldName)
	                        throws IllegalAccessException, NoSuchFieldException {
		Field F = getField(pObject.getClass(), pFieldName, false);
		if(F == null) throw new NoSuchFieldException(UObject.toString(pObject.getClass()) + "." + pFieldName);
		
		return getFieldValue(F, pObject);
	}
	
	/**
	 * Get the static field pField. Throws appropriate exception if any error occurs.<br />
	 * <b>Usage Note:</b> Since there is over computation for searching the field, use this field 
	 *   when the field is expected to be reused for more than a few times. 
	 **/
	static public Object getStaticFieldValue(Class<?> pClass, String pFieldName)
	                        throws IllegalAccessException, NoSuchFieldException {

		Field F = getField(pClass, pFieldName, true);
		if(F == null) throw new NoSuchFieldException(UObject.toString(pClass) + "." + pFieldName);

		return getFieldValue(F, null);
	}
	
	// Testing -------------------------------------------------------------------------------------
	
	/** For Testing. */
	static public int testSum(int ... pIs) {
		if(pIs == null) return 0;
		int Sum = 0;
		for(int i = pIs.length; --i >= 0; ) Sum += pIs[i];
		return Sum;
	}
	/** For Testing. */
	static public int[] testSumEach(int[][] pIs) {
		if(pIs == null) return null;
		int[] Sums = new int[pIs.length];
		for(int i = pIs.length; --i >= 0; ) {
			Sums[i] = 0; 
			for(int j = pIs[i].length; --j >= 0; ) Sums[i] += pIs[i][j];
		}
		return Sums;
	}
	/** For Testing. */
	static public void main(String ... Args) throws Exception {
		System.out.println(getMethod(   UArray.class, "toString", true,       new Object[] { new Integer[] {1, 2, 3} }));
		
		try {
		System.out.println(invokeMethod(UArray.class, "toString", true, null, new Object[] { new Integer[] {1, 2, 3} }));
		} catch (Exception E) {
			E.printStackTrace();
		}
		
		System.out.println(getMethod(   UClass.class, "getClassByName", true,       new Object[] { "java.util.List", null }));
		System.out.println(invokeMethod(UClass.class, "getClassByName", true, null, new Object[] { "java.util.List", null }));

		System.out.println(getMethod(   UClass.class, "testSum", true,       new Object[] { new int[] {1, 2, 3} }));
		System.out.println(invokeMethod(UClass.class, "testSum", true, null, new Object[] { new int[] {1, 2, 3} }));
		System.out.println(getMethod(   UClass.class, "testSum", true,                      new int[] {1, 2, 3}  ));
		System.out.println(invokeMethod(UClass.class, "testSum", true, null,                new int[] {1, 2, 3}  ));

		System.out.println(getMethod(   UClass.class, "testSum", true,       FixedLengthDataArray.newInstance( new int[]     {1, 2, 3} )));
		System.out.println(invokeMethod(UClass.class, "testSum", true, null, FixedLengthDataArray.newInstance( new int[]     {1, 2, 3} )));
		System.out.println(getMethod(   UClass.class, "testSum", true,       FixedLengthDataArray.newInstance( new Integer[] {1, 2, 3} )));
		System.out.println(invokeMethod(UClass.class, "testSum", true, null, FixedLengthDataArray.newInstance( new Integer[] {1, 2, 3} )));
				
		System.out.println(UObject.toString(getMethod(   UClass.class, "testSumEach", true,       new Object[] { new int[][]     { { 1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10} } })));
		System.out.println(UObject.toString(invokeMethod(UClass.class, "testSumEach", true, null, new Object[] { new int[][]     { { 1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10} } })));
		System.out.println(UObject.toString(getMethod(   UClass.class, "testSumEach", true,       new Object[] { new Integer[][] { { 1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10} } })));
		System.out.println(UObject.toString(invokeMethod(UClass.class, "testSumEach", true, null, new Object[] { new Integer[][] { { 1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10} } })));
		
		System.out.println(UObject.toString(getMethod(   UClass.class, "testSumEach", true,       new Object[] { FixedLengthDataArray.newInstance(new int[][]     { { 1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10} } ) })));
		System.out.println(UObject.toString(invokeMethod(UClass.class, "testSumEach", true, null, new Object[] { FixedLengthDataArray.newInstance(new int[][]     { { 1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10} } ) })));
		System.out.println(UObject.toString(getMethod(   UClass.class, "testSumEach", true,       new Object[] { FixedLengthDataArray.newInstance(new Integer[][] { { 1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10} } ) })));

		System.out.println(UObject.toString(invokeMethod(UClass.class, "testSumEach", true, null, new Object[] { FixedLengthDataArray.newInstance(new Integer[][] { { 1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10} } ) })));

		
		int[]              Is    = new int[]     { 1, 2, 3, 4, 5 };
		Integer[]          Ints  = new Integer[] { 1, 2, 3, 4, 5 };
		DataArray<Integer> DAI   = FixedLengthDataArray.newInstance(Is);
		DataArray<Integer> DAInt = FixedLengthDataArray.newInstance(Ints);
		
		System.out.println("- TestSum ------------");
		System.out.println(testSum(Is));
		System.out.println("----------------------");
		
		Method M = null;
		M = getMethod(getClassByName(UClass.class.getCanonicalName()),"testSum", true, Is);
		System.out.println(UObject.toString(invokeMethod(M, null, Is)));
		
		M = getMethod(getClassByName(UClass.class.getCanonicalName()),"testSum", true, Ints);
		System.out.println(UObject.toString(invokeMethod(M, null, Ints)));
		
		M = getMethod(getClassByName(UClass.class.getCanonicalName()),"testSum", true, new Object[] { DAI });
		System.out.println(UObject.toString(invokeMethod(M, null, new Object[] { DAI })));
		
		M = getMethod(getClassByName(UClass.class.getCanonicalName()),"testSum", true, new Object[] { DAInt });
		System.out.println(UObject.toString(invokeMethod(M, null, new Object[] { DAInt })));
		
		int[][]              Iss    = new int[][]     { { 1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10} };
		Integer[][]          Intss  = new Integer[][] { { 1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10} };
		DataArray<int[]>     DAIs   = FixedLengthDataArray.newInstance(Iss);
		DataArray<Integer[]> DAInts = FixedLengthDataArray.newInstance(Intss);
		
		System.out.println("- TestSumEachs --------");
		System.out.println(UObject.toString(Iss));
		System.out.println(UObject.toString(testSumEach(Iss)));
		System.out.println("----------------------");

		M = getMethod(getClassByName(UClass.class.getCanonicalName()),"testSumEach", true, new Object[] { Iss });
		System.out.println(UObject.toString(invokeMethod(M, null, new Object[] { Iss })));
		
		M = getMethod(getClassByName(UClass.class.getCanonicalName()),"testSumEach", true, new Object[] { Intss });
		System.out.println(UObject.toString(invokeMethod(M, null, new Object[] { Intss })));

		M = getMethod(getClassByName(UClass.class.getCanonicalName()),"testSumEach", true, new Object[] { Iss });
		System.out.println(UObject.toString(invokeMethod(M, null, new Object[] { DAIs })));
		
		M = getMethod(getClassByName(UClass.class.getCanonicalName()),"testSumEach", true, new Object[] { Intss });
		System.out.println(UObject.toString(invokeMethod(M, null, new Object[] { DAInts })));
		
		DAIs   = AppendableDataArray.newInstance(Iss);
		DAInts = AppendableDataArray.newInstance(Intss);

		M = getMethod(getClassByName(UClass.class.getCanonicalName()),"testSumEach", true, new Object[] { Iss });
		System.out.println(UObject.toString(invokeMethod(M, null, new Object[] { DAIs })));
		
		M = getMethod(getClassByName(UClass.class.getCanonicalName()),"testSumEach", true, new Object[] { Intss });
		System.out.println(UObject.toString(invokeMethod(M, null, new Object[] { DAInts })));
		/* */
		System.out.println("- Test Speed ----------");
		
		int Count = 1000;
		
		long[] Times = new long[4];
		long   Start = 0;
		
		// Warmup
		
		// Java
		Start = System.currentTimeMillis();
		for(int i = Count; --i >= 0; ) { testSumEach(Iss); }
		Times[0] = System.currentTimeMillis() - Start;
		
		// Reflection
		Start = System.currentTimeMillis();
		for(int i = Count; --i >= 0; ) {
			try {
				Method M1 = UClass.class.getMethod("testSumEach", new Class<?>[] { int[][].class });
				M1.invoke(null, new Object[] { Iss });
			} catch(Exception E) {}
		}
		Times[1] = System.currentTimeMillis() - Start;
		
		// Curry
		Start = System.currentTimeMillis();
		for(int i = Count; --i >= 0; ) { invokeMethod(UClass.class, "testSumEach", true, null, new Object[] { Iss }); }
		Times[2] = System.currentTimeMillis() - Start;

		// Curry w/ Cache
		Start = System.currentTimeMillis();
		M     = getMethod(getClassByName(UClass.class.getCanonicalName()),"testSumEach", true, new Object[] { Iss });
		for(int i = Count; --i >= 0; ) { invokeMethod(M, null, new Object[] { Iss }); }
		Times[3] = System.currentTimeMillis() - Start;
		
		// Actually test
		Count = 100;
		
		// Java
		Start = System.currentTimeMillis();
		for(int i = Count; --i >= 0; ) { testSumEach(Iss); }
		Times[0] = System.currentTimeMillis() - Start;
		
		// Reflection
		Start = System.currentTimeMillis();
		for(int i = Count; --i >= 0; ) {
			try {
				Method M1 = UClass.class.getMethod("testSumEach", new Class<?>[] { int[][].class });
				M1.invoke(null, new Object[] { Iss });
			} catch(Exception E) {}
		}
		Times[1] = System.currentTimeMillis() - Start;
		
		// Curry
		Start = System.currentTimeMillis();
		for(int i = Count; --i >= 0; ) { invokeMethod(UClass.class, "testSumEach", true, null, new Object[] { Iss }); }
		Times[2] = System.currentTimeMillis() - Start;

		// Curry w/ Cache
		Start = System.currentTimeMillis();
		M     = getMethod(getClassByName(UClass.class.getCanonicalName()),"testSumEach", true, new Object[] { Iss });
		for(int i = Count; --i >= 0; ) { invokeMethod(M, null, new Object[] { Iss }); }
		Times[3] = System.currentTimeMillis() - Start;
		
		System.out.println("Loop " + Count + " times:");
		
		System.out.println("Java:             " + Times[0] + " milliseconds.");
		System.out.println("Reflection:       " + Times[1] + " milliseconds.");
		System.out.println("Curry:            " + Times[2] + " milliseconds.");
		System.out.println("Curry w/ caching: " + Times[3] + " milliseconds.");
		
		/* Results
		 * Loop 1000000 times:
		 * Java:             91   milliseconds.
		 * Reflection:       1085 milliseconds.
		 * Curry:            6348 milliseconds.
		 * Curry w/ caching: 1470 milliseconds.
		 */

		Class<?> Cls = getClassByName(UClass.SearchResult.class.getCanonicalName());
		System.out.println(Cls);
		Cls = getClassByName("java.io.File");
		System.out.println(Cls);
		System.out.println(newInstance(getClassByName("java.io.File"), new Object[] { "/home/nawa/Desktop/ToDelete" } ));
		System.out.println(new File("/home/nawa/Desktop/ToDelete"));
		Constructor<?> C = getConstructorByParams(getClassByName("java.io.File"), new Object[] { "/home/nawa/Desktop/ToDelete" });
		System.out.println(C);
		System.out.println(newInstance(C, new Object[] { "/home/nawa/Desktop/ToDelete" }));
	}
	
}

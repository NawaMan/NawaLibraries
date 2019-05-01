package net.nawaman.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

// Assume that null of Number is "(int)0"

/**
 * Number utility
 *
 * @author	Nawapunth Manusitthipol
 **/
public class UNumber {

	/** Integer array of 0 member. This is used to reduce unnecessary object creation. */
	static public final Number[]      EmptyNumberArray      = new Number[0];
	static public final Byte[]        EmptyByteArray        = new Byte[0];
	static public final Short[]       EmptyShortArray       = new Short[0];
	static public final Integer[]     EmptyIntegerArray     = new Integer[0];
	static public final Long[]        EmptyLongArray        = new Long[0];
	static public final Float[]       EmptyFloatArray       = new Float[0];
	static public final Double[]      EmptyDoubleArray      = new Double[0];
	static public final BigInteger[]  EmptyBigIntegerArray  = new BigInteger[0];
	static public final BigDecimal[]  EmptyBigDecimalArray  = new BigDecimal[0];
	static public final byte[]        Empty_ByteArray       = new byte[0];
	static public final short[]       Empty_ShortArray      = new short[0];
	static public final int[]         Empty_IntegerArray    = new int[0];
	static public final long[]        Empty_LongArray       = new long[0];
	static public final float[]       Empty_FloatArray      = new float[0];
	static public final double[]      Empty_DoubleArray     = new double[0];
	
	static public final BigDecimal BD_MAX_BYTE_VALUE   = getBigDecimal(Byte   .MAX_VALUE);
	static public final BigDecimal BD_MIN_BYTE_VALUE   = getBigDecimal(Byte   .MIN_VALUE);
	static public final BigDecimal BD_MAX_SHORT_VALUE  = getBigDecimal(Short  .MAX_VALUE);
	static public final BigDecimal BD_MIN_SHORT_VALUE  = getBigDecimal(Short  .MIN_VALUE);
	static public final BigDecimal BD_MAX_INT_VALUE    = getBigDecimal(Integer.MAX_VALUE);
	static public final BigDecimal BD_MIN_INT_VALUE    = getBigDecimal(Integer.MIN_VALUE);
	static public final BigDecimal BD_MAX_LONG_VALUE   = getBigDecimal(Long   .MAX_VALUE);
	static public final BigDecimal BD_MIN_LONG_VALUE   = getBigDecimal(Long   .MIN_VALUE);
	static public final BigDecimal BD_MAX_FLOAT_VALUE  = getBigDecimal(Float  .MAX_VALUE);
	static public final BigDecimal BD_MIN_FLOAT_VALUE  = getBigDecimal(Float  .MIN_VALUE);
	static public final BigDecimal BD_MAX_DOUBLE_VALUE = getBigDecimal(Double .MAX_VALUE);
	static public final BigDecimal BD_MIN_DOUBLE_VALUE = getBigDecimal(Double .MIN_VALUE);

	static public final BigInteger BI_MAX_BYTE_VALUE   = getBigInteger(Byte   .MAX_VALUE);
	static public final BigInteger BI_MIN_BYTE_VALUE   = getBigInteger(Byte   .MIN_VALUE);
	static public final BigInteger BI_MAX_SHORT_VALUE  = getBigInteger(Short  .MAX_VALUE);
	static public final BigInteger BI_MIN_SHORT_VALUE  = getBigInteger(Short  .MIN_VALUE);
	static public final BigInteger BI_MAX_INT_VALUE    = getBigInteger(Integer.MAX_VALUE);
	static public final BigInteger BI_MIN_INT_VALUE    = getBigInteger(Integer.MIN_VALUE);
	static public final BigInteger BI_MAX_LONG_VALUE   = getBigInteger(Long   .MAX_VALUE);
	static public final BigInteger BI_MIN_LONG_VALUE   = getBigInteger(Long   .MIN_VALUE);
	static public final BigInteger BI_MAX_FLOAT_VALUE  = getBigInteger(Float  .MAX_VALUE);
	static public final BigInteger BI_MIN_FLOAT_VALUE  = getBigInteger(Float  .MIN_VALUE);
	static public final BigInteger BI_MAX_DOUBLE_VALUE = getBigInteger(Double .MAX_VALUE);
	static public final BigInteger BI_MIN_DOUBLE_VALUE = getBigInteger(Double .MIN_VALUE);
	
	private UNumber() {}

	/**
	 * Number Type Enumeration type
	 *
	 * @author	Nawapunth Manusitthipol
	 **/
	static public enum NumberType {
		/** Number type of byte */
		BYTE,
		/** Number type of short */
		SHORT,
		/** Number type of integer */
		INT,
		/** Number type of float */
		FLOAT,
		/** Number type of long */
		LONG,
		/** Number type of double */
		DOUBLE,
		/** Number type of big integer */
		BIGINTEGER,
		/** Number type of big decimal */
		BIGDECIMAL;
		
		/** Returns the maximum number type between this value and the type of number N. */
		static public NumberType valueOf(Number N) {
			if     (N instanceof    Integer) return INT;
			else if(N instanceof     Double) return DOUBLE;
			else if(N instanceof       Long) return LONG;
			else if(N instanceof       Byte) return BYTE;
			else if(N instanceof BigInteger) return BIGINTEGER;
			else if(N instanceof BigDecimal) return BIGDECIMAL;
			else if(N instanceof      Short) return SHORT;
			else if(N instanceof      Float) return FLOAT;
			return INT;
		}
		/** Returns the maximum number type between this value and the type of number N. */
		static public NumberType valueOf(Class<? extends Number> NC) {
			if     (NC ==    Integer.class) return INT;
			else if(NC ==     Double.class) return DOUBLE;
			else if(NC ==       Long.class) return LONG;
			else if(NC ==       Byte.class) return BYTE;
			else if(NC == BigInteger.class) return BIGINTEGER;
			else if(NC == BigDecimal.class) return BIGDECIMAL;
			else if(NC ==      Short.class) return SHORT;
			else if(NC ==      Float.class) return FLOAT;
			return INT;
		}
		
		/** Returns the maximum number type between this value and the type of number N. */
		public NumberType max(Number N) {
			return max(this, valueOf(N));
		}
		/** Returns the name of the type. */
		public String getName() {
			if(this == BIGINTEGER) return "BigInteger";
			if(this == BIGDECIMAL) return "BigDecimal";
			return this.toString().charAt(0) + this.toString().toLowerCase().substring(1);
		}
		
		public Class<? extends Number> getNumberClass() {
			switch(this) {
				case BYTE:       return Byte      .class;
				case SHORT:      return Short     .class;
				case INT:        return Integer   .class;
				case LONG:       return Long      .class;
				case FLOAT:      return Float     .class;
				case DOUBLE:     return Double    .class;
				case BIGINTEGER: return BigInteger.class;
				case BIGDECIMAL: return BigDecimal.class;
			}
			return Number.class;
		}
		
		public NumberType next() {
			switch(this) {
				case BYTE:       return NumberType.SHORT;
				case SHORT:      return NumberType.INT;
				case INT:        return NumberType.LONG;
				case LONG:       return NumberType.BIGINTEGER;
				case FLOAT:      return NumberType.DOUBLE;
				case DOUBLE:     return NumberType.BIGDECIMAL;
				case BIGINTEGER: return NumberType.BIGINTEGER;
				default:         return NumberType.BIGDECIMAL;
			}
		}
		
		/** Returns the maximum number type between this value and the type of number N. */
		static public NumberType max(NumberType NT1, NumberType NT2) {
			// From Small
			if(NT1 == null) return (NT2 == null)?BYTE:NT2;
			if(NT2 == null) return NT1;
			if(NT1 == BYTE) return NT2;
			if(NT2 == BYTE) return NT1;
			
			// From Big
			if((NT1 == BIGDECIMAL) || (NT2 == BIGDECIMAL)) return BIGDECIMAL;
			
			if(NT1 == BIGINTEGER) { if((NT2 == DOUBLE) || (NT2 == FLOAT)) return BIGDECIMAL; return NT1; }
			if(NT2 == BIGINTEGER) { if((NT1 == DOUBLE) || (NT1 == FLOAT)) return BIGDECIMAL; return NT2; }
			
			if((NT1 == DOUBLE) || (NT2 == DOUBLE)) return DOUBLE;
			
			if(NT1 == FLOAT) { if((NT2 == LONG) || (NT2 == INT)) return DOUBLE; return FLOAT; }
			if(NT2 == FLOAT) { if((NT1 == LONG) || (NT1 == INT)) return DOUBLE; return FLOAT; }
			
			if((NT1 ==   LONG) || (NT2 ==   LONG)) return   LONG;
			if((NT1 ==    INT) || (NT2 ==    INT)) return    INT;
			if((NT1 ==  SHORT) || (NT2 ==  SHORT)) return  SHORT;
			return null;
		}
	}
	
	/** Obtaini BigInteger from number */
	static public BigInteger getBigInteger(String N) { return new BigInteger(N);           }
	static public BigInteger getBigInteger(byte   N) { return BigInteger.valueOf(N);       }
	static public BigInteger getBigInteger(short  N) { return BigInteger.valueOf(N);       }
	static public BigInteger getBigInteger(int    N) { return BigInteger.valueOf(N);       }
	static public BigInteger getBigInteger(long   N) { return BigInteger.valueOf(N);       }
	static public BigInteger getBigInteger(float  N) { return BigInteger.valueOf((long)N); }
	static public BigInteger getBigInteger(double N) { return BigInteger.valueOf((long)N); }
	static public BigInteger getBigInteger(Number N) {
		if(N == null)               return BigInteger.valueOf(0);
		if(N instanceof BigDecimal) return ((BigDecimal)N).toBigInteger();
		if(N instanceof BigInteger) return (BigInteger)N;
		return BigInteger.valueOf(N.longValue());
	}
	

	/** Obtaini BigDecimal from number */
	static public BigDecimal getBigDecimal(String N) { return new BigDecimal(N);             }
	static public BigDecimal getBigDecimal(byte   N) { return BigDecimal.valueOf((double)N); }
	static public BigDecimal getBigDecimal(short  N) { return BigDecimal.valueOf((double)N); }
	static public BigDecimal getBigDecimal(int    N) { return BigDecimal.valueOf((double)N); }
	static public BigDecimal getBigDecimal(long   N) { return BigDecimal.valueOf((double)N); }
	static public BigDecimal getBigDecimal(float  N) { return BigDecimal.valueOf((double)N); }
	static public BigDecimal getBigDecimal(double N) { return BigDecimal.valueOf((double)N); }
	static public BigDecimal getBigDecimal(Number N) {
		if(N == null)               return BigDecimal.valueOf(0.0);
		if(N instanceof BigDecimal) return (BigDecimal)N;
		if(N instanceof BigInteger) return BigDecimal.valueOf(1.0).multiply(new BigDecimal((BigInteger)N));
		return BigDecimal.valueOf(N.doubleValue());	// ^-- Ensure that it is wrapping the real number not integer
	}
	

	// Constants used for Plus
	static private final int        Plus_Overfloat_CkB   = 0x80000040;
	static private final int        Plus_Overfloat_CkS   = 0x80004000;
	static private final int        Plus_Overfloat_CkI   = 0xC0000000;
	static private final long       Plus_Overfloat_CkL   = 0xC000000000000000L;
	static private final long       Plus_Overfloat_CkFDM = 0x7FE0000000000000L;
	static private final long       Plus_Overfloat_CkF   = 0x47E0000000000000L;
	static private final long       Plus_Overfloat_CkD   = 0x7FD0000000000000L;
	static private final int        Plus_CanContainInB   = 0x7FFFFF80;
	static private final int        Plus_CanContainInS   = 0x7FFF8000;
	static private final long       Plus_CanContainInI   = 0x7FFFFFFF80000000L;
	static private final double     DOUBLE_SROOT         = Math.sqrt(Double.MAX_VALUE);
	static private final BigInteger LONGMAX_BIGINT       = getBigInteger(Long.MAX_VALUE);
	static private final BigInteger LONGMIN_BIGINT       = getBigInteger(Long.MIN_VALUE);
	static private final BigDecimal DOUBLEMAX_BIGDEC     = getBigDecimal( Double.MAX_VALUE);
	static private final BigDecimal DOUBLEMIN_BIGDEC     = getBigDecimal(-Double.MAX_VALUE);
	
	
	/**
	 * Plus operation for whatever number type is.
	 * 
	 * The result will be in the largest input number types. For example, int + long will be long.
	 * If the result require larger number type, the method will automatically upgrade to the larger one.
	 * For example, Integer.MAX_VALUE + 1 will be a long.
	 * 
	 * This method also process BigInteger and BigDecimal.
	 * 
	 * NOTE: Due to the way sign is process, upgrading for integer number (byte, short, int, long) is slightly differ
	 *         from the real number (float and double). Integer number has one less positive number than its possible
	 *         negative number as on of the possitive number is used for zero. Real number has two zero notations.
	 *       Integer number will be upgraded when 2*HALF(of Max) + 2 precision while,
	 *       Real    number will be upgraded when 2*HALF(of Max) + 1 precision.
	 **/
	static public Number plus(Number ... pParams) {
		if(pParams        == null) return 0;									// Returns because summation of [] is 0
		if(pParams.length ==    0) return 0;									// Returns because summation of [] is 0
		if(pParams.length ==    1) return (pParams[0] == null)?0:pParams[0];	// Returns because summation of [0]
		
		Number     N  = pParams[0];
		NumberType NT = NumberType.valueOf(N);
		NumberType NNT;                         // Next Number Type
		NumberType UpFrom = null;               // Is Upgraded from
		
		int        i = 0;
		long       l = 0;
		double     d = 0;
		BigInteger I = null;
		BigDecimal D = null;
		
		switch(NT) {
			case BYTE:
			case SHORT:
			case INT:        i = N.intValue();     break;
			
			case LONG:       l = N.longValue();    break;
			
			case FLOAT: 
			case DOUBLE:     d = N.doubleValue();  break;
			
			case BIGINTEGER: I = getBigInteger(N); break;
			case BIGDECIMAL: D = getBigDecimal(N); break;
		}
		
		for(int n = 1; n < pParams.length; n++) {
			N = pParams[n];
			if((NumberType.max(NT, (NNT = NumberType.valueOf(N))) != NT)) {
				UpFrom = null;
				switch(NT) {
					case BYTE: 
					case SHORT: 
					case INT: {
						boolean IsToFloat = false;
						switch(NNT) {
							case LONG:       l = i;                break;
							case FLOAT:      IsToFloat = true;
							case DOUBLE:     d = i;                break;
							case BIGINTEGER: I = getBigInteger(i); break;
							case BIGDECIMAL: D = getBigDecimal(i); break;
						}
						// Float + Int = Double
						if(IsToFloat && (NT == NumberType.INT)) NT = NumberType.DOUBLE;
						else                                    NT = NNT;
						break;
					}
					case LONG: {
						NT = NNT;
						switch(NNT) {
							case FLOAT:      NT = NumberType.DOUBLE; 
							case DOUBLE:     d = l;                break;
							case BIGINTEGER: I = getBigInteger(l); break;
							case BIGDECIMAL: D = getBigDecimal(l); break;
						}
						break;
					}
					case FLOAT:
					case DOUBLE: {
						if(NNT == NumberType.BIGINTEGER) NT = NumberType.BIGDECIMAL;
						else                             NT = NNT;
						switch(NNT) {
							case BIGINTEGER: 
							case BIGDECIMAL: D = getBigDecimal(d); break;
						}
						break;
					}
					case BIGINTEGER: {
						D = getBigDecimal(I); NT = NumberType.BIGDECIMAL;
						break;
					} 
				}
			}

			boolean InLimit = false;
			switch(NT) {
				case BYTE:
				case SHORT:
				case INT: {
					int     CkI;
					boolean IsInt;
					if(IsInt = (NT == NumberType.INT))  CkI = Plus_Overfloat_CkI;
					else if(    NT == NumberType.BYTE)  CkI = Plus_Overfloat_CkB;
					else                                CkI = Plus_Overfloat_CkS;
						
					int Ni  = N.intValue();
					int Ci  = (i  & CkI); 
					int CNi = (Ni & CkI); 
					InLimit = ((Ci == 0) || (Ci == CkI)) && ((CNi == 0) || (CNi == CkI));
					
					// None of them are more than half of the maximum value, Go to the next value
					if(InLimit) { i += Ni; break; }
					
					// Upgrade to long if needed (when overfloat from Int)
					if(IsInt) l = ((long)i + (long)Ni);	// Change the variable to l, when upgrade to LONG
					else      i += Ni;
					
					break;
				}
				
				case LONG: {
					long CkL = Plus_Overfloat_CkL;
					long Nl  = N.longValue();
					long Cl  = (l  & CkL); 
					long CNl = (Nl & CkL); 
					InLimit = ((Cl == 0) || (Cl == CkL)) && ((CNl == 0) || (CNl == CkL));
					
					// None of them are more than half of the maximum value, Go to the next value
					if(InLimit) { l += N.longValue(); break; }
					
					// Upgrade to BigInteger
					I = getBigInteger(l).add(getBigInteger(Nl));
					
					break;
				}

				case FLOAT:
				case DOUBLE: {
					long    CkD;
					boolean IsDouble;
					if(IsDouble = (NT == NumberType.DOUBLE)) CkD = Plus_Overfloat_CkD;
					else                                     CkD = Plus_Overfloat_CkF;

					double Nd  = N.doubleValue();
					long   Cd  = (Double.doubleToLongBits(d)  & Plus_Overfloat_CkFDM); 
					long   CNd = (Double.doubleToLongBits(Nd) & Plus_Overfloat_CkFDM); 
					InLimit = (Cd < CkD) && (CNd < CkD);	// #### Differ greatly from integer-liked number.
					// Haft-max of Float  value in double will be as soon as 0x47E0000000000000 (just above half)
					// Haft-max of Double value in double will be as soon as 0x7FE0000000000000 (just above half)

					// None of them are more than half of the maximum value, Go to the next value
					if(InLimit) { d += Nd; break; }

					// Upgrade to BigDecimal if needed (when overfloat from Double)
					if(IsDouble) D = getBigDecimal(d).add(getBigDecimal(Nd)); // Change the variable to D, when upgrade to BigDecimal
					else         d += Nd;
					
					break;
				}
				
				case BIGINTEGER: { I = I.add(getBigInteger(N)); InLimit = true; break; }
				case BIGDECIMAL: { D = D.add(getBigDecimal(N)); InLimit = true; break; }
			}
			
			if(!InLimit) { // Upgrade
				UpFrom = NT;
				NT     = NT.next();
			}
		}

		// Check if the upgrade is not necessary before return
		switch(NT) {
			case BYTE:  return (byte) i;
			case FLOAT: return (float)d;
			
			case SHORT:  { if(UpFrom != null) { int  Chk = i & Plus_CanContainInB; if((Chk == 0)  || (Chk == Plus_CanContainInB)) return (byte) i; } return (short)i; }
			case INT:    { if(UpFrom != null) { int  Chk = i & Plus_CanContainInS; if((Chk == 0)  || (Chk == Plus_CanContainInS)) return (short)i; } return        i; }
			case LONG:   { if(UpFrom != null) { long Chk = l & Plus_CanContainInI; if((Chk == 0L) || (Chk == Plus_CanContainInI)) return (int)  l; } return        l; }
			case DOUBLE: {
				if(UpFrom != null) {
					if((((float)d) <= Float.MAX_VALUE) && (((float)d) >= -Float.MAX_VALUE)) return (float)d;
					return d;/*
					long Chk = Double.doubleToLongBits(d) & Plus_Overfloat_CkFDM;
					if(Chk < Plus_CanContainInF) return (float)d;*/	// #### Differ greatly from integer-liked number.
					// Max of Float  value in double will be as soon as 0x47F0000000000000 (just above max of float)
				}
				return d;
			}
			
			case BIGINTEGER: {
				// Checks if this is an overfloat from LONG and if the value can still be put in long
				if((UpFrom != null) &&
					!((I.compareTo(LONGMAX_BIGINT) > 0) ||
					  (I.compareTo(LONGMIN_BIGINT) < 0)))
					return I.longValue();
				return I;
			}
			case BIGDECIMAL: {
				if((UpFrom != null) &&
					!((D.compareTo(DOUBLEMAX_BIGDEC) > 0) ||
					  (D.compareTo(DOUBLEMIN_BIGDEC) < 0)))
					return D.doubleValue();
				return D;
			}
		}

		return 0;	// Returns 0 because  accumulative-summation of non-number is not a number which is represented by 0
	}

	/** Multiply operation for whatever number type is. */
	static public Number multiply(Number ... pParams) {
		if(pParams        == null) return 1;	// Returns because accumulative-multiplication of [] is 1
		if(pParams.length ==    0) return 1;	// Returns because accumulative-multiplication of [] is 1
		if(pParams.length ==    1) return (pParams[0] == null)?1:pParams[0];
		
		boolean HasZero = false;
		NumberType NT = NumberType.BYTE;
		for(int i = 0; i < pParams.length; i++) {
			if(pParams[i] == null) HasZero = true;	// Returns 0 because Null of Number is 0
			NT = NT.max(pParams[i]);
		}
		NumberType MNT = NT;	// Max Number Type
		
		if(HasZero) {
			switch(NT) {
				case BYTE:       return (byte) 0;
				case SHORT:      return (short)0;
				case INT:        return        0;
				case LONG:       return        0L;
				case FLOAT:      return (float)0.0;
				case DOUBLE:     return        0.0;
				case BIGINTEGER: getBigInteger(0);
				case BIGDECIMAL: getBigDecimal(0);
			}
			return 0;
		}
		
		Number     N = pParams[0];
		int        n = 1;
		int        i = 1;
		long       l = 1;
		double     d = 1;
		BigInteger I = null;
		BigDecimal D = null;
		
		switch(NT) {
			case BYTE:
			case SHORT:
			case INT:        i = N.intValue();     break;
			case LONG:       l = N.longValue();    break;
			case FLOAT: 
			case DOUBLE:     d = N.doubleValue();  break;
			case BIGINTEGER: I = getBigInteger(N); break;
			case BIGDECIMAL: D = getBigDecimal(N); break;
		}

		// i > 0x0000B504L or i < 0xFFFF4AFCL
		// l > 0x00000000B504F333L or l < 0xFFFFFFFF4AFB0CCDL
		// (d & 0x5FE0000000000000) > 0x5FE0000000000000 

		if((NT == NumberType.INT) || (NT == NumberType.BYTE) || (NT == NumberType.SHORT)) {
			for(; n < pParams.length; ) {
				N = pParams[n++];

				int Ni = N.intValue();
				boolean InLimit = !(((i > 0x0000B504) || (i < 0xFFFF4AFC)) || ((Ni > 0x0000B504) || (Ni < 0xFFFF4AFC)));
				
				// None of them are more than half of the maximum value, Go to the next value
				if(InLimit) { i *= Ni; continue; }

				// Upgrade to Long
				l  = ((long)i * (long)Ni);
				NT = NumberType.LONG;
				
				break;
			}
		}
		
		if(NT == NumberType.LONG) {
			for(; n < pParams.length; ) {
				N = pParams[n++];

				long Nl = N.longValue();
				boolean InLimit = !(((l  > 0x00000000B504F333L) || (l  < 0xFFFFFFFF4AFB0CCDL)) ||
				                    ((Nl > 0x00000000B504F333L) || (Nl < 0xFFFFFFFF4AFB0CCDL)));
				
				// None of them are more than half of the maximum value, Go to the next value
				if(InLimit) { l *= Nl; continue; } 

				// Upgrade to BigInteger
				I = getBigInteger(l).multiply(getBigInteger(Nl));
				NT = NumberType.BIGINTEGER;
				
				break;
			}
		}
		
		if((NT == NumberType.DOUBLE) || (NT == NumberType.FLOAT)) {
			for(; n < pParams.length; ) {
				N = pParams[n++];

				double Nd = N.doubleValue();
				boolean InLimit = !(((d  >= DOUBLE_SROOT) || (d  <= -DOUBLE_SROOT)) ||
				                    ((Nd >= DOUBLE_SROOT) || (Nd <= -DOUBLE_SROOT)));
				
				// None of them are more than half of the maximum value, Go to the next value
				if(InLimit) { d *= Nd; continue; }

				// Upgrade to BigDecimal
				D = getBigDecimal(d).multiply(getBigDecimal(Nd));
				NT = NumberType.BIGDECIMAL;
				
				break;
			}
		}

		if(NT == NumberType.BIGINTEGER) {
			for(; n < pParams.length; ) {
				I = I.multiply(getBigInteger(pParams[n++]));
			}
		}

		if(NT == NumberType.BIGDECIMAL) {
			for(; n < pParams.length; ) {
				D = D.multiply(getBigDecimal(pParams[n++]));
			}
		}

		// Check if the upgrade is not necessary before return
		switch(NT) {
			case BYTE:  { int  Chk = i & Plus_CanContainInB; if((Chk == 0)  || (Chk == Plus_CanContainInB)) return (byte) i; }
			case SHORT: { int  Chk = i & Plus_CanContainInS; if((Chk == 0)  || (Chk == Plus_CanContainInS)) return (short)i; }
			case INT:   return i;
			case LONG:  {
				if(MNT != NT) {
					long Chk = l & Plus_CanContainInI; if((Chk == 0L) || (Chk == Plus_CanContainInI))
						return (int)l;
				}
				return l;
			}
			
			case FLOAT: {
				if((((float)d) <= Float.MAX_VALUE) && (((float)d) >= -Float.MAX_VALUE)) return (float)d;
				return d;
			}
			case DOUBLE: {
				if(MNT != NT) {
					if((((float)d) <= Float.MAX_VALUE) && (((float)d) >= -Float.MAX_VALUE)) return (float)d;
					return d;
				}
				return d;
			}
			
			case BIGINTEGER: {
				// Checks if this is an overfloat from LONG and if the value can still be put in long
				if((MNT != NT) &&
					!((I.compareTo(LONGMAX_BIGINT) > 0) ||
					  (I.compareTo(LONGMIN_BIGINT) < 0)))
					return I.longValue();
				return I;
			}
			case BIGDECIMAL: {
				if((MNT != NT) &&
					!((D.compareTo(DOUBLEMAX_BIGDEC) > 0) ||
					  (D.compareTo(DOUBLEMIN_BIGDEC) < 0)))
					return D.doubleValue();
				return D;
			}
		}
		
		return 0;	// Returns 0 because  accumulative-multiplication of non-number is not a number which is represented by 0
	}
	
	// Param1 - Param2
	/** Subtract operation for whatever number type is. */
	static public Number subtract(Number pParam1, Number pParam2) {
		if(pParam1 == null) {
			if(pParam2 == null) return 0;
			return neg(pParam2);
		}
		if(pParam2 == null) return pParam1;
		
		return plus(pParam1, neg(pParam2));
	}
	
	// Param1 / Param2
	/** Divide operation for whatever number type is. */
	static public Number divide(Number pParam1, Number pParam2) {
		if(pParam1 == null) pParam1 = 0;
		if(pParam2 == null) pParam2 = 0;
		
		NumberType NT = NumberType.BYTE;
		NT = NT.max(pParam1);
		NT = NT.max(pParam2);
		
		switch(NT) {
			case       BYTE: return (pParam1.byteValue()   / pParam2.byteValue());
			case      SHORT: return (pParam1.shortValue()  / pParam2.shortValue());
			case        INT: return  pParam1.intValue()    / pParam2.intValue();
			case       LONG: return (pParam1.longValue()   / pParam2.longValue());
			case BIGINTEGER: return getBigInteger(pParam1).divide(getBigInteger(pParam2));
			case BIGDECIMAL: {
				try { return getBigDecimal(pParam1).divide(getBigDecimal(pParam2)); }
				catch(ArithmeticException AE) {
					if(AE.getMessage().startsWith("Non-terminating decimal expansion"))
						return getBigDecimal(pParam1.doubleValue()/pParam2.doubleValue());
					throw AE;
				}
			}
			
			case  FLOAT: {
				double D = pParam1.doubleValue() / pParam2.doubleValue();
				if(!Double.isInfinite(D)) {
					if((D <= Float.MAX_VALUE) && (D >= Float.MIN_VALUE)) return (float)D;
					return D;
				}
			} 
			case DOUBLE: {
				double D = pParam1.doubleValue() / pParam2.doubleValue();
				if(!Double.isInfinite(D)) return D;
				return getBigDecimal(pParam1).divide(getBigDecimal(pParam2));
			}
		}
		return 0;
	}
	
	// Param1 % Param2
	/** Modulo operation for whatever number type is. */
	static public Number modulus(Number pParam1, Number pParam2) {
		if(pParam1 == null) pParam1 = 0;
		if(pParam2 == null) pParam2 = 0;
		
		NumberType NT = NumberType.BYTE;
		NT = NT.max(pParam1);
		NT = NT.max(pParam2);
		
		switch(NT) {
			case       BYTE: return (pParam1.byteValue()   % pParam2.byteValue());
			case      SHORT: return (pParam1.shortValue()  % pParam2.shortValue());
			case        INT: return  pParam1.intValue()    % pParam2.intValue();
			case       LONG: return (pParam1.longValue()   % pParam2.longValue());
			case      FLOAT: return (pParam1.floatValue()  % pParam2.floatValue());
			case     DOUBLE: return  pParam1.doubleValue() % pParam2.doubleValue();
			case BIGINTEGER: return getBigInteger(pParam1).remainder(getBigInteger(pParam2));
			case BIGDECIMAL: return getBigInteger(pParam1).remainder(getBigInteger(pParam2));
		}
		return 0;
	}
	
	// Param1 << Param2
	/** Shift bits to the left. */
	static public Number shiftLeft(Number pParam1, Number pParam2) {
		if(pParam1 == null)     pParam1 = 0;
		if(pParam2 == null)     pParam2 = 0;
		if(isNegative(pParam2)) return pParam1;
		
		NumberType NT = NumberType.BYTE;
		NT = NT.max(pParam1);
		NT = NT.max(pParam2);
		switch(NT) {
			case  BYTE: return (pParam1.byteValue()   << pParam2.byteValue());
			case SHORT: return (pParam1.shortValue()  << pParam2.shortValue());
			case   INT: return  pParam1.intValue()    << pParam2.intValue();
			case  LONG: return (pParam1.longValue()   << pParam2.longValue());
			
			case  FLOAT:
			case DOUBLE: { 
				double D = pParam1.doubleValue() * Math.pow(2, pParam2.doubleValue());
				if(NT == NumberType.FLOAT) return (float)D;
				return D;
			}
			
			case BIGINTEGER: return getBigInteger(pParam1).shiftLeft(pParam2.intValue());
			case BIGDECIMAL: return getBigDecimal(pParam1).multiply(BigDecimal.valueOf(2).pow(pParam2.intValue()));
		}
		return 0;
	}
	
	// Param1 >> Param2
	/** Shift bits to the right. */
	static public Number shiftRight(Number pParam1, Number pParam2) {
		if(pParam1 == null)     pParam1 = 0;
		if(pParam2 == null)     pParam2 = 0;
		if(isNegative(pParam2)) return pParam1;
		
		NumberType NT = NumberType.BYTE;
		NT = NT.max(pParam1);
		NT = NT.max(pParam2);
		switch(NT) {
			case   BYTE: return (pParam1.byteValue()  >> pParam2.byteValue());
			case  SHORT: return (pParam1.shortValue() >> pParam2.intValue());
			case    INT: return  pParam1.intValue()   >> pParam2.intValue();
			case   LONG: return (pParam1.longValue()  >> pParam2.longValue());
			
			case  FLOAT:
			case DOUBLE: { 
				double D = pParam1.doubleValue() / Math.pow(2, pParam2.doubleValue());
				if(NT == NumberType.FLOAT) return (float)D;
				return D;
			}
			
			case BIGINTEGER: return getBigInteger(pParam1).shiftRight(pParam2.intValue());
			case BIGDECIMAL: return getBigDecimal(pParam1).divide(BigDecimal.valueOf(2).pow(pParam2.intValue()));
		}
		return 0;
	}
	
	// Param1 >>> Param2
	/** Shift bits to the right without adding negative bit. */
	static public Number shiftRightUnsigned(Number pParam1, Number pParam2) {
		if(pParam1 == null)     pParam1 = 0;
		if(pParam2 == null)     pParam2 = 0;
		if(isNegative(pParam2)) return pParam1;
		
		
		NumberType NT = NumberType.BYTE;
		NT = NT.max(pParam1);
		NT = NT.max(pParam2);
		switch(NT) {
			case   BYTE: return (pParam1.byteValue()  >>> pParam2.byteValue());
			case  SHORT: return (pParam1.shortValue() >>> pParam2.shortValue());
			case    INT: return  pParam1.intValue()   >>> pParam2.intValue();
			case   LONG: return (pParam1.longValue()  >>> pParam2.longValue());
			
			case  FLOAT:
			case DOUBLE: { 
				double D = pParam1.doubleValue() / Math.pow(2, pParam2.doubleValue());
				if(NT == NumberType.FLOAT) return (float)D;
				return D;
			}
			
			case BIGINTEGER: return getBigInteger(pParam1).shiftRight(pParam2.intValue());
			case BIGDECIMAL: return getBigDecimal(pParam1).divide(BigDecimal.valueOf(2).pow(pParam2.intValue()));
		}
		return 0;
	}

	/** Bitwise operation AND for whatever number type is. */
	static public Number and(Number ... pParams) {
		if(pParams        == null) return 0;	// Returns because accumulative-and of [] is 0
		if(pParams.length ==    0) return 0;	// Returns because accumulative-and of [] is 0

		boolean HasZero = false;
		NumberType NT = NumberType.BYTE;
		for(int i = 0; i < pParams.length; i++) {
			if(pParams[i] == null) HasZero = true;	// Returns 0 because Null of Number is 0
			NT = NT.max(pParams[i]);
		}
		
		switch(NT) {
			case   BYTE: if(HasZero) return (byte)  0; byte   RB = pParams[0].byteValue();  for(int i = 1; i < pParams.length; i++) { RB &= pParams[i].byteValue();  } return RB;
			case  SHORT: if(HasZero) return (short) 0; short  RS = pParams[0].shortValue(); for(int i = 1; i < pParams.length; i++) { RS &= pParams[i].shortValue(); } return RS;
			case    INT: if(HasZero) return         0; int    RI = pParams[0].intValue();   for(int i = 1; i < pParams.length; i++) { RI &= pParams[i].intValue();   } return RI;
			case   LONG: if(HasZero) return (long)  0; long   RL = pParams[0].longValue();  for(int i = 1; i < pParams.length; i++) { RL &= pParams[i].longValue();  } return RL;
			case  FLOAT: if(HasZero) return (float) 0; int    RF = pParams[0].intValue();   for(int i = 1; i < pParams.length; i++) { RF &= pParams[i].intValue();   } return (float) RF;
			case DOUBLE: if(HasZero) return (double)0; long   RD = pParams[0].longValue();  for(int i = 1; i < pParams.length; i++) { RD &= pParams[i].longValue();  } return (double)RD;

			
			case BIGINTEGER:
			case BIGDECIMAL: {
				if(HasZero) {
					if(NT == NumberType.BIGINTEGER) return BigInteger.valueOf(0);
					else                            return BigDecimal.valueOf(0);
				}
				
				BigInteger BI = getBigInteger(pParams[0]);
				for(int i = 1; i < pParams.length; i++) BI = BI.and(getBigInteger(pParams[i]));
				
				if(NT == NumberType.BIGINTEGER) return BI;
				else                            return getBigDecimal(BI);
			}
		}
		return 0;	// Returns 0 because  accumulative-and of non-number is not a number which is represented by 0
	}

	/** Bitwise operation OR for whatever number type is. */
	static public Number or(Number ... pParams) {
		if(pParams        == null) return 0;	// Returns because accumulative-or of [] is 0
		if(pParams.length ==    0) return 0;	// Returns because accumulative-or of [] is 0
			
		NumberType NT = NumberType.BYTE;
		for(int i = 0; i < pParams.length; i++) {
			if(pParams[i] == null) return 0;	// Returns 0 because Null of Number is 0
			NT = NT.max(pParams[i]);
		}
		
		switch(NT) {
			case   BYTE: byte   RB = pParams[0].byteValue();  for(int i = 1; i < pParams.length; i++) { RB |= pParams[i].byteValue();  } return RB;
			case  SHORT: short  RS = pParams[0].shortValue(); for(int i = 1; i < pParams.length; i++) { RS |= pParams[i].shortValue(); } return RS;
			case    INT: int    RI = pParams[0].intValue();   for(int i = 1; i < pParams.length; i++) { RI |= pParams[i].intValue();   } return RI;
			case   LONG: long   RL = pParams[0].longValue();  for(int i = 1; i < pParams.length; i++) { RL |= pParams[i].longValue();  } return RL;
			case  FLOAT: int    RF = pParams[0].intValue();   for(int i = 1; i < pParams.length; i++) { RF |= pParams[i].intValue();   } return (float)RF;
			case DOUBLE: long   RD = pParams[0].longValue();  for(int i = 1; i < pParams.length; i++) { RD |= pParams[i].longValue();  } return (long) RD;

			case BIGINTEGER:
			case BIGDECIMAL: {
				BigInteger BI = getBigInteger(pParams[0]);
				for(int i = 1; i < pParams.length; i++) BI = BI.or(getBigInteger(pParams[i]));
				if(NT == NumberType.BIGINTEGER) return BI;
				else                            return getBigDecimal(BI);
			}
		}
		return 0;	// Returns 0 because  accumulative-or of non-number is not a number which is represented by 0
	}

	/** Bitwise operation XOR for whatever number type is. */
	static public Number xor(Number ... pParams) {
		if(pParams        == null) return 0;	// Returns because accumulative-xor of [] is 0
		if(pParams.length ==    0) return 0;	// Returns because accumulative-xor of [] is 0
			
		NumberType NT = NumberType.BYTE;
		for(int i = 0; i < pParams.length; i++) {
			if(pParams[i] == null) return 0;	// Returns 0 because Null of Number is 0
			NT = NT.max(pParams[i]);
		}
		
		switch(NT) {
			case   BYTE: byte   RB = pParams[0].byteValue();  for(int i = 1; i < pParams.length; i++) { RB ^= pParams[i].byteValue();  } return RB;
			case  SHORT: short  RS = pParams[0].shortValue(); for(int i = 1; i < pParams.length; i++) { RS ^= pParams[i].shortValue(); } return RS;
			case    INT: int    RI = pParams[0].intValue();   for(int i = 1; i < pParams.length; i++) { RI ^= pParams[i].intValue();   } return RI;
			case   LONG: long   RL = pParams[0].longValue();  for(int i = 1; i < pParams.length; i++) { RL ^= pParams[i].longValue();  } return RL;
			case  FLOAT: int    RF = pParams[0].intValue();   for(int i = 1; i < pParams.length; i++) { RF ^= pParams[i].intValue();   } return (float)RF;
			case DOUBLE: long   RD = pParams[0].longValue();  for(int i = 1; i < pParams.length; i++) { RD ^= pParams[i].longValue();  } return (long) RD;

			case BIGINTEGER:
			case BIGDECIMAL: {
				BigInteger BI = getBigInteger(pParams[0]);
				for(int i = 1; i < pParams.length; i++) BI = BI.xor(getBigInteger(pParams[i]));
				if(NT == NumberType.BIGINTEGER) return BI;
				else                            return getBigDecimal(BI);
			}
		}
		return 0;	// Returns 0 because  accumulative-xor of non-number is not a number which is represented by 0
	}

	/** Returns absolute value of pParam for whatever the number type is. */
	static public Number not(Number pParam) {
		if(pParam == null) return 0;
		if     (pParam instanceof    Integer) { int   R = pParam.intValue();  return        ~R; }
		else if(pParam instanceof     Double) { long  R = pParam.longValue(); return        ~R; }
		else if(pParam instanceof       Byte) { byte  R = pParam.byteValue(); return (byte) ~R; }
		else if(pParam instanceof       Long) { long  R = pParam.longValue(); return (long) ~R; }
		else if(pParam instanceof      Float) { int   R = pParam.intValue();  return        ~R; }
		else if(pParam instanceof      Short) { short R = pParam.shortValue();return (short)~R; }
		else if(pParam instanceof BigInteger) { return               getBigInteger(pParam).xor(getBigInteger(0));  }
		else if(pParam instanceof BigDecimal) { return getBigDecimal(getBigInteger(pParam).xor(getBigInteger(0))); }
		return 0;
	}

	/** Returns absolute value of pParam for whatever the number type is. */
	static public Number abs(Number pParam) {
		if(pParam == null) return 0;
		if     (pParam instanceof Integer) { int    R = ((Integer)pParam).intValue();    return (R < 0)?       -R:       R; }
		else if(pParam instanceof  Double) { double R = (( Double)pParam).doubleValue(); return (R < 0)?       -R:       R; }
		else if(pParam instanceof    Byte) { byte   R = ((   Byte)pParam).byteValue();   return (R < 0)? (byte)-R: (byte)R; }
		else if(pParam instanceof    Long) { long   R = ((   Long)pParam).longValue();   return (R < 0)? (long)-R: (long)R; }
		else if(pParam instanceof   Float) { float  R = ((  Float)pParam).floatValue();  return (R < 0)?(float)-R:(float)R; }
		else if(pParam instanceof   Short) { short  R = ((  Short)pParam).shortValue();  return (R < 0)?(short)-R:(short)R; }
		else if(pParam instanceof BigInteger) { return getBigInteger(pParam).abs(); }
		else if(pParam instanceof BigDecimal) { return getBigDecimal(pParam).abs(); }
		return 0;
	}

	/** Returns opposite-sign value of pParam for whatever the number type is. */
	static public Number neg(Number pParam) {
		if(pParam == null) return 0;
		if     (pParam instanceof Integer) { int    R = ((Integer)pParam).intValue();    return        -R; }
		else if(pParam instanceof  Double) { double R = (( Double)pParam).doubleValue(); return        -R; }
		else if(pParam instanceof    Byte) { byte   R = ((   Byte)pParam).byteValue();   return  (byte)-R; }
		else if(pParam instanceof    Long) { long   R = ((   Long)pParam).longValue();   return  (long)-R; }
		else if(pParam instanceof   Float) { float  R = ((  Float)pParam).floatValue();  return (float)-R; }
		else if(pParam instanceof   Short) { short  R = ((  Short)pParam).shortValue();  return (short)-R; }
		else if(pParam instanceof BigInteger) { return getBigInteger(pParam).negate(); }
		else if(pParam instanceof BigDecimal) { return getBigDecimal(pParam).negate(); }
		return 0;
	}

	/** Checks the number is zero for whatever the number type is. */
	static public boolean isZero(Number pParam) {
		if(pParam == null) return false;
		if     (pParam instanceof Integer) { int    R = ((Integer)pParam).intValue();    return R == 0; }
		else if(pParam instanceof  Double) { double R = (( Double)pParam).doubleValue(); return R == 0; }
		else if(pParam instanceof    Byte) { byte   R = ((   Byte)pParam).byteValue();   return R == 0; }
		else if(pParam instanceof    Long) { long   R = ((   Long)pParam).longValue();   return R == 0; }
		else if(pParam instanceof   Float) { float  R = ((  Float)pParam).floatValue();  return R == 0; }
		else if(pParam instanceof   Short) { short  R = ((  Short)pParam).shortValue();  return R == 0; }
		else if(pParam instanceof BigInteger) { return getBigInteger(pParam).equals(getBigInteger(0)); }
		else if(pParam instanceof BigDecimal) { return getBigDecimal(pParam).equals(getBigDecimal(0)); }
		return false;
	}
	/** Checks the number is positive for whatever the number type is. */
	static public boolean isPositive(Number pParam) {
		if(pParam == null) return false;
		if     (pParam instanceof Integer) { int    R = ((Integer)pParam).intValue();    return R > 0; }
		else if(pParam instanceof  Double) { double R = (( Double)pParam).doubleValue(); return R > 0; }
		else if(pParam instanceof    Byte) { byte   R = ((   Byte)pParam).byteValue();   return R > 0; }
		else if(pParam instanceof    Long) { long   R = ((   Long)pParam).longValue();   return R > 0; }
		else if(pParam instanceof   Float) { float  R = ((  Float)pParam).floatValue();  return R > 0; }
		else if(pParam instanceof   Short) { short  R = ((  Short)pParam).shortValue();  return R > 0; }
		else if(pParam instanceof BigInteger) { return getBigInteger(pParam).compareTo(getBigInteger(0)) > 0; }
		else if(pParam instanceof BigDecimal) { return getBigDecimal(pParam).compareTo(getBigDecimal(0)) > 0; }
		return false;
	}
	/** Checks the number is negative for whatever the number type is. */
	static public boolean isNegative(Number pParam) {
		if(pParam == null) return false;
		if     (pParam instanceof Integer) { int    R = ((Integer)pParam).intValue();    return R < 0; }
		else if(pParam instanceof  Double) { double R = (( Double)pParam).doubleValue(); return R < 0; }
		else if(pParam instanceof    Byte) { byte   R = ((   Byte)pParam).byteValue();   return R < 0; }
		else if(pParam instanceof    Long) { long   R = ((   Long)pParam).longValue();   return R < 0; }
		else if(pParam instanceof   Float) { float  R = ((  Float)pParam).floatValue();  return R < 0; }
		else if(pParam instanceof   Short) { short  R = ((  Short)pParam).shortValue();  return R < 0; }
		else if(pParam instanceof BigInteger) { return getBigInteger(pParam).compareTo(getBigInteger(0)) < 0; }
		else if(pParam instanceof BigDecimal) { return getBigDecimal(pParam).compareTo(getBigDecimal(0)) < 0; }
		return false;
	}
	/** Checks the number is one for whatever the number type is. */
	static public boolean isOne(Number pParam) {
		if(pParam == null) return false;
		if     (pParam instanceof Integer) { int    R = ((Integer)pParam).intValue();    return R ==         1; }
		else if(pParam instanceof  Double) { double R = (( Double)pParam).doubleValue(); return R ==         1; }
		else if(pParam instanceof    Byte) { byte   R = ((   Byte)pParam).byteValue();   return R ==   (byte)1; }
		else if(pParam instanceof    Long) { long   R = ((   Long)pParam).longValue();   return R ==         1; }
		else if(pParam instanceof   Float) { float  R = ((  Float)pParam).floatValue();  return R ==         1; }
		else if(pParam instanceof   Short) { short  R = ((  Short)pParam).shortValue();  return R ==  (short)1; }
		else if(pParam instanceof BigInteger) { return getBigInteger(pParam).equals(getBigInteger(1)); }
		else if(pParam instanceof BigDecimal) { return getBigDecimal(pParam).equals(getBigDecimal(1)); }
		return false;
	}
	/** Checks the number is minus one for whatever the number type is. */
	static public boolean isMinusOne(Number pParam) {
		if(pParam == null) return false;
		if     (pParam instanceof Integer) { int    R = ((Integer)pParam).intValue();    return R ==         -1; }
		else if(pParam instanceof  Double) { double R = (( Double)pParam).doubleValue(); return R ==         -1; }
		else if(pParam instanceof    Byte) { byte   R = ((   Byte)pParam).byteValue();   return R ==   (byte)-1; }
		else if(pParam instanceof    Long) { long   R = ((   Long)pParam).longValue();   return R ==         -1; }
		else if(pParam instanceof   Float) { float  R = ((  Float)pParam).floatValue();  return R ==         -1; }
		else if(pParam instanceof   Short) { short  R = ((  Short)pParam).shortValue();  return R ==  (short)-1; }
		else if(pParam instanceof BigInteger) { return getBigInteger(pParam).equals(getBigInteger(-1)); }
		else if(pParam instanceof BigDecimal) { return getBigDecimal(pParam).equals(getBigDecimal(-1)); }
		return false;
	}

	/** Converts the input number to a different number type for whatever the number type is. */
	static public Number to(NumberType NT, Number N) {
		if(N == null) N = 0;
		switch(NT) {
			case       BYTE: return N.byteValue();
			case      SHORT: return N.shortValue();
			case        INT: return N.intValue();
			case       LONG: return N.longValue();
			case      FLOAT: return N.floatValue();
			case     DOUBLE: return N.doubleValue();
			case BIGINTEGER: return getBigInteger(N);
			case BIGDECIMAL: return getBigDecimal(N);
		}
		return N.intValue();
	}

	/** Checks if the number can be safely converted as byte without loose the most significant value */
	static public boolean canBeInByte(Number N) {
		NumberType NT = NumberType.valueOf(N);
		switch(NT) {
			case BYTE: return true;

			case SHORT:
			case INT: {
				int i = N.intValue();
				return ((i < Byte.MAX_VALUE) && (i > Byte.MIN_VALUE));
			}
			
			case LONG:{
				long l = N.longValue();
				return ((l <= Byte.MAX_VALUE) && (l >= Byte.MIN_VALUE));
			}
			
			case FLOAT:
			case DOUBLE:{
				double d = N.longValue();
				return ((d <= Byte.MAX_VALUE) && (d >= Byte.MIN_VALUE));
			}
			
			case BIGINTEGER:{
				BigInteger BI = (BigInteger)N;
				return (BI.compareTo(BI_MAX_BYTE_VALUE) <= 0) && (BI.compareTo(BI_MIN_BYTE_VALUE) >= 0);
			}
			case BIGDECIMAL: {
				BigDecimal BD = (BigDecimal)N;
				return (BD.compareTo(BD_MAX_BYTE_VALUE) <= 0) && (BD.compareTo(BD_MIN_BYTE_VALUE) >= 0);
			}
		}
		return false;
	}
	/** Checks if the number can be safely converted as short without loose the most significant value */
	static public boolean canBeInShort(Number N) {
		NumberType NT = NumberType.valueOf(N);
		switch(NT) {
			case BYTE: 
			case SHORT: return true;

			case INT: {
				int i = N.intValue();
				return ((i < Short.MAX_VALUE) && (i > Short.MIN_VALUE));
			}
			
			case LONG:{
				long l = N.longValue();
				return ((l <= Short.MAX_VALUE) && (l >= Short.MIN_VALUE));
			}
			
			case FLOAT:
			case DOUBLE:{
				double d = N.longValue();
				return ((d <= Short.MAX_VALUE) && (d >= Short.MIN_VALUE));
			}

			case BIGINTEGER:{
				BigInteger BI = (BigInteger)N;
				return (BI.compareTo(BI_MAX_SHORT_VALUE) <= 0) && (BI.compareTo(BI_MIN_SHORT_VALUE) >= 0);
			}
			case BIGDECIMAL: {
				BigDecimal BD = (BigDecimal)N;
				return (BD.compareTo(BD_MAX_SHORT_VALUE) <= 0) && (BD.compareTo(BD_MIN_SHORT_VALUE) >= 0);
			}
		}
		return false;
	}
	/** Checks if the number can be safely converted as integer without loose the most significant value */
	static public boolean canBeInInteger(Number N) {
		NumberType NT = NumberType.valueOf(N);
		switch(NT) {
			case BYTE: 
			case SHORT:
			case INT:   return true;
			
			case LONG:{
				long l = N.longValue();
				return ((l <= Integer.MAX_VALUE) && (l >= Integer.MIN_VALUE));
			}
			
			case FLOAT:
			case DOUBLE:{
				double d = N.longValue();
				return ((d <= Integer.MAX_VALUE) && (d >= Integer.MIN_VALUE));
			}

			case BIGINTEGER:{
				BigInteger BI = (BigInteger)N;
				return (BI.compareTo(BI_MAX_INT_VALUE) <= 0) && (BI.compareTo(BI_MIN_INT_VALUE) >= 0);
			}
			case BIGDECIMAL: {
				BigDecimal BD = (BigDecimal)N;
				return (BD.compareTo(BD_MAX_INT_VALUE) <= 0) && (BD.compareTo(BD_MIN_INT_VALUE) >= 0);
			}
		}
		return false;
	}
	/** Checks if the number can be safely converted as long without loose the most significant value */
	static public boolean canBeInLong(Number N) {
		NumberType NT = NumberType.valueOf(N);
		switch(NT) {
			case BYTE: 
			case SHORT:
			case INT:   
			case LONG: return true;
			
			case FLOAT:
			case DOUBLE:{
				double d = N.longValue();
				return ((d <= Long.MAX_VALUE) && (d >= Long.MIN_VALUE));
			}

			case BIGINTEGER:{
				BigInteger BI = (BigInteger)N;
				return (BI.compareTo(BI_MAX_LONG_VALUE) <= 0) && (BI.compareTo(BI_MIN_LONG_VALUE) >= 0);
			}
			case BIGDECIMAL: {
				BigDecimal BD = (BigDecimal)N;
				return (BD.compareTo(BD_MAX_LONG_VALUE) <= 0) && (BD.compareTo(BD_MIN_LONG_VALUE) >= 0);
			}
		}
		return false;
	}
	/** Checks if the number can be safely converted as float without loose the most significant value */
	static public boolean canBeInFloat(Number N) {
		NumberType NT = NumberType.valueOf(N);
		switch(NT) {
			case BYTE: 
			case SHORT:
			case INT:   
			case LONG:
			case FLOAT: return true;
			
			case DOUBLE:{
				double d = N.longValue();
				return ((d <= Float.MAX_VALUE) && (d >= Float.MIN_VALUE));
			}

			case BIGINTEGER:{
				BigInteger BI = (BigInteger)N;
				return (BI.compareTo(BI_MAX_FLOAT_VALUE) <= 0) && (BI.compareTo(BI_MIN_FLOAT_VALUE) >= 0);
			}
			case BIGDECIMAL: {
				BigDecimal BD = (BigDecimal)N;
				return (BD.compareTo(BD_MAX_FLOAT_VALUE) <= 0) && (BD.compareTo(BD_MIN_FLOAT_VALUE) >= 0);
			}
		}
		return false;
	}
	/** Checks if the number can be safely converted as double without loose the most significant value */
	static public boolean canBeInDouble(Number N) {
		NumberType NT = NumberType.valueOf(N);
		switch(NT) {
			case BYTE: 
			case SHORT:
			case INT:   
			case LONG:
			case FLOAT:
			case DOUBLE: return true;

			case BIGINTEGER:{
				BigInteger BI = (BigInteger)N;
				return (BI.compareTo(BI_MAX_DOUBLE_VALUE) <= 0) && (BI.compareTo(BI_MIN_DOUBLE_VALUE) >= 0);
			}
			case BIGDECIMAL: {
				BigDecimal BD = (BigDecimal)N;
				return (BD.compareTo(BD_MAX_DOUBLE_VALUE) <= 0) && (BD.compareTo(BD_MIN_DOUBLE_VALUE) >= 0);
			}
		}
		return false;
	}
	/** Checks if the number can be safely converted as the given number type without loose the most significant value */
	static public boolean canBeIn(NumberType NT, Number N) {
		if(N  == null) return true;
		if(NT == null) return false;
		
		NumberType MNT = NT.max(N);
		if(MNT == NT) return true;
		
		switch(NT) {
			case       BYTE: return canBeInByte(N); 
			case      SHORT: return canBeInShort(N);
			case        INT: return canBeInInteger(N);
			case       LONG: return canBeInLong(N);
			case      FLOAT: return canBeInFloat(N);
			case     DOUBLE: return canBeInDouble(N);
			case BIGINTEGER: return true;
			case BIGDECIMAL: return true;
		}
		return false;
	}

	/** Checks if the given number has no fraction. */
	static public boolean canBeInFloatExact(Number N) {
		if(N == null) return true;
		
		NumberType NT = NumberType.valueOf(N);
		
		switch(NT) {
			case  BYTE:  
			case SHORT:
			case FLOAT: return true;
			
			case    INT: 
			case   LONG: 
			case DOUBLE: {
				return Double.doubleToLongBits((double)N.floatValue())
				       ==
				       Double.doubleToLongBits(N.doubleValue());
				
			}
			
			case BIGINTEGER:
			case BIGDECIMAL: {
				if(canBeInFloat(N))
					return Double.doubleToLongBits((double)N.floatValue())
				       ==
				       Double.doubleToLongBits(N.doubleValue());
				return false;
			}
		}
		return false;
	}

	/** Checks if the given number has no fraction. */
	@SuppressWarnings("unchecked")
	static public boolean canBeInDoubleExact(Number N) {
		if(N == null) return true;
		
		NumberType NT = NumberType.valueOf(N);
		
		switch(NT) {
			case   BYTE:  
			case  SHORT:
			case    INT: 
			case  FLOAT:
			case DOUBLE: return true; 
			
			case LONG: {
				return Double.doubleToLongBits((double)N.longValue())
			       ==
			       Double.doubleToLongBits(N.longValue());
			}
			
			case BIGINTEGER:
			case BIGDECIMAL: {
				if(canBeInDouble(N)) {
					Comparable<Number> C = (Comparable<Number>)N;
					return (C.compareTo(getBigDecimal(N.doubleValue())) == 0);
				}
				return false;
			}
		}
		return false;
	}

	/** Checks if the given number has no fraction. */
	static public boolean hasFraction(Number N) {
		if(N == null) return true;
		
		NumberType NT = NumberType.valueOf(N);
		
		switch(NT) {
			case       BYTE:  
			case      SHORT: 
			case        INT: 
			case       LONG: 
			case BIGINTEGER: return false;
			case      FLOAT: return ((N.floatValue() % 1) != 0.0f);
			case     DOUBLE: return ((N.doubleValue() % 1) != 0.0f);
			case BIGDECIMAL: return ((BigDecimal)N).remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0;
		}
		return false;
	}
	
	/** Checks if two number are equals and have the same type */
	@SuppressWarnings("unchecked")
	static public boolean is(Number N1, Number N2) {
		if(N1 == N2) return true;
		if(N1 == null) N1 = 0;
		if(N2 == null) N2 = 0; 
		
		NumberType NT1 = NumberType.valueOf(N1);
		NumberType NT2 = NumberType.valueOf(N2);
		if(NT1 == NT2) {
			if((N1 instanceof Comparable) && (((Comparable)N1).compareTo(N2) == 0)) return true;
			return N1.equals(N2);
		}
		return false;
	}
	
	/** Checks if two number are equals */
	@SuppressWarnings("unchecked")
	static public boolean equal(Number N1, Number N2) {
		if(N1 == N2) return true;
		if(N1 == null) N1 = 0;
		if(N2 == null) N2 = 0;
		
		NumberType NT1 = NumberType.valueOf(N1);
		NumberType NT2 = NumberType.valueOf(N2);
		if(NT1 == NT2) {
			if((N1 instanceof Comparable) && (((Comparable)N1).compareTo(N2) == 0)) return true; 
			return N1.equals(N2);
		}
		
		NumberType NT = NumberType.max(NT1, NT2);
		
		switch (NT) {
			case BYTE:
			case SHORT:
			case INT:        return N1.intValue()    == N2.intValue();
			case LONG:       return N1.longValue()   == N2.longValue();
			case FLOAT:      return N1.floatValue()  == N2.floatValue();
			case DOUBLE:     return N1.doubleValue() == N2.doubleValue();
			case BIGINTEGER: return UNumber.getBigInteger(N1).compareTo(UNumber.getBigInteger(N2)) == 0;
			case BIGDECIMAL: return UNumber.getBigDecimal(N1).compareTo(UNumber.getBigDecimal(N2)) == 0;
		}
		return N1.equals(N2);
	}
	
	/** Checks if two number are equals */
	@SuppressWarnings("unchecked")
	static public int compare(Number N1, Number N2) {
		if(N1 == N2) return 0;
		if(N1 == null) N1 = 0;
		if(N2 == null) N2 = 0;
		
		NumberType NT1 = NumberType.valueOf(N1);
		NumberType NT2 = NumberType.valueOf(N2);
		NumberType NT  = NumberType.max(NT1, NT2);
		switch (NT) {
			case BYTE:
			case SHORT:
			case INT:  return N1.intValue() - N2.intValue();
			case LONG: {
				if(NT1 != NumberType.LONG) N1 = Long.valueOf(N1.longValue());
				if(NT2 != NumberType.LONG) N2 = Long.valueOf(N2.longValue());
				return ((Long)N1).compareTo((Long)N2);
			}
			
			case FLOAT: {
				if(NT1 != NumberType.FLOAT) N1 = Float.valueOf(N1.floatValue());
				if(NT2 != NumberType.FLOAT) N2 = Float.valueOf(N2.floatValue());
				return ((Float)N1).compareTo((Float)N2);
			}
			case DOUBLE: {
				if(NT1 != NumberType.DOUBLE) N1 = Double.valueOf(N1.doubleValue());
				if(NT2 != NumberType.DOUBLE) N2 = Double.valueOf(N2.doubleValue());
				return ((Double)N1).compareTo((Double)N2);
			}
			case BIGINTEGER: {
				if(NT1 != NumberType.BIGINTEGER) N1 = getBigInteger(N1);
				if(NT2 != NumberType.BIGINTEGER) N2 = getBigInteger(N2);
				return (getBigInteger(N1)).compareTo(getBigInteger(N2));
			}
			case BIGDECIMAL: {
				if(NT1 != NumberType.BIGINTEGER) N1 = getBigDecimal(N1);
				if(NT2 != NumberType.BIGINTEGER) N2 = getBigDecimal(N2);
				return (getBigDecimal(N1)).compareTo(getBigDecimal(N2));
			}
		}
		if(N1 instanceof Comparable) ((Comparable<Number>)N1).compareTo(N2);
		// Don't know to me
		return N1.toString().compareTo(N2.toString());
	}
	
	// -----------------------------------------------------------------------------------------------------------------
	// Test ------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------
	
	static private char getKindShort(Object O) {
		if(!(O instanceof Number)) return ' ';
		Class<?> C = O.getClass();
		if(C == Byte      .class) return 'b';       
		if(C == Short     .class) return 's';       
		if(C == Integer   .class) return 'i';       
		if(C == Long      .class) return 'l';       
		if(C == Float     .class) return 'f';       
		if(C == Double    .class) return 'd';       
		if(C == BigInteger.class) return 'I';       
		if(C == BigDecimal.class) return 'D';
		return ' ';
	}
	
	static private boolean Check(Object O, Object Value) {
		return is((Number)O, (Number)Value);
	}
	
	// Plus -----------------------------------------------------------------------------
	
	static private String getMessagePlus(Number N1, Number N2, Number NResult) {
		return String.format("(%10s%s + %10s%s) = %10s%s",
				N1,      getKindShort(N1),
				N2,      getKindShort(N2),
				NResult, getKindShort(NResult)
			   );
	}
	static private void AssertPlus(boolean IsToEcho, Number N1, Number N2, Number NResult) {
		
		if(IsToEcho) System.out.println(getMessagePlus(N1, N2, NResult));
		Number NR = plus(N1, N2);
		if(Check(NR, NResult)) return;
		
		throw new AssertionError(getMessagePlus(N1, N2, NR) + " != " + String.format("%10s%s", NResult, getKindShort(NResult)));
	}
	static private String getMessagePlusMultiple(Number NResult, Number ... Ns) {
		StringBuilder SB = new StringBuilder();
		SB.append("(");
		for(int i = 0; i < ((Ns == null)?0:Ns.length); i++) {
			if(i != 0) SB.append(" + ");
			SB.append(String.format("%10s%s", Ns[i], getKindShort(Ns[i])));
		}
		SB.append(") = ");
		SB.append(String.format("%10s%s", NResult, getKindShort(NResult)));
		return SB.toString();
	}
	static private void AssertPlusMultiple(boolean IsToEcho, Number NResult, Number ... Ns) {
		
		if(IsToEcho) System.out.println(getMessagePlusMultiple(NResult, Ns));
		Number NR = plus(Ns);
		if(Check(NR, NResult)) return;
		
		if(IsToEcho) {
			System.out.println("Differ: " + getBigDecimal(NR).subtract(getBigDecimal(NResult)));
		}
		throw new AssertionError(getMessagePlusMultiple(NR, Ns) + " != " + String.format("%10s%s", NResult, getKindShort(NResult)));
	}
	
	static void showAddFloat(float F1, float F2) {
		float FR = (float)((float)F1 + (float)F2);
		System.out.printf(
			"  %110.70f[%8X]\n+ %110.70f[%8X]\n= %110.70f[%8X]" +
			"\n----------------------------------------------------------------------------------------\n",
			F1, Float.floatToIntBits(F1), F2, Float.floatToIntBits(F2), FR, Float.floatToIntBits(FR));
	}
	
	static void showAddDouble(double F1, double F2) {
		double FR = F1 + F2;
		System.out.printf(
			"  [%16X]%1060.750f\n+ [%16X]%1060.750f\n= [%16X]%1060.750f" +
			"\n----------------------------------------------------------------------------------------\n",
			Double.doubleToLongBits(F1), F1, Double.doubleToLongBits(F2), F2, Double.doubleToLongBits(FR), FR);
	}
	
	static private void testPlusMultiply(boolean IsToEcho) {
		
		final byte   HALF_BYPE    = Byte   .MAX_VALUE/2;
		final short  HALF_SHORT   = Short  .MAX_VALUE/2;
		final int    HALF_INTEGER = Integer.MAX_VALUE/2;
		final long   HALF_LONG    = Long   .MAX_VALUE/2;
		final float  HALF_FLOAT   = Float  .MAX_VALUE/2;
		final double HALF_DOUBLE  = Double .MAX_VALUE/2;
		
		final byte   SROOT_BYPE    = (byte)  Math.sqrt(Byte   .MAX_VALUE);
		final short  SROOT_SHORT   = (short) Math.sqrt(Short  .MAX_VALUE);
		final int    SROOT_INTEGER = (int)   Math.sqrt(Integer.MAX_VALUE);
		final long   SROOT_LONG    = (long)  Math.sqrt(Long   .MAX_VALUE);
		final float  SROOT_FLOAT   = (float) Math.sqrt(Float  .MAX_VALUE);
		final double SROOT_DOUBLE  = (double)Math.sqrt(Double .MAX_VALUE);
		
		// Test each kind ----------------------------------------------------------------------------------------------
		System.out.println("Testing Kind ... ");
		
		// Ouput kind from Input kinds ----------------------------------------------------------------------
		System.out.println("\nSame Kind ... ");
		AssertPlus(IsToEcho, (byte)        0 , (byte)        0 , (byte)        0);
		AssertPlus(IsToEcho, (short)       0 , (short)       0 , (short)       0);
		AssertPlus(IsToEcho, (int)         0 , (int)         0 , (int)         0);
		AssertPlus(IsToEcho, (long)        0 , (long)        0 , (long)        0);
		AssertPlus(IsToEcho, (float)       0 , (float)       0 , (float)       0);
		AssertPlus(IsToEcho, (double)      0 , (double)      0 , (double)      0);
		AssertPlus(IsToEcho, getBigInteger(0), getBigInteger(0), getBigInteger(0));
		AssertPlus(IsToEcho, getBigDecimal(0), getBigDecimal(0), getBigDecimal(0));
		
		AssertMultiply(IsToEcho, (byte)        0 , (byte)        0 , (byte)        0);
		AssertMultiply(IsToEcho, (short)       0 , (short)       0 , (short)       0);
		AssertMultiply(IsToEcho, (int)         0 , (int)         0 , (int)         0);
		AssertMultiply(IsToEcho, (long)        0 , (long)        0 , (long)        0);
		AssertMultiply(IsToEcho, (float)       0 , (float)       0 , (float)       0);
		AssertMultiply(IsToEcho, (double)      0 , (double)      0 , (double)      0);
		AssertMultiply(IsToEcho, getBigInteger(0), getBigInteger(0), getBigInteger(0));
		AssertMultiply(IsToEcho, getBigDecimal(0), getBigDecimal(0), getBigDecimal(0));

		System.out.println("\nOther kinds with Byte ... ");
		AssertPlus(IsToEcho, (byte)0, (short)       0 , (short)       0);
		AssertPlus(IsToEcho, (byte)0, (int)         0 , (int)         0);
		AssertPlus(IsToEcho, (byte)0, (long)        0 , (long)        0);
		AssertPlus(IsToEcho, (byte)0, (float)       0 , (float)       0);  // Byte & Float => Float
		AssertPlus(IsToEcho, (byte)0, (double)      0 , (double)      0);
		AssertPlus(IsToEcho, (byte)0, getBigInteger(0), getBigInteger(0));
		AssertPlus(IsToEcho, (byte)0, getBigDecimal(0), getBigDecimal(0));
		
		AssertMultiply(IsToEcho, (byte)0, (short)       0 , (short)       0);
		AssertMultiply(IsToEcho, (byte)0, (int)         0 , (int)         0);
		AssertMultiply(IsToEcho, (byte)0, (long)        0 , (long)        0);
		AssertMultiply(IsToEcho, (byte)0, (float)       0 , (float)       0);  // Byte & Float => Float
		AssertMultiply(IsToEcho, (byte)0, (double)      0 , (double)      0);
		AssertMultiply(IsToEcho, (byte)0, getBigInteger(0), getBigInteger(0));
		AssertMultiply(IsToEcho, (byte)0, getBigDecimal(0), getBigDecimal(0));

		System.out.println("\nOther kinds with Short ... ");
		AssertPlus(IsToEcho, (short)0, (int)         0 , (int)         0);
		AssertPlus(IsToEcho, (short)0, (long)        0 , (long)        0);
		AssertPlus(IsToEcho, (short)0, (float)       0 , (float)       0);  // Byte & Float => Float
		AssertPlus(IsToEcho, (short)0, (double)      0 , (double)      0);
		AssertPlus(IsToEcho, (short)0, getBigInteger(0), getBigInteger(0));
		AssertPlus(IsToEcho, (short)0, getBigDecimal(0), getBigDecimal(0));

		AssertMultiply(IsToEcho, (short)0, (int)         0 , (int)         0);
		AssertMultiply(IsToEcho, (short)0, (long)        0 , (long)        0);
		AssertMultiply(IsToEcho, (short)0, (float)       0 , (float)       0);  // Byte & Float => Float
		AssertMultiply(IsToEcho, (short)0, (double)      0 , (double)      0);
		AssertMultiply(IsToEcho, (short)0, getBigInteger(0), getBigInteger(0));
		AssertMultiply(IsToEcho, (short)0, getBigDecimal(0), getBigDecimal(0));

		System.out.println("\nOther kinds with Integer ... ");
		AssertPlus(IsToEcho, (int)0, (long)        0 , (long)        0);
		AssertPlus(IsToEcho, (int)0, (float)       0 , (double)      0);  // Integer & Float => Double
		AssertPlus(IsToEcho, (int)0, (double)      0 , (double)      0);
		AssertPlus(IsToEcho, (int)0, getBigInteger(0), getBigInteger(0));
		AssertPlus(IsToEcho, (int)0, getBigDecimal(0), getBigDecimal(0));

		AssertMultiply(IsToEcho, (int)0, (long)        0 , (long)        0);
		AssertMultiply(IsToEcho, (int)0, (float)       0 , (double)      0);  // Integer & Float => Double
		AssertMultiply(IsToEcho, (int)0, (double)      0 , (double)      0);
		AssertMultiply(IsToEcho, (int)0, getBigInteger(0), getBigInteger(0));
		AssertMultiply(IsToEcho, (int)0, getBigDecimal(0), getBigDecimal(0));

		System.out.println("\nOther kinds with Long ... ");
		AssertPlus(IsToEcho, (long)0, (float)       0 , (double)      0);  // Long & Float => Double
		AssertPlus(IsToEcho, (long)0, (double)      0 , (double)      0);
		AssertPlus(IsToEcho, (long)0, getBigInteger(0), getBigInteger(0));
		AssertPlus(IsToEcho, (long)0, getBigDecimal(0), getBigDecimal(0));

		AssertMultiply(IsToEcho, (long)0, (float)       0 , (double)      0);  // Long & Float => Double
		AssertMultiply(IsToEcho, (long)0, (double)      0 , (double)      0);
		AssertMultiply(IsToEcho, (long)0, getBigInteger(0), getBigInteger(0));
		AssertMultiply(IsToEcho, (long)0, getBigDecimal(0), getBigDecimal(0));

		System.out.println("\nOther kinds with Float ... ");
		AssertPlus(IsToEcho, (float)0, (double)      0 , (double)      0);
		AssertPlus(IsToEcho, (float)0, getBigInteger(0), getBigDecimal(0));  // BigInteger & Float => BigDecimal
		AssertPlus(IsToEcho, (float)0, getBigDecimal(0), getBigDecimal(0));

		AssertMultiply(IsToEcho, (float)0, (double)      0 , (double)      0);
		AssertMultiply(IsToEcho, (float)0, getBigInteger(0), getBigDecimal(0));  // BigInteger & Float => BigDecimal
		AssertMultiply(IsToEcho, (float)0, getBigDecimal(0), getBigDecimal(0));

		System.out.println("\nOther kinds with Double ... ");
		AssertPlus(IsToEcho, (double)0, getBigInteger(0), getBigDecimal(0.0));  // BigInteger & Double => BigDecimal
		AssertPlus(IsToEcho, (double)0, getBigDecimal(0), getBigDecimal(0.0));

		AssertMultiply(IsToEcho, (double)0, getBigInteger(0), getBigDecimal(0.0));  // BigInteger & Double => BigDecimal
		AssertMultiply(IsToEcho, (double)0, getBigDecimal(0), getBigDecimal(0.0));
		
		// Overfloating (upgrade the result if not enough space) --------------------------------------------
		System.out.println();
		System.out.println("Overfloating ...");
		
		byte HB = HALF_BYPE;
		byte MB = Byte.MAX_VALUE;
		AssertPlus(IsToEcho, (byte)(HB - 1), (byte)(HB - 1), (byte) ((byte)HB + (byte)HB - (byte)2));
		AssertPlus(IsToEcho, (byte)(HB - 1), (byte) HB     , (byte) ((byte)HB + (byte)HB - (byte)1));
		AssertPlus(IsToEcho, (byte) HB     , (byte) HB     , (byte) ((byte)HB + (byte)HB          ));
		AssertPlus(IsToEcho, (byte)(HB + 1), (byte) HB     , (byte) ((byte)HB + (byte)HB + (byte)1));
		AssertPlus(IsToEcho, (byte)(HB + 1), (byte)(HB + 1), (short)((byte)HB + (byte)HB + (byte)2));
		
		AssertPlus(IsToEcho, (byte)(MB - 1), (byte)(MB - 1), (short)((byte)MB + (byte)MB - (byte)2));
		AssertPlus(IsToEcho, (byte)(MB - 1), (byte) MB     , (short)((byte)MB + (byte)MB - (byte)1));
		AssertPlus(IsToEcho, (byte) MB     , (byte) MB     , (short)((byte)MB + (byte)MB          ));

		byte SB = SROOT_BYPE;
		AssertMultiply(IsToEcho, (byte)(SB - 1), (byte)(SB - 1), (byte) (((byte)SB - (byte)1) * (byte)((byte)SB - (byte)1)));
		AssertMultiply(IsToEcho, (byte)(SB - 1), (byte) SB     , (byte) (((byte)SB - (byte)1) * (byte)       SB           ));
		AssertMultiply(IsToEcho, (byte) SB     , (byte) SB     , (byte) ( (byte)SB            * (byte)       SB           ));
		AssertMultiply(IsToEcho, (byte)(SB + 1), (byte) SB     , (short)(((byte)SB + (byte)1) * (byte)       SB           ));
		AssertMultiply(IsToEcho, (byte)(SB + 1), (byte)(SB + 1), (short)(((byte)SB + (byte)1) * (byte)((byte)SB + (byte)1)));
		
		AssertMultiply(IsToEcho, (byte)(MB - 1), (byte)(MB - 1), (short)(((byte)MB - (byte)1) * ((byte)MB - (byte)1)));
		AssertMultiply(IsToEcho, (byte)(MB - 1), (byte) MB     , (short)(((byte)MB - (byte)1) *  (byte)MB           ));
		AssertMultiply(IsToEcho, (byte) MB     , (byte) MB     , (short)( (byte)MB            *  (byte)MB           ));
		
		short HS = HALF_SHORT;
		short MS = Short.MAX_VALUE;
		AssertPlus(IsToEcho, (short)(HS - 1), (short)(HS - 1), (short)((short)HS + (short)HS - (short)2));
		AssertPlus(IsToEcho, (short)(HS - 1), (short) HS     , (short)((short)HS + (short)HS - (short)1));
		AssertPlus(IsToEcho, (short) HS     , (short) HS     , (short)((short)HS + (short)HS           ));
		AssertPlus(IsToEcho, (short)(HS + 1), (short) HS     , (short)((short)HS + (short)HS + (short)1));
		AssertPlus(IsToEcho, (short)(HS + 1), (short)(HS + 1), (int)  ((short)HS + (short)HS + (short)2));
		
		AssertPlus(IsToEcho, (short)(MS - 1), (short)(MS - 1), (int)  ((short)MS + (short)MS - (short)2));
		AssertPlus(IsToEcho, (short)(MS - 1), (short) MS     , (int)  ((short)MS + (short)MS - (short)1));
		AssertPlus(IsToEcho, (short) MS     , (short) MS     , (int)  ((short)MS + (short)MS           ));
		
		short SS = SROOT_SHORT;
		AssertMultiply(IsToEcho, (short)(SS - 1), (short)(SS - 1), (short)(((short)SS - (short)1) * (short)((short)SS - (short)1)));
		AssertMultiply(IsToEcho, (short)(SS - 1), (short) SS     , (short)(((short)SS - (short)1) * (short)        SS            ));
		AssertMultiply(IsToEcho, (short) SS     , (short) SS     , (short)( (short)SS             * (short)        SS            ));
		AssertMultiply(IsToEcho, (short)(SS + 1), (short) SS     , (int)  (((short)SS + (short)1) * (short)        SS            ));
		AssertMultiply(IsToEcho, (short)(SS + 1), (short)(SS + 1), (int)  (((short)SS + (short)1) * (short)((short)SS + (short)1)));
		
		AssertMultiply(IsToEcho, (short)(MS - 1), (short)(MS - 1), (int)  (((short)MS - (short)1) * ((short)MS - (short)1)));
		AssertMultiply(IsToEcho, (short)(MS - 1), (short) MS     , (int)  (((short)MS - (short)1) *  (short)MS           ));
		AssertMultiply(IsToEcho, (short) MS     , (short) MS     , (int)  ( (short)MS             *  (short)MS           ));
		
		int HI = HALF_INTEGER;
		int MI = Integer.MAX_VALUE;
		AssertPlus(IsToEcho, (int)(HI - 1), (int)(HI - 1), (int) ((int) HI + (int) HI - (int) 2));
		AssertPlus(IsToEcho, (int)(HI - 1), (int) HI     , (int) ((int) HI + (int) HI - (int) 1));
		AssertPlus(IsToEcho, (int) HI     , (int) HI     , (int) ((int) HI + (int) HI          ));
		AssertPlus(IsToEcho, (int)(HI + 1), (int) HI     , (int) ((int) HI + (int) HI + (int) 1));
		AssertPlus(IsToEcho, (int)(HI + 1), (int)(HI + 1), (long)((long)HI + (long)HI + (long)2));
		
		AssertPlus(IsToEcho, (int)(MI - 1), (int)(MI - 1), (long)((long)MI + (long)MI - (long)2));
		AssertPlus(IsToEcho, (int)(MI - 1), (int) MI     , (long)((long)MI + (long)MI - (long)1));
		AssertPlus(IsToEcho, (int) MI     , (int) MI     , (long)((long)MI + (long)MI          ));

		int SI = SROOT_INTEGER;
		AssertMultiply(IsToEcho, (int)(SI - 1), (int)(SI - 1), (int) (((int) SI - (int) 1) * (int)( (int) SI - (int)1)));
		AssertMultiply(IsToEcho, (int)(SI - 1), (int) SI     , (int) (((int) SI - (int) 1) * (int)        SI          ));
		AssertMultiply(IsToEcho, (int) SI     , (int) SI     , (int) ( (int) SI            * (int)        SI          ));
		AssertMultiply(IsToEcho, (int)(SI + 1), (int) SI     , (int) (((long)SI + (long)1) * (long)       SI          ));
		AssertMultiply(IsToEcho, (int)(SI + 1), (int)(SI + 1), (long)(((long)SI + (long)1) * (long)((long)SI + (long)1)));
		
		AssertMultiply(IsToEcho, (int)(MI - 1), (int)(MI - 1), (long)(((long)MI - (long)1) * ((long)MI - (long)1)));
		AssertMultiply(IsToEcho, (int)(MI - 1), (int) MI     , (long)(((long)MI - (long)1) *  (long)MI           ));
		AssertMultiply(IsToEcho, (int) MI     , (int) MI     , (long)( (long)MI            *  (long)MI           ));
		
		long HL = HALF_LONG;
		long ML = Long.MAX_VALUE;
		AssertPlus(IsToEcho, (long)(HL - 1L), (long)(HL - 1L), (long) ((long) HL + (long) HL - (long) 2L));
		AssertPlus(IsToEcho, (long)(HL - 1L), (long) HL      , (long) ((long) HL + (long) HL - (long) 1L));
		AssertPlus(IsToEcho, (long) HL      , (long) HL      , (long) ((long) HL + (long) HL           ));
		AssertPlus(IsToEcho, (long)(HL + 1L), (long) HL      , (long) ((long) HL + (long) HL + (long) 1L));
		AssertPlus(IsToEcho, (long)(HL + 1L), (long)(HL + 1L), getBigInteger(HL).add(getBigInteger(HL)).add(getBigInteger(2)));
		
		AssertPlus(IsToEcho, (long)(ML - 1L), (long)(ML - 1L), getBigInteger(ML).add(getBigInteger(ML)).add(getBigInteger(-2)));
		AssertPlus(IsToEcho, (long)(ML - 1L), (long) ML      , getBigInteger(ML).add(getBigInteger(ML)).add(getBigInteger(-1)));
		AssertPlus(IsToEcho, (long) ML      , (long) ML      , getBigInteger(ML).add(getBigInteger(ML)));

		long SL = SROOT_LONG;
		AssertMultiply(IsToEcho, (long)(SL - 1), (long)(SL - 1), (long) (((long) SL - (long) 1) * (long)((long) SL - (long)1)));
		AssertMultiply(IsToEcho, (long)(SL - 1), (long) SL     , (long) (((long) SL - (long) 1) * (long)        SL           ));
		AssertMultiply(IsToEcho, (long) SL     , (long) SL     , (long) ( (long) SL             * (long)        SL           ));
		AssertMultiply(IsToEcho, (long)(SL + 1), (long) SL     , getBigInteger(SL + 1L).multiply(getBigInteger(SL     )).longValue());
		AssertMultiply(IsToEcho, (long)(SL + 1), (long)(SL + 1), getBigInteger(SL + 1L).multiply(getBigInteger(SL + 1L))            );
		
		AssertMultiply(IsToEcho, (long)(ML - 1), (long)(ML - 1), getBigInteger(ML - 1L).multiply(getBigInteger(ML - 1L)));
		AssertMultiply(IsToEcho, (long)(ML - 1), (long) ML     , getBigInteger(ML - 1L).multiply(getBigInteger(ML     )));
		AssertMultiply(IsToEcho, (long) ML     , (long) ML     , getBigInteger(ML     ).multiply(getBigInteger(ML     )));
		
		double HF = HALF_FLOAT;
		double MF = Float.MAX_VALUE;
		double StF = Float.intBitsToFloat(0x73800000); 	// One step - for the precision of HALF_FLOAT
		AssertPlus(IsToEcho, (float)(HF - StF), (float)(HF - StF), (float)  ((float)((float)HF - (float)StF)  +          (float)((float)HF - (float)StF) ));
		AssertPlus(IsToEcho, (float)(HF - StF), (float) HF       , (float)  ((float)        HF                +          (float)((float)HF - (float)StF) ));
		AssertPlus(IsToEcho, (float) HF       , (float) HF       , (float)  ((float)        HF                +          (float)        HF              ));
		AssertPlus(IsToEcho, (float)(HF + StF), (float) HF       , (double)(((float)        HF              ) + (double)((float)((float)HF + (float)StF))));	// <- Ensure precision
		AssertPlus(IsToEcho, (float)(HF + StF), (float)(HF + StF), (double)(((float)((float)HF + (float)StF)) + (double)((float)((float)HF + (float)StF))));	// <- Ensure precision

		AssertPlus(IsToEcho, (float)(MF - StF), (float)(MF - StF), (double)(((float)((float)MF - (float)StF)) + (double)((float)((float)MF - (float)StF))));	// <- Ensure precision
		AssertPlus(IsToEcho, (float)(MF - StF), (float) MF       , (double)(((float)((float)MF - (float)StF)) + (double)((float)        MF             )));	// <- Ensure precision
		AssertPlus(IsToEcho, (float) MF       , (float) MF       , (double)((        (float)MF              ) + (double)((float)        MF             )));	// <- Ensure precision

		float SF  = SROOT_FLOAT;
		StF = Float.intBitsToFloat(0x53800000); 	// One step - for the precision of SROOT_FLOAT
		AssertMultiply(IsToEcho, (float)(SF - StF), (float)(SF - StF), (float) (((float) SF - StF) * (float)((float)  SF - StF)));
		AssertMultiply(IsToEcho, (float)(SF - StF), (float) SF       , (float) (((float) SF - StF) * (float)          SF       ));
		AssertMultiply(IsToEcho, (float) SF       , (float) SF       , (float) ( (float) SF        * (float)          SF       ));
		AssertMultiply(IsToEcho, (float)(SF + StF), (float) SF       , (float) (((double)SF + StF) * (double)         SF       ));
		AssertMultiply(IsToEcho, (float)(SF + StF), (float)(SF + StF), (double)(((double)SF + StF) * (double)((double)SF + StF )));
		
		StF = Float.intBitsToFloat(0x73800000); 	// One step - for the precision of MAX_FLOAT
		AssertMultiply(IsToEcho, (float)(MF - StF), (float)(MF - StF), (double)(((double)MF - StF) * ((double)MF - StF)));
		AssertMultiply(IsToEcho, (float)(MF - StF), (float) MF       , (double)(((double)MF - StF) *  (double)MF       ));
		AssertMultiply(IsToEcho, (float) MF       , (float) MF       , (double)( (double)MF        *  (double)MF       ));
		
		double HD  = HALF_DOUBLE;
		double MD  = Double.MAX_VALUE;
		double StD = Double.longBitsToDouble(0x7CA0000000000000L); 	// One step - for the precision of HALF_DOUBLE
		
		AssertPlus(IsToEcho, HD - StD, HD - StD, HD - StD + HD - StD);
		AssertPlus(IsToEcho, HD - StD, HD      , HD - StD + HD      );
		AssertPlus(IsToEcho, HD      , HD      , HD       + HD      );
		AssertPlus(IsToEcho, HD + StD, HD      , getBigDecimal(HD + StD).add(getBigDecimal(HD      )));	// <- Ensure precision
		AssertPlus(IsToEcho, HD + StD, HD + StD, getBigDecimal(HD + StD).add(getBigDecimal(HD + StD)));	// <- Ensure precision

		AssertPlus(IsToEcho, MD - StD, MD - StD, getBigDecimal(MD - StD).add(getBigDecimal(MD - StD)));	// <- Ensure precision
		AssertPlus(IsToEcho, MD - StD, MD      , getBigDecimal(MD - StD).add(getBigDecimal(MD      )));	// <- Ensure precision
		AssertPlus(IsToEcho, MD      , MD      , getBigDecimal(MD      ).add(getBigDecimal(MD      )));	// <- Ensure precision
		
		double SD = SROOT_DOUBLE;
		StD = Double.longBitsToDouble(0x5CA0000000000000L); 	// One step - for the precision of SROOT_FLOAT
		AssertMultiply(IsToEcho, (double)(SD - StD), (double)(SD - StD), (double)(((double) SD - (double)StD) * (double)((double) SD - (double)StD)));
		AssertMultiply(IsToEcho, (double)(SD - StD), (double) SD       , (double)(((double) SD - (double)StD) * (double)          SD               ));
		AssertMultiply(IsToEcho, (double) SD       , (double) SD       , getBigDecimal(SD      ).multiply(getBigDecimal(SD      )).doubleValue());
		AssertMultiply(IsToEcho, (double)(SD + StD), (double) SD       , getBigDecimal(SD + StD).multiply(getBigDecimal(SD      )));
		AssertMultiply(IsToEcho, (double)(SD + StD), (double)(SD + StD), getBigDecimal(SD + StD).multiply(getBigDecimal(SD + StD)));

		StD = Double.longBitsToDouble(0x7CA0000000000000L); 	// One step - for the precision of MAX_DOUBLE
		AssertMultiply(IsToEcho, (double)(MD - StD), (double)(MD - StD), getBigDecimal(MD - StD).multiply(getBigDecimal(MD - StD)));
		AssertMultiply(IsToEcho, (double)(MD - StD), (double) MD       , getBigDecimal(MD - StD).multiply(getBigDecimal(MD      )));
		AssertMultiply(IsToEcho, (double) MD       , (double) MD       , getBigDecimal(MD      ).multiply(getBigDecimal(MD      )));
		/* */ 
		// Value -------------------------------------------------------------------------------------------------------

		/* */
		// Assume Plus of BigDecimal always true
		System.out.println("Testing Value ... ");
		Random Rnd = new Random();
		
		int j = 0;
		for(int i = Short.MAX_VALUE; --i >= 0; j++) {
			if(j ==    4) {        IsToEcho = false; }
			if(j == 1024) { j = 0; IsToEcho = true;  }
			
			byte   b1 = (byte) Rnd.nextInt(HALF_BYPE);
			byte   b2 = (byte) Rnd.nextInt(HALF_BYPE);
			short  s1 = (short)Rnd.nextInt(HALF_SHORT);
			short  s2 = (short)Rnd.nextInt(HALF_SHORT);
			int    i1 = (int)  Rnd.nextInt(HALF_INTEGER);
			int    i2 = (int)  Rnd.nextInt(HALF_INTEGER);
			long   l1 = (long) Math.abs(Rnd.nextLong()) / 2L;
			long   l2 = (long) Math.abs(Rnd.nextLong()) / 2L;
			float  f1 = (float)Math.abs(Rnd.nextFloat()) / 2.0f;
			float  f2 = (float)Math.abs(Rnd.nextFloat()) / 2.0f;
			double d1 = Math.abs(Rnd.nextDouble()) / 2.0;
			double d2 = Math.abs(Rnd.nextDouble()) / 2.0;

			StF = Float .intBitsToFloat(  0x73800000);              // One step - for the precision of MAX_FLOAT
			StD = Double.longBitsToDouble(0x7CA0000000000000L); 	// One step - for the precision of MAX_DOUBLE
			
			// No upgrade
			AssertPlus(IsToEcho, (byte)  b1, (byte)  b2, (byte)  (b1+b2));
			AssertPlus(IsToEcho, (short) s1, (short) s2, (short) (s1+s2));
			AssertPlus(IsToEcho, (int)   i1, (int)   i2, (int)   (i1+i2));
			AssertPlus(IsToEcho, (long)  l1, (long)  l2, (long)  (l1+l2));
			AssertPlus(IsToEcho, (float) f1, (float) f2, (float) (f1+f2));
			AssertPlus(IsToEcho, (double)d1, (double)d2, (double)(d1+d2));
			
			AssertPlus(IsToEcho, (byte)  -b1, (byte)  -b2, (byte)  -(b1+b2));
			AssertPlus(IsToEcho, (short) -s1, (short) -s2, (short) -(s1+s2));
			AssertPlus(IsToEcho, (int)   -i1, (int)   -i2, (int)   -(i1+i2));
			AssertPlus(IsToEcho, (long)  -l1, (long)  -l2, (long)  -(l1+l2));
			AssertPlus(IsToEcho, (float) -f1, (float) -f2, (float) -(f1+f2));
			AssertPlus(IsToEcho, (double)-d1, (double)-d2, (double)-(d1+d2));
			
			
			// Calculate before it some
			Number rd_advance = plus(plus(plus(plus(plus(d1, d2), HALF_DOUBLE), HALF_DOUBLE), StD), StD);
			if(rd_advance instanceof BigDecimal) rd_advance = ((BigDecimal)rd_advance).negate();
			
			// Upgrade
			b1 += HALF_BYPE    +   1; b2 += HALF_BYPE    +   1;
			s1 += HALF_SHORT   +   1; s2 += HALF_SHORT   +   1;
			i1 += HALF_INTEGER +   1; i2 += HALF_INTEGER +   1;
			l1 += HALF_LONG    +   1; l2 += HALF_LONG    +   1;
			f1 += HALF_FLOAT   + StF; f2 += HALF_FLOAT   + StF;
			d1 += HALF_DOUBLE  + StD; d2 += HALF_DOUBLE  + StD;

			long       rb = -((long)b1 + (long)b2);
			long       rs = -((long)s1 + (long)s2);
			long       ri = -((long)i1 + (long)i2);
			BigInteger rl = getBigInteger(l1).add(getBigInteger(l2)).negate();
			double     rf = -((double)f1 + (double)f2);
			BigDecimal rd = getBigDecimal(d1).add(getBigDecimal(d2)).negate();

			Number Rb = Short  .valueOf((short)rb); if((b1 <= HALF_BYPE    +  1) && (b2 <= HALF_BYPE    +  1)) Rb = Byte   .valueOf((byte) rb);
			Number Rs = Integer.valueOf((int)  rs); if((s1 <= HALF_SHORT   +  1) && (s2 <= HALF_SHORT   +  1)) Rs = Short  .valueOf((short)rs);
			Number Ri = Long   .valueOf((long) ri); if((i1 <= HALF_INTEGER +  1) && (i2 <= HALF_INTEGER +  1)) Ri = Integer.valueOf((int)  ri);
			Number Rl =                        rl ; if((l1 <= HALF_LONG    +  1) && (l2 <= HALF_LONG    +  1)) Rl = rl.longValue();
			Number Rf = Double.valueOf((double)rf);
			Number Rd =                        rd ;
			
			AssertPlus(IsToEcho, (byte)  -b1, (byte)  -b2, Rb);
			AssertPlus(IsToEcho, (short) -s1, (short) -s2, Rs);
			AssertPlus(IsToEcho, (int)   -i1, (int)   -i2, Ri);
			AssertPlus(IsToEcho, (long)  -l1, (long)  -l2, Rl);
			AssertPlus(IsToEcho, (float) -f1, (float) -f2, Rf);
			AssertPlus(IsToEcho, (double)-d1, (double)-d2, Rd);
			
			// Multiple

			b1 -= HALF_BYPE    +   1; b2 -= HALF_BYPE    +   1;
			s1 -= HALF_SHORT   +   1; s2 -= HALF_SHORT   +   1;
			i1 -= HALF_INTEGER +   1; i2 -= HALF_INTEGER +   1;
			l1 -= HALF_LONG    +   1; l2 -= HALF_LONG    +   1;
			f1 -= HALF_FLOAT   + StF; f2 -= HALF_FLOAT   + StF;
			d1 -= HALF_DOUBLE  + StD; d2 -= HALF_DOUBLE  + StD;
			
			AssertPlusMultiple(IsToEcho, Rb, (byte)  -b1, (byte)  -b2, (byte)  -HALF_BYPE,    (byte)  -HALF_BYPE,    (byte)   -1, (byte)   -1);
			AssertPlusMultiple(IsToEcho, Rs, (short) -s1, (short) -s2, (short) -HALF_SHORT,   (short) -HALF_SHORT,   (short)  -1, (short)  -1);
			AssertPlusMultiple(IsToEcho, Ri, (int)   -i1, (int)   -i2, (int)   -HALF_INTEGER, (int)   -HALF_INTEGER, (int)    -1, (int)    -1);
			AssertPlusMultiple(IsToEcho, Rl, (long)  -l1, (long)  -l2, (long)  -HALF_LONG,    (long)  -HALF_LONG,    (long)   -1, (long)   -1);
			AssertPlusMultiple(IsToEcho, Rf, (float) -f1, (float) -f2, (float) -HALF_FLOAT,   (float) -HALF_FLOAT,   (float)-StF, (float)-StF);
			AssertPlusMultiple(IsToEcho, rd_advance, (double)-d1, (double)-d2, (double)-HALF_DOUBLE,  (double)-HALF_DOUBLE,  (double)-StD, (double)-StD);

			// Multiply ------------------------------------------------------------------------------------------------

			StF = Float .intBitsToFloat(  0x53800000);              // One step - for the precision of SROOT_FLOAT
			StD = Double.longBitsToDouble(0x5CA0000000000000L); 	// One step - for the precision of SROOT_DOUBLE
			
			b1 = (byte) Rnd.nextInt(SROOT_BYPE);
			b2 = (byte) Rnd.nextInt(SROOT_BYPE);
			s1 = (short)Rnd.nextInt(SROOT_SHORT);
			s2 = (short)Rnd.nextInt(SROOT_SHORT);
			i1 = (int)  Rnd.nextInt(SROOT_INTEGER);
			i2 = (int)  Rnd.nextInt(SROOT_INTEGER);
			l1 = (long) Math.sqrt(Math.abs(Rnd.nextLong()));
			l2 = (long) Math.sqrt(Math.abs(Rnd.nextLong()));
			f1 = (float)Math.sqrt(Math.abs(Rnd.nextFloat()));
			f2 = (float)Math.sqrt(Math.abs(Rnd.nextFloat()));
			d1 = Math.sqrt(Math.abs(Rnd.nextDouble()));
			d2 = Math.sqrt(Math.abs(Rnd.nextDouble()));
						
			// No upgrade
			AssertMultiply(IsToEcho, (byte)  b1, (byte)  b2, (byte)  (b1 * b2));
			AssertMultiply(IsToEcho, (short) s1, (short) s2, (short) (s1 * s2));
			AssertMultiply(IsToEcho, (int)   i1, (int)   i2, (int)   (i1 * i2));
			AssertMultiply(IsToEcho, (long)  l1, (long)  l2, (long)  (l1 * l2));
			AssertMultiply(IsToEcho, (float) f1, (float) f2, (float) (f1 * f2));
			AssertMultiply(IsToEcho, (double)d1, (double)d2, (double)(d1 * d2));
			
			AssertMultiply(IsToEcho, (byte)  -b1, (byte)  -b2, (byte)  (b1 * b2));
			AssertMultiply(IsToEcho, (short) -s1, (short) -s2, (short) (s1 * s2));
			AssertMultiply(IsToEcho, (int)   -i1, (int)   -i2, (int)   (i1 * i2));
			AssertMultiply(IsToEcho, (long)  -l1, (long)  -l2, (long)  (l1 * l2));
			AssertMultiply(IsToEcho, (float) -f1, (float) -f2, (float) (f1 * f2));
			AssertMultiply(IsToEcho, (double)-d1, (double)-d2, (double)(d1 * d2));
			
			// Upgrade
			b1 += SROOT_BYPE    +   1; b2 += SROOT_BYPE    +   1;
			s1 += SROOT_SHORT   +   1; s2 += SROOT_SHORT   +   1;
			i1 += SROOT_INTEGER +   1; i2 += SROOT_INTEGER +   1;
			l1 += SROOT_LONG    +   1; l2 += SROOT_LONG    +   1;
			f1 += SROOT_FLOAT   + StF; f2 += SROOT_FLOAT   + StF;
			d1 += SROOT_DOUBLE  + StD; d2 += SROOT_DOUBLE  + StD;

			rb = ((long)b1 * (long)b2);
			rs = ((long)s1 * (long)s2);
			ri = ((long)i1 * (long)i2);
			rl = getBigInteger(l1).multiply(getBigInteger(l2));
			rf = ((double)f1 * (double)f2);
			rd = getBigDecimal(d1).multiply(getBigDecimal(d2));

			Rb = Short  .valueOf((short)rb); if((b1 <= SROOT_BYPE   ) && (b2 <= SROOT_BYPE   )) Rb = Byte   .valueOf((byte) rb);
			Rs = Integer.valueOf((int)  rs); if((s1 <= SROOT_SHORT  ) && (s2 <= SROOT_SHORT  )) Rs = Short  .valueOf((short)rs);
			Ri = Long   .valueOf((long) ri); if((i1 <= SROOT_INTEGER) && (i2 <= SROOT_INTEGER)) Ri = Integer.valueOf((int)  ri);
			Rl =                        rl ; if((l1 <= SROOT_LONG   ) && (l2 <= SROOT_LONG   )) Rl = rl.longValue();
			Rf = Double.valueOf((double)rf);
			Rd =                        rd ;
			
			AssertMultiply(IsToEcho, (byte)  -b1, (byte)  -b2, Rb);
			AssertMultiply(IsToEcho, (short) -s1, (short) -s2, Rs);
			AssertMultiply(IsToEcho, (int)   -i1, (int)   -i2, Ri);
			AssertMultiply(IsToEcho, (long)  -l1, (long)  -l2, Rl);
			AssertMultiply(IsToEcho, (float) -f1, (float) -f2, Rf);
			AssertMultiply(IsToEcho, (double)-d1, (double)-d2, Rd);
		}
		/* */
	}
	
	// Multiple -------------------------------------------------------------------------
	
	static private String getMessageMultiply(Number N1, Number N2, Number NResult) {
		return String.format("(%10s%s x %10s%s) = %10s%s",
				N1,      getKindShort(N1),
				N2,      getKindShort(N2),
				NResult, getKindShort(NResult)
			   );
	}
	static private void AssertMultiply(boolean IsToEcho, Number N1, Number N2, Number NResult) {
		
		if(IsToEcho) System.out.println(getMessageMultiply(N1, N2, NResult));
		Number NR = multiply(N1, N2);
		if(Check(NR, NResult)) return;
		
		throw new AssertionError(getMessageMultiply(N1, N2, NR) + " != " + String.format("%10s%s", NResult, getKindShort(NResult)));
	}
	
	/**
	 * @param Args
	 */
	static public void main(String ... Args) {
		testPlusMultiply(true);
 		System.out.println("DONE ---------------------------------------------------------------------------------------");
	}

}

package sun.sdn.swing.treetable;


/*
 * %W% %E%
 *
 * Copyright 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer. 
 *   
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution. 
 *   
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.  
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE 
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,   
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER  
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF 
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS 
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

/**
 * An implementation of MergeSort, needs to be sub-classed to provide a comparator.
 *
 * @version %I% %G%
 *
 * @author Scott Violet
 */
public abstract class MergeSort extends Object {
    
    protected Object ToSort[];
    protected Object SwapSpace[];
   
    public void sort(final Object pArray[]) {
       if (pArray != null && pArray.length > 1) {
           final int aMaxLength = pArray.length;
          
           this.SwapSpace = new Object[aMaxLength];
           this.ToSort = pArray;
           this.mergeSort(0, aMaxLength - 1);
           this.SwapSpace = null;
           this.ToSort    = null;
       }
    }
    
    public abstract int compareElementsAt(
            final int pBeginLoc,
            final int pEndLoc);
    
    protected void mergeSort(
            final int pBegin,
            final int pEnd) {
        if (pBegin != pEnd) {
            final int mid = (pBegin + pEnd) / 2;
            this.mergeSort(pBegin, mid);
            this.mergeSort(mid + 1, pEnd);
            this.merge(pBegin, mid, pEnd);
        }
    }
    
   protected void merge(
           final int pBegin,
           final int pMiddle,
           final int pEnd) {
       
       int aFirstHalf;
       int aSecondHalf;
       int aCount;
       
       aFirstHalf  = aCount = pBegin;
       aSecondHalf = pMiddle + 1;
       while ((aFirstHalf <= pMiddle) && (aSecondHalf <= pEnd)) {
           if (this.compareElementsAt(aSecondHalf, aFirstHalf) < 0)
                this.SwapSpace[aCount++] = this.ToSort[aSecondHalf++];
           else this.SwapSpace[aCount++] = this.ToSort[aFirstHalf++];
       }
       if (aFirstHalf <= pMiddle) {
           while (aFirstHalf <= pMiddle)
               this.SwapSpace[aCount++] = this.ToSort[aFirstHalf++];
       } else {
           while (aSecondHalf <= pEnd)
               this.SwapSpace[aCount++] = this.ToSort[aSecondHalf++];
       }
       for (aCount = pBegin; aCount <= pEnd; aCount++)
           this.ToSort[aCount] = this.SwapSpace[aCount];
    }
}

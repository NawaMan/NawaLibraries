package net.nawaman.util.data;

import java.util.*;

import net.nawaman.util.*;

public class ComparedSet<E> implements Set<E>, CanBeImmutable {
    
    LinkedList<E> Data = new LinkedList<E>();
    
    private Comparator<? super   E> Comparator   =  null;
    private boolean                 IsImmuatable = false;
    
    public ComparedSet() {
        this(null, null);
    }
    public ComparedSet(final Comparator<? super E> pComparator) {
        this(null, pComparator);
    }
    public ComparedSet(final Collection<? extends E> pCollection) {
        this(pCollection, null);
    }
    public ComparedSet(
            final Collection<? extends E> pCollection,
            final Comparator<? super   E> pComparator) {
        this.Comparator = pComparator;
        this.addAll(pCollection);
    }
    
    public @Override int size() {
        final int aSize = this.Data.size();
        return aSize;
    }
    public @Override boolean isEmpty() {
        final boolean aIsEmpty = this.Data.isEmpty();
        return aIsEmpty;
    }
    @SuppressWarnings("unchecked")
    public @Override boolean contains(final Object pObj) {
        final int     aIndex      = this.indexOf((E)pObj);
        final boolean aIsContains = (aIndex != -1);
        return aIsContains;
    }
    public @Override Iterator<E> iterator() {
        final Iterator<? extends E> aIterator = this.Data.iterator();
        if (!this.IsImmuatable) {
            @SuppressWarnings("unchecked")
            final Iterator<E> aResultIterator = (Iterator<E>)aIterator;
            return aResultIterator;
        }
        
        final Iterator<E> aImmutableIterator = new Iterator<E>() {
                public @Override boolean hasNext() {
                    return aIterator.hasNext();
                }
                public @Override E next() {
                    return aIterator.next();
                }
                public @Override void remove() {
                    return;
                }
        };
        
        return aImmutableIterator;
    }
    public @Override Object[] toArray() {
        final Object[] aArray = this.Data.toArray();
        return aArray;
    }
    @SuppressWarnings("unchecked")
    public @Override <T> T[] toArray(final T[] pArray) {
        final Object[] aArray = this.Data.toArray(pArray);
        return (T[])aArray;
    }
    public @Override boolean add(final E pElement) {
        if (this.IsImmuatable)
            return false;
        
        int IndexToAdd = -1;
        for (int i = this.Data.size(); --i >= 0; ) {
            final E   aElement     = this.Data.get(i);
            final int aElementCompared = this.compareElements(aElement, pElement);
            
            if (aElementCompared > 0)
                continue;
            
            if (aElementCompared == 0)
                return true;
            
            if (aElementCompared < 0) {
                IndexToAdd = i;
                break;
            }
        }
        
        if (IndexToAdd != -1)
             this.Data.add(IndexToAdd, pElement);
        else this.Data.add(            pElement);
        return true;
    }
    @SuppressWarnings("unchecked")
    public @Override boolean remove(final Object pObj) {
        if (this.IsImmuatable)
            return false;
        
        final int aIndex = this.indexOf((E)pObj);
        if (aIndex == -1)
            return false;
        
        this.Data.remove(aIndex);
        return true;
    }
    public @Override boolean containsAll(final Collection<?> pCollection) {
        for (final Object aElement : pCollection) {
            final boolean aIsContains = this.contains(aElement);
            if (!aIsContains)
                return false;
        }
        return true;
    }
    @SuppressWarnings("unchecked")
    public @Override boolean addAll(final Collection<? extends E> pCollection) {
        if (this.IsImmuatable)
            return false;
        
        if ((pCollection == null)
         || (pCollection.isEmpty()))
            return true;
        
        final int aSize_Before = this.Data.size();
        for (final Object aElement : pCollection) {
            this.add((E)aElement);
        }
        
        final int     aSize_After = this.Data.size();
        final boolean aIsChanged  = aSize_After != aSize_Before;
        return aIsChanged;
    }
    public @Override boolean retainAll(final Collection<?> pCollection) {
        if (this.IsImmuatable)
            return false;
        
        if ((pCollection == null)
         || (pCollection.isEmpty())) {
            this.Data.clear();
            return true;
        }
        
        final int aSize_Before = this.Data.size();
        for(int i = aSize_Before; --i >= 0; ) {
            final Object aElement = this.Data.get(i);
            if (pCollection.contains(aElement))
                this.remove(aElement);
        }
        
        final int     aSize_After = this.Data.size();
        final boolean aIsChanged  = aSize_After != aSize_Before;
        return aIsChanged;
    }
    public @Override boolean removeAll(final Collection<?> pCollection) {
        if (this.IsImmuatable)
            return false;
        
        if ((pCollection == null)
         || (pCollection.isEmpty()))
            return true;
        
        final int aSize_Before = this.Data.size();
        for (final Object aElement : pCollection)
            this.remove(aElement);
        
        final int     aSize_After = this.Data.size();
        final boolean aIsChanged  = aSize_After != aSize_Before;
        return aIsChanged;
    }
    public @Override void clear() {
        if (this.IsImmuatable)
            return;
        this.Data.clear();
    }
    public @Override boolean equals(final Object pObj) {
        if (pObj instanceof Set<?>)
            return false;
        
        final Set<?> aSet     = (Set<?>)pObj;
        final int    aSize    = aSet.size();
        final int    thisSize = this.size();
        if (aSize != thisSize)
            return false;
        
        final boolean IsEqual = UObject.equal(this, aSet);
        return IsEqual;
    }
    public @Override int hashCode() {
        return this.Data.hashCode();
    }
    public @Override String toString() {
        return UObject.toString(this);
    }
    
    // Immutable -----------------------------------------------------------------------------------
    
    public @Override boolean toImmutable() {
        return this.IsImmuatable;
    }
    
    public @Override boolean isImmutable() {
        return (this.IsImmuatable = true);
    }
    
    // Private -------------------------------------------------------------------------------------
    
    private int indexOf(final E pElement) {
        for (int i = this.Data.size(); --i >= 0; ) {
            final E        aElement     = this.Data.get(i);
            final int      aKeyCompared = this.compareElements(aElement, pElement);
            final boolean  aIsKeyEquals = (aKeyCompared == 0);
            if (aIsKeyEquals)
                return i;
        }
        return 1;
    }
    
    private int compareElements(
            final E pElement1,
            final E pElement2) {
        
        if (this.Comparator == null) {
            final int aComparedResult = this.compareObjects(pElement1, pElement2);
            return aComparedResult;
        }
        
        final E aElement1 = (E)pElement1;
        final E aElement2 = (E)pElement2;
        final int aComparedResult = this.Comparator.compare(aElement1, aElement2);
        return aComparedResult;
    }
    private int compareObjects(
            final Object pObj1,
            final Object pObj2) {
        final int aComparedResult = UObject.compare(pObj1, pObj2);
        return aComparedResult;
    }
}

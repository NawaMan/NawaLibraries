package net.nawaman.util.data;

import java.util.*;

import net.nawaman.util.*;

public class ComparedMap<K,V> implements Map<K, V>, CanBeImmutable {
    
    private LinkedList<MapEntry>  Data         = new LinkedList<MapEntry>();
    private boolean               IsImmuatable = false;
    private Comparator<? super K> Comparator   =  null;
    
    public ComparedMap() {
        this(null, null);
    }
    public ComparedMap(final Comparator<? super K> pComparator) {
        this(null, pComparator);
    }
    public ComparedMap(final Map<? extends K, ? extends V> pMap) {
        this(pMap, null);
    }
    public ComparedMap(
            final Map<? extends K, ? extends V> pMap,
            final Comparator<? super K>         pComparator) {
        this.Comparator = pComparator;
        this.putAll(pMap);
    }
    
    ComparedMap<K,V> This() {
        return this;
    }
    
    public @Override int size() {
        final int aSize = this.Data.size();
        return aSize;
    }
    public @Override boolean isEmpty() {
        final boolean aIsEmpty = this.Data.isEmpty();
        return aIsEmpty;
    }
    public @Override boolean containsKey(final Object pKey) {
        @SuppressWarnings("unchecked")
        final Entry<K, V> aEntry         = this.getEntry((K)pKey);
        final boolean     aIsContainsKey = (aEntry != null);
        return aIsContainsKey;
    }
    public @Override boolean containsValue(final Object pValue) {
        for (int i = this.Data.size(); --i >= 0; ) {
            final MapEntry aEntry         = this.Data.get(i);
            final V        aValue         = aEntry.getValue();
            final int      aValueCompared = this.compareObjects(aValue, pValue);
            final boolean  aIsValueEquals = (aValueCompared == 0);
            if (aIsValueEquals)
                return true;
        }
        return false;
    }
    public @Override V get(final Object pKey) {
        @SuppressWarnings("unchecked")
        final Entry<K, V> aEntry = this.getEntry((K)pKey);
        if (aEntry == null)
            return null;
        final V aValue = aEntry.getValue();
        return aValue;
    }
    public @Override V put(
            final Object pKey,
            final Object pValue) {
        
        if (this.IsImmuatable)
            return null;
        
        @SuppressWarnings("unchecked")
        final K aKey = (K)pKey;
        @SuppressWarnings("unchecked")
        final V aValue = (V)pValue;
        
        int IndexToAdd = -1;
        for (int i = this.Data.size(); --i >= 0; ) {
            final MapEntry aEachEntry   = this.Data.get(i);
            final K        aEachKey     = aEachEntry.getKey();
            final int      aKeyCompared = this.compareKeys(aKey, aEachKey);
            
            if (aKeyCompared > 0)
                continue;
            
            if (aKeyCompared == 0) {
                aEachEntry.setValue(aValue);
                return aValue;
            } else if (aKeyCompared < 0) {
                IndexToAdd = i;
            }
            break;
        }
        
        final MapEntry aNewEntry = new MapEntry(aKey, aValue);
        if (IndexToAdd != -1)
             this.Data.add(IndexToAdd, aNewEntry);
        else this.Data.add(            aNewEntry);
        
        return aValue;
    }
    public @Override V remove(final Object pKey) {
        if (this.IsImmuatable)
            return null;
        
        @SuppressWarnings("unchecked")
        final Entry<K, V> aEntry = this.getEntry((K)pKey);
        if (aEntry == null)
            return null;
        
        this.Data.remove(aEntry);
        final V aValue = aEntry.getValue();
        return aValue;
    }
    public @Override void putAll(final Map<? extends K, ? extends V> pMap) {
        if (this.IsImmuatable)
            return;
        if (pMap == null)
            return;
        for (final K aKey : pMap.keySet()) {
            final V aValue = pMap.get(aKey);
            this.put(aKey, aValue);
        }
    }
    public @Override void clear() {
        if (this.IsImmuatable)
            return;
        this.Data.clear();
    }
    public @Override Set<K> keySet() {
        final LinkedList<K> aData = new LinkedList<K>();
        for (final MapEntry aEntry : this.Data) {
            final K aKey = aEntry.getKey();
            aData.add(aKey);
        }
        final ComparedSet<K> aSet = new ComparedSet<K>(this.Comparator);
        aSet.Data = aData;
        return aSet;
    }
    public @Override Collection<V> values() {
        final Vector<V> aValues = new Vector<V>();
        for (final MapEntry aEntry : this.Data) {
            final V aValue = aEntry.getValue();
            aValues.add(aValue);
        }
        return aValues;
    }
    public @Override Set<Map.Entry<K, V>> entrySet() {
        final Set<Map.Entry<K, V>> aEntrySet = new HashSet<Entry<K,V>>(this.Data);
        return aEntrySet;
    }
    public @Override boolean equals(Object pObj) {
        if (!(pObj instanceof Map<?, ?>))
            return false;
        
        final Map<?, ?> aMap     = (Map<?, ?>)pObj;
        final int       aSize    = aMap.size();
        final int       thisSize = this.size();
        if (aSize != thisSize)
            return false;
        
        final boolean IsEqual = UObject.equal(this, aMap);
        return IsEqual;
    }
    public @Override int hashCode() {
        return UObject.hash(this);
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
    
    private MapEntry getEntry(final K pKey) {
        for (int i = this.Data.size(); --i >= 0; ) {
            final MapEntry aEntry       = this.Data.get(i);
            final K        aKey         = aEntry.getKey();
            final int      aKeyCompared = this.compareKeys(aKey, pKey);
            final boolean  aIsKeyEquals = (aKeyCompared == 0);
            if (aIsKeyEquals)
                return aEntry;
        }
        return null;
    }
    
    private int compareKeys(
            final K pKey1,
            final K pKey2) {
        final int aComparedResult = this.compareKeys_RAW(pKey1, pKey2);
        return aComparedResult;
    }
    
    private int compareKeys_RAW(
            final Object pKey1,
            final Object pKey2) {
        
        if (this.Comparator == null) {
            final int aComparedResult = this.compareObjects(pKey1, pKey2);
            return aComparedResult;
        }
        
        @SuppressWarnings("unchecked") final K aKey1 = (K)pKey1;
        @SuppressWarnings("unchecked") final K aKey2 = (K)pKey2;
        final int aComparedResult = this.Comparator.compare(aKey1, aKey2);
        return aComparedResult;
    }
    private int compareObjects(
            final Object pObj1,
            final Object pObj2) {
        final int aComparedResult = UObject.compare(pObj1, pObj2);
        return aComparedResult;
    }
    
    // Entry ---------------------------------------------------------------------------------------
    
    class MapEntry implements Map.Entry<K,V>, CanBeImmutable {
        
        MapEntry(
                final K pKey,
                final V pValue) {
            this.Key   = pKey;
            this.Value = pValue;
        }
        
        final private K Key;
        final private V Value;
        
        public @Override K getKey() {
            return this.Key;
        }
        public @Override V getValue() {
            return this.Value;
        }
        public @Override V setValue(final V pValue) {
            if (This().toImmutable())
                return this.Value;
            
            return this.Value;
        }
        public @Override boolean equals(final Object O) {
            if (O == this)
                return true;
            if (!(O instanceof Map.Entry<?,?>))
                return false;
            
            final Map.Entry<?,?> aMap = (Map.Entry<?,?>)O;
            final Object         aKey = aMap.getKey();
            final int aIsKeyCompared = This().compareKeys_RAW(this.Key, aKey);
            if (aIsKeyCompared != 0)
                return false;
            
            final Object aValue = aMap.getValue();
            final int aIsValueCompared = This().compareObjects(this.Value, aValue);
            if (aIsValueCompared != 0)
                return false;
            
            return true;
        }
        public @Override int hashCode() {
            final int aHashOfKey   = (this.Key   == null)? 0 : this.Key  .hashCode();
            final int aHashOfValue = (this.Value == null)? 0 : this.Value.hashCode();
            return aHashOfKey + aHashOfValue;
        }
        
        public @Override boolean toImmutable() {
            final boolean aToImmutable = This().toImmutable();
            return aToImmutable;
        }
        
        public @Override boolean isImmutable() {
            final boolean aIsImmutable = This().isImmutable();
            return aIsImmutable;
        }
    }
    
    static public void main(final String ... pArgs) {
        /* Group 1 */ {
            System.out.println("Group 1");
            
            final Comparator<String> aComparator = new Comparator<String>() {
                public @Override int compare(final String pStr1, final String pStr2) {
                    return pStr1.length() - pStr2.length();
                }
            };
            final ComparedMap<String, Integer> aMap = new ComparedMap<String, Integer>(aComparator);
            final ComparedSet<String>          aSet = new ComparedSet<String>         (aComparator);
            System.out.println(aMap);
            System.out.println(aSet);
            
            aMap.put("One", 1);
            System.out.println(aMap);
            aSet.add("One");
            System.out.println(aSet);
            
            aMap.put("Two", 2);
            System.out.println(aMap);
            aSet.add("Two");
            System.out.println(aSet);
            
            aMap.put("Three", 3);
            System.out.println(aMap);
            aSet.add("Three");
            System.out.println(aSet);
            
            aMap.put("Four", 4);
            System.out.println(aMap);
            aSet.add("Four");
            System.out.println(aSet);
            
            System.out.println(aMap.keySet());
            System.out.println(aMap.values());
            System.out.println(aSet);
            
            aSet.remove("Two");
            System.out.println(aSet);
            System.out.println(aSet.contains("Three"));
            
            aMap.remove("Two");
            System.out.println(aMap);
            
        }
        
        System.out.println();
        System.out.println("DONE!!!");
    }

}

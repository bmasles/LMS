package com.smoothstack.lms.borrowermicroservice.database.sql;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class SQLDataMap extends LinkedHashMap<String, Object> {

    /**
     * Constructs an empty insertion-ordered <tt>LinkedHashMap</tt> instance
     * with the specified initial capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is negative
     *                                  or the load factor is nonpositive
     */
    public SQLDataMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Constructs an empty insertion-ordered <tt>LinkedHashMap</tt> instance
     * with the specified initial capacity and a default load factor (0.75).
     *
     * @param initialCapacity the initial capacity
     * @throws IllegalArgumentException if the initial capacity is negative
     */
    public SQLDataMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs an empty insertion-ordered <tt>LinkedHashMap</tt> instance
     * with the default initial capacity (16) and load factor (0.75).
     */
    public SQLDataMap() {
    }

    /**
     * Constructs an insertion-ordered <tt>LinkedHashMap</tt> instance with
     * the same mappings as the specified map.  The <tt>LinkedHashMap</tt>
     * instance is created with a default load factor (0.75) and an initial
     * capacity sufficient to hold the mappings in the specified map.
     *
     * @param m the map whose mappings are to be placed in this map
     * @throws NullPointerException if the specified map is null
     */
    public SQLDataMap(Map<? extends String, ?> m) {
        super(m);
    }

    /**
     * Constructs an empty <tt>LinkedHashMap</tt> instance with the
     * specified initial capacity, load factor and ordering mode.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor      the load factor
     * @param accessOrder     the ordering mode - <tt>true</tt> for
     *                        access-order, <tt>false</tt> for insertion-order
     * @throws IllegalArgumentException if the initial capacity is negative
     *                                  or the load factor is nonpositive
     */
    public SQLDataMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    static SQLDataMap newDataMap() {
        return (SQLDataMap) new LinkedHashMap<String, Object>();
    }

    public String keySetCsv() {
        return String.join(", ", keySet());
    }

    public  String keyReferenceCsv() {
        return keySet().stream().map(v-> "?").collect(Collectors.joining(", "));
    }

    public  String valueReferenceCsv() {
        return values().stream().map(v-> "?").collect(Collectors.joining(", "));
    }

    public  String keyValueReferenceCsv() {
        return keySet().stream().map(k-> String.format("%s = ?", k)).collect(Collectors.joining(", "));
    }

    public  Object getByIndex(int index) {
        Object[] keyArray = keySet().toArray();
        if (index < keyArray.length)
            return get(keyArray[index]);
        else
            return null;
    }
    public  int getKeyIndex(Object key, int start) {
        if (!containsKey(key))
            return -1;

        int i = 0;
        for (Object k : keySet()) {
            if (key.equals(k))
                return i + start;
            ++i;
        }

        return -1;
    }



    public  int getKeyIndex(Object key) {
        return getKeyIndex(key, 1);
    }

    public static  <R> R onDuplicatedKeyDrop(R v1, R v2) {
        return v1;
    }

    public static  <R> R onDuplicatedKeyReplace(R v1, R v2) {
        return v2;
    }

    public static  <R> R onDuplicatedKeyThrow(R v1, R v2) {
        throw new IllegalArgumentException("Duplicated key detected.");
    }
}

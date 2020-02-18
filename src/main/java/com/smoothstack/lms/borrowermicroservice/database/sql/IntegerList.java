package com.smoothstack.lms.borrowermicroservice.database.sql;

import java.util.*;
import java.util.stream.Collectors;

public class IntegerList extends ArrayList<Integer> {

    public static IntegerList emptyList() {
        return new IntegerList();
    }
    public IntegerList(int initialCapacity) {
        super(initialCapacity);
    }

    public IntegerList() {
    }

    public IntegerList(Collection<? extends Integer> c) {
        super(c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        IntegerList other = (IntegerList) o;

        if (this.size() != other.size())
            return false;

        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(other.get(i)))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (Integer integer : this)
            hashCode = 31*hashCode + (integer==null ? 0 : integer.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
      return "["+this.stream().map(String::valueOf).collect(Collectors.joining(","))+"]";
    }

    public Integer get() {
        return this.size()>0?this.get(0):0;
    }
    public void set(Integer i) {
        if (this.size() > 0) {
            this.set(0, i);
        } else {
            this.add(i);
        }
    }

    public static IntegerList of (Integer... integers) {
        return new IntegerList(Arrays.asList(integers));
    }
}

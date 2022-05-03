package com.cwt.symbolinfinitemedian.util;

import com.cwt.symbolinfinitemedian.model.SymbolModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RBHashMap {

    private static final int SIZE = 1997;

    private Entry table[] = new Entry[SIZE];

    public class Entry {
        String key;
        SymbolModel value;
        Entry next;

        Entry(String k, SymbolModel v) {
            key = k;
            value = v;
        }

        public SymbolModel getValue() {
            return value;
        }

        public void setValue(SymbolModel value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }
    }

    /**
     * * REFERENCE: JAVA SOURCE CODE *
     *
     * Applies a supplemental hash function to a given hashCode, which defends
     * against poor quality hash functions. This is critical because HashMap
     * uses power-of-two length hash tables, that otherwise encounter collisions
     * for hashCodes that do not differ in lower bits. Note: Null keys always
     * map to hash 0, thus index 0.
     */
    private int getSupplementalHash(int h) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * It makes sure the bucket number falls within the size of the hashmap
     *
     * @param hash
     * @return returns index for hashcode hash
     */
    private int getBucketNumber(int hash) {
        return hash & (SIZE - 1);
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is
     * replaced.
     */
    public void put(String key, SymbolModel value) {
        // get the hashcode and regenerate it to be optimum
        int userHash = key.hashCode();
        int hash = getSupplementalHash(userHash);

        // compute the bucket number
        int bucket = getBucketNumber(hash);
        Entry existingElement = table[bucket];

        for (; existingElement != null; existingElement = existingElement.next) {

            if (existingElement.key.equals(key)) {
                System.out
                        .println("duplicate key value pair, replacing existing key " + key + ", with value " + value);
                existingElement.value = value;
                return;
            } else {
                System.out.println("Collision detected for key " + key  + ", adding element to the existing bucket");
            }
        }
        System.out.println("PUT adding key:" + key + ", value:" + value + " to the list");
        Entry entryInOldBucket = new Entry(key, value);
        entryInOldBucket.next = table[bucket];
        table[bucket] = entryInOldBucket;
    }

    /**
     * Returns the entry associated with the specified key in the HashMap.
     * Returns null if the HashMap contains no mapping for the key.
     */
    public Entry get(String key) {
        // get the hashcode and regenerate it to be optimum
        int hash = getSupplementalHash(key.hashCode());

        // compute the bucket number (0-15 based on our default size)
        // this always results in a number between 0-15
        int bucket = getBucketNumber(hash);

        // get the element at the above bucket if it exists
        Entry existingElement = table[bucket];

        // if bucket is found then traverse through the linked list and
        // see if element is present
        while (existingElement != null) {
            System.out
                    .println("Traversing the list inside the bucket for the key "
                            + existingElement.getKey());
            if (existingElement.key.equals(key)) {
                return existingElement;
            }
            existingElement = existingElement.next;
        }

        // if nothing is found then return null
        return null;
    }

}
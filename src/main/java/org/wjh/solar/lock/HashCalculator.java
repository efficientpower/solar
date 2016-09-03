package org.wjh.solar.lock;

public class HashCalculator {
    static final int MAX_SEGMENTS = 1 << 16;
    final int segmentMask;
    final int segmentShift;

    public HashCalculator(int concurrencyLevel) {
        if (concurrencyLevel > MAX_SEGMENTS){
            concurrencyLevel = MAX_SEGMENTS;
        }
        int sshift = 0;
        int ssize = 1;
        while (ssize < concurrencyLevel) {
            ++sshift;
            ssize <<= 1;
        }
        segmentShift = 32 - sshift;
        segmentMask = ssize - 1;
    }

    private int jenkinsHash(int h) {
        // Spread bits to regularize both segment and index locations,
        // using variant of single-word Wang/Jenkins hash.
        h += (h << 15) ^ 0xffffcd7d;
        h ^= (h >>> 10);
        h += (h << 3);
        h ^= (h >>> 6);
        h += (h << 2) + (h << 14);
        return h ^ (h >>> 16);
    }

    public int getIndexOfValue(int value) {
        int hash = jenkinsHash(value);
        return (hash >>> segmentShift) & segmentMask;
    }

    public int getIndexMask() {
        return segmentMask;
    }
}

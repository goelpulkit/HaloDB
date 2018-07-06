/*
 * Copyright 2018, Oath Inc
 * Licensed under the terms of the Apache License 2.0. Please refer to accompanying LICENSE file for terms.
 */

// This code is a derivative work heavily modified from the OHC project. See NOTICE file for copyright and license.

package com.oath.halodb.cache;

import com.google.common.base.Objects;
import com.oath.halodb.cache.linked.SegmentStats;

public final class OHCacheStats
{
    private final long hitCount;
    private final long missCount;
    private final long capacity;
    private final long size;
    private final long rehashCount;
    private final long putAddCount;
    private final long putReplaceCount;
    private final long putFailCount;
    private final long removeCount;
    private final long totalAllocated;
    private final long lruCompactions;
    private final SegmentStats[] segmentStats;

    public OHCacheStats(long hitCount, long missCount,
                        long size, long capacity, long free, long rehashCount,
                        long putAddCount, long putReplaceCount, long putFailCount, long removeCount,
                        long totalAllocated, long lruCompactions, SegmentStats[] segmentStats)
    {
        this.hitCount = hitCount;
        this.missCount = missCount;
        this.size = size;
        this.capacity = capacity;
        this.rehashCount = rehashCount;
        this.putAddCount = putAddCount;
        this.putReplaceCount = putReplaceCount;
        this.putFailCount = putFailCount;
        this.removeCount = removeCount;
        this.totalAllocated = totalAllocated;
        this.lruCompactions = lruCompactions;
        this.segmentStats = segmentStats;
    }

    public long getCapacity()
    {
        return capacity;
    }

    public long getRehashCount()
    {
        return rehashCount;
    }

    public long getHitCount()
    {
        return hitCount;
    }

    public long getMissCount()
    {
        return missCount;
    }

    public long getSize()
    {
        return size;
    }

    public long getPutAddCount()
    {
        return putAddCount;
    }

    public long getPutReplaceCount()
    {
        return putReplaceCount;
    }

    public long getPutFailCount()
    {
        return putFailCount;
    }

    public long getRemoveCount()
    {
        return removeCount;
    }

    public long getTotalAllocated()
    {
        return totalAllocated;
    }

    public long getLruCompactions()
    {
        return lruCompactions;
    }

    public SegmentStats[] getSegmentStats() {
        return segmentStats;
    }

    public String toString()
    {
        return Objects.toStringHelper(this)
                      .add("hitCount", hitCount)
                      .add("missCount", missCount)
                      .add("size", size)
                      .add("capacity", capacity)
                      .add("rehashCount", rehashCount)
                      .add("put(add/replace/fail)", Long.toString(putAddCount)+'/'+putReplaceCount+'/'+putFailCount)
                      .add("removeCount", removeCount)
                      .add("totalAllocated", totalAllocated)
                      .add("lruCompactions", lruCompactions)
                      .toString();
    }

    private static long maxOf(long[] arr)
    {
        long r = 0;
        for (long l : arr)
            if (l > r)
                r = l;
        return r;
    }

    private static long minOf(long[] arr)
    {
        long r = Long.MAX_VALUE;
        for (long l : arr)
            if (l < r)
                r = l;
        return r;
    }

    private static double avgOf(long[] arr)
    {
        double r = 0d;
        for (long l : arr)
            r += l;
        return r / arr.length;
    }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OHCacheStats that = (OHCacheStats) o;

        if (capacity != that.capacity) return false;
        if (hitCount != that.hitCount) return false;
        if (missCount != that.missCount) return false;
        if (putAddCount != that.putAddCount) return false;
        if (putFailCount != that.putFailCount) return false;
        if (putReplaceCount != that.putReplaceCount) return false;
//        if (rehashCount != that.rehashCount) return false;
        if (removeCount != that.removeCount) return false;
        if (size != that.size) return false;
//        if (totalAllocated != that.totalAllocated) return false;

        return true;
    }

    public int hashCode()
    {
        int result = (int) (hitCount ^ (hitCount >>> 32));
        result = 31 * result + (int) (missCount ^ (missCount >>> 32));
        result = 31 * result + (int) (capacity ^ (capacity >>> 32));
        result = 31 * result + (int) (size ^ (size >>> 32));
//        result = 31 * result + (int) (rehashCount ^ (rehashCount >>> 32));
        result = 31 * result + (int) (putAddCount ^ (putAddCount >>> 32));
        result = 31 * result + (int) (putReplaceCount ^ (putReplaceCount >>> 32));
        result = 31 * result + (int) (putFailCount ^ (putFailCount >>> 32));
        result = 31 * result + (int) (removeCount ^ (removeCount >>> 32));
//        result = 31 * result + (int) (totalAllocated ^ (totalAllocated >>> 32));
        return result;
    }
}

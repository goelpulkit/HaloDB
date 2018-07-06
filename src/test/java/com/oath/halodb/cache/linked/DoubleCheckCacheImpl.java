/*
 * Copyright 2018, Oath Inc
 * Licensed under the terms of the Apache License 2.0. Please refer to accompanying LICENSE file for terms.
 */

// This code is a derivative work heavily modified from the OHC project. See NOTICE file for copyright and license.

package com.oath.halodb.cache.linked;

import com.google.common.primitives.Longs;
import com.oath.halodb.cache.OHCache;
import com.oath.halodb.cache.OHCacheStats;
import com.oath.halodb.cache.histo.EstimatedHistogram;

import org.testng.Assert;

import java.io.IOException;

/**
 * Test code that contains an instance of the production and check {@link OHCache}
 * implementations {@link OHCacheLinkedImpl} and
 * {@link CheckOHCacheImpl}.
 */
public class DoubleCheckCacheImpl<V> implements OHCache<V>
{
    public final OHCache<V> prod;
    public final OHCache<V> check;

    public DoubleCheckCacheImpl(OHCacheBuilder<V> builder)
    {
        this.prod = builder.build();
        this.check = new CheckOHCacheImpl<>(builder);
    }

    public boolean put(byte[] key, V value)
    {
        boolean rProd = prod.put(key, value);
        boolean rCheck = check.put(key, value);
        Assert.assertEquals(rProd, rCheck, "for key='" + key + '\'');
        return rProd;
    }

    public boolean addOrReplace(byte[] key, V old, V value)
    {
        boolean rProd = prod.addOrReplace(key, old, value);
        boolean rCheck = check.addOrReplace(key, old, value);
        Assert.assertEquals(rProd, rCheck, "for key='" + key + '\'');
        return rProd;
    }

    public boolean putIfAbsent(byte[] k, V v)
    {
        boolean rProd = prod.putIfAbsent(k, v);
        boolean rCheck = check.putIfAbsent(k, v);
        Assert.assertEquals(rProd, rCheck, "for key='" + k + '\'');
        return rProd;
    }

    public boolean putIfAbsent(byte[] key, V value, long expireAt)
    {
        throw new UnsupportedOperationException();
    }

    public boolean put(byte[] key, V value, long expireAt)
    {
        throw new UnsupportedOperationException();
    }

    public boolean remove(byte[] key)
    {
        boolean rProd = prod.remove(key);
        boolean rCheck = check.remove(key);
        Assert.assertEquals(rCheck, rProd, "for key='" + key + '\'');
        return rProd;
    }

    public void clear()
    {
        prod.clear();
        check.clear();
    }

    public V get(byte[] key)
    {
        V rProd = prod.get(key);
        V rCheck = check.get(key);
        Assert.assertEquals(rProd, rCheck, "for key='" + Longs.fromByteArray(key) + '\'');
        return rProd;
    }

    public boolean containsKey(byte[] key)
    {
        boolean rProd = prod.containsKey(key);
        boolean rCheck = check.containsKey(key);
        Assert.assertEquals(rProd, rCheck, "for key='" + key + '\'');
        return rProd;
    }

    public void resetStatistics()
    {
        prod.resetStatistics();
        check.resetStatistics();
    }

    public long size()
    {
        long rProd = prod.size();
        long rCheck = check.size();
        Assert.assertEquals(rProd, rCheck);
        return rProd;
    }

    public int[] hashTableSizes()
    {
        return prod.hashTableSizes();
    }

    @Override
    public SegmentStats[] perSegmentStats() {
        SegmentStats[] rProd = prod.perSegmentStats();
        SegmentStats[] rCheck = check.perSegmentStats();
        Assert.assertEquals(rProd, rCheck);
        return rProd;
    }

    public EstimatedHistogram getBucketHistogram()
    {
        return prod.getBucketHistogram();
    }

    public int segments()
    {
        int rProd = prod.segments();
        int rCheck = check.segments();
        Assert.assertEquals(rProd, rCheck);
        return rProd;
    }

    public long capacity()
    {
        long rProd = prod.capacity();
        long rCheck = check.capacity();
        Assert.assertEquals(rProd, rCheck);
        return rProd;
    }

    public long memUsed()
    {
        long rProd = prod.memUsed();
        long rCheck = check.memUsed();
        Assert.assertEquals(rProd, rCheck);
        return rProd;
    }

    public long freeCapacity()
    {
        long rProd = prod.freeCapacity();
        long rCheck = check.freeCapacity();
        Assert.assertEquals(rProd, rCheck, "capacity: " + capacity());
        return rProd;
    }

    public float loadFactor()
    {
        float rProd = prod.loadFactor();
        float rCheck = check.loadFactor();
        Assert.assertEquals(rProd, rCheck);
        return rProd;
    }

    public OHCacheStats stats()
    {
        OHCacheStats rProd = prod.stats();
        OHCacheStats rCheck = check.stats();
        Assert.assertEquals(rProd, rCheck);
        return rProd;
    }

    public void setCapacity(long capacity)
    {
        prod.setCapacity(capacity);
        check.setCapacity(capacity);
    }

    public void close() throws IOException
    {
        prod.close();
        check.close();
    }
}

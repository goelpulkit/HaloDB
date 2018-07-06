/*
 * Copyright 2018, Oath Inc
 * Licensed under the terms of the Apache License 2.0. Please refer to accompanying LICENSE file for terms.
 */

package com.oath.halodb;

import com.google.common.base.MoreObjects;
import com.oath.halodb.cache.linked.SegmentStats;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Arjun Mannaly
 */
public class HaloDBStats {

    private final long statsResetTime;

    private final long size;

    private final int numberOfFilesPendingCompaction;
    private final Map<Integer, Double> staleDataPercentPerFile;

    private final long rehashCount;
    private final long numberOfSegments;
    private final long maxSizePerSegment;
    private final SegmentStats[] segmentStats;

    private final long numberOfTombstonesFoundDuringOpen;
    private final long numberOfTombstonesCleanedUpDuringOpen;

    private final long numberOfRecordsCopied;
    private final long numberOfRecordsReplaced;
    private final long numberOfRecordsScanned;
    private final long sizeOfRecordsCopied;
    private final long sizeOfFilesDeleted;
    private final long sizeReclaimed;

    private final HaloDBOptions options;

    public HaloDBStats(long statsResetTime, long size, int numberOfFilesPendingCompaction,
                       Map<Integer, Double> staleDataPercentPerFile, long rehashCount, long numberOfSegments,
                       long maxSizePerSegment, SegmentStats[] segmentStats, long numberOfTombstonesFoundDuringOpen,
                       long numberOfTombstonesCleanedUpDuringOpen, long numberOfRecordsCopied,
                       long numberOfRecordsReplaced, long numberOfRecordsScanned, long sizeOfRecordsCopied,
                       long sizeOfFilesDeleted, long sizeReclaimed, HaloDBOptions options) {
        this.statsResetTime = statsResetTime;
        this.size = size;
        this.numberOfFilesPendingCompaction = numberOfFilesPendingCompaction;
        this.staleDataPercentPerFile = staleDataPercentPerFile;
        this.rehashCount = rehashCount;
        this.numberOfSegments = numberOfSegments;
        this.maxSizePerSegment = maxSizePerSegment;
        this.segmentStats = segmentStats;
        this.numberOfTombstonesFoundDuringOpen = numberOfTombstonesFoundDuringOpen;
        this.numberOfTombstonesCleanedUpDuringOpen = numberOfTombstonesCleanedUpDuringOpen;
        this.numberOfRecordsCopied = numberOfRecordsCopied;
        this.numberOfRecordsReplaced = numberOfRecordsReplaced;
        this.numberOfRecordsScanned = numberOfRecordsScanned;
        this.sizeOfRecordsCopied = sizeOfRecordsCopied;
        this.sizeOfFilesDeleted = sizeOfFilesDeleted;
        this.sizeReclaimed = sizeReclaimed;
        this.options = options;
    }

    public long getSize() {
        return size;
    }

    public int getNumberOfFilesPendingCompaction() {
        return numberOfFilesPendingCompaction;
    }

    public Map<Integer, Double> getStaleDataPercentPerFile() {
        return staleDataPercentPerFile;
    }

    public long getRehashCount() {
        return rehashCount;
    }

    public long getNumberOfSegments() {
        return numberOfSegments;
    }

    public long getMaxSizePerSegment() {
        return maxSizePerSegment;
    }

    public long getNumberOfRecordsCopied() {
        return numberOfRecordsCopied;
    }

    public long getNumberOfRecordsReplaced() {
        return numberOfRecordsReplaced;
    }

    public long getNumberOfRecordsScanned() {
        return numberOfRecordsScanned;
    }

    public long getSizeOfRecordsCopied() {
        return sizeOfRecordsCopied;
    }

    public long getSizeOfFilesDeleted() {
        return sizeOfFilesDeleted;
    }

    public long getSizeReclaimed() {
        return sizeReclaimed;
    }

    public HaloDBOptions getOptions() {
        return options;
    }

    public long getNumberOfTombstonesFoundDuringOpen() {
        return numberOfTombstonesFoundDuringOpen;
    }

    public long getNumberOfTombstonesCleanedUpDuringOpen() {
        return numberOfTombstonesCleanedUpDuringOpen;
    }

    public SegmentStats[] getSegmentStats() {
        return segmentStats;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("")
            .add("statsResetTime", statsResetTime)
            .add("size", size)
            .add("Options", options)
            .add("numberOfFilesPendingCompaction", numberOfFilesPendingCompaction)
            .add("numberOfRecordsCopied", numberOfRecordsCopied)
            .add("numberOfRecordsReplaced", numberOfRecordsReplaced)
            .add("numberOfRecordsScanned", numberOfRecordsScanned)
            .add("sizeOfRecordsCopied", sizeOfRecordsCopied)
            .add("sizeOfFilesDeleted", sizeOfFilesDeleted)
            .add("sizeReclaimed", sizeReclaimed)
            .add("rehashCount", rehashCount)
            .add("maxSizePerSegment", maxSizePerSegment)
            .add("numberOfTombstonesFoundDuringOpen", numberOfTombstonesFoundDuringOpen)
            .add("numberOfTombstonesCleanedUpDuringOpen", numberOfTombstonesCleanedUpDuringOpen)
            .add("segmentStats", Arrays.toString(segmentStats))
            .add("numberOfSegments", numberOfSegments)
            .add("staleDataPercentPerFile", staleDataMapToString())
            .toString();
    }

    private String staleDataMapToString() {
        StringBuilder builder = new StringBuilder("[");
        boolean isFirst = true;

        for (Map.Entry<Integer, Double> e : staleDataPercentPerFile.entrySet()) {
            if (!isFirst) {
                builder.append(", ");
            }
            isFirst = false;
            builder.append("{");
            builder.append(e.getKey());
            builder.append("=");
            builder.append(String.format("%.1f", e.getValue()));
            builder.append("}");
        }
        builder.append("]");
        return builder.toString();
    }

}

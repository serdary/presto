package com.facebook.presto.block.uncompressed;

import com.facebook.presto.Range;
import com.facebook.presto.block.TupleStream;
import com.facebook.presto.block.Cursor;
import com.facebook.presto.TupleInfo;
import com.facebook.presto.operator.GenericCursor;
import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.Iterator;

public class UncompressedTupleStream
        implements TupleStream, Iterable<UncompressedBlock>
{
    private final TupleInfo info;
    private final Iterable<UncompressedBlock> source;

    public UncompressedTupleStream(TupleInfo info, UncompressedBlock... source)
    {
        this(info, Arrays.asList(source));
    }

    public UncompressedTupleStream(TupleInfo info, Iterable<UncompressedBlock> source)
    {
        Preconditions.checkNotNull(info, "info is null");
        Preconditions.checkNotNull(source, "source is null");

        this.info = info;
        this.source = source;
    }

    @Override
    public Iterator<UncompressedBlock> iterator()
    {
        return source.iterator();
    }

    @Override
    public TupleInfo getTupleInfo()
    {
        return info;
    }

    @Override
    public Range getRange()
    {
        return Range.ALL;
    }

    @Override
    public Cursor cursor()
    {
        return new GenericCursor(info, source.iterator());
    }
}
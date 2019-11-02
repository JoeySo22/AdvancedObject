package org.josesoto;

import java.util.Comparator;

public class TrackSegmentComparator implements Comparator<TrackSegment> {

    @Override
    public int compare(TrackSegment o1, TrackSegment o2) throws
            NullPointerException{
        if (o1 == null | o2 == null)
            throw new NullPointerException("compare(o1 o2), args was Null");
        if (o1.place == o2.place)
            return 0;
        if (o1.place < o2.place)
            return -1;
        if (o1.place > o2.place)
            return 1;
        return 0;
    }
}

package org.josesoto;

import java.util.ArrayList;
import java.util.Collections;

public class CircularTrack extends AbstractTrack {

    public CircularTrack(){
        this.segments = new ArrayList<TrackSegment>();
    }

    public void addSegment(TrackSegment trackSegment) {
        // variable segments is null
        segments.add(trackSegment);
        TrackSegmentComparator comparator = new TrackSegmentComparator();
        Collections.sort(segments, comparator);
        this.trackLength += trackSegment.getLength();
    }

    @Override
    public double getTrackLength() {
        return this.trackLength;
    }

}

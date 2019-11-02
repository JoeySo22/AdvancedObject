package org.josesoto;

public class Main {

    public static void main(String[] args) {
        // We run these in this order
        //**view will make the model**CircularTrackModel model = CircularTrackModel.getInstance("circular_lane_test.xml");
        CircularTrackView view = CircularTrackView.getInstance();
        //CircularTrackControl control = CircularTrackControl.getInstance(model, view);
    }
}

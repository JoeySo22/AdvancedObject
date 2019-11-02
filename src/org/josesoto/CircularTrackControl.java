package org.josesoto;

import java.io.IOException;

public class CircularTrackControl {
    private static CircularTrackControl instance;

    private CircularTrackModel model;
    private CircularTrackView view;

    private CircularTrackControl(CircularTrackModel model, CircularTrackView view){
        this.model = model;
        this.view = view;
    }

    public static CircularTrackControl getInstance(CircularTrackModel model, CircularTrackView view){
        if (instance == null)
            instance = new CircularTrackControl(model, view);
        return instance;
    }


    /*
        Our users will have a play, pause, and reset option.
        Play and pause use the calling part of the Model which is an update function with time. Model tracks
        the time and other values in each driver.
     */

    private void update(double timeInHours) throws IOException {
        model.update(timeInHours);
        view.update();
    }
}

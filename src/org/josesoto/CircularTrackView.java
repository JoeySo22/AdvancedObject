package org.josesoto;

import com.sun.javaws.util.JfxHelper;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class CircularTrackView extends JPanel{
    private static CircularTrackView instance;

    private CircularTrackModel model;
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu menu;
    private final JFileChooser fileChooser;
    private JMenuItem menuItemOpen;
    private JMenuItem menuItemExit;
    private final int HEIGHT = 800;
    private final int WIDTH = 800;

    private CircularTrackView(){
        frame = new JFrame("Circular Track");
        menu = new JMenu("File");
        menuBar = new JMenuBar();
        fileChooser = new JFileChooser();
        menuItemOpen = new JMenuItem("Open");
        menuItemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start file explorer and select only xml file.
                // make a notice if the model instantiation fails
                // set it to model variable
                FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "xml");
                fileChooser.setFileFilter(filter);
                fileChooser.showOpenDialog(frame);
                try
                {
                    model = CircularTrackModel.getInstance(fileChooser.getSelectedFile().getAbsolutePath());
                    System.out.println("Successful");

                } catch (Exception exception) {
                    new JOptionPane().showMessageDialog(frame, fileChooser.getSelectedFile().getName()
                            + " failed to load");
                }
            }
        });
        menuItemExit = new JMenuItem("Exit");
        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(menuItemOpen);
        menu.add(menuItemExit);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        frame.add(this);
    }

    public void paint(Graphics g){
        // for the circle the width/height are the radius
        // dimensions for the frame is 48 pixes wider (on x)
        g.drawOval(224, 200,350,350);
        g.drawLine(0,0,848,799);

        /* It seems that will have to draw small rectangles around the circle to simulate the cars.
        *  The formulas are:
        *       S = theta * r
        *       x = r * cos(theta)
        *       y = r * sin(theta)
        * */
    }



    public static CircularTrackView getInstance(){
        if (instance == null)
            instance = new CircularTrackView();
        return instance;
    }
    @SuppressWarnings("unchecked")
    void update() throws IOException {
        //do nothing for now. View should call on Model and query the SERIALIZED FORM of the List of drivers.
        // It should be obvious that List isn't serializable so we have to get an ArrayList
        // Lets try to get this from our model. The model should create a deep clone List.

        byte[] data = model.getSerializedDriverListUpdateQuery();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
        try {
            ArrayList<Driver> drivers = (ArrayList) inputStream.readObject();
        } catch (ClassNotFoundException e){
            // THIS SHOULD ALSO DO SOMETHING AND SHOW TO THE VIEWER. IT SHOULD STOP A EVERYTHING.
            e.printStackTrace();
        }

        // after getting the data, this method needs to update the GUI with it.
    }

    // Need to start finding ways to use the Swing and AWT.

}

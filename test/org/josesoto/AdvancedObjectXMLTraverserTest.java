package org.josesoto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdvancedObjectXMLTraverserTest {

    @Test
    void testReadabilityOfTraverser(){
        AdvancedObjectXMLTraverser traverser = null;
        try {
            traverser = new AdvancedObjectXMLTraverser("circular_lane_test.xml");
        } catch (Exception e){
            e.printStackTrace();
        }
        assertEquals("circular_lane_test.xml", traverser.getFileName());
        assertEquals("MODEL", traverser.getRootElementTagName());
    }
}
package org.josesoto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircularLinkedListIteratorTest {

    @Test
    void next() {
        CircularLinkedListIterator<String> iterator = new CircularLinkedListIterator<String>();
        iterator.add("Root");
        assertEquals("Root", iterator.next());
    }

    @Test
    void previous(){
        CircularLinkedListIterator<String> iterator = new CircularLinkedListIterator<String>();
        iterator.add("Root");
        iterator.add("Tail");
        assertEquals("Tail", iterator.previous());
    }
}
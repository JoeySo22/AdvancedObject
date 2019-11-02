package org.josesoto;

public abstract class AbstractCircularList<E>{
    protected int position;
    protected int size;
    protected Node<E> root;
    protected Node<E> currentNode;

    public int getPosition() { return this.position; }
    public int getSize() { return this.size; }
    public abstract E get(int index);
    public abstract boolean isEmpty();

    class Node<E>{
        E element;
        Node<E> next = null;
        Node<E> prev = null;

        protected Node(E element){
            this.element = element;
        }
        @Override
        public String toString(){
            String nextString = (next == null) ? "null" : next.element.toString();
            String prevString = (prev == null) ? "null" : prev.element.toString();
            return ("Element=[" + element.toString() + "] ,next=[" + nextString + "] ,prev=[" + prevString + "]");
        }
        @Override
        public boolean equals(Object otherElement){
            E element = (E) otherElement;
            return element.equals(otherElement);
        }
        @Override
        public Node<E> clone() throws CloneNotSupportedException{
            throw new CloneNotSupportedException("Node clone not supported.");
        }
    }

}

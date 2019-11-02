package org.josesoto;
import java.util.ListIterator;

public class CircularLinkedListIterator<E> extends AbstractCircularList<E> implements ListIterator<E> {
    private Node<E> root;
    private Node<E> currentNode;

    public CircularLinkedListIterator(){
        this.root = null;
        this.currentNode = this.root;
    }

    @Override
    public E previous(){
        int newPosition;
        if (position == 0)
            newPosition = this.getSize() - 1;
        else
            newPosition = position - 1;
        position = newPosition;
        return get(newPosition);
    }

    @Override
    public void add(E element){
        if (root == null){
            root = new Node<E>(element);
            root.next = root;
            root.prev = root;
            this.size++;
            this.currentNode = this.root;
        }
        else {
            Node<E> node = root;
            int number = 0;
            while (number < (this.size - 1)){
                node = node.next;
                number++;
            }
            Node<E> newNode = new Node<E>(element);
            node.next = newNode;
            root.prev = newNode;
            newNode.next = root;
            newNode.prev = node;
            this.size++;
        }
    }
    @Override
    public boolean hasNext() {
        if (this.getPosition() + 1 == this.getSize())
            return false;
        return true;
    }

    @Override
    public E next(){
        int newPosition = (position + 1) % this.getSize();
        position = newPosition;
        this.currentNode = this.currentNode.next;
        return this.currentNode.element;
    }

    public E getCurrentElement(){
        return this.currentNode.element;
    }

    @Override
    public boolean hasPrevious() {
        if ((this.getPosition() - 1) < 0)
            return false;
        else return true;
    }
    @Override
    public int nextIndex() {
        return (this.getPosition() + 1) % this.getSize();
    }

    @Override
    public int previousIndex() {
        return (this.getPosition() - 1) % this.getSize();
    }

    @Override
    public void remove() {
        Node<E> previousNode = this.currentNode.prev;
        Node<E> nextNode = this.currentNode.next;
        previousNode.next = nextNode;
        nextNode.prev = previousNode;
        this.currentNode = previousNode;
    }

    @Override
    public void set(E e) {
        this.currentNode.element = e;
    }

    @Override
    public E get(int index){
        int newIndex = index % this.size;
        this.currentNode = this.root;
        for (int i = 0; i < newIndex; i++)
            this.currentNode = this.currentNode.next;
        return this.currentNode.element;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }
}

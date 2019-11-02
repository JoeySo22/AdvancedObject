package org.josesoto;
import org.omg.CORBA.Object;

import java.util.*;

public class CircularList<E> extends AbstractCircularList<E> implements List<E>{

    public CircularList(){
        this.position = 0;
        this.size = 0;
    }

    public int size(){
        return size;
    }

    @Override
    public E set(int index, E element) throws IndexOutOfBoundsException, NullPointerException{
        if (index > this.size() | index < 0)
            throw new IndexOutOfBoundsException("Index greater than size");
        if (element == null)
            throw new NullPointerException("set(index, null) null exception");
        Node<E> n = this.root;
        int innerSize = this.size();
        while (innerSize >= 0){
            if (innerSize == index){
                Node<E> previousNode = n.prev;
                Node<E> nextNode = n.next;
                Node<E> newNode = new Node<E>(element);
                previousNode.next = newNode;
                nextNode.prev = newNode;
                return n.element;
            }
            innerSize--;
        }
        return null;
    }

    @Override
    public void add(int index, E element)  throws IndexOutOfBoundsException, NullPointerException{
        if (index > this.size() | index < 0)
            throw new IndexOutOfBoundsException("Index greater than size");
        if (element == null)
            throw new NullPointerException("set(index, null) null exception");
        Node<E> n = this.root;
        int innerSize = this.size();
        while (innerSize >= 0){
            if (innerSize == index){
                Node<E> previousNode = n.prev;
                Node<E> nextNode = n.next;
                Node<E> newNode = new Node<E>(element);
                previousNode.next = newNode;
                nextNode.prev = newNode;
            }
            innerSize++;
        }
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException{
        if (index > this.size() | index < 0)
            throw new IndexOutOfBoundsException("Index greater than size");
        Node<E> n = this.root;
        int innerSize = this.size();
        while (innerSize >= 0){
            if (innerSize == index){
                Node<E> previousNode = n.prev;
                Node<E> nextNode = n.next;
                previousNode.next = nextNode;
                nextNode.prev = previousNode;
                return n.element;
            }
            innerSize--;
        }
        return null;
    }

    @Override
    public int indexOf(java.lang.Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(java.lang.Object o) {
        return 0;
    }





    @Override
    public ListIterator<E> listIterator() throws IndexOutOfBoundsException{
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) throws IndexOutOfBoundsException{
        if (index < 0)
            throw new IndexOutOfBoundsException("Index=" + index + " out of Bounds");
        CircularLinkedListIterator<E> iterator = new CircularLinkedListIterator<>();
        for (; index < this.size(); index++){
            iterator.add(this.get(index));
        }
        return iterator;
    }

    @Override
    public CircularList<E> clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException();
        /*
        super.close();
        CircularList<E> newCircularLinkedList = new CircularList<>();
        newCircularLinkedList.position = this.position;
        newCircularLinkedList.size = this.size;
        for (int i = 0; i < newCircularLinkedList.size(); i++)
            newCircularLinkedList.add(this.get(i));
        return newCircularLinkedList;*/
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        CircularList<E> newList = new CircularList<E>();
        for (int i = 0; i <= toIndex; i++)
            newList.add(this.get(i));
        return newList;
    }

    public int getPosition(){
        return this.position;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public boolean add(E element){
        if (root == null){
            root = new Node<E>(element);
            root.next = root;
            root.prev = root;
            size++;
            return true;
        }
        else {
            Node<E> node = root;
            while (node.next != root){
                node = node.next;
            }
            Node<E> newNode = new Node<E>(element);
            node.next = newNode;
            root.prev = newNode;
            newNode.next = root;
            newNode.prev = node;
            size++;
            return true;
        }
    }

    @Override
    public boolean remove(java.lang.Object o) {
        return false;
    }

    public boolean isEmpty(){
        return root == null;
    }

    @Override
    public boolean contains(java.lang.Object o) {
        return false;
    }



    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    public E[] toArray() {
        @SuppressWarnings("unchecked")
        E[] elementArray = (E[]) (new Object[this.size()]);
        for (int i = 0; i < elementArray.length; i++)
            elementArray[i] = this.get(i);
        return elementArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }



    @Override
    public boolean addAll(Collection c) {
        @SuppressWarnings("unchecked")
        Iterator<E> iterator = c.iterator();
        while (iterator.hasNext())
            this.add(iterator.next());
        return true;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    @Override
    public void clear() {
        this.position = 0;
        this.size = 0;
        this.root = null;
    }

    @Override
    public boolean retainAll(Collection c) throws NullPointerException{
        if (c == null)
            throw new NullPointerException("Collection is Null");
        for (E element: this) {
            if (!c.contains(element))
                this.remove(element);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection c) throws NullPointerException{
        if (c == null)
            throw new NullPointerException("Collection is Null");
        @SuppressWarnings("unchecked")
        Iterator<E> iterator = c.iterator();
        boolean removedAll = true;
        while (iterator.hasNext()){
            E element = iterator.next();
            int foundPosition = this.indexOf(element);
            if (foundPosition == -1) {
                removedAll = false;
                continue;
            }
            else {
                this.remove(foundPosition);
            }
        }
        return removedAll;
    }

    @Override
    public boolean containsAll(Collection c) throws NullPointerException{
        if (c == null)
            throw new NullPointerException("Collection is Null");
        @SuppressWarnings("unchecked")
        Iterator<E> iterator = c.iterator();
        while (iterator.hasNext()){
            E element = iterator.next();
            if (!this.contains(element))
                return false;
        }
        return true;
    }
}

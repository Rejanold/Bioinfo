import java.util.*;
import java.lang.*;
/**
 * Single Linked List Class for BioInfo Homework
 * CS245
 * Professor: Dr. Amthauer
 * Authors Robert Hable, Blake Furlano and Mason Waters
 */


public class SLList<E> implements List<E>{
    private Node<E> head;//reference to the head of the list
    private int size = 0; //How big is our list

    /**
     * Constructors
     *
     * @param item - the item we are adding to the head if the list
     */
    public SLList(E item) {
        head = new Node<E>(item);
        size++;
    }

    /**
     * Default constructor.
     */
    public SLList() {
        head = null;
        size = 0;
    }

    /**
     * clear the list
     */
    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public void insert(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == 0) {//want a new head of list
            addFirst(item); //Helper method that needs to be written
        } else {
            Node<E> node = getNode(index - 1);//the node before the index
            addAfter(node, item); //helper methods need to be written
        }
    }

    /**
     * Helper method that makes a new head
     *
     * @param item to insert into the new head.
     */
    private void addFirst(E item) {
        head = new Node<E>(item, head);
        size++;
    }

    /**
     * Helper method to add a node after a given node
     *
     * @param item to add into node
     * @param node to add after
     */
    private void addAfter(Node<E> node, E item) {
        node.setNext(new Node<E>(item, node.getNext()));
        size++;

    }

    /**
     * Helper method to get a node at a given index
     *
     * @param index - the index of the node to retrieve
     */
    private Node<E> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node;
    }

    /**
     * adding nodes to the list
     * @param item The element to be appended.
     */
    @Override
    public void add(E item) {
        if (size == 0) { //Nothing in the list so new head was made
            addFirst(item);
        } else {
            Node<E> node = getNode(size - 1);
            addAfter(node, item); //Adds the node to the end
        }

    }

    @Override
    /**
     * remove the element at the given location.
     * @param index - index of node to remove
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == 0) { //if your removing the head
            removeFirst();
        } else {
            Node<E> node = getNode(index - 1);
            removeAfter(node);
        }
    }

    /**
     * Helper to remove the head
     */
    private void removeFirst() {
        if (head != null) {
            head = head.getNext();
            size--;
        }
    }

    /**
     * Helper to remove after a given node.
     *
     * @param node - the node to remove after
     */
    private void removeAfter(Node<E> node) {
        Node<E> temp = node.getNext();
        if (temp != null) {
            node.setNext(temp.getNext());
            size--;
        }
    }

    /**
     * Get the element in the position to one step left
     *
     * @return element in the node to the left of the node at the index,
     * null if at the head.
     */
    @Override
    public E prev(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == 0) {
            return null;
        } else {
            return getNode(index - 1).getElement();
        }
    }

    /**
     * get the element in the position one step right.
     *
     * @return the element in the node to the right of,
     * the node at tht index, null if at the end.
     */
    @Override
    public E next(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == size - 1) {
            return null;
        } else {
            return getNode(index + 1).getElement();
        }
    }

    /**
     * @return The number of elements in the list
     */
    @Override
    public int length() {
        return size;
    }

    /**
     * Turn the contents of the Nodes to a string in order from head to end.
     *
     * @return The String representation of the,
     * elements in the list from head to end.
     */
    public String toString() {
        Node<E> nodeRef = head;
        String result = "";
        while (nodeRef != null) {
            result = result + nodeRef.getElement().toString();

            if (nodeRef.getNext() != null) {
                result = result + " ==> ";
            }
            nodeRef = nodeRef.getNext();
        }
        return result;
    }

    /**
     * needed a new toString method for overlap
     * @return a boring string of the list
     */
    public String toStringNoArrow(){
        Node<E> nodeRef = head;
        String result = "";
        while (nodeRef != null){
            result = result + nodeRef.getElement().toString();
            nodeRef = nodeRef.getNext();
        }
        return result;
    }

    /**
     * Revers the content of the list.
     * if list is A => B => C it becoms C => B => A
     */
    @Override
    public void reverse() {
        Node<E> node = head;
        if (node == null || node.getNext() == null) { // empty or one node
            return;
        }
        Node<E> prev = node.getNext();
        Node<E> curr = prev.getNext();
        prev.setNext(node);
        node.setNext(null);
        while (curr != null) {
            Node<E> next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }
        head = prev;

    }

    /**
     * @return the element at a given position
     */
    @Override
    public E getValue(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node.getElement();
    }

    /**
     * needed to write this for transcribe to modify elements in our list
     * during convertion
     * @param index what pos
     * @param item what item we intend to replace
     */
    public void setElement(int index, E item){

        Node<E> nodeRef = getNode(index);
        addAfter(nodeRef,item);
        remove(index);


    }
    /**
     * insert a list after given index
     * @param list  the list to be inserted
     * @param index the index of where the list should go after
     */
    public void insertList(SLList list, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> n = getNode(index);
        Node<E> next = n.getNext();
        if (list.getHead() != null) {
            Node<E> head = list.getHead();
            Node<E> last = list.getLast();
            n.setNext(head);
            last.setNext(next);
        }
        size = list.length() + size;
    }

    /**
     * Clip position method to remove nodes located at a given start and end or just start position
     * There is Error checking in main because we spent forever writing it, however error checking is
     * included here for SLList re-use.
     * @param start the position given to start clipping
     * @param end the position given or not given to end the clipping.
     */
    public void clipPos(int start, int end){
        if (start < 0 || start > size-1) {
            throw new IndexOutOfBoundsException(Integer.toString(start));
        }
        if (end < 0 || end > size-1) {
            throw new IndexOutOfBoundsException(Integer.toString(end));
        }
        else {
            Node<E> startNode = this.getNode(start);
            Node<E> endNode = this.getNode(end);
            head = startNode;
            endNode.setNext(null);
            size = end - start + 1;
        }
    }

    /**
     * wrote this never used it
     * @param n length
     */
    public void setLength(int n){
        size = n;
    }

    /**
     * Just alot of pointing nodes around from list to list
     * @param list the second list used
     * @param start1 where we started the swap in list 1
     * @param start2 where we started the swap in list 2
     */
    public void swap(SLList<E> list, int start1, int start2) {
        if (start1 < 0 || start1 > size) {
            throw new IndexOutOfBoundsException(Integer.toString(start1));
        }
        if (start2 < 0 || start2 > size) {
            throw new IndexOutOfBoundsException(Integer.toString(start2));
        }

        else {
            int sizethis = size;
            int sizeSwap = list.length();
            if (start1 == size) {
                Node<E> start1Node = this.getNode(start1 - 1);
                Node<E> start2Node = list.getNode(start2);
                start1Node.setNext(start2Node);

                Node<E> start2Prev = list.getNode(start2 - 1);
                start2Prev.setNext(null);
            } else if (start2 == list.length()) {
                Node<E> start2Node = list.getNode(start2 - 1);
                Node<E> start1Node = this.getNode(start1);

                start2Node.setNext(start1Node);
                Node<E> start1Prev = this.getNode(start1 - 1);
                start1Prev.setNext(null);
            } else {
                Node<E> start1Node = this.getNode(start1);
                Node<E> start2Node = list.getNode(start2);
                Node<E> start1Prev = this.getNode(start1 - 1);
                Node<E> start2Prev = list.getNode(start2 - 1);
                start1Prev.setNext(start2Node);
                start2Prev.setNext(start1Node);
            }
            size = start1 + (sizethis - start2) -1 ;
            list.setLength(start2 +(sizeSwap-start1)+1);
        }
    }



    /**
     * @return the head of the list
     */
    public Node<E> getHead() {
        return head;
    }

    /**
     * @return the last node in the list
     */
    public Node<E> getLast() {
        return getNode(size - 1);

    }




}

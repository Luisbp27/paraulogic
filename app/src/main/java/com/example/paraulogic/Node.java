package com.example.paraulogic;

public class Node<E> {

    private E element;
    private Node left, rigth;

    public Node() {
        this.left = null;
        this.rigth = null;
    }

    public E getElement() {
        return element;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRigth() {
        return rigth;
    }
}

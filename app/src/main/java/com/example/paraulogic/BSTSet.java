package com.example.paraulogic;

public class BSTSet<E extends Comparable> {

    private Node root;

    public BSTSet() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(E element) {
        return contains(element, root);
    }

    private boolean contains(E element, Node current) {
        if (root != null) {
            if (current.getElement().equals(element)) {
                return true;
            }

            if (element.compareTo(current.getElement()) < 0) {
                // Buscamos el hijo izquierdo
                return contains(element, current.getLeft());
            } else {
                // Buscamos el hijo derecho
                return contains(element, current.getRigth());
            }
        } else {
            return false;
        }
    }

    private void add(String str) {

    }

    private Node remove(E element, Node current) {
        if (current == null) {
            return null;
        }

        if (current.getElement().equals(element)) {
            // Eliminamos el nodo

        }

        if (element.compareTo(current.getElement()) < 0) {
            // Subarbol izquierdo
            // current.getLeft() = remove(element, current.getLeft(), cerca);
        } else {
            // Subarbol derecho
            // current.getRigth() = remove(element, current.getRigth(), cerca);
        }

        return current;
    }
}

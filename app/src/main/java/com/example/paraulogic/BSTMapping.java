package com.example.paraulogic;

import java.util.Iterator;
import java.util.Stack;

public class BSTMapping<K extends Comparable, V> {

    private Node root;

    /**
     * Método constructor de la clase
     */
    public BSTMapping() {
        this.root = null;
    }

    /**
     * Método que devuelve un iterador del mapping
     * @return
     */
    public Iterator IteratorBSTMappging() {
        return new IteratorBSTMapping();
    }

    /**
     * Método que añade un elemento al mapping
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value){
        // Obtenemos el valor anterior
        V valor = get(key);
        // Modificamos
        this.root = put(key, value, root);

        return valor;
    }

    /**
     * Método que añade un elemento al mapping
     *
     * @param key
     * @param value
     * @param current
     * @return
     */
    private Node put(K key, V value, Node current) {
        if (current == null) {
            return new Node(key, value, null, null);
        } else {
            if (current.key.equals(key)) {
                current.value = value;
                return current;
            }

            if (key.compareTo(current.key) < 0) {
                current.left = put(key, value, current.left);
            } else {
                current.right = put(key, value, current.right);
            }

            return current;
        }
    }

    /**
     * Método que devuelve el conjunto asociado a la key pasada por parámetro
     *
     * @param key
     * @return
     */
    public V get(K key) {
        return get(key, root);
    }

    /**
     *
     * @param key
     * @param current
     * @return
     */
    private V get(K key, Node current) {
        if (current == null) {
            return null;
        } else {
            if (current.key.equals(key)) {
                return current.value;
            }
            if (key.compareTo(current.key) < 0) {
                return get(key, current.left);
            } else {
                return get(key, current.right);
            }
        }
    }

    /**
     * Método que elimina el elemento asociado a la key pasada por parámetro
     *
     * @param key
     * @return
     */
    public V remove(K key) {
        this.root = remove(key, root);
        return root.value;
    }

    /**
     *
     * @param key
     * @param current
     * @return
     */
    private Node remove(K key, Node current) {
        // Si no encontramos el elemento
        if (current == null) {
            return null;
        }

        if (current.key.equals(key)) {
            // Eliminamos el nodo
            if (current.right == null && current.left == null) {
                return null;
            } else {
                // Si tiene 1 hijo
                if (!(current.left == null && current.right == null)) {
                    if (current.left == null) {
                        return current.right;
                    } else {
                        return current.left;
                    }
                    // Si tiene 2 hijos
                } else {
                    // Nodo más a la izquierda del hijo derecho
                    Node plowest = current.right;
                    Node parent = current;

                    while (plowest.left != null) {
                        parent = plowest;
                        plowest = plowest.left;
                    }

                    plowest.left = current.left;
                    if (plowest != current.right) {
                        parent.left = plowest.right;
                        plowest.right = current.right;
                    }

                    return plowest;
                }
            }
        }

        // Subarbol izquierdo
        if (key.compareTo(current.key) < 0) {
            current.left = remove(key, current.left);
            // Subarbol derecho
        } else {
            current.right = remove(key, current.right);
        }

        return current;
    }

    /**
     * Método que verifica si hay o no elementos
     *
     * @return
     */
    public boolean isEmpty() {
        return root == null;
    }

    // CLASES PRIVADAS //

    /**
     * Clase Nodo
     */
    private class Node {

        public Node(K key, V value, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        private K key;
        private V value;
        private Node left, right;
    }

    protected class Pair {

        private K key;
        private V value;

        private Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

    }

    /**
     *
     */
    private class IteratorBSTMapping implements Iterator {

        private Stack<Node> iterator;

        public IteratorBSTMapping() {
            Node p;
            this.iterator = new Stack();

            if (root != null) {
                p = root;

                while (p.left != null) {
                    iterator.push(p);
                    p = p.left;
                }

                iterator.push(p);
            }
        }

        /**
         * Método que verifica si quedan más nodos por visitar
         * @return
         */
        @Override
        public boolean hasNext() {
            return !iterator.isEmpty();
        }

        /**
         * Método que devuelve el siguiente nodo a visitar
         *
         * @return
         */
        @Override
        public Object next() {
            Node p = iterator.pop();
            Pair pair = new Pair(p.key, p.value);
            if (p.right != null) {
                p = p.right;
                while (p.left != null) {
                    iterator.push(p);
                    p = p.left;
                }
                iterator.push(p);
            }
            return pair;
        }
    }
}

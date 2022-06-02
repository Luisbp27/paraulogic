package com.example.paraulogic;

import java.util.Iterator;
import java.util.Stack;

public class BSTMapping<K extends Comparable, V> {

    private Node root;

    /**
     * Mètode constructor de la classe
     *
     */
    public BSTMapping() {
        this.root = null;
    }

    /**
     * Mètode que retorna un iterador del mapping
     *
     * @return
     */
    public Iterator IteratorBSTMappging() {
        return new IteratorBSTMapping();
    }

    /**
     * Mètode que afegeix un element al mapping
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value){
        // Obtenim el valor anterior
        V valor = get(key);
        // El modificam
        this.root = put(key, value, root);

        return valor;
    }

    /**
     * Mètode que afegeix un element al mapping
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
     * Mètode que retorna el conjunt associat a la key pasada per paràmetre
     *
     * @param key
     * @return
     */
    public V get(K key) {
        return get(key, root);
    }

    /**
     * Mètode que retorna el conjunt associat a la key i el node passats per paràmetre
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
     * Mètode que elimina l'element associat a la key passada per paràmetre
     *
     * @param key
     * @return
     */
    public V remove(K key) {
        this.root = remove(key, root);
        return root.value;
    }

    /**
     * Mètode que elimina l'element associat a la key i el node passats per paràmetre
     *
     * @param key
     * @param current
     * @return
     */
    private Node remove(K key, Node current) {
        // Si no hi ha element
        if (current == null) {
            return null;
        }

        if (current.key.equals(key)) {
            // Eliminam el node
            if (current.right == null && current.left == null) {
                return null;
            } else {
                // Si té 1 fill
                if (!(current.left == null && current.right == null)) {
                    if (current.left == null) {
                        return current.right;
                    } else {
                        return current.left;
                    }
                // Si té 2 fills
                } else {
                    // Node més a l'esquerre del fill dret
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

        // Subarbre esquerre
        if (key.compareTo(current.key) < 0) {
            current.left = remove(key, current.left);
        // Subarbre dret
        } else {
            current.right = remove(key, current.right);
        }

        return current;
    }

    /**
     * Classe Node
     *
     */
    private class Node {

        private K key;
        private V value;
        private Node left, right;

        /**
         * Mètode constructor de la classe
         *
         * @param key
         * @param value
         * @param left
         * @param right
         */
        public Node(K key, V value, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Classe Pair
     *
     */
    protected class Pair {

        private K key;
        private V value;

        /**
         * Mètode constructor de la classe
         *
         * @param key
         * @param value
         */
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
     * Classe Iterator per al BSTMapping
     *
     */
    private class IteratorBSTMapping implements Iterator {

        private Stack<Node> iterator;

        /**
         * Mètode constructor de la classe
         *
         */
        public IteratorBSTMapping() {
            Node node;
            this.iterator = new Stack();

            if (root != null) {
                node = root;

                while (node.left != null) {
                    iterator.push(node);
                    node = node.left;
                }

                iterator.push(node);
            }
        }

        /**
         * Mètode que verifica si queden més nodes per visitar
         *
         * @return
         */
        @Override
        public boolean hasNext() {
            return !iterator.isEmpty();
        }

        /**
         * Mètode que retorna el següent node a visitar
         *
         * @return
         */
        @Override
        public Object next() {
            Node node = iterator.pop();
            Pair pair = new Pair(node.key, node.value);

            if (node.right != null) {
                node = node.right;
                while (node.left != null) {
                    iterator.push(node);
                    node = node.left;
                }
                iterator.push(node);
            }
            return pair;
        }
    }
}

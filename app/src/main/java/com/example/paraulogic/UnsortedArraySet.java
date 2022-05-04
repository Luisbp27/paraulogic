package com.example.paraulogic;

import java.util.Iterator;

public class UnsortedArraySet<E> {

    private E[] array;
    private int n;

    /**
     * Método constructor de la clase
     *
     * @param max
     */
    public UnsortedArraySet(int max) {
        this.array = (E[]) new Object[max];
        this.n = 0;
    }

    /**
     * Método que nos permite saber si un elemento, pasado por parámetro, esta contenido en el array
     *
     * @param elem
     * @return
     */
    public boolean contains(E elem) {
        for (int i = 0; i < n; i++) {
            if (array[i].equals(elem)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Método que añade un elemento, pasado por parámetro, al array
     * @param elem
     * @return
     */
    public boolean add(E elem) {
        if ((n < array.length) && !(contains(elem))) {
            array[n] = elem;
            n++;

            return true;
        } else {
            return false;
        }
    }

    /**
     * Método constructor de la clase Iterator
     *
     * @return
     */
    public Iterator iterator(){
        Iterator iterator = new IteratorUnsortedArraySet();
        return iterator;
    }

    /**
     * Clase Iterator para el arraySet creado
     *
     */
    private class IteratorUnsortedArraySet implements Iterator{
        private int idxIterator;

        /**
         * Método constructor de la clase
         *
         */
        private IteratorUnsortedArraySet() {
            idxIterator = 0;
        }

        /**
         * Método que comprueba si existe un valor en la siguiente posición de la lista
         *
         * @return
         */
        @Override
        public boolean hasNext() {
            return idxIterator < n;
        }

        /**
         * Método que devuelve el siguiente elemento de la lista
         *
         * @return
         */
        @Override
        public Object next() {
            idxIterator++;
            return array[idxIterator - 1];
        }

    }
}

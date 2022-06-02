package com.example.paraulogic;

import java.util.Iterator;

public class UnsortedArraySet<E> {

    private E[] array;
    private int n;

    /**
     * Mètode constructor de la classe
     *
     * @param max
     */
    public UnsortedArraySet(int max) {
        this.array = (E[]) new Object[max];
        this.n = 0;
    }

    /**
     * Mètode que permet verificar is un element, pasat per paràmetre, està o no contingut dins l'array
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
     * Mètode que afegeix un element, pasat per paràmetre, a l'array
     *
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
     * Mètode constructor de la classe Iterator
     *
     * @return
     */
    public Iterator iterator(){
        return new IteratorUnsortedArraySet();
    }

    /**
     * Mètode que implementa la classe Iterator per a l'array set creat
     *
     */
    private class IteratorUnsortedArraySet implements Iterator{
        private int idxIterator;

        /**
         * Mètode constructor de la classe
         *
         */
        private IteratorUnsortedArraySet() {
            idxIterator = 0;
        }

        /**
         * Mètode que verifica si existeix un valor a la següent posició de la llista
         *
         * @return
         */
        @Override
        public boolean hasNext() {
            return idxIterator < n;
        }

        /**
         * Mètode que retorna el següent element de la llista
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

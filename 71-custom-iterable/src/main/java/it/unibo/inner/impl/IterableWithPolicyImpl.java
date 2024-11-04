package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{
    
    private final List<T> elementsToFilter;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(T[] elements, Predicate<T> filter) {
        Objects.requireNonNull(elements);
        this.elementsToFilter = List.of(elements);
        this.filter = filter;
        // for (T elem : Objects.requireNonNull(elements)) {
        //     elementsToFilter.add(elem);
        // };
    }

    public IterableWithPolicyImpl(T[] elements){
        this(elements, new Predicate<T>() {
            
            @Override
            public boolean test(T elem) {
                return true;   
            }
        }
        );
    }

    private class InnerIterator implements Iterator<T>{

        private int index = 0;

        @Override
        public boolean hasNext() {
            while (elementsToFilter.size() > index) {
                if (filter.test(elementsToFilter.get(index))) {
                    return true;
                }
                this.index++;
            }
            return false;
        }

        @Override
        public T next() {
            if (hasNext()) {
                return elementsToFilter.get(this.index++);
            }

            throw new NoSuchElementException();
        }
   }     

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }

    @Override
    public InnerIterator iterator() {
        return new InnerIterator();
    }
}

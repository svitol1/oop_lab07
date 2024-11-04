package it.unibo.inner.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{
    
    private List<T> elementsToFilter = new ArrayList<>();
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

        private int index = -1;

        @Override
        public boolean hasNext() {
            return elementsToFilter.size() - 1 > this.index;
        }

        @Override
        public T next() {

            while (hasNext()) {
                this.index++;

                if( !filter.test(elementsToFilter.get(index)) ){
                    return elementsToFilter.get(index);
                }
            }

            throw new IndexOutOfBoundsException("Finished filtering the list");
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

package model.adt;


import model.exceptions.InterpreterException;
import model.values.IValue;

import java.util.Map;

public class Heap implements IHeap{
    private final IDict<Integer, IValue> innerHeap;

    public Heap(IDict<Integer, IValue> innerHeap) {
        this.innerHeap = innerHeap;
    }

    public Heap(){
        this.innerHeap = new Dict<>();
    }

    @Override
    public void add(Integer key, IValue value) {
        this.innerHeap.add(key, value);
    }

    @Override
    public void update(Integer key, IValue value) throws InterpreterException {
        this.innerHeap.update(key, value);
    }

    @Override
    public IValue search(Integer key) throws InterpreterException {
        return this.innerHeap.search(key);
    }

    @Override
    public void remove(Integer key) {
        this.innerHeap.remove(key);
    }

    @Override
    public void setContent(Map<Integer, IValue> other) {
        this.innerHeap.setContent(other);
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return innerHeap.getContent();
    }

    @Override
    public boolean isDefined(Integer key) {
        return this.innerHeap.isDefined(key);
    }

    @Override
    public String toString(){
        return innerHeap.toString();
    }
}

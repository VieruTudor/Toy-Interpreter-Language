package model.adt;

import model.exceptions.InterpreterException;
import model.values.IValue;

import java.util.Map;

public interface IHeap {
    void add(Integer key, IValue value);
    void update(Integer key, IValue value) throws InterpreterException;
    IValue search(Integer key) throws InterpreterException;
    void remove(Integer key);
    void setContent(Map<Integer, IValue> other);
    Map<Integer, IValue> getContent();
    boolean isDefined(Integer key);
}

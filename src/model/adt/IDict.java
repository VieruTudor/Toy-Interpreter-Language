package model.adt;

import model.exceptions.InterpreterException;

import java.util.Map;

public interface IDict<T1, T2> {
    void add(T1 key, T2 value);
    void update(T1 key, T2 value) throws InterpreterException;
    T2 search(T1 key) throws InterpreterException;
    void remove(T1 key);
    Dict<T1, T2> getCopy();
    void setContent(Map<T1, T2> other);
    Map<T1, T2> getContent();
    boolean isDefined(T1 key);
}

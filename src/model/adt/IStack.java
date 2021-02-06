package model.adt;


import model.exceptions.InterpreterException;

import java.util.ArrayList;

public interface IStack<T> {
    void push(T elem);
    T pop() throws InterpreterException;
    boolean isEmpty();
    T peek();

    ArrayList<T> getContent();
}

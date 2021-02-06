package model.adt;

import model.exceptions.InterpreterException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Stack<T> implements IStack<T>{
    private final Deque<T> innerStack;

    public Stack(){
        this.innerStack = new ArrayDeque<T>() {};
    }

    @Override
    public void push(T elem) {
        innerStack.push(elem);
    }

    @Override
    public T pop() throws InterpreterException {
        if(innerStack.isEmpty())
            throw new InterpreterException("Error : Stack is empty.");
        return innerStack.pop();
    }

    @Override
    public boolean isEmpty() {
        return innerStack.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T elem : innerStack)
        {
            stringBuilder.append(elem.toString());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public T peek(){
        return innerStack.peek();
    }

    @Override
    public ArrayList<T> getContent() {
        return new ArrayList<T>(this.innerStack);
    }
}

package model.adt;

import java.util.LinkedList;
import java.util.Queue;

public class MyList<T> implements IList<T>{
    private final Queue<T> innerList;

    public MyList(){
        this.innerList = new LinkedList<>();
    }

    @Override
    public void add(T element) {
        innerList.add(element);
    }

    @Override
    public T pop() {
        return innerList.poll();
    }

    @Override
    public boolean isEmpty() {
        return innerList.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T elem : innerList)
        {
            stringBuilder.append(elem.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public LinkedList<T> getContent(){
        return (LinkedList<T>) this.innerList;
    }
}

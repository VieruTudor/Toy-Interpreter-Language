package model.adt;

import model.exceptions.InterpreterException;

import java.util.HashMap;
import java.util.Map;

public class Dict<T1, T2> implements IDict<T1, T2> {
    private final HashMap<T1, T2> innerDict;

    public Dict() {
        innerDict = new HashMap<>();
    }

    @Override
    public void add(T1 key, T2 value){
        innerDict.put(key, value);
    }

    @Override
    public void update(T1 key, T2 value) throws InterpreterException {
        innerDict.put(key, value);
    }

    @Override
    public T2 search(T1 key) throws InterpreterException {
        if (!innerDict.containsKey(key))
            throw new InterpreterException("Error: key " + key.toString() + " not in the symTable");
        return innerDict.get(key);
    }

    @Override
    public void remove(T1 key) {
        this.innerDict.remove(key);
    }

    @Override
    public void setContent(Map<T1, T2> other) {
        innerDict.clear();
        innerDict.putAll(other);
    }

    @Override
    public Map<T1, T2> getContent() {
        return innerDict;
    }

    @Override
    public Dict<T1, T2> getCopy(){
        Dict<T1, T2> copy = new Dict<>();
        copy.setContent(this.getContent());
        return copy;
    }

    @Override
    public boolean isDefined(T1 key) {
        return innerDict.containsKey(key);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T1 key : innerDict.keySet()) {
            stringBuilder.append(String.format("%s:%s\n", key.toString(), innerDict.get(key).toString()));
        }
        return stringBuilder.toString();
    }
}

package model.adt;

public interface IList<T> {
    void add(T element);
    T pop();
    boolean isEmpty();
}

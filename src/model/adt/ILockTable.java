package model.adt;

import java.util.Map;

public interface ILockTable<T> {
    int allocate(T value);
    void update(int address, T value);
    Map<Integer, T> getContent();
    boolean exists(int address);
    void setContent(Map<Integer, T> map);
    T get(int addr);

}

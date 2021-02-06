package model.adt;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LockTable<T> implements ILockTable<T>{
    AtomicInteger freeValue;
    Map<Integer, T> lockTable;

    public LockTable(){
        this.lockTable = new HashMap<>();
        freeValue = new AtomicInteger(0);
    }

    @Override
    public synchronized int allocate(T value) {
        lockTable.put(freeValue.incrementAndGet(), value);
        return freeValue.get();
    }

    @Override
    public synchronized void update(int address, T value) {
        lockTable.put(address, value);
    }

    @Override
    public synchronized Map<Integer, T> getContent() {
        return lockTable;
    }

    @Override
    public synchronized boolean exists(int address) {
        return lockTable.containsKey(address);
    }

    @Override
    public synchronized void setContent(Map<Integer, T> map) {
        lockTable = map;
    }

    @Override
    public synchronized T get(int addr) {
        return lockTable.get(addr);
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (var elem : lockTable.keySet()){
            if (elem != null)
                s.append(elem.toString())
                .append(" -> ")
                .append(lockTable.get(elem).toString())
                .append("\n");
        }
        return s.toString();
    }
}

package model.values;

import model.types.IType;
import model.types.IntType;

import java.util.Objects;

public class IntValue implements IValue {
    private final int val;

    public IntValue(int _val) {
        this.val = _val;
    }

    public IntValue() {
        this.val = 0;
    }

    public int getValue() {
        return val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue intValue = (IntValue) o;
        return val == intValue.val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.format("%d", val);
    }

}

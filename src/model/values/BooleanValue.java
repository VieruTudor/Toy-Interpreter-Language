package model.values;

import model.types.BooleanType;
import model.types.IType;

import java.util.Objects;

public class BooleanValue implements IValue {
    private final boolean val;

    public BooleanValue(boolean _val) {
        this.val = _val;
    }

    public BooleanValue() {
        this.val = false;
    }

    public boolean getValue() {
        return val;
    }

    @Override
    public IType getType() {
        return new BooleanType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanValue that = (BooleanValue) o;
        return val == that.val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        if (val)
            return "true";
        return "false";
    }
}

package model.values;

import model.types.IType;
import model.types.StringType;

import java.util.Objects;

public class StringValue implements IValue {
    private final String value;

    public StringValue() {
        this.value = "";
    }

    public StringValue(String _value) {
        this.value = _value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public String toString(){
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValue that = (StringValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

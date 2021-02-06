package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType {

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public boolean equals(IType other) {
        return other instanceof StringType;
    }

    @Override
    public IValue defaultValue() {
        return new StringValue();
    }
}

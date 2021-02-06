package model.types;

import model.values.IValue;
import model.values.IntValue;

public class IntType implements IType {
    @Override
    public String toString() {
        return "int";
    }

    @Override
    public boolean equals(IType other) {
        return other instanceof IntType;
    }

    @Override
    public IValue defaultValue() {
        return new IntValue();
    }
}

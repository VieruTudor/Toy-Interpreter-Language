package model.types;

import model.values.BooleanValue;
import model.values.IValue;

public class BooleanType implements IType{
    @Override
    public String toString()
    {
        return "bool";
    }

    @Override
    public boolean equals(IType other) {
        return other instanceof BooleanType;
    }

    @Override
    public IValue defaultValue() {
        return new BooleanValue();
    }
}

package model.types;

import model.values.IValue;
import model.values.RefValue;

public class RefType implements IType {
    private final IType inner;

    public RefType(IType _inner){
        this.inner = _inner;
    }

    public IType getInner(){
        return inner;
    }

    @Override
    public boolean equals(IType other) {
        if (other instanceof RefType){
            return inner.equals(((RefType) other).getInner());
        }
        else{
            return false;
        }
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public String toString(){
        return "Reference(" + inner.toString()+")";
    }
}

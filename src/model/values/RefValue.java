package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue {
    private final int address;
    private final IType locationType;

    public RefValue(int _address, IType _locationType) {
        this.address = _address;
        this.locationType = _locationType;
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString(){
        return "Ref(" + address + ", "  + locationType.toString() + ")";
    }
}

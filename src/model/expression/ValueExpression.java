package model.expression;

import model.adt.IDict;
import model.adt.IHeap;
import model.exceptions.InterpreterException;
import model.types.IType;
import model.values.IValue;

public class ValueExpression implements IExpression {
    private final IValue value;

    public ValueExpression(IValue _value) {
        this.value = _value;
    }

    @Override
    public String toString() {
        return value.toString();
    }


    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) throws InterpreterException {
        return value;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        return value.getType();
    }
}

package model.expression;

import model.adt.IDict;
import model.adt.IHeap;
import model.exceptions.InterpreterException;
import model.types.IType;
import model.values.IValue;

public class VariableExpression implements IExpression {
    private final String varName;

    public VariableExpression(String _varName){
        this.varName = _varName;
    }

    @Override
    public String toString(){
        return varName;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) throws InterpreterException {
        return symTable.search(varName);
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        return typeEnvironment.search(varName);
    }
}

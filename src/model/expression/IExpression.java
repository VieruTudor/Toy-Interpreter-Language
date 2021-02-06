package model.expression;

import model.adt.IHeap;
import model.types.IType;
import model.values.IValue;
import model.exceptions.InterpreterException;
import model.adt.IDict;

public interface IExpression {
    IValue eval(IDict<String, IValue> symTable, IHeap heap) throws InterpreterException;
    IType typeCheck(IDict<String , IType> typeEnvironment) throws InterpreterException;
}

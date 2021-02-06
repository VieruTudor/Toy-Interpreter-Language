package model.expression;

import model.adt.IDict;
import model.adt.IHeap;
import model.exceptions.InterpreterException;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class ReadHeap implements IExpression{
    private final IExpression expression;

    public ReadHeap(IExpression _expression) {
        this.expression = _expression;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) throws InterpreterException {
        IValue expressionValue = expression.eval(symTable, heap);
        if (!(expressionValue instanceof RefValue))
            throw new InterpreterException("Expression not of RefType");
        int address = ((RefValue) expressionValue).getAddress();
        if(!heap.isDefined(address))
            throw new InterpreterException("Expression not defined");
        return heap.search(address);
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType type = expression.typeCheck(typeEnvironment);
        if (type instanceof RefType){
            RefType refType = (RefType)type;
            return refType.getInner();
        }
        else
            throw new InterpreterException("Operand is not a Reference");
    }

    @Override
    public String toString(){
        return "ReadHeap(" + expression.toString() + ")";
    }
}

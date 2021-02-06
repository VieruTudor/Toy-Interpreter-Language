package model.statement;

import model.ProgramState;
import model.adt.Dict;
import model.adt.Heap;
import model.adt.IDict;
import model.exceptions.InterpreterException;
import model.expression.IExpression;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class WriteHeapStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public WriteHeapStatement(String _variableName, IExpression _expression) {
        this.variableName = _variableName;
        this.expression = _expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Dict<String, IValue> symbolTable = state.getSymTable();
        Heap heap = state.getHeap();

        if(!symbolTable.isDefined(variableName))
            throw new InterpreterException("Variable not declared in symbol table");

        IValue variableValue = symbolTable.search(variableName);
        if(!(variableValue instanceof RefValue))
            throw new InterpreterException("Variable is not a Reference");

        RefValue reference = (RefValue)variableValue;
        if(!heap.isDefined(reference.getAddress()))
            throw new InterpreterException("Variable not defined in heap");

        IValue value = expression.eval(symbolTable, heap);
        if(!value.getType().equals(reference.getLocationType()))
            throw new InterpreterException("Expression does not match the variable");

        heap.update(reference.getAddress(), value);

        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType varType = typeEnvironment.search(variableName);
        IType expType = expression.typeCheck(typeEnvironment);
        if (varType.equals(new RefType(expType)))
            return typeEnvironment;
        else
            throw new InterpreterException("Different types when writing on heap.");
    }

    @Override
    public String toString(){
        return "WriteHeap(" + variableName + ", " + expression.toString() + ")";
    }
}

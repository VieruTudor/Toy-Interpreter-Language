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

import java.util.Random;

public class New implements IStatement {
    private final String name;
    private final IExpression expression;

    public New(String _name, IExpression _expression) {
        this.name = _name;
        this.expression = _expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Dict<String, IValue> symbolTable = state.getSymTable();
        Heap heap = state.getHeap();

        if (!symbolTable.isDefined(name))
            throw new InterpreterException("Variable not defined in symbol table");

        IValue value = symbolTable.search(name);
        if (!(value.getType() instanceof RefType))
            throw new InterpreterException("Variable not a reference");

        IValue expressionValue = expression.eval(symbolTable, heap);
        IType locationType = ((RefValue) value).getLocationType();
        if (!expressionValue.getType().equals(locationType))
            throw new InterpreterException("Types not equal");

        Random random = new Random();
        int position = random.nextInt(50);
        if (position == 0 || heap.isDefined(position))
            position = random.nextInt(50);

        heap.add(position, expressionValue);
        symbolTable.add(name, new RefValue(position, locationType));

        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType varType = typeEnvironment.search(name);
        IType expType = expression.typeCheck(typeEnvironment);
        if (varType.equals(new RefType(expType))){
            return typeEnvironment;
        }
        else
            throw new InterpreterException("New : RHS and LHS have different types.");
    }

    @Override
    public String toString() {
        return "New(" + this.name + ", " + this.expression.toString() + ")";
    }
}

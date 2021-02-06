package model.statement;

import model.ProgramState;
import model.adt.Dict;
import model.adt.Heap;
import model.adt.IDict;
import model.adt.Stack;
import model.exceptions.InterpreterException;
import model.expression.IExpression;
import model.types.BooleanType;
import model.types.IType;
import model.values.BooleanValue;
import model.values.IValue;

public class IfStatement implements IStatement{
    private final IExpression expression;
    private final IStatement thenStatement;
    private final IStatement elseStatement;

    @Override
    public String toString(){
        return "( IF (" + expression.toString() + ") THEN (" + thenStatement.toString() + ") ELSE (" + elseStatement.toString() + "))";
    }

    public IfStatement(IExpression _expression, IStatement _thenStatement, IStatement _elseStatement) {
        this.expression = _expression;
        this.thenStatement = _thenStatement;
        this.elseStatement = _elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Stack<IStatement> executionStack = state.getExecutionStack();
        Dict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeap();

        IValue condition = expression.eval(symTable, heap);
        if(condition.getType().equals(new BooleanType())){
            BooleanValue conditionValue = (BooleanValue)condition;
            if(conditionValue.getValue()){
                executionStack.push(thenStatement);
            }
            else
                executionStack.push(elseStatement);
        }

        state.setExecutionStack(executionStack);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType expType = expression.typeCheck(typeEnvironment);
        if (expType.equals(new BooleanType())){
            thenStatement.typeCheck(typeEnvironment.getCopy());
            elseStatement.typeCheck(typeEnvironment.getCopy());
            return typeEnvironment;
        }
        else
            throw new InterpreterException("The condition of the IF is not of Boolean Type.");
    }
}

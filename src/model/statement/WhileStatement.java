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

public class WhileStatement implements IStatement {
    private final IExpression expression;
    private final IStatement statement;

    public WhileStatement(IExpression _expression, IStatement _statemtent){
        this.expression = _expression;
        this.statement = _statemtent;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Stack<IStatement> executionStack = state.getExecutionStack();
        Dict<String, IValue> symbolTable = state.getSymTable();
        Heap heap = state.getHeap();

        IValue value = expression.eval(symbolTable, heap);
        if(!value.getType().equals(new BooleanType()))
            throw new InterpreterException("Expression is not of boolean type");

        if(((BooleanValue)value).getValue()){
            executionStack.push(this);
            executionStack.push(statement);
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType expType = expression.typeCheck(typeEnvironment);
        if (expType.equals(new BooleanType())){
            statement.typeCheck(typeEnvironment);
            return typeEnvironment;
        }
        else
            throw new InterpreterException("Expression must be of Boolean Type in the While Statement.");
    }

    @Override
    public String toString(){
        return "(while (" + expression.toString() + ")" + statement.toString() + ")";
    }
}

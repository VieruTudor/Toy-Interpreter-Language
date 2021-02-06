package model.statement;

import model.ProgramState;
import model.adt.Dict;
import model.adt.Heap;
import model.adt.IDict;
import model.adt.Stack;
import model.exceptions.InterpreterException;
import model.expression.IExpression;
import model.types.IType;
import model.values.IValue;

public class AssignStatement implements IStatement {
    private final String id;
    private final IExpression expression;

    public AssignStatement(String _id, IExpression _expression){
        this.id = _id;
        this.expression = _expression;
    }

    public String getId(){
        return this.id;
    }

    public IExpression getExpression(){
        return this.expression;
    }

    @Override
    public String toString(){
        return this.id + " = " + this.expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Stack<IStatement> executionStack = state.getExecutionStack();
        Dict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeap();

        if(symTable.isDefined(id)){
            IValue value = expression.eval(symTable, heap);
            IType typeId = symTable.search(id).getType();
            if (value.getType().equals(typeId))
                symTable.update(id, value);
            else
                throw new InterpreterException("Declared type of variable " + id + " and type of the assigned expression do not match.");
        }
        else
            throw new InterpreterException("The used variable " + id + " was not declared.");
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType varType = typeEnvironment.search(id);
        IType expType = expression.typeCheck(typeEnvironment);
        if (varType.equals(expType)){
            return typeEnvironment;
        }
        else
            throw new InterpreterException("Operands must have the same type in the assignment.");
    }
}

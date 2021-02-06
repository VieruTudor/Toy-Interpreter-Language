package model;

import model.adt.*;
import model.exceptions.InterpreterException;
import model.statement.IStatement;
import model.values.IValue;

import java.io.BufferedReader;

public class ProgramState {
    private Stack<IStatement> executionStack;
    private Dict<String, IValue> symTable;
    private MyList<IValue> output;
    private Dict<String, BufferedReader> fileTable;


    private LockTable<Integer> lockTable;
    private Heap heap;
    private static int lastID = 1;
    private int ID;

    public ProgramState(Stack<IStatement> _executionStack,
                        Dict<String, IValue> _symTable,
                        MyList<IValue> _output,
                        Dict<String, BufferedReader> _fileTable,
                        Heap _heap,
                        IStatement _statement) {
        this.executionStack = _executionStack;
        this.symTable = _symTable;
        this.output = _output;
        this.fileTable = _fileTable;
        this.heap = _heap;
        this.executionStack.push(_statement);
        this.ID = 1;
    }


    public synchronized void setID() {
        lastID += 1;
        ID = lastID;
    }


    public ProgramState(Stack<IStatement> _executionStack,
                        Dict<String, IValue> _symTable,
                        MyList<IValue> _output,
                        Dict<String, BufferedReader> _fileTable,
                        Heap _heap,
                        LockTable<Integer> _lockTable) {
        this.executionStack = _executionStack;
        this.symTable = _symTable;
        this.output = _output;
        this.fileTable = _fileTable;
        this.heap = _heap;
        this.lockTable = _lockTable;
        this.ID = 1;
    }

    public ProgramState(IStatement originalProgram) {
        this.executionStack = new Stack<>();
        this.symTable = new Dict<>();
        this.output = new MyList<>();
        this.fileTable = new Dict<>();
        this.heap = new Heap();
        this.lockTable = new LockTable<>();
        this.ID = 1;
        executionStack.push(originalProgram);

    }

    @Override
    public String toString() {
        return "------STEP BEGIN------\n" +
                "ID : " + this.ID + "\n" +
                "Output Console: \n" +
                output.toString() + "\n" +
                "Symbol Table: \n" +
                symTable.toString() + "\n" +
                "Execution Stack: \n" +
                executionStack.toString() + "\n" +
                "File Table: \n" +
                fileTable.toString() + "\n" +
                "Heap: \n" +
                heap.toString() + "\n" +
                "Lock: \n" +
                lockTable.toString() + "\n" +
                "------STEP DONE------\n";
    }

    public boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }


    public Stack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(Stack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public Dict<String, IValue> getSymTable() {
        return symTable;
    }

    public void setSymTable(Dict<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public MyList<IValue> getOutput() {
        return output;
    }

    public void setOutput(MyList<IValue> output) {
        this.output = output;
    }

    public Heap getHeap() {
        return heap;
    }

    public Dict<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(Dict<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }


    public ProgramState oneStep() throws InterpreterException {
        if (executionStack.isEmpty())
            throw new InterpreterException("Execution Stack is empty.");
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public void typeCheck() throws InterpreterException {
        this.getExecutionStack().peek().typeCheck(new Dict<>());
    }

    public int getId() {
        return this.ID;
    }

    public LockTable<Integer> getLockTable() {
        return lockTable;
    }

    public void setLockTable(LockTable<Integer> lockTable) {
        this.lockTable = lockTable;
    }
}

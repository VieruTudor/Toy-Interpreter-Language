package Interpreter;

import Controller.Controller;
import Repository.Repository;
import View.ExitCommand;
import View.RunExample;
import View.TextMenu;
import model.ProgramState;
import model.expression.*;
import model.statement.*;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;

import java.util.LinkedList;


public class Interpreter {
    public static void main(String[] args) {


        IStatement state1 = fileTest();
        ProgramState programState1 = new ProgramState(state1);
        Repository repository1 = new Repository("log1.txt");
        repository1.addProgramState(programState1);
        Controller controller1 = new Controller(repository1);

        IStatement state2 = relationalTest();
        ProgramState programState2 = new ProgramState(state2);
        Repository repository2 = new Repository("log2.txt");
        repository2.addProgramState(programState2);
        Controller controller2 = new Controller(repository2);

        IStatement state3 = garbageCollectorTest();
        ProgramState programState3 = new ProgramState(state3);
        Repository repository3 = new Repository("log3.txt");
        repository3.addProgramState(programState3);
        Controller controller3 = new Controller(repository3);

        IStatement state4 = referenceTest();
        ProgramState programState4 = new ProgramState(state4);
        Repository repository4 = new Repository("log4.txt");
        repository4.addProgramState(programState4);
        Controller controller4 = new Controller(repository4);

        IStatement state5 = whileTest();
        ProgramState programState5 = new ProgramState(state5);
        Repository repository5 = new Repository("log5.txt");
        repository5.addProgramState(programState5);
        Controller controller5 = new Controller(repository5);

        IStatement state6 = readHeap();
        ProgramState programState6 = new ProgramState(state6);
        Repository repository6 = new Repository("log6.txt");
        repository6.addProgramState(programState6);
        Controller controller6 = new Controller(repository6);

        IStatement state7 = writeHeap();
        ProgramState programState7 = new ProgramState(state7);
        Repository repository7 = new Repository("log7.txt");
        repository7.addProgramState(programState7);
        Controller controller7 = new Controller(repository7);

        IStatement state8 = thr2();
        ProgramState programState8 = new ProgramState(state8);
        Repository repository8 = new Repository("log8.txt");
        repository8.addProgramState(programState8);
        Controller controller8 = new Controller(repository8);

        IStatement state9 = relationalTestFAIL();
        ProgramState programState9 = new ProgramState(state9);
        Repository repository9 = new Repository("log9.txt");
        repository9.addProgramState(programState9);
        Controller controller9 = new Controller(repository9);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", state1.toString(), controller1));
        menu.addCommand(new RunExample("2", state2.toString(), controller2));
        menu.addCommand(new RunExample("3", state3.toString(), controller3));
        menu.addCommand(new RunExample("4", state4.toString(), controller4));
        menu.addCommand(new RunExample("5", state5.toString(), controller5));
        menu.addCommand(new RunExample("6", state6.toString(), controller6));
        menu.addCommand(new RunExample("7", state7.toString(), controller7));
        menu.addCommand(new RunExample("8", state8.toString(), controller8));
        menu.addCommand(new RunExample("9", state9.toString(), controller9));
        menu.show();


    }
    public LinkedList<IStatement> getExamples(){
        IStatement statement1 = fileTest();
        IStatement statement2 = relationalTest();
        IStatement statement3 = referenceTest();
        IStatement statement4 = garbageCollectorTest();
        IStatement statement5 = whileTest();
        IStatement statement6 = writeHeap();
        IStatement statement7 = readHeap();
        IStatement statement8 = thr2();
        IStatement statement9 = relationalTestFAIL();

        LinkedList<IStatement> stateList = new LinkedList<>();
        stateList.add(statement1);
        stateList.add(statement2);
        stateList.add(statement3);
        stateList.add(statement4);
        stateList.add(statement5);
        stateList.add(statement6);
        stateList.add(statement7);
        stateList.add(statement8);
        stateList.add(statement9);
        return stateList;
    }
    public static IStatement fileTest() {
        return new CompStatement(new VarDeclStatement("varf", new StringType()),
                new CompStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompStatement(new VarDeclStatement("varc", new IntType()),
                                new CompStatement(new OpenReadFile(new VariableExpression("varf")),
                                        new CompStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFile(new VariableExpression("varf"))))))))));
    }

    public static IStatement relationalTest() {
        return new CompStatement(new VarDeclStatement("x", new IntType()),
                new CompStatement(new AssignStatement("x", new ValueExpression(new IntValue(2))), new PrintStatement(new RelationalExpression(new VariableExpression("x"),
                        new ValueExpression(new IntValue(3)), "<"))));
    }

    public static IStatement referenceTest() {
        return new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new New("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new New("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new VariableExpression("a")),
                                                new PrintStatement(new VariableExpression("v")))))));

    }

    public static IStatement garbageCollectorTest() {
        return new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new New("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new New("a", new VariableExpression("v")),
                                        new CompStatement(new New("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeap(new ReadHeap(new VariableExpression("a")))))))));
    }

    public static IStatement whileTest() {
        return new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                                new CompStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression('-',
                                        new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));
    }

    public static IStatement writeHeap() {
        return new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new New("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new PrintStatement(new ReadHeap(new VariableExpression("v"))),
                                new CompStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression('+', new ReadHeap(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));
    }

    public static IStatement readHeap() {
        return new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new New("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new New("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new ReadHeap(new VariableExpression("a"))),
                                                new PrintStatement(new ReadHeap(new VariableExpression("v"))))))));
    }

    public static IStatement thr2(){
        return new CompStatement(
                new VarDeclStatement(
                        "v",
                        new IntType()
                ),
                new CompStatement(
                        new VarDeclStatement(
                                "a",
                                new RefType(
                                        new IntType()
                                )
                        ),
                        new CompStatement(
                                new AssignStatement(
                                        "v",
                                        new ValueExpression(
                                                new IntValue(10)
                                        )
                                ),
                                new CompStatement(
                                        new New(
                                                "a",
                                                new ValueExpression(
                                                        new IntValue(22)
                                                )
                                        ),
                                        new CompStatement(
                                                new Fork(
                                                        new CompStatement(
                                                                new WriteHeapStatement(
                                                                        "a",
                                                                        new ValueExpression(
                                                                                new IntValue(30)
                                                                        )
                                                                ),
                                                                new CompStatement(
                                                                        new AssignStatement(
                                                                                "v",
                                                                                new ValueExpression(
                                                                                        new IntValue(32)
                                                                                )
                                                                        ),
                                                                        new CompStatement(
                                                                                new PrintStatement(
                                                                                        new VariableExpression("v")
                                                                                ),
                                                                                new PrintStatement(
                                                                                        new ReadHeap(
                                                                                                new VariableExpression(
                                                                                                        "a"
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStatement(
                                                        new PrintStatement(
                                                                new VariableExpression(
                                                                        "v"
                                                                )
                                                        ),
                                                        new PrintStatement(
                                                                new ReadHeap(
                                                                        new VariableExpression("a")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static IStatement relationalTestFAIL() {
        return new CompStatement(new VarDeclStatement("x", new StringType()),
                new CompStatement(new AssignStatement("x", new ValueExpression(new IntValue(2))), new PrintStatement(new RelationalExpression(new VariableExpression("x"),
                        new ValueExpression(new IntValue(3)), "<"))));
    }
}

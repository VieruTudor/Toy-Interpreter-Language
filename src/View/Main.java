package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.exceptions.InterpreterException;
import model.expression.*;
import model.statement.*;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    ListController listController;

    @Override
    public void start(Stage primaryStage) throws Exception, InterpreterException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgramChooser.fxml"));
            BorderPane root = (BorderPane) loader.load();
            listController = loader.getController();
            addProgramStates();
            Scene scene = new Scene(root, 1000, 511);

            scene.getStylesheets().add(String.valueOf(getClass().getResource("app.css")));
            primaryStage.setScene(scene);
            primaryStage.setTitle("Program Chooser");
            primaryStage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Interpreter Error");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText(e.toString());
            alert.showAndWait();

        }
    }

    private void addProgramStates() {
        IStatement state1 = new CompStatement(new VarDeclStatement("varf", new StringType()),
                new CompStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompStatement(new VarDeclStatement("varc", new IntType()),
                                new CompStatement(new OpenReadFile(new VariableExpression("varf")),
                                        new CompStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFile(new VariableExpression("varf"))))))))));
        IStatement state2 = new CompStatement(new VarDeclStatement("x", new IntType()),
                new CompStatement(new AssignStatement("x", new ValueExpression(new IntValue(2))), new PrintStatement(new RelationalExpression(new VariableExpression("x"),
                        new ValueExpression(new IntValue(3)), "<"))));

        IStatement state3 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new New("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new New("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new VariableExpression("a")),
                                                new PrintStatement(new VariableExpression("v")))))));
        IStatement state4 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new New("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new New("a", new VariableExpression("v")),
                                        new CompStatement(new New("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeap(new ReadHeap(new VariableExpression("a")))))))));
        IStatement state5 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                                new CompStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression('-',
                                        new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));
        IStatement state6 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new New("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new PrintStatement(new ReadHeap(new VariableExpression("v"))),
                                new CompStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression('+', new ReadHeap(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));
        IStatement state7 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new New("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new New("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new ReadHeap(new VariableExpression("a"))),
                                                new PrintStatement(new ReadHeap(new VariableExpression("v"))))))));
        IStatement state8 = new CompStatement(
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

        IStatement state9 = new CompStatement(new VarDeclStatement("x", new StringType()),
                new CompStatement(new AssignStatement("x", new ValueExpression(new IntValue(2))), new PrintStatement(new RelationalExpression(new VariableExpression("x"),
                        new ValueExpression(new IntValue(3)), "<"))));


        IStatement state10 = new CompStatement(
                new VarDeclStatement(
                        "a",
                        new RefType(
                                new IntType()
                        )
                ),
                new CompStatement(
                        new New(
                                "a",
                                new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new ForStatement(
                                        new ValueExpression(new IntValue(0)),
                                        new ValueExpression(new IntValue(3)),
                                        new ArithmeticExpression(
                                                '+',
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(1))
                                        ),
                                        new Fork(new CompStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement(
                                                        "v",
                                                        new ArithmeticExpression(
                                                                '*',
                                                                new VariableExpression("v"),
                                                                new ReadHeap(new VariableExpression("a"))
                                                        )
                                                )
                                        ))
                                ),
                                new PrintStatement(new ReadHeap(new VariableExpression("a")))
                        )

                )
        );
        
        IStatement state11 = new CompStatement(
                new VarDeclStatement(
                        "v1",
                        new RefType(new IntType())
                ),
                new CompStatement(
                        new VarDeclStatement(
                                "v2",
                                new RefType(new IntType())
                        ),
                        new CompStatement(
                                new VarDeclStatement("x", new IntType()),
                                new CompStatement(
                                        new VarDeclStatement("q", new IntType()),
                                        new CompStatement(
                                                new New("v1", new ValueExpression(new IntValue(20))),
                                                new CompStatement(
                                                        new New("v2", new ValueExpression(new IntValue(30))),
                                                        new CompStatement(
                                                                new NewLockStatement("x"),
                                                                new CompStatement(
                                                                        new Fork(
                                                                                new CompStatement(
                                                                                        new Fork(
                                                                                                new CompStatement(
                                                                                                        new LockStatement("x"),
                                                                                                        new CompStatement(
                                                                                                                new WriteHeapStatement(
                                                                                                                        "v1",
                                                                                                                        new ArithmeticExpression(
                                                                                                                                '-',
                                                                                                                                new ReadHeap(new VariableExpression("v1")),
                                                                                                                                new ValueExpression(new IntValue(1))
                                                                                                                        )
                                                                                                                ),
                                                                                                                new UnlockStatement("x")
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStatement(
                                                                                                new LockStatement("x"),
                                                                                                new CompStatement(
                                                                                                        new WriteHeapStatement(
                                                                                                                "v1",
                                                                                                                new ArithmeticExpression(
                                                                                                                        '*',
                                                                                                                        new ReadHeap(new VariableExpression("v1")),
                                                                                                                        new ValueExpression(new IntValue(10))
                                                                                                                )
                                                                                                        ),
                                                                                                        new UnlockStatement("x")
                                                                                                )
                                                                                        )
                                                                                )
                                                                        ),
                                                                        new CompStatement(
                                                                                new NewLockStatement("q"),
                                                                                new CompStatement(
                                                                                        new Fork(
                                                                                                new CompStatement(
                                                                                                        new Fork(
                                                                                                                new CompStatement(
                                                                                                                        new LockStatement("q"),
                                                                                                                        new CompStatement(
                                                                                                                                new WriteHeapStatement(
                                                                                                                                        "v2",
                                                                                                                                        new ArithmeticExpression(
                                                                                                                                                '+',
                                                                                                                                                new ReadHeap(new VariableExpression("v2")),
                                                                                                                                                new ValueExpression(new IntValue(5))
                                                                                                                                        )
                                                                                                                                ),
                                                                                                                                new UnlockStatement("q")
                                                                                                                        )
                                                                                                                )
                                                                                                        ),
                                                                                                        new CompStatement(
                                                                                                                new LockStatement("q"),
                                                                                                                new CompStatement(
                                                                                                                        new WriteHeapStatement(
                                                                                                                                "v2",
                                                                                                                                new ArithmeticExpression(
                                                                                                                                        '*',
                                                                                                                                        new ReadHeap(new VariableExpression("v2")),
                                                                                                                                        new ValueExpression(new IntValue(10))
                                                                                                                                )
                                                                                                                        ),
                                                                                                                        new UnlockStatement("q")
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStatement(
                                                                                                new NopStatement(),
                                                                                                new CompStatement(
                                                                                                        new NopStatement(),
                                                                                                        new CompStatement(
                                                                                                                new NopStatement(),
                                                                                                                new CompStatement(
                                                                                                                        new NopStatement(),
                                                                                                                        new CompStatement(
                                                                                                                                new LockStatement("x"),
                                                                                                                                new CompStatement(
                                                                                                                                        new PrintStatement(new ReadHeap(new VariableExpression("v1"))),
                                                                                                                                        new CompStatement(
                                                                                                                                                new UnlockStatement("x"),
                                                                                                                                                new CompStatement(
                                                                                                                                                        new LockStatement("q"),
                                                                                                                                                        new CompStatement(
                                                                                                                                                                new PrintStatement(new ReadHeap(new VariableExpression("v2"))),
                                                                                                                                                                new UnlockStatement("q")
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );



        this.listController.addStatement(state1);
        this.listController.addStatement(state2);
        this.listController.addStatement(state3);
        this.listController.addStatement(state4);
        this.listController.addStatement(state5);
        this.listController.addStatement(state6);
        this.listController.addStatement(state7);
        this.listController.addStatement(state8);
        this.listController.addStatement(state9);
        this.listController.addStatement(state10);
        this.listController.addStatement(state11);
    }
}

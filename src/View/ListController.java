package View;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.adt.Dict;
import model.exceptions.InterpreterException;
import model.statement.IStatement;

import java.io.IOException;
import java.util.LinkedList;

public class ListController {
    private static LinkedList<IStatement> statements = new LinkedList<>();

    @FXML
    private Button selectButton;

    @FXML
    private ListView<IStatement> programList;

    @FXML
    private void initialize(){
        programList.setItems(FXCollections.observableArrayList(statements));
    }

    public void addStatement(IStatement statement) {
        statements.add(statement);
        programList.setItems(FXCollections.observableArrayList(statements));

    }

    public void displayError(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Interpreter error");
        alert.setContentText(error);
        alert.show();
    }

    public void selectItem(){
        try{
            int index = programList.getSelectionModel().getSelectedIndex();
            if(index == -1){
                displayError("Please select a program from the list !");
                return;
            }
            index += 1;
            startProgram(index);
        }
        catch (InterpreterException | IOException e){
            displayError(e.getMessage());
        }
    }

    private void startProgram(int i) throws IOException, InterpreterException {
        IStatement statement = statements.get(i - 1);
        statement.typeCheck(new Dict<>());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgramExecutor.fxml"));
        BorderPane root = (BorderPane)loader.load();
        ProgramExecutorController controller = loader.getController();
        controller.setProgramState(statement, i);

        Scene newScene = new Scene(root, 900, 700);
        newScene.getStylesheets().add(String.valueOf(getClass().getResource("app.css")));

        Stage newWindow = new Stage();
        newWindow.setTitle("Running program : " + i);
        newWindow.setScene(newScene);
        newWindow.show();
    }
}
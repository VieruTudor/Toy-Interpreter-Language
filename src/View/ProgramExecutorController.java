package View;

import Controller.Controller;
import Repository.IRepository;
import Repository.Repository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Pair;
import model.ProgramState;
import model.exceptions.InterpreterException;
import model.statement.IStatement;
import model.values.IValue;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ProgramExecutorController {

    @FXML
    public Label executionStackText;
    @FXML
    public Label symbolTableText;
    
    @FXML
    public TableColumn<Pair<Integer, Integer>, Integer> lockAddress;
    @FXML
    public TableColumn<Pair<Integer, Integer>, Integer> lockValue;
    public TableView<Pair<Integer, Integer>> lockTableView;
    @FXML
    private TextField numberOfPrgStates;
    @FXML
    private TableView<Pair<String, IValue>> symbolTable;
    @FXML
    private TableColumn<Pair<Object, Object>, Object> variableName;
    @FXML
    private TableColumn<Pair<Object, Object>, Object> variableValue;
    @FXML
    private ListView<String> executionStack;
    @FXML
    private TableColumn<Pair<Object, Object>, Object> addressHeap;
    @FXML
    private TableColumn<Pair<Object, Object>, Object> valueHeap;
    @FXML
    private TableView<Pair<Integer, IValue>> heap;
    @FXML
    private ListView<String> fileTable;
    @FXML
    private ListView<String> programIdentifiers;
    @FXML
    private ListView<String> output;
    @FXML
    private Button runButton;

    private Controller controller;
    List<ProgramState> programStateList;
    private int currentState = 1;

    public void setProgramState(IStatement statement, int index) throws InterpreterException {
        String logFile = "log" + index + ".txt";
        ProgramState state = new ProgramState(statement);
        IRepository repository = new Repository(logFile);
        repository.addProgramState(state);
        controller = new Controller(repository);
        controller.openExecutor();
        programStateList = controller.getStates();
    }

    public void displayError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Interpreter error");
        alert.setContentText(error);
        alert.show();
    }

    private void oneStep() {
        try {
            controller.conservativeGarbageCollector(programStateList);
            controller.oneStepForAllPrograms(programStateList);
            programStateList = controller.getStates();
            if (programStateList.size() == 0) {
                controller.closeExecutor();
            }
        } catch (InterpreterException | InterruptedException e) {
            String errorMessage = e.getMessage();
            errorMessage = errorMessage.substring(errorMessage.indexOf(':') + 1);
            displayError(errorMessage);
        }

    }

    private void setAllFields() {
        populateSymbolTable();
        populateExecutionStack();
        populateHeapTable();
        populateOutputTable();
        populateFileTable();
        populateLockTable();
        setNumberOfStates();
        populateProgramList();
    }

    private void populateLockTable() {
        List<Pair<Integer, Integer>> list = new LinkedList<>();
        controller.getLockTable().getContent().forEach((key, value) -> list.add(new Pair<>(key, value)));
        lockTableView.setItems(FXCollections.observableList(list));
    }

    private void populateProgramList() {
        List<String> list = new LinkedList<>();
        controller.getStates().forEach((el) -> list.add(String.valueOf(el.getId())));
        programIdentifiers.setItems(FXCollections.observableList(list));
    }

    private void setNumberOfStates() {
        int numberOfStates = controller.getStates().size();
        if (numberOfStates == 0) {
            numberOfPrgStates.setText("There are currently no program states");
        } else if (numberOfStates == 1) {
            numberOfPrgStates.setText("There is currently 1 program state");
        } else {
            numberOfPrgStates.setText("There are currently " + numberOfStates + " program states");
        }
    }

    private void populateFileTable() {
        List<String> list = new LinkedList<>();
        controller.getFileTable().getContent().forEach((key, value) -> list.add(key));
        fileTable.setItems(FXCollections.observableList(list));
    }

    private void populateOutputTable() {
        List<String> list = controller
                .getOutput()
                .getContent()
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        output.setItems(FXCollections.observableList(list));
    }

    private void populateHeapTable() {
        List<Pair<Integer, IValue>> list = new LinkedList<>();
        controller.getHeapTable().getContent().forEach((key, value) -> list.add(new Pair<>(key, value)));
        heap.setItems(FXCollections.observableList(list));
    }

    private void populateExecutionStack() {
        executionStackText.setText("Execution Stack for Program State : " + currentState);
        List<String> list = controller
                .getExecutionStack(currentState)
                .getContent()
                .stream()
                .map(Objects::toString)
                .collect(Collectors.toList());
        Collections.reverse(list);
        executionStack.setItems(FXCollections.observableList(list));
    }

    private void populateSymbolTable() {
        symbolTableText.setText("Symbol Table for Program State : " + currentState);
        List<Pair<String, IValue>> list = new LinkedList<>();
        controller.getSymbolTable(currentState)
                .getContent()
                .forEach((key, value) -> list.add(new Pair<>(key, value)));
        symbolTable.setItems(FXCollections.observableList(list));
    }

    @FXML
    private void runOneStep() {
        if (this.programStateList.size() > 0)
        {
            oneStep();
            setAllFields();
        }
        else
        {
            displayError("There are no instructions to be executed.");
            this.runButton.setDisable(true);
        }

    }


    @FXML
    private void initialize() {
        heap.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        symbolTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        addressHeap.setCellValueFactory(new PairKeyFactory<>());
        valueHeap.setCellValueFactory(new PairValueFactory<>());
        lockAddress.setCellValueFactory(new PairKeyFactory<>());
        lockValue.setCellValueFactory(new PairValueFactory<>());
        variableName.setCellValueFactory(new PairKeyFactory<>());
        variableValue.setCellValueFactory(new PairValueFactory<>());
        programIdentifiers.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            try {
                currentState = Integer.parseInt(t1);
            } catch (Exception ignored) {
                return;
            }
            if (currentState == 0) {
                return;
            }
            populateSymbolTable();
            populateExecutionStack();
        });
    }
}

class PairKeyFactory<T, E> implements Callback<TableColumn.CellDataFeatures<Pair<T, E>, T>, ObservableValue<T>> {
    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<Pair<T, E>, T> data) {
        return new ReadOnlyObjectWrapper<>(data.getValue().getKey());
    }
}

class PairValueFactory<T, E> implements Callback<TableColumn.CellDataFeatures<Pair<T, E>, E>, ObservableValue<E>> {
    @Override
    public ObservableValue<E> call(TableColumn.CellDataFeatures<Pair<T, E>, E> data) {
        E value = data.getValue().getValue();
        if (value instanceof ObservableValue) {
            return (ObservableValue<E>) value;
        } else {
            return new ReadOnlyObjectWrapper<>(value);
        }
    }
}



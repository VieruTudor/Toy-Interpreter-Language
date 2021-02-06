package Controller;

import Repository.IRepository;
import model.ProgramState;
import model.adt.*;
import model.adt.Stack;
import model.exceptions.InterpreterException;
import model.statement.IStatement;
import model.values.IValue;
import model.values.RefValue;

import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private final IRepository repository;
    private ExecutorService executor;
    private List<String> errorList;
    private Map<Integer, Boolean> alreadyCompleted;

    public Controller(IRepository _repository) {
        this.repository = _repository;
        this.errorList = new ArrayList<>();
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList) {
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<ProgramState> states) throws InterruptedException, InterpreterException {
        states = removeCompletedPrograms(states);
        states.forEach(
                program -> {
                    try {
                        repository.logProgramStateExecution(program);
                    } catch (InterpreterException e) {
                        System.out.println(e.getMessage());
                    }
                    
                }
        );
        List<Callable<ProgramState>> callList = states.stream()
                .map((ProgramState program) -> (Callable<ProgramState>) (program::oneStep))
                .collect(Collectors.toList());

        try{
            List<ProgramState> newProgramsList = executor.invokeAll(callList).stream()
                    .map(future -> {
                                try {
                                    return future.get();
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                    errorList.add(e.getMessage());
                                }
                                return null;
                            }

                    )
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            states.addAll(newProgramsList);
        }
        catch (InterruptedException e)
        {
            throw new InterpreterException(e.getMessage());
        }
        if (errorList.size() != 0){
            throw new InterpreterException(errorList.get(0));
        }
        states.forEach(
                program -> {
                    try {
                        repository.logProgramStateExecution(program);
                    } catch (InterpreterException e) {
                        System.out.println(e.getMessage());
                    }
                }
        );
        repository.setProgramsList(states);
    }


    public void allSteps() throws InterruptedException, InterpreterException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrograms(repository.getProgramsList());
        while (programStates.size() > 0) {
            conservativeGarbageCollector(programStates);
            oneStepForAllPrograms(programStates);
            programStates = removeCompletedPrograms(repository.getProgramsList());
        }
        executor.shutdownNow();
        repository.setProgramsList(programStates);
        System.out.println("-------------------");

    }

    public void conservativeGarbageCollector(List<ProgramState> programStates) {
        var heap = Objects.requireNonNull(programStates.stream()
                .map(program -> getAddrFromSymbolTable(
                        program.getSymTable().getContent().values(), program.getHeap().getContent().values()
                ))
                .map(Collection::stream)
                .reduce(Stream::concat).orElse(null).collect(Collectors.toList()));
        programStates.forEach(programState -> {
            programState.getHeap().setContent(
                    unsafeGarbageCollector(heap,
                            programStates.get(0).getHeap().getContent()
                    )
            );
        });

    }

    public Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symbolTableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symbolTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Set<Integer> getAddrFromSymbolTable(Collection<IValue> symbolTableValues, Collection<IValue> heap) {
        return Stream.concat(symbolTableValues.stream(), heap.stream())
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toSet());
    }

    public void typeCheck() throws InterpreterException {
        repository.getProgramsList().get(0).typeCheck();
    }

    public void openExecutor(){
        executor = Executors.newFixedThreadPool(2);
    }

    public List<ProgramState> getStates(){
        return this.repository.getProgramsList();
    }

    public void closeExecutor() {
        executor.shutdownNow();
    }

    public Stack<IStatement> getExecutionStack(int process){
        List<ProgramState> states = repository
                .getProgramsList()
                .stream()
                .filter((el) -> el.getId() == process)
                .collect(Collectors.toList());
        if (states.size() == 0)
            return new Stack<>();
        else
            return states.get(0).getExecutionStack();
    }

    public Dict<String, BufferedReader> getFileTable() {
        if (repository.getProgramsList().size() == 0) {
            return new Dict<>();
        }
        return repository.getProgramsList().get(0).getFileTable();
    }

    public Dict<String, IValue> getSymbolTable(int process) {
        List<ProgramState> states = repository.getProgramsList().stream().filter((el) -> el.getId() == process).collect(Collectors.toList());
        if (states.size() == 0) {
            return new Dict<>();
        } else {
            return states.get(0).getSymTable();
        }
    }

    public Heap getHeapTable() {
        if (repository.getProgramsList().size() == 0) {
            return new Heap();
        }
        return repository.getProgramsList().get(0).getHeap();
    }

    public MyList<IValue> getOutput() {
        if (repository.getProgramsList().size() == 0) {
            return new MyList<>();
        }
        return repository.getProgramsList().get(0).getOutput();
    }

    public void setFinalStateList(List<ProgramState> programStateList) {
        repository.setProgramsList(programStateList);
    }

    public LockTable<Integer> getLockTable() {
        return this.repository.getProgramsList().get(0).getLockTable();
    }
}

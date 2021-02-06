package Repository;

import model.ProgramState;

import model.exceptions.InterpreterException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStates;
    String logFilePath;

    public Repository() {
        this.programStates = new ArrayList<>();
    }

    public Repository(String _logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = _logFilePath;

    }

    @Override
    public void addProgramState(ProgramState programState) {
        this.programStates.add(programState);
    }

    @Override
    public ProgramState getProgramState() throws InterpreterException {
//        if (programStates.isEmpty())
//            throw new InterpreterException("There are no program states.");
//        return this.programStates.pop();
        return null;
    }

    @Override
    public void logProgramStateExecution(ProgramState state) throws InterpreterException {
        PrintWriter logFileWriter;
        try {
            FileWriter writer = new FileWriter(logFilePath, true);
            logFileWriter = new PrintWriter(new BufferedWriter(writer));
            logFileWriter.flush();
        } catch (IOException e) {
            throw new InterpreterException("Could not write to the specified path.");
        }
        logFileWriter.println(state);
        logFileWriter.close();
    }

    @Override
    public void setProgramsList(List<ProgramState> states) {
        this.programStates = states;
    }

    public List<ProgramState> getProgramsList(){
        return programStates;
    }


}

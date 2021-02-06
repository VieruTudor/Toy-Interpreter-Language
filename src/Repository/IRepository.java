package Repository;

import model.ProgramState;
import model.exceptions.InterpreterException;

import java.util.List;

public interface IRepository {
    void addProgramState(ProgramState programState);
    ProgramState getProgramState() throws InterpreterException;
    void logProgramStateExecution(ProgramState state) throws InterpreterException;
    void setProgramsList(List<ProgramState> states);

    List<ProgramState> getProgramsList();
}

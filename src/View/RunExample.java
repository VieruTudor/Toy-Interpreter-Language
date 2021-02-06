package View;

import Controller.Controller;
import model.exceptions.InterpreterException;

public class RunExample extends Command {
    private final Controller controller;
    public RunExample(String _key, String _description, Controller _controller) {
        super(_key, _description);
        this.controller = _controller;
    }

    @Override
    public void execute() {
        try{
            controller.typeCheck();
            controller.allSteps();
        }
        catch (InterruptedException | InterpreterException e) {
            System.out.println(e.getMessage());
        }
    }
}

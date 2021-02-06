package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class TextMenu {
    private final Map<String, Command> commands;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }

    private void printMenu() {
        for (Command comm : this.commands.values()) {
            String line = String.format("%4s: %s", comm.getKey(), comm.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            Command comm = commands.get(key);
            if (comm == null)
            {
                System.out.println("Invalid Option");
                continue;
            }
            comm.execute();
        }
    }

}

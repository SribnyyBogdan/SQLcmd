package controller.command;

/**
 * Created by Богдан on 07.02.2016.
 */
public class FindCommand implements Command {
    private CommandStore commandStore;


    public FindCommand(CommandStore commandStore){

        this.commandStore = commandStore;
    }

    @Override
    public void execute() {
        commandStore.find();

    }
}

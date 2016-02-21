package controller.command;

/**
 * Created by Богдан on 15.02.2016.
 */
public class ClearCommand implements Command {
    private CommandStore commandStore;

    public ClearCommand(CommandStore commandStore){
        this.commandStore = commandStore;
    }

    @Override
    public void execute() {
        commandStore.clear();
    }
}

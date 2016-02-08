package controller.command;

/**
 * Created by Богдан on 08.02.2016.
 */
public class ConnectCommand implements Command {
    private CommandStore commandStore;

    public ConnectCommand(CommandStore commandStore){

        this.commandStore = commandStore;
    }

    @Override
    public void execute() {
        commandStore.connect();

    }
}

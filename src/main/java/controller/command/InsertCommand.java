package controller.command;

/**
 * Created by Богдан on 15.02.2016.
 */
public class InsertCommand implements Command {
    private CommandStore commandStore;

    public InsertCommand(CommandStore commandStore){
        this.commandStore = commandStore;
    }


    @Override
    public void execute() {
        commandStore.insert();
    }
}

package controller.command;

import controller.Controller;

/**
 * Created by Богдан on 12.01.2016.
 */
public class QuitCommand implements Command {
    private CommandStore commandStore;

    public QuitCommand(CommandStore commandStore){
        this.commandStore = commandStore;
    }

    @Override
    public void execute() {
        commandStore.quit();
    }
}

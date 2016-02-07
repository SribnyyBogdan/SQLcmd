package controller.command;

import controller.Controller;

/**
 * Created by Богдан on 12.01.2016.
 */
public class HelpCommand implements Command {
    private CommandStore commandStore;

    public HelpCommand(CommandStore commandStore){
        this.commandStore = commandStore;
    }

    @Override
    public void execute() {
        commandStore.help();

    }
}

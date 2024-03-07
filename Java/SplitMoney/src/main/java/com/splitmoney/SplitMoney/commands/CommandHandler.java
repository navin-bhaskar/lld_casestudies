package com.splitmoney.splitmoney.commands;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandHandler {
    private String helpStr = "";

    private final List<Command> commands;
    public CommandHandler(List<Command> commands) {
        this.commands = commands;
        for (Command cmd: commands) {
            helpStr = helpStr.concat(cmd.help());
            helpStr = helpStr.concat("\n");
        }
    }

    public void executeCmdStr(String cmdStr) {
        boolean cmdExecuted = false;
        for(Command cmd: commands) {
            if(cmd.check(cmdStr)) {
                cmd.execute(cmdStr);
                cmdExecuted = true;
            }
        }
        if(!cmdExecuted){
            System.out.println("Unknown command or the arguments were unexpected");
        }
    }

    public String getHelp() {
        return helpStr;
    }
}

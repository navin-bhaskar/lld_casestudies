package services.CommandHandler.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {
    final private List<Command> commands;
    private String helpStr = "";

    public CommandHandler() {
        commands = new ArrayList<>();
        commands.add(new Register());
        commands.add(new UpdateProfile());
        commands.add(new AddGroup());

        for(Command cmd: commands) {
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

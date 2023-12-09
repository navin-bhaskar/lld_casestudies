package services.CommandHandler.commands;


interface Command {

    boolean check(String cmdStr);
    void execute(String cmdStr);

    String help();
}

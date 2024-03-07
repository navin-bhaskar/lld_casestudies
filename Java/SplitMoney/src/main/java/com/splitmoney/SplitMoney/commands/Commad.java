package com.splitmoney.splitmoney.commands;


interface Command {

    boolean check(String cmdStr);
    void execute(String cmdStr);

    String help();
}

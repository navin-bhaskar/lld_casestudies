package com.splitmoney.splitmoney;

import com.splitmoney.splitmoney.commands.CommandHandler;
import com.splitmoney.splitmoney.services.ExpenseService;
import com.splitmoney.splitmoney.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
@EntityScan(basePackages = "com.splitmoney.splitmoney.models")
@EnableJpaRepositories(basePackages = "com.splitmoney.splitmoney.repositories")
@ComponentScan(basePackages = "com.splitmoney.splitmoney.commands")
@EnableJpaAuditing
public class SplitMoneyApplication implements CommandLineRunner {
	private final CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(SplitMoneyApplication.class, args);
	}

	public SplitMoneyApplication(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}

	@Override
	public void run(String... args) throws Exception {

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Enter a command, h for help and q to quit: ");
			String cmd = sc.nextLine();
			if(cmd.equalsIgnoreCase("q")) {
				break;
			} else if (cmd.equalsIgnoreCase("h")) {
				System.out.println(commandHandler.getHelp());
			} else {
				commandHandler.executeCmdStr(cmd);
			}
		}

	}


}

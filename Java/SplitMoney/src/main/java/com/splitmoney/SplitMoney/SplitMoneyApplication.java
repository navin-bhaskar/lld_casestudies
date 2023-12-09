package com.splitmoney.SplitMoney;

import org.aspectj.runtime.reflect.Factory;
import services.CommandHandler.commands.CommandHandler;
import services.ExpenseHandler;
import models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class SplitMoneyApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SplitMoneyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CommandHandler cmdHandler = new CommandHandler();
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Enter a command, h for help and q to quit: ");
			String cmd = sc.nextLine();
			if(cmd.equalsIgnoreCase("q")) {
				break;
			} else if (cmd.equalsIgnoreCase("h")) {
				System.out.println(cmdHandler.getHelp());
			} else {
				cmdHandler.executeCmdStr(cmd);
			}
		}

	}

	// TODO: Test code for transaction, move it to test class
	public void test_transaction() {
		Map<String, User> users = new HashMap<>();

		Scanner sc = new Scanner(System.in);
		System.out.println("Add users");
		System.out.println("q to exit");

		boolean acceptingUsers = true;
		int cnt=1;
		while (acceptingUsers) {
			String userName = sc.next();
			if(userName.equals("q") || userName.equals("Q")) {
				acceptingUsers = false;
				continue;
			}
			if(users.containsKey(userName)) {
				System.out.println("User with given name already exists");
			} else {
				User usr = new User();
				String genUsrName = "u" + cnt;
				usr.setName(userName);
				users.put(genUsrName, usr);
				cnt += 1;
			}
		}

		for(String u: users.keySet()) {
			System.out.println("Name: " + users.get(u).getName() + " Alias: " + u);
		}

		ExpenseHandler expHandler = new ExpenseHandler(users);

		System.out.println("How many transactions do you wish to enter? ");
		int entries = sc.nextInt();

		for(int i=0; i<entries; i++) {
			System.out.println("How many people paid?");
			int paid = sc.nextInt();

			for(int j=0; j<paid; j++) {
				System.out.println("Enter alias of owed to: ");
				String owedTo = sc.next();
				int amt;
				System.out.println("Enter amount: ");
				amt = sc.nextInt();
				expHandler.addExpense(owedTo, amt);
			}

			System.out.println("How many people owed this money");
			int n = sc.nextInt();
			for(int k=0; k<n; k++) {
				System.out.println("Enter alias of owed by: ");
				String owedBy = sc.next();
				System.out.println("Enter amount: ");
				int amt = sc.nextInt();
				expHandler.addExpense(owedBy, -amt);
			}
		}
		expHandler.generateTransaction();
	}

}

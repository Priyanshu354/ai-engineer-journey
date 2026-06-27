package com.priyanshu.Smart_CLI_Q.A.Bot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.plaf.synth.SynthTreeUI;
import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final ChatService chatService;
    private final ConversationHistory conversationHistory;

    public Application(ChatService chatService, ConversationHistory conversationHistory) {
        this.chatService = chatService;
        this.conversationHistory = conversationHistory;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("HI, WELCOME TO MY CHAT BOT ( PRIYANSHU RAJ ) ");

        while(true) {

            System.out.println("""
                Choose a Persona:
                
                1. DSA
                2. Java
                3. System Design
                4. Interview
                
                Enter your choice:
              """);

            String personaChoice = scanner.nextLine();

            System.out.println("Write Prompt: ");
            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("exit")){
                System.out.println("Good Bye...");
                break;
            }

            String response = chatService.chat(input, personaChoice);
            Node newNode = new Node(input, response);
            conversationHistory.insert(newNode);

            // System.out.println("response is here " + response);
            System.out.println();
        }

        scanner.close();
    }
}

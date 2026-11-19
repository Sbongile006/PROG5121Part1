package com.mycompany.prog5121part1;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/** Main console application for the QuickChat project. */
public class PROG5121Part1 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Hi, Please enter your first name");
        String firstName = input.nextLine();
        System.out.println("Please enter your last name");
        String lastName = input.nextLine();
        System.out.println("Please enter your username");
        String username = input.nextLine();
        System.out.println("Please enter your password");
        String password = input.nextLine();
        System.out.println("Please enter your South African cell phone number: ");
        String cellPhoneNumber = input.nextLine();

        Login login = new Login(username, password, cellPhoneNumber, firstName, lastName);
        System.out.println(login.registerUser());

        System.out.print("Enter your username to login: ");
        String loginUsername = input.nextLine();
        System.out.print("Enter your password to login: ");
        String loginPassword = input.nextLine();
        String loginMessage = login.returnLoginStatus(loginUsername, loginPassword);
        System.out.println(loginMessage);

        if (loginMessage.startsWith("Welcome")) {
            System.out.println("Welcome to QuickChat.");
            System.out.print("How many messages would you like to enter? ");
            int numberOfMessages = readPositiveInteger(input);

            Task task = new Task(Math.max(100, numberOfMessages + 10));
            try {
                task.readStoredMessages("messages.json");
            } catch (IOException exception) {
                System.out.println("Could not read stored messages: " + exception.getMessage());
            }

            int choice;
            do {
                System.out.println("\nWelcome to QuickChat.");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.println("4) Stored Messages");
                System.out.print("Choose an option: ");
                choice = readInteger(input);

                switch (choice) {
                    case 1 -> sendMessages(input, numberOfMessages, task);
                    case 2 -> displaySentMessages(task);
                    case 3 -> System.out.println("Goodbye!");
                    case 4 -> storedMessagesMenu(input, task, cellPhoneNumber);
                    default -> System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
                }
            } while (choice != 3);
        }
        input.close();
    }

    private static void sendMessages(Scanner input, int numberOfMessages, Task task) {
        for (int i = 0; i < numberOfMessages; i++) {
            System.out.print("Enter recipient cell number: ");
            String recipient = input.nextLine();
            System.out.print("Enter your message: ");
            String messageText = input.nextLine();

            Message message = new Message(recipient, messageText, i + 1);
            System.out.println(message.checkRecipientCell());
            System.out.println(message.checkMessageLength());

            if (message.checkRecipientCell().equals("Cell phone number successfully captured.")
                    && message.checkMessageLength().equals("Message ready to send.")) {
                System.out.println("Message ID generated: " + message.getMessageID());
                System.out.println("Message Hash: " + message.createMessageHash());
                System.out.println("Choose: 1) Send  2) Disregard  3) Store");
                int messageChoice = readInteger(input);

                if (messageChoice == 1) {
                    message.SentMessage(1);
                    task.addSentMessage(message);
                    System.out.println("Message successfully sent.");
                } else if (messageChoice == 2) {
                    task.addDisregardedMessage(message);
                    System.out.println("Message disregarded.");
                } else if (messageChoice == 3) {
                    message.storeMessage();
                    task.addStoredMessage(message);
                    System.out.println("Message successfully stored.");
                } else {
                    System.out.println("Invalid message option.");
                }
            }
        }
    }

    private static void displaySentMessages(Task task) {
        if (task.getSentCount() == 0) {
            System.out.println("No sent messages available.");
            return;
        }
        for (Message message : task.getSentMessages()) {
            if (message != null) {
                System.out.println("Message ID: " + message.getMessageID());
                System.out.println("Recipient: " + message.getRecipient());
                System.out.println("Message: " + message.getMessage());
            }
        }
    }

    private static void storedMessagesMenu(Scanner input, Task task, String sender) {
        int option;
        do {
            System.out.println("\nSTORED MESSAGES");
            System.out.println("1) Display sender and recipient of all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search messages by recipient");
            System.out.println("5) Delete a message using its hash");
            System.out.println("6) Display a full stored-message report");
            System.out.println("7) Return to main menu");
            System.out.print("Choose an option: ");
            option = readInteger(input);

            switch (option) {
                case 1 -> System.out.println(task.displayStoredSendersAndRecipients(sender));
                case 2 -> {
                    Message longest = task.getLongestStoredMessage();
                    if (longest == null) System.out.println("No stored messages found.");
                    else System.out.println("Longest stored message: " + longest.getMessage());
                }
                case 3 -> {
                    System.out.print("Enter message ID: ");
                    Message found = task.searchStoredMessageByID(input.nextLine());
                    if (found == null) System.out.println("Message not found.");
                    else System.out.println("Recipient: " + found.getRecipient() + "\nMessage: " + found.getMessage());
                }
                case 4 -> {
                    System.out.print("Enter recipient number: ");
                    List<Message> matches = task.searchStoredMessagesByRecipient(input.nextLine());
                    if (matches.isEmpty()) System.out.println("No messages found for this recipient.");
                    for (Message message : matches) System.out.println(message.getMessage());
                }
                case 5 -> {
                    System.out.print("Enter message hash: ");
                    boolean deleted = task.deleteStoredMessageByHash(input.nextLine());
                    System.out.println(deleted ? "Message successfully deleted." : "Message hash not found.");
                }
                case 6 -> System.out.println(task.displayStoredMessageReport());
                case 7 -> System.out.println("Returning to main menu.");
                default -> System.out.println("Invalid option. Please choose 1 to 7.");
            }
        } while (option != 7);
    }

    private static int readPositiveInteger(Scanner input) {
        int value = readInteger(input);
        while (value < 1) {
            System.out.print("Please enter a number greater than zero: ");
            value = readInteger(input);
        }
        return value;
    }

    private static int readInteger(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            input.nextLine();
        }
        int value = input.nextInt();
        input.nextLine();
        return value;
    }
}

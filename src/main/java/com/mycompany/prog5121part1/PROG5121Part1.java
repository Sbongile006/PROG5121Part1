 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prog5121part1;
import java.util.Scanner;
/**
 *
 * @author Student
 */
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

        
        Login login = new Login(username,password,cellPhoneNumber,firstName,lastName);
        
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
    int numberOfMessages = input.nextInt();
    input.nextLine();

    int choice;

do {
    System.out.println("\nWelcome to QuickChat.");
    System.out.println("1) Send Messages");
    System.out.println("2) Show recently sent messages");
    System.out.println("3) Quit");
    System.out.print("Choose an option: ");

    choice = input.nextInt();
    input.nextLine();

    switch (choice) {
        case 1:
    for (int i = 0; i < numberOfMessages; i++) {
        System.out.print("Enter recipient cell number: ");
        String recipient = input.nextLine();

        System.out.print("Enter your message: ");
        String messageText = input.nextLine();

        Message msg = new Message(recipient, messageText, i + 1);

        System.out.println(msg.checkRecipientCell());
        System.out.println(msg.checkMessageLength());

        if (msg.checkRecipientCell().equals(
                "Cell phone number successfully captured.")
                && msg.checkMessageLength().equals("Message ready to send.")) {

            System.out.println("Message ID generated: " + msg.getMessageID());
            System.out.println("Message Hash: " + msg.createMessageHash());

            System.out.println("Choose: 1) Send  2) Disregard  3) Store");
            int messageChoice = input.nextInt();
            input.nextLine();

            if (messageChoice == 1) {
                System.out.println(msg.SentMessage(1));
            } else if (messageChoice == 2) {
                System.out.println("Message disregarded.");
            } else if (messageChoice == 3) {
                msg.storeMessage();
                System.out.println(msg.SentMessage(3));
            }
        }
    }
    break;
        case 2:
            System.out.println("Coming Soon.");
            break;
        case 3:
            System.out.println("Goodbye!");
            break;
        default:
            System.out.println("Invalid option. Please choose 1, 2, or 3.");
    }
} while (choice != 3);

input.close();
}
        
        
        
    }
    
    
}

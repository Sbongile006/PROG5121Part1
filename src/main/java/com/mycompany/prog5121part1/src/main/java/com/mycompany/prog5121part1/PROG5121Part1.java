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
        input.close();
        
        
        
    }
    
    
}

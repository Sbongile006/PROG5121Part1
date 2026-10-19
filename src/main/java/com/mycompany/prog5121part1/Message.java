/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121part1;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author Student
 */
public class Message {
    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String message;
    private String messageHash;

    private static int totalMessagesSent = 0;

    public Message() {
        this.messageID = String.format("%010d",
                new Random().nextInt(1000000000));
    }
    public Message(String recipient, String message, int numMessagesSent) {
    this();
    this.recipient = recipient;
    this.message = message;
    this.numMessagesSent = numMessagesSent;
}
    public boolean checkMessageID() {
    return messageID != null && messageID.length() <= 10;
}
    public String checkRecipientCell() {
    if (recipient != null
            && recipient.matches("^\\+\\d{1,3}\\d{1,10}$")
            && recipient.length() <= 13) {
        return "Cell phone number successfully captured.";
    } else {
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
}
    public String createMessageHash() {
    String firstTwoDigits = messageID.substring(0, 2);
    String[] words = message.trim().split("\\s+");

    String firstWord = words[0];
    String lastWord = words[words.length - 1];

    messageHash = (firstTwoDigits + ":"
            + numMessagesSent + ":"
            + firstWord + lastWord).toUpperCase();

    return messageHash;
}
    public String checkMessageLength() {
    if (message != null && message.length() <= 250) {
        return "Message ready to send.";
    } else {
        int length = (message == null) ? 0 : message.length();
        int excess = length - 250;

        return "Message exceeds 250 characters by " 
                + excess + "; please reduce the size.";
    }
}
    public String SentMessage(int choice) {
    switch (choice) {
        case 1:
            totalMessagesSent++;
            return "Message successfully sent.";
        case 2:
            return "Press 0 to delete the message.";
        case 3:
            return "Message successfully stored.";
        default:
            return "Invalid option.";
    }
}
    public int returnTotalMessages() {
    return totalMessagesSent;
}
    public String printMessages() {
    return "Messages sent: " + totalMessagesSent;
}
    public String getMessageID() {
    return messageID;
}

public String getRecipient() {
    return recipient;
}

public String getMessage() {
    return message;
}

public String getMessageHash() {
    return messageHash;
}
public void storeMessage() {
    try {
        java.io.File file = new java.io.File("messages.json");
        String existing = "";

        if (file.exists()) {
            java.util.Scanner scanner = new java.util.Scanner(file);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                existing = scanner.next();
            }
            scanner.close();
        }

        existing = existing.trim();
        String entry = "  {\n"
                + "    \"messageID\": \"" + messageID + "\",\n"
                + "    \"messageHash\": \"" + messageHash + "\",\n"
                + "    \"recipient\": \"" + recipient + "\",\n"
                + "    \"message\": \"" + message.replace("\\", "\\\\")
                        .replace("\"", "\\\"").replace("\n", "\\n") + "\"\n"
                + "  }";

        if (existing.isEmpty() || existing.equals("[]")) {
            existing = "[\n" + entry + "\n]";
        } else {
            existing = existing.substring(0, existing.lastIndexOf("]"))
                    .trim();
            if (existing.endsWith("}")) {
                existing += ",\n" + entry + "\n]";
            } else {
                existing = "[\n" + entry + "\n]";
            }
        }

        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(existing);
        }

    } catch (IOException e) {
        System.out.println("Error storing message: " + e.getMessage());
    }
}
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121part1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Student
 */
public class MessageTest {
    @Test
public void testCheckMessageLength() {
    Message message = new Message(
        "+27718693002",
        "Hi Mike, can you join us for dinner tonight?",
        1
    );

    assertEquals("Message ready to send.",
                 message.checkMessageLength());
}
@Test
public void testMessageTooLong() {
    Message message = new Message(
        "+27718693002",
        "a".repeat(251),
        1
    );

    assertEquals("Message exceeds 250 characters by 1; please reduce the size.",
                 message.checkMessageLength());
}
@Test
public void testCheckRecipientCell() {
    Message message = new Message(
        "+27718693002",
        "Hi Mike, can you join us for dinner tonight?",
        1
    );

    assertEquals("Cell phone number successfully captured.",
                 message.checkRecipientCell());
}
@Test
public void testCreateMessageHash() {
    Message message = new Message(
        "+27718693002",
        "Hi Mike, can you join us for dinner tonight?",
        1
    );

    String hash = message.createMessageHash();

    assertTrue(hash.endsWith("1:HITONIGHT?"));
}
@Test
public void testSendMessage() {
    Message message = new Message(
        "+27718693002",
        "Hi Mike, can you join us for dinner tonight?",
        1
    );

    assertEquals("Message successfully sent.",
            message.SentMessage(1));
}
@Test
public void testDiscardMessage() {
    Message message = new Message(
        "+27718693002",
        "Hi Mike, can you join us for dinner tonight?",
        1
    );

    assertEquals("Press 0 to delete the message.",
            message.SentMessage(2));
}
@Test
public void testStoreMessage() {
    Message message = new Message(
        "+27718693002",
        "Hi Mike, can you join us for dinner tonight?",
        1
    );

    assertEquals("Message successfully stored.",
            message.SentMessage(3));
}
@Test
public void testStoreTwoMessages() throws Exception {
    java.io.File file = new java.io.File("messages.json");

    try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
        writer.write("[]");
    }

    Message mike = new Message(
        "+27718693002",
        "Hi Mike, can you join us for dinner tonight?",
        1
    );

    mike.createMessageHash();
    mike.storeMessage();

    Message keegan = new Message(
        "08575975889",
        "Hi Keegan, did you receive the payment?",
        2
    );

    keegan.createMessageHash();
    keegan.storeMessage();

    java.util.Scanner scanner = new java.util.Scanner(file);
    scanner.useDelimiter("\\A");
    String content = scanner.hasNext() ? scanner.next() : "";
    scanner.close();

    assertTrue(content.startsWith("["));
    assertTrue(content.endsWith("]"));
    assertTrue(content.contains("+27718693002"));
    assertTrue(content.contains("Hi Mike, can you join us for dinner tonight?"));
    assertTrue(content.contains("08575975889"));
    assertTrue(content.contains("Hi Keegan, did you receive the payment?"));
}
}

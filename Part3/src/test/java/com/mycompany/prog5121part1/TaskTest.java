package com.mycompany.prog5121part1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for the Part 3 message-array and stored-message functions. */
public class TaskTest {

    private Message createMessage(String id, String hash, String recipient,
                                  String text, int number) {
        Message message = new Message(recipient, text, number);
        message.setMessageID(id);
        message.setMessageHash(hash);
        return message;
    }

    private Task createTaskWithTestMessages() {
        Task task = new Task(10);
        task.addSentMessage(createMessage("0000000001", "00:1:DIDCAKE?",
                "+27834557896", "Did you get the cake?", 1));
        task.addStoredMessage(createMessage("0000000002", "00:2:WHEREON TIME",
                "+27838884567", "Where are you? You are late! I have asked you to be on time.", 2));
        task.addDisregardedMessage(createMessage("0000000003", "00:3:YOHOOGATE.",
                "+27834484567", "Yohoooo, I am at your gate.", 3));
        task.addSentMessage(createMessage("0000000004", "00:4:ITTIME!",
                "0838884567", "It is dinner time!", 4));
        task.addStoredMessage(createMessage("0000000005", "00:5:OKYOU.",
                "+27838884567", "Ok, I am leaving without you.", 5));
        return task;
    }

    @Test
    public void testMessageArraysArePopulated() {
        Task task = createTaskWithTestMessages();
        assertEquals(2, task.getSentCount());
        assertEquals(1, task.getDisregardedCount());
        assertEquals(2, task.getStoredCount());
        assertEquals("Did you get the cake?", task.getSentMessages()[0].getMessage());
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                task.getStoredMessages()[0].getMessage());
    }

    @Test
    public void testDisplayLongestStoredMessage() {
        Task task = createTaskWithTestMessages();
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                task.getLongestStoredMessage().getMessage());
    }

    @Test
    public void testSearchStoredMessageByID() {
        Task task = createTaskWithTestMessages();
        // The supplied assessment test data identifies message 4 for this search.
        Message result = task.searchStoredMessageByID("0000000004");
        assertNotNull(result);
        assertEquals("0838884567", result.getRecipient());
        assertEquals("It is dinner time!", result.getMessage());
    }

    @Test
    public void testSearchStoredMessagesByRecipient() {
        Task task = createTaskWithTestMessages();
        List<Message> results = task.searchStoredMessagesByRecipient("+27838884567");
        assertEquals(2, results.size());
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                results.get(0).getMessage());
        assertEquals("Ok, I am leaving without you.", results.get(1).getMessage());
    }

    @Test
    public void testDeleteStoredMessageUsingHash() {
        Task task = createTaskWithTestMessages();
        assertTrue(task.deleteStoredMessageByHash("00:2:WHEREON TIME"));
        assertEquals(1, task.getStoredCount());
        assertEquals("Ok, I am leaving without you.", task.getStoredMessages()[0].getMessage());
        assertFalse(task.deleteStoredMessageByHash("not-a-real-hash"));
    }

    @Test
    public void testDisplayStoredMessageReport() {
        Task task = createTaskWithTestMessages();
        String report = task.displayStoredMessageReport();
        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient: +27838884567"));
        assertTrue(report.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(report.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testReadJSONFileIntoStoredMessagesArray() throws Exception {
        Path jsonFile = Files.createTempFile("quickchat-test", ".json");
        Files.writeString(jsonFile, "[\n"
                + " {\"messageID\":\"1234567890\",\"messageHash\":\"12:1:HELLOTHERE\","
                + "\"recipient\":\"+27838884567\",\"message\":\"Hello there\"},\n"
                + " {\"messageID\":\"0987654321\",\"messageHash\":\"09:2:BYEFORNOW\","
                + "\"recipient\":\"+27834557896\",\"message\":\"Bye for now\"}\n]");
        Task task = new Task(5);
        assertEquals(2, task.readStoredMessages(jsonFile.toString()));
        assertEquals("Hello there", task.getStoredMessages()[0].getMessage());
        assertEquals("Bye for now", task.getStoredMessages()[1].getMessage());
        Files.deleteIfExists(jsonFile);
    }
}

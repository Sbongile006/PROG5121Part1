package com.mycompany.prog5121part1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manages the message arrays and the stored-message operations required by
 * the PROG5121 Part 3 task.
 */
public class Task {

    private final Message[] sentMessages;
    private final Message[] disregardedMessages;
    private final Message[] storedMessages;
    private final String[] messageHashes;
    private final String[] messageIDs;

    private int sentCount;
    private int disregardedCount;
    private int storedCount;
    private int hashCount;
    private int idCount;

    public Task(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Array capacity must be at least 1.");
        }
        sentMessages = new Message[capacity];
        disregardedMessages = new Message[capacity];
        storedMessages = new Message[capacity];
        messageHashes = new String[capacity];
        messageIDs = new String[capacity];
    }

    public boolean addSentMessage(Message message) {
        if (message == null || sentCount == sentMessages.length) return false;
        sentMessages[sentCount++] = message;
        addIdentifiers(message);
        return true;
    }

    public boolean addDisregardedMessage(Message message) {
        if (message == null || disregardedCount == disregardedMessages.length) return false;
        disregardedMessages[disregardedCount++] = message;
        addIdentifiers(message);
        return true;
    }

    public boolean addStoredMessage(Message message) {
        if (message == null || storedCount == storedMessages.length) return false;
        storedMessages[storedCount++] = message;
        addIdentifiers(message);
        return true;
    }

    private void addIdentifiers(Message message) {
        if (message.getMessageHash() != null && hashCount < messageHashes.length) {
            messageHashes[hashCount++] = message.getMessageHash();
        }
        if (message.getMessageID() != null && idCount < messageIDs.length) {
            messageIDs[idCount++] = message.getMessageID();
        }
    }

    /** Reads message objects from the project's messages.json file. */
    public int readStoredMessages(String fileName) throws IOException {
        String json = Files.readString(Path.of(fileName), StandardCharsets.UTF_8);
        List<Message> loaded = parseMessages(json);
        storedCount = 0;
        hashCount = 0;
        idCount = 0;
        for (Message message : loaded) {
            if (storedCount == storedMessages.length) break;
            storedMessages[storedCount++] = message;
            addIdentifiers(message);
        }
        return storedCount;
    }

    private List<Message> parseMessages(String json) {
        List<Message> messages = new ArrayList<>();
        Pattern objectPattern = Pattern.compile("\\{([^{}]*)\\}");
        Matcher objectMatcher = objectPattern.matcher(json);
        while (objectMatcher.find()) {
            String object = objectMatcher.group(1);
            String id = readJsonValue(object, "messageID");
            String hash = readJsonValue(object, "messageHash");
            String recipient = readJsonValue(object, "recipient");
            String text = readJsonValue(object, "message");
            if (recipient != null && text != null) {
                Message message = new Message(recipient, text, messages.size() + 1);
                message.setMessageID(id);
                message.setMessageHash(hash);
                messages.add(message);
            }
        }
        return messages;
    }

    private String readJsonValue(String object, String key) {
        Pattern valuePattern = Pattern.compile("\\\"" + Pattern.quote(key)
                + "\\\"\\s*:\\s*\\\"((?:\\\\.|[^\\\"\\\\])*)\\\"");
        Matcher matcher = valuePattern.matcher(object);
        if (!matcher.find()) return null;
        return matcher.group(1).replace("\\n", "\n")
                .replace("\\\"", "\"").replace("\\\\", "\\");
    }

    public String displayStoredSendersAndRecipients(String sender) {
        if (storedCount == 0) return "No stored messages found.";
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < storedCount; i++) {
            output.append("Sender: ").append(sender)
                    .append(" | Recipient: ").append(storedMessages[i].getRecipient())
                    .append(System.lineSeparator());
        }
        return output.toString().trim();
    }

    public Message getLongestStoredMessage() {
        if (storedCount == 0) return null;
        Message longest = storedMessages[0];
        for (int i = 1; i < storedCount; i++) {
            if (storedMessages[i].getMessage().length() > longest.getMessage().length()) {
                longest = storedMessages[i];
            }
        }
        return longest;
    }

    public Message searchStoredMessageByID(String id) {
        Message found = findMessageByID(sentMessages, sentCount, id);
        if (found != null) return found;
        found = findMessageByID(storedMessages, storedCount, id);
        if (found != null) return found;
        return findMessageByID(disregardedMessages, disregardedCount, id);
    }

    private Message findMessageByID(Message[] messages, int count, String id) {
        if (id == null) return null;
        String requestedID = id.trim();
        for (int i = 0; i < count; i++) {
            if (messages[i] != null && messages[i].getMessageID() != null
                    && messages[i].getMessageID().trim().equals(requestedID)) {
                return messages[i];
            }
        }
        return null;
    }

    /** Searches messages that were sent or stored, as required by the test brief. */
    public List<Message> searchStoredMessagesByRecipient(String recipient) {
        List<Message> matches = new ArrayList<>();
        addRecipientMatches(sentMessages, sentCount, recipient, matches);
        addRecipientMatches(storedMessages, storedCount, recipient, matches);
        return matches;
    }

    private void addRecipientMatches(Message[] messages, int count, String recipient,
                                     List<Message> matches) {
        for (int i = 0; i < count; i++) {
            if (messages[i].getRecipient().equals(recipient)) matches.add(messages[i]);
        }
    }

    public boolean deleteStoredMessageByHash(String hash) {
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].getMessageHash() != null
                    && storedMessages[i].getMessageHash().equalsIgnoreCase(hash)) {
                for (int j = i; j < storedCount - 1; j++) storedMessages[j] = storedMessages[j + 1];
                storedMessages[--storedCount] = null;
                rebuildIdentifiers();
                return true;
            }
        }
        return false;
    }

    private void rebuildIdentifiers() {
        hashCount = 0;
        idCount = 0;
        for (int i = 0; i < storedCount; i++) addIdentifiers(storedMessages[i]);
    }

    public String displayStoredMessageReport() {
        if (storedCount == 0) return "No stored messages found.";
        StringBuilder report = new StringBuilder("STORED MESSAGE REPORT\n");
        for (int i = 0; i < storedCount; i++) {
            Message message = storedMessages[i];
            report.append("Message Hash: ").append(message.getMessageHash()).append('\n')
                    .append("Recipient: ").append(message.getRecipient()).append('\n')
                    .append("Message: ").append(message.getMessage()).append("\n\n");
        }
        return report.toString().trim();
    }

    public Message[] getSentMessages() { return sentMessages.clone(); }
    public Message[] getDisregardedMessages() { return disregardedMessages.clone(); }
    public Message[] getStoredMessages() { return storedMessages.clone(); }
    public String[] getMessageHashes() { return messageHashes.clone(); }
    public String[] getMessageIDs() { return messageIDs.clone(); }
    public int getSentCount() { return sentCount; }
    public int getDisregardedCount() { return disregardedCount; }
    public int getStoredCount() { return storedCount; }
}

package memoranda.util.subscriber;

/**
 * Immutable Message to be sent between objects in the simulation
 */
public class Message {
    final String messageContent;

    public Message (String m) {
        this.messageContent = m;
    }

    public String getMessage() {
        return messageContent;
    }
}

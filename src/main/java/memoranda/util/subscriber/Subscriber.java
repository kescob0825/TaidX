package memoranda.util.subscriber;

import java.io.IOException;

/**
 * Interface for the Observer pattern.
 * This interface defines the contract for objects that want to be notified
 * when a message is received.
 */
public interface Subscriber {
    /**
     * Update the observer with a new message
     */
    void update() throws IOException;
}

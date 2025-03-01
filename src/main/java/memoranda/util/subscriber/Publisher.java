package memoranda.util.subscriber;

import java.io.IOException;

/**
 * Interface for the Observer pattern.
 * This interface defines the contract for objects that want to be notified
 * when a message is received.
 */
public interface Publisher {
    /**
     * Register an observer to the subject
     * @param subscriber The observer to be registered
     */
    void register(Subscriber subscriber);
    /**
     * Unregister an observer from the subject
     * @param subscriber The observer to be unregistered
     */
    void unregister(Subscriber subscriber);

    /**
     * Notify all subscribers of a change in the subject
     */
    void notifySubscribers() throws IOException;
}

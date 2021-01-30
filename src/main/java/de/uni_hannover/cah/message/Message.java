package de.uni_hannover.cah.message;

/**
 * Messages are sent between server and client to communicate
 */
public class Message<T> {
    public String type;
    public T payload;
    public String playerID;
    public Message(String type, T payload, String playerID) {
        this.type = type;
        this.payload = payload;
        this.playerID = playerID;
    }

    public static class IdPayload {
        public int id;

        public IdPayload(int id) {
            this.id = id;
        }
    }
}

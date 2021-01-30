package de.uni_hannover.cah.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.uni_hannover.cah.message.Message;
import de.uni_hannover.cah.observer.SimpleObservable;
import de.uni_hannover.cah.observer.SimpleObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
/**
 * class for every client who joins the game
 */
public class Client implements SimpleObservable {
    private Socket socket = null;
    private BufferedReader reader;
    private PrintWriter writer;
    private Thread listener;
    private boolean closed = false;
    private boolean connected = false;
    private  SimpleObserver observer;

    public Client(String host) {
        try {
            socket = new Socket(host, 5612);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) throws IOException {
        writer.println(message);
    }

    public boolean getConnected(){
        return this.connected;
    }

    public <T> void send(Message<T> message) throws IOException {
        Gson gson = new Gson();
        Type messageType = new TypeToken<Message<T>>() {}.getType();
        String json = gson.toJson(message, messageType);
        send(json);
    }

    public void close() throws IOException {
        synchronized (this) {
            closed = true;
        }
        reader.close();
        writer.close();
        socket.close();
        System.out.println("Socket geschlossen...");
    }

    @SuppressWarnings("unchecked")
    public void listen() {
        listener = new Thread(new Runnable() {
            public void run() {
                while (!socket.isClosed()) {
                    String line = null;
                    try {
                        synchronized (this) {
                            if (!closed && reader.ready()) {
                                line = reader.readLine();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (line != null) {
                        if(observer != null) observer.messageReceived(line);
                    }
                }
            }
        });
        listener.start();
    }

    public void setObserver(SimpleObserver observer) {
        this.observer = observer;
    }

    public void removeObserver() {
        this.observer = null;
    }

    public boolean isClosed() {
        return closed;
    }
}

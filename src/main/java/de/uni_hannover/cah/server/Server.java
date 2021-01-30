package de.uni_hannover.cah.server;

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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 * The game takes place on the server.
 * Every player(client) connects to the server to play.
 * The communication of the clients are managed by the server.
 */
public class Server extends Thread implements SimpleObservable {
    private class ClientSocket extends Thread {
        private Server owner;
        private Socket client;
        private PrintWriter writer;
        private BufferedReader reader;
        private boolean closed = false;

        public ClientSocket(Socket client, Server owner) {
            this.client = client;
            this.owner = owner;
        }


        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                writer = new PrintWriter(client.getOutputStream(), true);
                while (!client.isClosed()) {
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
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try{ close(true); }
                catch(Exception e) { ; }
            }
        }

        public void close(boolean removeFromList) throws IOException {
            synchronized (this) {
                closed = true;
            }
            reader.close();
            writer.close();
            client.close();
            synchronized (owner.clients) {
                if (removeFromList) {
                    owner.clients.remove(this);
                }
            }
        }

        public void send(String message) {
            writer.println(message);
        }
    }

    ArrayList<ClientSocket> clients  = new ArrayList<ClientSocket>();
    ServerSocket server = null;
    public SimpleObserver observer;
    public Server() {
        try {
            server = new ServerSocket(5612);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Socket client = null;
        while (true) {
            try {
                client = server.accept();
                if(clients.size() == 8){
                    client.close();
                    continue;
                }
                ClientSocket clientSocket = new ClientSocket(client, this);
                clients.add(clientSocket);
                clientSocket.start();
            } catch (Exception e) {
                try{ if(client != null) client.close();}
                catch(Exception _e) { _e.printStackTrace(); }
            }
        }
    }

    public void close() throws IOException {
        synchronized (clients) {
            for(ClientSocket c : clients) {
                c.close(false);
            }
        }
        clients.clear();
        server.close();
    }

    public void send(String message) throws IOException{
        for(ClientSocket c : clients) {
            c.send(message);
        }
    }

    public <T> void send(Message<T> message) throws IOException {
        Gson gson = new Gson();
        Type messageType = new TypeToken<Message<T>>() {}.getType();
        String json = gson.toJson(message, messageType);
        send(json);
    }

    public void setObserver(SimpleObserver observer) {
        this.observer = observer;
    }

    public void removeObserver() {
        this.observer = null;
    }
}

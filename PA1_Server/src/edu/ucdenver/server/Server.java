package edu.ucdenver.server;

import edu.ucdenver.tournament.Tournament;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * <p>This defines the Server class
 * which holds all the methods and attributes
 * of the Server. This class
 * is responsible for allowing hosts to connect
 * to it. When a connection is received, the server
 * is responsible in delegating the connecting client
 * to a clientWorker to handle all its requests.
 * </p>
 * @author Karim Kabbara
 * @version 1.0
 * @since 2022-11-11
 */
public class Server implements Runnable{

    /**
     * <p>
     *     Port number to
     *     host server on.
     * </p>
     */
    private final int port;

    /**
     * <p>
     *     Maximum number of connections
     * </p>
     */
    private final int backlog;

    /**
     * <p>
     *     Server socket
     *     to allow create server.
     * </p>
     */
    private final ServerSocket socketServer;

    /**
     * <p>
     *     List of all client workers
     *     running in parallel
     * </p>
     */
    private final ArrayList<ClientWorker> workers;

    /**
     * <p>
     *     If a shutdown
     *     request is received
     *     set to false.
     * </p>
     */
    private boolean keepServerRunning;

    /**
     * <p>
     *     To receive from the client
     * </p>
     */
    private BufferedReader input;

    /**
     * <p>
     *     Number of connections
     * </p>
     */
    private int connectionCounter;

    /**
     * <p>The tournament object that will be used</p>
     */
    public Tournament tournament;

    /**
     * <p>This is the constructor
     * for the server class. It sets the port,
     * backlog, creates an array list of workers
     * and creates a blank tournament to load into
     * itself when loadFromFile is called.
     * </p>
     * @param port      port number
     * @param backlog   backlog
     */
    public Server(int port, int backlog){
        this.port = port;
        this.backlog = backlog;
        this.workers = new ArrayList<>();
        this.keepServerRunning = true;
        // a blank tournament is needed because we need to instantiate the tournament before we can call the loadFromFile
        this.tournament = new Tournament("blankTournament", LocalDate.of(9999, 12, 30),
                LocalDate.of(9999, 12, 31));
        try {
            this.socketServer = new ServerSocket(this.port, this.backlog);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * <p>This method loops continuously
     * until a client connects, and it breaks.
     * When a client connects, the connection is set
     * and is returned
     * </p>
     * @return Client Socket
     */
    public Socket waitForConnection(){
        Socket connection = null;
        try{
            while (true){
                connection = this.socketServer.accept();
                if (connection != null){
                    this.connectionCounter++;
                    break;
                }
            }
        }
        catch (Exception ignored){
        }
        return connection;
    }

    /**
     * <p>This method runs the server.
     * It continuously calls waitForConnection()
     * and assigns any incoming connections to a
     * clientWorker. The server receives the first
     * message from the client telling the server
     * if it is an admin or not. Finally, the thread
     * is started and the server waits for new connections.
     * </p>
     */
    @Override
    public void run() {
        while (this.keepServerRunning){
            Socket client;
            client = waitForConnection();
            try {
                this.input = this.getInputStream(client);
            }
            catch (Exception ignored) {}
            ClientWorker worker = new ClientWorker(this, client);
            this.workers.add(worker);
            new Thread(worker).start();
        }
    }

    /**
     * <p>This method creates a new BufferedReader object</p>
     * @param s Client Socket
     * @return BufferedReader to receive from client
     * @throws IOException If socket is invalid
     */
    public BufferedReader getInputStream(Socket s) throws IOException {
        return new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    /**
     * <p>This method loops through all
     * existing client workers and shuts them down.
     * Then lastly, the serverSocket is shutdown.
     * </p>
     */
    public void shutdown(){
        for (int i = 0; i < this.connectionCounter; i ++){
            ClientWorker worker = this.workers.get(i);
            worker.keepRunningClient = false;
            worker.closeConnection(worker.getConnection(), worker.getInput(), worker.getOutput());
        }
        try {
            this.keepServerRunning = false;
            this.socketServer.close();
        }
        catch (Exception e){e.printStackTrace();}
    }
}

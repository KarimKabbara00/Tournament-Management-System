package edu.ucdenver.app;

import edu.ucdenver.server.Server;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>This defines the ServerApplication class
 * which holds all the methods and attributes
 * of the ServerApplication. This class
 * is responsible for loading, saving serialized
 * object files. It is also responsible for starting
 * and stopping the server.
 * </p>
 * @author David Desrochers
 * @version 1.0
 * @since 2022-11-11
 */
public class ServerApplication {

    /**
     * <p>
     *     Name of file to save to
     *     or load from.
     * </p>
     */
    private final String filename;

    /**
     * <p>
     *     Server object to be
     *     instantiated.
     * </p>
     */
    private Server server;

    /**
     * <p>
     *     True if server is started
     * </p>
     */
    private boolean isStarted;

    /**
     * <p>
     *     Executor service
     *     to run server in parallel
     * </p>
     */
    private final ExecutorService executorService;

    /**
     * <p>
     * This is the constructor
     * for the ServerApplication class
     * It sets the file name to serialize to,
     * sets the isStarted flag to false, and
     * creates instantiates an executorService.
     * </p>
     */
    public ServerApplication(){
        this.filename  = "./tournament.ser";
        this.isStarted = false;
        this.executorService  = Executors.newCachedThreadPool();
    }
    /**
     * <p>
     * This method instantiates the server
     * with a given port and backlog. The
     * executorService is assigned to execute
     * the server. This is to allow the continued
     * operation of the command line interface
     * after starting the server.
     * </p>
     */
    public void startServer(){
        this.server = new Server(9988, 5);
        this.executorService.execute(this.server);
        this.isStarted = true;
    }
    /**
     * <p>
     * This method shuts down the server
     * and sets isStarted to false.
     * </p>
     */
    public void shutDown(){
        this.server.shutdown();
        this.isStarted = false;
    }
    /**
     * <p>
     * This method calls the loadFromFile() method
     * in the tournament class and loads the saved
     * file if it exists. An exception is thrown otherwise.
     * </p>
     */
    public void loadFromFile(){
        this.server.tournament = this.server.tournament.loadFromFile(this.filename);
    }
    /**
     * <p>
     * This method calls the saveFromFile() method
     * in the tournament class and saves the current
     * tournament object.
     * </p>
     */
    public void saveToFile(){
        this.server.tournament.saveToFile(this.filename);
    }

    /**
     * <p>
     * Instantiates the server application and runs the program.
     * </p>
     * @param args A list of arguments if ran on the command line.
     */
    public static void main(String[] args) {
        String choice;
        boolean keepRunning = true;
        Scanner input = new Scanner(System.in);
        ServerApplication serverApplication = new ServerApplication();

        String optionLoad = "1. Load Previous State";
        String optionStart = "2. Start Server";
        String optionStop = "3. Stop Server";
        String optionSave = "4. Save to File";
        String optionExit = "5. Exit";

        while (keepRunning){
            System.out.println(optionLoad);
            System.out.println(optionStart);
            System.out.println(optionStop);
            System.out.println(optionSave);
            System.out.println(optionExit);

            System.out.print("Option: ");
            choice = input.next();

            if (choice.equals("1")){
                if (serverApplication.isStarted){
                    serverApplication.loadFromFile();
                }
                else {
                    System.err.println("Server must be started to load!");
                }
            }
            else if (choice.equals("2")) {
                if (!serverApplication.isStarted){
                    serverApplication.startServer();
                }
                else {
                    System.err.println("Server already running!");
                }
            }
            else if (choice.equals("3")) {
                if (serverApplication.isStarted){
                    serverApplication.shutDown();
                }
                else {
                    System.err.println("Server not started!");
                }
            }
            else if (choice.equals("4")) {
                if (!serverApplication.isStarted){
                    serverApplication.saveToFile();
                }
                else {
                    System.err.println("Shutdown the server first!");
                }
            }
            else if (choice.equals("5")){
                keepRunning = false;
            }
            else {
                System.err.println("Invalid Choice");
            }
            System.out.println("\n\n");
        }
        System.exit(0);
    }
}

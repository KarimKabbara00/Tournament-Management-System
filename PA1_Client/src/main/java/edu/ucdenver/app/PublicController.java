package edu.ucdenver.app;

import javafx.collections.FXCollections;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * <p>This defines the PublicController class
 * which holds all the methods and attributes
 * of the public application.
 * </p>
 * @author David Desrochers
 * @version 1.0
 * @since 2022-11-11
 */
public class PublicController {

    /**
     * <p>
     *     Server port to connect to
     * </p>
     */
    private final int serverPort;

    /**
     * <p>
     *     Server IP to connect to
     * </p>
     */
    private final String serverIp;

    /**
     * <p>
     *     Field for team name
     *     to get all matches
     *     for that team
     * </p>
     */
    public TextField allGamesTeamName;

    /**
     * <p>
     *     Drop down list for all
     *     matches in tournament
     * </p>
     */
    public ComboBox ddlMatchLineups;

    /**
     * <p>
     *     Socket to connect to server
     * </p>
     */
    private Socket serverConnection;

    /**
     * <p>
     *     To read from server
     * </p>
     */
    private BufferedReader input;

    /**
     * <p>
     *     To send to server
     * </p>
     */
    private PrintWriter output;

    /**
     * <p>
     *     State of admin.
     *     In this case,
     *     it is false
     * </p>
     */
    private final boolean isAdmin;

    /**
     * <p>
     *     List of all upcoming matches
     * </p>
     */
    public ListView upcomingMatches;

    /**
     * <p>
     *     Date picker for matches
     * </p>
     */
    public DatePicker dtFindMatches;

    /**
     * <p>
     * List of all matches on a date
     * </p>
     */
    public ListView dateListMatches;

    /**
     * <p>
     *All games for a team
     * </p>
     */
    public ListView gamesForTeam;

    /**
     * <p>
     *Tab for upcoming matches
     * </p>
     */
    public Tab tabListUpcoming;

    /**
     * <p>
     *Tab for list of all matches
     * on a date
     * </p>
     */
    public Tab tabListOfMatches;

    /**
     * <p>
     *Tab for matches for
     * a team
     * </p>
     */
    public Tab tabTeamGames;

    /**
     * <p>
     *Tab for lineups for a match
     * </p>
     */
    public Tab tabLineups;

    /**
     * <p>
     *List for lineup A for
     * a match
     * </p>
     */
    public ListView listALineups;

    /**
     * <p>
     *List for lineup B for
     * a match
     * </p>
     */
    public ListView listBLineups;

    /**
     * <p>This is the constructor for the PublicController Class.
     * It initializes the server port, server IP, and whether or not
     * it is an administrator.</p>
     */
    public PublicController(){
        this.serverPort = 9988;
        this.serverIp = "127.0.0.1";
        this.isAdmin = false;
    }

    /**
     * <p>This method initializes the socket, input and
     * output streams, and sends to the server the state
     * of isAdmin.</p>
     */
    public void initialize() {
        try{
            this.serverConnection = new Socket(this.serverIp, this.serverPort);
            this.input = this.getInputStream(this.serverConnection);          // receive from the server
            this.output = this.getOutputStream(this.serverConnection);        // send to the server
            this.output.println(this.isAdmin);
        }
        catch (IOException ioe){ioe.printStackTrace();}
    }
    /**
     * <p>This method creates a PrintWriter Object
     * to allow the application to send messages
     * to the server.</p>
     * @param s The application's socket
     * @return  A PrintWriter Object.
     * @throws IOException Invalid Socket
     */
    public PrintWriter getOutputStream(Socket s) throws IOException {
        return new PrintWriter(s.getOutputStream(), true);
    }
    /**
     * <p>This method creates a BufferedReader Object
     * to allow the application to receive messages
     * from the server.</p>
     * @param s The application's socket
     * @return  A BufferedReader Object.
     * @throws IOException Invalid Socket
     */
    public BufferedReader getInputStream(Socket s) throws IOException {
        return new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    /**
     * <p>This Method gets all upcoming matches
     * after the current date and time.</p>
     * @return A list of matches.
     */
    public String[] getUpcomingMatches() {
        String command = "Show Upcoming Matches|";
        this.output.println(command);
        String[] matchesList = new String[0];
        try {
            String s = this.input.readLine();
            matchesList = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return matchesList;
    }
    /**
     * <p>This method gets all matches on a specified
     * date. Time is not taken into account here.
     * Instead of returning the match list, this method
     * updates the listView directly.</p>
     */
    public void getMatchesOnDate() {
        String command = "Date List of Matches|" + this.dtFindMatches.getValue();
        this.output.println(command);
        String[] matchesList = new String[0];
        try {
            String s = this.input.readLine();
            matchesList = s.split("\\|");
            this.dateListMatches.setItems(FXCollections.observableArrayList(matchesList));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * <p>This method gets all matches for a specific
     * team. Instead of returning the match list, the
     * listView is updated directly. </p>
     */
    public void teamGames() {
        String command = "Team Matches|" + this.allGamesTeamName.getText();
        this.output.println(command);
        String[] matchesList = new String[0];
        try {
            String s = this.input.readLine();
            matchesList = s.split("\\|");
            this.gamesForTeam.setItems(FXCollections.observableArrayList(matchesList));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * <p>This method gets the lineups for a given
     * match and stores them in a 2D list. Each
     * sublist contains the lineup for a team.</p>
     * @return A 2D list containing match lineups.
     */
    public String[][] getTeamLineUps() { //if selected && team name == saved name, refresh and show list
        String command = "Team Lineups|" + this.ddlMatchLineups.getValue();
        this.output.println(command);
        String[] lineUps = new String[0];
        String[] lineUpA = new String[0];
        String[] lineUpB = new String[0];
        String[][] finalLineUps = new String[2][];
        try {
            String s = this.input.readLine();
            lineUps = s.split("\\$");
            if (lineUps.length > 0){
                lineUpA = lineUps[0].split("\\|");
            }
            if (lineUps.length > 1){
                lineUpB = lineUps[1].split("\\|");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        finalLineUps[0] = lineUpA;
        finalLineUps[1] = lineUpB;
        return finalLineUps;
    }
    /**
     * <p>This method gets all the matches in the tournament</p>
     * @return A list of all matches.
     */
    public String[] getAllMatches() {
        String command = "Get All Matches|";
        this.output.println(command);
        String[] matchesList = new String[0];
        try {
            String s = this.input.readLine();
            matchesList = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return matchesList;
    }

    /**
     * <p>This method is called when the Upcoming Matches
     * tab is selected. This method calls the getUpcomingMatches
     * method and updates the listView.</p>
     */
    public void upcomingMatchesUpdate() {
        if (this.tabListUpcoming.isSelected()){
            this.upcomingMatches.setItems(FXCollections.observableArrayList(this.getUpcomingMatches()));
        }
    }

    /**
     * <p>This method is called when the Match Lineups
     * tab is selected. This method calls the getAllMatches
     * method and updates the listView.</p>
     */
    public void updateMatchDDL(){
        if (this.tabLineups.isSelected()) {
            this.ddlMatchLineups.setItems(FXCollections.observableArrayList(this.getAllMatches()));
        }
    }

    /**
     * <p>This method is called when a match is selected
     * from the drop down list in the Match Lineups tab.
     * This method calls the getTeamLineUps method for
     * each team in the match and updates the lineups
     * in the listView.</p>
     */
    public void teamLineups(){
        this.listALineups.setItems(FXCollections.observableArrayList(this.getTeamLineUps()[0]));
        this.listBLineups.setItems(FXCollections.observableArrayList(this.getTeamLineUps()[1]));
    }
}
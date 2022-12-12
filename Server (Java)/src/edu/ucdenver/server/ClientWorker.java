package edu.ucdenver.server;

import edu.ucdenver.tournament.Tournament;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>This defines the ClientWorker class
 * which holds all the methods and attributes
 * of the ClientWorker. This class
 * is responsible for receiving commands from
 * the client and translating that to the
 * correct function call.
 * </p>
 * @author Karim Kabbara
 * @author David Desrochers
 * @version 1.0
 * @since 2022-11-11
 */
public class ClientWorker implements Runnable{

    /**
     * <p>
     *     Connection socket
     *     for client
     * </p>
     */
    private final Socket connection;

    /**
     * <p>
     *     Parent server
     * </p>
     */
    private final Server server;

    /**
     * <p>
     *     Client updates
     *     this variable
     *     if they are an admin.
     *     Set to false for safety.
     * </p>
     */
    private boolean isAdmin = false;

    /**
     * <p>
     *     To receive from the client
     * </p>
     */
    private BufferedReader input;

    /**
     * <p>
     *     To send to the client
     * </p>
     */
    private PrintWriter output;

    /**
     * <p>
     *     Formats String to date
     *     using certain format
     * </p>
     */
    private final DateTimeFormatter formatter; // converts date string from fx app to LocalDate

    /**
     * <p>
     *     Formats String to date time
     *     using certain format
     * </p>
     */
    private final DateTimeFormatter timeFormatter; // converts dateTime string from fx app to LocalDateTime

    /**
     * <p>Turns false when a request
     * to shut down is received. </p>
     */
    protected boolean keepRunningClient;

    /**
     * <p>This is the constructor for the
     * clientWorker. It initializes the
     * server and the client socket as well
     * as date and time formatters for parsing
     * localDateTimes. It also initializes
     * the input and output streams to communicate
     * with the client. Finally, it gets the isAdmin
     * status from the client to control access.
     * </p>
     * @param Server Associated Server
     * @param socket Client Socket
     */
    public ClientWorker(Server Server, Socket socket){
        this.server = Server;
        this.connection = socket;
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.keepRunningClient = true;
        try{
            this.input = this.getInputStream(this.connection);          // receive from client
            this.output = this.getOutputStream(this.connection);        // send to client
            this.isAdmin = Boolean.parseBoolean(this.input.readLine());
        }
        catch (Exception ignored){}

    }

    /**
     * <p>This method runs the clientWorker and
     * continuously receives commands from the client.
     * The command is then sent to the processClientMessage()
     * method to execute the appropriate method.
     * </p>
     */
    @Override
    public void run() {
        String command;
        while (true){
            try {
                command = this.input.readLine();// read response
                this.processClientMessage(command); // process it
            }
            catch (Exception ignored) {}
        }
    }

    /**
     * <p>This method process the command
     * sent from the client and calls the
     * appropriate function from the tournament.
     * The commands are received in this format:
     *      Command|arg1|arg2|arg3|...
     * The first section is the command to be
     * executed, and the rest are the arguments
     * that are necessary for that command.
     * The input is split by '|' into an array.
     * </p>
     * @param command Command from the client
     */
    public void processClientMessage(String command){
        String[] args = command.split("\\|"); // split commands delimited by |
        if (this.isAdmin){  // if client connecting is an admin
            if (args[0].equals("Create Tournament")){
                this.createTournament(args[1], LocalDate.parse(args[2], this.formatter), LocalDate.parse(args[3], this.formatter));
            }
            else if(args[0].equals("Add Participating Country")){
                this.addParticipatingCountry(args[1]);
            }
            else if(args[0].equals("Add Team")){
                this.addNationalTeam(args[1], args[2]);
            }
            else if(args[0].equals("Add Players")){
                this.addPlayer(args[1], args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
            }
            else if(args[0].equals("Add Referee")){
                this.addReferee(args[1], args[2]);
            }
            else if (args[0].equals("Create Match")){
                String dateTime = args[1] + " " + args[4];
                this.addMatch(LocalDateTime.parse(dateTime, this.timeFormatter), args[2], args[3]);
            }
            else if (args[0].equals("Add Referee to Match")){
                this.addRefereeToMatch(args[1], args[2]);
            }
            else if(args[0].equals("Add Player to LineUp")){
                this.addPlayerToMatch(args[1], args[2], args[3]);
            }
            else if (args[0].equals("Record Score")) {
                this.setMatchScore(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            }
            ///////////////additional commands///////////////
            else if (args[0].equals("Get Tournament Name")) {
                try {
                    this.output.println(this.server.tournament.getTournamentName());
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get Countries")) {
                try {
                    this.output.println(this.server.tournament.getCountryList());
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get Teams")) {
                try {
                    this.output.println(this.server.tournament.getTeamList(args[1]));
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get All Teams")) {
                try {
                    this.output.println(this.server.tournament.getAllTeams());
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get Players")) {
                try {
                    this.output.println(this.server.tournament.getPlayerList(args[1]));
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get Referees")) {
                try {
                    this.output.println(this.server.tournament.getRefereeList());
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get Matches")) {
                try {
                    this.output.println(this.server.tournament.getMatches());
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get Referees for Match")) {
                try {
                    this.output.println(this.server.tournament.getRefereesOn(LocalDateTime.parse(args[1], this.timeFormatter)));
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get Teams in Match")) {
                try {
                    this.output.println(this.server.tournament.getTeamsForMatch(LocalDateTime.parse(args[1], this.timeFormatter)));
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get Players in Team")) {
                try {
                    this.output.println(this.server.tournament.getPlayersInTeam(args[1], LocalDateTime.parse(args[2], this.timeFormatter)));
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get Players in LineUp")) {
                try {
                    this.output.println(this.server.tournament.getPlayersInLineUp(args[1], LocalDateTime.parse(args[2], this.timeFormatter)));
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get Past Matches")) {
                try {
                    this.output.println(this.server.tournament.getPastMatches());
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get Recorded Matches")) {
                try {
                    this.output.println(this.server.tournament.getRecordedMatches());
                }
                catch (Exception E){E.printStackTrace();}
            }
        }
        else { // if not an admin
            if (args[0].equals("Show Upcoming Matches")){
                try {
                    this.output.println(this.getUpcomingMatches());
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Date List of Matches")){
                try {
                    this.output.println(this.getMatchesOnDate(args[1]));
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Team Matches")){
                try {
                    this.output.println(this.getMatchesForTeam(args[1]));
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Get All Matches")){
                try {
                    this.output.println(this.getAllMatches());
                }
                catch (Exception E){E.printStackTrace();}
            }
            else if (args[0].equals("Team Lineups")){
                try {
                    this.output.println(this.getLineUpsForMatch(args[1]));
                }
                catch (Exception E){E.printStackTrace();}
            }
        }
    }
    ////////////////////////////////////////Admin Functions////////////////////////////////////////
    /**
     * <p> Instantiates a new tournament class.</p>
     * @param name          name of the tournament
     * @param dateStart     starting date
     * @param dateEnd       ending date
     */
    public void createTournament(String name, LocalDate dateStart, LocalDate dateEnd){
        this.server.tournament = new Tournament(name, dateStart, dateEnd);
    }

    /**
     * <p> Adds a Country to the tournament.</p>
     * @param country name of the country
     */
    public void addParticipatingCountry(String country){
        this.server.tournament.addCountry(country);
    }

    /**
     * <p> Adds a team belonging to a country.</p>
     * @param team    name of the team
     * @param country name of the country
     */
    public void addNationalTeam(String team, String country){
        this.server.tournament.addTeam(team, country);
    }

    /**
     * <p> Adds a player belonging to a team.</p>
     * @param playerName    name of the player
     * @param teamName      name of the team
     * @param age           age of player
     * @param height        height of player
     * @param weight        weight of player
     */
    public void addPlayer(String teamName, String playerName, int age, double height, double weight){
        this.server.tournament.addPlayer(teamName, playerName, age, weight, height);
    }

    /**
     * <p> Adds a referee to the tournament.</p>
     * @param name      referee name
     * @param country   referee country
     */
    public void addReferee(String name, String country){
        this.server.tournament.addReferee(name, country);
    }

    /**
     * <p> Adds a match to the tournament.</p>
     * @param dateTime  match starting date and time
     * @param teamA     first team
     * @param teamB     second team
     */
    public void addMatch(LocalDateTime dateTime, String teamA, String teamB){
        this.server.tournament.addMatch(dateTime, teamA, teamB);
    }

    /**
     * <p> Adds a referee to a match.</p>
     * @param dateTime      match starting date and time
     * @param refereeName   name of referee
     */
    public void addRefereeToMatch(String dateTime, String refereeName){
        this.server.tournament.addRefereeToMatch(dateTime, refereeName);
    }

    /**
     * <p> Adds a player to a lineup in a match.</p>
     * @param dateTime      match starting date and time
     * @param teamName      name of the team
     * @param playerName    name of the player
     * */
    public void addPlayerToMatch(String dateTime, String teamName, String playerName){
        this.server.tournament.addPlayerToMatch(dateTime, teamName, playerName);
    }

    /**
     * <p> Adds a player to a lineup in a match.</p>
     * @param dateTime      match starting date and time
     * @param a             goals scored by team A
     * @param b             goals scored by team B
     * */
    public void setMatchScore(String dateTime, int a, int b){
        this.server.tournament.setMatchScore(dateTime, a, b);
    }

    ////////////////////////////////////////Public Functions////////////////////////////////////////

    /**
     * NOTE
     * For all methods below, the returned String is in this format:
     * String1|String2|String3|...
     * NOTE
     *
     * <p> Gets all upcoming matches after current date.</p>
     * @return  String of all matches
     * */
    public String getUpcomingMatches(){
        return this.server.tournament.getUpcomingMatchesAsStrings();
    }

    /**
     * <p> Gets all matches on a specific date.</p>
     * @param date  date of matches
     * @return String of all matches on date
     * */
    public String getMatchesOnDate(String date){
        return this.server.tournament.getMatchesOnAsStrings(date);
    }

    /**
     * <p> Gets all matches for a specific team.</p>
     * @param teamName  name of team
     * @return String of all matches for a team
     * */
    public String getMatchesForTeam(String teamName){
        return this.server.tournament.getMatchesForAsStrings(teamName);
    }

    /**
     * <p> Gets all matches in the tournament.</p>
     * @return String of all matches in the tournament.
     * */
    public String getAllMatches(){
        return this.server.tournament.getMatches();
    }

    /**
     * <p> Gets both lineups for a match.</p>
     * @param match     match description
     * @return String of all players in both lineups in match.
     * */
    public String getLineUpsForMatch(String match){
        return this.server.tournament.getMatchLineUpsAsStrings(match);
    }

    ////////////////////////////////////////Server Functions////////////////////////////////////////
    /**
     * <p> Creates a PrintWriter Object to send
     * results to the client.</p>
     * @param s     Client Socket
     * @return PrintWriter object
     * @throws IOException If socket is invalid
     * */
    public PrintWriter getOutputStream(Socket s) throws IOException {
        return new PrintWriter(s.getOutputStream(), true);
    }

    /**
     * <p> Creates a BufferedReader Object
     * to receive results to the client.</p>
     * @param s     Client Socket
     * @return      BufferedReader object
     * @throws IOException If socket is invalid
     * */
    public BufferedReader getInputStream(Socket s) throws IOException {
        return new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    /**
     * <p> Closes the connection between
     * the clientWorker/Server and the client</p>
     * @param s     Client Socket
     * @param b     BufferedReader
     * @param p     PrintWriter
     * */
    public void closeConnection(Socket s, BufferedReader b, PrintWriter p) {
        try {
            s.close();
            b.close();
            p.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p> Gets Socket connection</p>
     * @return socket connection
     * */
    public Socket getConnection() {
        return this.connection;
    }

    /**
     * <p> Gets BufferedReader</p>
     * @return BufferedReader
     * */
    public BufferedReader getInput() {
        return this.input;
    }

    /**
     * <p> Gets PrintWriter</p>
     * @return PrintWriter
     * */
    public PrintWriter getOutput() {
        return this.output;
    }
}
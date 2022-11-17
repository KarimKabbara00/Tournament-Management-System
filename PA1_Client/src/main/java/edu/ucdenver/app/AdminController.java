package edu.ucdenver.app;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * <p>This defines the AdminController class
 * which holds all the methods and attributes
 * of the Admin application.
 * </p>
 * @author Karim Kabbara
 * @version 1.0
 * @since 2022-11-11
 */
public class AdminController {

    /**
     * <p>
     *     Holds the server port
     * </p>
     */
    private final int serverPort;

    /**
     * <p>
     *     Holds the server IP
     * </p>
     */
    private final String serverIp;

    /**
     * <p>
     *     List of all referees in tournament
     * </p>
     */
    public ListView refereeListView;

    /**
     * <p>
     *     Team A drop down list
     *     when creating match
     * </p>
     */
    public ComboBox ddlTeamA;

    /**
     * <p>
     *     Team B drop down list
     *     when creating match
     * </p>
     */
    public ComboBox ddlTeamB;

    /**
     * <p>
     *     Tab attribute. Used to
     *     check if tournament is
     *     created before changing
     *     tabs
     * </p>
     */
    public TabPane tabPane;

    /**
     * <p>
     *     Create new Tournament
     *     tab
     * </p>
     */
    public Tab newTournamentTab;

    /**
     * <p>
     *     Displays how many referees are needed
     *     for a match
     * </p>
     */
    public Label refereesNeeded;

    /**
     * <p>
     *     Socket to connect
     *     to server
     * </p>
     */
    private Socket serverConnection;

    /**
     * <p>
     *     To receive from server
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
     *     Holds True if
     *     app is admin.
     *     True in this case.
     * </p>
     */
    private final boolean isAdmin;

    /**
     * <p>
     *     List of teams in a
     *     given country
     * </p>
     */
    public ListView listOfTeamsInCountry;

    /**
     * <p>
     *    Drop down list for
     *    all countries when
     *    adding a player
     * </p>
     */
    public ComboBox ddlAPCountryName;

    /**
     * <p>
     *     List of all matches
     * </p>
     */
    public ListView matchesList;

    /**
     * <p>
     *      List of all recorded matches
     * </p>
     */
    public ListView recordedScoresList;

    /**
     * <p>
     *Create match tab
     * </p>
     */
    public Tab createMatchTab;

    /**
     * <p>
     *add team to country tab
     * </p>
     */
    public Tab addTeamTab;

    /**
     * <p>
     *add player to team tab
     * </p>
     */
    public Tab addPlayersTab;

    /**
     * <p>
     *add referee to tournament tab
     * </p>
     */
    public Tab addRefereeTab;

    /**
     * <p>
     *add players to a lineup
     * </p>
     */
    public Tab lineupTab;

    /**
     * <p>
     *Add referee to a match tab
     * </p>
     */
    public Tab addRefereeToMatchTab;

    /**
     * <p>
     *record score for a match
     * </p>
     */
    public Tab recordScoreTab;
    //@FXML
    /**
     * <p>
     *Date picker for tournament start date
     * </p>
     */
    public DatePicker dtTournamentStart;

    /**
     * <p>
     *Date picker for tournament end date
     * </p>
     */
    public DatePicker dtTournamentEnd;

    /**
     * <p>
     *Button to create new tournament
     * </p>
     */
    public Button btnCreateTournament;


    /**
     * <p>
     *Add a new country tab
     * </p>
     */
    public Tab addCountryTab;

    /**
     * <p>
     *Field for new country name
     * </p>
     */
    public TextField txtCountryName;

    /**
     * <p>
     *Button to add a new country
     * </p>
     */
    public Button btnAddCountry;

    /**
     * <p>
     *List of all countries in tournament
     * </p>
     */
    public ListView listOfParticipatingCountries;


    /**
     * <p>
     *Field for new team name
     * </p>
     */
    public TextField txtTeamName;

    /**
     * <p>
     *Drop down list of all countries
     * when adding a new team
     * </p>
     */
    public ComboBox ddlCountries;

    /**
     * <p>
     *Drop down list for teams
     * when adding a new player
     * </p>
     */
    public ComboBox ddlAPTeamName;

    /**
     * <p>
     *Field for new player name
     * </p>
     */
    public TextField txtPlayerName;

    /**
     * <p>
     *Field for new player age
     * </p>
     */
    public TextField txtPlayerAge;

    /**
     * <p>
     *Field for new player weight
     * </p>
     */
    public TextField txtPlayerWeight;

    /**
     * <p>
     *Field for new player height
     * </p>
     */
    public TextField txtPlayerHeight;

    /**
     * <p>
     *List of all players in current team
     * </p>
     */
    public ListView listOfCurrentPlayers;

    /**
     * <p>
     *Field for new referee name
     * </p>
     */
    public TextField txtRefereeName;

    /**
     * <p>
     *List of all countries when adding
     * a new referee
     * </p>
     */
    public ComboBox ddlCountriesAR;


    /**
     * <p>
     *     Field for match start time
     * </p>
     */
    public TextField txtMatchTime;

    /**
     * <p>
     * Date picker for match start date
     * </p>
     */
    public DatePicker dtMatchDate;

    /**
     * <p>
     *Drop down list for referees
     * when adding referee to match
     * </p>
     */
    public ComboBox ddlRefereeList;

    /**
     * <p>
     *     Drop down list for all matches
     *     when adding a referee to match
     * </p>
     */
    public ComboBox ddlMatchesARTM;

    /**
     * <p>
     *List of all referees currently
     * added to a match
     * </p>
     */
    public ListView listOfRefereeOnMatch;



    /**
     * <p>
     *Drop down list of all teams in
     * country when adding a player to
     * line up
     * </p>
     */
    public ComboBox ddlTeamList;

    /**
     * <p>
     *Drop down list of all players
     * in a team
     * </p>
     */
    public ComboBox ddlPlayerList;

    /**
     * <p>
     *List of all playesr in a lineup
     * for a team for a match
     * </p>
     */
    public ListView lvLineupPreview;

    /**
     * <p>
     *Drop down list of all matches
     * </p>
     */
    public ComboBox ddlMatchesSLU;


    /**
     * <p>
     *Drop down list of all matches
     * in the past
     * </p>
     */
    //Record Score
    public ComboBox ddlMatchesRS;

    /**
     * <p>
     *     Team A score
     * </p>
     */
    public TextField txtTAScore;

    /**
     * <p>
     *     Team B score
     * </p>
     */
    public TextField txtTBScore;


    /**
     * <p>
     *     Name of new Tournament
     * </p>
     */
    public TextField txtTournamentName;

    /**
     * <p>
     *     Button to create a match
     * </p>
     */
    public Button btnCreateMatch;

    /**
     * <p>This is the constructor for the AdminController Class.
     * It initializes the server port, server IP, and whether or not
     * it is an administrator.</p>
     */
    public AdminController(){
        this.serverPort = 9988;
        this.serverIp = "127.0.0.1";
        this.isAdmin = true;
    }

    /**
     * <p>This method initializes the socket, input and
     * output streams, and sends to the server the state
     * of isAdmin.</p>
     */
    public void initialize() {
        try {
            this.serverConnection = new Socket(this.serverIp, this.serverPort);
            this.input = this.getInputStream(this.serverConnection);          // receive from the server
            this.output = this.getOutputStream(this.serverConnection);        // send to the server
            this.output.println(this.isAdmin);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * <p>This method creates a PrintWriter Object
     * to allow the application to send messages
     * to the server.</p>
     * @param s The application's socket
     * @return  A PrintWriter Object.
     * @throws IOException Invalid socket
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
     * @throws IOException Invalid socket
     */
    public BufferedReader getInputStream(Socket s) throws IOException {
        return new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    /**
     * <p>This method creates a tournament by pulling
     * the data from the application and sending it to
     * the server. The fields for creating a tournament
     * are error checked and validated.</p>
     */
    public void createTournament() {
        String command = "Create Tournament|" + this.txtTournamentName.getText() + "|" + dtTournamentStart.getValue() +
                "|" + this.dtTournamentEnd.getValue();
        this.output.println(command);

        if (this.txtTournamentName.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter a Tournament Name!");
            alert.show();
        }
        else if (this.dtTournamentStart.getValue().isAfter(this.dtTournamentEnd.getValue())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "End Date is Before Start Date!");
            alert.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Added " + this.txtTournamentName.getText()
                    + ". Starts: " + this.dtTournamentStart.getValue() + ". Ends: " + this.dtTournamentEnd.getValue());
            alert.show();
        }
    }

    /**
     * <p>This method creates a country by pulling
     * the information in the fields and sending it
     * to the server. The fields are error checked
     * and validated here. If the country is valid,
     * it is added to the list view.</p>
     */
    public void addCountryName() {
        String temp = this.listOfParticipatingCountries.getItems().toString();          // before adding to lsitview, get current info
        String command = "Add Participating Country|" + this.txtCountryName.getText();
        this.output.println(command);
        this.listOfParticipatingCountries.setItems(FXCollections.observableArrayList(this.listCountryUpdate())); // update list
        if (this.txtCountryName.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter a Country First!");
            alert.show();
        }
        else if (temp.equals(this.listOfParticipatingCountries.getItems().toString())){ // if info before storing is the same after, then item must have been invalid.
            Alert alert = new Alert(Alert.AlertType.ERROR, "Country Already in Tournament!");
            alert.show();
        }
        this.cleanAddCountry();
    }

    /**
     * <p>This method adds a team to a country.
     * The selected country and the team name are
     * pulled from the fields and sent to the server.
     * If the input is valid, the team is added to the country</p>
     */
    public void addTeam() {
        String temp = this.listOfTeamsInCountry.getItems().toString();          // before adding to listview, get current info
        String command = "Add Team|" + this.txtTeamName.getText() + "|" + this.ddlCountries.getValue();
        if (!this.txtTeamName.getText().equals("")){
            this.output.println(command);
        }
        this.listOfTeamsInCountry.setItems(FXCollections.observableArrayList(this.listTeamUpdate(this.ddlCountries)));
        if (this.txtTeamName.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter a Team First!");
            alert.show();
        }
        else if (temp.equals(this.listOfTeamsInCountry.getItems().toString())){ // if info before storing is the same after, then item must have been invalid.
            Alert alert = new Alert(Alert.AlertType.ERROR, "Team Already Exists!");
            alert.show();
        }
        this.cleanAddTeam();
    }

    /**
     * <p>This method adds players to a team. The
     * player's team, name, age, weight, and height are
     * pulled from the fields and are sent to the server.
     * If the information is valid, the player is added
     * to that team.</p>
     */
    public void addPlayers() {
        String temp = this.listOfCurrentPlayers.getItems().toString();          // before adding to listview, get current info
        String command = "Add Players|" + this.ddlAPTeamName.getValue() + "|" + this.txtPlayerName.getText() + "|" + this.txtPlayerAge.getText() +
                "|" + this.txtPlayerWeight.getText() + "|" + this.txtPlayerHeight.getText();
        this.output.println(command);
        this.listOfCurrentPlayers.setItems(FXCollections.observableArrayList(this.listPlayersUpdate()));
        if (this.txtPlayerName.getText().equals("") || this.txtPlayerAge.getText().equals("") ||
                this.txtPlayerWeight.getText().equals("") || this.txtPlayerHeight.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Missing Information!");
            alert.show();
        }
        else if (this.listOfCurrentPlayers.getItems().size() > 35) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Team is Full!");
            alert.show();
        }
        else if (temp.equals(this.listOfCurrentPlayers.getItems().toString())){ // if info before storing is the same after, then item must have been invalid.
            Alert alert = new Alert(Alert.AlertType.ERROR, "Player Already Exists!");
            alert.show();
        }
        this.cleanAddPlayers();
    }
    /**
     * <p>This method adds a referee to a country.
     * Countries can have as many referees, but their names
     * must be unique. The name and country are pulled from the
     * fields and are sent to the server.</p>
     */
    public void addReferee() {
        String temp = this.refereeListView.getItems().toString();          // before adding to listview, get current info
        String command = "Add Referee|" + this.txtRefereeName.getText() + "|" + this.ddlCountriesAR.getValue();
        if (!this.txtRefereeName.getText().equals("")){
            this.output.println(command);
        }
        this.refereeListView.setItems(FXCollections.observableArrayList(this.listRefereesUpdate()));
        if (this.ddlCountriesAR.getValue().toString().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Choose a Country First!");
            alert.show();
        }
        else if (this.txtRefereeName.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter a Referee First!");
            alert.show();
        }
        else if (temp.equals(this.refereeListView.getItems().toString())){ // if info before storing is the same after, then item must have been invalid.
            Alert alert = new Alert(Alert.AlertType.ERROR, "Referee Already Exists!");
            alert.show();
        }
        else {
            this.cleanAddReferee();
        }
    }

    /**
     * <p>This method creates a match. The match date and time,
     * team A, and team B are pulled from the fields and sent
     * to the server. The data is error checked and validated.</p>
     */
    public void createMatch() {
        String temp = this.matchesList.getItems().toString();          // before adding to listview, get current info
        String command = "Create Match|" + this.dtMatchDate.getValue() +
                "|" + this.ddlTeamA.getValue() + "|" + this.ddlTeamB.getValue() + "|" + this.txtMatchTime.getText();
        this.output.println(command);
        this.matchesList.setItems(FXCollections.observableArrayList(this.listMatchesUpdate()));
        if (this.dtMatchDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select a Date First!");
            alert.show();
        }
        else if (this.txtMatchTime.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter a Time First!");
            alert.show();
        }
        else if (this.txtMatchTime.getText().length() != 5){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Time Format!");
            alert.show();
        }
        else if (!this.txtMatchTime.getText().matches("\\d{2}:\\d{2}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Time Format!");
            alert.show();
        }
        else if (this.ddlTeamA.getSelectionModel().isEmpty() || this.ddlTeamB.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select Two Teams!");
            alert.show();
        }
        else if (this.ddlTeamA.getValue().toString().equals(this.ddlTeamB.getValue().toString())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Teams Cannot be the Same!");
            alert.show();
        }
        else if (temp.equals(this.matchesList.getItems().toString())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Match Already Scheduled at this Time!");
            alert.show();
        }
        else {
            this.cleanCreateMatch();
        }
    }

    /**
     * <p>This method adds a referee to a match. The referee's
     * nationality cannot be the same as either team's nationality.
     * The information is pulled from the fields and sent to the server</p>
     */
    public void addRefereeToMatch() {
        String tempList = this.listOfRefereeOnMatch.getItems().toString();
        String temp = this.ddlMatchesARTM.getValue().toString();
        String dateTime = temp.substring(temp.length() - 19, temp.length() - 9) + " " +  temp.substring(temp.length() - 5);
        String command = "Add Referee to Match|" + dateTime + "|" + this.ddlRefereeList.getValue();
        this.output.println(command);
        this.listOfRefereeOnMatch.setItems(FXCollections.observableArrayList(this.updateRefereeListforMatch()));
        if (this.listOfRefereeOnMatch.getItems().size() < 4){
            this.refereesNeeded.setText("At least " + (4 - this.listOfRefereeOnMatch.getItems().size()) + " more referees needed.");
        }
        else {
            this.refereesNeeded.setText("");
        }

        if (this.ddlMatchesARTM.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select a Match First!");
            alert.show();
        }
        else if (this.ddlRefereeList.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select a Referee First!");
            alert.show();
        }
        else if (tempList.equals(this.listOfRefereeOnMatch.getItems().toString())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Text text = new Text("\tInvalid Entry!\n\n\tNote:\n\t- Referee Nationality Cannot Match Either Team's Nationality" +
                    "\n\t- Same Referee Cannot be Added Twice!");
            text.setWrappingWidth(500);
            alert.getDialogPane().setContent(text);
            alert.show();
        }
    }

    /**
     * <p>This method adds a player to a team's lineup. The match selected,
     * team name, and player name pulled and sent to the server.
     * The inputs are validated before doing so.</p>
     */
    public void addPlayerToLineup() {
        String tempList = this.lvLineupPreview.getItems().toString();
        String temp = this.ddlMatchesSLU.getValue().toString();
        String dateTime = temp.substring(temp.length() - 19, temp.length() - 9) + " " +  temp.substring(temp.length() - 5);
        String command = "Add Player to LineUp|" + dateTime + "|" + this.ddlTeamList.getValue().toString() + "|" +
                this.ddlPlayerList.getValue().toString();
        this.output.println(command);
        this.lvLineupPreview.setItems(FXCollections.observableArrayList(this.lineUpListUpdate()));

        if (this.lvLineupPreview.getItems().size() > 11){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Lineup is Full!");
            alert.show();
        }
        else if (tempList.equals(this.lvLineupPreview.getItems().toString())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Player Already in Lineup!");
            alert.show();
        }
    }

    /**
     * <p>This method records the score of a completed match.
     * The match must be completed before the score can be recorded.
     * The selected match, score 1, and score 2 are pulled and sent
     * to the server after being validated.</p>
     */
    public void recordScore() {
        String tempList = this.recordedScoresList.getItems().toString();
        String temp = "";
        if (this.ddlMatchesRS.getValue() != null){
            temp = this.ddlMatchesRS.getValue().toString();
            String dateTime = temp.substring(temp.length() - 19, temp.length() - 9) + " " +  temp.substring(temp.length() - 5);
            String command = "Record Score|" + dateTime + "|" + this.txtTAScore.getText() + "|" + this.txtTBScore.getText();
            this.output.println(command);
            this.recordedScoresList.setItems(FXCollections.observableArrayList(this.updateRecordedMatchesList()));
            try {
                Double.parseDouble(this.txtTAScore.getText());
                Double.parseDouble(this.txtTBScore.getText());
            }
            catch (NumberFormatException numberFormatException){
                if (this.txtTAScore.getText().isEmpty() || this.txtTBScore.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Scores First!");
                    alert.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Scores!");
                    alert.show();
                }
            }
            if (tempList.equals(this.recordedScoresList.getItems().toString())){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Match Already Recorded!");
                alert.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select a Match First!");
            alert.show();
        }
        this.cleanRecordScore();
    }
    ////////////////Additional Functions////////////////
    /**
     * <p>This method checks to see if the default blank
     * tournament name has been overridden. If it has,
     * then a tournament has been created. If not, then
     * the admin cannot change tabs unless they create
     * a tournament.</p>
     * @return      Whether a tournament has been created or not.
     */
    public boolean tournamentCreated(){
        String command = "Get Tournament Name|";
        this.output.println(command);
        boolean isCreated = false;
        try {
            String s = this.input.readLine();
            isCreated = !s.equals("blankTournament");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return isCreated;
    }
    /**
     * <p>This method gets the list of countries
     * in the tournament and returns them as a list.</p>
     * @return      List of countries in tournament
     */
    public String[] listCountryUpdate() {
        String command = "Get Countries|";
        this.output.println(command);

        String[] countryList = new String[0];
        try {
            String s = this.input.readLine();
            s = s.replaceAll("[^a-zA-Z0-9|\\s]", "");
            countryList = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return countryList;
    }
    /**
     * <p>This method gets the list of teams
     * for a specified country.</p>
     * @param country   country to get teams of.
     * @return          List of teams in country
     */
    public String[] listTeamUpdate(ComboBox country) {
        String command = "Get Teams|" + country.getValue();
        this.output.println(command);

        String[] teamList = new String[0];
        try {
            String s = this.input.readLine();
            teamList = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return teamList;
    }
    /**
     * <p>This method gets all the teams
     * in the tournament to be displayed
     * in the create match tab under
     * the combo box for team selection.</p>
     * @return  List of all teams in tournament
     */
    public String[] listAllTeamsUpdate() {
        String command = "Get All Teams|";
        this.output.println(command);

        String[] teamList = new String[0];
        try {
            String s = this.input.readLine();
            teamList = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return teamList;
    }
    /**
     * <p>This method gets all the players
     * for a specified team. This is to list
     * the players when adding a player to a team</p>
     * @return  List of all players in a team
     */
    public String[] listPlayersUpdate(){
        String command = "Get Players|" + this.ddlAPTeamName.getValue();
        this.output.println(command);

        String[] playerList = new String[0];
        try {
            String s = this.input.readLine();
            playerList = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return playerList;
    }
    /**
     * <p>This method gets referees in the
     * tournament to be displayed under the
     * add a referee tab.</p>
     * @return  List of all referees in the tournament
     */
    public String[] listRefereesUpdate() {
        String command = "Get Referees|";
        this.output.println(command);

        String[] refereeList = new String[0];
        try {
            String s = this.input.readLine();
            refereeList = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return refereeList;
    }
    /**
     * <p>This method gets all the matches
     * in the tournament to be displayed
     * in the create match tab.</p>
     * @return  List of all matches in the tournament
     */
    public String[] listMatchesUpdate() {
        String command = "Get Matches|";
        this.output.println(command);
        String[] matchList = new String[0];
        try {
            String s = this.input.readLine();
            matchList = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return matchList;
    }
    /**
     * <p>This method gets all the
     * referees assigned to a match.</p>
     * @return  List of all referees in a match
     */
    public String[] updateRefereeListforMatch(){
        String temp = this.ddlMatchesARTM.getValue().toString();
        String dateTime = temp.substring(temp.length() - 19, temp.length() - 9) + " " +  temp.substring(temp.length() - 5);
        String command = "Get Referees for Match|" + dateTime;
        this.output.println(command);
        String[] refereeList = new String[0];
        try {
            String s = this.input.readLine();
            refereeList = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return refereeList;
    }
    /**
     * <p>This method gets the teams
     * that were a part of the specified match.</p>
     * @return  List of all teams in a match
     */
    public String[] getMatchTeams() {
        String temp = this.ddlMatchesSLU.getValue().toString();
        String dateTime = temp.substring(temp.length() - 19, temp.length() - 9) + " " +  temp.substring(temp.length() - 5);
        String command = "Get Teams in Match|" + dateTime;
        this.output.println(command);

        String[] teamList = new String[0];
        try {
            String s = this.input.readLine();
            teamList = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return teamList;
    }
    /**
     * <p>This method gets all the players
     * in a team to be displayed when creating lineups</p>
     * @return  List of all teams in a match
     */
    public String[] getPlayersInTeam() {
        String temp = this.ddlMatchesSLU.getValue().toString();
        String dateTime = temp.substring(temp.length() - 19, temp.length() - 9) + " " +  temp.substring(temp.length() - 5);
        String command = "Get Players in Team|" + ddlTeamList.getValue() + "|" + dateTime;
        this.output.println(command);
        String[] playerList = new String[0];
        try {
            String s = this.input.readLine();
            playerList = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return playerList;
    }
    /**
     * <p>This method gets all the players
     * in a lineup. This is called when a player
     * is added to a lineup to update the list
     * accordingly.</p>
     * @return  List of all players in a lineup
     */
    public String[] lineUpListUpdate() {
        String temp = this.ddlMatchesSLU.getValue().toString();
        String dateTime = temp.substring(temp.length() - 19, temp.length() - 9) + " " +  temp.substring(temp.length() - 5);
        String command = "Get Players in LineUp|" + ddlTeamList.getValue() + "|" + dateTime;
        this.output.println(command);
        String[] lineUpList = new String[0];
        try {
            String s = this.input.readLine();
            lineUpList = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return lineUpList;
    }
    /**
     * <p>This method gets a list of all matches
     * before the current date to be displayed
     * when in the record score tab.</p>
     * @return  List of all matches before now.
     */
    public String[] getPastMatches() {

        String command = "Get Past Matches|";
        this.output.println(command);
        String[] pastMatches = new String[0];
        try {
            String s = this.input.readLine();
            pastMatches = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return pastMatches;
    }
    /**
     * <p>This method gets a list of all recorded
     * matches. This is called after a match is
     * recorded to update the list accordingly.</p>
     * @return  List of all recorded matches.
     */
    public String[] updateRecordedMatchesList(){
        String command = "Get Recorded Matches|";
        this.output.println(command);
        String[] recordedMatches = new String[0];
        try {
            String s = this.input.readLine();
            recordedMatches = s.split("\\|");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return recordedMatches;
    }

    //////////////updating based on tab clicks//////////////
    /**
     * <p>This method updates the country list
     * when the tab is clicked. It calls the
     * listCountryUpdate() method. </p>
     * <p>
     * FOR ALL METHODS BELOW:
     * This is where the logic occurs for
     * checking if a tournament has been created
     * to allow the tabs to be changed. Each method
     * calls that check.
     * </p>
     */
    public void countryTabUpdateList() {
        if (this.addCountryTab.isSelected()){
            if (!this.tournamentCreated()){
                this.tabPane.getSelectionModel().select(this.newTournamentTab);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Create a Tournament First!");
                alert.show();
            }
            else {
                this.listOfParticipatingCountries.setItems(FXCollections.observableArrayList(this.listCountryUpdate()));
            }
        }
    }
    /**
     * <p>This method updates the countries drop
     * down list when the tab is clicked. It
     * calls the listCountryUpdate() method and
     * sets the listView here. </p>
     */
    public void teamTabUpdateList() {
        if (this.addTeamTab.isSelected()){
            if (!this.tournamentCreated()){
                this.tabPane.getSelectionModel().select(this.newTournamentTab);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Create a Tournament First!");
                alert.show();
            }
            else {
                this.ddlCountries.setItems(FXCollections.observableArrayList(this.listCountryUpdate()));
            }
        }
    }
    /**
     * <p>This method updates the dropdown
     * list for the teams when a country is
     * selected in the add team tab</p>
     */
    public void updateTeamCountries() {
        this.listOfTeamsInCountry.setItems(FXCollections.observableArrayList(this.listTeamUpdate(this.ddlCountries)));
    }
    /**
     * <p>This method updates the dropdown
     * list for the countries when the
     * add player tab is selected.</p>
     */
    public void playersTabUpdateCountries() {
        if (this.addPlayersTab.isSelected()){
            if (!this.tournamentCreated()){
                this.tabPane.getSelectionModel().select(this.newTournamentTab);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Create a Tournament First!");
                alert.show();
            }
            else {
                this.ddlAPCountryName.setItems(FXCollections.observableArrayList(this.listCountryUpdate()));
            }
        }
    }
    /**
     * <p>This method updates the dropdown
     * list for the teams when a country is
     * selected in the add player tab</p>
     */
    public void updateTeamsForCountry() {
        this.ddlAPTeamName.setItems(FXCollections.observableArrayList(this.listTeamUpdate(this.ddlAPCountryName)));
    }
    /**
     * <p>This method updates the listview
     * of current players in a team when
     * a player is added to a team.</p>
     */
    public void updateCurrentPlayers() {
        this.listOfCurrentPlayers.setItems(FXCollections.observableArrayList(this.listPlayersUpdate()));
    }
    /**
     * <p>This method updates the countries
     * in the add referee tab when it is clicked.
     * It also updates the referee list when a referee
     * is added.</p>
     */
    public void refereeTabUpdateCountries() {
        if (this.addRefereeTab.isSelected()){
            if (!this.tournamentCreated()){
                this.tabPane.getSelectionModel().select(this.newTournamentTab);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Create a Tournament First!");
                alert.show();
            }
            else {
                this.refereeListView.setItems(FXCollections.observableArrayList(this.listRefereesUpdate()));
                this.ddlCountriesAR.setItems(FXCollections.observableArrayList(this.listCountryUpdate()));
            }
        }
    }
    /**
     * <p>This method updates match list
     * when a match is created. It also
     * updates the drop down lists for
     * the team select boxes. This method
     * is called when the create match
     * tab is clicked</p>
     */
    public void createMatchUpdateList() {
        if (this.createMatchTab.isSelected()){
            if (!this.tournamentCreated()){
                this.tabPane.getSelectionModel().select(this.newTournamentTab);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Create a Tournament First!");
                alert.show();
            }
            else {
                this.ddlTeamA.setItems(FXCollections.observableArrayList(this.listAllTeamsUpdate()));
                this.ddlTeamB.setItems(FXCollections.observableArrayList(this.listAllTeamsUpdate()));
                this.matchesList.setItems(FXCollections.observableArrayList(this.listMatchesUpdate()));
            }
        }
    }
    /**
     * <p>This method updates the match
     * list when the lineup tab is clicked</p>
     */
    public void updateMatchListForLineUp() {
        if (this.lineupTab.isSelected()){
            if (!this.tournamentCreated()){
                this.tabPane.getSelectionModel().select(this.newTournamentTab);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Create a Tournament First!");
                alert.show();
            }
            else {
                this.ddlMatchesSLU.setItems(FXCollections.observableArrayList(this.listMatchesUpdate()));
            }
        }
    }
    /**
     * <p>This method updates the team list
     * based on the teams that participated
     * in the selected match</p>
     */
    public void updateTeamsForMatchSelected() {
        if (this.ddlMatchesSLU.valueProperty().getValue() != null){
            this.ddlTeamList.setItems(FXCollections.observableArrayList(this.getMatchTeams()));
        }
    }
    /**
     * <p>This method updates the lineup
     * for the selected team and updates the
     * player ddl according to the selected team.</p>
     */
    public void updateCurrentLineup() {
        this.lvLineupPreview.setItems(FXCollections.observableArrayList(this.lineUpListUpdate()));
        this.ddlPlayerList.setItems(FXCollections.observableArrayList(this.getPlayersInTeam()));
    }
    /**
     * <p>This method updates the match ddl
     * and referee ddl when the add referee to match
     * tab is clicked.</p>
     */
    public void updateMatchAndRefereeList() {
        if (this.addRefereeToMatchTab.isSelected()){
            if (!this.tournamentCreated()){
                this.tabPane.getSelectionModel().select(this.newTournamentTab);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Create a Tournament First!");
                alert.show();
            }
            else {
                this.ddlMatchesARTM.setItems(FXCollections.observableArrayList(this.listMatchesUpdate()));
                this.ddlRefereeList.setItems(FXCollections.observableArrayList(this.listRefereesUpdate()));
            }
        }
    }
    /**
     * <p>This method updates the referee
     * list for a given match whenever a match
     * is selected in the add referee to match tab.</p>
     */
    public void getRefereesOnMatch() {
        if (this.ddlMatchesARTM.valueProperty().getValue() != null){
            this.listOfRefereeOnMatch.setItems(FXCollections.observableArrayList(this.updateRefereeListforMatch()));
            if (this.listOfRefereeOnMatch.getItems().size() != 4){
                if (this.listOfRefereeOnMatch.getItems().get(0).equals("")){
                    this.refereesNeeded.setText("At least " + (5 - this.listOfRefereeOnMatch.getItems().size() + " more referees needed"));
                }
                else {
                    this.refereesNeeded.setText("At least " + (4 - this.listOfRefereeOnMatch.getItems().size() + " more referees needed"));
                }
            }
            else {
                this.refereesNeeded.setText("");
            }

        }
    }
    /**
     * <p>This method updates the ddl for
     * past matches and updates the list
     * for matches that have already been recored.</p>
     */
    public void updateMatchesAndListRecordScore() {
        if (this.recordScoreTab.isSelected()){
            if (!this.tournamentCreated()){
                this.tabPane.getSelectionModel().select(this.newTournamentTab);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Create a Tournament First!");
                alert.show();
            }
            else {
                this.ddlMatchesRS.setItems(FXCollections.observableArrayList(this.getPastMatches()));
                this.recordedScoresList.setItems(FXCollections.observableArrayList(this.updateRecordedMatchesList()));
            }
        }
    }

    ///////////////Cleaning Text Fields After Submission///////////////
    /**
     * <p>
     * ALL METHODS BELOW:
     * The methods below are called
     * after the respective button is
     * clicked to clean the entry boxes
     * </p>
     */
    private void cleanAddCountry(){
        this.txtCountryName.setText("");
    }
    private void cleanAddTeam(){
        this.txtTeamName.setText("");
    }
    private void cleanAddPlayers(){
        this.txtPlayerName.setText("");
        this.txtPlayerAge.setText("");
        this.txtPlayerWeight.setText("");
        this.txtPlayerHeight.setText("");
    }
    private void cleanAddReferee(){
        this.txtRefereeName.setText("");
    }
    private void cleanCreateMatch(){
        this.txtMatchTime.setText("");
    }
    private void cleanRecordScore(){
        this.txtTAScore.setText("");
        this.txtTBScore.setText("");
    }
}
package edu.ucdenver.tournament;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>This defines the Tournament class
 * which holds all the methods and attributes
 * of the Tournament. This class
 * is responsible for the logic behind all
 * the methods defined in this project.
 * This class allows for creation of
 * countries, teams, players, referees,
 * and matches, then assigning each
 * to a match.
 * </p>
 * @author Karim Kabbara
 * @author David Desrochers
 * @version 1.0
 * @since 2022-11-11
 */
public class Tournament implements Serializable {

    /**
     * <p>
     *     Tournament name
     * </p>
     */
    private final String name;

    /**
     * <p>
     *     Tournament start date
     * </p>
     */
    private final LocalDate startDate;

    /**
     * <p>
     *     Tournament end date
     * </p>
     */
    private final LocalDate endDate;

    /**
     * <p>
     *     List of all countries in the tournament
     * </p>
     */
    private final ArrayList<Country> countryList;

    /**
     * <p>
     *     List of all matches in the tournament
     * </p>
     */
    private final ArrayList<Match> matchList;

    /**
     * <p>
     *     List of all recorded matches in the tournament
     * </p>
     */
    private final ArrayList<Match> recordedMatchList;

    /**
     * <p>The tournament needs to be
     * instantiated to allow to load a file.
     * When a file is loaded, this object
     * is replaces as the tournament being used
     * and is sent to the server</p>
     */
    protected Tournament loadedTournament;

    /**
     * <p>
     * This is the constructor
     * for the Tournament class. It sets
     * the name, start date, end date
     * as well as initialize arrayLists
     * to store all the matches,
     * countries,and recorded matches
     * </p>
     * @param name          Tournament Name
     * @param startDate     Tournament Start
     * @param endDate       Tournament End
     */
    public Tournament(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        // initialize
        this.countryList = new ArrayList<>();
        this.recordedMatchList = new ArrayList<>();
        this.matchList = new ArrayList<>();
    }

    /**
     * *NOTE:
     * All methods are synchronized to prevent
     * race conditions due to the fact that
     * the server allows multiple admins to
     * connect and modify data concurrently.
     * END NOTE*
     *
     * <p>
     * This method loads a serialized
     * object file and instantiates
     * the tournament with the loaded file.
     * </p>
     * @param lf    file name and directory
     */
    public synchronized void load(String lf) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(lf));
            this.loadedTournament = (Tournament) ois.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (ois != null){
                try{
                    ois.close();
                }
                catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
    }

    /**
     * <p>
     * This method calls the load function
     * and returns an instantiated tournament.
     * </p>
     * @param lf    file name and directory
     * @return      instantiated tournament
     */
    public synchronized Tournament loadFromFile(String lf){
        this.load(lf);
        return this.loadedTournament;
    }

    /**
     * <p>
     * This method serializes the current instance
     * of the tournament for later use.
     * </p>
     * @param sf    file name and directory
     */
    public synchronized void saveToFile(String sf){
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(new FileOutputStream(sf));
            oos.writeObject(this);
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        finally {
            if (oos != null){
                try{
                    oos.close();
                }
                catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
    }

    /**
     * <p>
     * This method adds a country to the country list after
     * checking if the country doesnt already exist.
     * </p>
     * @param country    country name
     */
    public synchronized void addCountry(String country) {
        try {   // check if country already exists
            for (Country c : this.countryList) {
                if (c.getCountryName().equals(country)) {
                    throw new IllegalArgumentException(); // if duplicate found
                }
            }
            this.countryList.add(new Country(country));  // otherwise, add country
        }
        catch (IllegalArgumentException iae){   // catch exception
            throw new IllegalArgumentException("Country Already in Tournament!");
        }
    }

    /**
     * <p>
     * This method adds a team to a country after
     * checking if the country exists and the same
     * team doesnt already exist in that country.
     * </p>
     * @param name       team name
     * @param country    country name
     */
    public synchronized void addTeam(String name, String country){
        Team team = new Team(name, new Country(country));
        boolean found = false;                              // to check if country exists.
        boolean foundTeam = false;
        Country countryFound = null;
        for (Country c : this.countryList) {     // loop through all countries
            if (c.getCountryName().equals(country)) {
                found = true;// set flag
                countryFound = c;
                for (Team t: c.getTeamsInCountry()){
                    if (t.getName().equals(name)){
                        foundTeam = true;
                        break;
                    }
                }
                break;
            }
        }

        if (found && !foundTeam){
            countryFound.addTeam(team);
        }

        if (!found){                            // if country is not found
            throw new IllegalArgumentException("Country Does Not Exist!");
        }
        if (foundTeam){
            throw new IllegalArgumentException("Team Already Exists!");
        }
    }

    /**
     * <p>
     * This method adds a referee to a country after
     * checking if the country exists and the same
     * referee doesnt already exist in that country.
     * </p>
     * @param name       referee name
     * @param country    country name
     */
    public synchronized void addReferee(String name, String country){
        try {
            for (Country c: this.countryList){
                if (c.getCountryName().equals(country)){
                    for (Referee r: c.getRefereesInCountry()) {          // look through referee list
                        if (r.getName().equals(name)){      // if name already exists
                            throw new IllegalArgumentException();
                        }
                    }
                    c.refereesInCountry.add(new Referee(name, new Country(country)));      // otherwise, add referee
                    break;
                }
            }

        }
        catch (IllegalArgumentException iae){
            throw new IllegalArgumentException("Referee Already Exists!");
        }
    }

    /**
     * <p>
     * This method adds a player to a team after
     * checking if the team exists and the same
     * player doesnt already exist in that team.
     * </p>
     * @param teamName       referee name
     * @param playerName     country name
     * @param age            player age
     * @param weight         player weight
     * @param height         player height
     */
    public synchronized void addPlayer(String teamName, String playerName, int age, double weight, double height){
        boolean found = false;                      // if team is found
        try {
            for (Country c: this.countryList){
                for (Team t: c.getTeamsInCountry()) {           // check if team exists
                    if (t.getName().equals(teamName)){
                        found = true;                   // set flag
                        for (Player p: t.getSquad()) {  // loop through all players of the current team
                            if (p.getName().equals(playerName)){    // if player already exists
                                throw new IllegalArgumentException();
                            }
                        }
                        if (t.getSquad().size() < 35){ // checks if team size is less than 35
                            t.addPlayer(playerName, age, weight, height);       // add a player
                        }
                        else throw new Exception("Squad is Full!");  // if 35 or over
                    }
                }
            }
        }
        catch (IllegalArgumentException iae){
            throw new IllegalArgumentException("Player Already Exists in This Team!");
        }
        catch (Exception sle){
            throw new IllegalArgumentException("Team Size Exceeded!");
        }
        if (!found){
            throw new IllegalArgumentException("Team Does Not Exist!");
        }
    }

    /**
     * <p>
     * This method adds a match to the tournament.
     * The two teams are checked for existence and
     * whether another match already exists at that
     * time.
     * </p>
     * @param dateTime  start time of match
     * @param teamA     first team
     * @param teamB     second team
     */
    public synchronized void addMatch(LocalDateTime dateTime, String teamA, String teamB) {
        Team team1 = null, team2 = null;
        for (Match match: this.matchList) {             // loop through all matches
            if (match.getDateTime().equals(dateTime)){  // if any match time is the same
                throw new IllegalArgumentException("Another Match is Scheduled at this Time!");
            }
        }
        for (Country c: this.countryList){
            for (Team team: c.getTeamsInCountry()) {                // loop through all teams
                if (team.getName().equals(teamA)){          // find teamA
                    team1 = team;
                }
                if (team.getName().equals(teamB)) {         // find teamB
                    team2 = team;
                }
            }
        }
        try {                               // try to get names of both teams
            team1.getName();
            team2.getName();
        }
        catch (NullPointerException npe){   // null pointer exception will throw if a team doesn't exist
            throw new IllegalArgumentException("One of the Teams Does Not Exist!");
        }

        if (team1.getName().equals(team2.getName())){   // if team names are the same
            throw new IllegalArgumentException("Teams are the Same!");
        }
        this.matchList.add(new Match(dateTime, team1, team2));
    }

    /**
     * <p>
     * This method adds a referee to a match.
     * The match and referee are checked for
     * existence, and whether the referee's
     * nationality matches any team's nationality.
     * </p>
     * @param dateTime        start time of match
     * @param refereeName     referee name
     */
    public synchronized void addRefereeToMatch(String dateTime, String refereeName) {
        boolean refereeFlag = false;
        boolean matchFlag = false;
        boolean biasFlag = false;
        boolean alreadyExists = false;
        dateTime = dateTime.substring(0,10) + "T" + dateTime.substring(11);
        for (Match m: this.matchList) {
            if (m.getDateTime().toString().equals(dateTime)){
                matchFlag = true;
                for (Country c: this.countryList){
                    for (Referee r: c.getRefereesInCountry()) {
                        if (r.getName().equals(refereeName)) {
                            refereeFlag = true;
                            if (!(r.getCountry().getCountryName().equals(m.getLineUpACountry().getCountryName())) &&
                                    (!r.getCountry().getCountryName().equals(m.getLineUpBCountry().getCountryName()))){
                                biasFlag = true;
                                for (Referee ref: m.getRefereeList()){
                                    if (ref.getName().equals(refereeName)){
                                        alreadyExists = true;
                                        break;
                                    }
                                }
                                if (!alreadyExists){
                                    m.addReferee(r);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!matchFlag){
            throw new IllegalArgumentException("Match Does Not Exist!");
        }
        if (!refereeFlag){
            throw new IllegalArgumentException("Referee Does Not Exist!");
        }
        if (!biasFlag){
            throw new IllegalArgumentException("Referee Nationality Cannot Match Team Nationality!");
        }
        if (alreadyExists){
            throw new IllegalArgumentException("Referee Already assigned to this Match!");
        }
    }

    /**
     * <p>
     * This method adds a player to a matches'
     * team lineup. The match, player and team
     * are checked for existence.
     * </p>
     * @param dateTime        start time of match
     * @param teamName        name of team
     * @param playerName      name of player
     */
    public synchronized void addPlayerToMatch(String dateTime, String teamName, String playerName) {
        boolean matchFlag = false;
        boolean teamFlag = false;
        boolean playerFlag = false;
        dateTime = dateTime.substring(0,10) + "T" + dateTime.substring(11);
        for (Match m: this.matchList) {
            if (m.getDateTime().toString().equals(dateTime)){
                matchFlag = true;
                for (Country c: this.countryList){
                    for (Team t: c.getTeamsInCountry()){
                        if (t.getName().equals(teamName)){
                            teamFlag = true;
                            for (Player p: t.getSquad()){
                                if (p.getName().equals(playerName)){
                                    playerFlag = true;
                                    m.addPlayer(p, t);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!matchFlag){
            throw new IllegalArgumentException("No Match on This Date!");
        }
        if (!teamFlag){
            throw new IllegalArgumentException("Team Not in Tournament!");
        }
        if (!playerFlag){
            throw new IllegalArgumentException("Player Not in Team");
        }
    }

    /**
     * <p>
     * This method sets the score for a
     * match that has already taken place.
     * The match is checked for existence.
     * </p>
     * @param dateTime        start time of match
     * @param a               Team A Score
     * @param b               Team B Score
     */
    public synchronized void setMatchScore(String dateTime, int a, int b){
        boolean matchFlag = false;
        boolean pastFlag = false;
        dateTime = dateTime.substring(0,10) + "T" + dateTime.substring(11);
        for (Match m: this.matchList){
            if (m.getDateTime().toString().equals(dateTime)){
                matchFlag = true;
                if (m.getDateTime().isBefore(LocalDateTime.now())){
                    pastFlag = true;
                    if (m.getIsRecorded()){
                        throw new IllegalArgumentException("Match Already Recorded!");
                    }
                    m.setMatchScore(a, b);
                    this.recordedMatchList.add(m);
                }
            }
        }
        if (!matchFlag){
            throw new IllegalArgumentException("Match Not Found on Given Date!");
        }
        if (!pastFlag){
            throw new IllegalArgumentException("Match Hasn't Occurred Yet!");
        }
    }

    /**
     * <p>
     * This method gets a list of all
     * upcoming matches after the current date.
     * </p>
     * @return List of matches after now
     */
    public synchronized ArrayList<Match> getUpcomingMatches(){
        ArrayList<Match> upcomingMatches = new ArrayList<>();
        for (Match m: this.matchList){
            if (m.getDateTime().isAfter(LocalDateTime.now())){
                upcomingMatches.add(m);
            }
        }
        return upcomingMatches;
    }

    /**
     * <p>
     * This method gets a list of all
     * matches on a specific date.
     * </p>
     * @param date  date of match
     * @return List of matches on date
     */
    public synchronized ArrayList<Match> getMatchesOn(String date){
        ArrayList<Match> matchesOn = new ArrayList<>();
        for (Match match: this.matchList) {
            if (match.getDateTime().toLocalDate().toString().equals(date)){
                matchesOn.add(match);
            }
        }
        return matchesOn;
    }

    /**
     * <p>
     * This method gets a list of all
     * matches for a specific team
     * </p>
     * @param teamName  name of team
     * @return List of matches for team
     */
    public synchronized List<Match> getMatchesFor(String teamName){
        List<Match> teamMatches = new ArrayList<>();
        for (Match m: this.matchList){
            if (m.getTeamA().getTeam().getName().equals(teamName) || m.getTeamB().getTeam().getName().equals(teamName)){
                teamMatches.add(m);
            }
        }
        return teamMatches;
    }

    /**
     * <p>
     * This method gets a both
     * lineups for a specific match
     * </p>
     * @param date      date and time of match
     * @return          List of lineups for a match
     */
    public synchronized ArrayList<LineUp> getMatchLineUps(LocalDateTime date){
        ArrayList<LineUp> lineUps = new ArrayList<>();
        for (Match m: this.matchList){
            if (m.getDateTime().equals(date)){
                lineUps.add(m.getTeamA());
                lineUps.add(m.getTeamB());
            }
        }
        return lineUps;
    }

    /**
     * <p>
     * This method gets a all
     * countries in tournament
     * </p>
     * @return String of all countries
     */
    public synchronized String getCountryList() {
        String countries = "";
        for (Country c: this.countryList) {
            countries += (c.getCountryName() + "|");
        }
        return countries;
    }

    /**
     * <p>
     * This method gets a all
     * teams in a country
     * </p>
     * @param country country name
     * @return String of all teams in a country
     */
    public synchronized String getTeamList(String country){
        String teams = "";
        for (Country c: this.countryList){
            if (c.getCountryName().equals(country)){
                for (Team t: c.getTeamsInCountry()){
                    teams += (t.getName()+"|");
                }
            }
        }
        return teams;
    }

    /**
     * <p>
     * This method gets a all
     * teams in the tournament
     * </p>
     * @return String of all team names
     */
    public String getAllTeams() {
        String allTeams = "";
        for (Country c: this.countryList){
            for (Team t: c.getTeamsInCountry()){
                allTeams += t.getName() + "|";
            }
        }
        return allTeams;
    }

    /**
     * <p>
     * This method gets a all
     * players in a team
     * </p>
     * @param team team name
     * @return String of all player names in a team
     */
    public synchronized String getPlayerList(String team){
        String playerList = "";
        for (Country c: this.countryList){
            for (Team t: c.getTeamsInCountry()){
                if (t.getName().equals(team)){
                    for (Player p: t.getSquad()){
                        playerList += (p.getName() + "|");
                    }
                }
            }
        }
        return playerList;
    }

    /**
     * <p>
     * This method gets a all
     * referees in tournament
     * </p>
     * @return String of all referee names
     */
    public synchronized String getRefereeList() {
        String refereeList = "";
        for (Country c: this.countryList){
            for (Referee r: c.getRefereesInCountry()){
                refereeList += (r.getName() + "|");
            }
        }
        return refereeList;
    }

    /**
     * <p>
     * This method gets a all
     * matches in tournament
     * </p>
     * @return String of all match descriptions
     */
    public synchronized String getMatches() {
        String matchList = "";
        for (Match m: this.matchList){
            matchList += (m.toString() + "|");
        }
        return matchList;
    }

    /**
     * <p>
     * This method gets a referees
     * for a specific match
     * </p>
     * @param dateTime start date/time for match
     * @return String of all referee names for a match
     */
    public synchronized String getRefereesOn(LocalDateTime dateTime) {
        String refereeList = "";
        for (Match m: this.matchList){
            if (m.getDateTime().equals(dateTime)){
                for (Referee r: m.getRefereeList()){
                    refereeList += (r.getName() + "|");
                }
            }
        }
        return refereeList;
    }

    /**
     * <p>
     * This method gets team names
     * for a specific match
     * </p>
     * @param dateTime  start date/time for match
     * @return Names of both teams for a match
     */
    public synchronized String getTeamsForMatch(LocalDateTime dateTime){
        String teams = "";
        for (LineUp l: this.getMatchLineUps(dateTime)){
            teams += (l.getTeam().getName() + "|");
        }
        return teams;
    }

    /**
     * <p>
     * This method gets team names
     * for a specific match
     * </p>
     * @param team      team name
     * @param dateTime  start date/time for match
     * @return String of both team names for a match
     */
    public synchronized String getPlayersInTeam(String team, LocalDateTime dateTime){
        String players = "";
        for (LineUp l: this.getMatchLineUps(dateTime)){
            if (l.getTeam().getName().equals(team)){
                for (Player p: l.getTeam().getSquad()){
                    players += p.getName() + "|";
                }
            }
        }
        return players;
    }

    /**
     * <p>
     * This method gets all players
     * in a team's lineup for a specific match
     * </p>
     * @param team      team name
     * @param dateTime  start date/time for match
     * @return String of player names in a lineup for a match
     */
    public synchronized String getPlayersInLineUp(String team, LocalDateTime dateTime){
        String players = "";
        for (LineUp l: this.getMatchLineUps(dateTime)){
            if (l.getTeam().getName().equals(team)){
                for (Player p: l.getPlayers()){
                    players += p.getName() + "|";
                }
                break;
            }
        }
        return players;
    }

    /**
     * <p>
     * This method gets all match descriptions
     * before the current date
     * </p>
     * @return String of match descriptions before now
     */
    public synchronized String getPastMatches(){
        String matches = "";
        for (Match m: this.matchList){
            if (m.getDateTime().isBefore(LocalDateTime.now())){
                matches += m + "|";
            }
        }
        return matches;
    }

    /**
     * <p>
     * This method gets all recorded match descriptions
     * </p>
     * @return String of recorded match descriptions
     */
    public synchronized String getRecordedMatches(){
        String matches = "";
        for (Match m: this.recordedMatchList){
            matches += m.recordedToString() + "|";
        }
        return matches;
    }

    /**
     * <p>
     * This method gets all upcoming match descriptions
     * after the current date
     * </p>
     * @return String of upcoming match descriptions
     */
    public synchronized String getUpcomingMatchesAsStrings(){
        String upcomingMatches = "";
        for (Match m: this.getUpcomingMatches()){
            upcomingMatches += m.toString() + "|";
        }
        return upcomingMatches;
    }

    /**
     * <p>
     * This method gets all match descriptions
     * on a specified date
     * </p>
     * @param date date of matches
     * @return String of specific match descriptions
     */
    public synchronized String getMatchesOnAsStrings(String date){
        String matchList = "";
        for (Match m: this.getMatchesOn(date)){
            if (m.getDateTime().isBefore(LocalDateTime.now())){
                matchList += m.recordedToString() + "|";
            }
            else {
                matchList += m + "|";
            }
        }
        return matchList;
    }

    /**
     * <p>
     * This method gets all match descriptions
     * for a specific team
     * </p>
     * @param teamName name of the team
     * @return String of all match descriptions for a team
     */
    public synchronized String getMatchesForAsStrings(String teamName){
        String matches = "";
        for (Match m: this.getMatchesFor(teamName)){
            if (m.getDateTime().isBefore(LocalDateTime.now())){
                matches += m.recordedToString() + "|";
            }
            else {
                matches += m + "|";
            }
        }
        return matches;
    }

    /**
     * <p>
     * This method gets both lineups for a specific match
     * The lineups are separated by a '$' as follows:
     * player1|player2|player3|$playerA|playerB|playerC
     * </p>
     * @param matchName description of match
     * @return String of all players in both lineups for a match
     */
    public synchronized String getMatchLineUpsAsStrings(String matchName){
        String[] team1 = matchName.split(" vs. ");
        String[] team2 = team1[1].split(" on ");
        String dateTime = team2[1].substring(0,10) + "T" + team2[1].substring(14);
        String lineUpTeam1 = "";
        String lineUpTeam2 = "";
        for (LineUp l: this.getMatchLineUps(LocalDateTime.parse(dateTime))){
            if (l.getTeam().getName().equals(team1[0])){
                for (Player p: l.getPlayers()){
                    lineUpTeam1 += p.getName() + "|";
                }
            }
            else if (l.getTeam().getName().equals(team2[0])) {
                for (Player p: l.getPlayers()){
                    lineUpTeam2 += p.getName() + "|";
                }
            }
        }
        return lineUpTeam1 + "$" + lineUpTeam2;
    }

    /**
     * <p>
     * This method returns the name
     * of the tournament.
     * </p>
     * @return tournament name
     */
    public String getTournamentName() {
        return this.name;
    }
}
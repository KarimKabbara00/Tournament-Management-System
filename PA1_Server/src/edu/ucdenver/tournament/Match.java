package edu.ucdenver.tournament;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * <p>This defines the Match class.
 * The Match class is responsible
 * for creating matches, setting lineups
 * setting referees, and match scores
 * </p>
 * @author David Desrochers
 * @version 1.0
 * @since 2022-11-11
 */
public class Match implements Serializable {

    /**
     * <p>
     *     Starting date time for the match
     * </p>
     */
    private final LocalDateTime dateTime;

    /**
     * <p>
     *     Team A Score
     * </p>
     */
    private int scoreTeamA;

    /**
     * <p>
     *     Team B Score
     * </p>
     */
    private int scoreTeamB;

    /**
     * <p>
     *     Line up A in the match
     * </p>
     */
    private final LineUp lineUpA;

    /**
     * <p>
     *     Line up B in the match
     * </p>
     */
    private final LineUp lineUpB;

    /**
     * <p>
     *     List of referees for the match.
     *     Must be a minimum of 4 for the match
     *     to begin
     * </p>
     */
    private final ArrayList<Referee> refereeList;

    /**
     * <p>
     *     If the match score has
     *     been recorded, set to true.
     * </p>
     */
    private boolean isRecorded;


    /**
     * <p>
     * This is the constructor
     * for the Match class. It sets
     * the date/time, and lineups for teams
     * A and B. This initializes the referee list.
     * </p>
     * @param dateTime  date/time of match
     * @param TeamA     Team A
     * @param TeamB     Team B
     */
    public Match(LocalDateTime dateTime, Team TeamA, Team TeamB) {
        this.dateTime = dateTime;
        this.lineUpA = new LineUp(TeamA);
        this.lineUpB = new LineUp(TeamB);
        this.refereeList = new ArrayList<>();
        this.isRecorded = false;
    }

    /**
     * <p>
     * Returns Team A
     * </p>
     * @return Team A
     */
    public LineUp getTeamA(){
        return this.lineUpA;
    }

    /**
     * <p>
     * Returns Team B
     * </p>
     * @return Team B
     */
    public LineUp getTeamB(){
        return this.lineUpB;
    }

    /**
     * <p>
     * Returns if a match is upcoming
     * </p>
     * @return True or False
     */
    public boolean isUpcoming(){
        return this.dateTime.isAfter(LocalDateTime.now());
    }

    /**
     * <p>
     * Adds a player to a lineup of a team.
     * Ensures player isn't already in lineup.
     * </p>
     * @param t Team Name
     * @param p Player Name
     */
    public void addPlayer(Player p, Team t){
        boolean flag = true;
        if (this.lineUpA.getTeam().equals(t)){
            for (Player player: this.lineUpA.getPlayers()){
                if (player.getName().equals(p.getName())) {
                    flag = false;
                    break;
                }
            }
            if (flag){
                this.lineUpA.addPlayer(p);
            }
        }
        else if (this.lineUpB.getTeam().equals(t)){
            for (Player player: this.lineUpB.getPlayers()){
                if (player.getName().equals(p.getName())) {
                    flag = false;
                    break;
                }
            }
            if (flag){
                this.lineUpB.addPlayer(p);
            }
        }
    }

    /**
     * <p>
     * Adds a referee to match.
     * Ensures referee count isn't above 4.
     * </p>
     * @param referee Referee object
     */
    public void addReferee(Referee referee) {
        this.refereeList.add(referee);
    }

    /**<p>
     * Sets the score for a match
     * </p>
     * @param A     Score of Team A
     * @param B     Score of Team B
     */
    public void setMatchScore(int A, int B){
        this.isRecorded = true;
        this.scoreTeamA = A;
        this.scoreTeamB = B;
    }

    /**
     * <p>
     * Returns Datetime of match
     * </p>
     * @return datetime
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * <p>
     * Returns referees in match
     * </p>
     * @return referee list
     */
    public ArrayList<Referee> getRefereeList() {
        return this.refereeList;
    }

    /**
     * <p>
     * Returns Lineup A
     * </p>
     * @return Lineup A
     */
    public Country getLineUpACountry() {
        return this.lineUpA.getTeam().getCountry();
    }

    /**
     * <p>
     * Returns Lineup B
     * </p>
     * @return Lineup B
     */
    public Country getLineUpBCountry() {
        return this.lineUpB.getTeam().getCountry();
    }

    /**
     * <p>
     * Returns if a match is recorded
     * </p>
     * @return True or false
     */
    public boolean getIsRecorded(){
        return this.isRecorded;
    }

    /**
     * <p>
     * Returns if a match description listing
     * the team names and the time of the match.
     * Example:
     *      TeamA vs. TeamB on 2022-11-11 at 22:00
     * </p>
     * @return String of match description
     */
    @Override
    public String toString() {
        return lineUpA.getTeam().getName() + " vs. " + lineUpB.getTeam().getName() +
                    " on " + this.dateTime.toLocalDate() + " at " + this.dateTime.toLocalTime().toString();
    }

    /**
     * <p>
     * If the match is in the past, the score is included:
     * Example:
     *      TeamA vs. TeamB on 2022-11-11 at 22:00 --> Final Score: 2-1
     * </p>
     * @return String of match description
     */
    public String recordedToString(){
        return lineUpA.getTeam().getName() + " vs. " + lineUpB.getTeam().getName() +
                    " on " + this.dateTime.toLocalDate() + " at " + this.dateTime.toLocalTime() +
                    " --> Final Score: " + this.scoreTeamA + " - " + this.scoreTeamB;
    }
}
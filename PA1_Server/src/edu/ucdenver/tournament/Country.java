package edu.ucdenver.tournament;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>This defines the Country class
 * which holds all the methods and attributes
 * of the Country. This class
 * is responsible for adding teams to a country
 * and retreiving necessary information
 * </p>
 * @author David Desrochers
 * @version 1.0
 * @since 2022-11-11
 */
public class Country implements Serializable {

    /**
     * <p>
     *     Name of the country
     * </p>
     */
    private final String countryName;

    /**
     * <p>
     *     Holds a list of all teams in a country
     * </p>
     */
    protected ArrayList<Team> teamsInCountry;

    /**
     * <p>
     *     Holds a list of all referees in a country
     * </p>
     */
    protected ArrayList<Referee> refereesInCountry;

    /**
     * <p>
     * This is the constructor
     * for the Country class. It sets
     * the country name and initializes
     * the team and referee lists
     * </p>
     * @param countryName Name of country
     */
    public Country(String countryName) {
        this.countryName = countryName;
        this.teamsInCountry = new ArrayList<>();
        this.refereesInCountry = new ArrayList<>();
    }

    /**
     * <p>
     * Returns the name of the country
     * </p>
     * @return country name
     * */
    public String getCountryName() {
        return this.countryName;
    }

    /**
     * <p>
     * Adds a team to the country
     * </p>
     * @param team Team object
     * */
    public void addTeam(Team team){
        this.teamsInCountry.add(team);
    }

    /**
     * <p>
     * Gets all teams in the country
     * </p>
     * @return List of teams in country
     * */
    public ArrayList<Team> getTeamsInCountry() {
        return this.teamsInCountry;
    }

    /**
     * <p>
     * Gets all referees in the country
     * </p>
     * @return List of referees in country
     * */
    public ArrayList<Referee> getRefereesInCountry() {
        return refereesInCountry;
    }
}
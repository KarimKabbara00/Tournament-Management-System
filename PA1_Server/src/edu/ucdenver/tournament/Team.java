package edu.ucdenver.tournament;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>This defines the team class.
 * The team class is responsible
 * for storing the players belonging to it
 * </p>
 * @author Karim Kabbara
 * @version 1.0
 * @since 2022-11-11
 */
public class Team implements Serializable {

    /**
     * <p>
     *     Team's name
     * </p>
     */
    private final String name;

    /**
     * <p>
     *     List of all players in a team
     * </p>
     */
    private final ArrayList <Player> playerList;

    /**
     * <p>
     *     Country associated with the team
     * </p>
     */
    private final Country country;

    /**
     * <p>
     * This is the constructor
     * for the Team class. It sets
     * the name, and country it belongs to
     * as well as instantiating the player list
     * </p>
     * @param name          Team Name
     * @param country       Country name
     */
    public Team(String name, Country country) {
        this.name = name;
        this.playerList = new ArrayList<>();
        this.country = country;
    }

    /**
     * <p>
     * Returns the country object belonging
     * to the Team.
     * </p>
     * @return country object
     */
    public Country getCountry(){
        return this.country;
    }

    /**
     * <p>
     * Returns the player list for the team
     * </p>
     * @return player list
     */
    public ArrayList<Player> getSquad() {
        return this.playerList;
    }

    /**
     * <p>
     * Adds a player to the list of players
     * </p>
     * @param name      player name
     * @param age       player age
     * @param weight    player weight
     * @param height    player height
     * */
    public void addPlayer(String name, int age, double height, double weight){
        this.playerList.add(new Player(name, age, height, weight));
    }

    /**
     * <p>
     * Returns name of team
     * </p>
     * @return Name of team
     * */
    public String getName() {
        return this.name;
    }
}
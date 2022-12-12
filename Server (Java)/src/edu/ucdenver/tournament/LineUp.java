package edu.ucdenver.tournament;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>This defines the Lineup class
 * which holds all the methods and attributes
 * of the Lineups. This class
 * is responsible for adding players to a lineup
 * and retreiving necessary information.
 * </p>
 * @author Karim Kabbara
 * @version 1.0
 * @since 2022-11-11
 */
public class LineUp implements Serializable {

    /**
     * <p>
     *     Team object for the lineup
     * </p>
     */
    private final Team team;

    /**
     * <p>
     *     List of players in the lineup
     * </p>
     */
    private final ArrayList<Player> lineUpPlayers;

    /**
     * <p>
     * This is the constructor
     * for the LineUp class. It sets
     * the team and initializes the player
     * list
     * </p>
     * @param team Team Object
     */
    public LineUp(Team team) {
        this.team = team;
        this.lineUpPlayers = new ArrayList<>();
    }

    /**
     * <p>
     * Gets the team for the lineup
     * </p>
     * @return Team object
     * */
    public Team getTeam() {
        return this.team;
    }

    /**
     * <p>
     * List of players in the lineup
     * </p>
     * @return List of players
     * */
    public ArrayList<Player> getPlayers() {
        return this.lineUpPlayers;
    }

    /**
     * <p>
     * Adds a player to the lineup.
     * Ensures lineup size is less than 11
     * before adding
     * </p>
     * @param player Player Object
     * */
    public void addPlayer(Player player) {
        try{
            if (this.lineUpPlayers.size() < 11){
                this.lineUpPlayers.add(player);
            }
            else throw new RuntimeException();
        }
        catch (RuntimeException rte){
            System.err.println("Lineup is Full!");
        }
    }

    /**
     * <p>
     * Gets all player's names in the lineup
     * </p>
     * @return String of all players' names
     * */
    @Override
    public String toString() {
        String lineUp = "";
        for (Player p: this.lineUpPlayers){
            lineUp += (p.getName() + "\n");
        }
        return lineUp;
    }
}
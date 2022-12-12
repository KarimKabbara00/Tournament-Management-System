package edu.ucdenver.tournament;

import java.io.Serializable;

/**
 * <p>This defines the Referee class.
 * The Referee class is responsible
 * for creating a referee and returning
 * necessary information.
 * </p>
 * @author David Desrochers
 * @version 1.0
 * @since 2022-11-11
 */
public class Referee implements Serializable {

    /**
     * <p>
     *     Referee's name
     * </p>
     */
    private final String name;

    /**
     * <p>
     *     Referee's country
     * </p>
     */
    private final Country country;

    /**
     * <p>
     * This is the constructor
     * for the Referee class. It sets
     * the referee name and country
     * </p>
     * @param name          Referee name
     * @param country       Referee country
     */
    public Referee(String name, Country country){
        this.name = name;
        this.country = country;
    }

    /**
     * <p>
     * Returns Referee's country
     * </p>
     * @return Referee's Country
     * */
    public Country getCountry() {
        return this.country;
    }

    /**
     * <p>
     * Returns Referee's name
     * </p>
     * @return Referee's name
     * */
    public String getName() {
        return this.name;
    }
}
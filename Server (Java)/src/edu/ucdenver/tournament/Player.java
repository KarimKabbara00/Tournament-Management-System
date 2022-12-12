package edu.ucdenver.tournament;

import java.io.Serializable;

/**
 * <p>This defines the Player class.
 * The Player class is responsible
 * for creating a player and returning
 * necessary information.
 * </p>
 * @author Karim Kabbara
 * @version 1.0
 * @since 2022-11-11
 */
public class Player implements Serializable {

    /**
     * <p>
     *     Player's name
     * </p>
     */
    private final String name;

    /**
     * <p>
     *     Player's age
     * </p>
     */
    private final int age;

    /**
     * <p>
     *     Player's height
     * </p>
     */
    private final double height;

    /**
     * <p>
     *     Player's weight
     * </p>
     */
    private final double weight;

    /**
     * <p>
     * This is the constructor
     * for the player class. It sets
     * the name, age, height and weight.
     * </p>
     * @param name          Player Name
     * @param age           Player Age
     * @param height        Player Height
     * @param weight        Player Weight
     */
    public Player(String name, int age, double height, double weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    /**
     * <p>
     * Returns player name
     * </p>
     * @return Player name
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * Returns player age
     * </p>
     * @return Player age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * <p>
     * Returns player height
     * </p>
     * @return Player height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * <p>
     * Returns player weight
     * </p>
     * @return Player weight
     */
    public double getWeight() {
        return this.weight;
    }
}
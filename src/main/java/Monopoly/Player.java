package Monopoly;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private String mention;
    private int boardPos;
    private int money;
    private boolean rolledDoubles;
    private int jailTime;
    private boolean inJail;
    private ArrayList<Integer> properties;
    private ArrayList<Integer> houses;
    private int[] coloredProperties;
    private ArrayList<Integer> monopolies;
    public static List<String> players = new ArrayList<>();
    public static List<String> playerNames = new ArrayList<>();
    public static List<Player> playerObjects;

    public Player (String name, String mention) {
        this.name = name;
        this.mention = mention;
        coloredProperties = new int[8];
        monopolies = new ArrayList<>();
        properties = new ArrayList<>();
        houses = new ArrayList<>();
        boardPos = 0;
        money = 1500;
        rolledDoubles = false;
        inJail = false;
    }

    public void addBoardPos (int amount) {
        boardPos += amount;

        if (boardPos >= 40) {
            boardPos %= 40;
            money += 200;
        }
    }

    public void setBoardPos (int tile) {
        boardPos = tile;
    }

    public void addMoney (int amount) {
        money += amount;
    }

    public void subtractMoney (int amount) {
        money -= amount;
    }

    public int getBoardPos() {
        return boardPos;
    }

    public ArrayList<Integer> getProperties() {
        return properties;
    }

    public void addProperty(int property) {
        int color = BoardData.monopolyData[property];
        properties.add(property);
        houses.add(0);
        if (color == 0) {return;}
        coloredProperties[color - 1]++;
        if (!monopolies.contains(color) && (color == 1 || color == 8)) {
            if (coloredProperties[color - 1] == 2) {
                monopolies.add(color);
                Commands.Say("Monopoly " + color + " has been obtained!!!");
            }
        }
        else {
            if (coloredProperties[color - 1] == 3) {
                monopolies.add(color);
                Commands.Say("Monopoly " + color + " has been obtained!!!");
            }
        }
    }

    public int getHouses(int property) {
        return houses.get(properties.indexOf(property));
    }

    public void addHouse(int property) {
        int color = BoardData.monopolyData[property];
        if (monopolies.contains(color)) {
            int propertyIndex = properties.indexOf(property);
            if (houses.get(propertyIndex) < 4) {
                houses.set(propertyIndex, houses.get(propertyIndex) + 1);
            }
            subtractMoney(BoardData.propertyData.get(propertyIndex)[7]);
        }
        else {
            Commands.Say("You don't have the monopoly for this card!");
        }
    }

    public void mortgageHouse(int property) {
        int color = BoardData.monopolyData[property];
        if (monopolies.contains(color)) {
            int propertyIndex = properties.indexOf(property);
            if (houses.get(propertyIndex) > -1) {
                houses.set(propertyIndex, houses.get(propertyIndex) - 1);
            }
            addMoney(BoardData.propertyData.get(propertyIndex)[7] / 2);
        }
        else {
            Commands.Say("You don't have the monopoly for this card!");
        }
    }

    public int getMoney() {
        return money;
    }

    public boolean getRolledDoubles() { return rolledDoubles; }

    public void setRolledDoubles(boolean rolledDoubles) {
        this.rolledDoubles = rolledDoubles;
    }

    public void setJailTime(int jailTime) {
        this.jailTime = jailTime;
    }

    public int getJailTime() {
        return jailTime;
    }

    public void decrementJailTime() { jailTime -= 1; }
}
package Monopoly;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private String mention;
    private int boardPos;
    private int money;
    private ArrayList<Integer> properties;
    private ArrayList<Integer> houses;
    public static List<String> players = new ArrayList<>();
    public static List<String> playerNames = new ArrayList<>();
    public static List<Player> playerObjects;

    public Player (String name, String mention) {
        this.name = name;
        this.mention = mention;
        properties = new ArrayList<Integer>();
        houses = new ArrayList<Integer>();
        boardPos = 0;
        money = 1500;
    }

    public Player (String name, String mention, int boardPos, int money) {
        this.name = name;
        this.mention = mention;
        this.boardPos = boardPos;
        properties = new ArrayList<Integer>();
        houses = new ArrayList<Integer>();
        this.money = money;
    }

    public void addBoardPos (int amount) {
        boardPos += amount;

        if (boardPos >= 40) {
            boardPos %= 40;
            money += 200;
        }
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
        properties.add(property);
        houses.add(0);
    }

    public int getHouses(int property) {
        return houses.get(properties.indexOf(property));
    }

    public void addHouse(int property) {
        int propertyIndex = properties.indexOf(property);
        if (houses.get(propertyIndex) < 4) {
            houses.set(propertyIndex, houses.get(propertyIndex) + 1);
        }
        subtractMoney(BoardData.propertyData.get(propertyIndex)[7]);
    }

    public void mortgageHouse(int property) {
        int propertyIndex = properties.indexOf(property);
        if (houses.get(propertyIndex) > -1) {
            houses.set(propertyIndex, houses.get(propertyIndex) - 1);
        }
        addMoney(BoardData.propertyData.get(propertyIndex)[7] / 2);
    }

    public int getMoney() {
        return money;
    }
}
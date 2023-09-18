package Monopoly;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private String mention;
    private int boardPos;
    private int money;
    private boolean rolledDoubles;
    private ArrayList<Integer> properties;
    private int[] coloredProperties;
    private ArrayList<Integer> monopolies;
    private int timeInJail;
    private int doublesInARow;
    private int utilitiesOwned;
    private int getOutOfJailFree;
    private PropertyMenu propertyMenu;
    public static List<String> players = new ArrayList<>();
    public static List<String> playerNames = new ArrayList<>();
    public static List<Player> playerObjects;

    public Player(String name, String mention) {
        this.name = name;
        this.mention = mention;
        coloredProperties = new int[8];
        monopolies = new ArrayList<>();
        properties = new ArrayList<>();
        boardPos = 0;
        money = 1500;
        rolledDoubles = false;
        timeInJail = 0;
        doublesInARow = 0;
        utilitiesOwned = 0;
    }

    public void addBoardPos(int amount) {
        boardPos += amount;

        if (boardPos >= 40) {
            boardPos %= 40;
            money += 200;
        }
    }

    public void setBoardPos(int tile) {
        boardPos = tile;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public void subtractMoney(int amount) {
        money -= amount;
    }

    public int getBoardPos() {
        return boardPos;
    }

    public ArrayList<Integer> getProperties() {
        return properties;
    }

    public void addProperty(int property) {
        int color = BoardData.propertyData.get(property).getColor();
        properties.add(property);
        if (color == -1) {
            return;
        } else if (color == 9) { // it's a utility
            utilitiesOwned++;
            return;
        }
        coloredProperties[color - 1]++;
        if (!monopolies.contains(color) && (color == 1 || color == 8)) {
            if (coloredProperties[color - 1] == 2) {
                monopolies.add(color);
                Discord.append("Monopoly " + color + " has been obtained!!!");
            }
        } else {
            if (coloredProperties[color - 1] == 3) {
                monopolies.add(color);
                Discord.append("Monopoly " + color + " has been obtained!!!");
            }
        }
    }

    public static void payProperty(int property, int ownerID, int amountRolled, Player payer) {
        int amount = -1;
        Player owner = Player.playerObjects.get(ownerID);
        Property propertyObject = BoardData.propertyData.get(property);
        switch (propertyObject.getTileType()) {
            case 0:
                amount = propertyObject.getRent();
                if (owner.monopolies.contains(propertyObject.getColor())) {
                    amount = (propertyObject.getHouseCount() == 0) ?
                            amount * 2 :
                            propertyObject.getHousesRent(propertyObject.getHouseCount());

                }
                break;
            case 1:
                ArrayList<Integer> properties = owner.getProperties();
                int railroadsOwned = 0;
                for (int i = 0; i < properties.size(); i++) {
                    if (BoardData.tileIDs[properties.get(i)] == 1) {
                        railroadsOwned++;
                    }
                }
                amount = 25 * (int) Math.pow(2, (railroadsOwned - 1));
                break;
            case 2:
                if (owner.utilitiesOwned == 1) {
                    amount = 4 * amountRolled;
                } else {
                    amount = 10 * amountRolled;
                }
                break;
        }
        payer.subtractMoney(amount);
        owner.addMoney(amount);

        Discord.append("You Landed On " + Player.playerNames.get(ownerID) + "'s Property.\nYou Paid $" + amount);
    }

    public int getMoney() {
        return money;
    }

    public boolean getRolledDoubles() {
        return rolledDoubles;
    }
    public void setRolledDoubles(boolean rolledDoubles) {
        this.rolledDoubles = rolledDoubles;
    }

    public void increaseTimeInJail(Jail jail) {
        timeInJail += 1;
        if (timeInJail >= 4) {
            jail.removePlayer(Player.playerNames.indexOf(name));
            Discord.append(name + " spent 3 turns in jail, so they were released on good behaviour");
        }
    }

    public void resetDoublesInARow() {
        doublesInARow = 0;
    }

    public void addDoubleInARow() {
        doublesInARow++;
    }

    public int getDoublesInARow() {
        return doublesInARow;
    }

    public void setTimeInJail(int timeInJail) {
        this.timeInJail = timeInJail;
    }

    public int getUtilitiesOwned() {
        return utilitiesOwned;
    }

    public void addGetOutOfJailFree() {getOutOfJailFree++;}

    public int getGetOutOfJailFree() {return getOutOfJailFree;}

    public void removeGetOutOfJailFree() {getOutOfJailFree--;}

    public ArrayList<Integer> getMonopolies() {
        return monopolies;
    }

    public void setPropertyMenu(PropertyMenu propertyMenu) {
        this.propertyMenu = propertyMenu;
    }

    public PropertyMenu getPropertyMenu() {
        return propertyMenu;
    }

//    public static String compilePlayer(Player player) {
//        StringBuilder builder = new StringBuilder("P");
//        for (Integer i : player.getProperties()) {
//
//        }
//    }
}
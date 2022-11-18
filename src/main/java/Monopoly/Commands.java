package Monopoly;

import java.io.IOException;

import Monopoly.*;


public class Commands {
    public static void CallFunction(String command, String[] args) throws IOException {
        switch (command) {
            case "-clearplayers" -> Monopoly.ClearPlayers();
            case "-addplayer" -> Monopoly.AddPlayer(args);
            case "-removeplayer" -> Monopoly.RemovePlayer(args);
            case "-displayplayers" -> Monopoly.DisplayPlayers();
            case "-start" -> Monopoly.Start();
            case "-money" -> Monopoly.DisplayMoney();
            case "-roll" -> Monopoly.Roll();
            case "-buy" -> Monopoly.BuyProperty();
            case "-auction" -> Monopoly.AuctionProperty();
            case "-bid" -> Monopoly.BidOnAuction(args);
            case "-quit" -> Monopoly.QuitBid();
            case "-boardpos" -> Monopoly.BoardPos();
        }
    }
}
package Monopoly;

import java.util.HashMap;
import java.util.Map;

public class BoardData {
    public static Map<Integer, Property> propertyData;
    public static Map<Integer, String> imageLinks;
    public static int[] tileIDs;
    public static int[] monopolyData;

    public static void innit() {
        System.out.println();
        propertyData = new HashMap<>();
        imageLinks = new HashMap<>();
        // 0 is property, 1 is railroad, 2 is chance, 3 is chest, 4 is electric / water works, 5 is other (taxes, jail, go, etc)
        tileIDs = new int[]{5, 0, 3, 0, 5, 1, 0, 2, 0, 0, 5, 0, 4, 0, 0, 1, 0, 3, 0, 0, 5, 0, 2, 0, 0, 1, 0, 0, 4, 0, 5, 0, 0, 3, 0, 1, 2, 0, 5, 0};

        // Brown, Light Blue, Magenta, Orange, Red, Yellow, Green, Dark Blue
        // 1      2           3        4       5    6       7      8     
        monopolyData = new int[]{0, 1, 0, 1, 0, 0, 2, 0, 2, 2, 0, 3, 0, 3, 3, 0, 4, 0, 4, 4, 0, 5, 0, 5, 5, 0, 6, 6, 0, 6, 0, 7, 7, 0, 7, 0, 0, 8, 0, 8};

        // board pos, cost, rent, rent with houses 1, 2, 3, 4, hotel cost, cost for houses
        propertyData.put(1, new Property(1, 60, 2, new int[]{10, 30, 90, 160, 250}, 50, 1));
        propertyData.put(3, new Property(3, 60, 4, new int[]{20, 60, 180, 320, 450}, 50, 1));
        propertyData.put(5, new Railroad(5));
        propertyData.put(6, new Property(6, 100, 6, new int[]{30, 90, 270, 400, 550}, 50, 2));
        propertyData.put(8, new Property(8, 100, 6, new int[]{30, 90, 270, 400, 550}, 50, 2));
        propertyData.put(9, new Property(9, 120, 8, new int[]{40, 100, 300, 450, 600}, 50, 2));
        propertyData.put(11, new Property(11, 140, 10, new int[]{50, 150, 450, 625, 750}, 100, 3));
        propertyData.put(13, new Property(13, 140, 10, new int[]{50, 150, 450, 625, 750}, 100, 3));
        propertyData.put(14, new Property(14, 160, 12, new int[]{60, 180, 500, 700, 900}, 100, 3));
        propertyData.put(15, new Railroad(15));
        propertyData.put(16, new Property(16, 180, 14, new int[]{70, 200, 550, 750, 950}, 100, 4));
        propertyData.put(18, new Property(18, 180, 14, new int[]{70, 200, 550, 750, 950}, 100, 4));
        propertyData.put(19, new Property(19, 200, 16, new int[]{80, 220, 600, 800, 1000}, 100, 4));
        propertyData.put(21, new Property(21, 220, 18, new int[]{90, 250, 700, 875, 1050}, 150, 5));
        propertyData.put(23, new Property(23, 220, 18, new int[]{90, 250, 700, 875, 1050}, 150, 5));
        propertyData.put(24, new Property(24, 240, 20, new int[]{100, 300, 750, 925, 1100}, 150, 5));
        propertyData.put(25, new Railroad(25));
        propertyData.put(26, new Property(26, 260, 22, new int[]{110, 330, 800, 975, 1150}, 150, 6));
        propertyData.put(27, new Property(27, 260, 22, new int[]{110, 330, 800, 975, 1150}, 150, 6));
        propertyData.put(29, new Property(29, 280, 24, new int[]{120, 360, 850, 1025, 1200}, 150, 6));
        propertyData.put(31, new Property(31, 300, 26, new int[]{130, 390, 900, 1100, 1275}, 200, 7));
        propertyData.put(32, new Property(32, 300, 26, new int[]{130, 390, 900, 1100, 1275}, 200, 7));
        propertyData.put(34, new Property(34, 320, 28, new int[]{150, 450, 1000, 1200, 1400}, 200, 7));
        propertyData.put(35, new Railroad(35));
        propertyData.put(37, new Property(37, 350, 35, new int[]{175, 500, 1100, 1300, 1500}, 200, 8));
        propertyData.put(39, new Property(39, 400, 50, new int[]{200, 600, 1400, 1700, 2000}, 200, 8));

        imageLinks.put(1, "https://imgur.com/jsuD0D1");
        imageLinks.put(3, "https://imgur.com/XGYQL5A");
        imageLinks.put(5, "https://imgur.com/x7OlnHo");
        imageLinks.put(6, "https://imgur.com/l5f1Gys");
        imageLinks.put(8, "https://imgur.com/wEPoXaA");
        imageLinks.put(9, "https://imgur.com/JbRQmYw");
        imageLinks.put(11, "https://imgur.com/IJD2ba2");
        imageLinks.put(12, "https://imgur.com/ynpLb9e");
        imageLinks.put(13, "https://imgur.com/qw6oF8E");
        imageLinks.put(14, "https://imgur.com/GuNRSJq");
        imageLinks.put(15, "https://imgur.com/ZMUuLAW");
        imageLinks.put(16, "https://imgur.com/XYGm6vT");
        imageLinks.put(18, "https://imgur.com/GIKGdY3");
        imageLinks.put(19, "https://imgur.com/7QstIQV");
        imageLinks.put(21, "https://imgur.com/Z0z43w2");
        imageLinks.put(23, "https://imgur.com/UUaYb82");
        imageLinks.put(24, "https://imgur.com/fasxuoX");
        imageLinks.put(25, "https://imgur.com/kMTXjn1");
        imageLinks.put(26, "https://imgur.com/GVQWax5");
        imageLinks.put(27, "https://imgur.com/u0wyYu8");
        imageLinks.put(28, "https://imgur.com/T1zZ4Ds");
        imageLinks.put(29, "https://imgur.com/OvJDKFm");
        imageLinks.put(31, "https://imgur.com/YMpVlGr");
        imageLinks.put(32, "https://imgur.com/rpVFVWe");
        imageLinks.put(34, "https://imgur.com/aOogASA");
        imageLinks.put(35, "https://imgur.com/AxZwhDv");
        imageLinks.put(37, "https://imgur.com/NC7zUos");
        imageLinks.put(39, "https://imgur.com/gOcvAbb");
    }
}
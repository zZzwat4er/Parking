package com.example.parking.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class StringChecker {

    private static final String nameRegEx = "^[А-ЯЁA-Zа-яёa-z \\-]{2,}$";
    private static final String cardRegEx = "^\\d{1,10}$";
    private static final String passRegEx = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$";
    private static final String emailRegEx ="[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}";
    private static final String platesRegEx = "^[АВЕКМНОРСТУХABEKMHOPCTYXавекмнорстухabekmhopctyx]{1}\\d{3}(?<!000)[АВЕКМНОРСТУХABEKMHOPCTYXавекмнорстухabekmhopctyx]{2}\\d{2,3}(?<!000|00|0\\d\\d)$";

    private static final Map<Character, Character> translate = new HashMap<Character, Character>(){{
        put('a', 'а');
        put('b', 'в');
        put('e', 'е');
        put('k', 'к');
        put('m', 'м');
        put('h', 'н');
        put('o', 'о');
        put('p', 'р');
        put('c', 'с');
        put('t', 'т');
        put('y', 'у');
        put('x', 'х');
    }};

//    private static final String rus = "авекмнорстух";
//    private static final String end = "abekmhopctyx";

    public static boolean isName(String s){return Pattern.matches(nameRegEx, s);}
    public static boolean isCard(String s){return Pattern.matches(cardRegEx, s);}
    public static boolean isPassword(String s){return Pattern.matches(passRegEx, s);}
    public static boolean isEmail(String s){return Pattern.matches(emailRegEx, s);}
    public static boolean isPlates(String s){return Pattern.matches(platesRegEx, s);}

    public static String eng2rus(String s){
        String newString = "";
        for(int i = 0; i < s.length(); i++){
            if(translate.get(s.charAt(i)) == null) newString += s.charAt(i);
            else newString += translate.get(s.charAt(i));
        }
        return newString;
    }
}

package com.example.scannerapp;

import java.util.HashMap;

public class Similarity {

    private HashMap<Integer, String> deviceList;

    public Similarity(HashMap<Integer, String> newDeviceList){
        deviceList = newDeviceList;
    }

    public HashMap<Integer, Integer> getSimilar(String query){
        HashMap<Integer, String> names = (HashMap)deviceList.clone();
        for(int key: names.keySet()){
            String name = names.get(key);
            names.put(key, name);
        }
        query = query.toLowerCase();
        HashMap<Character, Integer> queryChars = new HashMap<>();
        for(int i = 0; i < query.length(); i++){
            if (queryChars.containsKey(query.charAt(i))){
                queryChars.put(query.charAt(i), queryChars.get(query.charAt(i)));
            }
            else{
                queryChars.put(query.charAt(i), 1);
            }
        }
        HashMap<Integer, Integer> mostSimilar = new HashMap<>();
        for(int key: names.keySet()){
            HashMap<Character, Integer> nameChars = new HashMap<>();
            String name = names.get(key);
            for(int i = 0; i < name.length(); i++){
                if (nameChars.containsKey(name.charAt(i))){
                    nameChars.put(name.charAt(i), nameChars.get(name.charAt(i)));
                }
                else{
                    nameChars.put(name.charAt(i), 1);
                }
            }
            int dotProduct = 0;
            for(char character: queryChars.keySet()){
                if (nameChars.containsKey(character)){
                    dotProduct += queryChars.get(character)*nameChars.get(character);
                }
            }
            mostSimilar.put(key, dotProduct/(name.length()*query.length()));
        }
        return mostSimilar;
    }

}

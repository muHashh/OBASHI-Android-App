package com.example.scannerapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class Similarity {

    private HashMap<Integer, String> deviceList;

    public Similarity(HashMap<Integer, String> newDeviceList){
        deviceList = newDeviceList;
    }

    public HashMap<Integer, Double> getSimilar(String query){
        HashMap<Integer, String> names = (HashMap)deviceList.clone();
        for(int key: names.keySet()){
            String name = names.get(key).toLowerCase();
            names.put(key, name);
        }
        query = query.toLowerCase();
        HashMap<Character, Integer> queryChars = new HashMap<>();
        for(int i = 0; i < query.length(); i++){
            if (queryChars.containsKey(query.charAt(i))){
                queryChars.put(query.charAt(i), queryChars.get(query.charAt(i))+1);
            }
            else{
                queryChars.put(query.charAt(i), 1);
            }
        }
        HashMap<Integer, Double> mostSimilar = new HashMap<>();
        for(int key: names.keySet()){
            HashMap<Character, Integer> nameChars = new HashMap<>();
            String name = names.get(key);
            for(int i = 0; i < name.length(); i++){
                if (nameChars.containsKey(name.charAt(i))){
                    nameChars.put(name.charAt(i), nameChars.get(name.charAt(i))+1);
                }
                else{
                    nameChars.put(name.charAt(i), 1);
                }
            }
            double dotProduct = (double)dotProduct(queryChars, nameChars);
            double queryLength = Math.sqrt((double)dotProduct(queryChars, queryChars));
            double nameLength = Math.sqrt((double)dotProduct(nameChars, nameChars));
            double distance = dotProduct/(nameLength*queryLength);
            mostSimilar.put(key, distance);
        }
        for(int key: mostSimilar.keySet()){
            String name = names.get(key);
            int[][] stringDistanceTable = new int[name.length()+1][query.length()+1];
            for(int i = 0; i < name.length()+1; i++){
                stringDistanceTable[i][0] = 0;
            }
            for(int i = 0; i < query.length()+1; i++){
                stringDistanceTable[0][i] = 0;
            }
            for(int i = 1; i < name.length()+1; i++){
                for(int j = 1; j < query.length()+1; j++){
                    if(name.charAt(i-1) == query.charAt(j-1)){
                        stringDistanceTable[i][j] = stringDistanceTable[i-1][j-1];
                    }
                    else{
                        int minimum = stringDistanceTable[i-1][j-1];
                        if(stringDistanceTable[i][j-1] < minimum){
                            minimum = stringDistanceTable[i][j-1];
                        }
                        if(stringDistanceTable[i-1][j] < minimum){
                            minimum = stringDistanceTable[i-1][j];
                        }
                        stringDistanceTable[i][j] = minimum + 1;
                    }
                }
            }
            double distance = stringDistanceTable[name.length()][query.length()];
            double largestLength = query.length();
            if(name.length() > query.length()){
                largestLength = name.length();
            }
            mostSimilar.put(key, mostSimilar.get(key)/2 + (largestLength-distance)/(2*largestLength));
        }

        return sortHashMapByValues(mostSimilar);
    }

    public int dotProduct(HashMap<Character, Integer> chars1,
                          HashMap<Character, Integer> chars2) {
        int dotProduct = 0;
        for(char character: chars1.keySet()){
            if (chars2.containsKey(character)){
                dotProduct += chars1.get(character)*chars2.get(character);
            }
        }
        return dotProduct;
    }

    public LinkedHashMap<Integer, Double> sortHashMapByValues(HashMap<Integer, Double> passedMap) {
        List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Double> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.reverse(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<Integer, Double> sortedMap =
                new LinkedHashMap<>();

        Iterator<Double> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Double val = valueIt.next();
            Iterator<Integer> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                Double comp1 = passedMap.get(key);
                Double comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

}

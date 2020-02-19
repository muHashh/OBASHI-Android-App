package com.example.scannerapp;

import com.example.scannerapp.ui.search.Similarity;
import org.junit.Test;
import java.util.HashMap;
import java.util.LinkedHashMap;
import static org.junit.Assert.*;

/**
 * This file tests the file Similarity.java, in the package ui.search
 */
public class SimilarityTest {

    @Test
    public void emptyListReturnsEmptyList(){
        Similarity similarity = new Similarity();
        LinkedHashMap<Integer, Double> returnList = similarity.getSimilar(null,"");
        assertEquals(returnList, null);
        HashMap<Integer, String> newMap = new HashMap<>();
        returnList = similarity.getSimilar(newMap,"");
        assertEquals(returnList, null);
    }

    @Test
    public void emptyQueryReturnsEmptyList(){
        Similarity similarity = new Similarity();
        LinkedHashMap<Integer, Double> returnList = similarity.getSimilar(null,null);
        assertEquals(returnList, null);
        returnList = similarity.getSimilar(null,"");
        assertEquals(returnList, null);
    }

}

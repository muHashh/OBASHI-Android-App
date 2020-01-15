package com.example.scannerapp;

import com.example.scannerapp.ConnectionHelper.HttpJsonParser;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpJsonParserTest {

    @Test
    public void keyData_isCorrect() {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        assertEquals("data", httpJsonParser.getKeyData());
    }

    @Test
    public void keyMessage_isCorrect() {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        assertEquals("message", httpJsonParser.getKeyMessage());
    }

    @Test
    public void keySuccess_isCorrect() {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        assertEquals("success", httpJsonParser.getKeySuccess());
    }
}

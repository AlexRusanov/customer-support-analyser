package com.qualityunit;

import com.qualityunit.analyser.Analyser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Application {
    public static void main(String[] args) {
        try(BufferedReader reader = new BufferedReader(
                new StringReader(
                    "7\n" +
                    "C 1.1 8.15.1 P 15.10.2012 83\n" +
                    "C 1 10.1 P 01.12.2012 65\n" +
                    "C 1.1 5.5.1 P 01.11.2012 117\n" +
                    "D 1.1 8 P 01.01.2012-01.12.2012\n" +
                    "C 3 10.2 N 02.10.2012 100\n" +
                    "D 1 * P 08.10.2012-20.11.2012\n" +
                    "D 3 10 P 01.12.2012"))) {
                        Analyser.analyse(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

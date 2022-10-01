/*
 * Copyright (c) 2022. Miahinantenaina RAFEHIVOLA
 * All rights reserved.
 */

package org.kata.bowling;


import org.junit.jupiter.api.Test;
import org.kata.bowling.record.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PartieTests {

    private static Partie partie;

    static ICoup iCoup;

    @Test
    public void test10PairsOf9AndMISS(){
        iCoup = new Miss(9)
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9));
        partie = new Partie("test10PairsOf9AndMISS", iCoup);
        var tableauDeScore = partie.calculateScore();
        assertEquals(90, tableauDeScore.stream().mapToInt(s -> s).sum());
    }


    @Test
    public void test12Rolls12STRIKES(){
        iCoup = new Strike()
                .then(new Strike())
                .then(new Strike())
                .then(new Strike())
                .then(new Strike())
                .then(new Strike())
                .then(new Strike())
                .then(new Strike())
                .then(new Strike())
                .then(new Strike())
                .then(new Strike())
                .then(new Strike());
        partie = new Partie("test12Rolls12STRIKES", iCoup);
        var tableauDeScore = partie.calculateScore();
        assertEquals(300, tableauDeScore.stream().mapToInt(s -> s).sum());
    }

    @Test
    public void test10PairsOf5AndSPARE(){
        iCoup = new Spare(5)
                .then(new Spare(5))
                .then(new Spare(5))
                .then(new Spare(5))
                .then(new Spare(5))
                .then(new Spare(5))
                .then(new Spare(5))
                .then(new Spare(5))
                .then(new Spare(5))
                .then(new Spare(5))
                .then(new Miss(5));
        partie = new Partie("test10PairsOf5AndSPARE", iCoup);
        var tableauDeScore = partie.calculateScore();
        assertEquals(150, tableauDeScore.stream().mapToInt(s -> s).sum());
    }

    @Test
    public void testInvalidMissValue(){
        iCoup = new Miss(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            partie = new Partie("testInvalidMissValue", iCoup);
            partie.calculateScore();
        });

        assertEquals("Illegal Miss value", exception.getMessage());
    }

    @Test
    public void testInvalidSpareValue(){
        iCoup = new Spare(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            partie = new Partie("testInvalidSpareValue", iCoup);
            partie.calculateScore();
        });

        assertEquals("Illegal Spare value", exception.getMessage());
    }

    @Test
    public void testInvalidPartie(){
        iCoup = new Miss(9)
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9))
                .then(new Miss(9));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            partie = new Partie("testInvalidPartie", iCoup);
            partie.calculateScore();
        });

        assertEquals("Partie non valide", exception.getMessage());
    }
}

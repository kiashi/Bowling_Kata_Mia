/*
 * Copyright (c) 2022. Miahinantenaina RAFEHIVOLA
 * All rights reserved.
 */

package org.kata.bowling;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kata.bowling.record.ICoup;
import org.kata.bowling.record.Miss;
import org.kata.bowling.record.Spare;
import org.kata.bowling.record.Strike;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartieTests {

    private static Partie partie;

    @Mock
    static ICoup iCoup;

    @BeforeAll
    public static void setup(){
        partie = Partie.builder().nomJoueur("Test Unitaire").build();
    }


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
        partie.setChainCoup(iCoup);
        List<Integer> tableauDeScore = partie.calculateScore();
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
        partie.setChainCoup(iCoup);
        List<Integer> tableauDeScore = partie.calculateScore();
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
        partie.setChainCoup(iCoup);
        List<Integer> tableauDeScore = partie.calculateScore();
        assertEquals(150, tableauDeScore.stream().mapToInt(s -> s).sum());
    }
}

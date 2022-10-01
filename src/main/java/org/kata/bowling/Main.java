/*
 * Copyright (c) 2022. Miahinantenaina RAFEHIVOLA
 * All rights reserved.
 */

package org.kata.bowling;

import org.kata.bowling.record.Miss;
import org.kata.bowling.record.Spare;
import org.kata.bowling.record.Strike;

public class Main {
    public static void main(String[] args) {

        System.out.println("--------------START---------------");

        var coup = new Miss(5)
                .then(new Miss(6))
                .then(new Spare(5))
                .then(new Miss(9))
                .then(new Miss(5))
                .then(new Miss(5))
                .then(new Miss(5))
                .then(new Strike())
                .then(new Miss(5))
                .then(new Miss(5));

        Partie partieOnePoint = Partie.builder().nomJoueur("Onepoint : ").chainCoup(coup).build();
        var tableauDeScoreOnePoint = partieOnePoint.calculateScore();
        var scoreOnePoint = tableauDeScoreOnePoint.stream().mapToInt(s -> s).sum();
        System.out.println(partieOnePoint.getNomJoueur() + tableauDeScoreOnePoint + " => " + scoreOnePoint);


        System.out.println("--------------X---------------");


        var coup1 = new Miss(5)
                .then(new Miss(1))
                .then(new Miss(5))
                .then(new Miss(5))
                .then(new Miss(5))
                .then(new Miss(1))
                .then(new Miss(5))
                .then(new Miss(5))
                .then(new Miss(9))
                .then(new Strike())
                .then(new Miss(6));

        Partie partieMia = Partie.builder().nomJoueur("Mia : ").chainCoup(coup1).build();
        var tableauDeScoreMia = partieMia.calculateScore();
        var scoreMia = tableauDeScoreMia.stream().mapToInt(s -> s).sum();
        System.out.println(partieMia.getNomJoueur() + tableauDeScoreMia + " => " + scoreMia);

        System.out.println("--------------THANKS---------------");

    }


}
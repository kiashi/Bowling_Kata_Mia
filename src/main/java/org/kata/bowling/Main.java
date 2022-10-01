/*
 * Copyright (c) 2022. Miahinantenaina RAFEHIVOLA
 * All rights reserved.
 */

package org.kata.bowling;

import org.kata.bowling.record.Miss;
import org.kata.bowling.record.Partie;
import org.kata.bowling.record.Spare;
import org.kata.bowling.record.Strike;

public class Main {
    public static void main(String[] args) {

        System.out.println("--------------START MAIN BOWLING KATA---------------");

        var coupOnePoint = new Miss(1)
                .then(new Miss(6))
                .then(new Spare(5))
                .then(new Miss(9))
                .then(new Miss(5))
                .then(new Miss(5))
                .then(new Miss(5))
                .then(new Strike())
                .then(new Miss(5))
                .then(new Miss(5));

        var partieOnePoint = new Partie("Onepoint : ", coupOnePoint);
        var tableauDeScoreOnePoint = partieOnePoint.calculateScore();
        var scoreOnePoint = tableauDeScoreOnePoint.stream().mapToInt(s -> s).sum();
        System.out.println(partieOnePoint.nomJoueur() + tableauDeScoreOnePoint + " => " + scoreOnePoint);

        assert scoreOnePoint == 80;


        System.out.println("--------------X---------------");


        var coupMia = new Miss(5)
                .then(new Miss(1))
                .then(new Miss(5))
                .then(new Miss(5))
                .then(new Miss(5))
                .then(new Spare(9))
                .then(new Miss(5))
                .then(new Miss(9))
                .then(new Miss(3))
                .then(new Miss(6));

        var partieMia = new Partie("Mia : ", coupMia);
        var tableauDeScoreMia = partieMia.calculateScore();
        var scoreMia = tableauDeScoreMia.stream().mapToInt(s -> s).sum();
        System.out.println(partieMia.nomJoueur() + tableauDeScoreMia + " => " + scoreMia);

        assert scoreMia == 59;

        System.out.println("--------------THANKS---------------");

    }


}
/*
 * Copyright (c) 2022. Miahinantenaina RAFEHIVOLA
 * All rights reserved.
 */

package org.kata.bowling.record;

import org.kata.bowling.utils.PartieUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.kata.bowling.constantes.ConstantesCoup.MAX_ALLOWED_WITHOUT_BONUS;
import static org.kata.bowling.constantes.ConstantesException.PARTIE_NON_VALIDE;


public record Partie(String nomJoueur, ICoup chainCoup) {

    public List<Integer> calculateScore() {
        var scoreNonTraite = buildTableauScore();
        if(PartieUtils.isPartieValid.test(scoreNonTraite)) return tableauScore();
        else throw new IllegalArgumentException(PARTIE_NON_VALIDE);
    }

    private List<Integer> tableauScore() {
        var tableauDeScoreWithBonus = new ArrayList<Integer>();
        var scoreNonTraite = buildTableauScore();
        scoreNonTraite.forEach((key, value) -> {
            if(key < MAX_ALLOWED_WITHOUT_BONUS) {
                if(PartieUtils.isMiss.test(value)) tableauDeScoreWithBonus.add(PartieUtils.getScore.apply(value));
                if(PartieUtils.isSpare.test(value)) tableauDeScoreWithBonus.add(10 + PartieUtils.getScore.apply(scoreNonTraite.get(key +1)));
                if(PartieUtils.isStrike.test(value)) tableauDeScoreWithBonus.add(10 + PartieUtils.getScoreStrike.apply(scoreNonTraite.get(key +1)) + PartieUtils.getScoreStrike.apply(scoreNonTraite.get(key +2)));
            }
        });
        return PartieUtils.getListeScoreFinal.apply(tableauDeScoreWithBonus);
    }

    private Map<Integer, String> buildTableauScore() {
        var scoreInMapWithIndex = new HashMap<Integer, String>();
        var index = new AtomicInteger(0);
        PartieUtils.handle(this.chainCoup, scoreInMapWithIndex , index);
        return scoreInMapWithIndex;
    }
}

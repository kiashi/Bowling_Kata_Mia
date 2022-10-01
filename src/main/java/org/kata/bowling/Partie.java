/*
 * Copyright (c) 2022. Miahinantenaina RAFEHIVOLA
 * All rights reserved.
 */

package org.kata.bowling;

import lombok.Builder;
import lombok.Data;
import org.kata.bowling.record.ICoup;
import org.kata.bowling.utils.PartieUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
public class Partie {

    private String nomJoueur;
    private ICoup chainCoup;

    public List<Integer> calculateScore() {
        List<Integer> score;
        Map<Integer, String> scoreNonTraite = buildTableauScore();
        if(PartieUtils.isPartieValid.test(scoreNonTraite)){
            score = tableauScore();
        } else {
            throw new IllegalArgumentException("Partie non valide");
        };
        return score;
    }

    private List<Integer> tableauScore() {
        List<Integer> tableauDeScoreWithBonus = new ArrayList<>();
        Map<Integer, String> scoreNonTraite = buildTableauScore();
        scoreNonTraite.forEach((key, value) -> {
            if(PartieUtils.isMiss.test(value)) tableauDeScoreWithBonus.add(PartieUtils.getScore.apply(value));
            if(PartieUtils.isSpare.test(value)) PartieUtils.gestionSpare(tableauDeScoreWithBonus, scoreNonTraite, key, value);
            if(PartieUtils.isStrike.test(value)) PartieUtils.gestionStrike(tableauDeScoreWithBonus, scoreNonTraite, key);
        });
        return PartieUtils.getListeScoreFinal.apply(tableauDeScoreWithBonus);
    }

    private Map<Integer, String> buildTableauScore() {
        Map<Integer, String> scoreInMapWithIndex = new HashMap<>();
        AtomicInteger index = new AtomicInteger(0);
        PartieUtils.handle(this.chainCoup, scoreInMapWithIndex , index);
        return scoreInMapWithIndex;
    }
}

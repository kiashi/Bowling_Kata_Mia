/*
 * Copyright (c) 2022. Miahinantenaina RAFEHIVOLA
 * All rights reserved.
 */

package org.kata.bowling.utils;

import org.kata.bowling.constantes.ConstantesCoup;
import org.kata.bowling.record.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static org.kata.bowling.constantes.ConstantesCoup.*;

public class PartieUtils {

    public static Predicate<String> isSpare = coup -> coup.contains(ConstantesCoup.SPARE);

    public static Predicate<String> isStrike = coup -> coup.contains(ConstantesCoup.STRIKE);

    public static Predicate<String> isMiss = coup -> coup.contains(ConstantesCoup.MISS);

    public static Function<String, Integer> getScore = coup -> Integer.parseInt(coup.replaceAll("[-/X]", ""));

    public static BiPredicate<Map<Integer, String>, Integer> isLastIndex = (scoreInMapWithIndex, index) ->{
        Map.Entry<Integer, String> lastEntry = scoreInMapWithIndex.entrySet().stream().reduce((one, two) -> two).get();
        return Objects.equals(lastEntry.getKey(), index);
    };

    public static Function<List<Integer>,List<Integer>> getListeScoreFinal = (List<Integer> tableauDeScoreWithBons) -> {
        int initTab = tableauDeScoreWithBons.size() > MAX_ALLOWED ? tableauDeScoreWithBons.size() - MAX_ALLOWED : 0;
        IntStream.range(0, initTab).forEach((s) -> tableauDeScoreWithBons.remove(tableauDeScoreWithBons.size()-1));
        return tableauDeScoreWithBons;
    };

    public static void handle(ICoup coup, Map<Integer, String> scoreInMapWithIndex, AtomicInteger count) {
        if (coup instanceof Miss m) {
            scoreInMapWithIndex.put(count.get(), m.points().toString().concat(ConstantesCoup.MISS));
            count.getAndIncrement();
        } else if (coup instanceof Spare s) {
            scoreInMapWithIndex.put(count.get(),  s.points().toString().concat(ConstantesCoup.SPARE));
            count.getAndIncrement();
        } else if (coup instanceof Strike) {
            scoreInMapWithIndex.put(count.get(), "10".concat(ConstantesCoup.STRIKE));
            count.getAndIncrement();
        } else if (coup instanceof Chain a) {
            handle(a.a(), scoreInMapWithIndex, count);
            handle(a.b(), scoreInMapWithIndex, count);
        }
    }

    public static Predicate<Map<Integer, String>> isPartieValid = (Map<Integer, String> tableaudeScoreNonTraite) -> {
        List<String> tableauList = new ArrayList<>(tableaudeScoreNonTraite.values());
        int maxLengthAllowed = 12;

        long nombreMissInvalid = tableauList.stream()
                .filter(value -> value.endsWith(ConstantesCoup.MISS) && value.length() > 2)
                .count();

        if (nombreMissInvalid > 0) throw new IllegalArgumentException("Partie non valide");

        if(tableauList.size() > maxLengthAllowed) {
            return false;
        } else {
            if(tableauList.size() == maxLengthAllowed){
                return isSpare.test(tableauList.get(maxLengthAllowed-2)) || isStrike.test(tableauList.get(maxLengthAllowed-2));
            } else if(tableauList.size() == maxLengthAllowed-1){
                return isSpare.test(tableauList.get(maxLengthAllowed-3)) || isStrike.test(tableauList.get(maxLengthAllowed-3)) ;
            }else return tableauList.size() == maxLengthAllowed-2;
        }
    };

    public static void gestionSpare(List<Integer> tableauDeScoreWithBonus, Map<Integer, String> scoreNonTraite, Integer key, String value) {
        if(PartieUtils.isLastIndex.test(scoreNonTraite, key)) {
            tableauDeScoreWithBonus.add(PartieUtils.getScore.apply(value));
        } else {
            if(scoreNonTraite.containsKey(key +MIN_BONUS_ALLOWED)) {
                tableauDeScoreWithBonus.add(PartieUtils.getScore.apply(scoreNonTraite.get(key +MIN_BONUS_ALLOWED)) + MAX_ALLOWED);
            }
        }
    }

    public static void gestionStrike(List<Integer> tableauDeScoreWithBonus, Map<Integer, String> scoreNonTraite, Integer key) {
        if(PartieUtils.isLastIndex.test(scoreNonTraite, key)) {
            tableauDeScoreWithBonus.add(MAX_ALLOWED);
        } else {
            gestionStrikeWithBonus(tableauDeScoreWithBonus, scoreNonTraite, key);
        }
    }

    private static void gestionStrikeWithBonus(List<Integer> tableauDeScoreWithBonus, Map<Integer, String> scoreNonTraite, Integer key) {
        if(scoreNonTraite.containsKey(key +MIN_BONUS_ALLOWED)) {
            if(scoreNonTraite.containsKey(key +MAX_BONUS_ALLOWED)){
                if(PartieUtils.isStrike.test(scoreNonTraite.get(key +1))){
                    if(PartieUtils.isStrike.test(scoreNonTraite.get(key +MAX_BONUS_ALLOWED))){
                        tableauDeScoreWithBonus.add(PartieUtils.getScore.apply(scoreNonTraite.get(key +MIN_BONUS_ALLOWED))
                                + PartieUtils.getScore.apply(scoreNonTraite.get(key +MAX_BONUS_ALLOWED)) + MAX_ALLOWED);
                    } else {
                        tableauDeScoreWithBonus.add(PartieUtils.getScore.apply(scoreNonTraite.get(key +MIN_BONUS_ALLOWED)) + MAX_ALLOWED);
                    }
                }else {
                    if(PartieUtils.isStrike.test(scoreNonTraite.get(key +MAX_BONUS_ALLOWED))){
                        tableauDeScoreWithBonus.add(MAX_ALLOWED);
                    } else {
                        tableauDeScoreWithBonus.add(PartieUtils.getScore.apply(scoreNonTraite.get(key +MIN_BONUS_ALLOWED))
                                + PartieUtils.getScore.apply(scoreNonTraite.get(key +MAX_BONUS_ALLOWED)) + MAX_ALLOWED);
                    }
                }
            } else {
                if(PartieUtils.isStrike.test(scoreNonTraite.get(key +MIN_BONUS_ALLOWED))){
                    tableauDeScoreWithBonus.add(MAX_ALLOWED);
                } else {
                    tableauDeScoreWithBonus.add(PartieUtils.getScore.apply(scoreNonTraite.get(key +MIN_BONUS_ALLOWED)) + MAX_ALLOWED);
                }

            }
        }
    }
}



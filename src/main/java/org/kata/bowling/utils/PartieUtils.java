/*
 * Copyright (c) 2022. Miahinantenaina RAFEHIVOLA
 * All rights reserved.
 */

package org.kata.bowling.utils;

import org.kata.bowling.constantes.ConstantesCoup;
import org.kata.bowling.record.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static org.kata.bowling.constantes.ConstantesCoup.*;

public class PartieUtils {

    public static Predicate<String> isSpare = coup -> coup.contains(SPARE);

    public static Predicate<String> isStrike = STRIKE::equals;

    public static Predicate<String> isMiss = coup -> coup.contains(ConstantesCoup.MISS);

    public static Function<String, Integer> getScore = coup -> Integer.parseInt(coup.replaceAll("[-/X]", ""));

    public static Function<String, Integer> getScoreStrike = coup -> {
        if(isStrike.test(coup)) return Integer.parseInt(coup.replaceAll("[X]", "10"));
        else return getScore.apply(coup);
    };

    public static Function<List<Integer>,List<Integer>> getListeScoreFinal = (List<Integer> tableauDeScoreWithBons) -> {
        var initTab = tableauDeScoreWithBons.size() > MAX_ALLOWED_WITH_BONUS ? tableauDeScoreWithBons.size() - MAX_ALLOWED_WITH_BONUS : 0;
        IntStream.range(0, initTab).forEach((s) -> tableauDeScoreWithBons.remove(tableauDeScoreWithBons.size()-1));
        return tableauDeScoreWithBons;
    };

    public static void handle(ICoup coup, Map<Integer, String> scoreInMapWithIndex, AtomicInteger count) {
        if (coup instanceof Miss m) {
            scoreInMapWithIndex.put(count.get(), m.points().toString().concat(ConstantesCoup.MISS));
            count.getAndIncrement();
        } else if (coup instanceof Spare s) {
            scoreInMapWithIndex.put(count.get(),  s.points().toString().concat(SPARE));
            count.getAndIncrement();
        } else if (coup instanceof Strike) {
            scoreInMapWithIndex.put(count.get(), ConstantesCoup.STRIKE);
            count.getAndIncrement();
        } else if (coup instanceof Chain a) {
            handle(a.a(), scoreInMapWithIndex, count);
            handle(a.b(), scoreInMapWithIndex, count);
        }
    }

    public static Predicate<Map<Integer, String>> isPartieValid = (Map<Integer, String> tableaudeScoreNonTraite) -> {
        var tableauSize = tableaudeScoreNonTraite.size();
        var index9 = tableaudeScoreNonTraite.get(MAX_ALLOWED_WITH_BONUS-3);
        var index10 = tableaudeScoreNonTraite.get(MAX_ALLOWED_WITH_BONUS-2);
        var index11 = tableaudeScoreNonTraite.get(MAX_ALLOWED_WITH_BONUS-1);
        if(tableauSize > MAX_ALLOWED_WITH_BONUS) return false;
        else {
            if(tableauSize == MAX_ALLOWED_WITH_BONUS) return isStrike.test(index11) && isStrike.test(index10);
            else if(tableauSize == MAX_ALLOWED_WITH_BONUS-1) return isSpare.test(index9) || isStrike.test(index9);
            else return tableauSize == MAX_ALLOWED_WITH_BONUS-2;
        }
    };

}



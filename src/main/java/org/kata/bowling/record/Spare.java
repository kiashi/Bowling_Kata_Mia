/*
 * Copyright (c) 2022. Miahinantenaina RAFEHIVOLA
 * All rights reserved.
 */

package org.kata.bowling.record;

import static org.kata.bowling.constantes.ConstantesException.SCORE_NON_VALIDE;

public record Spare(Integer points) implements ICoup {

    @Override
    public Integer points() {
        if(points >= 10) throw new IllegalArgumentException(String.format(SCORE_NON_VALIDE, "Spare"));
        else return points;
    }
}

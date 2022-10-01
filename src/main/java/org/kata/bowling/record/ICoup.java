/*
 * Copyright (c) 2022. Miahinantenaina RAFEHIVOLA
 * All rights reserved.
 */

package org.kata.bowling.record;

public sealed interface ICoup permits Chain, Miss, Spare, Strike {
    default ICoup then(ICoup suivant) {
        return new Chain(this, suivant);
    }
}

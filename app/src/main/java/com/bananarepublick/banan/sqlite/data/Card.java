package com.bananarepublick.banan.sqlite.data;

import com.orm.SugarRecord;

/**
 * Created by Banan on 04.03.2018.
 */

public class Card extends SugarRecord {

    public int number;

    public ViewCard viewCard;
    public Owner owner;


    public Card() {
    }

    public Card(int number, ViewCard viewCard, Owner owner) {

        this.number = number;
        this.viewCard = viewCard;
        this.owner = owner;


    }

    @Override
    public String toString() {
        return "ID = " + getId() +
                " number = " + number +
                " viewCard = " + viewCard.name +
                " Owner = " + owner.fio + "\n";
    }
}

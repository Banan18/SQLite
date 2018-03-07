package com.bananarepublick.banan.sqlite.data;

import com.orm.SugarRecord;

/**
 * Created by Banan on 02.03.2018.
 */

public class ViewCard extends SugarRecord {

    public String name;
    public int sale;

    public ViewCard() {

    }

    public ViewCard(String name, int sale) {

        this.name = name;
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "id = " + getId() +
                " name = " + name + '\'' +
                ", sale = " + sale + '\'' +
                "\n";
    }
}

package com.bananarepublick.banan.sqlite.data;

import com.orm.SugarRecord;

/**
 * Created by Banan on 05.03.2018.
 */

public class Owner extends SugarRecord {
    public String fio;
    public String number;

    public Owner() {
    }

    public Owner(String fio, String number) {
        this.fio = fio;
        this.number = number;
    }

    @Override
    public String toString() {
        return "id = " + getId() + "\n" +
                " fio = " + fio + "\n" +
                " number = " + number +
                "\n" + " " + "\n";
    }
}

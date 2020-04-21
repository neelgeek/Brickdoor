package com.example.brickdoor.models;

import java.util.Arrays;

public enum Badge {
    AWESOME(5),
    GOOD(4),
    MEDIOCRE(3),
    BAD(2),
    AWFUL(1),
    NOTAVAILABLE(0);

    private int id;

    Badge(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public static Badge parse(int id) {
        Badge badge = null;
        for (Badge b : Badge.values()) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }
}

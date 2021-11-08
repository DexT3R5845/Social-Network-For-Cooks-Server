package com.edu.netc.bakensweets.model.account;

public enum Gender {
    MALE("m"),
    FEMALE("f"),
    OTHER("other");

    private String gender;

    Gender (String gender) {
        this.gender = gender;
    }
}

package com.example.demo.enums;

import lombok.Getter;

public enum CupSize {

    LARGE("A", "大"), MIDDLE("B", "中"), SMALL("C", "小");

    @Getter
    private String value;
    private String desc;

    private CupSize(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static CupSize size(String size) {
        if (LARGE.value.equals(size)) {
            return LARGE;
        } else if (MIDDLE.value.equals(size)) {
            return MIDDLE;
        } else if (SMALL.value.equals(size)) {
            return SMALL;
        }
        return null;
    }
}

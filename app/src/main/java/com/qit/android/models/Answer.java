package com.qit.android.models;

import java.util.Set;

import lombok.Data;

@Data
public class Answer {

    private Long id;
    private Result result;
    private Set<Variant> variants;

}

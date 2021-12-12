package com.edu.netc.bakensweets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class PageDTO<T> {
    private Collection<T> items;
    private int itemCount;
}

package com.edu.netc.bakensweets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class ItemsPerPageDTO<T> {
    private Collection<T> items;
    private int currentPage;
    private int itemCount;
}
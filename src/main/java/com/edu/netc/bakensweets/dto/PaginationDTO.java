package com.edu.netc.bakensweets.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Data
@RequiredArgsConstructor
public class PaginationDTO<T> {
    private final Collection<T> content;
    private final int totalElements;
}

package com.edu.netc.bakensweets.model.role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
    private int id;
    private String roleName;
}

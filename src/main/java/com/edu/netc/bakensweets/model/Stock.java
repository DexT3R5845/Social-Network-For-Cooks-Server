package com.edu.netc.bakensweets.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    private long id;
    private long accountId;
    private long ingrId;
    private int amount;

    public Stock(long accountId, long ingrId, int amount) {
        this.accountId = accountId;
        this.ingrId = ingrId;
        this.amount = amount;
    }
}

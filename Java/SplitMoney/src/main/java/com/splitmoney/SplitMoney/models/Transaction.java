package com.splitmoney.splitmoney.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    User owedTo;
    int amt;
    User owedBy;
}

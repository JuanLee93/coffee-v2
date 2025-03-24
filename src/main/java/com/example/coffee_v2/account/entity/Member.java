package com.example.coffee_v2.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Member {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String phone;
    private boolean isBuy;

}

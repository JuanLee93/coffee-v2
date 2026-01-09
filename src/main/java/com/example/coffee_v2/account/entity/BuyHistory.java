package com.example.coffee_v2.account.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "buy_history")
public class BuyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 커피 산 사람
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /**
     * 커피 산 날짜
     */
    @Column(name = "buy_date", nullable = false)
    private LocalDate buyDate;

    public BuyHistory(Member member, LocalDate buyDate) {
        this.member = member;
        this.buyDate = buyDate;
    }
}



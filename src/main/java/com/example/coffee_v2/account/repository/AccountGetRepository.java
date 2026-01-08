package com.example.coffee_v2.account.repository;

import com.example.coffee_v2.account.entity.Member;
import com.example.coffee_v2.account.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 조회 담당 Repository
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountGetRepository {

    private final JPAQueryFactory queryFactory;

    public List<Member> findAllMember() {
        QMember m = QMember.member;
        return queryFactory
                .selectFrom(m)
                .fetch();
    }

    public Optional<Member> findById(Long memberId) {
        QMember m = QMember.member;
        Member member = queryFactory
                .selectFrom(m)
                .where(m.id.eq(memberId))
                .fetchOne();
        return Optional.ofNullable(member);
    }
}

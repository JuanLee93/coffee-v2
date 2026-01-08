package com.example.coffee_v2.account.repository;

import com.example.coffee_v2.account.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 수정 담당 Repository
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountUpdateRepository {

    private final JPAQueryFactory queryFactory;

    // 멤버를 커피 산 사람으로 수정
    public void doMemberAsBuyer(Long memberId) {
        QMember m = QMember.member;
        queryFactory.update(m)
                .set(m.isBuy, true)
                .where(m.id.eq(memberId))
                .execute();
    }

    // 전체 멤버를 커피 안산 사람으로 리셋
    public void resetMemberBuyStatus() {
        QMember m = QMember.member;
        queryFactory.update(m)
                .set(m.isBuy, false)
                .execute();
    }

}

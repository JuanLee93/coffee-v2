package com.example.coffee_v2.account.repository;

import com.example.coffee_v2.account.entity.BuyHistory;
import com.example.coffee_v2.account.entity.Member;
import com.example.coffee_v2.account.entity.QBuyHistory;
import com.example.coffee_v2.account.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 수정 담당 Repository
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountUpdateRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

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

    // 오늘 커피 산사람을 저장
    public void saveTodayBuyer(LocalDate today, long memberId) {
        QMember m = QMember.member;

        // member 테이블에 is_buy = true로 업데이트
        queryFactory.update(m)
                .set(m.isBuy, true)
                .where(m.id.eq(memberId))
                .execute();

        // 오늘 구매기록 있는지 확인, 있으면 리턴 (구매기록 저장x)
        boolean exists = getTodayBuyHistory(memberId, today);
        if (exists) return;

        // 구매기록 저장
        insertBuyHistory(today, memberId);
    }

    private boolean getTodayBuyHistory(long memberId, LocalDate today) {
        QBuyHistory h = QBuyHistory.buyHistory;
        return queryFactory.selectFrom(h)
                .where(
                        h.member.id.eq(memberId),
                        h.buyDate.between(today, today.plusDays(1))
                )
                .fetchCount() > 0;
    }

    // 구매 기록 저장
    public void insertBuyHistory(LocalDate buyDate, long memberId) {
        Member memberRef = em.getReference(Member.class, memberId);
        BuyHistory history = new BuyHistory(memberRef, buyDate);
        em.persist(history);
    }

    public Optional<Long> findTodayBuyer(LocalDate today) {
        QBuyHistory h = QBuyHistory.buyHistory;

        Long memberId = queryFactory.select(h.member.id)
                .from(h)
                .where(h.buyDate.between(today, today.plusDays(1)))
                .fetchFirst(); // 한 건만 가져오기

        return Optional.ofNullable(memberId);
    }
}
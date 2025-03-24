package com.example.coffee_v2.account;

import com.example.coffee_v2.account.dto.InformDto;
import com.example.coffee_v2.account.entity.Member;
import com.example.coffee_v2.account.entity.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    List<Member> members = new ArrayList<>();

    public List<Member> getAllAccount(){
        QMember member = QMember.member;
        return jpaQueryFactory.select(Projections.fields(Member.class,
                member.id,
                member.name,
                member.phone,
                member.isBuy))
                .from(member).fetch();
    }

    public InformDto getBuyerByCurrentDate() {
        members = getAllAccount();

        InformDto inform = new InformDto();

        LocalDateTime datetime = LocalDateTime.now();
        String currentDate = String.valueOf(datetime.getDayOfWeek());

        if (currentDate.equals("SATURDAY") || currentDate.equals("SUNDAY")) {
            inform.setMessage("오늘은 휴일입니다. 커피를 사는날이 아닙니다.");
            inform.setBuyerInform("없음.");
        } else {
            String buyer = getNextBuyer(); // 커피를 사야 할 사람 찾기
            inform.setMessage("오늘은 커피를 먹는날!");
            inform.setBuyerInform("오늘의 커피 계산은 " + buyer + " 님 입니다.");
        }

        inform.setDateInform("오늘의 날짜는 " + currentDate + " 입니다.");

        return inform;
    }

    // 커피를 사는 사람을 결정하는 로직
    private String getNextBuyer() {
        QMember qMember = QMember.member;
        JPAUpdateClause clause = jpaQueryFactory.update(qMember);

        int buyCount = 0;

        for (Member member : members) {
            if (member.isBuy()) {  // 커피를 산 사람
                buyCount++;
            } else {  // 커피를 안 산 사람
                clause.set(qMember.isBuy, true);
                clause.where(qMember.id.eq(member.getId())).execute();
                return member.getName();
            }
        }

        // 만약 모든 사람이 커피를 샀다면, 첫 번째 사람에게 커피를 사게 함
        if (buyCount == members.size()) {
            //커피를 산 사람들의 isBuy 값을 리셋
            clause.set(qMember.isBuy, false);
            clause.execute();

            Member firstMember = members.get(0);

            //첫번째 사람만 true
            clause.set(qMember.id, firstMember.getId());
            clause.set(qMember.isBuy, true);
            clause.where(qMember.id.eq(firstMember.getId())).execute();

            return members.get(0).getName();
        }


        return "없음."; // 예외 처리 (모든 사람이 커피를 샀다면)
    }

    public void temp(){
        
    }

}

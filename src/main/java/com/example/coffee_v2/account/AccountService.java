package com.example.coffee_v2.account;

import com.example.coffee_v2.account.dto.InformDto;
import com.example.coffee_v2.account.entity.Member;
import com.example.coffee_v2.account.repository.AccountGetRepository;
import com.example.coffee_v2.account.repository.AccountUpdateRepository;
import com.example.coffee_v2.account.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountGetRepository getRepository;
    private final AccountUpdateRepository updateRepository;
    private final RedisRepository redisRepository;

    // 전체 멤버를 조회
    @Cacheable(value = "members", key = "'all'")
    @Transactional(readOnly = true)
    public List<Member> getAllAccount() {
        return getRepository.findAllMember();
    }

    // 현재일 기준으로 커피 사는 사람이 누구인지 조회
    public InformDto getBuyerByCurrentDate() {

        LocalDate today = LocalDate.now();

        InformDto inform = new InformDto();
        inform.setDateInform("오늘의 날짜는 " + today + " 입니다.");

        // db 먼저 확인
        Optional<Long> cache = updateRepository.findTodayBuyer(today);

        Member member;

        if (cache.isPresent()) {
            member = getRepository.findById(cache.get())
                .orElseThrow(
                    () -> new RuntimeException("DB에 회원 정보가 없습니다.")
                );
        } else {
            String buyer = this.getNextBuyer();
            member = this.getAllAccount().stream()
                    .filter(m -> m.getName().equals(buyer))
                    .findFirst()
                    .orElseThrow();
        }

        // db에 오늘 커피 산 사람 저장
        updateRepository.saveTodayBuyer(today, member.getId());

        // InformDTO 세팅
        inform.setBuyerInform(member.getName());
        inform.setMessage(
            today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY
                ? "오늘은 휴일입니다. 커피를 사는 날이 아닙니다."
                : "오늘은 커피를 먹는날! 오늘의 커피 계산은 " + member.getName() + "님 입니다."
        );

        return inform;
    }

    // 커피를 사는 사람을 결정하는 로직
    private String getNextBuyer() {

        List<Member> members = getRepository.findAllMember();

        // 1. 커피 안 산 사람 찾기
        Optional<Member> nextBuyer = members.stream()
                .filter(member -> !member.isBuy())
                .findFirst();

        if (nextBuyer.isPresent()) {
            Member member = nextBuyer.get();
            updateRepository.doMemberAsBuyer(member.getId());
            return member.getName();
        }

        // 2. 모두 커피를 샀다면 리셋
        updateRepository.resetMemberBuyStatus();

        Member firstMember = members.get(0);
        updateRepository.doMemberAsBuyer(firstMember.getId());

        return firstMember.getName();
    }

}

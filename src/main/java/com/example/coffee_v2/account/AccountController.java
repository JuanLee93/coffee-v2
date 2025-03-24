package com.example.coffee_v2.account;

import com.example.coffee_v2.account.dto.InformDto;
import com.example.coffee_v2.account.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping(value = "/getAllAccount")
    public ResponseEntity<List<Member>> GetAllAccount(){
        List<Member> account = accountService.getAllAccount();
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping(value = "/getBuyerByCurrentDate")
    public ResponseEntity<InformDto> GetBuyerByCurrentDate(){
        InformDto response = accountService.getBuyerByCurrentDate();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class AccountController {
    
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable(value = "id") Long accountId) throws Exception {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new Exception("Account not found for this id :: " + accountId));
        return ResponseEntity.ok().body(account);
    }

    @PostMapping("/accounts")
    public Account createAccount(@Validated @RequestBody Account account) {
        return accountRepository.save(account);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable(value = "id") Long accountId, 
        @Validated @RequestBody Account accountDetails) throws Exception {

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new Exception("Account not found for this id :: " + accountId));
        account.setCin(accountDetails.getCin());
        account.setFirstName(accountDetails.getFirstName());
        account.setLastName(accountDetails.getLastName());
        account.setAccountNumber(accountDetails.getAccountNumber());
        account.setAmount(accountDetails.getAmount());
        account.setAgency(accountDetails.getAgency());

        final Account updatedAccount = accountRepository.save(account);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/accounts/{id}")
    public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long accountId) throws Exception {
        Account accounts = accountRepository.findById(accountId).orElseThrow(() -> new Exception("Account not found for this id :: " + accountId));
        accountRepository.delete(accounts);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}

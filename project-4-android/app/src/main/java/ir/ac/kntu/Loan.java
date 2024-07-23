package ir.ac.kntu;

import ir.ac.kntu.Calendar;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Loan implements Serializable {
    enum Accept {ACCEPT, REJECT, PENDING}

    private Long money;
    private Long accountNum;
    private Instant startDate;
    private Integer loanId;
    private static Integer countId = 15;
    private Instant endDate;
    private Integer countOfInstallment;
    private Integer sendedInstallment;
    private Integer precentOfInstallment;
    private Long eachInstallment;
    private Accept accept;
    private Integer mustInstallUntilNow;

    public Loan(Long accountNum, Long balance, Integer month) {

        this.loanId = countId++;
        this.accountNum = accountNum;
        this.money = balance;
        this.startDate = Calendar.now();
        LocalDateTime startDateTime = LocalDateTime.ofInstant(startDate, ZoneOffset.UTC);
        LocalDateTime endDateTime = startDateTime.plusMonths(month);
        this.endDate = endDateTime.toInstant(ZoneOffset.UTC);
        this.countOfInstallment = month;
        this.sendedInstallment = 0;
        this.precentOfInstallment = 20;
        this.eachInstallment = (money * precentOfInstallment / 100 + money) / month;
        this.accept = Accept.PENDING;
        this.mustInstallUntilNow = 0;
    }

    public void startLoan() {
        Bank bank = CentralBank.getInstance().getFariBank();
        BankAccount account = bank.checkAccount(accountNum);
        if (account == null) {
            accept = Accept.REJECT;
            return;
        }
        account.setBalance(account.getBalance() + money);
        accept = Accept.ACCEPT;

    }

    public Integer getMustInstallUntilNow() {
        if (accept.equals(Accept.REJECT)) {
            return 0;
        }
        Instant now = Calendar.now();
        LocalDateTime tempTime;
        LocalDateTime startDateTime = LocalDateTime.ofInstant(startDate, ZoneOffset.UTC);


        for (int i = 0; i <= countOfInstallment + 1; i++) {
            tempTime = startDateTime.plusMonths(i);
            Instant time = tempTime.toInstant(ZoneOffset.UTC);

            if (now.isBefore(time)) {
                return i - 1;
            }
            if (now.isAfter(endDate)) {
                return countOfInstallment;
            }
        }
        return 0;
    }

    public boolean spendInstallment() {
        if (accept.equals(Accept.REJECT)) {
            return false;
        }
        Bank bank = CentralBank.getInstance().getFariBank();
        BankAccount account = bank.checkAccount(accountNum);
        int count = getMustInstallUntilNow() - sendedInstallment;
        Long moneyToSpend = count * eachInstallment;
        if (count == 0) {
            return false;
        }
        if (account.getBalance() > moneyToSpend) {
            account.setBalance(account.getBalance() - moneyToSpend);
            sendedInstallment += count;
            return true;
        }
        return false;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Long accountNum) {
        this.accountNum = accountNum;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public static Integer getCountId() {
        return countId;
    }

    public static void setCountId(Integer countId) {
        Loan.countId = countId;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getCountOfInstallment() {
        return countOfInstallment;
    }

    public void setCountOfInstallment(Integer countOfInstallment) {
        this.countOfInstallment = countOfInstallment;
    }

    public Integer getSendedInstallment() {
        return sendedInstallment;
    }

    public void setSendedInstallment(Integer sendedInstallment) {
        this.sendedInstallment = sendedInstallment;
    }

    public Integer getPrecentOfInstallment() {
        return precentOfInstallment;
    }

    public void setPrecentOfInstallment(Integer precentOfInstallment) {
        this.precentOfInstallment = precentOfInstallment;
    }

    public Long getEachInstallment() {
        return eachInstallment;
    }

    public void setEachInstallment(Long eachInstallment) {
        this.eachInstallment = eachInstallment;
    }

    public Accept getAccept() {
        return accept;
    }

    public void setAccept(Accept accept) {
        this.accept = accept;
    }

    public void setMustInstallUntilNow(Integer mustInstallUntilNow) {
        this.mustInstallUntilNow = mustInstallUntilNow;
    }
}

package ir.ac.kntu;

import ir.ac.kntu.Calendar;

import java.io.Serializable;
import java.time.Instant;

public abstract class CashDesk implements Serializable {
    private Long accountNum;
    private Long balance;
    private Instant startDate;
    private Integer deskId;
    private static Integer countId = 15;

    public CashDesk(Long accountNum, Long balance) {
        this.accountNum = accountNum;
        this.balance = balance;
        this.startDate = Calendar.now();
        this.deskId = countId++;
    }

    public Integer getDeskId() {
        return deskId;
    }

    public void setDeskId(Integer deskId) {
        this.deskId = deskId;
    }

    public static Integer getCountId() {
        return countId;
    }

    public static void setCountId(Integer countId) {
        CashDesk.countId = countId;
    }

    public Long getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Long accountNum) {
        this.accountNum = accountNum;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public abstract void cashToAcc(BankAccount account);


    @Override
    public String toString() {
        return "CashDesk{" +
                "accountNum=" + accountNum +
                ", balance=" + balance +
                ", startDate=" + startDate +
                ", deskId=" + deskId +
                '}';
    }
}

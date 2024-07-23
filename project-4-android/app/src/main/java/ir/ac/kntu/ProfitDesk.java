package ir.ac.kntu;

import ir.ac.kntu.Calendar;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ProfitDesk extends CashDesk implements Serializable {
    private Instant endDate;
    private Integer countOfPrifit;
    private Integer sendedProfit;
    private Integer precentOfProfit;
    private String name;

    public ProfitDesk(Long accountNum, Long balance, Integer month, Integer precentOfProfit) {
        super(accountNum, balance);
        LocalDateTime startDateTime = LocalDateTime.ofInstant(super.getStartDate(), ZoneOffset.UTC);
        LocalDateTime endDateTime = startDateTime.plusMonths(month);
        this.endDate = endDateTime.toInstant(ZoneOffset.UTC);
        this.countOfPrifit = month;
        this.sendedProfit = 0;
        this.precentOfProfit = precentOfProfit;
        this.name = "ProfitDesk";
    }


    @Override
    public void cashToAcc(BankAccount account) {
        Instant now = Calendar.now();
        LocalDateTime startDateTime = LocalDateTime.ofInstant(super.getStartDate(), ZoneOffset.UTC);
        LocalDateTime tempTime;
        for (int i = sendedProfit + 1; i <= countOfPrifit; i++) {
            tempTime = startDateTime.plusMonths(i);
            Instant time = tempTime.toInstant(ZoneOffset.UTC);
            if (now.isAfter(time)) {
                this.sendedProfit++;
                account.setBalance(account.getBalance() + super.getBalance() * precentOfProfit / 100);
            }
        }
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getCountOfPrifit() {
        return countOfPrifit;
    }

    public void setCountOfPrifit(Integer countOfPrifit) {
        this.countOfPrifit = countOfPrifit;
    }

    public Integer getSendedProfit() {
        return sendedProfit;
    }

    public void setSendedProfit(Integer sendedProfit) {
        this.sendedProfit = sendedProfit;
    }

    public Integer getPrecentOfProfit() {
        return precentOfProfit;
    }

    public void setPrecentOfProfit(Integer precentOfProfit) {
        this.precentOfProfit = precentOfProfit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


//work successfuly

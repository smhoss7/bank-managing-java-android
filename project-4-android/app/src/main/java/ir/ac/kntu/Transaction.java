package ir.ac.kntu;

import java.io.Serializable;
import java.time.Instant;

enum TransactionType {TOPPINGUP, TRANSFER}

public class Transaction implements Serializable {
    private Instant time;
    private TransactionType type;
    private Person depositor;
    private Person receiver;
    private Long money;
    private Long tax;
    private Integer transactionId;
    private static Integer counterId = 1156;


    public Transaction(Instant time, TransactionType type, Person depositor, Person receiver, Long money, Long tax) {
        this.time = time;
        this.type = type;
        this.depositor = depositor;
        this.receiver = receiver;
        this.money = money;
        this.tax = tax;
        this.transactionId = counterId++;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Person getDepositor() {
        return depositor;
    }

    public void setDepositor(Person depositor) {
        this.depositor = depositor;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getTax() {
        return tax;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "time=" + time +
                ", type=" + type +
                ", depositor=" + depositor +
                ", receiver=" + receiver +
                ", money=" + money +
                ", tax=" + tax +
                '}';
    }
}

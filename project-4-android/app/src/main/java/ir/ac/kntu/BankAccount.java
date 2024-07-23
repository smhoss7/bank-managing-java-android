package ir.ac.kntu;

import java.io.Serializable;
import java.util.*;

public class BankAccount implements Serializable {
    private Integer bankId;
    private static Integer counterId = 7645;
    private Long accountNumber;
    private static Long counterAccNum = 10000005689L;
    private Long balance;
    private Long cardNumber;
    private static Long counterCardNum = 6221061247561234L;
    private Integer userId;
    private String cardPass;
    private List<Transaction> transactions = new ArrayList<>();
    private List<Long> lastAccNum = new ArrayList<>();
    private Map<Integer, CashDesk> cashDeskMap = new HashMap<>();
    private Map<Integer, Loan> loans = new HashMap<>();

    public BankAccount(Integer userId) {
        this.bankId = counterId++;
        this.accountNumber = counterAccNum++;
        this.cardNumber = counterCardNum++;
        this.balance = 0L;
        this.userId = userId;
        this.cardPass = "1234";
    }

    public Transaction getTrans(Transaction transaction) {
        for (Transaction transaction1 : this.transactions) {
            if (transaction1.getTransactionId().equals(transaction.getTransactionId())) {
                return transaction1;
            }
        }
        return null;
    }

    public void updateTransaction(Person contact, String firstName, String lastName) {
        for (Transaction transaction : this.transactions) {

            if (transaction.getReceiver() == null) {
                continue;
            }
            if (transaction.getReceiver().getFirstName().equals(contact.getFirstName()) && transaction.getReceiver().getLastName().equals(contact.getLastName()) && transaction.getReceiver().getPhone().equals(contact.getPhone())) {
                transaction.getReceiver().setFirstName(firstName);
                transaction.getReceiver().setLastName(lastName);
            }
        }
    }

    public Integer getId() {
        return bankId;
    }

    public void setId(Integer bankId) {
        this.bankId = bankId;
    }

    public static Integer getCounterId() {
        return counterId;
    }

    public Map<Integer, Loan> getLoans() {
        return loans;
    }

    public void setLoans(Map<Integer, Loan> loans) {
        this.loans = loans;
    }

    public void addLoan(Loan loan) {
        this.loans.put(loan.getLoanId(), loan);

    }

    public boolean addDesk(CashDesk cashDesk) {
        if (cashDesk instanceof ProfitDesk || cashDesk instanceof SavingDesk) {
            this.cashDeskMap.put(cashDesk.getDeskId(), cashDesk);
            return true;
        }
        if (cashDesk instanceof RemainDesk) {
            boolean exist = this.cashDeskMap.values().stream().anyMatch(cashDesk1 -> cashDesk1 instanceof RemainDesk);
            if (exist) {
                return false;
            }
            this.cashDeskMap.put(cashDesk.getDeskId(), cashDesk);
            return true;
        }
        return false;
    }

    public void removeDesk(CashDesk cashDesk) {
        this.cashDeskMap.remove(cashDesk.getDeskId());
    }

    public RemainDesk getRemainDesk() {
        for (CashDesk cashDesk : cashDeskMap.values()) {
            if (cashDesk instanceof RemainDesk remainDesk) {
                return remainDesk;
            }
        }
        return null;
    }

    public Map<Integer, CashDesk> getCashDeskMap() {
        return cashDeskMap;
    }

    public void setCashDeskMap(Map<Integer, CashDesk> cashDeskMap) {
        this.cashDeskMap = cashDeskMap;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        Collections.sort(this.transactions, new SortTransaction());
    }

    public static void setCounterId(Integer counterId) {
        BankAccount.counterId = counterId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public static Long getCounterAccNum() {
        return counterAccNum;
    }

    public static void setCounterAccNum(Long counterAccNum) {
        BankAccount.counterAccNum = counterAccNum;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public static Long getCounterCardNum() {
        return counterCardNum;
    }

    public static void setCounterCardNum(Long counterCardNum) {
        BankAccount.counterCardNum = counterCardNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCardPass() {
        return cardPass;
    }

    public void setCardPass(String cardPass) {
        this.cardPass = cardPass;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Long> getLastAccNum() {
        return lastAccNum;
    }

    public void setLastAccNum(List<Long> lastAccNum) {
        this.lastAccNum = lastAccNum;
    }

    public void toppingUp(Long money) {
        this.balance += money;
    }


    public boolean transferToOtherBank(Long money, Long tax) {
        if (this.balance < money + tax) {
            return false;
        }
        this.balance = this.balance - money - tax;
        return true;
    }

    public boolean moneyTransfer(Long money, Long wage) {
        if (wage > 100) {
            return false;
        }
        Long allMoney = money + money * wage / 100;
        if (this.balance < allMoney) {
            return false;
        }
        this.balance = this.balance - allMoney;
        return true;

    }

    public void addLastAccount(Long accountNumber) {
        if (!this.lastAccNum.contains(accountNumber)) {
            this.lastAccNum.add(accountNumber);
        }
    }

    public Transaction lastTrasnaction() {
        if (this.transactions.isEmpty()) {
            return null;
        }
        Collections.sort(this.transactions, new SortTransaction());
        return this.transactions.get(0);
    }

    public static boolean checkCardPass(String cardPass) {
        return cardPass.matches("[0-9]{4}");
    }


    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", cardNumber=" + cardNumber +
                ", cardPass=" + cardPass +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BankAccount that)) {
            return false;
        }
        return Objects.equals(bankId, that.bankId) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(balance, that.balance) && Objects.equals(cardNumber, that.cardNumber) && Objects.equals(userId, that.userId) && Objects.equals(cardPass, that.cardPass) && Objects.equals(transactions, that.transactions) && Objects.equals(lastAccNum, that.lastAccNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankId, accountNumber, balance, cardNumber, userId, cardPass, transactions, lastAccNum);
    }
}

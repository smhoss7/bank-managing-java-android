package ir.ac.kntu;

import java.io.Serializable;

public class RemainDesk extends CashDesk implements Serializable {
    private String name;

    public RemainDesk(Long accountNum, Long balance) {
        super(accountNum, balance);
        this.name = "Remain desk";
    }

    @Override
    public void cashToAcc(BankAccount account) {
        account.setBalance(account.getBalance() + super.getBalance());
        super.setBalance(0L);
    }

    public boolean manualCashToAcc(BankAccount account, Long money) {
        if (money > super.getBalance()) {
            return false;
        }
        account.setBalance(money + account.getBalance());
        super.setBalance(super.getBalance() - money);
        return true;
    }


    public void addMoney(Long money) {
        super.setBalance(super.getBalance() + money);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package ir.ac.kntu;

import java.util.HashMap;
import java.util.Map;

public class CentralBank {
    private Map<String, Bank> banks = new HashMap<>();
    private Map<Long, Bank> accounts = new HashMap<>();

    private static CentralBank instance = new CentralBank();

    private CentralBank() {

    }

    public Bank getFariBank() {
        return banks.get("Fari Bank");
    }


    public static CentralBank getInstance() {
        return instance;
    }

    public void addBank(Bank bank, String name) {
        this.banks.put(name, bank);
    }

    public void addAccount(Bank bank, Long accountNum) {
        this.accounts.put(accountNum, bank);
    }

    public BankAccount checkAccount(Long accountNum) {
        if (accounts.containsKey(accountNum)) {
            return accounts.get(accountNum).checkAccount(accountNum);
        }
        return null;
    }

    public User accountToUser(Long accountNum) {
        if (accounts.containsKey(accountNum)) {
            return accounts.get(accountNum).accountToUser(accountNum);
        }
        return null;
    }

    public Bank accountToBank(Long accountNum) {
        return accounts.getOrDefault(accountNum, null);
    }

    public Person accountNumToUser(Long accountNum) {
        return accounts.get(accountNum).accountToUser(accountNum);
    }


    public BankAccount checkCardNum(Long cardNum) {
        for (Bank bank : banks.values()) {
            if (bank.checkCardNum(cardNum) != null) {
                return bank.checkCardNum(cardNum);
            }
        }
        return null;
    }

}

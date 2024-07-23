package ir.ac.kntu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Bank implements Serializable {
    private Map<Integer, BankAccount> accounts = new HashMap<>();
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Admin> admins = new HashMap<>();
    private static Long wage = 0L;
    private Map<Integer, Boss> bosses = new HashMap<>();
    private static Map<String, Long> phones = new HashMap<>();
    private List<TransferWithBoss> bossTransfers = new ArrayList<>();
    private Integer profit = 2;

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    public Bank() {

    }


    public void removeDesk() {
        for (BankAccount account : accounts.values()) {
            for (CashDesk cashDesk : account.getCashDeskMap().values()) {
                if (cashDesk instanceof ProfitDesk profitDesk) {
                    if (profitDesk.getSendedProfit().equals(profitDesk.getCountOfPrifit())) {
                        account.setBalance(account.getBalance() + profitDesk.getBalance());
                    }
                }
            }
            List<ProfitDesk> desksToRemove = account.getCashDeskMap().values().stream()
                    .filter(cashDesk -> cashDesk instanceof ProfitDesk)
                    .map(cashDesk -> (ProfitDesk) cashDesk)
                    .filter(profitDesk -> profitDesk.getSendedProfit().equals(profitDesk.getCountOfPrifit()))
                    .collect(Collectors.toList());
            desksToRemove.forEach(account::removeDesk);


        }


    }

    public static Long getWage() {
        return wage;
    }

    public static void setWage(Long wage) {
        Bank.wage = wage;
    }

    public List<TransferWithBoss> getBossTransfers() {
        return bossTransfers;
    }

    public void setBossTransfers(List<TransferWithBoss> bossTransfers) {
        this.bossTransfers = bossTransfers;
    }

    public Map<Integer, Boss> getBosses() {
        return bosses;
    }

    public void setBosses(Map<Integer, Boss> bosses) {
        this.bosses = bosses;
    }

    public Bank(Map<Integer, BankAccount> accounts, Map<Integer, User> users) {
        this.accounts = accounts;
        this.users = users;
    }

    public static Long phoneToAmount(String phone) {
        return phones.getOrDefault(phone, 0L);
    }

    public static void setPhone(String phone, Long money) {
        if (Bank.phones.containsKey(phone)) {
            phones.put(phone, phones.get(phone) + money);
        } else {
            phones.put(phone, money);
        }
    }

    public void addBoss(Boss boss) {
        this.bosses.put(boss.getBossId(), boss);

    }


    public Boss getBoss(String username, String password) {
        return this.bosses.values().stream().filter(boss -> boss.getUserName().equals(username) && boss.getPassword().equals(password)).findFirst().orElse(null);
    }


    public void doAuthen(int userId, AuthCheck check, String problem) {
        User user = users.get(userId);
        switch (check) {
            case ACCEPT -> {
                user.setAuthentication(true);
                user.setAboutAuthen("true");
                BankAccount account = new BankAccount(userId);
                user.setAcountID(account.getId());
                this.accounts.put(account.getId(), account);
                Admin.removeAuthen(userId);
            }
            case REJECT -> user.setAboutAuthen(problem);
            default -> {
            }

        }
    }

    public void addAdmin(Admin admin) {
        if (!this.admins.containsKey(admin.getId())) {
            this.admins.put(admin.getId(), admin);
        }
    }

    public Admin getAdmin(String username, String password) {
        for (Admin admin : this.admins.values()) {
            if (admin.getUserName().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }

    private void doRemainDesk(Long accountNum, Long money, Long tax) {
        Long allMoney = money + tax;
        BankAccount account = checkAccount(accountNum);
        RemainDesk remainDesk = account.getRemainDesk();
        if (remainDesk == null) {
            return;
        }
        int fineDigit = allMoney.toString().length() * 75 / 100;
        if (fineDigit == 0) {
            return;
        }
        Long moneyToDesk = (long) (Math.pow(10, fineDigit) - (allMoney % Math.pow(10, fineDigit)));
        if (account.getBalance() >= moneyToDesk) {
            account.setBalance(account.getBalance() - moneyToDesk);
            remainDesk.addMoney(moneyToDesk);
        }


    }

    public boolean traFromThis(Long accountNum, Long money, Long tax) {//tax is not precent and it is amount
        BankAccount account = checkAccount(accountNum);
        if (account == null) {
            return false;
        }

        if (account.transferToOtherBank(money, tax)) {
            doRemainDesk(accountNum, money, tax);
            return true;
        }


        return false;
    }


    public BankAccount checkCardNum(Long cardNum) {
        return accounts.values().stream().filter(account -> account.getCardNumber().equals(cardNum)).findFirst().orElse(null);
    }

    public void traToThis(Long accountNum, Long money) {
        BankAccount account = checkAccount(accountNum);
        account.toppingUp(money);
    }

    public boolean moneyTransfer(int signedUserId, Long money, Long accountNum) {
        BankAccount account = this.checkAccount(accountNum);
        if (account == null) {
            return false;
        }

        BankAccount depositerAcc = this.accounts.get(this.users.get(signedUserId).getAcountID());
        if (!depositerAcc.moneyTransfer(money, wage)) {
            return false;
        }
        Transaction transaction = new Transaction(Calendar.now(), TransactionType.TRANSFER, this.users.get(signedUserId), this.users.get(account.getUserId()), money, wage * money / 100);
        account.addTransaction(transaction);
        depositerAcc.addTransaction(transaction);
        account.toppingUp(money);
        doRemainDesk(depositerAcc.getAccountNumber(), money, wage * money / 100);

        return true;

    }


    public boolean moneyTransfer(int signedUserId, Long money, Person person) {
        User receiver = this.phoneToUser(person.getPhone());
        if (receiver == null) {
            return false;
        }
        BankAccount depositerAcc = this.accounts.get(this.users.get(signedUserId).getAcountID());
        if (!depositerAcc.moneyTransfer(money, wage)) {
            return false;
        }
        Transaction transaction = new Transaction(Calendar.now(), TransactionType.TRANSFER, this.users.get(signedUserId), person, money, wage * money / 100);
        this.accounts.get(receiver.getAcountID()).toppingUp(money);
        this.accounts.get(receiver.getAcountID()).addTransaction(transaction);
        depositerAcc.addTransaction(transaction);
        //depositerAcc.moneyTransfer(money, wage);
        doRemainDesk(depositerAcc.getAccountNumber(), money, wage * money / 100);

        return true;
    }

    public List<User> seeUsers(String firstName, String lastName, String phone) {
        List<User> filteredUsers = new ArrayList<>();
        for (User user : this.users.values()) {
            if (!user.isAuthentication()) {
                continue;
            }
            boolean matches = true;
            if (firstName != null && !firstName.isEmpty() && !user.getFirstName().equalsIgnoreCase(firstName)) {
                matches = false;
            }
            if (lastName != null && !lastName.isEmpty() && !user.getLastName().equalsIgnoreCase(lastName)) {
                matches = false;
            }
            if (phone != null && !phone.isEmpty() && !user.getPhone().equals(phone)) {
                matches = false;
            }
            if (matches) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    public void allUsers() {
        if (this.users.isEmpty()) {
            System.out.println("0 user");
            return;
        }

        for (User user : this.users.values()) {
            if (user.isAuthentication()) {
                System.out.println(user.getFirstName() + " " + user.getLastName() + ", " + user.getPhone() + ", " + this.userToAcc(user.getUserId()).getAccountNumber());
            }
        }

    }

    public User phoneToUser(String phone) {
        for (User user : this.users.values()) {
            if (user.getPhone().equals(phone)) {
                return user;
            }
        }
        return null;
    }

    public User personalCodeToUser(Long personalCode) {
        for (User user : this.users.values()) {
            if (user.getPersonalCode().equals(personalCode)) {
                return user;
            }
        }
        return null;
    }

    public User accountToUser(Long accountNum) {
        for (BankAccount bankAccount : this.accounts.values()) {
            if (bankAccount.getAccountNumber().equals(accountNum)) {
                return this.users.get(bankAccount.getUserId());
            }
        }
        return null;
    }

    public BankAccount userToAcc(int userId) {
        if (!users.containsKey(userId)) {
            return null;
        }
        int bankId = users.get(userId).getAcountID();
        if (!accounts.containsKey(bankId)) {
            return null;
        }
        return accounts.get(users.get(userId).getAcountID());
    }

    public BankAccount checkAccount(Long accountNum) {
        for (BankAccount account : this.accounts.values()) {
            if (account.getAccountNumber().equals(accountNum)) {
                return account;
            }
        }
        return null;
    }

    public BankAccount getAccount(int bankId) {
        if (accounts.containsKey(bankId)) {
            return accounts.get(bankId);
        }
        return null;
    }

    public boolean toppingUp(int userId, Long money) {
        BankAccount account = this.userToAcc(userId);
        if (account == null) {
            return false;
        }
        account.toppingUp(money);
        Transaction transaction = new Transaction(Calendar.now(), TransactionType.TOPPINGUP, users.get(userId), null, money, 0L);
        account.addTransaction(transaction);
        return true;
    }

    public void removeUser(User user) {
        this.users.remove(user.getUserId());
    }

    public void addUser(User user) {
        this.users.put(user.getUserId(), user);
    }

    public Map<Integer, BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<Integer, BankAccount> accounts) {
        this.accounts = accounts;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public void setUsers(Map<Integer, User> users) {
        this.users = users;
    }

    public Map<Integer, Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(Map<Integer, Admin> admins) {
        this.admins = admins;
    }

    public void addAccount(BankAccount account) {
        this.accounts.put(account.getId(), account);
    }

    public void addTransfer(TransferWithBoss transfer) {
        this.bossTransfers.add(transfer);
    }


}

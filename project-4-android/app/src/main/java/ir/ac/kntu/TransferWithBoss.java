package ir.ac.kntu;

/*
import ir.ac.kntu.util.Calendar;
*/

public class TransferWithBoss {
    private Long depositerAccNum;
    private Long reciverAccNum;
    private Long money;
    private Boolean check;

    public TransferWithBoss(Long depositerAccNum, Long reciverAccNum, Long money) {
        this.depositerAccNum = depositerAccNum;
        this.reciverAccNum = reciverAccNum;
        this.money = money;
        this.check = false;
    }

    public void doTransfer() {
        Bank depositerBank = CentralBank.getInstance().accountToBank(depositerAccNum);
        Bank reciverBank = CentralBank.getInstance().accountToBank(reciverAccNum);
        if (depositerBank.traFromThis(depositerAccNum, money, 2000L)) {
            reciverBank.traToThis(reciverAccNum, money);
            addTransaction();
            this.check = true;
        }
    }

    private void addTransaction() {
        BankAccount depositerAcc = CentralBank.getInstance().checkAccount(depositerAccNum);
        Transaction transaction = new Transaction(Calendar.now(), TransactionType.TRANSFER, CentralBank.getInstance().accountNumToUser(depositerAccNum),
                CentralBank.getInstance().accountToUser(reciverAccNum), money, 2000L);
        depositerAcc.addTransaction(transaction);
        depositerAcc.addLastAccount(reciverAccNum);
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "TransferWithBoss{" +
                "depositerAccNum=" + depositerAccNum +
                ", reciverAccNum=" + reciverAccNum +
                ", money=" + money +
                ", check=" + check +
                '}';
    }
}

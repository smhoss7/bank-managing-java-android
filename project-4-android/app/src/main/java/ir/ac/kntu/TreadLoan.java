package ir.ac.kntu;

public class TreadLoan implements Runnable {

    public TreadLoan() {

    }

    public boolean checkInstallment(BankAccount account) {
        for (Loan loan : account.getLoans().values()) {
            if (loan.getAccept().equals(Loan.Accept.ACCEPT)) {
                if (!loan.getMustInstallUntilNow().equals(loan.getSendedInstallment())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                for (BankAccount account : CentralBank.getInstance().getFariBank().getAccounts().values()) {
                    for (Loan loan : account.getLoans().values()) {
                        if (loan.getAccept().equals(Loan.Accept.PENDING)) {
                            if (checkInstallment(account)) {
                                System.out.println(loan.getMoney());
                                loan.startLoan();
                            } else {
                                loan.setAccept(Loan.Accept.REJECT);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("tread loan error");

            }
        }
    }
}

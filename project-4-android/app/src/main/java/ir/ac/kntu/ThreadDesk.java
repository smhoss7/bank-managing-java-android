package ir.ac.kntu;

import ir.ac.kntu.Calendar;

public class ThreadDesk implements Runnable {


    public ThreadDesk() {
    }

    public void profitDesk(Bank bank) {
        for (BankAccount account : bank.getAccounts().values()) {
            for (CashDesk desk : account.getCashDeskMap().values()) {
                if (desk instanceof ProfitDesk profitDesk) {
                    profitDesk.cashToAcc(account);
                }
            }
        }

        bank.removeDesk();
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(8600);
                profitDesk(CentralBank.getInstance().getFariBank());
            } catch (Exception e) {

            }
        }
    }


}

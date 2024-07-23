package ir.ac.kntu;

public class ThreadBossTransfer implements Runnable {

    public ThreadBossTransfer() {
    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(9000);
                for (TransferWithBoss transfer : CentralBank.getInstance().getFariBank().getBossTransfers()) {
                    transfer.doTransfer();
                }
                CentralBank.getInstance().getFariBank().getBossTransfers().removeIf(TransferWithBoss::getCheck);
            } catch (Exception e) {

            }
        }

    }
}

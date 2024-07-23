package ir.ac.kntu;

public class ThreadRequest implements Runnable {

    public ThreadRequest() {
    }

    private void requestAuthen(Bank bank, Request request) {
        User user = request.getUser();
        user.setAuthentication(true);
        BankAccount account = new BankAccount(user.getUserId());
        user.setAcountID(account.getId());
        bank.addAccount(account);
        CentralBank.getInstance().addAccount(bank, account.getAccountNumber());
        request.setCondition(Request.Condition.CLOSE);
    }

    private void answerRequest(Request request) {
        request.setAdminAnswer("it will answer soon");
        request.setAnswer(true);
        request.setCondition(Request.Condition.PENDING);
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                if (!Admin.getRequestList().isEmpty()) {
                    for (Request request : Admin.getRequestList().values()) {
                        if (request.getCondition().equals(Request.Condition.CLOSE)) {
                            continue;
                        }
                        if (request.getReqType().equals(Request.ReqType.AUTHEN)) {
                            requestAuthen(CentralBank.getInstance().getFariBank(), request);
                        } else {
                            answerRequest(request);

                        }
                    }
                }
            } catch (Exception e) {


            }


        }
    }
}

package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActLoanDetail extends AppCompatActivity {

    private User user;
    private Loan loan;
    private Bank bank;
    private TextView moneyView;
    private TextView startView;
    private TextView mustView;
    private TextView sendedView;
    private TextView countView;
    private BankAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_loan_detail);

        initial();
    }

    public void initial() {
        try {


            bank = CentralBank.getInstance().getFariBank();
            user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
            account = bank.userToAcc(user.getUserId());
            loan = account.getLoans().get(((Loan) getIntent().getSerializableExtra("LOAN")).getLoanId());
            moneyView = findViewById(R.id.LoanMoneyDetail);
            startView = findViewById(R.id.LoanStartDetail);
            mustView = findViewById(R.id.LoanMustDetail);
            sendedView = findViewById(R.id.LoanSendedDetail);
            countView = findViewById(R.id.LoanConutDetail);
            moneyView.setText(String.valueOf(loan.getMoney()));
            startView.setText(String.valueOf(loan.getStartDate().toString()));
            mustView.setText(String.valueOf(loan.getMustInstallUntilNow()));
            sendedView.setText(String.valueOf(loan.getSendedInstallment()));
            countView.setText(String.valueOf(loan.getCountOfInstallment()));


        } catch (Exception e) {

        }
    }

    public void onClickSpend(View view) {
        if (loan.spendInstallment()) {
            Toast.makeText(this, "spend", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActLoanDetail.this, Dashboard.class);
            intent.putExtra("USER", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Toast.makeText(this, "you cant spand", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickBack(View view) {
        Intent intent = new Intent(ActLoanDetail.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
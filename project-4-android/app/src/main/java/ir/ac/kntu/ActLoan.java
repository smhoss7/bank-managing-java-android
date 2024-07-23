package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ActLoan extends AppCompatActivity {

    private EditText moneyView;
    private EditText monthView;
    private User user;
    private Bank bank;
    private BankAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_loan);
        System.out.println(Calendar.now());
        initial();
        showLoan();
    }

    public void showLoan() {
        try {


            List<String> loanName = new ArrayList<>();
            if (account != null && account.getLoans() != null) {
                for (Loan loan : account.getLoans().values()) {
                    loanName.add(loan.getMoney() + " " + loan.getMustInstallUntilNow() + " " + loan.getAccept().toString());
                }
            }
            ListView loanListView = findViewById(R.id.listViewLoan);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, loanName);
            loanListView.setAdapter(adapter);
            loanListView.setOnItemClickListener((parent, view, position, id) -> {

                Intent intent = new Intent(ActLoan.this, ActLoanDetail.class);
                intent.putExtra("USER", user);
                intent.putExtra("LOAN", (Loan) new ArrayList<>(account.getLoans().values()).get(position));
                startActivity(intent);
            });


        } catch (Exception e) {

        }
    }

    public void initial() {
        try {
            bank = CentralBank.getInstance().getFariBank();
            user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
            account = bank.userToAcc(user.getUserId());

            moneyView = findViewById(R.id.AddLoanAmount);
            monthView = findViewById(R.id.AddLoanMonth);
        } catch (Exception e) {
            Toast.makeText(this, "intial loan fail", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Dashboard.class);
            intent.putExtra("USER", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void onClickAdd(View view) {
        if (monthView.getText().toString().isEmpty() || moneyView.getText().toString().isEmpty()) {
            Toast.makeText(this, "fill all", Toast.LENGTH_SHORT).show();
        }
        Loan loan = new Loan(account.getAccountNumber(), Long.valueOf(moneyView.getText().toString()), Integer.valueOf(monthView.getText().toString()));
        account.addLoan(loan);
        Toast.makeText(this, "Load added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActLoan.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(ActLoan.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

}
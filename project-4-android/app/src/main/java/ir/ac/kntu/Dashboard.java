package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {


    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView personalView;
    private TextView accView;
    private TextView balanceView;
    private TextView cardView;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeViews();
        displayUserInfo();
    }

    private void initializeViews() {
        nameTextView = findViewById(R.id.full);
        phoneTextView = findViewById(R.id.textView11);
        personalView = findViewById(R.id.prsTxt);
        accView = findViewById(R.id.accTxt);
        balanceView = findViewById(R.id.balanceTxt);
        cardView = findViewById(R.id.cardTxt);
        Bank bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());

        for (User user : CentralBank.getInstance().getFariBank().getUsers().values()) {
            System.out.println("central" + user.getContact().toString());
        }

    }


    private void displayUserInfo() {
        Bank bank = CentralBank.getInstance().getFariBank();
        BankAccount account = bank.userToAcc(user.getUserId());
        if (user != null) {
            doDisplay(bank, user, account);
        } else {
            nameTextView.setText("null");
            phoneTextView.setText("null");
            personalView.setText("null");
            accView.setText("null");
            balanceView.setText("null");
            cardView.setText("null");

        }
    }

    public void doDisplay(Bank bank, User user, BankAccount account) {
        nameTextView.setText(user.getFirstName() + " " + user.getLastName());
        phoneTextView.setText(user.getPhone());
        personalView.setText(user.getPersonalCode().toString());
        accView.setText(account.getAccountNumber().toString());
        balanceView.setText(account.getBalance().toString());
        cardView.setText(account.getCardNumber().toString());
    }

    public void onClickTransaction(View view) {

        Intent intent = new Intent(Dashboard.this, ActTransaction.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void onClickContact(View view) {

        Intent intent = new Intent(Dashboard.this, ActContact.class);
        intent.putExtra("USER", user);

        startActivity(intent);


    }

    public void onClickToppingUp(View view) {
        Intent intent = new Intent(Dashboard.this, ActToppingUp.class);
        intent.putExtra("USER", user);
        startActivity(intent);

    }

    public void onClickTransfer(View view) {

        Intent intent = new Intent(Dashboard.this, ActTransfer.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void onClickDesk(View view) {

        Intent intent = new Intent(Dashboard.this, ActDesk.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void onClickRequest(View view) {

        Intent intent = new Intent(Dashboard.this, ActRequset.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(this, ActLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickLoan(View view) {

        Intent intent = new Intent(Dashboard.this, ActLoan.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }


}
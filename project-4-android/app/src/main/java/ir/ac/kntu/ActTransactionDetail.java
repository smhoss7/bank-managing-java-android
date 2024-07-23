package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActTransactionDetail extends AppCompatActivity {
    private Transaction transaction;
    private TextView type;
    private TextView time;
    private TextView depositor;
    private TextView receiver;
    private TextView money;
    private TextView tax;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_transaction_detail);
        initializeViews();
        displayTransaction();
    }

    private void initializeViews() {
        type = findViewById(R.id.TransType);
        time = findViewById(R.id.TransTime);
        depositor = findViewById(R.id.TransDepositer);
        receiver = findViewById(R.id.TransReciver);
        money = findViewById(R.id.TransMoney);
        tax = findViewById(R.id.TransTax);
        Bank bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
        BankAccount account = bank.userToAcc(user.getUserId());
        transaction = account.getTrans(((Transaction) getIntent().getSerializableExtra("TRANSACTION")));
        if (transaction == null) {
            Intent intent = new Intent(this, Dashboard.class);
            intent.putExtra("USER", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }


    }

    private void displayTransaction() {
        type.setText(transaction.getType().toString());
        time.setText(transaction.getTime().toString());
        depositor.setText(transaction.getDepositor().getFirstName() + " " + transaction.getDepositor().getLastName());
        if (transaction.getReceiver() == null) {
            receiver.setText("NA");
        } else {
            receiver.setText(transaction.getReceiver().getFirstName() + " " + transaction.getReceiver().getLastName());
        }
        money.setText(transaction.getMoney().toString());
        tax.setText(transaction.getTax().toString());

    }

    public void onClickBack(View view) {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
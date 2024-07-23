package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActDooTransfer extends AppCompatActivity {
    private User user;
    private String reciverAccNum;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_doo_transfer);

        Bank bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
        reciverAccNum = getIntent().getStringExtra("RECIEVERACCNUM");
        amount = getIntent().getStringExtra("AMOUNT");
        System.out.println("act do transfer okay");
        initial();

    }

    public void initial() {
        TextView name = findViewById(R.id.acceptName);
        TextView amountView = findViewById(R.id.acceptAmount);
        TextView accountView = findViewById(R.id.acceptAccNum);
        User reciver = CentralBank.getInstance().getFariBank().accountToUser(Long.valueOf(reciverAccNum));
        if (reciver == null) {
            showError();
        }
        name.setText(reciver.getFirstName() + " " + reciver.getLastName());
        amountView.setText(amount);
        accountView.setText(reciverAccNum);


    }

    public void onClick(View view) {
        try {
            Bank bank = CentralBank.getInstance().getFariBank();
            BankAccount account = bank.userToAcc(user.getUserId());
            User reciver = bank.accountToUser(Long.valueOf(reciverAccNum));
            if (reciver == null) {
                showError();
            }
            if (bank.moneyTransfer(user.getUserId(), Long.valueOf(amount), Long.valueOf(reciverAccNum))) {
                bank.userToAcc(user.getUserId()).addLastAccount(bank.userToAcc(reciver.getUserId()).getAccountNumber());
                showComplete();
            } else {
                showError();

            }

        } catch (Exception e) {
            showError();
        }
    }

    public void showComplete() {
        Toast.makeText(this, "transfer done", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActDooTransfer.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void showError() {
        Toast.makeText(this, "somthing went wrong", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActDooTransfer.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(ActDooTransfer.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
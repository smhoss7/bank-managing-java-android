package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActToppingUp extends AppCompatActivity {
    private User user;
    private EditText topUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_topping_up);
        Bank bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
        topUp = findViewById(R.id.amountView);
    }

    public void onClickTopUp(View view) {
        Bank bank = CentralBank.getInstance().getFariBank();
        BankAccount account = bank.userToAcc(user.getUserId());
        if (!topUp.getText().toString().matches("\\d+")) {
            Toast.makeText(this, "enter number", Toast.LENGTH_SHORT).show();
            return;
        }
        bank.toppingUp(user.getUserId(), Long.valueOf(topUp.getText().toString()));

        Toast.makeText(this, account.getBalance().toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActToppingUp.this, Dashboard.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }


}
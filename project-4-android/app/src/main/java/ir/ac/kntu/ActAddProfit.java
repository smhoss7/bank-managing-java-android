package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActAddProfit extends AppCompatActivity {

    private User user;
    private Bank bank;
    private EditText money;
    private EditText month;

    private BankAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_add_profit);
        initial();
    }

    private void initial() {
        bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
        account = bank.userToAcc(user.getUserId());
        money = findViewById(R.id.CreatMoney);
        month = findViewById(R.id.CreatMonth);
    }

    public void onClick(View view) {
        if (money.getText().toString().isEmpty() || month.getText().toString().isEmpty()) {
            Toast.makeText(this, "fill all", Toast.LENGTH_SHORT).show();
            return;
        }
        Long money = Long.parseLong(this.money.getText().toString());
        Integer month = Integer.parseInt(this.month.getText().toString());
        ProfitDesk profitDesk = new ProfitDesk(account.getAccountNumber(), money, month, bank.getProfit());
        account.setBalance(account.getBalance() - money);
        account.addDesk(profitDesk);
        Toast.makeText(this, "done successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActAddProfit.this, ActDesk.class);
        intent.putExtra("USER", user);
        startActivity(intent);

    }
}
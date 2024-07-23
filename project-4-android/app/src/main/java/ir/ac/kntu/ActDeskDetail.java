package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActDeskDetail extends AppCompatActivity {

    private User user;
    private CashDesk desk;
    private Bank bank;
    private TextView deskName;
    private TextView deskBalance;
    private TextView deskStartDate;
    private TextView deskEndDate;

    private Button remove;
    private Button deskToAcc;
    private Button accToDesk;
    private EditText amount;
    private BankAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_desk_detail);
        bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
        account = bank.userToAcc(user.getUserId());
        desk = (CashDesk) getIntent().getSerializableExtra("DESK");
        desk = account.getCashDeskMap().get(desk.getDeskId());
        initial();
    }

    private void initial() {
        try {
            deskName = findViewById(R.id.DeskName);
            deskBalance = findViewById(R.id.DeskBalance);
            deskStartDate = findViewById(R.id.DeskDate);
            deskEndDate = findViewById(R.id.DeskEnd);
            remove = findViewById(R.id.Remove);
            deskToAcc = findViewById(R.id.DesktoAccount);
            accToDesk = findViewById(R.id.AccounttoDesk);
            amount = findViewById(R.id.amountDesk);

            if (desk instanceof ProfitDesk profitDesk) {
                remove.setEnabled(false);
                deskToAcc.setEnabled(false);
                accToDesk.setEnabled(false);
                deskEndDate.setText(profitDesk.getEndDate().toString());


            }
            if (desk instanceof RemainDesk) {
                accToDesk.setEnabled(false);
            }
            System.out.println("okay2");
            deskName.setText(desk.getClass().getSimpleName());
            deskBalance.setText(desk.getBalance().toString());
            deskStartDate.setText(desk.getStartDate().toString());
        } catch (Exception e) {
            Toast.makeText(this, "intial make fail", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActDeskDetail.this, Dashboard.class);
            intent.putExtra("USER", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(ActDeskDetail.this, Dashboard.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void onClickDeskToAcc(View view) {
        if (amount.getText().toString().isEmpty()) {
            showError("fill all");
            return;
        }
        Long money = Long.parseLong(amount.getText().toString());
        if (money > desk.getBalance()) {
            showError("you dont have money");
            return;
        }
        desk.setBalance(desk.getBalance() - money);
        account.setBalance(account.getBalance() + money);
        Intent intent = new Intent(ActDeskDetail.this, Dashboard.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    public void onClickAccToDesk(View view) {
        if (amount.getText().toString().isEmpty()) {
            showError("fill all");
            return;
        }
        Long money = Long.parseLong(amount.getText().toString());
        if (money > account.getBalance()) {
            showError("you dont have money");
            return;
        }
        desk.setBalance(desk.getBalance() + money);
        account.setBalance(account.getBalance() - money);
        Intent intent = new Intent(ActDeskDetail.this, Dashboard.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void onClickRemove(View view) {
        account.setBalance(desk.getBalance() + account.getBalance());
        account.removeDesk(desk);
        Intent intent = new Intent(ActDeskDetail.this, Dashboard.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }
}
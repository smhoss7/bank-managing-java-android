package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ActDesk extends AppCompatActivity {
    private User user;
    private Bank bank;
    private BankAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_desk);
        bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
        showDesk();
    }

    public void showDesk() {
        try {


            account = bank.userToAcc(user.getUserId());
            List<String> deskName = new ArrayList<>();
            if (account != null) {
                for (CashDesk cashDesk : account.getCashDeskMap().values()) {
                    if (cashDesk instanceof SavingDesk) {
                        deskName.add("Saving Desk" + " " + cashDesk.getBalance());
                    }
                    if (cashDesk instanceof ProfitDesk) {
                        deskName.add("Profit Desk" + " " + cashDesk.getBalance());
                    }
                    if (cashDesk instanceof RemainDesk) {
                        deskName.add("Remain Desk" + " " + cashDesk.getBalance());
                    }
                }
                ListView deskListView = findViewById(R.id.DeskListView);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deskName);
                deskListView.setAdapter(adapter);
                deskListView.setOnItemClickListener((parent, view, position, id) -> {
                    CashDesk selectedDesk = (CashDesk) new ArrayList<>(account.getCashDeskMap().values()).get(position);

                    Intent intent = new Intent(ActDesk.this, ActDeskDetail.class);
                    intent.putExtra("USER", user);
                    intent.putExtra("DESK", selectedDesk);
                    startActivity(intent);
                });

            }
        } catch (Exception e) {
            Toast.makeText(this, "somthing fail", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActDesk.this, Dashboard.class);

            intent.putExtra("USER", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(ActDesk.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickAddSaving(View view) {

        SavingDesk savingDesk = new SavingDesk(account.getAccountNumber(), 0L);
        account.addDesk(savingDesk);
        Toast.makeText(this, "it add complete", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActDesk.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickAddProfit(View view) {
        Intent intent = new Intent(ActDesk.this, ActAddProfit.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void onClickAddRemain(View view) {
        RemainDesk remainDesk = new RemainDesk(account.getAccountNumber(), 0L);


        if (!account.addDesk(remainDesk)) {
            Toast.makeText(this, "you have remain desk", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "add complete", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActDesk.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
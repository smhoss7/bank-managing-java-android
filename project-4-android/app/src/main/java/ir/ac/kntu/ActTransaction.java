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

public class ActTransaction extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_transaction);
        Bank bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
        showTransaction();
    }

    public void showTransaction() {
        Bank bank = CentralBank.getInstance().getFariBank();
        BankAccount account = bank.userToAcc(user.getUserId());

        try {
            List<String> transactionName = new ArrayList<>();
            if (user != null && account != null && account.getTransactions() != null) {
                for (Transaction transaction : account.getTransactions()) {
                    transactionName.add(transaction.getType() + " " + transaction.getTime());
                    System.out.println(transaction.getReceiver());
                }
            }
            ListView transactionListView = findViewById(R.id.transactionListView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, transactionName);
            transactionListView.setAdapter(adapter);
            transactionListView.setOnItemClickListener((parent, view, position, id) -> {
                Transaction selectedTransaction = (Transaction) new ArrayList<>(account.getTransactions()).get(position);
                Intent intent = new Intent(ActTransaction.this, ActTransactionDetail.class);
                intent.putExtra("TRANSACTION", selectedTransaction);
                intent.putExtra("USER", user);
                startActivity(intent);
            });


        } catch (Exception e) {
            Toast.makeText(this, "somthing went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
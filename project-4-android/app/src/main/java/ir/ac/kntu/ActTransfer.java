package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class ActTransfer extends AppCompatActivity {

    private User user;

    private EditText accountView;
    private EditText amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_transfer);
        Bank bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());

        accountView = findViewById(R.id.AccNumTransfer);
        amount = findViewById(R.id.amountView);
        doContact();
        doAccount();
    }

    public void onClickManualy(View view) {
        if (!amount.getText().toString().matches("\\d+") || !accountView.getText().toString().matches("\\d+")) {
            Toast.makeText(this, "fill all", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println(accountView.getText().toString());
        System.out.println(CentralBank.getInstance().checkAccount(Long.valueOf(accountView.getText().toString())));

        if (CentralBank.getInstance().checkAccount(Long.valueOf(accountView.getText().toString())) == null) {
            Toast.makeText(this, "this account is not exist", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(ActTransfer.this, ActDooTransfer.class);
        intent.putExtra("RECIEVERACCNUM", accountView.getText().toString());
        intent.putExtra("USER", user);
        intent.putExtra("AMOUNT", amount.getText().toString());
        startActivity(intent);
    }

    public void doContact() {
        try {
            List<String> contactNames = new ArrayList<>();
            if (user != null && user.getContact() != null) {
                for (Person person : user.getContact()) {
                    contactNames.add(person.getFirstName() + " " + person.getLastName());
                }
            }
            ListView contactListView = findViewById(R.id.LastContact);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactNames);
            contactListView.setAdapter(adapter);
            contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Person selectedContact = (Person) new ArrayList<>(user.getContact()).get(position);
                    sendContact(selectedContact);
                }
            });

        } catch (Exception e) {
            showError();
        }

    }

    public void sendContact(Person person) {
        Bank bank = CentralBank.getInstance().getFariBank();
        User contactUser = bank.phoneToUser(person.getPhone());
        if (contactUser == null || !contactUser.togetherContact(user)) {
            Toast.makeText(this, "You cannot transfer money to this contact as they have not added you as a contact.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActTransfer.this, Dashboard.class);
            intent.putExtra("USER", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }
        if (!amount.getText().toString().matches("\\d+")) {
            showError();
            return;
        }
        Intent intent = new Intent(ActTransfer.this, ActTransferContact.class);
        intent.putExtra("CONTACT", person);
        intent.putExtra("USER", user);
        intent.putExtra("AMOUNT", amount.getText().toString());
        startActivity(intent);
    }


    public void showError() {
        Toast.makeText(this, "somthing went wrong", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActTransfer.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void doAccount() {
        try {
            Bank bank = CentralBank.getInstance().getFariBank();
            BankAccount account = bank.userToAcc(user.getUserId());

            List<String> accountNums = new ArrayList<>();
            if (account != null && account.getLastAccNum() != null) {
                for (Long bankAccount : account.getLastAccNum()) {
                    accountNums.add(bankAccount.toString());
                }
            }
            ListView accountList = findViewById(R.id.LastAcc);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accountNums);
            accountList.setAdapter(adapter);

            accountList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Long accountToSend = (Long) new ArrayList<>(account.getLastAccNum()).get(position);
                    System.out.println(accountToSend);
                    sendAcc(accountToSend);
                }
            });
        } catch (Exception e) {
            showError();
        }
    }

    public void sendAcc(Long account) {

        if (!amount.getText().toString().matches("\\d+")) {
            Toast.makeText(this, "fill correctly", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(ActTransfer.this, ActDooTransfer.class);
        intent.putExtra("RECIEVERACCNUM", account.toString());

        intent.putExtra("USER", user);
        intent.putExtra("AMOUNT", amount.getText().toString());
        startActivity(intent);

    }

    public void onClickBack(View view) {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }


}
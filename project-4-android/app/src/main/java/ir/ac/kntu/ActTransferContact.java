package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActTransferContact extends AppCompatActivity {

    private User user;
    private Person person;
    private String amount;
    private User contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_transfer_contact);

        Bank bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
        person = (Person) getIntent().getSerializableExtra("CONTACT");
        contact = bank.phoneToUser(person.getPhone());
        if (person == null || contact == null || !contact.togetherContact(user)) {

            showError();
        }
        amount = getIntent().getStringExtra("AMOUNT");
        initial();
    }


    public void initial() {
        TextView name = findViewById(R.id.acceptName);
        TextView amountView = findViewById(R.id.acceptAmount);
        TextView accountView = findViewById(R.id.acceptAccNum);

        name.setText(person.getFirstName() + " " + person.getLastName());
        amountView.setText(amount);
        accountView.setText(CentralBank.getInstance().getFariBank().userToAcc(contact.getUserId()).getAccountNumber().toString());


    }

    public void onClick(View view) {
        try {
            Bank bank = CentralBank.getInstance().getFariBank();
            BankAccount account = bank.userToAcc(user.getUserId());

            if (bank.moneyTransfer(user.getUserId(), Long.valueOf(amount), person)) {
                account.addLastAccount(bank.userToAcc(contact.getUserId()).getAccountNumber());
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
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void showError() {
        Toast.makeText(this, "somthing went wrong", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
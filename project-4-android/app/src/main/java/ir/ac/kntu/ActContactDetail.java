package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActContactDetail extends AppCompatActivity {

    private Person contact;
    private User user;
    private EditText firstName;
    private EditText lastName;
    private TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_contact_detail);
        Bank bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
        contact = user.getAddedContact(((Person) getIntent().getSerializableExtra("CONTACT")));
        if (contact == null) {
            Intent intent = new Intent(ActContactDetail.this, Dashboard.class);
            intent.putExtra("USER", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }
        firstName = findViewById(R.id.contactnameDetail);
        lastName = findViewById(R.id.contactlastDetail);
        phone = findViewById(R.id.contactphoneDetail);
        firstName.setText(contact.getFirstName());
        lastName.setText(contact.getLastName());
        phone.setText(contact.getPhone());

    }

    public void onClickChange(View view) {
        if (firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty()) {
            Toast.makeText(this, "fill all", Toast.LENGTH_SHORT).show();
            return;
        }
        Bank bank = CentralBank.getInstance().getFariBank();
        BankAccount account = bank.userToAcc(user.getUserId());

        account.updateTransaction(contact, firstName.getText().toString(), lastName.getText().toString());
        user.getCont(contact, firstName.getText().toString(), lastName.getText().toString());


        Toast.makeText(this, contact.getFirstName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActContactDetail.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickBack(View view) {


        Intent intent = new Intent(ActContactDetail.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void onClickRemove(View view) {
        user.getContact().remove(contact);
        Intent intent = new Intent(ActContactDetail.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
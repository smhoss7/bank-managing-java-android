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

public class AddContact extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);
        intialize();
    }

    public void intialize() {
        firstName = findViewById(R.id.AddFirstNameContact);
        lastName = findViewById(R.id.AddContactLastName);
        phone = findViewById(R.id.AddContactPhone);

    }

    public void onClickAdd(View view) {
        String firstName = this.firstName.getText().toString();
        String lastName = this.lastName.getText().toString();
        String phone = this.phone.getText().toString();
        Bank bank = CentralBank.getInstance().getFariBank();

        User user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
        if (!Person.checkPhone(phone)) {

            Toast.makeText(this, "phone must be 09*********", Toast.LENGTH_SHORT).show();

            return;
        }
        Person contact = new Person(firstName, lastName, phone);
        user.addContact(contact);
        Intent intent = new Intent(AddContact.this, ActContact.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
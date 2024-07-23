package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActLogin extends AppCompatActivity {
    private Bank bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(CentralBank.getInstance().getFariBank().getUsers().toString());
        super.onCreate(savedInstanceState);
        initial();
        setContentView(R.layout.activity_act_login);

    }

    public void initial() {
        bank = CentralBank.getInstance().getFariBank();
        for (User user : bank.getUsers().values()) {
            System.out.println(user.getContact().toString());
        }

    }

    public void onClickLogin(View view) {
        EditText phoneInput = findViewById(R.id.phoneInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.button);

        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();
        User user = findUserByPhone(phone);
        if (user != null && user.getPassword().equals(password) && user.isAuthentication()) {
            Toast.makeText(this, "okay", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Dashboard.class);
            intent.putExtra("USER", user);
            startActivity(intent);
        } else {

            Toast.makeText(this, "Invalid phone or password", Toast.LENGTH_SHORT).show();
        }

    }

    private User findUserByPhone(String phone) {
        return bank.phoneToUser(phone);
    }

    public void onClickSignIn(View view) {
        Intent signInIntent = new Intent(this, SignIn.class);
        startActivity(signInIntent);

    }
}
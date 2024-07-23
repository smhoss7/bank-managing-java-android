package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {

    private EditText firstNameTxt;
    private EditText lastNameTxt;
    private EditText phoneTxt;
    private EditText personalCodeTxt;
    private EditText passwordTxt;
    private Button signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        intialize();

    }

    public void intialize() {
        firstNameTxt = findViewById(R.id.firstName);
        lastNameTxt = findViewById(R.id.lastName);
        phoneTxt = findViewById(R.id.contactPhone);
        personalCodeTxt = findViewById(R.id.personalCode);
        passwordTxt = findViewById(R.id.password);
        signInButton = findViewById(R.id.button);
    }


    public void onClickSignIn(View view) {
        String phone = phoneTxt.getText().toString();
        String firstName = firstNameTxt.getText().toString();
        String lastName = lastNameTxt.getText().toString();
        String personalCode = personalCodeTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        Bank bank = CentralBank.getInstance().getFariBank();
        if (phone.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || personalCode.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!signError(bank, phone, personalCode, password)) {
            return;
        }
        User user = new User(firstName, lastName, phone, Long.valueOf(personalCode), password);
        bank.addUser(user);
        Admin.addReq(new Request(user));
        Toast.makeText(this, "sign in successfully", Toast.LENGTH_SHORT).show();

        finish();


    }

    private boolean signError(Bank bank, String phone, String personalCode, String password) {
        if (!Person.checkPhone(phone)) {
            Toast.makeText(this, "phone must be 09*********", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (bank.phoneToUser(phone) != null) {
            Toast.makeText(this, "this phone has exist", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (bank.personalCodeToUser(Long.valueOf(personalCode)) != null || !User.checkPersinalCode(personalCode)) {
            Toast.makeText(this, "this personal code has exist", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!User.checkPass(password)) {
            Toast.makeText(this, "password it must consist of uppercase and lowercase and number and sign", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onClickBack(View view) {

        Intent intent = new Intent(SignIn.this, ActLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
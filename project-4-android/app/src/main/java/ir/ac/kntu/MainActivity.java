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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("main act dooooooooooooooooooo");
        Bank bank = new Bank();
        Boss boss = new Boss("moh", "hoss", "m", "M");
        bank.addBoss(boss);
        /*User user = new User("ali", "hosseini", "09125504144", 0250367671L, "1");
        bank.addUser(user);//10000005689
        Admin.addReq(new Request(user));
        User user2 = new User("mahnaz", "rezaii", "2", 0250367672L, "2");
        bank.addUser(user2);//10000005690
        Admin.addReq(new Request(user2));
        User user3 = new User("mohammad", "hosseini", "09100677456", 0250367673L, "3");
        bank.addUser(user3);//10000005691
        Admin.addReq(new Request(user3));*/

        CentralBank.getInstance().addBank(bank, "Fari Bank");
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ThreadBossTransfer());
        executorService.execute(new ThreadDesk());
        executorService.execute(new ThreadRequest());
        System.out.println("main act dooooooooooooooooooo2");
        executorService.execute(new TreadLoan());


        Intent intent = new Intent(MainActivity.this, ActLogin.class);
        startActivity(intent);

    }

    public void onClickLogin(View view) {
        EditText phoneInput = findViewById(R.id.phoneInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.button);

        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();
        User user = findUserByPhone(phone);
        if (user != null && user.getPassword().equals(password) && user.isAuthentication()) {
            Toast.makeText(MainActivity.this, "okay", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, Dashboard.class);
            intent.putExtra("USER", user);
            startActivity(intent);
        } else {

            Toast.makeText(MainActivity.this, "Invalid phone or password", Toast.LENGTH_SHORT).show();
        }

    }


    private User findUserByPhone(String phone) {
        Bank bank = CentralBank.getInstance().getFariBank();
        return bank.phoneToUser(phone);
    }

    public void onClickSignIn(View view) {
        Intent signInIntent = new Intent(MainActivity.this, SignIn.class);
        startActivity(signInIntent);

    }


}










/*HELP
* Bundle userData = new Bundle();
userData.putSerializable("USER1", findUserByPhone(phone));
userData.putSerializable("USER2", new User("ali", "hosseini", "09100677456", 0250367671L, "Mm1!"));

Intent intent = new Intent(MainActivity.this, Dashboard.class);
intent.putExtra("USER_BUNDLE", userData);
startActivity(intent);
*
*
* Bundle userData = getIntent().getBundleExtra("USER_BUNDLE");
if (userData != null) {
    User user1 = (User) userData.getSerializable("USER1");
    User user2 = (User) userData.getSerializable("USER2");
    // ... use user1 and user2
}
* */
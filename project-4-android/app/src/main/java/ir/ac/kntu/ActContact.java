package ir.ac.kntu;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ActContact extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_contact);
        Bank bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());

        List<String> contactNames = new ArrayList<>();
        if (user != null && user.getContact() != null) {
            for (Person person : user.getContact()) {
                contactNames.add(person.getFirstName() + " " + person.getLastName());
            }
        }
        System.out.println(user.getContact().toString());
        ListView contactListView = findViewById(R.id.ContactListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactNames);
        contactListView.setAdapter(adapter);

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person selectedContact = (Person) new ArrayList<>(user.getContact()).get(position);
                Intent intent = new Intent(ActContact.this, ActContactDetail.class);
                intent.putExtra("CONTACT", selectedContact);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });


    }


    public void onClickBack(View view) {
        System.out.println(user.getContact().toString());

        Intent intent = new Intent(ActContact.this, Dashboard.class);
        intent.putExtra("USER", user);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

    public void onClickAdd(View view) {

        Intent intent = new Intent(ActContact.this, AddContact.class);
        intent.putExtra("USER", user);
        startActivity(intent);

    }

}
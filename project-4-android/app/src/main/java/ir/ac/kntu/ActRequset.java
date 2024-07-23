package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ir.ac.kntu.Request.ReqType;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ActRequset extends AppCompatActivity {

    private User user;
    private Bank bank;
    private EditText requestMessageEditText;
    private RadioGroup requestTypeRadioGroup;
    private Button submitRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_requset);
        initial();
        showReq();
    }

    public void showReq() {
        try {


            List<String> reqNames = new ArrayList<>();
            if (user != null && user.getRequests() != null) {
                for (Request request : user.getRequests()) {
                    reqNames.add(request.getUserRequest());
                }
            }
            ListView listView = findViewById(R.id.reqListView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reqNames);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {

                Request selectedReq = user.getRequests().get(position);
                Intent intent = new Intent(ActRequset.this, ActReqDetail.class);
                intent.putExtra("USER", user);
                intent.putExtra("REQ", selectedReq);
                startActivity(intent);


            });
        } catch (Exception e) {
            Toast.makeText(this, "somthing went wrong", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActRequset.this, Dashboard.class);
            intent.putExtra("USER", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }


    }

    private void initial() {
        requestMessageEditText = findViewById(R.id.requestMessageEditText);
        requestTypeRadioGroup = findViewById(R.id.requestTypeRadioGroup);
        submitRequestButton = findViewById(R.id.submitRequestButton);
        bank = CentralBank.getInstance().getFariBank();
        user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());
    }

    public void onClickCreate(View view) {
        try {


            String requestMessage = requestMessageEditText.getText().toString();
            ReqType requestType = getSelectedRequestType();
            if (requestMessage.isEmpty()) {
                Toast.makeText(this, "Please enter a request message", Toast.LENGTH_SHORT).show();
                return;
            }
            if (requestType == null) {
                Toast.makeText(this, "Please select a request type", Toast.LENGTH_SHORT).show();
                return;
            }
            Request request = new Request(requestMessage, requestType, user);
            Admin.addReq(request);
            Toast.makeText(this, "Request created successfully", Toast.LENGTH_SHORT).show();
            user.addRequest(request);
            Intent intent = new Intent(ActRequset.this, Dashboard.class);
            intent.putExtra("USER", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "somthing went wrong", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActRequset.this, Dashboard.class);
            intent.putExtra("USER", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(ActRequset.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    private ReqType getSelectedRequestType() {
        int selectedId = requestTypeRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        if (selectedRadioButton != null) {
            String selectedType = selectedRadioButton.getText().toString();
            switch (selectedType) {
                case "About Contact":
                    return ReqType.ABOUT_CONTACT;
                case "Report":
                    return ReqType.REPORT;
                case "Setting":
                    return ReqType.SETTING;
                case "Transfer":
                    return ReqType.TRANSFER;
                default:
            }
        }
        return null;
    }
}
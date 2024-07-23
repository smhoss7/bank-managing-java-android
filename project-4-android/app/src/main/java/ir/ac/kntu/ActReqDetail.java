package ir.ac.kntu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActReqDetail extends AppCompatActivity {

    private User user;
    private Bank bank;
    private Request request;
    private TextView requestMessageTextView;
    private TextView requestTypeTextView;
    private TextView adminAnswerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_req_detail);
        initial();
    }

    public void initial() {
        try {
            bank = CentralBank.getInstance().getFariBank();
            user = bank.phoneToUser(((User) getIntent().getSerializableExtra("USER")).getPhone());

            request = (Request) getIntent().getSerializableExtra("REQ");
            requestMessageTextView = findViewById(R.id.ReqStory);
            requestTypeTextView = findViewById(R.id.reqTypeDetail);
            adminAnswerTextView = findViewById(R.id.ReqAnswerDetail);

            requestMessageTextView.setText(request.getUserRequest().toString());
            requestTypeTextView.setText(request.getReqType().toString());
            if (request.getAdminAnswer() != null) {
                adminAnswerTextView.setText(request.getAdminAnswer());
            } else {
                adminAnswerTextView.setText("no answer");
            }
        } catch (Exception e) {

            Toast.makeText(this, "intial REQ make fail", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Dashboard.class);
            intent.putExtra("USER", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void inClickBack(View view) {


        Intent intent = new Intent(ActReqDetail.this, Dashboard.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
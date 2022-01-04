package kr.ac.mjc.myapplicationproject;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class JoinActivity extends AppCompatActivity {
    EditText emailEt;
    EditText passwordEt;
    EditText passwordConfirmEt;

    Button joinBtn;
    ImageView backIv;
    ProgressBar loginPb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        passwordConfirmEt = findViewById(R.id.password_confirm_et);
        joinBtn = findViewById(R.id.join_btn);
        loginPb = findViewById(R.id.loading_pb);
        backIv = findViewById(R.id.back_iv);

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                String passwordConfirm = passwordConfirmEt.getText().toString(); //변수에 저장

                if (email.equals("")) {
                    Toast.makeText(JoinActivity.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(JoinActivity.this, "비밀번호를 6자이상 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(passwordConfirm)) {
                    Toast.makeText(JoinActivity.this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    passwordConfirmEt.setText("");
                    passwordConfirmEt.requestFocus();
                    return;
                }

                login(email, password);

            }
        });
    }
    public void login(String email, String password){
        loginPb.setVisibility(View.VISIBLE);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loginPb.setVisibility(GONE);
                        if(task.isSuccessful()){
                            Toast.makeText(JoinActivity.this,"회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(JoinActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        else { //회원가입 실패한 경우
                            String message = task.getException().getMessage();
                            Toast.makeText(JoinActivity.this,message, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    }


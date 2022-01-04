package kr.ac.mjc.myapplicationproject;
import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.common.base.Charsets;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {

    EditText emailEt;
    EditText passwordEt;

    Button LoginBtn;
    Button JoinBtn;

    ProgressBar loadingPb;

    TextView ForgetPassword;

    float v = 0;

    FloatingActionButton googlelogin, twitterlogin, naverlogin;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    final int REQ_GOOGLE_SIGNIN = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser user = auth.getCurrentUser();//인증된 정보를 저장하고 있다가 들어갈 때마다 로그인 안하게
        updateUI(user); //로그인이 되어있을때만

        ForgetPassword = findViewById(R.id.passwordforget_et);
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);

        LoginBtn = findViewById(R.id.login_btn);
        JoinBtn = findViewById(R.id.join_btn);

        googlelogin = findViewById(R.id.fab_google);
        twitterlogin = findViewById(R.id.twitter_fb);
        naverlogin = findViewById(R.id.naver_nav);

        loadingPb = findViewById(R.id.loading_pb);

        naverlogin.setTranslationY(300);
        googlelogin.setTranslationY(300);
        twitterlogin.setTranslationY(300);

        naverlogin.setAlpha(v);
        googlelogin.setAlpha(v);
        twitterlogin.setAlpha(v);

        naverlogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        googlelogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        twitterlogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();

        // Configure Google Sign In 구글 로그인 통함
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("573911070146-pdlq0ct89od2sb3qkmqq0akqjc3huo38.apps.googleusercontent.com")
                .requestEmail()
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(this, gso);


        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = client.getSignInIntent();
                startActivityForResult(intent, REQ_GOOGLE_SIGNIN);
            }
        });

        JoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });


        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                if (email.equals("")) {
                    Toast.makeText(LoginActivity.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 1) {
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                login(email, password); //밑에랑 이어짐
            }
        });
        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FindActivity.class);
                startActivity(intent);
            }
        });
    }



    public void login(String email, String password) {
        loadingPb.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loadingPb.setVisibility(GONE);
                        if (task.isSuccessful()) { //로그인 성공시
                            FirebaseUser user = auth.getCurrentUser(); //로그인 정보를 받아옴
                            String email = user.getEmail(); //로그인 정보를 불러와서 toast로 띄움
                            Toast.makeText(LoginActivity.this, email + "님 로그인되셨습니다", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else { //로그인 실패시
                            String message = task.getException().getMessage();
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser user) { //로그인 되고 나서
        if (user != null) {//사용자 로그인이 null이 아닐때만, google로 로그인 만, 인증된 경우에만
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_GOOGLE_SIGNIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

            }
        }
    }

}

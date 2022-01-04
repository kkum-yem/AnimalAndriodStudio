package kr.ac.mjc.myapplicationproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.MaterialToolbar;

public class ConfigSettingActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;

    int count = 0; //(3) 클릭되는 횟수 찍히게 count 함수를 넣어줌

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configsetting);
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this); //(4) 설정값을 메모리에 저장해서 나갔다와서 count 유지되게
        count = pref.getInt("count",0); //(5) count 저장소에서 값을 가지고 옴, 값이 비어 있을 때 0이 리턴되게 해라  가지고만 오는 것이고 edit가 값 저장

        lottieAnimationView = findViewById(R.id.lottie);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Button testBtn = findViewById(R.id.test_btn); //(1) 메인에서 테스트 버튼을 클릭했을 때
        testBtn.setOnClickListener(new View.OnClickListener() { //(2) 클릭될때마다 이벤트 처리를 적어줌
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfigSettingActivity.this,ConfigActivity.class);
                startActivity(intent); //(3) configactivity를 띄우게 만들어줌
            }
        });

        Button alarmBtn = findViewById(R.id.alarm_btn); //(6) 알림 버튼 저장
        alarmBtn.setOnClickListener(new View.OnClickListener() { //(7) 알림 버튼이 클릭됐을 때 리스너
            @Override
            public void onClick(View view) {
                boolean isMessageAlarm = pref.getBoolean("message_alarm",false); //(8) sharedpreference에  있는 것을 가져옴, 설정값을 공유
                if(isMessageAlarm){ //메세지 알림이 켜져 있을때만 알림음이 울리게
                    Toast.makeText(ConfigSettingActivity.this, "새메서지가 있습니다.",Toast.LENGTH_SHORT).show();
                    boolean isSound = pref.getBoolean("sound",false); //(9) 소리도 가져옴
                    if(isSound){ // (10) 소리가 켜져 있을 때 소리가 나게
                        String soundFile = pref.getString("soundfile","카톡"); //(11) 알림음의 키값이 soundfile임, 기본 알림음이 카톡
                        MediaPlayer mediaPlayer; //(12) 음악 재생하기 위함
                        if(soundFile.equals("카톡")){
                            mediaPlayer = MediaPlayer.create(ConfigSettingActivity.this,R.raw.katok);
                        }
                        else if(soundFile.equals("카톡왔숑")){
                            mediaPlayer = MediaPlayer.create(ConfigSettingActivity.this,R.raw.katok2);
                        }
                        else {
                            mediaPlayer = MediaPlayer.create(ConfigSettingActivity.this,R.raw.kakaotalk);
                        }
                        mediaPlayer.start();
                    }
                }
            }
        });
    }
}
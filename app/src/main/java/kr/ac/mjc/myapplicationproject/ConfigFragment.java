package kr.ac.mjc.myapplicationproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import androidx.annotation.Nullable;

public class ConfigFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    //(1) fragment는 액티비티 안에 들어가야 제대로 작동

    SwitchPreference messageAlarmSP;
    SwitchPreference soundSP;
    ListPreference alarmFileListLP;
    SwitchPreference vibrateSP; //(3) 모든 위젯들 초기화


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragement_config); //(2) XML를 지정해서 화면을 지정해줌 => activity_config를 만들어냄

        messageAlarmSP = (SwitchPreference) findPreference("message_alarm");
        soundSP = (SwitchPreference) findPreference("sound");
        alarmFileListLP = (ListPreference) findPreference("soundfile");
        vibrateSP = (SwitchPreference) findPreference("vibrate"); //(4) mainactivity에서는 findviewid가 여기서는 swichpreference로 사용
        // 뒤에 나갔다 와도 저장된 내용 그대로 나타남, "키 값"으로 저장장
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity()); //(5) fragment가 여러개 들어갈 수 있도록 구조,
        // getActivity configfragment 액티비티 안에는 여러개의 프레그먼트 부모 자식 관계, 여기서 getActivity란 configActivity임
        pref.registerOnSharedPreferenceChangeListener(this); //(6) 설정정보가 변경되면 변경되는 리스너를 등록하겠다,this = configfragment =>(7)번이 실행됨

        boolean isMessageAlarm = pref.getBoolean("message_alarm", false); //(11) 그리하여 11번을 적어주는 것임
            if (isMessageAlarm) {
                soundSP.setEnabled(true);
                alarmFileListLP.setEnabled(true);
                vibrateSP.setEnabled(true);
            } else {
                soundSP.setEnabled(false);
                alarmFileListLP.setEnabled(false);
                vibrateSP.setEnabled(false);
            }
        }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) { //(7) 오버라이드 결과
        if (s.equals("message_alarm")) { // (8) s == message.alarm은 안됨 s는 키값의 주소를 비교하는 것임, 메세지 알람이 바뀔 때 다른 값들
            boolean isMessageAlarm = sharedPreferences.getBoolean(s, false); //(9) 메세지 알람 값을 가져와서 변수에 저장해줌
            if (isMessageAlarm) { //(10) messageAlarm 값을 이용해서 if else 이용, enabled 사용 가능하게 만드는 코드
                soundSP.setEnabled(true);
                alarmFileListLP.setEnabled(true);
                vibrateSP.setEnabled(true);
            } else {
                soundSP.setEnabled(false);
                alarmFileListLP.setEnabled(false);
                vibrateSP.setEnabled(false);
            }
        }// 이부분의 경우 변경됐을 때만 적용 시켜줬기 때문에 oncreate 부분에 한 번 더 적어줘야 함
    }
}

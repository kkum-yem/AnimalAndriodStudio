package kr.ac.mjc.myapplicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    LinearLayout linearLayout;
    LinearLayout linearLayoutAlarm;
    Button logoutBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) { //fragment는 보여지기 위해 createView
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { //뷰가 생성 완료 후 호출되는 함수, ACTIVITY 라이클사이크?랑 비슷한 느낌이나 다름
        super.onViewCreated(view, savedInstanceState);

        linearLayout = view.findViewById(R.id.email_lin);
        linearLayoutAlarm = view.findViewById(R.id.alram_lin);
        logoutBtn = view.findViewById(R.id.logout_btn);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linearLayout = new Intent(Intent.ACTION_SEND);
                linearLayout.setType("plain/text");
                String[] address = {"prettylee620@naver.com"};
                linearLayout.putExtra(Intent.EXTRA_EMAIL, address);
                linearLayout.putExtra(Intent.EXTRA_SUBJECT, "test@test");
                linearLayout.putExtra(Intent.EXTRA_TEXT, "내용 미리보기 (미리적을 수 있음)");
                startActivity(linearLayout);
            }
        });

        linearLayoutAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linearLayoutAlarm = new Intent(getActivity(),ConfigSettingActivity.class);
                startActivity(linearLayoutAlarm);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

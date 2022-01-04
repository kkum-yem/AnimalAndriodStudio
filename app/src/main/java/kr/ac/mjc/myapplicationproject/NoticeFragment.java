package kr.ac.mjc.myapplicationproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {

    private RecyclerView noticeListView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) { //fragment는 보여지기 위해 createView
        return inflater.inflate(R.layout.fragment_notice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { //뷰가 생성 완료 후 호출되는 함수, ACTIVITY 라이클사이크?랑 비슷한 느낌이나 다름
        super.onViewCreated(view, savedInstanceState);

        noticeListView = view.findViewById(R.id.noticeListView);
        ArrayList<Notice> noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("긴급공지사항", "2018년생 시츄", "공고종료 : 2019-11-25-am:03"));
        noticeList.add(new Notice("공지", "궁디팡팡 캣페스티벌", "무료초대권 신청 마감"));
        noticeList.add(new Notice("긴급공지사항", "2010년생 코숏치즈", "공고종료 : 2019-11-26-pm:02"));
        noticeList.add(new Notice("긴급공지사항", "2019년생 믹스견", "공고종료 : 2019-11-28-pm:03"));
        noticeList.add(new Notice("긴급공지사항", "2013년생 고슴도치", "공고종료 : 2019-11-26-am:10"));
        noticeList.add(new Notice("긴급공지사항", "2015년생 푸들", "공고종료 : 2019-11-29-pm:03"));
        noticeList.add(new Notice("긴급공지사항", "2017년생 페르시안", "공고종료 : 2019-11-28-pm:04"));
        noticeList.add(new Notice("긴급공지사항", "2016년생 시바견", "공고종료 : 2019-11-27-pm:05"));
        noticeList.add(new Notice("긴급공지사항", "2014년생 웰시코기", "공고종료 : 2019-11-26-pm:02"));
        NoticeListAdapter adapter = new NoticeListAdapter(noticeList);
        noticeListView.setAdapter(adapter);
    }
}

package kr.ac.mjc.myapplicationproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView timeLineRv;
    private TimelineAdapter timelineAdapter;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private ListenerRegistration listenerRegistration = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) { //fragment는 보여지기 위해 createView
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { //뷰가 생성 완료 후 호출되는 함수, ACTIVITY 라이클사이크?랑 비슷한 느낌이나 다름
        super.onViewCreated(view, savedInstanceState);
        timeLineRv = view.findViewById(R.id.timeline_rv);
        timelineAdapter = new TimelineAdapter(getActivity());  //데이터를 가져와서 레이아웃을 그려지게
        timeLineRv.setAdapter(timelineAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        timeLineRv.setLayoutManager(layoutManager);

        getTimeline(); //getTimeline을 불러옴
    }

    private void getTimeline() {
        if (listenerRegistration != null) return;

        listenerRegistration = firestore.collection("post")
                .orderBy("uploadDate", Query.Direction.DESCENDING) //내림차순으로 날짜 정렬
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        error.printStackTrace();
                        return;
                    }

                    ArrayList<Post> posts = new ArrayList<>();
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        posts.add(snapshot.toObject(Post.class));
                    }

                    timelineAdapter.submitList(posts);
                });
    }

    @Override
    public void onDestroy() {
        if (listenerRegistration != null) {
            listenerRegistration.remove();
            listenerRegistration = null;
        }

        super.onDestroy();
    }
}

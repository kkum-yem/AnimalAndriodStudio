package kr.ac.mjc.myapplicationproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SmsFragment extends Fragment implements MessageAdapter.OnMessageListener {

    RecyclerView messageListRv;
    EditText messageEt;
    Button submitBtn;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    List<Message> mMessageList = new ArrayList<>();
    MessageAdapter mMessageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();

        messageListRv = view.findViewById(R.id.message_list_rv);
        messageEt = view.findViewById(R.id.title_et);
        submitBtn = view.findViewById(R.id.submit_btn);

        String email = auth.getCurrentUser().getEmail(); //어댑터를 위해 이메일을 불러옴
        mMessageAdapter = new MessageAdapter(context, mMessageList, email);
        messageListRv.setAdapter(mMessageAdapter);

        mMessageAdapter.setOnMessageListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        messageListRv.setLayoutManager(layoutManager);

        firestore.collection("message")
                .orderBy("data", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<DocumentChange> documentChangeList = value.getDocumentChanges();
                        for (DocumentChange dc : documentChangeList) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Message message = dc.getDocument().toObject(Message.class); //가지고 있는 형태로 변경
                                String id = dc.getDocument().getId(); //각 각의 document에 해당하는 id 값
                                message.setId(id);
                                mMessageList.add(message);
                            }
                            if (dc.getType() == DocumentChange.Type.REMOVED) {
                                String id = dc.getDocument().getId();
                                for (Message message : mMessageList) {
                                    if (id.equals(message.getId())) {
                                        mMessageList.remove(message);
                                        break;
                                    }
                                }
                            }
                        }
                        mMessageAdapter.notifyDataSetChanged();
                        messageListRv.scrollToPosition(mMessageList.size() - 1); //항상 0부터 시작하기 때문에 -1을 넣어줌
                    }
                });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = messageEt.getText().toString();
                if (text.equals("")) {
                    return;
                } //빈 채팅일 때

                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                messageEt.setText("");

                Message message = new Message();
                message.setMessage(text);

                String email = auth.getCurrentUser().getEmail();
                message.setWriterId(email);

                firestore.collection("message")
                        .document()
                        .set(message);
            }
        });
    }

    @Override
    public void onMessageLongClick(Message message) { //onClick 된 데이터 값이 오게됨
        firestore.collection("message").document(message.getId()).delete();
    }
}

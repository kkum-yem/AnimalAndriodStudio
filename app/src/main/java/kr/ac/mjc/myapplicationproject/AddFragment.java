package kr.ac.mjc.myapplicationproject;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.ACTION_PICK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddFragment extends Fragment {

    private final int REQ_IMAGE_PICK = 1000;

    private ImageView imageIv;
    private EditText titleEt;
    private EditText messageEt;
    private Button submitBtn;

    private Uri selectedImage;

    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) { //fragment는 보여지기 위해 createView
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { //뷰가 생성 완료 후 호출되는 함수, ACTIVITY 라이클사이크?랑 비슷한 느낌이나 다름
        super.onViewCreated(view, savedInstanceState);

        imageIv = view.findViewById(R.id.image_iv);
        titleEt = view.findViewById(R.id.title_et);
        messageEt = view.findViewById(R.id.message_et);
        submitBtn = view.findViewById(R.id.submit_btn);

        imageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTION_PICK);
                intent.setType("image/*"); //전체이미지 선택가능
                startActivityForResult(intent, REQ_IMAGE_PICK);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPost();

/* Old
                if (selectedImage == null) {
                    Toast.makeText(getActivity(), "이미지를 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                String fileName = UUID.randomUUID().toString();

                storage.getReference().child("post").child(fileName).putFile(selectedImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String imageUrl = uri.toString();
                                                uploadPost(imageUrl);
                                            }
                                        });
                            }
                        });
*/
            }
        });
    }

    public void uploadPost() {
        if (selectedImage == null) {
            Toast.makeText(getActivity(), "이미지를 선택해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressDialog("업로드 중... 잠시만 기다려 주세요.");

        Handler uiHandler = new Handler(Looper.getMainLooper());

        String title = titleEt.getText().toString();
        String message = messageEt.getText().toString();
        FirebaseUser user = auth.getCurrentUser();
        String writerId;

        if (user == null) {
            writerId = "prettylee620@naver.com";
        } else {
            writerId = user.getEmail();
        }

        Post post = new Post();
        post.setTitle(title);
        post.setMessage(message);
        post.setWriterId(writerId);

        Executors.newFixedThreadPool(1).execute(() -> {
            AtomicBoolean success = new AtomicBoolean(true);

            String fileName = UUID.randomUUID().toString() + ".jpg";

            StorageReference reference = storage.getReference().child("post").child(fileName);
            StorageMetadata metadata = new StorageMetadata.Builder().setContentType("image/jpeg").build();

            try {
                Tasks.await(reference.putFile(selectedImage, metadata));
                String downloadUrl = Tasks.await(reference.getDownloadUrl()).toString();

                post.setImageUrl(downloadUrl);

                firestore.collection("post").document().set(post);

            } catch (Exception e) {
                e.printStackTrace();
                success.set(false);
            }

            uiHandler.post(() -> {
                dismissProgressDialog();

                if (success.get()) {
                    Toast.makeText(getContext(), "업로드 되었습니다.", Toast.LENGTH_SHORT).show();

                    titleEt.setText("");
                    imageIv.setImageDrawable(getActivity().getDrawable(R.drawable.baseline_add_circle_outline_black_48));

                    MainActivity mainActivity = (MainActivity) getActivity(); //액태비티 안에 들어있기에 속해있는 액태비티인 메인액티비티 호출
                    mainActivity.moveTab(R.id.action_home); //사진 올릴시 홈프레그먼트로 이동

                } else {
                    Toast.makeText(getContext(), "오류가 발생하였습니다. 잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
            });
        });

/* Old
        String message = messageEt.getText().toString();
        FirebaseUser user = auth.getCurrentUser();
        String writerId;
        if (user == null) {
            writerId = "prettylee620@naver.com";
        } else {
            writerId = user.getEmail();
        }

        Post post = new Post();
        post.setMessage(message);
        post.setImageUrl(imageUrl);
        post.setWriterId(writerId);


        firestore.collection("post").document().set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        messageEt.setText("");
                        imageIv.setImageDrawable(getActivity().getDrawable(R.drawable.baseline_add_circle_outline_black_48));

                        MainActivity mainActivity = (MainActivity) getActivity(); //액태비티 안에 들어있기에 속해있는 액태비티인 메인액티비티 호출
                        mainActivity.moveTab(0); //사진 올릴시 홈프레그먼트로 이동
                    }
                });
*/
    }

    private void showProgressDialog(String message) {
        dismissProgressDialog();

        progressDialog = ProgressDialog.show(getContext(), null, message, true, false);
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        dismissProgressDialog();

        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_IMAGE_PICK && resultCode == RESULT_OK) {
            imageIv.setImageURI(data.getData());
            selectedImage = data.getData();
        }
    }
}

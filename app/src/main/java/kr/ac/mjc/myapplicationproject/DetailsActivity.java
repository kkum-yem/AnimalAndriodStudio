package kr.ac.mjc.myapplicationproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;


public class DetailsActivity extends AppCompatActivity {

    private static final String ARGUMENT_POST = "ARGUMENT_POST";

    public static void startActivity(Context context, Post post) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(ARGUMENT_POST, post);

        context.startActivity(intent);
    }


    private Post post = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            post = getIntent().getParcelableExtra(ARGUMENT_POST);
        }

        if (post == null && savedInstanceState != null) {
            post = savedInstanceState.getParcelable(ARGUMENT_POST);
        }

        if (post == null) {
            finish();
            return;
        }

        setContentView(R.layout.activity_details);

        initUi();
    }

    private void initUi() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        ImageView imageView = findViewById(R.id.image_iv);
        TextView messageTextView = findViewById(R.id.message_contents_text_view);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        toolbar.setTitle(post.getTitle());
        Glide.with(this).load(post.getImageUrl()).into(imageView);
        messageTextView.setText(post.getMessage());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(ARGUMENT_POST, post);

        super.onSaveInstanceState(outState);
    }
}

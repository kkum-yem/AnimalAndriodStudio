package kr.ac.mjc.myapplicationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimelineAdapter extends ListAdapter<Post, TimelineAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Post> DIFF_CALLBACK = new DiffUtil.ItemCallback<Post>() {
        @Override
        public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getDocumentId().equals(newItem.getDocumentId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getMessage().equals(newItem.getMessage()) &&
                    oldItem.getImageUrl().equals(newItem.getImageUrl());
        }
    };


    Context mContext;

    public TimelineAdapter(Context context) {
        super(DIFF_CALLBACK);

        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // 레이아웃을 가져와서 뷰홀더로 넘겨주는 과정(1)
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_timeline, parent, false);

        return new ViewHolder(view); //뷰홀더에 담아서 리턴
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //(4)해당 데이터를 넣어줌
        Post post = getCurrentList().get(position);
        holder.bind(post); //바인드를 연결시켜줌
    }


    protected static class ViewHolder extends RecyclerView.ViewHolder {

        long mNow;
        Date mDate;
        SimpleDateFormat mFormat = new SimpleDateFormat("hh:mm:ss");

        ImageView profileIv;
        TextView emailTv;
        ImageView imageIv;
        TextView titleTv;
        TextView messageTv;
        TextView timeTv;

        public ViewHolder(@NonNull View itemView) { //onCreateViewHolder에서 들어오는 것들(2)
            super(itemView);
            profileIv = itemView.findViewById(R.id.profile_iv);
            emailTv = itemView.findViewById(R.id.email_tv);
            imageIv = itemView.findViewById(R.id.image_iv); //서버 안에 들어가 있음, uri에 해당하는 이미지를 넣어줌
            titleTv = itemView.findViewById(R.id.message_tv);
            messageTv = itemView.findViewById(R.id.counting_tv);
            timeTv = itemView.findViewById(R.id.time_tv);
        }

        public void bind(Post post) {//(3)
            Glide.with(imageIv).load(post.getImageUrl()).into(imageIv);
            emailTv.setText(post.getWriterId());
            titleTv.setText(post.getTitle());
            messageTv.setText(post.getMessage());
            timeTv.setText(mFormat.format(post.getUploadDate()));

            itemView.setOnClickListener(v -> DetailsActivity.startActivity(v.getContext(), post));
        }
    }
}

package kr.ac.mjc.myapplicationproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.NoticeItemViewHolder> {

    private final List<Notice> noticeList;


    public NoticeListAdapter(List<Notice> noticeList) {
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public NoticeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_notice, parent, false);
        return new NoticeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeItemViewHolder holder, int position) {
        Notice notice = noticeList.get(position);

        holder.noticeText.setText(notice.getNotice());
        holder.nameText.setText(notice.getName());
        holder.dateText.setText(notice.getDate());

        holder.itemView.setTag(notice.getNotice());
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }


    protected static class NoticeItemViewHolder extends RecyclerView.ViewHolder {

        TextView noticeText;
        TextView nameText;
        TextView dateText;

        public NoticeItemViewHolder(@NonNull View itemView) {
            super(itemView);

            noticeText = itemView.findViewById(R.id.noticeText);
            nameText = itemView.findViewById(R.id.nameText);
            dateText = itemView.findViewById(R.id.dateText);
        }
    }
}

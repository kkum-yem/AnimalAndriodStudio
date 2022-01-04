package kr.ac.mjc.myapplicationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    Context mContext;
    List<Message> mMessageList;
    String mUserId;

    final int VIEWTYPE_MY = 1;
    final int VIEWTYPE_OTHER = 2;

    OnMessageListener mListener;

    public MessageAdapter(Context context, List<Message> messageList, String userId) {
        this.mContext = context;
        this.mMessageList = messageList;
        this.mUserId = userId;
    }

    public void setOnMessageListener(OnMessageListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_MY) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_my, parent, false); //저장해서 그 레이아웃을 불러옴
            return new MyViewHolder(view);
        } else { //다른 사용자인 경우 other 레이아웃을 불러옴
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_other, parent, false);
            return new OtherViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //뷰홀더를 받음
        Message message = mMessageList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public int getItemViewType(int position) {
        Message message = mMessageList.get(position);
        //메세지 작성자의 아이디와 앱 로그인 된 아이디와 같은 경우
        if (mUserId.equals(message.getWriterId())) { //내가 작성한 메세지의 경우
            return VIEWTYPE_MY;
        } else { //다른 사용자가 작성한 message일 경우
            return VIEWTYPE_OTHER;
        }
    }

    public class OtherViewHolder extends ViewHolder {

        TextView writerTv;
        TextView messageTv;
        TextView timeTv;

        public OtherViewHolder(@NonNull View itemView) {
            super(itemView);
            writerTv = itemView.findViewById(R.id.writer_tv);
            messageTv = itemView.findViewById(R.id.message_tv);
            timeTv = itemView.findViewById(R.id.time_tv);
        }

        @Override
        void bind(Message message) {
            writerTv.setText(message.getWriterId());
            messageTv.setText(message.getMessage());
            timeTv.setText(message.getFormattedTime());
        }
    }

    public class MyViewHolder extends ViewHolder {

        TextView messageTv;
        TextView timeTv;

        Message mMessage; //메세지 삭제를 위함

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTv = itemView.findViewById(R.id.message_tv);
            timeTv = itemView.findViewById(R.id.time_tv);
        }

        @Override
        void bind(Message message) {
            mMessage = message;
            messageTv.setText(message.getMessage());
            timeTv.setText(message.getFormattedTime()); //시간을 넣어줌
            itemView.setOnLongClickListener(new View.OnLongClickListener() { //전체가 클릭됐을 때 secondActivity로 넘김
                @Override
                public boolean onLongClick(View view) {
                    mListener.onMessageLongClick(mMessage);
                    return false;
                }
            });
        }
    }

    abstract public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(Message message);
    }

    interface OnMessageListener {
        void onMessageLongClick(Message message);
    }
}

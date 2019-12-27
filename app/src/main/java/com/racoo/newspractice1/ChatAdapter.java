package com.racoo.newspractice1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset;
    private String myNickName;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView TextView_nickname;
        public TextView TextView_msg;
        public View rootView;

        public MyViewHolder(LinearLayout v) {
            super(v);

            TextView_nickname = v.findViewById(R.id.TextView_nickname);
            TextView_msg = v.findViewById(R.id.TextView_msg);
            rootView = v;
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(List<ChatData> myDataset, Context context, String myNickName) {

        mDataset = myDataset;
        this.myNickName = myNickName;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        ChatData chat = mDataset.get(position);

        holder.TextView_nickname.setText(chat.getNickname());
        holder.TextView_msg.setText(chat.getMsg());


        if(chat.getNickname().equals(this.myNickName)){
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

        } else {
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public ChatData getChat(int position){
        return mDataset != null ? mDataset.get(position) : null;
    }

    public void addChat(ChatData chat){
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1);
        //add 와 같이 데이터가 삽입되는 변화를 위해 addChat과 같은 함수를 구성하려면 리스트에 데이터를 더함과 동시에 그 데이터를 "어디에" 넣을거냐를 지정해줘야됨, 갱신용
        // 그런데 포지션인데 데이타 사이즈는 3일때 0, 1, 2 이므로 마지막 인덱을 넣어야 하는데 사이즈와 인덱스는 1차이가 나므로 일단 -1을 해줘야

    }
}
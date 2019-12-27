package com.racoo.newspractice1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<ChatData> chatList;
    private String nick = "nick2";//1:다수

    private EditText EditText_chat;
    private Button Button_send;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        EditText_chat = findViewById(R.id.EditText_chat);
        Button_send = findViewById(R.id.Button_send);

        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = EditText_chat.getText().toString(); // msg

                if(msg!=null){
                    ChatData chat = new ChatData();
                    chat.setNickname(nick);
                    chat.setMsg(msg);
                    myRef.push().setValue(chat);

                }


            }
        });

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        chatList = new ArrayList<>();
        mAdapter = new ChatAdapter(chatList, ChatActivity.this, nick);
        recyclerView.setAdapter(mAdapter);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

//        ChatData chat = new ChatData();
//        chat.setNickname(nick);
//        chat.setMsg("hi");
//        myRef.setValue(chat);



        //caution!!! 데이터베이스를 이용할 때 일정한 형식으로만 RW 해야됨. 그래서 첫 W할때는 데이터 베이스를 비워줘야 됨.


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Log.d("ChATCAHT", dataSnapshot.getValue().toString());
                ChatData chat = dataSnapshot.getValue(ChatData.class);// 또다른 형태의 클래스를 넣은 적이 있다면 이때 오류가 남.
                //addChildEventListener 은 데이터 베이스가 변경되었을 시에 포착해서 datasnapshot 에 데이터가 들어오고
                //변경된 데이터 getValue로 가져옴..

                ((ChatAdapter)mAdapter).addChat(chat);//그리고 여기 어댑터에 처넣지.


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

        //1. recyclerView - 반복
        //2. DB 내용을 넣는다.
        //3. 상대방 폰에 채팅 내용이 보임. - get

        //1-1. recyclerview - chat data
        //1. message, nickname, isMine - Data Transfer Object

}

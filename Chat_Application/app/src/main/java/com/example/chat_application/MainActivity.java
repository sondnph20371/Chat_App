package com.example.chat_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvChat;
    private EditText txtC;
    private ImageButton btnSend;
    private Chat_Adapter adapter;
    private List<String> listC;
    private final String URL = "http://10.0.2.2:3000";
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvChat = findViewById(R.id.rcvChat);
        txtC = findViewById(R.id.txtChat);
        btnSend = findViewById(R.id.btnSend);
        listC = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvChat.setLayoutManager(layoutManager);
        adapter = new Chat_Adapter(listC);
        rcvChat.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSocket.emit("chat message", txtC.getText().toString());
            }
        });
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                     String message = args[0].toString();
//                    message = data.optString("data");
                    adapter.setMessage(message);
                    Log.i("faf", message);
                }
            });
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSocket.connect();
        mSocket.on("chat message", onNewMessage);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSocket.disconnect();
    }
}
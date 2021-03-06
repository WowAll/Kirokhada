package com.example.Kirokhada.groupBuying;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Kirokhada.R;
import com.example.Kirokhada.findmate.data.UserInfo;
import com.example.Kirokhada.findmate.data.WriteInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GWritePostActivity extends AppCompatActivity {

    private static final String Tag = "GWritePostActivity";

    String itemRandomString;

    TextView titleTextView;
    TextView timeTextView;
    TextView placeTextView;
    TextView memberCountTextView;

    EditText titleEditText;
    EditText timeEditText;
    EditText placeEditText;
    EditText memberEditText;
    EditText contentEditText;

    Button upload_btn;

    // EditText에서 가져온 데이터
    String title, time, place, memberCount, content = null;

    // 업로드 시간을 체크하는 데이터
    String uploadTimeText = "";

    String name, userProfileUrl = "";

    private FirebaseUser user;
    private FirebaseFirestore fireStore;
    private FirebaseUser auth;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("users");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        init();
        setting();
        getUserProfile();
        getRandomString();

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();

                if (title != null && time != null && place != null && memberCount != null && content != null) {
                    uploadData();
                    finish();
                } else {
                    Toast showText = Toast.makeText(getApplicationContext(), "내용을 다 채워주세요~", Toast.LENGTH_SHORT);
                    showText.show();
                }
            }

        });

    }


    private void init() {

        upload_btn = findViewById(R.id.g_upload_button);

        View titleLayout = findViewById(R.id.g_title_Layer);
        View timeLayout = findViewById(R.id.g_time_Layer);
        View placeLayout = findViewById(R.id.g_place_Layer);
        View memberLayout = findViewById(R.id.g_memberCount_Layer);

        titleTextView = titleLayout.findViewById(R.id.textView_Bord);
        timeTextView = timeLayout.findViewById(R.id.textView_Bord);
        placeTextView = placeLayout.findViewById(R.id.textView_Bord);
        memberCountTextView = memberLayout.findViewById(R.id.textView_Bord);

        titleEditText = titleLayout.findViewById(R.id.editText_Bord);
        timeEditText = timeLayout.findViewById(R.id.editText_Bord);
        placeEditText = placeLayout.findViewById(R.id.editText_Bord);
        memberEditText = memberLayout.findViewById(R.id.editText_Bord);
        contentEditText = findViewById(R.id.g_content_Edit);

    }


    private void setting() {
        titleTextView.setText("글제목 :");
        timeTextView.setText("진행 날짜 :");
        placeTextView.setText("물품 :");
        memberCountTextView.setText("최소 금액 :");
    }

    private void getData() {

        title = titleEditText.getText().toString();
        time = timeEditText.getText().toString();
        place = placeEditText.getText().toString();
        memberCount = memberEditText.getText().toString();
        content = contentEditText.getText().toString();

        Date uploadTime = new Date(System.currentTimeMillis());

        SimpleDateFormat mFormat = new SimpleDateFormat("MM/dd HH:mm:ss");

        uploadTimeText = mFormat.format(uploadTime);
    }

    private void uploadData() {
        fireStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if (auth != null) {
            email = auth.getEmail();
        }

        String sc = itemRandomString;

        Log.d("asd", sc);


        WriteInfo data = new WriteInfo(title, time, place, memberCount, content, email, uploadTimeText, sc, name, userProfileUrl);

        fireStore.collection("groupBuying").document(sc).set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "업로드 성공!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
    }

    private void getUserProfile() {

        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        String uid = auth.getUid();

        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo = snapshot.getValue(UserInfo.class);

                Log.d("userInfo", userInfo.toString());
                name = userInfo.getUser_Name();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.getData() != null) {
                        userProfileUrl = (String) document.getData().get("photoUrl");
                    }
                } else {
                    Log.d("tag", "get failed with ", task.getException());
                }
            }
        });

    }




    private void getRandomString() {
        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();
        for (int i = 0; i < 20; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }

        itemRandomString = temp.toString();
    }
}


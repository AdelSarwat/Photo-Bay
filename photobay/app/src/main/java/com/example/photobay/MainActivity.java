package com.example.photobay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button SearchButton ;
    private EditText txt_search ;
    public static final String key ="D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchButton = findViewById(R.id.btn_search);
        txt_search = findViewById(R.id.editText);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txt_search.getText().toString().matches("")) {
                    Toast.makeText(MainActivity.this, "Please Add KeyWord", Toast.LENGTH_SHORT).show();
                     return;
                }
                else {

                    Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                    intent.putExtra(key, txt_search.getText().toString());
                    startActivity(intent);
                    txt_search.setText("");
                }
            }
        });
    }
}
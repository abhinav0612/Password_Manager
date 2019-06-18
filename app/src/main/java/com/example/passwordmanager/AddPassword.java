package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddPassword extends AppCompatActivity {
    EditText et_title;
    EditText et_pass;
    private String title;
    private String password;
    private  Integer position_id;

    public static final String Extra_Title="title";
    public static final String Extra_Password = "pass";
    public static final String Extra_id = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        et_title= findViewById(R.id.et_title);
        et_pass = findViewById(R.id.et_pass);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow);
        //to get all the intents passed to this activity
        //if from all those intents any one of them has id as put extra we use that
        Intent intent = getIntent();
        if (intent.hasExtra(Extra_id))
        {
            setTitle("Update Password");
            et_title.setText(intent.getStringExtra(Extra_Title));
            et_pass.setText(intent.getStringExtra(Extra_Password));
//            position_id = intent.getIntExtra(Extra_id,-1);
        }
        else
        {
            setTitle("Add Password");
        }

        position_id = intent.getIntExtra(Extra_id,-1);
        //if there is an id it means it is for update if there is nno id then it means it new entry


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_pass,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_pass:
                title = et_title.getText().toString().trim();
                password = et_pass.getText().toString().trim();
                if (title.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(this,"Enter Title and Password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent();
                    intent.putExtra(Extra_Title,title);
                    intent.putExtra(Extra_Password,password);
                    if (position_id != -1)
                    {
                        intent.putExtra(Extra_id,position_id);
                    }

                    setResult(RESULT_OK,intent);
                    finish();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

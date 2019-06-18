package com.example.passwordmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.passwordmanager.Room.PasswordEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PasswordModel passwordModel;
    private RecyclerView recyclerView;
    private FloatingActionButton button;
    public static final Integer ADD_REQUEST = 1;
    public static final Integer UPDATE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //recycler view

        final MyAdapter adapter = new MyAdapter();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setonitemclicklistner(new MyAdapter.onitemclicklistner() {
            @Override
            public void onitemclick(PasswordEntity passwordEntity) {
                Cuystom_Dialog_Fragment dialog_fragment = new Cuystom_Dialog_Fragment();
                dialog_fragment.show(getSupportFragmentManager(),"Dialog");
            }
        });
        ////////////////////////////////////////////
        passwordModel = ViewModelProviders.of(this).get(PasswordModel.class);

        passwordModel.getAllnotes().observe(this, new Observer<List<PasswordEntity>>() {
            @Override
            public void onChanged(List<PasswordEntity> passwordEntities) {
                adapter.setnotes(passwordEntities);

            }
        });
        ////////////////////////////////////////////
        button = findViewById(R.id.floating_action);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddPassword.class);
                startActivityForResult(intent,ADD_REQUEST);
            }
        });
        ///////////////////////////////////////////////
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT)
                {
                    String title;
                    String password;
                    Integer id;

                    PasswordEntity entity = adapter.getpassAt(viewHolder.getAdapterPosition());
                    title = entity.getTitle();
                    password = entity.getPassword();
                    id = entity.getId();

                    Intent intent = new Intent(MainActivity.this,AddPassword.class);
                    intent.putExtra(AddPassword.Extra_Title,title);
                    intent.putExtra(AddPassword.Extra_Password,password);
                    intent.putExtra(AddPassword.Extra_id,id);
                    startActivityForResult(intent,UPDATE_REQUEST);

                }
                if (direction==ItemTouchHelper.LEFT)
                {
                    passwordModel.delete_pass(adapter.getpassAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this,"Entry Deleted",Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADD_REQUEST && resultCode==RESULT_OK)
        {
            passwordModel.insert_pass(new PasswordEntity(data.getStringExtra(AddPassword.Extra_Title),
                    data.getStringExtra(AddPassword.Extra_Password)));
            Toast.makeText(this,"Password Added",Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == UPDATE_REQUEST && resultCode == RESULT_OK)
        {
            Integer id;
            id = data.getIntExtra(AddPassword.Extra_id,-1);
            if (id ==-1)
            {
                Toast.makeText(this,"Password Can't be Updated",Toast.LENGTH_SHORT).show();
            }
            else
            {
                PasswordEntity entity = new PasswordEntity(data.getStringExtra(AddPassword.Extra_Title)
                        ,data.getStringExtra(AddPassword.Extra_Password));
                entity.setId(id);
                passwordModel.update_pass(entity);
                Toast.makeText(this,"Password Updated",Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(this,"Password Not Added",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_all,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delete_all_entires :
                passwordModel.deleteAll_pass();
                return true;
             default:
                 return super.onOptionsItemSelected(item);
        }


    }

}

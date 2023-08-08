package com.example.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.todoapplication.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> list;
    private DrawerLayout drawerLayout;
    private ArrayAdapter<String> arrayAdapter;
    private String selectedList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView backButton = findViewById(R.id.backButton);
        final Button addButton = findViewById(R.id.addlist);
        final ImageButton menuButton = findViewById(R.id.menuButton);
        final ListView listView = findViewById(R.id.nameListView);
        drawerLayout = findViewById(R.id.Layout);
        //final ImageView search = findViewById(R.id.search);
        list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);

        listView.setAdapter(arrayAdapter);
        menuButton.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
        backButton.setOnClickListener(view -> onBackPressed());
        addButton.setOnClickListener(view -> addNameDialog());
        //search.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Search.class)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int i, final long id) {
                final Intent intent = new Intent(MainActivity.this, ChildList.class);
                selectedList = list.get(i);
                intent.putExtra("Name list", selectedList);
                startActivity(intent);

            }
        });
    }

    private void addNameDialog() {
        final EditText text = new EditText(this);
        text.setInputType(InputType.TYPE_CLASS_TEXT);

        new AlertDialog.Builder(this).setTitle("Add Name").setView(text).setPositiveButton("Ok", (dialog, which) -> {
            final String name = text.getText().toString();

            if (!name.isEmpty()) {
                final Todo todo = new Todo();
                todo.setLabel(name);
                list.add(todo.toString());
                arrayAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("Cancel", null).create().show();
    }
}
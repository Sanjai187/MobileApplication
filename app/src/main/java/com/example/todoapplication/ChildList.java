package com.example.todoapplication;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.todoapplication.model.Todo;
import com.example.todoapplication.model.TodoList;

public class ChildList extends AppCompatActivity {

    private TodoList list;
    private EditText editText;
    private TableLayout layout;
    private String selectedList;
    private DrawerLayout drawerLayout;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childlist);
        final ImageButton backButton = findViewById(R.id.backButton1);
        final Button addButton = findViewById(R.id.button);
        layout = findViewById(R.id.tableLayout);
        drawerLayout = findViewById(R.id.Layout);
        editText = findViewById(R.id.editText);
        selectedList = getIntent().getStringExtra("Name list");
        list = new TodoList(selectedList);

        if (null != selectedList) {
            final TextView textView = findViewById(R.id.textView);

            textView.setText(selectedList);
        }
        backButton.setOnClickListener(view -> onBackPressed());
        addButton.setOnClickListener(view -> addItem());
        loadTodoList(selectedList);
    }

    private void addItem() {
        final String text = editText.getText().toString();

        if (!text.isEmpty()) {
            final Todo todo = new Todo(text);

            list.add(todo);
            createTable ();
            saveTodoList();
            editText.getText().clear();
        }
    }

    private void createTable() {
        layout.removeAllViews();

        for (Todo todo : list.getAll()) {
            final TableRow table = new TableRow(this);
            final CheckBox checkBox = new CheckBox(this);
            final TextView textView = new TextView(this);
            final ImageView closeIcon = new ImageView(this);

            table.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    textView.setTextColor(Color.RED);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
            });
            table.addView(checkBox);
            textView.setText(todo.getLabel());
            table.addView(textView);
            closeIcon.setImageResource(R.drawable.close);
            closeIcon.setOnClickListener(v -> removeItem(todo));
            table.addView(closeIcon);
            layout.addView(table);
            editText.getText().clear();
        }
    }

    public void removeItem(final Todo todo) {
        list.remove(todo.getId());
        createTable();
        saveTodoList();
    }

    private void loadTodoList(final String listName) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String savedTodoItems = sharedPreferences.getString(listName, "");
        final String[] todoItems = savedTodoItems.split(",");

        for (final String todoItem : todoItems) {
            if (!todoItem.isEmpty()) {
                Todo todo = new Todo(todoItem);
                list.add(todo);
            }
        }
        createTable();
    }

    private void saveTodoList() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final StringBuilder todoItems = new StringBuilder();

        for (Todo todo : list.getAll()) {
            todoItems.append(todo.getLabel()).append(",");
        }

        editor.putString(selectedList, todoItems.toString());
        editor.apply();
    }
}
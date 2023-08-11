package com.example.todoapplication;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.todoapplication.model.Todo;
import com.example.todoapplication.model.TodoList;

/**
 * <p>
 * Representing the child project of the Todo application
 * </p>
 *
 * @author sanjai
 * @version 1.0
 */
public class ChildProject extends AppCompatActivity {

    private static final String PREF_NAME = "preference";
    private TodoList todoList;
    private EditText editText;
    private TableLayout layout;
    private String selectedList;
    private SearchView searchView;
    private Spinner spinner;

    /**
     * <p>
     * Creation of the child project
     * </p>
     *
     * @param savedInstanceState Refers the saved instance of the state
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childproject);
        final ImageButton backButton = findViewById(R.id.backButton1);
        final Button addButton = findViewById(R.id.button);
        final ImageView search = findViewById(R.id.search);
        layout = findViewById(R.id.tableLayout);
        editText = findViewById(R.id.editText);
        searchView = findViewById(R.id.searchbar);
        spinner = findViewById(R.id.statusbutton);
        selectedList = getIntent().getStringExtra("Name list");
        todoList = new TodoList(selectedList);

        if (null != selectedList) {
            final TextView textView = findViewById(R.id.textView);

            textView.setText(selectedList);
        }
        backButton.setOnClickListener(view -> onBackPressed());
        addButton.setOnClickListener(view -> addItem());
        search.setOnClickListener(view -> {
            if(searchView.getVisibility() == View.GONE) {
                searchView.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
            } else {
                searchView.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                filterTableLayout(newText);
                return true;
            }
        });
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.filter_options,  android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int position, final long l) {
                switch (position) {
                    case 0: {
                        layout.removeAllViews();
                        for (final Todo todo : todoList.getAll()) {
                            createTable(todo);
                        }
                        break;
                    }
                    case 1: {
                        layout.removeAllViews();
                        for (final Todo todo : todoList.getAll()) {
                            if (todo.isChecked()) {
                                createTable(todo);
                            }
                        }
                        break;
                    }
                    case 2: {
                        layout.removeAllViews();
                        for (final Todo todo : todoList.getAll()) {
                            if (!todo.isChecked()) {
                                createTable(todo);
                            }
                        }
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(final AdapterView<?> adapterView) {
            }
        });
        loadTodoList(selectedList);
    }

    /**
     * <p>
     * Find out the required list from table
     * </p>
     *
     * @param newText representing the new text to search from table
     */
    private void filterTableLayout(final String newText) {
        layout.removeAllViews();

        for (final Todo todo : todoList.getAll()) {

            if (todo.getLabel().toLowerCase().contains(newText.toLowerCase())) {
                createTable(todo);
            }
        }
    }

    /**
     * <p>
     * Add item to the child project
     * </p>
     */
    private void addItem() {
        final String text = editText.getText().toString();

        if (!text.isEmpty()) {
            final Todo todo = new Todo(text);

            todoList.add(todo);
            createTable(todo);
            saveTodoList();
            editText.getText().clear();
        }
    }

    /**
     * <p>
     * Create table from child table
     * </p>
     *
     * @param todo representing todo items
     */
    public void createTable(final Todo todo) {
        final TableRow table = new TableRow(this);
        final CheckBox checkBox = new CheckBox(this);
        final TextView textView = new TextView(this);
        final ImageView closeIcon = new ImageView(this);

        table.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        checkBox.setChecked(getCheckedBoxState(todo.getLabel()));
        checkBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {

            if (isChecked) {
                textView.setTextColor(Color.RED);
                todo.setChecked();
            } else {
                textView.setTextColor(Color.BLACK);
                todo.setChecked();
            }
            saveCheckedBoxState(todo.getLabel(), isChecked);
        });
        table.addView(checkBox);
        textView.setText(todo.getLabel());
        table.addView(textView);
        closeIcon.setImageResource(R.drawable.close);
        closeIcon.setOnClickListener(view -> removeItem(todo));
        table.addView(closeIcon);
        layout.addView(table);
    }

    /**
     * <p>
     * View the child project table
     * </p>
     */
    private void viewTable() {
        layout.removeAllViews();

        for (final Todo todo : todoList.getAll()) {
            createTable(todo);
            saveTodoList();
            editText.getText().clear();
        }
    }

    /**
     * <p>
     * Get the check box state
     * </p>
     *
     * @param label representing list name
     */
    private boolean getCheckedBoxState(final String label) {
        final SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        return sharedPreferences.getBoolean(label, false);
    }

    /**
     * <p>
     * Save the check box state
     * </p>
     *
     * @param label representing list name
     * @param isChecked representing list is checked or not checked
     */
    private void saveCheckedBoxState(final String label, boolean isChecked) {
        final SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(label, isChecked);
        editor.apply();
    }

    /**
     * <p>
     * Remove the item from table layout
     * </p>
     *
     * @param todo representing todo item
     */
    public void removeItem(final Todo todo) {
        todoList.remove(todo.getId());
        viewTable();
        saveTodoList();
    }

    /**
     * <p>
     * Load saved items associated with a specific list name from shared preferences
     * </p>
     *
     * @param listName Refers name of the todo list from which to load items
     */
    private void loadTodoList(final String listName) {
        final SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        final String savedTodoItems = sharedPreferences.getString(listName, "");
        final String[] todoItems = savedTodoItems.split(",");

        for (final String todoItem : todoItems) {
            if (!todoItem.isEmpty()) {
                final Todo todo = new Todo(todoItem);

                todoList.add(todo);
            }
        }
        viewTable();
    }

    /**
     * <p>
     * Saved the list of Items on shared preferences
     * </p>
     */
    private void saveTodoList() {
        final SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final StringBuilder todoItems = new StringBuilder();

        for (final Todo todo : todoList.getAll()) {
            todoItems.append(todo.getLabel()).append(",");
        }
        editor.putString(selectedList, todoItems.toString());
        editor.apply();
    }
}
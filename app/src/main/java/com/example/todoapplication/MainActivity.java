package com.example.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

/**
 * <p>
 * Representing the main activity of the Todo application
 * </p>
 *
 * @author sanjai
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private List<String> projectList;
    private DrawerLayout drawerLayout;
    private ArrayAdapter<String> arrayAdapter;
    private String selectedList;

    /**
     * <p>
     * Creation of the main activity
     * </p>
     *
     * @param savedInstanceState Refers the saved instance of the state
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView backButton = findViewById(R.id.backButton);
        final Button addButton = findViewById(R.id.addlist);
        final ImageButton menuButton = findViewById(R.id.menuButton);
        final ListView listView = findViewById(R.id.nameListView);
        drawerLayout = findViewById(R.id.Layout);
        projectList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, projectList);

        listView.setAdapter(arrayAdapter);
        menuButton.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
        backButton.setOnClickListener(view -> onBackPressed());
        addButton.setOnClickListener(view -> addNameDialog());
        listView.setOnItemClickListener((parent, view, i, id) -> {
            final Intent intent = new Intent(MainActivity.this, ChildProject.class);
            selectedList = projectList.get(i);

            intent.putExtra("Name list", selectedList);
            startActivity(intent);
        });
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            removeList(i);
            return true;
        });
    }

    /**
     * <p>
     * Close the drawer from menu
     * </p>
     */
    public void onBackPressed() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * <p>
     * Displays a dialog box for adding a new list name
     * </p>
     */
    private void addNameDialog() {
        final EditText text = new EditText(this);

        text.setInputType(InputType.TYPE_CLASS_TEXT);
        new AlertDialog.Builder(this).setTitle("Add Name").setView(text).setPositiveButton("Ok", (dialog, which) -> {
            final String name = text.getText().toString();

            if (!name.isEmpty()) {
                final Todo todo = new Todo();

                todo.setLabel(name);
                projectList.add(todo.toString());
                arrayAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("Cancel", null).create().show();
    }

    /**
     * <p>
     * Remove the list from menu list
     * </p>
     *
     * @param position representing list position
     */
    public void removeList(final int position) {
        projectList.remove(position);
        arrayAdapter.notifyDataSetChanged();
    }
}
package com.example.todo.service;

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

import com.example.todo.R;
import com.example.todo.controller.ActivatorController;
import com.example.todo.model.Project;
import com.example.todo.model.ProjectList;

import java.util.List;

/**
 *
 * <p>
 * Representing the main activity of the Todo application
 * </p>
 *
 * @author sanjai
 * @version 1.0
 */
public class Activitor extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ArrayAdapter<Project> arrayAdapter;
    private ActivatorController activatorController;
    private static Long id = 0L;

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
        final ProjectList projectList = new ProjectList();
        final List<Project> list = projectList.getAllList();
        drawerLayout = findViewById(R.id.Layout);
        activatorController = new ActivatorController(this, projectList);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);

        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        menuButton.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
        backButton.setOnClickListener(view -> drawerLayout.closeDrawer(GravityCompat.START));
        addButton.setOnClickListener(view -> activatorController.onAddNameClicked());
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            final Project selectedProject = projectList.getAllList().get(i);

            activatorController.onListItemClicked(selectedProject);
        });
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            final Project selectedProject = projectList.getAllList().get(i);

            activatorController.onListItemLongClicked(selectedProject);
            return true;
        });
    }

    /**
     * <p>
     * Displays a dialog box for adding a new project name
     * </p>
     */
    public void addNameDialog() {
        final EditText text = new EditText(this);

        text.setInputType(InputType.TYPE_CLASS_TEXT);
        new AlertDialog.Builder(this).setTitle(R.string.add_name).setView(text).setPositiveButton(R.string.ok, (dialog, which) -> {
            final String name = text.getText().toString().trim();

            activatorController.onNameAdded(name, ++id);
            arrayAdapter.notifyDataSetChanged();
        }).setNegativeButton(R.string.cancel, null).create().show();
    }

    /**
     * <p>
     * Removes a project from the project list
     * </p>
     *
     * @param project The position of the project to be removed
     */
    public void removeList(final Project project) {
        arrayAdapter.remove(project);
        arrayAdapter.notifyDataSetChanged();
    }

    public void goToListPage(final Project project) {
        final Intent intent = new Intent(Activitor.this, ChildProject.class);

        intent.putExtra(getString(R.string.project_name), project.getLabel());
        startActivity(intent);
    }
}
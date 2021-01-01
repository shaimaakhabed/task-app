package com.lab.todolistproject.Helpers;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import com.lab.todolistproject.Adapters.CategoriesAdapter;
import com.lab.todolistproject.Models.Category;
import com.lab.todolistproject.Models.Task;

public class TasksSearch {

    static Task[] allTasks;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public TasksSearch() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.getReference("users").child(uid).child("tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allTasks = new Task[(int) snapshot.getChildrenCount()];
                int i = 0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Task task = dataSnapshot.getValue(Task.class);
                    allTasks[i] = task;
                    ++i;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public static Task[] getSearchTasks(String search) {
        ArrayList<Task> tasksMatchSearch = new ArrayList<>();
        for(Task task: allTasks) {
            if(task.getTitle().toLowerCase().contains(search)) {
                tasksMatchSearch.add(task);
            }
        }

        Task[] tasksMatchSearchArray = new Task[tasksMatchSearch.size()];
        tasksMatchSearch.toArray(tasksMatchSearchArray);

        return tasksMatchSearchArray;
    }

}

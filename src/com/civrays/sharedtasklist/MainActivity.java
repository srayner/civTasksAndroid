package com.civrays.sharedtasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity implements Callback {
    
    private List<Task> tasks;
    private ArrayAdapter<Task> adapter;
    public final static String EXTRA_MESSAGE = "com.civrays.sharedtasklist.MESSAGE";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);
        
        // Get ListView object from xml
        ListView taskList = (ListView) findViewById(R.id.tasklist);
        
        // Define data items
        tasks = new ArrayList<Task>();
        
        // Create adapter and attach to view.
        adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, android.R.id.text1, tasks);
        taskList.setAdapter(adapter);
        
        // Refresh view
        refresh();
    }
    
    public boolean handleMessage(Message message) {
        Bundle bundle = message.getData();
        String string = bundle.getString("json");
        tasks.clear();
        System.out.println(string);
        try {
            JSONArray json = new JSONArray(string);
            for (int i=0; i < json.length(); i++){
               JSONObject jsonObject = json.getJSONObject(i);
               tasks.add(new Task(jsonObject.getString("text")));
            }
        } catch (Exception e) {
            
        }
        adapter.notifyDataSetChanged();     
        return false;
    }
    
    public void itemClick(View view) {
        Intent intent = new Intent(this, TaskActivity.class);
        String message = "Hello world!";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    
    public void refreshClick(View view) {
        refresh();
    }
    
    private void refresh() {
        new UpdateTaskListTask(getString(R.string.api_url), new Handler(this)).execute();
    }
}

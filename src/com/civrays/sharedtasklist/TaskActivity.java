package com.civrays.sharedtasklist;

import android.app.Activity;
import android.os.Bundle;

/**
 *
 * @author Steve
 */
public class TaskActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.task);      
    }
    
}

package com.civrays.sharedtasklist;

/**
 * Task
 * @author Steve
 */
public class Task {
    
    private String title;
    private String due;
    private boolean done;
    
    /**
     * Constructor
     * @param title - the task title.
     */
    public Task(String title){
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    
    public String toString() {
        return this.title;
    }
}

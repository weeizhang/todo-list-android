package example.com.todolist;


import android.app.Application;

import com.facebook.stetho.Stetho;

public class TodoListApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}

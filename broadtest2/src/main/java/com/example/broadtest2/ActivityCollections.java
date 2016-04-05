package com.example.broadtest2;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paozi on 2016/2/29.
 */
public class ActivityCollections {

    private static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static boolean removeActivity(Activity activity){
        return activities.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity : activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}

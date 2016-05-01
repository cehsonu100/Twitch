package com.roshni.com.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class WorkoutDetailFragment extends Fragment {
    private long workoutId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
                                                        }
         public void setWorkout(long id) {
             this.workoutId = id;
         }
}

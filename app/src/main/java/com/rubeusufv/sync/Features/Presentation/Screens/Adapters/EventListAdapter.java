package com.rubeusufv.sync.Features.Presentation.Screens.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rubeusufv.sync.Features.Domain.Models.Event;
import com.rubeusufv.sync.R;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<Event> {
    public EventListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Event> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Event event = getItem(position);
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.event_list_item, parent, false);
        }
        TextView listName = view.findViewById(R.id.eventlistName);
        TextView listTime = view.findViewById(R.id.eventListTime);
        String time = event.getStartHour() + " - " + event.getEndHour();
        listName.setText(event.getTitle());
        listTime.setText(time);
        return view;
    }
}

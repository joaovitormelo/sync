package com.rubeusufv.sync.Features.Presentation.Screens.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rubeusufv.sync.Features.Presentation.Screens.ListItems.EventDayListItem;
import com.rubeusufv.sync.R;

import java.util.ArrayList;

public class EventDayListAdapter extends ArrayAdapter<EventDayListItem> {
    EventListAdapter eventListAdapter;

    public EventDayListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<EventDayListItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        EventDayListItem eventDay = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.event_day_list_item, parent, false);
        }
        ListView eventListView = view.findViewById(R.id.eventList);
        eventListAdapter = new EventListAdapter(
                view.getContext(), R.layout.event_list_item, eventDay.getEventList()
        );
        eventListView.setAdapter(eventListAdapter);
        TextView weekdayTextView = view.findViewById(R.id.day_header_weekday);
        TextView dayTextView = view.findViewById(R.id.day_header_day);
        String weekDay = getDayOfWeekFromNumber(eventDay.getDate().getDay());
        int date = eventDay.getDate().getDate();
        int month = eventDay.getDate().getMonth();
        String dateMonth = String.format("%02d", date) + "/" + String.format("%02d", month);

        Toast.makeText(view.getContext(), String.valueOf(eventDay.getEventList().size()), Toast.LENGTH_SHORT).show();

        weekdayTextView.setText(weekDay);
        dayTextView.setText(dateMonth);

        return view;
    }

    private String getDayOfWeekFromNumber(int dayNum) {
        switch(dayNum) {
            case 0:
                return "Dom";
            case 1:
                return "Seg";
            case 2:
                return "Ter";
            case 3:
                return "Qua";
            case 4:
                return "Qui";
            case 5:
                return "Sex";
            default:
                return "Sáb";
        }
    }
}
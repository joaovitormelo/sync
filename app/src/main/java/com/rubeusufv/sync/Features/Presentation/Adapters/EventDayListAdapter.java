package com.rubeusufv.sync.Features.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Utils.DateParser;
import com.rubeusufv.sync.Features.Presentation.Types.EventDayListItem;
import com.rubeusufv.sync.R;

import java.util.ArrayList;
import java.util.Comparator;

public class EventDayListAdapter extends ArrayAdapter<EventDayListItem> {
    EventListAdapter eventListAdapter;
    private CallbackEventListItem callback;
    private Activity activity;

    public EventDayListAdapter(
        @NonNull Context context, int resource,
        @NonNull ArrayList<EventDayListItem> objects,
        CallbackEventListItem callback,
        Activity activity
    ) {
        super(context, resource, objects);
        this.callback = callback;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        EventDayListItem eventDay = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.event_day_list_item, parent, false);
        }
        ListView eventListView = view.findViewById(R.id.eventModelList);
        ArrayList<EventModel> eventList = eventDay.getEventList();
        eventList.sort(new Comparator<EventModel>() {
            @Override
            public int compare(EventModel o1, EventModel o2) {
                int[] time1 = DateParser.extractHourAndMinute(o1.getStartHour());
                int[] time2 = DateParser.extractHourAndMinute(o2.getStartHour());
                if (time1[0] < time2[0]) return -1;
                else if (time1[0] > time2[0]) return 1;
                return time1[1] - time2[1];
            }
        });
        eventListAdapter = new EventListAdapter(
            getContext(), R.layout.event_list_item, eventList,
                callback, activity
        );
        setListViewHeightBasedOnChildren(eventListView);
        eventListView.setAdapter(eventListAdapter);
        TextView weekdayTextView = view.findViewById(R.id.day_header_weekday);
        TextView dayTextView = view.findViewById(R.id.day_header_day);
        String weekDay = getDayOfWeekFromNumber(eventDay.getDate().getDay());
        int date = eventDay.getDate().getDay();
        int month = eventDay.getDate().getMonth();
        String dateMonth = String.format("%02d", date) + "/" + String.format("%02d", month);

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
    private void setListViewHeightBasedOnChildren(ListView listView) {
        int totalHeight = 0;
        for (int i = 0; i < eventListAdapter.getCount(); i++) {
            View listItem = eventListAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (eventListAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
package com.rubeusufv.sync.Features.Presentation.Screens.Adapters;

import static android.view.View.INVISIBLE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.rubeusufv.sync.Features.Domain.Models.Event;
import com.rubeusufv.sync.Features.Domain.Types.Color;
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

        CardView eventDayItem = view.findViewById(R.id.eventItem);
        ImageView rubeusIcon = view.findViewById(R.id.rubeusIcon);
        CardView rubeusIconWrapper = view.findViewById(R.id.rubeusIconWrapper);
        ImageView googleIcon = view.findViewById(R.id.googleIcon);
        CardView googleIconWrapper = view.findViewById(R.id.googleIconWrapper);

        if (!event.isRubeusSynchronized()) {
            eventDayItem.setCardBackgroundColor(view.getResources().getColor(R.color.grey));
            rubeusIcon.setVisibility(INVISIBLE);
            rubeusIconWrapper.setVisibility(INVISIBLE);
        } else {
            switch (event.getColor()) {
                case GREEN:
                    eventDayItem.setCardBackgroundColor(view.getResources().getColor(R.color.green));
                    break;
                case BLUE:
                    eventDayItem.setCardBackgroundColor(view.getResources().getColor(R.color.blue));
                    break;
                default:
                    eventDayItem.setCardBackgroundColor(view.getResources().getColor(R.color.purple));
            }
        }
        if (!event.isGoogleSynchronized()) {
            googleIcon.setVisibility(INVISIBLE);
            googleIconWrapper.setVisibility(INVISIBLE);
        }


        return view;
    }
}

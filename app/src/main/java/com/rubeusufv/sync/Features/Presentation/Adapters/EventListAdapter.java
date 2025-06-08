package com.rubeusufv.sync.Features.Presentation.Adapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.View.INVISIBLE;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Presentation.Screens.CreateEventActivity;
import com.rubeusufv.sync.R;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<EventModel> {
    public EventListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<EventModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        EventModel eventModel = getItem(position);
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.event_list_item, parent, false);
        }
        TextView listName = view.findViewById(R.id.eventlistName);
        TextView listTime = view.findViewById(R.id.eventListTime);
        String time = eventModel.getStartHour() + " - " + eventModel.getEndHour();
        listName.setText(eventModel.getTitle());
        listTime.setText(time);

        CardView eventDayItem = view.findViewById(R.id.eventItem);
        ImageView rubeusIcon = view.findViewById(R.id.rubeusIcon);
        CardView rubeusIconWrapper = view.findViewById(R.id.rubeusIconWrapper);
        ImageView googleIcon = view.findViewById(R.id.googleIcon);
        CardView googleIconWrapper = view.findViewById(R.id.googleIconWrapper);

        if (!eventModel.isRubeusImported()) {
            //eventDayItem.setCardBackgroundColor(view.getResources().getColor(R.color.grey));
            rubeusIcon.setVisibility(INVISIBLE);
            rubeusIconWrapper.setVisibility(INVISIBLE);
        } else {
            switch (eventModel.getColor()) {
                case GREEN:
                    eventDayItem.setCardBackgroundColor(view.getResources().getColor(R.color.green));
                    break;
                case BLUE:
                    eventDayItem.setCardBackgroundColor(view.getResources().getColor(R.color.blue));
                    break;
                case YELLOW:
                    eventDayItem.setCardBackgroundColor(view.getResources().getColor(R.color.yellow));
                    break;
                case PURPLE:
                    eventDayItem.setCardBackgroundColor(view.getResources().getColor(R.color.purple));
                    break;
                default:
                    eventDayItem.setCardBackgroundColor(view.getResources().getColor(R.color.red));
                    break;
            }
        }
        if (!eventModel.isGoogleImported()) {
            googleIcon.setVisibility(INVISIBLE);
            googleIconWrapper.setVisibility(INVISIBLE);
        }

        AppCompatImageButton btn = view.findViewById(R.id.btnEditEvent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), CreateEventActivity.class);
                it.addFlags(FLAG_ACTIVITY_NEW_TASK);
                it.putExtra("event", eventModel);
                getContext().startActivity(it);
            }
        });

        return view;
    }
}

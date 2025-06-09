package com.rubeusufv.sync.Features.Presentation.Adapters;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;

public interface CallbackEventListItem {
    void onDeleteEvent(EventModel event);
    void onEditEvent(EventModel event);
}

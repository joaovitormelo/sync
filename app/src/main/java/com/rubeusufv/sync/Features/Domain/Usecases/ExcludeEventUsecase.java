package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Core.Exceptions.UsecaseException;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;

public class ExcludeEventUsecase {
    private EventsDataContract rubeusData;
    private EventsDataContract googleData;
    private EventsDataContract eventsData;

    public ExcludeEventUsecase(
            EventsDataContract rubeusData, EventsDataContract googleData,
            EventsDataContract eventsData
    ) {
        this.rubeusData = rubeusData;
        this.googleData = googleData;
        this.eventsData = eventsData;
    }

    public void excludeEvent (
        EventModel event, boolean excludeFromRubeus, boolean excludeFromGoogle
    ) {
        if (excludeFromGoogle) {
            if (!event.isGoogleImported()) {
                throw new UsecaseException("Não é possível excluir da Google um evento que não está importado na Google!");
            }
            googleData.removeEvent(event);
            event.setGoogleImported(false);
        }
        if (excludeFromRubeus) {
            if (!event.isRubeusImported()) {
                throw new UsecaseException("Não é possível excluir da Rubeus um evento que não está importado na Rubeus!");
            }
            googleData.removeEvent(event);
            event.setRubeusImported(false);
        }
        if (!event.isGoogleImported() && !event.isRubeusImported()) {
            eventsData.removeEvent(event);
        }
    }
}

package com.alesaudate.rest.event;

import com.bea.wlevs.ede.api.EventRejectedException;
import com.bea.wlevs.ede.api.StreamSender;
import com.bea.wlevs.ede.api.StreamSink;
import com.bea.wlevs.ede.api.StreamSource;

public class TriggerUnconfirmedBooking implements StreamSink, StreamSource{
	
	private StreamSender sender;

	
	@Override
	public void onInsertEvent(Object event) throws EventRejectedException {
		System.out.println("Expired booking: " + event);
		sender.sendInsertEvent(event);
		
	}

	@Override
	public void setEventSender(StreamSender sender) {
		this.sender = sender;
	}
	
}

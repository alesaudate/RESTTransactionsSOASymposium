package com.alesaudate.rest.event;

import com.bea.wlevs.ede.api.EventRejectedException;
import com.bea.wlevs.ede.api.StreamSink;

public class TriggerAboutToExpireEmail implements StreamSink{
	
	
	@Override
	public void onInsertEvent(Object event) throws EventRejectedException {
		System.out.println("About to expire: " + event);
		
		
	}
	
	

}

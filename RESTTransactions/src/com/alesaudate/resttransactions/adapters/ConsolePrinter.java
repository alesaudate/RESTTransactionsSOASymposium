package com.alesaudate.resttransactions.adapters;

import com.bea.wlevs.ede.api.EventRejectedException;
import com.bea.wlevs.ede.api.StreamSink;

public class ConsolePrinter implements StreamSink{
	
	@Override
	public void onInsertEvent(Object event) throws EventRejectedException {
		System.out.println("Event >>>> :" + event );
		
	}

}

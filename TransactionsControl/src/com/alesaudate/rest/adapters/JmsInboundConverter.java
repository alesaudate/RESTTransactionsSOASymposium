package com.alesaudate.rest.adapters;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import com.bea.wlevs.adapters.jms.api.InboundMessageConverter;
import com.bea.wlevs.adapters.jms.api.MessageConverterException;


public class JmsInboundConverter implements InboundMessageConverter{
	
	
	
	@Override
	public List convert(Message arg0) throws MessageConverterException,
			JMSException {
		if (arg0 instanceof TextMessage) {
			String text = ((TextMessage) arg0).getText();
			
		}
		return null;
	}

}

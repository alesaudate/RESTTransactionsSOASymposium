package com.alesaudate.resttransactions.adapters;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import com.alesaudate.resttransactions.events.Booking;
import com.bea.wlevs.ede.api.StreamSender;
import com.bea.wlevs.ede.api.StreamSource;
import com.bea.wlevs.util.Service;

public class BookingAdapterServlet extends HttpServlet implements StreamSource {
	
	private StreamSender streamSender;
	
	
	@Override
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
	
		String bookingIdentifier = (String)arg0.getAttribute("identifier");
		
		Booking bookingEvent = new Booking();
		bookingEvent.setIdentifier(bookingIdentifier);
		
		streamSender.sendInsertEvent(bookingEvent);
		
	}
	
	
	
	@Override
	public void setEventSender(StreamSender sender) {
		this.streamSender = sender;
	}
	
	
	
	
	
	@Service(serviceType = HttpService.class)
	public void setHttpService (HttpService httpService) {
		 try {
	            httpService.registerServlet("/booking", this, null, null);
	        } catch (ServletException e) {
	            System.out.println("ERROR: Could not register servlet: "
	                    + e.getMessage());
	            e.printStackTrace();
	        } catch (NamespaceException e) {
	            System.out.println("ERROR: Could not register servlet: "
	                    + e.getMessage());
	            e.printStackTrace();
	        }
	}

}

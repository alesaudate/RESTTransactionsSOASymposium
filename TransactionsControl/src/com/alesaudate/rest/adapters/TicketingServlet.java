package com.alesaudate.rest.adapters;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import com.alesaudate.rest.event.Ticketing;
import com.bea.wlevs.ede.api.StreamSender;
import com.bea.wlevs.ede.api.StreamSource;
import com.bea.wlevs.util.Service;

public class TicketingServlet extends HttpServlet implements StreamSource{
	
private StreamSender streamSender;
	
	
	@Override
	public void setEventSender(StreamSender sender) {
		this.streamSender = sender;
	}
	
	
	@Override
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		
		String identifier = arg0.getParameter("id");
		String type = arg0.getParameter("type");
		
		
		Ticketing ticketing = new Ticketing();
		ticketing.setIdentifier(identifier);
		ticketing.setTicketingType(type);
		
		System.out.println("Recebido: " + ticketing);
		
		
		streamSender.sendInsertEvent(ticketing);		
		
	}
	
	
	
	
	@Service(serviceType= HttpService.class)
	public void setHttpService (HttpService httpService) {
		
		try {
			
            httpService.registerServlet("/ticketing", this, null, null);
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

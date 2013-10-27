package com.alesaudate.soasymposium.rest.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.alesaudate.soasymposium.rest.entities.Contact;
import com.alesaudate.soasymposium.rest.entities.hotel.Booking;
import com.alesaudate.soasymposium.rest.entities.hotel.Hotel;
import com.alesaudate.soasymposium.rest.entities.hotel.Hotels;
import com.alesaudate.soasymposium.rest.persistence.EntityManagerFactory;
import com.alesaudate.soasymposium.rest.persistence.IdentifierGenerator;


@Path("/hotel")
@Consumes("text/xml")
@Produces("text/xml")
public class HotelService {

	private EntityManager em;
	
	
	private ConnectionFactory connectionFactory;
	
	private Queue queue;
	
	public HotelService() throws NamingException {
		em = EntityManagerFactory.getInstance().getEntityManager();
		
		Context context = new InitialContext();
		connectionFactory = (ConnectionFactory)context.lookup("TicketingCF");
		
		queue = (Queue)context.lookup("TicketingQueue");
		
	}
	
	
	@GET
	public Hotels getHotels() {
		Hotels hotels = new Hotels();
		
		@SuppressWarnings("unchecked")
		List<Hotel> hotel = (List<Hotel>)em.createQuery("select h from Hotel h").getResultList();
		
		hotels.setHotels(hotel);
		
		return hotels;
	}
	
	
	@POST
	public Response addHotel(Hotel hotel) throws URISyntaxException {
		hotel.setIdentifier(IdentifierGenerator.generateId());
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(hotel);
		transaction.commit();
		return Response.created(new URI("/hotel/" + hotel.getIdentifier())).entity(hotel).build();
		
	}
	
	
	@POST
	@Path("/booking")
	public Response bookHotel(Booking booking) throws JMSException, URISyntaxException {
		
		booking.setIdentifier(IdentifierGenerator.generateId());
		if (booking.getContact() != null && !stringIsEmpty(booking.getContact().getIdentifier())) {
			
			Contact contact = em.find(Contact.class, booking.getContact().getIdentifier());
			if (contact != null) {
				booking.setContact(contact);
			}
			else {
				throw new WebApplicationException(Status.NOT_FOUND);
			}
		}
		else if (booking.getContact() != null) {
			booking.getContact().setIdentifier(IdentifierGenerator.generateId());
		}
		
		
		if (booking.getHotel() != null && !stringIsEmpty(booking.getHotel().getIdentifier())) {
			
			Hotel hotel = em.find(Hotel.class, booking.getHotel().getIdentifier());
			if (hotel != null) {
				booking.setHotel(hotel);
			}
			else {
				throw new WebApplicationException(Status.NOT_FOUND);
			}
		}
		else if (booking.getHotel() != null) {
			booking.getHotel().setIdentifier(IdentifierGenerator.generateId());
		}
		
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(booking);
		
		
		fireBookingMessage(booking);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 30);
		transaction.commit();
		
		//Should be 307 (Temporary Redirection), but OSB does not accept it
		return Response.status(307).header("Location", new URI("/hotel/booking/" + booking.getIdentifier())).header("Expires", calendar.getTime()).entity(booking).build();
	}
	
	@GET
	@Path("/booking/{id}")
	public Booking getBookFlight(@PathParam("id") String identifier) {
		
		
		Booking found = em.find(Booking.class, identifier);
		if (found == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		return found; 
		
	}
	
	@PUT
	@Path("/booking/{id}")
	public void confirmBooking (@PathParam("id") String identifier, Booking param) throws JMSException {
		System.out.println("Confirming hotel booking: " + identifier);
		Booking booking = em.find(Booking.class, identifier);
		
		if (booking != null)
			fireBookingMessage(booking.toConfirmationJson());
	}
	
	
	@DELETE
	@Path("/booking/{id}")
	public void deleteBooking (@PathParam("id") String identifier) {
		System.out.println("Discarding hotel booking: " + identifier);
		Booking booking = em.find(Booking.class, identifier);
		if (booking != null) {
			
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(booking);
			transaction.commit();
			
		}
		
	}

	
	private void fireBookingMessage(Booking booking) throws JMSException {
		fireBookingMessage(booking.toJson());
	}
	
	private void fireBookingMessage(String message) throws JMSException {
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		
		TextMessage textMessage = session.createTextMessage(message);
		
		MessageProducer producer = session.createProducer(queue);
		
		producer.send(textMessage);
		
		producer.close();
		session.close();
		connection.close();
	}
	
	private static boolean stringIsEmpty(String string) {
		return string == null || string.equals("");
	}
}

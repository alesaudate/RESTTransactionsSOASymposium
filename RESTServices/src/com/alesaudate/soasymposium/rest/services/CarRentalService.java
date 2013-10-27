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
import com.alesaudate.soasymposium.rest.entities.car.Car;
import com.alesaudate.soasymposium.rest.entities.car.CarRental;
import com.alesaudate.soasymposium.rest.entities.car.Cars;
import com.alesaudate.soasymposium.rest.persistence.EntityManagerFactory;
import com.alesaudate.soasymposium.rest.persistence.IdentifierGenerator;


@Path("/car")
@Produces("text/xml")
@Consumes("text/xml")
public class CarRentalService {
	
private EntityManager em;
	
	
	private ConnectionFactory connectionFactory;
	
	private Queue queue;
	
	public CarRentalService() throws NamingException {
		em = EntityManagerFactory.getInstance().getEntityManager();
		
		Context context = new InitialContext();
		connectionFactory = (ConnectionFactory)context.lookup("TicketingCF");
		
		queue = (Queue)context.lookup("TicketingQueue");
		
	}
	
	
	
	@GET
	public Cars getCars() {
		
		Cars cars = new Cars();
		
		@SuppressWarnings("unchecked")
		List<Car> car = em.createQuery("select c from Car c").getResultList();
		
		cars.setCars(car);
		return cars;
	}
	
	
	
	@POST
	public Response addCar(Car car) throws URISyntaxException {
		car.setIdentifier(IdentifierGenerator.generateId());
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(car);
		transaction.commit();
		return Response.created(new URI("/car/" + car.getIdentifier())).entity(car).build();
		
	}
	
	
	@POST
	@Path("/rent")
	public Response rentCar(CarRental carRental) throws URISyntaxException, JMSException {
		
		carRental.setIdentifier(IdentifierGenerator.generateId());
		if (carRental.getContact() != null && !stringIsEmpty(carRental.getContact().getIdentifier())) {
			
			Contact contact = em.find(Contact.class, carRental.getContact().getIdentifier());
			if (contact != null) {
				carRental.setContact(contact);
			}
			else {
				throw new WebApplicationException(Status.NOT_FOUND);
			}
		}
		else if (carRental.getContact() != null) {
			carRental.getContact().setIdentifier(IdentifierGenerator.generateId());
		}
		
		
		if (carRental.getCar() != null && !stringIsEmpty(carRental.getCar().getIdentifier())) {
			
			Car car = em.find(Car.class, carRental.getCar().getIdentifier());
			if (car != null) {
				carRental.setCar(car);
			}
			else {
				throw new WebApplicationException(Status.NOT_FOUND);
			}
		}
		else if (carRental.getCar() != null) {
			carRental.getCar().setIdentifier(IdentifierGenerator.generateId());
		}
		
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(carRental);
		
		
		fireBookingMessage(carRental);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 30);
		transaction.commit();
		
		//Should be 307 (Temporary Redirection), but OSB does not accept it
		return Response.status(307).header("Location", new URI("/car/rent/" + carRental.getIdentifier())).header("Expires", calendar.getTime()).entity(carRental).build();
	}


	@GET
	@Path("/rent/{id}")
	public CarRental getBookFlight(@PathParam("id") String identifier) {
		
		
		CarRental found = em.find(CarRental.class, identifier);
		if (found == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		return found; 
		
	}
	
	
	@PUT
	@Path("/rent/{id}")
	public void confirmRental (@PathParam("id") String identifier, CarRental param) throws JMSException {
		System.out.println("Confirming car rental: " + identifier);
		CarRental carRental = em.find(CarRental.class, identifier);
	
		
		if (carRental != null)
			fireBookingMessage(carRental.toConfirmationJson());
	}
	
	
	@DELETE
	@Path("/rent/{id}")
	public void deleteRental (@PathParam("id") String identifier) {
		System.out.println("Discarding car rental: " + identifier);
		CarRental carRental = em.find(CarRental.class, identifier);
		if (carRental != null) {
			
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(carRental);
			transaction.commit();
			
		}
		
	}

	private void fireBookingMessage(CarRental carRental) throws JMSException {
		fireBookingMessage(carRental.toJson());
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

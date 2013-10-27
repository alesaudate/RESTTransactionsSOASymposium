xquery version "1.0" encoding "Cp1252";
(:: pragma bea:global-element-parameter parameter="$bookFlight1" element="ns1:bookFlight" location="../../Interfaces/Contracts/FlightManagement_1_0.wsdl" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/bookFlightFromSOAPToREST/";
declare namespace ns1 = "http://services.alesaudate.com/flight";
declare namespace ns0 = "http://services.alesaudate.com/common";

declare function xf:bookFlightFromSOAPToREST($bookFlight1 as element(ns1:bookFlight))
    as element(*) {
        for $booking in $bookFlight1/*:booking
        return <booking>
        	<identifier>{data($booking/*:identifier)}</identifier>
        	{
        		for $flight in $booking/*:flight
        		return <flight>
        			<identifier>{data($flight/*:identifier)}</identifier>
        			<flightDate>{data($flight/*:flightDate)}</flightDate>
        			<fromDestination>{data($flight/*:fromDestination)}</fromDestination>
        			<toDestination>{data($flight/*:toDestination)}</toDestination>
        		</flight>
        		
        	}
        	
        	{
        		for $contact in $booking/*:contact
        		return <contact>
        			<identifier>{data($contact/*:identifier)}</identifier>
        			<name>{data($contact/*:name)}</name>
        			<email>{data($contact/*:email)}</email>
        		</contact>
        	
        	}
        </booking>
};

declare variable $bookFlight1 as element(ns1:bookFlight) external;

xf:bookFlightFromSOAPToREST($bookFlight1)
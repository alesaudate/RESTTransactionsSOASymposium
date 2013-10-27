xquery version "1.0" encoding "Cp1252";
(:: pragma bea:global-element-parameter parameter="$addFlight1" element="ns0:addFlight" location="../../Interfaces/Contracts/FlightManagement_1_0.wsdl" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/AddFlightsFromSOAPToREST/";
declare namespace ns0 = "http://services.alesaudate.com/flight";

declare function xf:AddFlightsFromSOAPToREST($addFlight1 as element(ns0:addFlight))
    as element(*) {
        
        
        for $flight in $addFlight1/*:flight
        return <flight>
        	<identifier>{data($flight/*:identifier)}</identifier>
        	<flightDate>{data($flight/*:flightDate)}</flightDate>
        	<fromDestination>{data($flight/*:fromDestination)}</fromDestination>   
        	<toDestination>{data($flight/*:toDestination)}</toDestination>     
        </flight>
};

declare variable $addFlight1 as element(ns0:addFlight) external;

xf:AddFlightsFromSOAPToREST($addFlight1)
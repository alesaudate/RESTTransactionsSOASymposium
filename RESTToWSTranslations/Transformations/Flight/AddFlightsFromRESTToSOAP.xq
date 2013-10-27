xquery version "1.0" encoding "Cp1252";
(:: pragma  parameter="$anyType1" type="xs:anyType" ::)
(:: pragma bea:global-element-return element="ns0:addFlightResponse" location="../../Interfaces/Contracts/FlightManagement_1_0.wsdl" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/AddFlightsFromRESTToSOAP/";
declare namespace ns0 = "http://services.alesaudate.com/flight";

declare function xf:AddFlightsFromRESTToSOAP($anyType1 as element(*))
    as element(ns0:addFlightResponse) {
        <ns0:addFlightResponse>
        	<ns0:flight>
        		<ns0:identifier>{data($anyType1/flight/identifier)}</ns0:identifier>
        		<ns0:flightDate>{data($anyType1/flight/flightDate)}</ns0:flightDate>
        		<ns0:fromDestination>{data($anyType1/flight/fromDestination)}</ns0:fromDestination>
        		<ns0:toDestination>{data($anyType1/flight/toDestination)}</ns0:toDestination>
        	</ns0:flight>
        </ns0:addFlightResponse>
};

declare variable $anyType1 as element(*) external;

xf:AddFlightsFromRESTToSOAP($anyType1)
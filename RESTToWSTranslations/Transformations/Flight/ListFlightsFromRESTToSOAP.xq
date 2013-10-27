xquery version "1.0" encoding "Cp1252";
(:: pragma  parameter="$anyType1" type="xs:anyType" ::)
(:: pragma bea:global-element-return element="ns0:listFlightsResponse" location="../../Interfaces/Contracts/FlightManagement_1_0.wsdl" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/ListFlightsFromRESTToSOAP/";
declare namespace ns0 = "http://services.alesaudate.com/flight";

declare function xf:ListFlightsFromRESTToSOAP($anyType1 as element(*))
    as element(ns0:listFlightsResponse) {
        <ns0:listFlightsResponse>
        	<ns0:flights>
        		{
        			for $flight in $anyType1//flight 
        		
        			return <ns0:flight>
        						<ns0:identifier>{data($flight/identifier)}</ns0:identifier>
        						<ns0:flightDate>{data($flight/flightDate)}</ns0:flightDate>
        						<ns0:toDestination>{data($flight/toDestination)}</ns0:toDestination>
        						<ns0:fromDestination>{data($flight/fromDestination)}</ns0:fromDestination>
        					</ns0:flight>
        		}
        	</ns0:flights>
        </ns0:listFlightsResponse>
};

declare variable $anyType1 as element(*) external;

xf:ListFlightsFromRESTToSOAP($anyType1)
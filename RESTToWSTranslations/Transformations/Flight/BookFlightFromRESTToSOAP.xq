xquery version "1.0" encoding "Cp1252";
(:: pragma  parameter="$anyType1" type="xs:anyType" ::)
(:: pragma bea:global-element-return element="ns1:bookFlightResponse" location="../../Interfaces/Contracts/FlightManagement_1_0.wsdl" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/BookFlightFromRESTToSOAP/";
declare namespace ns1 = "http://services.alesaudate.com/flight";
declare namespace ns0 = "http://services.alesaudate.com/common";

declare function xf:BookFlightFromRESTToSOAP($anyType1 as element(*),
    $dateTime1 as xs:string)
    as element(ns1:bookFlightResponse) {
        <ns1:bookFlightResponse>
           {
                let $booking := $anyType1
                return
                    <ns1:booking>
                       <ns1:identifier>{ data($booking/identifier) }</ns1:identifier>
                       <ns1:expires>{data($dateTime1)}</ns1:expires>
                       {
                       for $flight in $booking/flight
                            return
                                <ns1:flight>
                                	<ns1:identifier>{ data($flight/identifier) }</ns1:identifier>
                                    <ns1:flightDate>{ data($flight/flightDate) }</ns1:flightDate>
                                    <ns1:fromDestination>{ data($flight/fromDestination) }</ns1:fromDestination>
                                    <ns1:toDestination>{ data($flight/toDestination) }</ns1:toDestination>
                                </ns1:flight>
                        }
                        {
                            for $contact in $booking/contact
                            return
                                <ns0:contact>
                                    {
                                        for $identifier in $contact/identifier
                                        return
                                            <ns0:identifier>{ data($identifier) }</ns0:identifier>
                                    }
                                    {
                                        for $name in $contact/name
                                        return
                                            <ns0:name>{ data($name) }</ns0:name>
                                    }
                                    {
                                        for $email in $contact/email
                                        return
                                            <ns0:email>{ data($email) }</ns0:email>
                                    }
                                </ns0:contact>
                        }
                    </ns1:booking>
            }
        </ns1:bookFlightResponse>
};

declare variable $anyType1 as element(*) external;
declare variable $dateTime1 as xs:string external;

xf:BookFlightFromRESTToSOAP($anyType1,
    $dateTime1)
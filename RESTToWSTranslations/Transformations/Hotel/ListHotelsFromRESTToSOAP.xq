xquery version "1.0" encoding "Cp1252";
(:: pragma  parameter="$anyType1" type="xs:anyType" ::)
(:: pragma bea:global-element-return element="ns0:listHotelsResponse" location="../../Interfaces/Contracts/HotelManagement_1_0.wsdl" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/Hotel/ListHotelsFromRESTToSOAP/";
declare namespace ns0 = "http://services.alesaudate.com/hotel";

declare function xf:ListHotelsFromRESTToSOAP($anyType1 as element(*))
    as element(ns0:listHotelsResponse) {
        <ns0:listHotelsResponse>
        	<ns0:hotels>
        	{
        		for $hotel in $anyType1//hotel
        		return 
        			<ns0:hotel>
    					<ns0:identifier>{data($hotel/identifier)}</ns0:identifier>
    					<ns0:name>{data($hotel/name)}</ns0:name>
    					<ns0:address>{data($hotel/address)}</ns0:address>
  					</ns0:hotel>
        	}  
			</ns0:hotels>        
        </ns0:listHotelsResponse>
};

declare variable $anyType1 as element(*) external;

xf:ListHotelsFromRESTToSOAP($anyType1)

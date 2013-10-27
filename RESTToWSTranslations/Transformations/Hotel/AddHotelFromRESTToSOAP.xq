xquery version "1.0" encoding "Cp1252";
(:: pragma  parameter="$anyType1" type="xs:anyType" ::)
(:: pragma bea:global-element-return element="ns0:addHotelResponse" location="../../Interfaces/Contracts/HotelManagement_1_0.wsdl" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/Hotel/AddHotelFromRESTToSOAP/";
declare namespace ns0 = "http://services.alesaudate.com/hotel";

declare function xf:AddHotelFromRESTToSOAP($anyType1 as element(*))
    as element(ns0:addHotelResponse) {
        <ns0:addHotelResponse>
        	<ns0:hotel>
        		<ns0:identifier>{data($anyType1/hotel/identifier)}</ns0:identifier>
        		<ns0:name>{data($anyType1/hotel/name)}</ns0:name>
				<ns0:address>{data($anyType1/hotel/address)}</ns0:address>
			</ns0:hotel>
        </ns0:addHotelResponse>
        
        
};

declare variable $anyType1 as element(*) external;

xf:AddHotelFromRESTToSOAP($anyType1)

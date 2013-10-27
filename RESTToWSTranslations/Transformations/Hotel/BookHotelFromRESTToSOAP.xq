xquery version "1.0" encoding "Cp1252";
(:: pragma  parameter="$anyType1" type="xs:anyType" ::)
(:: pragma bea:global-element-return element="ns0:bookHotelResponse" location="../../Interfaces/Contracts/HotelManagement_1_0.wsdl" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/Hotel/BookHotelFromRESTToSOAP/";
declare namespace ns1 = "http://services.alesaudate.com/common";
declare namespace ns0 = "http://services.alesaudate.com/hotel";

declare function xf:BookHotelFromRESTToSOAP($anyType1 as element(*),
    $string1 as xs:string)
    as element(ns0:bookHotelResponse) {
        <ns0:bookHotelResponse>
        	<ns0:booking>
  				<ns0:hotel>
   					<ns0:identifier>{data($anyType1//hotel/identifier)}</ns0:identifier>
    				<ns0:name>{data($anyType1//hotel/name)}</ns0:name>
    				<ns0:address>{data($anyType1//hotel/address)}</ns0:address>
  				</ns0:hotel>
  				<ns1:contact>
    				<ns1:identifier>{data($anyType1//contact/identifier)}</ns1:identifier>
    				<ns1:name>{data($anyType1//contact/name)}</ns1:name>
    				<ns1:email>{data($anyType1//contact/email)}</ns1:email>
  				</ns1:contact>
  				<ns0:identifier>{data($anyType1/identifier)}</ns0:identifier>
  				<ns0:periodFrom>{data($anyType1/periodFrom)}</ns0:periodFrom>
  				<ns0:periodTo>{data($anyType1/periodTo)}</ns0:periodTo>
  				<ns0:numberOfPeople>{data($anyType1/numberOfPeople)}</ns0:numberOfPeople>
  				<ns0:expires>{data($string1)}</ns0:expires>
			</ns0:booking>
        </ns0:bookHotelResponse>
};

declare variable $anyType1 as element(*) external;
declare variable $string1 as xs:string external;

xf:BookHotelFromRESTToSOAP($anyType1,
    $string1)

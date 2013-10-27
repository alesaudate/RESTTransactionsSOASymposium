xquery version "1.0" encoding "Cp1252";
(:: pragma bea:global-element-parameter parameter="$bookHotel1" element="ns0:bookHotel" location="../../Interfaces/Contracts/HotelManagement_1_0.wsdl" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/Hotel/BookHotelFromSOAPToREST/";
declare namespace ns1 = "http://services.alesaudate.com/common";
declare namespace ns0 = "http://services.alesaudate.com/hotel";

declare function xf:BookHotelFromSOAPToREST($bookHotel1 as element(ns0:bookHotel))
    as element(*) {
        <booking>
  			<hotel>
    			<identifier>{data($bookHotel1//*:hotel/*:identifier)}</identifier>
    			<name>{data($bookHotel1//*:hotel/*:name)}</name>
    			<address>{data($bookHotel1//*:hotel/*:address)}</address>
  			</hotel>
  			<contact>
    			<identifier>{data($bookHotel1//*:contact/*:identifier)}</identifier>
    			<name>{data($bookHotel1//*:contact/*:name)}</name>
    			<email>{data($bookHotel1//*:contact/*:email)}</email>
  			</contact>
  			<identifier>{data($bookHotel1//*:booking/*:identifier)}</identifier>
  			<periodFrom>{data($bookHotel1//*:booking/*:periodFrom)}</periodFrom>
  			<periodTo>{data($bookHotel1//*:booking/*:periodTo)}</periodTo>
  			<numberOfPeople>{data($bookHotel1//*:booking/*:numberOfPeople)}</numberOfPeople>
		</booking>
};

declare variable $bookHotel1 as element(ns0:bookHotel) external;

xf:BookHotelFromSOAPToREST($bookHotel1)

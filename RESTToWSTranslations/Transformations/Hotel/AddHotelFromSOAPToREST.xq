xquery version "1.0" encoding "Cp1252";
(:: pragma bea:global-element-parameter parameter="$addHotel1" element="ns0:addHotel" location="../../Interfaces/Contracts/HotelManagement_1_0.wsdl" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/Hotel/AddHotelFromSOAPToREST/";
declare namespace ns0 = "http://services.alesaudate.com/hotel";

declare function xf:AddHotelFromSOAPToREST($addHotel1 as element(ns0:addHotel))
    as element(*) {
        
        
        <hotel>
			<identifier>{data($addHotel1//*:identifier)}</identifier>
			<name>{data($addHotel1//*:name)}</name>
			<address>{data($addHotel1//*:address)}</address>
		</hotel>
		
};

declare variable $addHotel1 as element(ns0:addHotel) external;

xf:AddHotelFromSOAPToREST($addHotel1)

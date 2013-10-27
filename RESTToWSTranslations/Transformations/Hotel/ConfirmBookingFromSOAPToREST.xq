xquery version "1.0" encoding "Cp1252";
(:: pragma bea:global-element-parameter parameter="$confirmBooking1" element="ns0:confirmBooking" location="../../Interfaces/Contracts/HotelManagement_1_0.wsdl" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/Hotel/ConfirmBookingFromSOAPToREST/";
declare namespace ns1 = "http://services.alesaudate.com/common";
declare namespace ns0 = "http://services.alesaudate.com/hotel";

declare function xf:ConfirmBookingFromSOAPToREST($confirmBooking1 as element(ns0:confirmBooking))
    as element(*) {
        <booking>
  			<hotel>
    			<identifier>{data($confirmBooking1//*:hotel/*:identifier)}</identifier>
    			<name>{data($confirmBooking1//*:hotel/*:name)}</name>
    			<address>{data($confirmBooking1//*:hotel/*:address)}</address>
  			</hotel>
  			<contact>
    			<identifier>{data($confirmBooking1//*:contact/*:identifier)}</identifier>
    			<name>{data($confirmBooking1//*:contact/*:name)}</name>
    			<email>{data($confirmBooking1//*:contact/*:email)}</email>
  			</contact>
  			<identifier>{data($confirmBooking1//*:booking/*:identifier)}</identifier>
  			<periodFrom>{data($confirmBooking1//*:booking/*:periodFrom)}</periodFrom>
  			<periodTo>{data($confirmBooking1//*:booking/*:periodTo)}</periodTo>
  			<numberOfPeople>{data($confirmBooking1//*:booking/*:numberOfPeople)}</numberOfPeople>
		</booking>
};

declare variable $confirmBooking1 as element(ns0:confirmBooking) external;

xf:ConfirmBookingFromSOAPToREST($confirmBooking1)

xquery version "1.0" encoding "Cp1252";
(:: pragma  parameter="$anyType1" type="xs:anyType" ::)
(:: pragma bea:global-element-return element="ns1:rentCarResponse" location="../../Interfaces/Contracts/CarRentalManagement_1_0.wsdl" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/Car/RentCarFromRESTToSOAP/";
declare namespace ns1 = "http://services.alesaudate.com/car";
declare namespace ns0 = "http://services.alesaudate.com/common";

declare function xf:RentCarFromRESTToSOAP($anyType1 as element(*),
    $string1 as xs:string)
    as element(ns1:rentCarResponse) {
         <ns1:rentCarResponse>
            <ns1:carRental>
                <ns1:identifier>{ data($anyType1/identifier) }</ns1:identifier>
                <ns1:car>
                    <ns1:identifier>{ data($anyType1/car/identifier) }</ns1:identifier>
                    <ns1:model>{ data($anyType1/car/model) }</ns1:model>
                    <ns1:year>{ data($anyType1/car/year) }</ns1:year>
                </ns1:car>
                <ns1:periodFrom>{ data($anyType1/periodFrom) }</ns1:periodFrom>
                <ns1:periodTo>{ data($anyType1/periodTo) }</ns1:periodTo>
                <ns0:contact>
                    <ns0:identifier>{ data($anyType1/contact/identifier) }</ns0:identifier>
                    <ns0:name>{ data($anyType1/contact/name) }</ns0:name>
                    <ns0:email>{ data($anyType1/contact/email) }</ns0:email>
                </ns0:contact>
                <ns1:expires>{data($string1)}</ns1:expires>
            </ns1:carRental>
        </ns1:rentCarResponse>
};

declare variable $anyType1 as element(*) external;
declare variable $string1 as xs:string external;

xf:RentCarFromRESTToSOAP($anyType1,
    $string1)

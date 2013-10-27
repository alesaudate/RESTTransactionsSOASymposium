xquery version "1.0" encoding "Cp1252";
(:: pragma bea:global-element-parameter parameter="$rentCar1" element="rentCar" location="../../Interfaces/Contracts/CarRentalManagement_1_0.wsdl" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/Car/RentCarFromSOAPToREST/";
declare namespace ns1 = "http://services.alesaudate.com/car";
declare namespace ns0 = "http://services.alesaudate.com/common";

declare function xf:RentCarFromSOAPToREST($rentCar1 as element(ns1:rentCar))
    as element(*) {
            <carRental>
                <identifier>{ data($rentCar1//*:carRental/*:identifier) }</identifier>
                <car>
                    <identifier>{ data($rentCar1//*:carRental/*:car/*:identifier) }</identifier>
                    <model>{ data($rentCar1//*:carRental/*:car/*:model) }</model>
                    <year>{ data($rentCar1//*:carRental/*:car/*:year) }</year>
                </car>
                <periodFrom>{ data($rentCar1//*:carRental/*:periodFrom) }</periodFrom>
                <periodTo>{ data($rentCar1//*:carRental/*:periodTo) }</periodTo>
                <contact>
                    <identifier>{ data($rentCar1//*:carRental/*:contact/*:identifier) }</identifier>
                    <name>{ data($rentCar1//*:carRental/*:contact/*:name) }</name>
                    <email>{ data($rentCar1//*:carRental/*:contact/*:email) }</email>
                </contact>
            </carRental>
        
};

declare variable $rentCar1 as element(ns1:rentCar) external;

xf:RentCarFromSOAPToREST($rentCar1)

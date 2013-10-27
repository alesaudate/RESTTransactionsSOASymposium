xquery version "1.0" encoding "Cp1252";
(:: pragma bea:global-element-parameter parameter="$confirmRental1" element="ns1:confirmRental" location="../../Interfaces/Contracts/CarRentalManagement_1_0.wsdl" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/Car/ConfirmRentalFromSOAPToREST/";
declare namespace ns1 = "http://services.alesaudate.com/car";
declare namespace ns0 = "http://services.alesaudate.com/common";

declare function xf:ConfirmRentalFromSOAPToREST($confirmRental1 as element(ns1:confirmRental))
    as element(*) {
        <carRental>
                <identifier>{ data($confirmRental1//*:carRental/*:identifier) }</identifier>
                <car>
                    <identifier>{ data($confirmRental1//*:carRental/*:car/*:identifier) }</identifier>
                    <model>{ data($confirmRental1//*:carRental/*:car/*:model) }</model>
                    <year>{ data($confirmRental1//*:carRental/*:car/*:year) }</year>
                </car>
                <periodFrom>{ data($confirmRental1//*:carRental/*:periodFrom) }</periodFrom>
                <periodTo>{ data($confirmRental1//*:carRental/*:periodTo) }</periodTo>
                <contact>
                    <identifier>{ data($confirmRental1//*:carRental/*:contact/*:identifier) }</identifier>
                    <name>{ data($confirmRental1//*:carRental/*:contact/*:name) }</name>
                    <email>{ data($confirmRental1//*:carRental/*:contact/*:email) }</email>
                </contact>
            </carRental>
};

declare variable $confirmRental1 as element(ns1:confirmRental) external;

xf:ConfirmRentalFromSOAPToREST($confirmRental1)

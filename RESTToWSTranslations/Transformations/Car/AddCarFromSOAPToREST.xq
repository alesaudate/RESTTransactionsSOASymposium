xquery version "1.0" encoding "Cp1252";
(:: pragma bea:global-element-parameter parameter="$addCar1" element="addCar" location="../../Interfaces/Contracts/CarRentalManagement_1_0.wsdl" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/Car/AddCarFromSOAPToREST/";
declare namespace ns0 = "http://services.alesaudate.com/car";

declare function xf:AddCarFromSOAPToREST($addCar1 as element(ns0:addCar))
    as element(*) {
         <car>
         	<identifier>{ data($addCar1//*:identifier) }</identifier>
            <model>{ data($addCar1//*:model) }</model>
            <year>{ data($addCar1//*:year) }</year>
		 </car>
};

declare variable $addCar1 as element(ns0:addCar) external;

xf:AddCarFromSOAPToREST($addCar1)

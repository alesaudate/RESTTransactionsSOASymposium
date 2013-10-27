(:: pragma  parameter="$anyType1" type="anyType" ::)
(:: pragma bea:global-element-return element="ns0:addCarResponse" location="../../Interfaces/Contracts/CarRentalManagement_1_0.wsdl" ::)

declare namespace ns0 = "http://services.alesaudate.com/car";
declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/Car/AddCarFromRESTToSOAP/";

declare function xf:AddCarFromRESTToSOAP($anyType1 as element(*))
    as element(ns0:addCarResponse) {
        <ns0:addCarResponse>
            <ns0:car>
                <ns0:identifier>{ data($anyType1/car/identifier) }</ns0:identifier>
                <ns0:model>{ data($anyType1/car/model) }</ns0:model>
                <ns0:year>{ data($anyType1/car/year) }</ns0:year>
            </ns0:car>
        </ns0:addCarResponse>
};

declare variable $anyType1 as element(*) external;

xf:AddCarFromRESTToSOAP($anyType1)

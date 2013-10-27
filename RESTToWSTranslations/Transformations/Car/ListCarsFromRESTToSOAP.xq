(:: pragma  parameter="$anyType1" type="anyType" ::)
(:: pragma bea:global-element-return element="ns0:listCarsResponse" location="../../Interfaces/Contracts/CarRentalManagement_1_0.wsdl" ::)

declare namespace ns0 = "http://services.alesaudate.com/car";
declare namespace xf = "http://tempuri.org/RESTToWSTranslations/Transformations/Car/ListCarsFromRESTToSOAP/";

declare function xf:ListCarsFromRESTToSOAP($anyType1 as element(*))
    as element(ns0:listCarsResponse) {
        <ns0:listCarsResponse>
            <ns0:cars>
            	{
            	for $car in $anyType1//car
            	return 
                <ns0:car>
                    <ns0:identifier>{ data($car/identifier) }</ns0:identifier>
                    <ns0:model>{ data($car/model) }</ns0:model>
                    <ns0:year>{ data($car/year) }</ns0:year>
                </ns0:car>
                }
            </ns0:cars>
        </ns0:listCarsResponse>
};

declare variable $anyType1 as element(*) external;

xf:ListCarsFromRESTToSOAP($anyType1)

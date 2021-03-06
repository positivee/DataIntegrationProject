
package client;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Katalog", targetNamespace = "http://integracja/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Katalog {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.Integer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCountOfManufacturer", targetNamespace = "http://integracja/", className = "client.GetCountOfManufacturer")
    @ResponseWrapper(localName = "getCountOfManufacturerResponse", targetNamespace = "http://integracja/", className = "client.GetCountOfManufacturerResponse")
    @Action(input = "http://integracja/Katalog/getCountOfManufacturerRequest", output = "http://integracja/Katalog/getCountOfManufacturerResponse")
    public Integer getCountOfManufacturer(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.Integer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCountOfResolution", targetNamespace = "http://integracja/", className = "client.GetCountOfResolution")
    @ResponseWrapper(localName = "getCountOfResolutionResponse", targetNamespace = "http://integracja/", className = "client.GetCountOfResolutionResponse")
    @Action(input = "http://integracja/Katalog/getCountOfResolutionRequest", output = "http://integracja/Katalog/getCountOfResolutionResponse")
    public Integer getCountOfResolution(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<client.StringArray>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getListOfelementValue", targetNamespace = "http://integracja/", className = "client.GetListOfelementValue")
    @ResponseWrapper(localName = "getListOfelementValueResponse", targetNamespace = "http://integracja/", className = "client.GetListOfelementValueResponse")
    @Action(input = "http://integracja/Katalog/getListOfelementValueRequest", output = "http://integracja/Katalog/getListOfelementValueResponse")
    public List<StringArray> getListOfelementValue(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "distinctList", targetNamespace = "http://integracja/", className = "client.DistinctList")
    @ResponseWrapper(localName = "distinctListResponse", targetNamespace = "http://integracja/", className = "client.DistinctListResponse")
    @Action(input = "http://integracja/Katalog/distinctListRequest", output = "http://integracja/Katalog/distinctListResponse")
    public List<String> distinctList(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}

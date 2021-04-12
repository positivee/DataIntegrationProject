
package client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DistinctList_QNAME = new QName("http://integracja/", "distinctList");
    private final static QName _DistinctListResponse_QNAME = new QName("http://integracja/", "distinctListResponse");
    private final static QName _GetCountOfManufacturer_QNAME = new QName("http://integracja/", "getCountOfManufacturer");
    private final static QName _GetCountOfManufacturerResponse_QNAME = new QName("http://integracja/", "getCountOfManufacturerResponse");
    private final static QName _GetCountOfResolution_QNAME = new QName("http://integracja/", "getCountOfResolution");
    private final static QName _GetCountOfResolutionResponse_QNAME = new QName("http://integracja/", "getCountOfResolutionResponse");
    private final static QName _GetListOfelementValue_QNAME = new QName("http://integracja/", "getListOfelementValue");
    private final static QName _GetListOfelementValueResponse_QNAME = new QName("http://integracja/", "getListOfelementValueResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StringArray }
     * 
     */
    public StringArray createStringArray() {
        return new StringArray();
    }

    /**
     * Create an instance of {@link DistinctList }
     * 
     */
    public DistinctList createDistinctList() {
        return new DistinctList();
    }

    /**
     * Create an instance of {@link DistinctListResponse }
     * 
     */
    public DistinctListResponse createDistinctListResponse() {
        return new DistinctListResponse();
    }

    /**
     * Create an instance of {@link GetCountOfManufacturer }
     * 
     */
    public GetCountOfManufacturer createGetCountOfManufacturer() {
        return new GetCountOfManufacturer();
    }

    /**
     * Create an instance of {@link GetCountOfManufacturerResponse }
     * 
     */
    public GetCountOfManufacturerResponse createGetCountOfManufacturerResponse() {
        return new GetCountOfManufacturerResponse();
    }

    /**
     * Create an instance of {@link GetCountOfResolution }
     * 
     */
    public GetCountOfResolution createGetCountOfResolution() {
        return new GetCountOfResolution();
    }

    /**
     * Create an instance of {@link GetCountOfResolutionResponse }
     * 
     */
    public GetCountOfResolutionResponse createGetCountOfResolutionResponse() {
        return new GetCountOfResolutionResponse();
    }

    /**
     * Create an instance of {@link GetListOfelementValue }
     * 
     */
    public GetListOfelementValue createGetListOfelementValue() {
        return new GetListOfelementValue();
    }

    /**
     * Create an instance of {@link GetListOfelementValueResponse }
     * 
     */
    public GetListOfelementValueResponse createGetListOfelementValueResponse() {
        return new GetListOfelementValueResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DistinctList }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DistinctList }{@code >}
     */
    @XmlElementDecl(namespace = "http://integracja/", name = "distinctList")
    public JAXBElement<DistinctList> createDistinctList(DistinctList value) {
        return new JAXBElement<DistinctList>(_DistinctList_QNAME, DistinctList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DistinctListResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DistinctListResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://integracja/", name = "distinctListResponse")
    public JAXBElement<DistinctListResponse> createDistinctListResponse(DistinctListResponse value) {
        return new JAXBElement<DistinctListResponse>(_DistinctListResponse_QNAME, DistinctListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCountOfManufacturer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCountOfManufacturer }{@code >}
     */
    @XmlElementDecl(namespace = "http://integracja/", name = "getCountOfManufacturer")
    public JAXBElement<GetCountOfManufacturer> createGetCountOfManufacturer(GetCountOfManufacturer value) {
        return new JAXBElement<GetCountOfManufacturer>(_GetCountOfManufacturer_QNAME, GetCountOfManufacturer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCountOfManufacturerResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCountOfManufacturerResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://integracja/", name = "getCountOfManufacturerResponse")
    public JAXBElement<GetCountOfManufacturerResponse> createGetCountOfManufacturerResponse(GetCountOfManufacturerResponse value) {
        return new JAXBElement<GetCountOfManufacturerResponse>(_GetCountOfManufacturerResponse_QNAME, GetCountOfManufacturerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCountOfResolution }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCountOfResolution }{@code >}
     */
    @XmlElementDecl(namespace = "http://integracja/", name = "getCountOfResolution")
    public JAXBElement<GetCountOfResolution> createGetCountOfResolution(GetCountOfResolution value) {
        return new JAXBElement<GetCountOfResolution>(_GetCountOfResolution_QNAME, GetCountOfResolution.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCountOfResolutionResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCountOfResolutionResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://integracja/", name = "getCountOfResolutionResponse")
    public JAXBElement<GetCountOfResolutionResponse> createGetCountOfResolutionResponse(GetCountOfResolutionResponse value) {
        return new JAXBElement<GetCountOfResolutionResponse>(_GetCountOfResolutionResponse_QNAME, GetCountOfResolutionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListOfelementValue }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetListOfelementValue }{@code >}
     */
    @XmlElementDecl(namespace = "http://integracja/", name = "getListOfelementValue")
    public JAXBElement<GetListOfelementValue> createGetListOfelementValue(GetListOfelementValue value) {
        return new JAXBElement<GetListOfelementValue>(_GetListOfelementValue_QNAME, GetListOfelementValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListOfelementValueResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetListOfelementValueResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://integracja/", name = "getListOfelementValueResponse")
    public JAXBElement<GetListOfelementValueResponse> createGetListOfelementValueResponse(GetListOfelementValueResponse value) {
        return new JAXBElement<GetListOfelementValueResponse>(_GetListOfelementValueResponse_QNAME, GetListOfelementValueResponse.class, null, value);
    }

}

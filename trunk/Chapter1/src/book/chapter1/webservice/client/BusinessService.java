
package book.chapter1.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "BusinessService", targetNamespace = "http://webservice.chapter1.book/client", wsdlLocation = "http://localhost:9527/BusinessService?wsdl")
public class BusinessService
    extends Service
{

    private final static URL BUSINESSSERVICE_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("http://localhost:9527/BusinessService?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BUSINESSSERVICE_WSDL_LOCATION = url;
    }

    public BusinessService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BusinessService() {
        super(BUSINESSSERVICE_WSDL_LOCATION, new QName("http://webservice.chapter1.book/client", "BusinessService"));
    }

    /**
     * 
     * @return
     *     returns Business
     */
    @WebEndpoint(name = "BusinessPort")
    public Business getBusinessPort() {
        return (Business)super.getPort(new QName("http://webservice.chapter1.book/client", "BusinessPort"), Business.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Business
     */
    @WebEndpoint(name = "BusinessPort")
    public Business getBusinessPort(WebServiceFeature... features) {
        return (Business)super.getPort(new QName("http://webservice.chapter1.book/client", "BusinessPort"), Business.class, features);
    }

}
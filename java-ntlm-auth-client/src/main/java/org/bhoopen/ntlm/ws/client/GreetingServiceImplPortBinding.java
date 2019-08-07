
package org.bhoopen.ntlm.ws.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "GreetingServiceImplPortBinding", targetNamespace = "http://impl.greeting.ws.rpsoft.com/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface GreetingServiceImplPortBinding {


    /**
     * 
     * @param personName
     * @return
     *     returns org.bhoopen.ntlm.ws.client.GreetingMessage
     */
    @WebMethod(action = "http://rpsoft.com/ws/greeting/sendGreeting")
    @WebResult(name = "GreetingMessage", targetNamespace = "http://rpsoft.com/ws/greeting/beans", partName = "sendGreetingResult")
    public GreetingMessage sendGreeting(
        @WebParam(name = "PersonName", targetNamespace = "http://rpsoft.com/ws/greeting/beans", partName = "PersonName")
        PersonName personName);

}


package com.rpsing.net.ws.client.stub;

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
@WebService(name = "GreetingServicePort", targetNamespace = "http://rpsoft.com/ws/greeting")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface GreetingServicePort {


    /**
     * 
     * @param personName
     * @return
     *     returns com.rpsing.net.ws.client.stub.GreetingMessage
     * @throws GreetingException_Exception
     */
    @WebMethod(action = "http://rpsoft.com/ws/greeting/sendGreeting")
    @WebResult(name = "GreetingMessage", targetNamespace = "http://rpsoft.com/ws/greeting/beans", partName = "greetingMessage")
    public GreetingMessage sendGreeting(
        @WebParam(name = "PersonName", targetNamespace = "http://rpsoft.com/ws/greeting/beans", partName = "personName")
        PersonName personName)
        throws GreetingException_Exception
    ;

}
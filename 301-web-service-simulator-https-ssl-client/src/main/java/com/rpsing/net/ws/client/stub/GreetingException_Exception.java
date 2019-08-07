
package com.rpsing.net.ws.client.stub;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "GreetingException", targetNamespace = "http://rpsoft.com/ws/greeting/beans")
public class GreetingException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private GreetingException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public GreetingException_Exception(String message, GreetingException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public GreetingException_Exception(String message, GreetingException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.rpsing.net.ws.client.stub.GreetingException
     */
    public GreetingException getFaultInfo() {
        return faultInfo;
    }

}
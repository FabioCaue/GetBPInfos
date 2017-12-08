/**
 * 
 */
package com.ibm.wam.model.util;

/**
 * @author jucelioj
 *
 */
public class WebException extends Exception {

    public WebException()
    {
        super();        
    }

    /**
     * @param message
     */
    public WebException(String message)
    {
        super(message);
         
    }

    /**
     * @param cause
     */
    public WebException(Throwable cause)
    {
        super(cause);
        
    }

    /**
     * @param message
     * @param cause
     */
    public WebException(String message, Throwable cause)
    {
        super(message, cause);
         
    }
}

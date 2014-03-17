package org.pentaho.gateway.api;

/**
 * Created by bryan on 3/16/14.
 */
public class HadoopGatewayException extends Exception {
    public HadoopGatewayException(String message) {
        super(message);
    }

    public HadoopGatewayException(Throwable cause) {
        super(cause);
    }

    public HadoopGatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}

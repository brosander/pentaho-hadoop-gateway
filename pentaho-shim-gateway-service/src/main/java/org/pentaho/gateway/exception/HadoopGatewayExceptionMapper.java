package org.pentaho.gateway.exception;

import org.pentaho.gateway.api.HadoopGatewayException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@SuppressWarnings("unused")
public class HadoopGatewayExceptionMapper implements ExceptionMapper<HadoopGatewayException> {
    @Override
    public Response toResponse(HadoopGatewayException exception) {
        return Response.status(500).entity(exception.getMessage()).type("text/plain").build();
    }
}

package org.pentaho.gateway.client.impl;

import org.pentaho.gateway.api.HdfsService;
import org.pentaho.gateway.client.ShimGatewayClient;

/**
 * Created by bryan on 3/17/14.
 */
public class ShimGatewayClientImpl implements ShimGatewayClient {
    @Override
    public HdfsService getHdfsService() {
        return new HdfsServiceClientImpl();
    }
}

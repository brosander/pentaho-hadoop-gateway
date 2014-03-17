package org.pentaho.gateway.api;

import org.pentaho.gateway.args.PathAndConfig;
import org.pentaho.gateway.args.RenameParameters;
import org.pentaho.gateway.args.SetModifiedTimeParameters;
import org.pentaho.gateway.args.WriteFileParameters;
import org.pentaho.gateway.ret.StringListWrapper;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

/**
 * Created by bryan on 3/15/14.
 */
public interface HdfsService {
    public void createFile(PathAndConfig pathAndConfig, InputStream inputStream) throws HadoopGatewayException;

    public void appendFile(PathAndConfig pathAndConfig, InputStream inputStream) throws HadoopGatewayException;

    public InputStream readFile(PathAndConfig pathAndConfig) throws HadoopGatewayException;

    public void delete(PathAndConfig pathAndConfig) throws HadoopGatewayException;

    public String getFileType(PathAndConfig pathAndConfig) throws HadoopGatewayException;

    public void createFolder(PathAndConfig pathAndConfig) throws HadoopGatewayException;

    public void rename(RenameParameters renameParameters) throws HadoopGatewayException;

    public long getLastModifiedTime(PathAndConfig pathAndConfig) throws HadoopGatewayException;

    public void setLastModifiedTime(SetModifiedTimeParameters setModifiedTimeParameters) throws HadoopGatewayException;

    public List<String> listChildren(PathAndConfig pathAndConfig) throws HadoopGatewayException;
}

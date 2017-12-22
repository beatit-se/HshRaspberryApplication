package se.beatit.hsh.raspberry.restclient;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:IoTHomeRestRoot [/iothome]<br>
 * USAGE:
 * <pre>
 *        IoTRestClient client = new IoTRestClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 *  @author Stefan Nilsson
 */
public class HshRestClient {
    private WebTarget webTarget;
    private Client client;

    
    
    public HshRestClient(final String baseUri) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(baseUri).path("iothome");
    }

    public String getHome(String home) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{home}));
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public String getHomes() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public String addUsage(String home, String kw) throws ClientErrorException {
        //WebTarget resource = webTarget;
        //resource = resource.path(java.text.MessageFormat.format("{0}/electricityuse/{1}", new Object[]{home, kw}));
        //return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
        return webTarget.path(java.text.MessageFormat.format("{0}/electricityuse/{1}", new Object[]{home, kw})).request().post(null, String.class);
    }

    public String createHome(String home) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{home})).request().post(null, String.class);
    }

    public String getStatus(String home, String device) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}/status", new Object[]{home, device}));
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public String setUsage(String home, String device, String usage) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("{0}/{1}/electricityuse/{2}", new Object[]{home, device, usage})).request().post(null, String.class);
    }

    public String setStatus(String home, String device, String status) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("{0}/{1}/status/{2}", new Object[]{home, device, status})).request().post(null, String.class);
    }

    public String getUsage(String home, String device) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}/electricityuse", new Object[]{home, device}));
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void close() {
        client.close();
    }
    
}

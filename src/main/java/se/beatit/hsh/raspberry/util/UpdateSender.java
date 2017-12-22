package se.beatit.hsh.raspberry.util;

import se.beatit.hsh.raspberry.restclient.HshRestClient;

/**
 *
 * @author Stefan Nilsson
 */
public class UpdateSender implements Runnable {
    private static long totalWhSent = 0;
    
    private final String home;
    //private final HshRestClient ioTRestClient;
    private final UpdateSenderFailCallback failSendBack;
    private final int maxSendRetries;
    private final int sendRetryDelay;
    
    private long whToSend;
    
    public UpdateSender(String home, String restBaseUri, 
            UpdateSenderFailCallback callback, int maxSendRetries, int sendRetryDelay) {
        this.home = home;
        this.failSendBack = callback;
        this.maxSendRetries = maxSendRetries;
        this.sendRetryDelay = sendRetryDelay;
        
        //ioTRestClient = new HshRestClient(restBaseUri);
    }
    
    public void sendUpdate(long wh) {
        this.whToSend = wh;
        UpdateSender.totalWhSent += wh;
        
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        for(int i=0; i<maxSendRetries; i++) {
            try {    
                //ioTRestClient.addUsage(home, String.valueOf(whToSend));
                Logger.log("IO Pin reported usage update: " + whToSend + 
                        "wh. Total since start:" + UpdateSender.totalWhSent + 
                        "wh (send retries: " + i + ")"); 
                break;
            } catch (Exception e) {
                if(i == maxSendRetries-1) {
                    Logger.log("Failed and stopped to try after " + 
                            maxSendRetries + " retries." + e.toString());
                    failSendBack.failedToSendUpdate(whToSend);
                } else {
                    Logger.log("Error ocurred while trying to send updated status to server. Retries ... " + e.toString());                
                    try {
                        Thread.sleep(sendRetryDelay);    
                    } catch(InterruptedException ie) {}      
                }
            }            
        }
        //ioTRestClient.close();
    }   
}

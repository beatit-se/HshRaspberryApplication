package se.beatit.hsh.raspberry.util;

/**
 *
 * @author Stefan Nilsson
 */
public interface UpdateSenderFailCallback {
    void failedToSendUpdate(long wh);
}

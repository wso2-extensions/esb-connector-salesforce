package org.wso2.carbon.connector.salesforce;

/**
 * Stores salesforce login data
 */
public class SessionInfo {

    String sessionId;
    String serviceUrl;
    int sessionCreatedTimeInSeconds;
    int sessionValidityInSeconds;

    public int getSessionCreatedTimeInSeconds() {
        return sessionCreatedTimeInSeconds;
    }

    public void setSessionCreatedTimeInSeconds(int sessionCreatedTimeInSeconds) {
        this.sessionCreatedTimeInSeconds = sessionCreatedTimeInSeconds;
    }

    public int getSessionValidityInSeconds() {
        return sessionValidityInSeconds;
    }

    public void setSessionValidityInSeconds(int sessionValidityInSeconds) {
        this.sessionValidityInSeconds = sessionValidityInSeconds;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}

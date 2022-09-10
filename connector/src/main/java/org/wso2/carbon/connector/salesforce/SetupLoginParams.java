/*
 *  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.connector.salesforce;

import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseException;
import org.apache.synapse.SynapseLog;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.util.ConnectorUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SetupLoginParams extends AbstractConnector {

    // Using a simple Map since there is no possibility of cache growth.
    // Cache key is LoginURL + UserName + Password
    private static Map<Integer, SessionInfo> cacheMap = new ConcurrentHashMap<>();
    private static final int sessionCheckBufferInSeconds = 120;

    public void connect(MessageContext messageContext) throws SynapseException {

        SynapseLog synLog = getLog(messageContext);

        try {
            Axis2MessageContext axis2smc = (Axis2MessageContext) messageContext;

            // Add the session info to the cache map
            if ("true".equals(axis2smc.getAxis2MessageContext().getOperationContext().getProperty(SalesforceUtil.SALESFORCE_LOGIN_ADD_TO_CACHE))) {

                String sessionId = (String) axis2smc.getAxis2MessageContext().getOperationContext().getProperty(SalesforceUtil.SALESFORCE_LOGIN_SESSION_ID);
                String sessionValidity = (String) axis2smc.getAxis2MessageContext().getOperationContext()
                        .getProperty(SalesforceUtil.SALESFORCE_LOGIN_SESSION_VALIDITY);

                // If we can't figure out the session validity no point in adding to the session store.
                if (sessionValidity != null && !sessionValidity.isEmpty()){
                    int sessionValidityInSeconds = Integer.parseInt((String) axis2smc.getAxis2MessageContext().getOperationContext()
                            .getProperty(SalesforceUtil.SALESFORCE_LOGIN_SESSION_VALIDITY));
                    String serviceUrl = (String) axis2smc.getAxis2MessageContext().getOperationContext().getProperty(SalesforceUtil.SALESFORCE_SERVICE_URL);

                    SessionInfo sessionInfo = new SessionInfo();
                    sessionInfo.setSessionId(sessionId);
                    sessionInfo.setServiceUrl(serviceUrl);
                    sessionInfo.setSessionValidityInSeconds(sessionValidityInSeconds);
                    sessionInfo.setSessionCreatedTimeInSeconds((int) (System.currentTimeMillis() / 1000));

                    cacheMap.put(getSfLoginHash(messageContext), sessionInfo);
                    return;
                }
            }

            // Set the force login
            String strValue = (String) ConnectorUtils.lookupTemplateParamater(messageContext,
                    SalesforceUtil.SALESFORCE_LOGIN_FORCE);
            // If force login is set we don't do anything
            if (strValue != null || "true".equals(strValue)) {
                // Setting Transport Headers
                System.out.println("Initializing the Session");
                axis2smc.getAxis2MessageContext().getOperationContext()
                        .setProperty(SalesforceUtil.SALESFORCE_LOGIN_DONE, "false");
            } else {
                synchronized (this) {
                    int hash = getSfLoginHash(messageContext);
                    SessionInfo sessionInfo = cacheMap.get(hash);
                    if (sessionInfo != null && isSessionValid(sessionInfo)) {
                        axis2smc.getAxis2MessageContext().getOperationContext().setProperty(SalesforceUtil.SALESFORCE_LOGIN_SESSION_ID, sessionInfo.sessionId);
                        axis2smc.getAxis2MessageContext().getOperationContext().setProperty(SalesforceUtil.SALESFORCE_SERVICE_URL, sessionInfo.getServiceUrl());
                        axis2smc.getAxis2MessageContext().getOperationContext()
                                .setProperty(SalesforceUtil.SALESFORCE_LOGIN_DONE, "true");
                    } else {
                        axis2smc.getAxis2MessageContext().getOperationContext()
                                .setProperty(SalesforceUtil.SALESFORCE_LOGIN_DONE, "false");
                    }
                }
            }
        } catch (Exception e) {
            synLog.error("Error occurred while setting logging params in SF connector " + e.getMessage());
            throw new SynapseException("Error occurred while setting logging params in SF connector " + e.getMessage());
        }
    }

    private int getSfLoginHash(MessageContext messageContext) {
        String username = (String) ConnectorUtils.lookupTemplateParamater(messageContext, SalesforceUtil.SALESFORCE_USER_NAME);
        String password = (String) ConnectorUtils.lookupTemplateParamater(messageContext, SalesforceUtil.SALESFORCE_USER_PASSWORD);
        String loginUrl = (String) ConnectorUtils.lookupTemplateParamater(messageContext, SalesforceUtil.SALESFORCE_LOGIN_URL);
        int hash = (loginUrl + username + password).hashCode();
        return hash;
    }

    private boolean isSessionValid(SessionInfo sessionInfo) {
        if ((sessionInfo.getSessionValidityInSeconds() - sessionCheckBufferInSeconds) < (System.currentTimeMillis() / 1000) - sessionInfo.getSessionCreatedTimeInSeconds()) {
            return false;
        }
        return true;
    }
}

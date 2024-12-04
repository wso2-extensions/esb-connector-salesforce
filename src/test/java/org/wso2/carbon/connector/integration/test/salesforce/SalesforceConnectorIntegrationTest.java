package org.wso2.carbon.connector.integration.test.salesforce;

/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;

import java.util.HashMap;
import java.util.Map;

public class SalesforceConnectorIntegrationTest extends ConnectorIntegrationTestBase {
    Map<String, String> nameSpaceMap = new HashMap<String, String>();
    private String apiEndPoint;

    /** artifacts/ESB/apiDescribeGlobalMandatory.xml
     * Set up the environment.
     */
    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        String connectorName = System.getProperty("connector_name") + "-connector-" +
                System.getProperty("connector_version") + ".zip";
        addCertificatesToEIKeyStore("client-truststore.jks", "wso2carbon");
        init(connectorName);
        getApiConfigProperties();
        apiEndPoint = connectorProperties.getProperty("loginUrl");
        nameSpaceMap.put("ns", "urn:partner.soap.sforce.com");
        nameSpaceMap.put("ns1", "urn:sobject.partner.soap.sforce.com");
    }

    /**
     *
     * Positive test case for getUserInfo method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "Salesforce {getUserInfo} integration test with mandatory parameters.")
    public void testGetUserInfoWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbGetUserInfoMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        String xPathExp = "string(//ns:userEmail)";
        String esbUserEmail = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);
        xPathExp = "string(//ns:userId)";
        String esbUserId = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        String apiSoapResponse = sendSOAPRequestWithApacheHTTPClient(apiEndPoint, "apiLogin.xml", null, "login");
        OMElement apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse);
        xPathExp = "string(//ns:sessionId)";
        String session = (String) xPathEvaluate(apiResponseElement, xPathExp, nameSpaceMap);
        connectorProperties.put("session", session);
        xPathExp = "string(//ns:serverUrl)";
        String serverUrl = (String) xPathEvaluate(apiResponseElement, xPathExp, nameSpaceMap);
        connectorProperties.put("serverUrl", serverUrl);

        apiSoapResponse = sendSOAPRequestWithApacheHTTPClient(serverUrl, "apiGetUserInfoMandatory.xml", null, "getUserInfo");
        apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse);
        xPathExp = "string(//ns:userEmail)";
        String apiUserEmail = (String) xPathEvaluate(apiResponseElement, xPathExp, nameSpaceMap);
        xPathExp = "string(//ns:userId)";
        String apiUserId = (String) xPathEvaluate(apiResponseElement, xPathExp, nameSpaceMap);

        Assert.assertEquals(esbUserEmail, apiUserEmail);
        Assert.assertEquals(esbUserId, apiUserId);
    }


    /**
     *
     * Positive test case for describeSObject method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {describeSObject} integration test with mandatory parameters.")
    public void testDescribeSObjectWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbDescribeSObjectMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        String xPathExp = "string(//ns:childRelationships)";
        String esbChildRelationship = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        String apiSoapResponse = sendSOAPRequestWithApacheHTTPClient(connectorProperties.getProperty("serverUrl"), "apiDescribeSObjectMandatory.xml", null, "describeSObject");
        OMElement apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse);
        xPathExp = "string(//ns:childRelationships)";
        String apiChildRelationship = (String) xPathEvaluate(apiResponseElement, xPathExp, nameSpaceMap);

        Assert.assertEquals(esbChildRelationship, apiChildRelationship);
    }

    /**
     *
     * Positive test case for describeGlobal method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {describeGlobal} integration test with mandatory parameters.")
    public void testDescribeGlobalWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbDescribeGlobalMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        String xPathExp = "string(//ns:sobjects)";
        String esbSObjects = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        String apiSoapResponse = sendSOAPRequestWithApacheHTTPClient(connectorProperties.getProperty("serverUrl"), "apiDescribeGlobalMandatory.xml", null, "describeGlobal");
        OMElement apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse);
        xPathExp = "string(//ns:sobjects)";
        String apiSObjects = (String) xPathEvaluate(apiResponseElement, xPathExp, nameSpaceMap);

        Assert.assertEquals(esbSObjects, apiSObjects);
    }

    /**
     *
     * Positive test case for describeSObjects method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {describeSObjects} integration test with mandatory parameters.")
    public void testDescribeSObjectsWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbDescribeSObjectsMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        String xPathExp = "string(//ns:sobjects)";
        String esbSObjects = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        String apiSoapResponse = sendSOAPRequestWithApacheHTTPClient(connectorProperties.getProperty("serverUrl"), "apiDescribeSObjectsMandatory.xml", null, "describeSObjects");
        OMElement apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse);
        xPathExp = "string(//ns:sobjects)";
        String apiSObjects = (String) xPathEvaluate(apiResponseElement, xPathExp, nameSpaceMap);

        Assert.assertEquals(esbSObjects, apiSObjects);
    }

    /**
     *
     * Positive test case for query method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {query} integration test with mandatory parameters.")
    public void testQueryWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbQueryMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        String xPathExp = "string(//ns:records)";
        String esbRes = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        String apiSoapResponse = sendSOAPRequestWithApacheHTTPClient(connectorProperties.getProperty("serverUrl"), "apiQueryMandatory.xml", null, "query");
        OMElement apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse);
        xPathExp = "string(//ns:records)";
        String apiRes = (String) xPathEvaluate(apiResponseElement, xPathExp, nameSpaceMap);

        Assert.assertEquals(esbRes, apiRes);
    }

    /**
     *
     * Positive test case for search method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {search} integration test with mandatory parameters.")
    public void testSearchWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbSearchMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        String xPathExp = "string(//ns:searchRecords)";
        String esbRes = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        String apiSoapResponse = sendSOAPRequestWithApacheHTTPClient(connectorProperties.getProperty("serverUrl"), "apiSearchMandatory.xml", null, "search");
        OMElement apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse);
        xPathExp = "string(//ns:searchRecords)";
        String apiRes = (String) xPathEvaluate(apiResponseElement, xPathExp, nameSpaceMap);

        Assert.assertEquals(esbRes, apiRes);
    }

    /**
     *
     * Positive test case for retrieve method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testUnDeleteWithMandatoryParameters"}, description = "Salesforce {retrieve} integration test with mandatory parameters.")
    public void testRetrieveWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbRetrieveMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        String xPathExp = "string(//ns1:Id)";
        String esbRes = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        Assert.assertEquals(esbRes, connectorProperties.getProperty("id"));
        Assert.assertTrue(esbResponseElement.toString().contains("retrieveResponse"));
    }

    /**
     *
     * Positive test case for create method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "Salesforce {create} integration test with mandatory parameters.")
    public void testCreateWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbCreateMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        String xPathExp = "string(//ns:id)";
        String id = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);
        connectorProperties.put("id", id);
        xPathExp = "string(//ns:success)";
        String status = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        Assert.assertEquals(status, "true");
        Assert.assertTrue(esbResponseElement.toString().contains("createResponse"));
    }

    /**
     *
     * Positive test case for update method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testCreateWithMandatoryParameters"}, description = "Salesforce {update} integration test with mandatory parameters.")
    public void testUpdateWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbUpdateMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        String xPathExp = "string(//ns:success)";
        String status = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        Assert.assertEquals(status, "true");
        Assert.assertTrue(esbResponseElement.toString().contains("updateResponse"));
    }

    /**
     *
     * Positive test case for delete method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testUpdateWithMandatoryParameters"}, description = "Salesforce {delete} integration test with mandatory parameters.")
    public void testDeleteWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbDeleteMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        String xPathExp = "string(//ns:success)";
        String status = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        Assert.assertEquals(status, "true");
        Assert.assertTrue(esbResponseElement.toString().contains("deleteResponse"));
    }

    /**
     *
     * Positive test case for UnDelete method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testDeleteWithMandatoryParameters"}, description = "Salesforce {UnDelete} integration test with mandatory parameters.")
    public void testUnDeleteWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbUnDeleteMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        String xPathExp = "string(//ns:success)";
        String status = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        Assert.assertEquals(status, "true");
        Assert.assertTrue(esbResponseElement.toString().contains("undeleteResponse"));
    }

    /**
     *
     * Positive test case for sendEmail method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "Salesforce {sendEmail} integration test with mandatory parameters.")
    public void testSendEMailWithMandatoryParameters() throws Exception {
        String esbContactSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbCreateContact.xml", null, "mediate");
        OMElement esbCreateContactResponseElement = AXIOMUtil.stringToOM(esbContactSoapResponse);
        String xPathExpContact = "string(//ns:id)";
        String id = (String) xPathEvaluate(esbCreateContactResponseElement, xPathExpContact, nameSpaceMap);
        connectorProperties.put("targetObjectId", id);

        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbSendEmailMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        String xPathExp = "string(//ns:success)";
        String status = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        connectorProperties.put("id", id);
        String esbDeleteContactSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbDeleteMandatory.xml", null, "mediate");

        Assert.assertTrue(esbResponseElement.toString().contains("sendEmailResponse"));
        Assert.assertEquals(status, "true");
    }

    /**
     *
     * Positive test case for logout method with mandatory parameters.
     */
    @Test(enabled = false, priority = 1, groups = {"wso2.esb"}, description = "Salesforce {logout} integration test with mandatory parameters.")
    public void testLogoutWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbLogoutMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);

        Assert.assertTrue(esbResponseElement.toString().contains("logoutResponse"));
    }

    /**
     *
     * Positive test case for getDeleted method with mandatory parameters.
     */
    @Test(enabled = true, priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {getDeleted} integration test with mandatory parameters.")
    public void testGetDeletedWithMandatoryParameters() throws Exception {
        connectorProperties.put("startDate", "2020-07-15T05:05:53+0000"); //Change startDate and endDate to a value within the last 30 days
        connectorProperties.put("endDate", "2020-07-22T05:05:53+0000");
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbGetDeletedMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        Assert.assertTrue(esbResponseElement.toString().contains("getDeletedResponse"));
    }

    /**
     *
     * Positive test case for getUpdated method with mandatory parameters.
     */
    @Test(enabled = true, priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {getDeleted} integration test with mandatory parameters.")
    public void testGetUpdatedWithMandatoryParameters() throws Exception {
        connectorProperties.put("startDate", "2020-07-15T05:05:53+0000"); //Change startDate and endDate to a value within the last 30 days
        connectorProperties.put("endDate", "2020-07-22T05:05:53+0000");
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbGetUpdatedMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        Assert.assertTrue(esbResponseElement.toString().contains("getUpdatedResponse"));
    }

    /**
     *
     * Positive test case for getServerTimestamp method with mandatory parameters.
     */
    @Test(enabled = true, priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {getServerTimestamp} integration test with mandatory parameters.")
    public void testGetServerTimestampWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbGetServerTimestampMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        Assert.assertTrue(esbResponseElement.toString().contains("getServerTimestampResponse"));
    }

    /**
     *
     * Positive test case for findDuplicates method with mandatory parameters.
     */
    @Test(enabled = true, priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {findDuplicates} integration test with mandatory parameters.")
    public void testFindDuplicatesWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbFindDuplicatesMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        Assert.assertTrue(esbResponseElement.toString().contains("findDuplicatesResponse"));
    }

    /**
     *
     * Positive test case for findDuplicatesByIds method with mandatory parameters.
     */
    @Test(enabled = true, priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {findDuplicatesByIds} integration test with mandatory parameters.")
    public void testFindDuplicatesByIdsWithMandatoryParameters() throws Exception {
        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbFindDuplicatesByIdsMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        Assert.assertTrue(esbResponseElement.toString().contains("findDuplicatesByIdsResponse"));
    }

    /**
     *
     * Positive test case for merge method with mandatory parameters.
     */
    @Test(enabled = true, priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {merge} integration test with mandatory parameters.")
    public void testMergeWithMandatoryParameters() throws Exception {
        String esbSoapResponseCreate = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbCreateMandatory.xml", null, "mediate");
        OMElement esbResponseElementCreate = AXIOMUtil.stringToOM(esbSoapResponseCreate);
        String xPathExp = "string(//ns:id)";
        String id = (String) xPathEvaluate(esbResponseElementCreate, xPathExp, nameSpaceMap);
        connectorProperties.put("idForMerge1", id);

        String esbSoapResponseCreate2 = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbCreateMandatory.xml", null, "mediate");
        OMElement esbResponseElementCreate2 = AXIOMUtil.stringToOM(esbSoapResponseCreate2);
        String id2 = (String) xPathEvaluate(esbResponseElementCreate2, xPathExp, nameSpaceMap);
        connectorProperties.put("idForMerge2", id2);

        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbMergeMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        Assert.assertTrue(esbResponseElement.toString().contains("mergeResponse"));
        String xPathExpSuccess = "string(//ns:success)";
        String status = (String) xPathEvaluate(esbResponseElement, xPathExpSuccess, nameSpaceMap);
        Assert.assertEquals(status, "true");
    }

    /**
     *
     * Positive test case for convertLead method with mandatory parameters.
     */
    @Test(enabled = true, priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {convertLead} integration test with mandatory parameters.")
    public void testConvertLeadWithMandatoryParameters() throws Exception {
        String esbSoapResponseCreate = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbCreateMandatory.xml", null, "mediate");
        OMElement esbResponseElementCreate = AXIOMUtil.stringToOM(esbSoapResponseCreate);
        String xPathExp = "string(//ns:id)";
        String id = (String) xPathEvaluate(esbResponseElementCreate, xPathExp, nameSpaceMap);
        connectorProperties.put("idForConvert1", id);

        String esbSoapResponseCreate2 = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbCreateLeadMandatory.xml", null, "mediate");
        OMElement esbResponseElementCreate2 = AXIOMUtil.stringToOM(esbSoapResponseCreate2);
        String id2 = (String) xPathEvaluate(esbResponseElementCreate2, xPathExp, nameSpaceMap);
        connectorProperties.put("idForConvert2", id2);

        String esbSoapResponse = sendSOAPRequestWithApacheHTTPClient(proxyUrl, "esbConvertLeadMandatory.xml", null, "mediate");
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse);
        Assert.assertTrue(esbResponseElement.toString().contains("convertLeadResponse"));
        String xPathExpSuccess = "string(//ns:success)";
        String status = (String) xPathEvaluate(esbResponseElement, xPathExpSuccess, nameSpaceMap);
        Assert.assertEquals(status, "true");
    }
}

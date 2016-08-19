package org.wso2.carbon.connector.integration.test.salesforce;

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

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axiom.soap.SOAPEnvelope;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;

import java.util.HashMap;
import java.util.Map;

public class SalesforceConnectorIntegrationTest extends ConnectorIntegrationTestBase {

    private final String SOAP_HEADER_XPATH_EXP = "/soapenv:Envelope/soapenv:Header/*";
    private final String SOAP_BODY_XPATH_EXP = "/soapenv:Envelope/soapenv:Body/*";
    Map<String, String> nameSpaceMap = new HashMap<String, String>();
    private String apiEndPoint;

    /**
     * Set up the environment.
     */
    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        init("salesforce-connector-1.0.2-SNAPSHOT");
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
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbGetUserInfoMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());
        String xPathExp = "string(//ns:userEmail)";
        String esbUserEmail = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);
        xPathExp = "string(//ns:userId)";
        String esbUserId = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        SOAPEnvelope apiSoapResponse = sendSOAPRequest(apiEndPoint, "apiLogin.xml", null, "login",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse.getBody().toString());
        xPathExp = "string(//ns:sessionId)";
        String session = (String) xPathEvaluate(apiResponseElement, xPathExp, nameSpaceMap);
        connectorProperties.put("session", session);
        xPathExp = "string(//ns:serverUrl)";
        String serverUrl = (String) xPathEvaluate(apiResponseElement, xPathExp, nameSpaceMap);
        connectorProperties.put("serverUrl", serverUrl);

        apiSoapResponse = sendSOAPRequest(serverUrl, "apiGetUserInfoMandatory.xml", null, "getUserInfo",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse.getBody().toString());
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
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbDescribeSObjectMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());
        String xPathExp = "string(//ns:childRelationships)";
        String esbChildRelationship = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        SOAPEnvelope apiSoapResponse = sendSOAPRequest(connectorProperties.getProperty("serverUrl"), "apiDescribeSObjectMandatory.xml", null, "describeSObject",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse.getBody().toString());
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
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbDescribeGlobalMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());
        String xPathExp = "string(//ns:sobjects)";
        String esbSObjects = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        SOAPEnvelope apiSoapResponse = sendSOAPRequest(connectorProperties.getProperty("serverUrl"), "apiDescribeGlobalMandatory.xml", null, "describeGlobal",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse.getBody().toString());
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
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbDescribeSObjectsMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());
        String xPathExp = "string(//ns:sobjects)";
        String esbSObjects = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        SOAPEnvelope apiSoapResponse = sendSOAPRequest(connectorProperties.getProperty("serverUrl"), "apiDescribeSObjectsMandatory.xml", null, "describeSObjects",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse.getBody().toString());
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
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbQueryMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());
        String xPathExp = "string(//ns:result)";
        String esbRes = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        SOAPEnvelope apiSoapResponse = sendSOAPRequest(connectorProperties.getProperty("serverUrl"), "apiQueryMandatory.xml", null, "query",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse.getBody().toString());
        xPathExp = "string(//ns:result)";
        String apiRes = (String) xPathEvaluate(apiResponseElement, xPathExp, nameSpaceMap);

        Assert.assertEquals(esbRes, apiRes);
    }

    /**
     *
     * Positive test case for search method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, dependsOnMethods = {"testGetUserInfoWithMandatoryParameters"}, description = "Salesforce {search} integration test with mandatory parameters.")
    public void testSearchWithMandatoryParameters() throws Exception {
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbSearchMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());
        String xPathExp = "string(//ns:searchRecords)";
        String esbRes = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        SOAPEnvelope apiSoapResponse = sendSOAPRequest(connectorProperties.getProperty("serverUrl"), "apiSearchMandatory.xml", null, "search",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement apiResponseElement = AXIOMUtil.stringToOM(apiSoapResponse.getBody().toString());
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
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbRetrieveMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());
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
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbCreateMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());
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
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbUpdateMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());
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
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbDeleteMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());
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
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbUnDeleteMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());
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
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbSendEmailMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());
        String xPathExp = "string(//ns:success)";
        String status = (String) xPathEvaluate(esbResponseElement, xPathExp, nameSpaceMap);

        Assert.assertTrue(esbResponseElement.toString().contains("sendEmailResponse"));
        Assert.assertEquals(status, "true");
    }

    /**
     *
     * Positive test case for logout method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "Salesforce {logout} integration test with mandatory parameters.")
    public void testLogoutWithMandatoryParameters() throws Exception {
        SOAPEnvelope esbSoapResponse = sendSOAPRequest(proxyUrl, "esbLogoutMandatory.xml", null, "mediate",
                SOAP_HEADER_XPATH_EXP, SOAP_BODY_XPATH_EXP);
        OMElement esbResponseElement = AXIOMUtil.stringToOM(esbSoapResponse.getBody().toString());

        Assert.assertTrue(esbResponseElement.toString().contains("logoutResponse"));
    }
}


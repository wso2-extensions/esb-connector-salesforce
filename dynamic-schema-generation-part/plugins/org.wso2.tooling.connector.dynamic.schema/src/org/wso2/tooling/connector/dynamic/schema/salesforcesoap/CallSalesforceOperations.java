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
package org.wso2.tooling.connector.dynamic.schema.salesforcesoap;

import java.io.StringWriter;
import java.util.List;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.NodeList;

public class CallSalesforceOperations {
	private static String serverUrl;
	private static String sessionId;
	private static String metadataserverUrl;

	private static CallSalesforceOperations callSalesforce = new CallSalesforceOperations();

	public static CallSalesforceOperations getInstance() {
		return callSalesforce;
	}

	public String getServerURL() {
		return serverUrl;
	}

	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Login to Salesforce to get sessionId, serverUrl, metadataUrl
	 * 
	 * @throws Exception
	 */
	protected void login() throws Exception {
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		String url = LoginForm.getInstance().getLoginURL();

		// Create a Soap Message
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		// This is the namespace URI.
		String serverURI = "urn:partner.soap.sforce.com";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("urn", serverURI);

		// SOAP Body
		SOAPBody buildSoapBody = envelope.getBody();
		SOAPElement soapBodyElem = buildSoapBody.addChildElement("login", "urn");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("username", "urn");
		soapBodyElem1.addTextNode(LoginForm.getInstance().getUserName());
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("password", "urn");
		soapBodyElem2.addTextNode(LoginForm.getInstance().getPassword() + LoginForm.getInstance().getSecurityToken());

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI + "login");
		soapMessage.saveChanges();

		// Request to soapResponse
		SOAPMessage soapResponse = soapConnection.call(soapMessage, url);
		soapConnection.close();
		SOAPPart soapBody = soapResponse.getSOAPPart();

		NodeList tagSession = soapBody.getElementsByTagName("sessionId");
		sessionId = tagSession.item(0).getFirstChild().getNodeValue();
		NodeList tagServerUrl = soapBody.getElementsByTagName("serverUrl");
		serverUrl = tagServerUrl.item(0).getFirstChild().getNodeValue();
		NodeList tagMetadataServerUrl = soapBody.getElementsByTagName("metadataServerUrl");
		metadataserverUrl = tagMetadataServerUrl.item(0).getFirstChild().getNodeValue();
	}

	/**
	 * Call Salesforce metadata to get All sObject of the Organization
	 * 
	 * @return sObjects
	 * @throws Exception
	 */
	public static String[] callMetaData() throws Exception {
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		String url = metadataserverUrl;

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = "http://soap.sforce.com/2006/04/metadata";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("met", serverURI);

		// SOAP Header
		SOAPHeader buildSoapHeader = envelope.getHeader();
		SOAPElement soapHeaderElem = buildSoapHeader.addChildElement("SessionHeader", "met");
		SOAPElement soapHeaderElem1 = soapHeaderElem.addChildElement("sessionId", "met");
		soapHeaderElem1.addTextNode(sessionId);

		// SOAP Body
		SOAPBody buildSoapBody = envelope.getBody();
		SOAPElement soapBodyElem = buildSoapBody.addChildElement("listMetadata", "met");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("queries", "met");
		SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("type", "met");
		soapBodyElem2.addTextNode("CustomObject");

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI + "listMetadata");

		soapMessage.saveChanges();

		SOAPMessage soapResponse = soapConnection.call(soapMessage, url);
		soapConnection.close();
		SOAPPart soapBody = soapResponse.getSOAPPart();
		DOMSource source = new DOMSource(soapBody);
		StringWriter stringResult = new StringWriter();
		TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));
		String response = stringResult.toString();

		List<String> output = GetTagValueFromXML.getTagValueFromXml(response, "fullName");
		String[] strarray = new String[output.size()];

		return output.toArray(strarray);
	}
}

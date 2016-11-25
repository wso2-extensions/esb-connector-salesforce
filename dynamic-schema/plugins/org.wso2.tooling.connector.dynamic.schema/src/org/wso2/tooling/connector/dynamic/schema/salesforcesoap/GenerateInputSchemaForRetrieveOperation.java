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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.NodeList;

public class GenerateInputSchemaForRetrieveOperation extends Dialog {

	private Group grpPropertyKey;
	private Label lblConnectorSalesforceLoginUserName;
	private Label lblConnectorLoginSalesforcePassword;
	private Label lblConnectorLoginSalesforceSecurityToken;
	private Label lblConnectorLoginSalesforceLoginURL;
	private Label lblSObject;
	private Label lblRetrieve;
	private static Text connectorLoginSalesforceUserNameTextField;
	private static Text connectorLoginSalesforcePasswordTextField;
	private static Text connectorLoginSalesforceSecurityTokenTextField;
	private static Text connectorLoginSalesforceLoginURLTextField;
	private static Text retrieveTextField;
	private Button login;
	private static Combo cmbSObjectType;
	private String value;
	private static String idValue;

	private static final String SELECT_CONNECTOR_LOGIN_USERNAME = Messages.SchemaKeyEditorDialog_SelectConnectorLoginUsername;
	private static final String SELECT_CONNECTOR_LOGIN_PASSWORD = Messages.SchemaKeyEditorDialog_SelectConnectorLoginPassword;
	private static final String SELECT_CONNECTOR_LOGIN_SECURITY_TOKEN = Messages.SchemaKeyEditorDialog_SelectConnectorLoginSecurityToken;
	private static final String SELECT_CONNECTOR_LOGIN_LOGIN_URL = Messages.SchemaKeyEditorDialog_SelectConnectorLoginLoginURL;
	private static final String SELECT_SALESFORCE_LOGIN = Messages.SchemaKeyEditorDialog_SelectConnectorLogin;
	private static final String SELECT_SALESFORCE_SOBJECT = Messages.SchemaKeyEditorDialog_SelectSObject;
	private static final String SELECT_SALESFORCE_RETRIEVE = Messages.SchemaKeyEditorDialog_Retrieve;

	public GenerateInputSchemaForRetrieveOperation(Shell parent) {
		super(parent);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		FillLayout fl_container = new FillLayout(SWT.HORIZONTAL);
		fl_container.marginHeight = 5;
		fl_container.marginWidth = 5;
		fl_container.spacing = 10;
		container.setLayout(fl_container);

		grpPropertyKey = new Group(container, SWT.None);

		FormLayout fl_grpPropertyKey = new FormLayout();
		fl_grpPropertyKey.marginHeight = 10;
		fl_grpPropertyKey.marginWidth = 10;
		grpPropertyKey.setLayout(fl_grpPropertyKey);

		lblConnectorSalesforceLoginUserName = new Label(grpPropertyKey, SWT.NORMAL);
		lblConnectorLoginSalesforcePassword = new Label(grpPropertyKey, SWT.NORMAL);
		lblConnectorLoginSalesforceSecurityToken = new Label(grpPropertyKey, SWT.NORMAL);
		lblConnectorLoginSalesforceLoginURL = new Label(grpPropertyKey, SWT.NORMAL);
		lblSObject = new Label(grpPropertyKey, SWT.NORMAL);
		lblRetrieve = new Label(grpPropertyKey, SWT.NORMAL);

		connectorLoginSalesforceUserNameTextField = new Text(grpPropertyKey, SWT.BORDER);
		connectorLoginSalesforcePasswordTextField = new Text(grpPropertyKey, SWT.BORDER | SWT.PASSWORD);
		connectorLoginSalesforceSecurityTokenTextField = new Text(grpPropertyKey, SWT.BORDER | SWT.PASSWORD);
		connectorLoginSalesforceLoginURLTextField = new Text(grpPropertyKey, SWT.BORDER);
		retrieveTextField = new Text(grpPropertyKey, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);

		cmbSObjectType = new Combo(grpPropertyKey, SWT.DROP_DOWN | SWT.READ_ONLY | SWT.BORDER);

		login = new Button(grpPropertyKey, SWT.PUSH);

		if (LoginForm.userName != null && LoginForm.password != null && LoginForm.securityToken != null
				&& LoginForm.loginUrl != null) {
			GenerateInputSchemaForRetrieveOperation.connectorLoginSalesforceUserNameTextField
					.setText(LoginForm.getInstance().getUserName());
			GenerateInputSchemaForRetrieveOperation.connectorLoginSalesforcePasswordTextField
					.setText(LoginForm.getInstance().getPassword());
			GenerateInputSchemaForRetrieveOperation.connectorLoginSalesforceSecurityTokenTextField
					.setText(LoginForm.getInstance().getSecurityToken());
			GenerateInputSchemaForRetrieveOperation.connectorLoginSalesforceLoginURLTextField
					.setText(LoginForm.getInstance().getLoginURL());
		}

		FormData salesforceLoginUserNameLabelLayoutData = new FormData();
		lblConnectorSalesforceLoginUserName.setText(SELECT_CONNECTOR_LOGIN_USERNAME);
		lblConnectorSalesforceLoginUserName.setLayoutData(salesforceLoginUserNameLabelLayoutData);

		FormData salesforceLoginUserNameTextFieldLayoutData = new FormData();
		salesforceLoginUserNameTextFieldLayoutData.left = new FormAttachment(lblConnectorSalesforceLoginUserName, 10,
				SWT.RIGHT);
		salesforceLoginUserNameTextFieldLayoutData.right = new FormAttachment(100, -5);
		connectorLoginSalesforceUserNameTextField.setLayoutData(salesforceLoginUserNameTextFieldLayoutData);

		FormData salesforceLoginPasswordLabelLayoutData = new FormData();
		salesforceLoginPasswordLabelLayoutData.top = new FormAttachment(lblConnectorSalesforceLoginUserName, 20,
				SWT.BOTTOM);
		lblConnectorLoginSalesforcePassword.setText(SELECT_CONNECTOR_LOGIN_PASSWORD);
		lblConnectorLoginSalesforcePassword.setLayoutData(salesforceLoginPasswordLabelLayoutData);

		FormData salesforceLoginPasswordTextFieldLayoutData = new FormData();
		salesforceLoginPasswordTextFieldLayoutData.top = new FormAttachment(connectorLoginSalesforceUserNameTextField,
				10, SWT.BOTTOM);
		salesforceLoginPasswordTextFieldLayoutData.left = new FormAttachment(lblConnectorLoginSalesforcePassword, 10,
				SWT.RIGHT);
		salesforceLoginPasswordTextFieldLayoutData.right = new FormAttachment(100, -5);
		connectorLoginSalesforcePasswordTextField.setLayoutData(salesforceLoginPasswordTextFieldLayoutData);

		FormData salesforceLoginSecurityTokenLabelLayoutData = new FormData();
		salesforceLoginSecurityTokenLabelLayoutData.top = new FormAttachment(lblConnectorLoginSalesforcePassword, 20,
				SWT.BOTTOM);
		lblConnectorLoginSalesforceSecurityToken.setText(SELECT_CONNECTOR_LOGIN_SECURITY_TOKEN);
		lblConnectorLoginSalesforceSecurityToken.setLayoutData(salesforceLoginSecurityTokenLabelLayoutData);

		FormData salesforceLoginSecurityTokenTextFieldLayoutData = new FormData();
		salesforceLoginSecurityTokenTextFieldLayoutData.top = new FormAttachment(
				connectorLoginSalesforcePasswordTextField, 12, SWT.BOTTOM);
		salesforceLoginSecurityTokenTextFieldLayoutData.left = new FormAttachment(
				lblConnectorLoginSalesforceSecurityToken, 10, SWT.RIGHT);
		salesforceLoginSecurityTokenTextFieldLayoutData.right = new FormAttachment(100, -5);
		connectorLoginSalesforceSecurityTokenTextField.setLayoutData(salesforceLoginSecurityTokenTextFieldLayoutData);

		FormData salesforceLoginLoginURLLabelLayoutData = new FormData();
		salesforceLoginLoginURLLabelLayoutData.top = new FormAttachment(lblConnectorLoginSalesforceSecurityToken, 20,
				SWT.BOTTOM);
		lblConnectorLoginSalesforceLoginURL.setText(SELECT_CONNECTOR_LOGIN_LOGIN_URL);
		lblConnectorLoginSalesforceLoginURL.setLayoutData(salesforceLoginLoginURLLabelLayoutData);

		FormData salesforceLoginLoginURLTextFieldLayoutData = new FormData();
		salesforceLoginLoginURLTextFieldLayoutData.top = new FormAttachment(
				connectorLoginSalesforceSecurityTokenTextField, 12, SWT.BOTTOM);
		salesforceLoginLoginURLTextFieldLayoutData.left = new FormAttachment(lblConnectorLoginSalesforceLoginURL, 10,
				SWT.RIGHT);
		salesforceLoginLoginURLTextFieldLayoutData.right = new FormAttachment(100, -5);
		connectorLoginSalesforceLoginURLTextField.setLayoutData(salesforceLoginLoginURLTextFieldLayoutData);

		FormData loginButtonLayoutData = new FormData();
		loginButtonLayoutData.top = new FormAttachment(connectorLoginSalesforceLoginURLTextField, 10, SWT.BOTTOM);
		loginButtonLayoutData.left = new FormAttachment(50, 10);
		loginButtonLayoutData.right = new FormAttachment(100, -5);
		login.setLayoutData(loginButtonLayoutData);
		login.setText(SELECT_SALESFORCE_LOGIN);

		login.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try {
					LoginForm.getInstance().setUserName(
							GenerateInputSchemaForRetrieveOperation.connectorLoginSalesforceUserNameTextField
									.getText());
					LoginForm.getInstance().setPassword(
							GenerateInputSchemaForRetrieveOperation.connectorLoginSalesforcePasswordTextField
									.getText());
					LoginForm.getInstance().setSecurityToken(
							GenerateInputSchemaForRetrieveOperation.connectorLoginSalesforceSecurityTokenTextField
									.getText());
					LoginForm.getInstance().setLoginURL(
							GenerateInputSchemaForRetrieveOperation.connectorLoginSalesforceLoginURLTextField
									.getText());
					CallSalesforceOperations.getInstance().login();
					String[] sObject = CallSalesforceOperations.callMetaData();
					cmbSObjectType.setItems(sObject);
					cmbSObjectType.select(0);
				} catch (Exception e) {
					MessageDialog.openWarning(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
							"Error In Login to Salesforce", "Check the Login Credentials and Try Again");
				}
			}
		});

		FormData sObjectLabelLayoutData = new FormData();
		sObjectLabelLayoutData.top = new FormAttachment(login, 20, SWT.BOTTOM);
		lblSObject.setText(SELECT_SALESFORCE_SOBJECT);
		lblSObject.setLayoutData(sObjectLabelLayoutData);

		FormData sObjectComboLayoutData = new FormData();
		sObjectComboLayoutData.top = new FormAttachment(login, 15, SWT.BOTTOM);
		sObjectComboLayoutData.left = new FormAttachment(lblSObject, 10, SWT.RIGHT);
		sObjectComboLayoutData.right = new FormAttachment(100, -5);
		cmbSObjectType.setLayoutData(sObjectComboLayoutData);

		cmbSObjectType.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				try {
					retrieveTextField.setText(callDescribeSObject());
				} catch (Exception e) {
					MessageDialog.openWarning(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
							"Error While Retrieve", "Check the retrieve ID");
				}
			}
		});

		FormData retrieveLabelLayoutData = new FormData();
		retrieveLabelLayoutData.top = new FormAttachment(lblSObject, 20, SWT.BOTTOM);
		lblRetrieve.setText(SELECT_SALESFORCE_RETRIEVE);
		lblRetrieve.setLayoutData(retrieveLabelLayoutData);

		FormData retrieveTextFieldLayoutData = new FormData();
		retrieveTextFieldLayoutData.top = new FormAttachment(lblRetrieve, 15, SWT.BOTTOM);
		retrieveTextFieldLayoutData.left = new FormAttachment(0, 5);
		retrieveTextFieldLayoutData.right = new FormAttachment(100, -5);
		retrieveTextField.setLayoutData(retrieveTextFieldLayoutData);
		retrieveTextFieldLayoutData.height = 125;

		return container;
	}

	/**
	 * Call the Salesforce SOAP api describeSobject method to retrieve All
	 * fields of the particular sObject.
	 * 
	 * @return All Fields.
	 * @throws Exception
	 */
	public static String callDescribeSObject() throws Exception {
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		String url = CallSalesforceOperations.getInstance().getServerURL();

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = "urn:partner.soap.sforce.com";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("urn", serverURI);

		// SOAP Header
		SOAPHeader buildSoapHeader = envelope.getHeader();
		SOAPElement soapHeaderElem = buildSoapHeader.addChildElement("SessionHeader", "urn");
		SOAPElement soapHeaderElem1 = soapHeaderElem.addChildElement("sessionId", "urn");
		soapHeaderElem1.addTextNode(CallSalesforceOperations.getInstance().getSessionId());

		// SOAP Body
		SOAPBody buildSoapBody = envelope.getBody();
		SOAPElement soapBodyElem = buildSoapBody.addChildElement("describeSObject", "urn");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("sObjectType", "urn");
		soapBodyElem1.addTextNode(cmbSObjectType.getText());

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI + "describeSObject");

		soapMessage.saveChanges();

		SOAPMessage soapResponse = soapConnection.call(soapMessage, url);
		soapConnection.close();
		SOAPPart soapBody = soapResponse.getSOAPPart();
		DOMSource source = new DOMSource(soapBody);
		StringWriter stringResult = new StringWriter();
		TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));
		String response = stringResult.toString();

		List<String> output = GetTagValueFromXML.getInnerTagFromXml(response, "fields");
		String[] out = output.toArray(new String[output.size()]);

		if (out.length > 0) {
			StringBuilder nameBuilder = new StringBuilder();

			for (String name : out) {
				nameBuilder.append(name);
				nameBuilder.append(",");
			}

			nameBuilder.deleteCharAt(nameBuilder.length() - 1);
			return nameBuilder.toString();
		} else {
			return "";
		}
	}

	/**
	 * Call the Salesforce SOAP api query method to retrieve a value of an Id
	 * field to call the callRetrieve() method.
	 * 
	 * @return Id.
	 * @throws Exception
	 */
	public static String getId() throws Exception {
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		String url = CallSalesforceOperations.getInstance().getServerURL();

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = "urn:partner.soap.sforce.com";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("urn", serverURI);

		// SOAP Header
		SOAPHeader buildSoapHeader = envelope.getHeader();
		SOAPElement soapHeaderElem = buildSoapHeader.addChildElement("SessionHeader", "urn");
		SOAPElement soapHeaderElem1 = soapHeaderElem.addChildElement("sessionId", "urn");
		soapHeaderElem1.addTextNode(CallSalesforceOperations.getInstance().getSessionId());

		// SOAP Body
		SOAPBody buildSoapBody = envelope.getBody();
		SOAPElement soapBodyElem = buildSoapBody.addChildElement("query", "urn");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("queryString", "urn");
		soapBodyElem1.addTextNode("Select Id from " + cmbSObjectType.getText() + " limit 1");

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI + "query");

		soapMessage.saveChanges();
		// Request to soapResponse
		SOAPMessage soapResponse = soapConnection.call(soapMessage, url);
		soapConnection.close();
		SOAPPart soapBody = soapResponse.getSOAPPart();

		NodeList id = soapBody.getElementsByTagName("sf:Id");
		idValue = id.item(0).getFirstChild().getNodeValue();

		return idValue;
	}

	/**
	 * Call Retrieve operation of Salesforce SOAP api to get the response to
	 * generate the Schema.
	 * 
	 * @return response.
	 * @throws Exception
	 */
	public static String callRetrieve() throws Exception {
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		String url = CallSalesforceOperations.getInstance().getServerURL();

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = "urn:partner.soap.sforce.com";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("urn", serverURI);

		// SOAP Header
		SOAPHeader buildSoapHeader = envelope.getHeader();
		SOAPElement soapHeaderElem = buildSoapHeader.addChildElement("SessionHeader", "urn");
		SOAPElement soapHeaderElem1 = soapHeaderElem.addChildElement("sessionId", "urn");
		soapHeaderElem1.addTextNode(CallSalesforceOperations.getInstance().getSessionId());

		// SOAP Body
		SOAPBody buildSoapBody = envelope.getBody();
		SOAPElement soapBodyElem = buildSoapBody.addChildElement("retrieve", "urn");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("fieldList", "urn");
		soapBodyElem1.addTextNode(retrieveTextField.getText());
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("sObjectType", "urn");
		soapBodyElem2.addTextNode(cmbSObjectType.getText());
		SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("ids", "urn");
		soapBodyElem3.addTextNode(getId());

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI + "retrieve");

		soapMessage.saveChanges();
		SOAPMessage soapResponse = soapConnection.call(soapMessage, url);
		SOAPPart soapBody = soapResponse.getSOAPPart();
		DOMSource source = new DOMSource(soapBody);
		StringWriter stringResult = new StringWriter();
		TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));

		String response = stringResult.toString();

		soapConnection.close();
		return response;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 550);
	}

	@Override
	protected void okPressed() {

		try {
			LoginForm.getInstance().setUserName(
					GenerateInputSchemaForRetrieveOperation.connectorLoginSalesforceUserNameTextField.getText());
			LoginForm.getInstance().setPassword(
					GenerateInputSchemaForRetrieveOperation.connectorLoginSalesforcePasswordTextField.getText());
			LoginForm.getInstance().setSecurityToken(
					GenerateInputSchemaForRetrieveOperation.connectorLoginSalesforceSecurityTokenTextField.getText());
			LoginForm.getInstance().setLoginURL(
					GenerateInputSchemaForRetrieveOperation.connectorLoginSalesforceLoginURLTextField.getText());
			value = callRetrieve();
		} catch (Exception e) {
			MessageDialog.openWarning(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
					"Error While calling the Retrieve Method", "Check the Login Credentials and Try Again");
		}
		super.okPressed();
	}

	/**
	 * The value to generate the Schema from the parent dialog.
	 * 
	 * @return response.
	 */
	public String getResponse() {
		return value;
	}
}
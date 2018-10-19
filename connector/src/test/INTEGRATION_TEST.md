Product: Integration tests for WSO2 ESB Salesforce SOAP connector

Pre-requisites:

 - Maven 3.x
 - Java 1.8
 - The org.wso2.esb.integration.integration-base project is required. The test suite has been configured to download this project automatically. If the automatic download fails, download the following project and compile it using the mvn clean install command to update your local repository:
   https://github.com/wso2-extensions/esb-connector-integrationbase

Tested Platform:
 - UBUNTU 16.04
 - WSO2 EI 6.4.0

Steps to follow in setting integration test.

 1. Download WSO2 EI 6.4.0 from official website and place it to {EI_Connector_Home}/repositiry/.

 2. Create a Salesforce account.

 3. Extract the certificate from browser(Mozilla Firefox) by navigating to Salesforce instance url(Eg:- https://ap4.salesforce.com) and place it to {EI_Connector_Home}/repositiry/.
        
 4. To access Salesforce, replace your current password with a combination of your password and a security token.

   Steps

       Log into Salesforce via the browser to request your security token.

       Do one of the following:

           If you have a regular Salesforce account, go to Setup > My Personal Information > Reset Security Token.

           If you have a Salesforce developer account, from the home page, go to Manage Users > Users > click your name > Change password > Reset Security Token.

       This triggers an email which contains your security token.

       Select and copy the token from the email.

       Replace your Salesforce password with combination of the password and the security token. For example, if your password is "MyPassword" and your security token is "XXXXXX", you would enter "MyPasswordXXXXXX" in the Password field.

 5. Update the esb-connector-salesforce.properties with the above credentials.

 6. Following are the Salesforce properties used in esb-connector-salesforce.properties and salesforce.properties file at location "{Connector_Home}/src/test/resources/artifacts/ESB/connector/config" to run the integration tests.
	
	i)      loginUrl           -   The API URL .
	ii) 	username           -   The username of the Salesforce account.
	iii)	password           -   The password of the Salesforce account.
	iv)     blocking           -
	v)      sobject            -   The sobject type(Eg: Account).
	vi)     allOrNone          -   Whether to rollback changes if an object fails(Default value is 0).
	vii)    allowFieldTruncate -   Whether to truncates strings that exceed the field length(Default value is 0).
	viii)   name               -   The name of the record being created.
	ix)     newName            -   The name of the record being updated.
	x)      type1              -   The sobject type.
	xi)     type2              -   Another sobject type(Eg: Contact).
	xii)    queryString        -   The queryString to get the results from API.
	xiii)   batchSize		   -   The number of records to return.
	xiv)    fieldList          -   A comma-separated list of the fields you want to retrieve from the records.
	xv)     objectType         -   The object type of the records.
	xvi)    searchString       -   The searchString to get the Result.

	These following properties are used to send the EMail

	xvii)   bccSender          -   This is a boolean value which Indicates whether the email sender receives a copy of the email that is sent. For a mass mail, the sender is only copied on the first email sent.
	xviii)  emailPriority      -   The priority of the email.
	xvix)   replyTo            -   The email address that receives the message when a recipient replies.
	xx)     saveAsActivity     -   The default value is true, meaning the email is saved as an activity.
	xxi)    senderDisplayName  -   The name that appears on the From line of the email.
	xxii)   subject            -   The email subject line.
	xxiii)  useSignature       -   Indicates whether the email includes an email signature if the user has one configured.
	xxiv)   plainTextBody      -   Text Body of the Mail.
	xxv)    email              -   A valid email address.

 4. Navigate to "{EI_Connector_Home}/" and run the following command.
	  $ mvn clean install -Dskip-tests=false

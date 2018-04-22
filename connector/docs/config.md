# Configuring Salesforce Operations

[[Importing the Salesforce Certificate]](#importing-the-salesforce-certificate)  [[Common parameters]](#common-parameters) [[Salesforce Operations]](#salesforce-perations) [[Logging out of Salesforce]](#logging-out-of-salesforce)

## Importing the Salesforce Certificate

To use the Salesforce connector, add the  <salesforce.init>  element in your configuration before carrying out any other Salesforce operations.

Before you start configuring the Salesforce connector, make sure to import the Salesforce certificate to your ESB client keystore.

* Follow the steps below to import the Salesforce certificate into the ESB client keystore:

    1. Log in to your Salesforce account in your browser (e.g., https://login.salesforce.com), and click the lock on the address bar to view the certificate.
    2. Export the certificate to the file system.
    3. Import the certificate into the ESB client keystore using the Management Console or by using the following command:
    ```
    keytool -importcert -file <certificate file> -keystore <ESB>/repository/resources/security/client-truststore.jks -alias "Salesforce"
    ```
    4. Restart the server and deploy the following Salesforce configuration:

**init**
```xml
<salesforce.init>
    <username>MyUsername</username>
    <password>MyPassword</password>
    <loginUrl>https://login.salesforce.com/services/Soap/u/27.0</loginUrl>
    <blocking>false</blocking>
</salesforce.init>
```
**Properties** 
* username:  The user name to access the Salesforce account.
* password:  The password provided here is a concatenation of the user password and the security token provided by Salesforce.
* loginUrl:  The login URL to access the Salesforce account.
* blocking:  Indicated whether the connector needs to perform blocking invocations to Salesforce. (Supported in ESB 4.9.0 and later) 

```text
*  Users can obtain the security token by changing the password or resetting the security token using the Salesforce user interface. The new security token will be sent to the email address recorded in the user's Salesforce record.
*  The response of this operation is attached to the message body and is used for subsequent Salesforce operations.
*  The session ID is saved into the property salesforce.sessionId, and the server URL is saved into salesforce.serviceUrl. If the given login details are invalid, the specified fault sequence will be triggered.
```
---
**Note :**
Secure Vault is supported for encrypting passwords. See, [Working with Passwords](https://docs.wso2.com/display/ADMIN44x/Encrypting+Passwords+with+Cipher+Tool) on integrating and using Secure Vault.
---
**Re-using Salesforce configurations**

You can save the Salesforce connection configuration as a [local entry](https://docs.wso2.com/display/EI620/Working+with+Local+Registry+Entries) and then easily reference it with the configKey attribute in your operations. For example, if you saved the above <salesforce.init> entry as a local entry named MySFConfig, you could reference it from an operation like getUserInfo as follows:

```xml
<salesforce.getUserInformation configKey="MySFConfig"/>
```
The Salesforce connector operation examples use this convention to show how to specify the connection configuration for that operation. In all cases, the configKey attribute is optional if the connection to Salesforce has already been established and is required only if you need to specify a different connection from the current connection.

## Common parameters

Listed below are parameters that are common when [working with records](records.md) and [working with the recyclebin](recyclebin.md) in Salesforce.


| Name | Description | Default value |
| ------------- | ------------- | ------------- |
| allowFieldTruncate | Set to 1, to truncate string values if they exceed the defined field length. | 0 |
| allOrNone | Set to 1, to roll back changes if any object fails when multiple objects are sent.If set to 0 (false), some records can be processed successfully while others are marked as failed in the call results. | 0 |

## Salesforce Operations

Now that you have connected to Salesforce, use the information in the following topics to perform various operations with the connector.

[Working with sObjects](sobjects.md)

[Working with Records](records.md)

[Working with the Recycle bin](recyclebin.md)

[Working with a User](user.md)

[Working with Emails](emails.md)

## Logging out of Salesforce
To log out of Salesforce and close the current connection, use salesforce.logout.

**logout**
```xml
<salesforce.logout/>
```
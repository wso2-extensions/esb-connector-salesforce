# Working with User in Salesforce

[[ Overview ]](#overview)  [[ Operation details ]](#operation-details)

### Overview 

The following operations allow you to work with users. Click an operation name to see details on how to use it.

| Operation        | Description |
| ------------- |-------------|
| [getUserInfo](#retrieving-the-user-information)    | Retrieves information about the currently logged in user. |
| [setPassword	](#retrieving-metadata-for-a-specific-object-type)    | Changes a user's password. |

### Operation details

This section provides more information on each of the operations.

#### Retrieving the user information

To retrieve information about the user who is currently logged in, use salesforce.getUserInfo. The information provided includes the name, ID, and contact information of the user. See, the [Salesforce documentation](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_getuserinfo_getuserinforesult.htm) for details of the information that is returned using this operation.

If you want to get additional information about the user that is not returned by this operation, use retrieve operation on the User object providing the ID returned from getUserInfo.

**getUserInfo**
```xml
<salesforce.getUserInfo configKey="MySFConfig"/>
```

**Sample response**

Given below is a sample response for the getUserInfo operation.

```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>11</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <getUserInfoResponse>
            <result>
                <accessibilityMode>false</accessibilityMode>
                <currencySymbol>$</currencySymbol>
                <orgAttachmentFileSizeLimit>5242880</orgAttachmentFileSizeLimit>
                <orgDefaultCurrencyIsoCode>USD</orgDefaultCurrencyIsoCode>
                <orgDisallowHtmlAttachments>false</orgDisallowHtmlAttachments>
                <orgHasPersonAccounts>false</orgHasPersonAccounts>
                <organizationId>00D6F000002SofgUAC</organizationId>
                <organizationMultiCurrency>false</organizationMultiCurrency>
                <organizationName>john</organizationName>
                <profileId>00e6F000003GTmYQAW</profileId>
                <roleId xsi:nil="true"/>
                <sessionSecondsValid>7200</sessionSecondsValid>
                <userDefaultCurrencyIsoCode xsi:nil="true"/>
                <userEmail>iamjohn@gmail.com</userEmail>
                <userFullName>john doe</userFullName>
                <userId>0056F000009wCJgQAM</userId>
                <userLanguage>en_US</userLanguage>
                <userLocale>en_US</userLocale>
                <userName>iamjohn@gmail.com</userName>
                <userTimeZone>America/Los_Angeles</userTimeZone>
                <userType>Standard</userType>
                <userUiSkin>Theme3</userUiSkin>
            </result>
        </getUserInfoResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_getuserinfo.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_getuserinfo.htm)

#### Changing user password

To change the user password by specifying the password, use salesforce.setPassword as follows:

**setPassword**
```xml
<salesforce.setPassword configKey="MySFConfig">
    <userId>0056F000009wCJgQAM</userId>
    <password>abc123</password>
</salesforce.setPassword>
```
**Properties**
* userId: The user's Salesforce ID.
* password: If using setPassword, the new password to assign to the user.

**Sample response**

Given below is a sample response for the setPassword operation.

```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>17</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <setPasswordResponse>
            <result/>
        </setPasswordResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

To change the user password using a system generated password, use salesforce.resetPassword as follows:

**resetPassword**
```xml
<salesforce.resetPassword configKey="MySFConfig">
    <userId>0056F000009wCJgQAM</userId>
</salesforce.resetPassword>
```
**Properties**
* userId: The user's Salesforce ID.

**Sample response**

Given below is a sample response for the resetPassword operation.

```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>23</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <resetPasswordResponse>
            <result>
                <password>H5fj8A6M</password>
            </result>
        </resetPasswordResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_setpassword.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_setpassword.htm)

**Sample Configuration**

Following example illustrates how to connect to Salesforce with the init operation and getUserInfo operation.

1. Create a sample proxy as below :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse"
       name="salesforce_getUserInfo"
       startOnLoad="true"
       statistics="disable"
       trace="disable"
       transports="http,https">
   <target>
      <inSequence>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:loginUrl/text()"
                   name="loginUrl"/>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:username/text()"
                   name="username"/>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:password/text()"
                   name="password"/>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:blocking/text()"
                   name="blocking"/>
         <salesforce.init>
            <loginUrl>{$ctx:loginUrl}</loginUrl>
            <username>{$ctx:username}</username>
            <password>{$ctx:password}</password>
            <blocking>{$ctx:blocking}</blocking>
         </salesforce.init>
         <salesforce.getUserInfo/>
         <respond/>
      </inSequence>
      <outSequence>
         <send/>
      </outSequence>
   </target>
   <description/>
</proxy>                              
```
2. Create a xml file called getUserInfo.xml containing the following xml:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:urn="wso2.connector.salesforce">
    <soapenv:Header/>
    <soapenv:Body>
        <urn:loginUrl>https://login.salesforce.com/services/Soap/u/30.0</urn:loginUrl>
        <urn:username>john@gmail.com</urn:username>
        <urn:password>john@123CtGoiPE3mCdjgUHlto8HJ3</urn:password>
        <urn:blocking>false</urn:blocking>
    </soapenv:Body>
</soapenv:Envelope>                              
```
3. Replace the credentials with your values.

4. Execute the following cURL command:

```bash
curl http://localhost:8280/services/salesforce_getUserInfo -H "Content-Type: text/xml" -d @getUserInfo.xml
```
5. Salesforce returns a xml response as below.
 
```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>11</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <getUserInfoResponse>
            <result>
                <accessibilityMode>false</accessibilityMode>
                <currencySymbol>$</currencySymbol>
                <orgAttachmentFileSizeLimit>5242880</orgAttachmentFileSizeLimit>
                <orgDefaultCurrencyIsoCode>USD</orgDefaultCurrencyIsoCode>
                <orgDisallowHtmlAttachments>false</orgDisallowHtmlAttachments>
                <orgHasPersonAccounts>false</orgHasPersonAccounts>
                <organizationId>00D6F000002SofgUAC</organizationId>
                <organizationMultiCurrency>false</organizationMultiCurrency>
                <organizationName>john</organizationName>
                <profileId>00e6F000003GTmYQAW</profileId>
                <roleId xsi:nil="true"/>
                <sessionSecondsValid>7200</sessionSecondsValid>
                <userDefaultCurrencyIsoCode xsi:nil="true"/>
                <userEmail>iamjohn@gmail.com</userEmail>
                <userFullName>john doe</userFullName>
                <userId>0056F000009wCJgQAM</userId>
                <userLanguage>en_US</userLanguage>
                <userLocale>en_US</userLocale>
                <userName>iamjohn@gmail.com</userName>
                <userTimeZone>America/Los_Angeles</userTimeZone>
                <userType>Standard</userType>
                <userUiSkin>Theme3</userUiSkin>
            </result>
        </getUserInfoResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

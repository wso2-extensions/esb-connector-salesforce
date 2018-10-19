# Working with Emails in Salesforce

[[ Overview ]](#overview)  [[ Operation details ]](#operation-details)

### Overview 

The following operations allow you to work with emails. Click an operation name to see details on how to use it.

| Operation        | Description |
| ------------- |-------------|
| [sendEmail](#creating-and-sending-an-email)    | Creates and sends an email using Salesforce. |
| [sendEmailMessage](#retrieving-metadata-for-a-specific-object-type)    | Sends emails that have already been drafted in Salesforce. |

### Operation details

This section provides further details on the operations related to Emails.

#### Creating and sending an email

To create and send an email, use salesforce.sendEmail and specify the following properties. 

**sendEmail**
```xml
<salesforce.sendEmail>
    <sendEmail xmlns:sfdc="sfdc">{//sfdc:emailWrapper}</sendEmail>
</salesforce.sendEmail>
```
**Properties**
* sendEmail: XML representation of the email.

**Sample request**

Given below is a sample request that can be handled by the sendEmail operation.

```xml
<payloadFactory>
   <format>
     <sfdc:emailWrapper xmlns:sfdc="sfdc">
     <sfdc:messages type="urn:SingleEmailMessage">
        <sfdc:bccSender>true</sfdc:bccSender>
        <sfdc:emailPriority>High</sfdc:emailPriority>
        <sfdc:replyTo>123@gmail.com</sfdc:replyTo>
        <sfdc:saveAsActivity>false</sfdc:saveAsActivity>
        <sfdc:senderDisplayName>wso2</sfdc:senderDisplayName>
        <sfdc:subject>test</sfdc:subject>
        <sfdc:useSignature>false</sfdc:useSignature>
       <sfdc:targetObjectId>00390000001PBFn</sfdc:targetObjectId>
       <sfdc:plainTextBody>Hello, this is a holiday greeting!</sfdc:plainTextBody>    
     </sfdc:messages>
      </sfdc:emailWrapper>
    </format>
    <args/>
</payloadFactory>
          
<salesforce.sendEmail>
    <sendEmail xmlns:sfdc="sfdc">{//sfdc:emailWrapper}</sendEmail>
</salesforce.sendEmail>
```
**Sample response**

Given below is a sample response for the sendEmail operation.

```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>67</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <sendEmailResponse>
            <result>
                <success>true</success>
            </result>
        </sendEmailResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_sendemail.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_sendemail.htm)

#### Sending existing email drafts

To send emails that have already been drafted, use salesforce.sendEmailMessage and specify the IDs of the emails you want to send. 

**sendEmailMessage**
```xml
<salesforce.sendEmailMessage config-ref="connectorConfig">
    <sendEmailMessage xmlns:sfdc="sfdc">{//sfdc:emails}</sendEmailMessage>
</salesforce.sendEmailMessage>
```

**Properties**
* sendEmailMessage: XML representation of the email IDs to send.

**Sample request**

Given below is a sample request that can be handled by the sendEmailMessage operation.

```xml
<payloadFactory>
    <format>
        <sfdc:emails xmlns:sfdc="sfdc">
            <sfdc:Ids>0019000000aaMkK</sfdc:Ids>
            <sfdc:Ids>0019000000bbMkK</sfdc:Ids>
        </sfdc:emails>
    </format>
    <args/>
</payloadFactory>
 
<salesforce.sendEmailMessage config-ref="connectorConfig">
    <sendEmailMessage xmlns:sfdc="sfdc">{//sfdc:emails}</sendEmailMessage>
</salesforce.sendEmailMessage>
```
**Sample response**

Given below is a sample response for the sendEmail operation.

```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>67</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <sendEmailResponse>
            <result>
                <success>true</success>
            </result>
        </sendEmailResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_send_email_message.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_send_email_message.htm)

**Sample Configuration**

Following example illustrates how to connect to Salesforce with the init operation and sendEmail operation.

1. Create a sample proxy as below :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse"
       name="salesforce_sendEmail"
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
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:bccSender/text()"
                   name="bccSender"/>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:emailPriority/text()"
                   name="emailPriority"/>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:replyTo/text()"
                   name="replyTo"/>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:saveAsActivity/text()"
                   name="saveAsActivity"/>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:senderDisplayName/text()"
                   name="senderDisplayName"/>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:subject/text()"
                   name="subject"/>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:useSignature/text()"
                   name="useSignature"/>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:targetObjectId/text()"
                   name="targetObjectId"/>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:plainTextBody/text()"
                   name="plainTextBody"/>
         <salesforce.init>
            <loginUrl>{$ctx:loginUrl}</loginUrl>
            <username>{$ctx:username}</username>
            <password>{$ctx:password}</password>
            <blocking>{$ctx:blocking}</blocking>
         </salesforce.init>
         <payloadFactory media-type="xml">
            <format>
               <sfdc:emailWrapper xmlns:sfdc="sfdc">
                  <sfdc:messages type="urn:SingleEmailMessage">
                     <sfdc:bccSender>$1</sfdc:bccSender>
                     <sfdc:emailPriority>$2</sfdc:emailPriority>
                     <sfdc:replyTo>$3</sfdc:replyTo>
                     <sfdc:saveAsActivity>$4</sfdc:saveAsActivity>
                     <sfdc:senderDisplayName>$5</sfdc:senderDisplayName>
                     <sfdc:subject>$6</sfdc:subject>
                     <sfdc:useSignature>$7</sfdc:useSignature>
                     <sfdc:targetObjectId>$8</sfdc:targetObjectId>
                     <sfdc:plainTextBody>$9</sfdc:plainTextBody>
                  </sfdc:messages>
               </sfdc:emailWrapper>
            </format>
            <args>
               <arg evaluator="xml" expression="$ctx:bccSender"/>
               <arg evaluator="xml" expression="$ctx:emailPriority"/>
               <arg evaluator="xml" expression="$ctx:replyTo"/>
               <arg evaluator="xml" expression="$ctx:saveAsActivity"/>
               <arg evaluator="xml" expression="$ctx:senderDisplayName"/>
               <arg evaluator="xml" expression="$ctx:subject"/>
               <arg evaluator="xml" expression="$ctx:useSignature"/>
               <arg evaluator="xml" expression="$ctx:targetObjectId"/>
               <arg evaluator="xml" expression="$ctx:plainTextBody"/>
            </args>
         </payloadFactory>
         <salesforce.sendEmail>
            <sendEmail xmlns:sfdc="sfdc">{//sfdc:emailWrapper}</sendEmail>
         </salesforce.sendEmail>
         <respond/>
      </inSequence>
      <outSequence>
         <send/>
      </outSequence>
   </target>
   <description/>
</proxy>                                                  
```
2. Create a xml file called sendEmail.xml containing the following xml:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:urn="wso2.connector.salesforce">
    <soapenv:Header/>
    <soapenv:Body>
        <urn:loginUrl>https://login.salesforce.com/services/Soap/u/30.0</urn:loginUrl>
        <urn:username>john@gmail.com</urn:username>
        <urn:password>john@123CtGoiPE3mCdjgUHlto8HJ3</urn:password>
        <urn:blocking>false</urn:blocking>
        <urn:bccSender>true</urn:bccSender>
        <urn:emailPriority>High</urn:emailPriority>
        <urn:replyTo>123@gmail.com</urn:replyTo>
        <urn:saveAsActivity>false</urn:saveAsActivity>
        <urn:senderDisplayName>wso2</urn:senderDisplayName>
        <urn:subject>Greetings</urn:subject>
        <urn:useSignature>false</urn:useSignature>
        <urn:targetObjectId>0036F00002mdwQY</urn:targetObjectId>
        <urn:plainTextBody>Hello, this is a holiday greeting!</urn:plainTextBody>        
    </soapenv:Body>
</soapenv:Envelope>                              
```
3. Replace the credentials with your values.

4. Execute the following cURL command:

```bash
curl http://localhost:8280/services/salesforce_sendEmail -H "Content-Type: text/xml" -d @sendEmail.xml
```
5. Salesforce returns a xml response as below.
 
```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>67</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <sendEmailResponse>
            <result>
                <success>true</success>
            </result>
        </sendEmailResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

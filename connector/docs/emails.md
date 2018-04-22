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

**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_send_email_message.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_send_email_message.htm)


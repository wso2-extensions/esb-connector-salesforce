# Working with Recycle Bin in Salesforce

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)

### Overview 

The Recycle Bin allows you to view and restore recently deleted records for a maximum of 15 days before they are permanently deleted. To purge records from the Recycle Bin so that they cannot be restored, use salesforce.emptyRecycleBin and specify the following properties. 

**emptyRecycleBin**
```xml
<salesforce.emptyRecycleBin config-ref="connectorConfig">
    <allOrNone>0</allOrNone>
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.emptyRecycleBin>
```
**Properties**
* allOrNone: Whether to rollback changes if an object fails (see Common Parameters).
* sobjects: XML representation of the records to purge from the Recycle Bin.

**Sample request**

Given below is a sample request that can be handled by the emptyRecycleBin operation.

```xml
<payloadFactory>
   <format>
      <sfdc:sObjects xmlns:sfdc="sfdc">
         <sfdc:Ids>0019000000aaMkZ</sfdc:Ids>
         <sfdc:Ids>0019000000aaMkP</sfdc:Ids>
      </sfdc:sObjects>
   </format>
   <args/>
</payloadFactory>
 
<salesforce.emptyRecycleBin>
    <allOrNone>0</allOrNone>
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.emptyRecycleBin>
```

**Sample response**

Given below is a sample response for the emptyRecycleBin operation.

```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>27</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <emptyRecycleBinResponse>
            <result>
                <id>0016F00002S4WaGQAV</id>
                <success>true</success>
            </result>
        </emptyRecycleBinResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_emptyrecyclebin.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_emptyrecyclebin.htm)

**Sample Configuration**

Following example illustrates how to connect to Salesforce with the init operation and emptyRecycleBin operation.

1. Create a sample proxy as below :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse"
       name="salesforce_emptyRecycleBin"
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
                   expression="//ns:id/text()"
                   name="id"/>
         <property xmlns:ns="wso2.connector.salesforce"
                   expression="//ns:allOrNone/text()"
                   name="allOrNone"/>
         <salesforce.init>
            <loginUrl>{$ctx:loginUrl}</loginUrl>
            <username>{$ctx:username}</username>
            <password>{$ctx:password}</password>
            <blocking>{$ctx:blocking}</blocking>
         </salesforce.init>
         <payloadFactory media-type="xml">
            <format>
               <sfdc:sObjects xmlns:sfdc="sfdc">
                  <sfdc:Ids>$1</sfdc:Ids>
               </sfdc:sObjects>
            </format>
            <args>
               <arg evaluator="xml" expression="$ctx:id"/>
            </args>
         </payloadFactory>
         <salesforce.emptyRecycleBin>
            <allOrNone>{$ctx:loginUrl}</allOrNone>
            <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
         </salesforce.emptyRecycleBin>
         <respond/>
      </inSequence>
      <outSequence>
         <send/>
      </outSequence>
   </target>
   <description/>
</proxy>                                                             
```
2. Create a xml file called emptyRecycleBin.xml containing the following xml:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:urn="wso2.connector.salesforce">
    <soapenv:Header/>
    <soapenv:Body>
        <urn:loginUrl>https://login.salesforce.com/services/Soap/u/30.0</urn:loginUrl>
        <urn:username>john@gmail.com</urn:username>
        <urn:password>john@123CtGoiPE3mCdjgUHlto8HJ3</urn:password>
        <urn:blocking>false</urn:blocking>
        <urn:id>0016F00002S4YYh</urn:id>
        <urn:allOrNone>0</urn:allOrNone>
    </soapenv:Body>
</soapenv:Envelope>                             
```
3. Replace the credentials with your values.

4. Execute the following cURL command:

```bash
curl http://localhost:8280/services/salesforce_emptyRecycleBin -H "Content-Type: text/xml" -d @emptyRecycleBin.xml
```
5. Salesforce returns a xml response as below.
 
```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>27</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <emptyRecycleBinResponse>
            <result>
                <id>0016F00002S4WaGQAV</id>
                <success>true</success>
            </result>
        </emptyRecycleBinResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

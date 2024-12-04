# Working with Utility Calls in Salesforce

[[ Overview ]](#overview)  [[ Operation details ]](#operation-details) [ [Sample configuration](#sample-configuration) ]

### Overview 

The following operations allow you to work with utility calls. Click an operation name to see details on how to use it.

| Operation        | Description |
| ------------- |-------------|
| [getServerTimestamp](#get-server-timestamp)    | Retrieves timestamp of the server at the request time. |

### Operation details

This section provides more information on each of the operations.

#### Retrieving Server Timestamp

To retrieve the timestampt of the server, use salesforce.getServerTimestamp as follows:

###### getServerTimestamp
```xml
<salesforce.getServerTimestamp configKey="MySFConfig"/>
```
###### Properties

###### Sample request

Given below is a sample request that can be handled by the undelete operation.

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:urn="wso2.connector.salesforce">
    <soapenv:Header/>
    <soapenv:Body>
        <urn:loginUrl>https://login.salesforce.com/services/Soap/u/30.0</urn:loginUrl>
        <urn:username>XXXXXXXXXX</urn:username>
        <urn:password>XXXXXXXXXX</urn:password>
        <urn:blocking>false</urn:blocking>
    </soapenv:Body>
</soapenv:Envelope> 
```

###### Sample response

Given below is a sample response for the setPassword operation.

```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>58</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <getServerTimestampResponse>
            <result>
                <timestamp>2020-07-03T09:14:41.321Z</timestamp>
            </result>
        </getServerTimestampResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

###### Related Salesforce documentation

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_getservertimestamp.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_getservertimestamp.htm)


### Sample configuration

Following example illustrates how to connect to Salesforce with the init operation and getUserInfo operation.

1. Create a sample proxy as below :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse"
       name="salesforce_getServerTimestamp"
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
         <salesforce.getServerTimestamp/>
         <respond/>
      </inSequence>
      <outSequence>
         <send/>
      </outSequence>
   </target>
   <description/>
</proxy>                              
```
2. Create an XML file named getServerTimestamp.xml and copy the XML configurations given below:

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

4. Execute the following curl command:

```bash
curl http://localhost:8280/services/salesforce_getServerTimestamp -H "Content-Type: text/xml" -d @getServerTimestamp.xml
```
5. Salesforce returns an XML response similar to the response given below:
 
```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>58</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <getServerTimestampResponse>
            <result>
                <timestamp>2020-07-03T09:14:41.321Z</timestamp>
            </result>
        </getServerTimestampResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

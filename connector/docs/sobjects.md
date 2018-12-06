# Working with sObjects in Salesforce

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details) [ [Sample configuration](#sample-configuration) ]

### Overview 

The following operations allow you to work with sObjects. Click an operation name to see details on how to use it.

| Operation        | Description |
| ------------- |-------------|
| [describeGlobal](#retrieving-a-list-of-available-objects)    | Retrieves the objects that are available in the system. |
| [describeSObject](#retrieving-metadata-for-a-specific-object-type)      | Retrieves metadata (such as name, label, and fields, including the field properties) for a specific object type. |
| [describeSObjects](#retrieving-metadata-for-multiple-object-types)    | Retrieves metadata (such as name, label, and fields, including the field properties) for multiple object types. |


An sObject refers to any object that can be stored in the [Force.com](https://www.salesforce.com) platform database and includes all the standard and custom objects in your organization. With the Salesforce connector operations, you can define an sObject structure in the message payload, which you can send directly from the caller or generate using the [PayloadFactory mediator](https://docs.wso2.com/display/EI620/PayloadFactory+Mediator) in ESB as follows:

```xml
<payloadFactory>
    <format>                                           
        <sfdc:sObjects xmlns:sfdc="sfdc" type="Account">
          <sfdc:sObject>
             <sfdc:Name>value01</sfdc:Name>             
          </sfdc:sObject>
          <sfdc:sObject>                                
             <sfdc:Name>value02</sfdc:Name>
          </sfdc:sObject>
       </sfdc:sObjects>
    </format>
    <args/>
</payloadFactory>
```
### Operation details

This section provides more information on each of the operations.

#### Retrieving a list of available objects

To retrieve a list of objects that are available in the system, use salesforce.describeGlobal. 

###### describeGlobal
```xml
<salesforce.describeGlobal configKey="MySFConfig"/>
```

###### Sample response

Given below is a sample response for the describeGlobal operation.

```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>29</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <describeGlobalResponse>
            <result>
                <encoding>UTF-8</encoding>
                <maxBatchSize>200</maxBatchSize>
                <sobjects>
                    <activateable>false</activateable>
                    <createable>false</createable>
                    <custom>false</custom>
                    <customSetting>false</customSetting>
                    <deletable>false</deletable>
                    <deprecatedAndHidden>false</deprecatedAndHidden>
                    <feedEnabled>false</feedEnabled>
                    <keyPrefix xsi:nil="true"/>
                    <label>Accepted Event Relation</label>
                    <labelPlural>Accepted Event Relations</labelPlural>
                    <layoutable>false</layoutable>
                    <mergeable>false</mergeable>
                    <name>AcceptedEventRelation</name>
                    <queryable>true</queryable>
                    <replicateable>false</replicateable>
                    <retrieveable>true</retrieveable>
                    <searchable>false</searchable>
                    <triggerable>false</triggerable>
                    <undeletable>false</undeletable>
                    <updateable>false</updateable>
                </sobjects>
                .
                .
            </result>
        </describeGlobalResponse>
    </soapenv:Body>
</soapenv:Envelope>
```
###### Related Salesforce documentation

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_describeglobal.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_describeglobal.htm)

#### Retrieving metadata for a specific object type

To retrieve metadata (such as name, label, and fields, including the field properties) for a specific object type, use salesforce.describeSobject and specify the following properties. 

###### describeSobject
```xml
<salesforce.describeSObject configKey="MySFConfig">
    <sobject>Account</sobject>
</salesforce.describeSObject>
```

###### Properties
* sobject: The object type of where you want to retrieve the metadata.

###### Sample response

Given below is a sample response for the describeSObject operation.

```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>31</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <describeSObjectResponse>
            <result>
                <activateable>false</activateable>
                <childRelationships>
                    <cascadeDelete>false</cascadeDelete>
                    <childSObject>Account</childSObject>
                    <deprecatedAndHidden>false</deprecatedAndHidden>
                    <field>ParentId</field>
                    <relationshipName>ChildAccounts</relationshipName>
                </childRelationships>
                .
                .
            </result>
        </describeSObjectResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

###### Related Salesforce documentation

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_describesobject.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_describesobject.htm)

#### Retrieving metadata for multiple object types

To retrieve metadata (such as name, label, and fields, including the field properties) for multiple object types returned as an array, use salesforce.describeSobjects and specify the following properties. 

###### describeSobjects
```xml
<salesforce.describeSobjects configKey="MySFConfig">
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.describeSobjects>
```

###### Properties
* sobjects: An XML representation of the object types of where you want to retrieve the metadata.

###### Sample request

Given below is a sample request that can be handled by the describeSobjects operation.

```xml
<payloadFactory>
    <format>
        <sfdc:sObjects xmlns:sfdc="sfdc">
            <sfdc:sObjectType>Account</sfdc:sObjectType>
            <sfdc:sObjectType>Contact</sfdc:sObjectType>
         </sfdc:sObjects>
     </format>
     <args/>
 </payloadFactory>
 
<salesforce.describeSobjects configKey="MySFConfig">
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.describeSobjects>
```

###### Sample response

Given below is a sample response for the describeSobjects operation.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>51</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <describeSObjectsResponse>
            <result>
                <activateable>false</activateable>
                <childRelationships>
                    <cascadeDelete>false</cascadeDelete>
                    <childSObject>Account</childSObject>
                    <deprecatedAndHidden>false</deprecatedAndHidden>
                    <field>ParentId</field>
                    <relationshipName>ChildAccounts</relationshipName>
                </childRelationships>
                .
                .
            </result>
            <result>
                <activateable>false</activateable>
                <childRelationships>
                    <cascadeDelete>false</cascadeDelete>
                    <childSObject>AcceptedEventRelation</childSObject>
                    <deprecatedAndHidden>false</deprecatedAndHidden>
                    <field>RelationId</field>
                    <relationshipName>AcceptedEventRelations</relationshipName>
                </childRelationships>
                .
                .
            </result>
        </describeSObjectsResponse>
    </soapenv:Body>
</soapenv:Envelope>
```
###### Related Salesforce documentation

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_describesobjects.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_describesobjects.htm)

### Sample configuration

Following example illustrates how to connect to Salesforce with the init operation and describeGlobal operation.

1. Create a sample proxy as below :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse"
       name="salesforce_describeGlobal"
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
         <salesforce.describeGlobal/>
         <respond/>
      </inSequence>
      <outSequence>
         <send/>
      </outSequence>
   </target>
   <description/>
</proxy>                              
```
2. Create an XML file named describeGlobal.xml and copy the XML configurations given below:

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
curl http://localhost:8280/services/salesforce_describeGlobal -H "Content-Type: text/xml" -d @describeGlobal.xml
```
5. Salesforce returns an XML response similar to the response given below:
 
```xml
<?xml version='1.0' encoding='utf-8'?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <soapenv:Header>
        <LimitInfoHeader>
            <limitInfo>
                <current>29</current>
                <limit>15000</limit>
                <type>API REQUESTS</type>
            </limitInfo>
        </LimitInfoHeader>
    </soapenv:Header>
    <soapenv:Body>
        <describeGlobalResponse>
            <result>
                <encoding>UTF-8</encoding>
                <maxBatchSize>200</maxBatchSize>
                <sobjects>
                    <activateable>false</activateable>
                    <createable>false</createable>
                    <custom>false</custom>
                    <customSetting>false</customSetting>
                    <deletable>false</deletable>
                    <deprecatedAndHidden>false</deprecatedAndHidden>
                    <feedEnabled>false</feedEnabled>
                    <keyPrefix xsi:nil="true"/>
                    <label>Accepted Event Relation</label>
                    <labelPlural>Accepted Event Relations</labelPlural>
                    <layoutable>false</layoutable>
                    <mergeable>false</mergeable>
                    <name>AcceptedEventRelation</name>
                    <queryable>true</queryable>
                    <replicateable>false</replicateable>
                    <retrieveable>true</retrieveable>
                    <searchable>false</searchable>
                    <triggerable>false</triggerable>
                    <undeletable>false</undeletable>
                    <updateable>false</updateable>
                </sobjects>
                .
                .
            </result>
        </describeGlobalResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

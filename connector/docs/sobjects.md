# Working with sObjects in Salesforce

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)

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

**describeGlobal**
```xml
<salesforce.describeGlobal configKey="MySFConfig"/>
```
**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_describeglobal.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_describeglobal.htm)

#### Retrieving metadata for a specific object type

To retrieve metadata (such as name, label, and fields, including the field properties) for a specific object type, use salesforce.describeSobject and specify the following properties. 

**describeSobject**
```xml
<salesforce.describeSObject configKey="MySFConfig">
    <sobject>Account</sobject>
</salesforce.describeSObject>
```

**Properties**
* sobject: The object type of where you want to retrieve the metadata.

**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_describesobject.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_describesobject.htm)

#### Retrieving metadata for multiple object types

To retrieve metadata (such as name, label, and fields, including the field properties) for multiple object types returned as an array, use salesforce.describeSobjects and specify the following properties. 

**describeSobjects**
```xml
<salesforce.describeSobjects configKey="MySFConfig">
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.describeSobjects>
```

**Properties**
* sobjects: An XML representation of the object types of where you want to retrieve the metadata.

**Sample request**

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
**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_describesobjects.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_describesobjects.htm)

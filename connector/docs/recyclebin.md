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

**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_emptyrecyclebin.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_emptyrecyclebin.htm)

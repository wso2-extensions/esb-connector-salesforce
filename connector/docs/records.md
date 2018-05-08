# Working with Records in Salesforce

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)

### Overview 

The following operations allow you to work with Records. A Record is an instance of a particular sObject. Click an operation name to see details on how to use it.

| Operation        | Description |
| ------------- |-------------|
| [create](#creating-records)    | Creates records in Salesforce. |
| [update](#updating-records)      | Updates a record in Salesforce. |
| [upsert](#updating-and-inserting-records)    | Updates existing records and inserts a new record in a single operation. |
| [search](#searching-records)    | Search for records in Salesforce. |
| [query](#querying-records)      |  	Retrieves data from a record in Salesforce. |
| [retrieve](#retrieving-specific-records)    | Retrieves data from a record in Salesforce when the record ID is provided. |
| [delete](#deleting-records)    | Deletes a record in Salesforce. |
| [undelete](#restoring-records)      | Restores a record that was previously deleted in Salesforce. |

### Operation details

This section provides further details on the operations related to records.


#### Creating records

To create one or more record, use salesforce.create and specify the following properties. 

**create**
```xml
<salesforce.create configKey="MySFConfig">
    <allOrNone>0</allOrNone>
    <allowFieldTruncate>0</allowFieldTruncate>
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.create>
```
**Properties**
* allOrNone: Whether to rollback changes if an object fails (see Common Parameters).
* allowFieldTruncate: Whether to truncate strings that exceed the field length (see Common Parameters).
* sobjects: XML representation of the records to add.

**Sample request**

Given below is a sample request that can be handled by the create operation.

```xml
<payloadFactory>
    <format>
       <sfdc:sObjects xmlns:sfdc="sfdc" type="Account">
          <sfdc:sObject>
              <sfdc:Name>wso2123</sfdc:Name>
           </sfdc:sObject>
           <sfdc:sObject>
             <sfdc:Name>abc123</sfdc:Name>
           </sfdc:sObject>
        </sfdc:sObjects>
    </format>
    <args/>
</payloadFactory>
 
<salesforce.create>
    <allOrNone>0</allOrNone>
    <allowFieldTruncate>0</allowFieldTruncate>
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.create>
```

**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_create.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_create.htm)

#### Updating records

To update one or more existing records, use salesforce.update and specify the following properties. 

**update**
```xml
<salesforce.update configKey="MySFConfig">
    <allOrNone>0</allOrNone>
    <allowFieldTruncate>0</allowFieldTruncate>
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.update>
```

**Properties**
* allOrNone: Whether to rollback changes if an object fails (see Common Parameters).
* allowFieldTruncate: Whether to truncate strings that exceed the field length (see Common Parameters).
* sobjects: XML representation of the records to add.

**Sample request**

Given below is a sample request that can be handled by the update operation.

```xml
<payloadFactory>
    <format>
        <sfdc:sObjects xmlns:sfdc="sfdc" type="Account">
          <sfdc:sObject>
             <sfdc:Id>0019000000aaMkZ</sfdc:Id>
             <sfdc:Name>newname01</sfdc:Name>
          </sfdc:sObject>
          <sfdc:sObject>
             <sfdc:Id>0019000000aaMkP</sfdc:Id>
             <sfdc:Name>newname02</sfdc:Name>
          </sfdc:sObject>
       </sfdc:sObjects>
    </format>
    <args/>
</payloadFactory>
 
<salesforce.update>
    <allOrNone>0</allOrNone>
    <allowFieldTruncate>0</allowFieldTruncate>
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.update>
```
**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_update.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_update.htm)

#### Updating and inserting records

To update existing records and insert new records in a single operation, use salesforce.upsert and specify the following properties. 

**upsert**
```xml
<salesforce.upsert configKey="MySFConfig">
    <allOrNone>0</allOrNone>
    <allowFieldTruncate>0</allowFieldTruncate>
    <externalId>Id</externalId>
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.upsert>
```

**Properties**
* allOrNone: Whether to rollback changes if an object fails (see Common Parameters).
* allowFieldTruncate: Whether to truncate strings that exceed the field length (see Common Parameters).
* externalId: The field containing the record ID, that is used by Salesforce to determine whether to update an existing record or create a new one. This is done by matching the ID to the record IDs in Salesforce. By default, the field is assumed to be named "Id".
* sObjects: XML representation of the records to update and insert. When inserting a new record, you do not specify sfdc:Id.

---
**Set the externalId field :**
If you need to give any existing externalId field of sObject to externalId then the payload should be with that externalId field and value as follows in sample
---
**Sample to set ExternalId field and value**


```xml
<payloadFactory>
    <format>
        <sfdc:sObjects xmlns:sfdc="sfdc" type="Account">
          <sfdc:sObject>
             <sfdc:sample__c>{any value}</sfdc:sample__c>
             <sfdc:Name>newname001</sfdc:Name>
          </sfdc:sObject>
       </sfdc:sObjects>
    </format>
    <args/>
</payloadFactory>
 
<salesforce.upsert>
    <allOrNone>0</allOrNone>
    <allowFieldTruncate>0</allowFieldTruncate>
    <externalId>sample__c</externalId>
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.upsert>
```

**Sample request**

Given below is a sample request that can be handled by the upsert operation.

```xml
<payloadFactory>
    <format>
        <sfdc:sObjects xmlns:sfdc="sfdc" type="Account">
          <sfdc:sObject>
             <sfdc:Id>0019000000aaMkZ</sfdc:Id>
             <sfdc:Name>newname001</sfdc:Name>
          </sfdc:sObject>
          <sfdc:sObject>
             <sfdc:Name>newname002</sfdc:Name>
          </sfdc:sObject>
       </sfdc:sObjects>
    </format>
    <args/>
</payloadFactory>
 
<salesforce.upsert>
    <allOrNone>0</allOrNone>
    <allowFieldTruncate>0</allowFieldTruncate>
    <externalId>Id</externalId>
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.upsert>
```
**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_upsert.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_upsert.htm)

#### Searching records

To search for records, use salesforce.search and specify the search string. If you already know the record IDs, use retrieve instead. 

**search**
```xml
<salesforce.search configKey="MySFConfig">
    <searchString>FIND {map*} IN ALL FIELDS RETURNING Account (Id, Name), Contact, Opportunity, Lead</searchString>
</salesforce.search>
```
**Properties**
* searchString: The SQL query to use to search for records.

**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_search.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_search.htm)

#### Querying records

To retrieve data from an object, use salesforce.query and specify the following properties. If you already know the record IDs, you can use retrieve instead. 

---
**Note :**
If you want your search results to include deleted records that are available in the Recycle Bin, use salesforce.queryAll in place of salesforce.query.
---

**query**
```xml
<salesforce.query configKey="MySFConfig">
    <batchSize>200</batchSize>
    <queryString>select id,name from Account</queryString>
</salesforce.query>
```

**Properties**
* batchSize: The number of records to return. If more records are available than the batch size, you can use the queryMore operation to get additional results.
* queryString: The SQL query to use to search for records.

**Sample request**

Following is a sample configuration to query records. It also illustrates the use of queryMore operation to get additional results:

```xml
<salesforce.query>
    <batchSize>200</batchSize>
    <queryString>select id,name from Account</queryString>
</salesforce.query>
<!-- Execute the following to get the other batches -->
<iterate xmlns:sfdc="http://wso2.org/salesforce/adaptor" continueParent="true" expression="//sfdc:iterator">
    <target>
        <sequence>
            <salesforce.queryMore>
                <batchSize>200</batchSize>
            </salesforce.queryMore>
        </sequence>
    </target>
</iterate>
```
**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_query.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_query.htm)

#### Retrieving specific records

If you know the IDs of the records you want to retrieve, use salesforce.retrieve and specify the following properties. If you do not know the record IDs, use query instead.

**retrieve**
```xml
<salesforce.retrieve configKey="MySFConfig">
    <fieldList>id,name</fieldList>
    <objectType>Account</objectType>
    <objectIDS xmlns:sfdc="sfdc">{//sfdc:sObjects}</objectIDS>
</salesforce.retrieve>
```

**Properties**
* fieldList: A comma-separated list of the fields you want to retrieve from the records.
* objectType: The object type of the records.
* sobjects: XML representation of the records to retrieve.

**Sample request**

Given below is a sample request that can be handled by the retrieve operation.

```xml
<payloadFactory>
   <format>
      <sfdc:sObjects xmlns:sfdc="sfdc">
         <sfdc:Ids>0019000000aaMkK</sfdc:Ids>
         <sfdc:Ids>0019000000aaMjl</sfdc:Ids>
      </sfdc:sObjects>
   </format>
   <args/>
</payloadFactory>
 
<salesforce.retrieve configKey="MySFConfig">
    <fieldList>id,name</fieldList>
    <objectType>Account</objectType>
    <objectIDS xmlns:sfdc="sfdc">{//sfdc:sObjects}</objectIDS>
</salesforce.retrieve>
```
**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_retrieve.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_retrieve.htm)

#### Deleting records

To delete one or more records, use salesforce.delete and specify the following properties. 

**delete**
```xml
<salesforce.delete configKey="MySFConfig">
   <allOrNone>0</allOrNone>
   <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.delete>
```

**Properties**
* allOrNone: Whether to rollback changes if an object fails (see Common Parameters).
* sobjects: XML representation of the records to delete, as shown in the following example.

**Sample request**

Given below is a sample request that can be handled by the delete operation.

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
 
<salesforce.delete>
   <allOrNone>0</allOrNone>
   <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.delete>
```
**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_delete.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_delete.htm)

#### Restoring records

To restore records that were previously deleted, use salesforce.undelete and specify the following properties. 

**undelete**
```xml
<salesforce.undelete configKey="MySFConfig">
    <allOrNone>0</allOrNone>
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.undelete>
```

**Properties**
* allOrNone: Whether to rollback changes if an object fails (see Common Parameters).
* sobjects: XML representation of the records to restore, as shown in the following example.

**Sample request**

Given below is a sample request that can be handled by the undelete operation.

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
 
<salesforce.undelete>
    <allOrNone>0</allOrNone>
    <sobjects xmlns:sfdc="sfdc">{//sfdc:sObjects}</sobjects>
</salesforce.undelete>
```
**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_undelete.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_undelete.htm)

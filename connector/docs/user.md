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

**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_getuserinfo.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_getuserinfo.htm)

#### Changing user password

To change the user password by specifying the password, use salesforce.setPassword as follows:

**setPassword**
```xml
<salesforce.setPassword configKey="MySFConfig">
    <userId>user@wso2.com</userId>
    <password>abc123</password>
</salesforce.setPassword>
```

To change the user password using a system generated password, use salesforce.resetPassword as follows:

**resetPassword**
```xml
<salesforce.resetPassword configKey="MySFConfig">
    <userId>user@wso2.com</userId>
</salesforce.resetPassword>
```
**Properties**
* userId: The user's Salesforce ID.
* password: If using setPassword, the new password to assign to the user.

**Related Salesforce documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_setpassword.htm](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_setpassword.htm)


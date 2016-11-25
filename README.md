### Salesforce ESB Connector

The Salesforce connector allows you to access the Salesforce API through WSO2 ESB. Salesforce is  a web-based service
that allows organizations to manage contact relationship management (CRM) data. You can use the Salesforce connector to create, query,
retrieve, update, and delete records in your organization's Salesforce data. The connector uses the Salesforce SOAP API to interact with Salesforce.

### Build

mvn clean install

### How You Can Contribute
You can create a third party connector and publish in WSO2 Connector Store.

https://docs.wso2.com/display/ESBCONNECTORS/Creating+a+Third+Party+Connector+and+Publishing+in+WSO2+Connector+Store

### Dynamic Schema Generation part for Salesforce ESB Connector for Dev-Tooling

This is the ESB Connector Feature for the Developer Studio. This provides tooling support
for Dynamic Schema Generation for dynamic Connector operations on Developer Studio.

### Build

Go to "{Connector_Home}/dynamic-schema"

mvn clean install

After that go to Location "{Connector_Home}/dynamic-schema/repository/main/target" then you can find the p2 repo as wso2esb-tooling-connector-salesforce-dynamicschema-generation-p2-1.0.0-SNAPSHOT.zip

Install the p2 repository in WSO2 dev-tooling support eclipse by following the below url.

https://docs.wso2.com/display/ESBCONNECTORS/Install+dynamic+schema+generation+feature+of+salesforce+soap+connector+to+ESB+Developer+Studio+Tooling
### Salesforce ESB Connector

The Salesforce connector allows you to access the Salesforce API through WSO2 ESB. Salesforce is  a web-based service
that allows organizations to manage contact relationship management (CRM) data. You can use the Salesforce connector to create, query,
retrieve, update, and delete records in your organization's Salesforce data. The connector uses the Salesforce SOAP API to interact with Salesforce.

### Build

mvn clean install

### How You Can Contribute
You can create a third party connector and publish in WSO2 Store.

https://docs.wso2.com/display/ESBCONNECTORS/Creating+and+Publishing+a+Third+Party+Connector

### Dynamic Schema Generation part for Salesforce ESB Connector for Dev-Tooling

This is the ESB Connector Feature for the Developer Studio. This provides tooling support
for Dynamic Schema Generation for dynamic Connector operations on Developer Studio.

### Build

Go to "{Connector_Home}/dynamic-schema"

mvn clean install

After that go to Location "{Connector_Home}/dynamic-schema/repository/main/target" then you can find the p2 repo as wso2esb-tooling-connector-salesforce-dynamicschema-generation-p2-2.0.0-SNAPSHOT.zip

Install the p2 repository in WSO2 dev-tooling support eclipse by following the below url.

https://docs.wso2.com/display/ESBCONNECTORS/Generating+Dynamic+Schemas+for+Connectors

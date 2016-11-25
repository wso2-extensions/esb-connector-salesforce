### Dynamic Schema Generation part for Salesforce ESB Connector for Dev-Tooling

This is the ESB Connector Feature for the Developer Studio. This provides tooling support
for Dynamic Schema Generation for dynamic Connector operations on Developer Studio.

### Build

Go to "{Connector_Home}/dynamicschemapart"

mvn clean install

After that go to Location "{Connector_Home}/dynamicschemapart/repository/main/target" then you can find the p2 repo as wso2-tooling-connector-p2-1.0.0-SNAPSHOT.zip

Install the p2 repository in WSO2 dev-tooling support in eclipse by following the below url.

https://docs.wso2.com/display/ESBCONNECTORS/Install+dynamic+schema+generation+feature+of+salesforce+soap+connector+to+ESB+Developer+Studio+Tooling
# Salesforce EI Connector

The Salesforce [Connector](https://docs.wso2.com/display/EI640/Working+with+Connectors) allows you to access the [Salesforce SOAP API](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_quickstart_intro.htm) through WSO2 Enterprise Integrator (WSO2 EI). Salesforce is  a web-based service
that allows organizations to manage contact relationship management (CRM) data. You can use the Salesforce connector to create, query,
retrieve, update, and delete records in your organization's Salesforce data. The connector uses the Salesforce SOAP API to interact with Salesforce.


## Compatibility

| Connector version | Supported Salesforce SOAP API version | Supported WSO2 ESB/EI version |
| ------------- | ------------- | ------------- |
| [2.0.2](https://github.com/wso2-extensions/esb-connector-salesforce/tree/wso2-esb-connector-salesforce-dynamic-schema-2.0.2) | 42.0 | ESB 4.8.1, ESB 4.9.0, ESB 5.0.0, EI 6.1.1, EI 6.2.0, EI 6.3.0, EI 6.4.0  |
| [2.0.1](https://github.com/wso2-extensions/esb-connector-salesforce/tree/wso2-esb-connector-salesforce-dynamic-schema-2.0.1) | 42.0 | ESB 4.8.1, ESB 4.9.0, ESB 5.0.0, EI 6.1.1  |

## Getting started

#### Download and install the connector

1. Download the connector from [WSO2 Store](https://store.wso2.com/store/assets/esbconnector/details/fbb433b5-4d74-4064-84c2-e4b23c531aa2) by clicking the Download Connector button.
2. Then you can follow this [Documentation](https://docs.wso2.com/display/EI640/Working+with+Connectors+via+the+Management+Console) to add and enable the connector via the Management Console in your EI instance.
3. For more information on using connectors and their operations in your EI configurations, see [Using a Connector](https://docs.wso2.com/display/EI640/Using+a+Connector).
4. If you want to work with connectors via EI tooling, see [Working with Connectors via Tooling](https://docs.wso2.com/display/EI640/Working+with+Connectors+via+Tooling).

#### Configuring the connector operations

To get started with Salesforce connector and their operations, see [Configuring Salesforce Operations](connector/docs/config.md).


## Building From the Source

If you want to build Salesforce connector from the source code:

1. Get a clone or download the source from [Github](https://github.com/wso2-extensions/esb-connector-salesforce).
2. Run the following Maven command from the `esb-connector-salesforce/connector` directory: `mvn clean install`.
3. Salesforce connector zip will be created under `esb-connector-salesforce/connector/target` directory


## Dynamic Schema Generation part for Salesforce ESB Connector for Dev-Tooling

This is the ESB Connector Feature for the Developer Studio. This provides tooling support
for Dynamic Schema Generation for dynamic Connector operations on Developer Studio.

### Building the Dynamic Schema Generation From the Source

Go to "{Connector_Home}/dynamic-schema"

mvn clean install

After that go to Location "{Connector_Home}/dynamic-schema/repository/main/target" then you can find the p2 repo as wso2esb-tooling-connector-salesforce-dynamicschema-generation-p2-2.0.0-SNAPSHOT.zip

Install the p2 repository in WSO2 dev-tooling support eclipse by following the below url.

https://docs.wso2.com/display/ESBCONNECTORS/Generating+Dynamic+Schemas+for+Connectors


## How You Can Contribute

As an open source project, WSO2 extensions welcome contributions from the community.
Check the [issue tracker](https://github.com/wso2-extensions/esb-connector-salesforce/issues) for open issues that interest you. We look forward to receiving your contributions.

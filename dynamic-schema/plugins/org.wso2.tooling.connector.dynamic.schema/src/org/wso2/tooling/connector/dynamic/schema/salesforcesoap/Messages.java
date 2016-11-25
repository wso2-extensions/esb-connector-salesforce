/*
 *  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.tooling.connector.dynamic.schema.salesforcesoap;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.wso2.tooling.connector.dynamic.schema.salesforcesoap.messages"; //$NON-NLS-1$
	public static String SchemaKeyEditorDialog_SelectConnectorLoginUsername;
	public static String SchemaKeyEditorDialog_SelectConnectorLoginPassword;
	public static String SchemaKeyEditorDialog_SelectConnectorLoginSecurityToken;
	public static String SchemaKeyEditorDialog_SelectConnectorLoginLoginURL;
	public static String SchemaKeyEditorDialog_SelectConnectorLogin;
	public static String SchemaKeyEditorDialog_SelectSObject;
	public static String SchemaKeyEditorDialog_Query;
	public static String SchemaKeyEditorDialog_Retrieve;
	public static String SchemaKeyEditorDialog_Search;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
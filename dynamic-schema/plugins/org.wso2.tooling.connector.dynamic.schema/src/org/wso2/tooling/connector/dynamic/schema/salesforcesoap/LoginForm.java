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

public class LoginForm {
	public static String userName, password, loginUrl, securityToken;
	
	private static LoginForm loginForm = new LoginForm();
	
	private LoginForm(){
	}
	   
	public static LoginForm getInstance() {
		return loginForm;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		LoginForm.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		LoginForm.password = password;
	}

	public String getLoginURL() {
		return loginUrl;
	}

	public void setLoginURL(String loginUrl) {
		LoginForm.loginUrl = loginUrl;
	}

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		LoginForm.securityToken = securityToken;
	}
}

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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class GetTagValueFromXML {
	private static GetTagValueFromXML getTagValueFromXML = new GetTagValueFromXML();

	private GetTagValueFromXML() {
	}

	public static GetTagValueFromXML getInstance() {
		return getTagValueFromXML;
	}

	/**
	 * 
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Document loadXML(String xml) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputSource insrc = new InputSource(new StringReader(xml));

		return db.parse(insrc);
	}

	/**
	 * Get the value inside the particular tagName.
	 * 
	 * @param xml
	 * @param tagName
	 * @return
	 * @throws Exception
	 */
	public static List<String> getTagValueFromXml(String xml, String tagName) throws Exception {
		Document xmlDoc = loadXML(xml);
		xmlDoc.getDocumentElement().normalize();

		NodeList nodeList = xmlDoc.getElementsByTagName(tagName);
		List<String> ids = new ArrayList<String>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node x = nodeList.item(i);
			ids.add(x.getFirstChild().getNodeValue());
		}
		return ids;
	}

	/**
	 * Returning the List of value of the name tag.
	 * 
	 * @param xml
	 * @param tagName
	 * @return
	 * @throws Exception
	 */
	public static List<String> getInnerTagFromXml(String xml, String tagName) throws Exception {
		Document xmlDoc = loadXML(xml);
		xmlDoc.getDocumentElement().normalize();

		NodeList nodeList = xmlDoc.getElementsByTagName(tagName);
		List<String> ids = new ArrayList<String>(nodeList.getLength());

		for (int temp = 0; temp < nodeList.getLength(); temp++) {
			Node nNode = nodeList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				ids.add(eElement.getElementsByTagName("name").item(0).getTextContent());
			}
		}
		return ids;
	}

	/**
	 * Returning the map of the name and type. This map contains the value of
	 * the name tag and it's value of the type tag.
	 * 
	 * @param xml
	 * @param tagName
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getInnerTagsFromXml(String xml, String tagName) throws Exception {
		Document xmlDoc = loadXML(xml);
		xmlDoc.getDocumentElement().normalize();

		NodeList nodeList = xmlDoc.getElementsByTagName(tagName);
		Map<String, String> ids = new HashMap<String, String>();

		for (int temp = 0; temp < nodeList.getLength(); temp++) {
			Node nNode = nodeList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				ids.put(eElement.getElementsByTagName("name").item(0).getTextContent(),
						eElement.getElementsByTagName("type").item(0).getTextContent());
			}
		}
		return ids;
	}
}

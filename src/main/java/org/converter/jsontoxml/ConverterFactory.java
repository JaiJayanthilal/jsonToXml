package org.converter.jsontoxml;

import java.io.File;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONException;
import org.json.JSONTokener;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.wnameless.json.flattener.JsonFlattener;

public class ConverterFactory implements XMLJSONConverterI {


	public String strResult = null;

	public static void main(String[] args) throws Exception {

		ConverterFactory cf = new ConverterFactory();
		String inputFile = " ";
		String outputFile = " ";

		for (int i = 0; i < args.length; i++) {
			inputFile = args[0];
			outputFile = args[1];
		}

		cf.convertJSONtoXML(inputFile, outputFile);

	}

	public void convertJSONtoXML(String inputFile, String outputFile) throws Exception {

		String data = "";
		String j = "";

		System.out.println(inputFile);
		System.out.println(outputFile);
		String newline = System.getProperty("line.separator");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.newDocument();
		Set<String> duplicate = new HashSet<String>();
		try {

			File myObj = new File(inputFile);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				data = myReader.nextLine();
				j = j + data + newline;
			}
			System.out.println(j);

			System.out.println("Flattened");

			System.out.println(JsonFlattener.flatten(j).replaceAll(",", "\n"));
			JSONObjectLinkedHashMap jo = new JSONObjectLinkedHashMap(JsonFlattener.flatten(j));

			Element root = doc.createElement("object");
			doc.appendChild(root);

			for (Map.Entry<String, Object> each : jo.toMap().entrySet()) {
				System.out.println(each.getKey() + "***" + each.getValue());
				String[] s = each.getKey().split("\\.");

				for (int u = 0; u < s.length; u++) {
					if ((u < s.length - 1) && (duplicate.add(s[u]))) {
						Element element = null;
						if (!s[u].contains("[")) {
							element = doc.createElement("object");
						}

						if (s[u].contains("[")) {
							System.out.println("Value to be checked For ==> " + each.getKey());
							System.out.println("check");
							if (duplicate.add(s[u].substring(0, s[u].indexOf('[')))) {
								element = doc.createElement("array");
								// root.appendChild(element);

								Attr attrForArrayElementParent = doc.createAttribute("name");
								attrForArrayElementParent.setValue(s[u].substring(0, s[u].indexOf('[')));
								element.setAttributeNode(attrForArrayElementParent);
								element.setIdAttributeNode(attrForArrayElementParent, true);
							} else {
								element = doc.getElementById(s[u].substring(0, s[u].indexOf('[')));
							}
						}

						root.appendChild(element);
						Attr attr = doc.createAttribute("name");
						attr.setValue(s[u]);
						element.setAttributeNode(attr);
						element.setIdAttributeNode(attr, true);

						if (u != 0) {
							Element parent = doc.getElementById(s[u - 1]);
							parent.appendChild(element);
						}
						//

					}
					if ((u == s.length - 1) && (duplicate.add(s[u]))) {
						Element parent;
						System.out
								.println((each.getValue() == null) ? null : each.getValue().getClass().getSimpleName());
						Element element = doc.createElement(
								(each.getValue() == null) ? "null" : each.getValue().getClass().getSimpleName());
						if (u == 0) {
							if (!s[u].contains("[")) {
								root.appendChild(element);
							}
						} else {
							parent = doc.getElementById(s[u - 1]);
							if (parent != null) {
								System.out.println("---> value" + parent.getTagName());
								String jointString = "." + s[u];
								if (each.getKey().endsWith(jointString) && !s[u].contains("[")
										&& !s[u - 1].contains("[")) {
									parent.appendChild(element);
								}

								else if (each.getKey().endsWith(jointString) && !s[u].contains("[")
										&& s[u - 1].contains("][")) {

									Element obj = doc.createElement("object");
									obj.appendChild(element);
									Element arr = doc.createElement("array");
									arr.appendChild(obj);
									parent.appendChild(arr);
								}

								else if (each.getKey().endsWith(jointString) && !s[u].contains("[")
										&& s[u - 1].contains("[") && !s[u - 1].contains("][")) {

									Element obj = doc.createElement("object");
									obj.appendChild(element);
									/*
									 * Element arr = doc.createElement("array"); arr.appendChild(obj);
									 */
									parent.appendChild(obj);
								}

								else if (!s[u].contains("[")) {
									parent.appendChild(element);
								}
							}
						}
						Attr attr = doc.createAttribute("name");
						attr.setValue(s[u]);
						element.setAttributeNode(attr);
						element.setIdAttributeNode(attr, true);
						element.setTextContent((each.getValue() == null) ? null : each.getValue().toString());
						if (s[u].contains("[")) {
							Element arrayElementParent = null;
							if (duplicate.add(s[u].substring(0, s[u].indexOf('[')))) {
								arrayElementParent = doc.createElement("array");
								root.appendChild(arrayElementParent);

								Attr attrForArrayElementParent = doc.createAttribute("name");
								attrForArrayElementParent.setValue(s[u].substring(0, s[u].indexOf('[')));
								arrayElementParent.setAttributeNode(attrForArrayElementParent);
								arrayElementParent.setIdAttributeNode(attrForArrayElementParent, true);
							}

							arrayElementParent = doc.getElementById(s[u].substring(0, s[u].indexOf('[')));

							Element arrayElementChild = doc.createElement(
									(each.getValue() == null) ? "null" : each.getValue().getClass().getSimpleName());
							if (null != arrayElementParent) {
								if (!each.getKey().contains("]\\.")) {
									arrayElementParent.appendChild(arrayElementChild);
								} else if (each.getKey().contains("]\\.")) {
									Element NestedArrayObject = doc.createElement("object");
									NestedArrayObject.appendChild(arrayElementChild);
									arrayElementParent.appendChild(NestedArrayObject);
								}

							}

							arrayElementChild
									.setTextContent((each.getValue() == null) ? null : each.getValue().toString());
							if (u == 0) {
								root.appendChild(arrayElementParent);
							} else {
								parent = doc.getElementById(s[u - 1]);
								if (parent != null) {
									parent.appendChild(arrayElementParent);
								}
							}
						}
					}
				}

			}

			strResult = xmlAsString(doc, duplicate, outputFile);
			System.out.println("Final");
			System.out.println("  ");
			System.out.println(strResult);

			myReader.close();
		} catch (Exception e) {

			try {
				JSONTokener jt = new JSONTokener(j);
				// System.out.println("jt" + jt.nextValue().getClass().getSimpleName());
				// String val = jt.nextValue().getClass().getSimpleName();
				// String realValue =
				// Element docElementForSingleTokens =
				// doc.createElement(jt.nextValue().getClass().getSimpleName());
				Element docElementForSingleTokens = doc.createElement(jt.nextValue().getClass().getSimpleName());
				// jt.back();
				if (!docElementForSingleTokens.getTagName().equals("Null")) {
					docElementForSingleTokens.setTextContent(j.trim());
				}
				doc.appendChild(docElementForSingleTokens);
				strResult = xmlAsString(doc, duplicate, outputFile);

				System.out.println("Final");
				System.out.println("  ");
				System.out.println(strResult);
			} catch (JSONException ex2) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}

		}

		// return new ConverterFactory();
	}

	public String xmlAsString(Document doc, Set<String> duplicate, String outputFile) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(doc);

		Set<String> arrayElement = new HashSet<String>();
		for (String each : duplicate) {
			if (each.contains("[")) {
				arrayElement.add(each);
			}
		}

		for (String each : arrayElement) {
			Element rename = doc.getElementById(each);
			if (null != rename) {
				if (rename.getAttribute("name").contains("[")) {
					// rename = doc.getElementById(each.substring(0, each.indexOf('[')));
					rename.setAttribute("name", rename.getAttribute("name").substring(0, each.indexOf('[')));
				}

			} else {
				continue;
			}
		}

		StreamResult result = new StreamResult(new File(outputFile));
		transformer.transform(source, result);
		StreamResult consoleResult = new StreamResult(System.out);
		transformer.transform(source, consoleResult);

		StringWriter writer = new StringWriter();
		StreamResult resultStream = new StreamResult(writer);

		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer2 = tFactory.newTransformer();
		transformer2.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer2.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer2.transform(source, resultStream);

		return writer.toString();
	}
}

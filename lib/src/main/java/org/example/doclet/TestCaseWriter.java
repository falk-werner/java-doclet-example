package org.example.doclet;

import java.io.File;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestCaseWriter {

    Document doc;
    Element testCases;

    public TestCaseWriter() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.doc = builder.newDocument();

        this.testCases = this.doc.createElement("testsuite");
        this.doc.appendChild(this.testCases);
    }

    public void add(String name, String className, String description) {
        Element testCase = this.doc.createElement("testcase");
        this.testCases.appendChild(testCase);

        testCase.setAttribute("name", name);
        testCase.setAttribute("classname", className);

        Element descriptionNode = this.doc.createElement("description");
        testCase.appendChild(descriptionNode);
        descriptionNode.setTextContent(description);
    }

    public void save(File file) throws Exception {
        TransformerFactory tranformerFactory = TransformerFactory.newInstance();
        Transformer transformer = tranformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        FileWriter writer = new FileWriter(file);
        StreamResult streamResult = new StreamResult(writer);

        transformer.transform(source, streamResult);
    }

}

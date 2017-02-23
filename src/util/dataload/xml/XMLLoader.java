package util.dataload.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLLoader {

	protected static Document getDocTreeForFile(String xmlFilePath) {
		File xmlFile = new File(xmlFilePath);
		DocumentBuilder builder = getBuilder();
		try {
			assert builder != null;
			return builder.parse(xmlFile);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static DocumentBuilder getBuilder() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			return factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
}

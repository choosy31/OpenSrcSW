package homeworkweek3;


import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
public class makeCollection {
	public Document makeDoc() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
	        document.setXmlStandalone(true);
	        return document;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Document makeBody(Document document, String directory) {
		File path = new File("src/"+ directory);
        File[] fileList = path.listFiles();
       
        int Num = fileList.length;

        Element docs = document.createElement("docs");
        document.appendChild(docs);
        
        for(int num = 0; num < Num; num++) {
			try {
	     
	        	Element doc = document.createElement("doc");
	            docs.appendChild(doc);
	 
	            String number = Integer.toString(num);
	            doc.setAttribute("id", number);
	            num = Integer.parseInt(number);
	            
	            Scanner myReader;
				myReader = new Scanner(fileList[num]);
				String responseBody = myReader.useDelimiter("\\A").next();
				responseBody = responseBody.replace("</p>\r\n            <p>", "");
	            responseBody = responseBody.replaceAll("            <p>", "");
	            responseBody = responseBody.replace("</p>\r\n    ", "");
	          
	            Element title = document.createElement("title");
	            title.appendChild(document.createTextNode(responseBody.substring(responseBody.indexOf("<title>") + 7,responseBody.indexOf("</title>"))));
	            doc.appendChild(title);
	            
	            Element body = document.createElement("body");
	            body.appendChild(document.createTextNode(responseBody.substring(responseBody.indexOf("<div id=\"content\">") + 20,responseBody.indexOf("</div>"))));
	            doc.appendChild(body);
		
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return document;
	}
	public void makeXml(Document document, String name) {
        try {
        	 TransformerFactory transformerFactory = TransformerFactory.newInstance();
        	 
             Transformer transformer = transformerFactory.newTransformer();
             transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
             transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
             transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
             
             DOMSource source = new DOMSource(document);
             StreamResult result = new StreamResult(new FileOutputStream(new File("src/homeworkweek3/"+ name)));
  
             transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package test;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class genSnippet {

	public static void main (String[] args) {
		String input = args[1];
		String ques = args[3];
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
	        document.setXmlStandalone(true);
	        document.createTextNode("라면 밀가루 달걀 밥 생선/n");
	        document.createTextNode("라면 물 소금 반죽/n");
	        document.createTextNode("첨부 봉지면 인기/n");
	        document.createTextNode("초밥 라면 밥물 채소 소금/n");
	        document.createTextNode("초밥 종류 활어/n");
	      
	  
	        
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        	 
             Transformer transformer = transformerFactory.newTransformer();
             
             transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
             transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
             
             DOMSource source = new DOMSource(document);
             StreamResult result = new StreamResult(new FileOutputStream(new File("src/test/"+ input)));
  
             transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
}

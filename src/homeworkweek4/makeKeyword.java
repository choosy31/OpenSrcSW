package homeworkweek4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class makeKeyword {
	public Document kkma(Document document, String directory) {
		File path = new File("src/homeworkweek4/"+ directory);
        
		Scanner myReader;
		try {
			myReader = new Scanner(path);
			String responseBody = myReader.useDelimiter("\\A").next();
			
			Element docs = document.createElement("docs");
	        document.appendChild(docs);
	        
			for(int num = 0; num < 5; num++) {
				Element doc = document.createElement("doc");
	            docs.appendChild(doc);
	 
	            String number = Integer.toString(num);
	            doc.setAttribute("id", number);
	            num = Integer.parseInt(number);
	            
				Element title = document.createElement("title");
				String sub = responseBody.substring(responseBody.indexOf("<title>", num+1) + 7 ,responseBody.indexOf("</title>", num+1));
		        title.appendChild(document.createTextNode(sub));
		        doc.appendChild(title);
		        responseBody = responseBody.replaceAll(responseBody.substring(responseBody.indexOf("<title>"),responseBody.indexOf("</title>")+8),"");
		       		        
		        String str = responseBody.substring(responseBody.indexOf("<body>") + 6,responseBody.indexOf("</body>"));
		        String kk = "";
		        KeywordExtractor ke = new KeywordExtractor();
		        KeywordList kl = ke.extractKeyword(str, true);
		        for(int i=0;i< kl.size();i++) {
		        	Keyword kwrd = kl.get(i);
		        	kk += kwrd.getString()+ ":"+ kwrd.getCnt()+ "#";
		        }
		        responseBody = responseBody.replace(str, kk);
		        Element body = document.createElement("body");
		        body.appendChild(document.createTextNode(responseBody.substring(responseBody.indexOf("<body>", num+1) + 6,responseBody.indexOf("</body>", num+1))));
		        doc.appendChild(body);
		        responseBody = responseBody.replaceAll(responseBody.substring(responseBody.indexOf("<body>"),responseBody.indexOf("</body>")+7),"");	
			}
		} catch (FileNotFoundException e1) {
	
			e1.printStackTrace();
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
             StreamResult result = new StreamResult(new FileOutputStream(new File("src/homeworkweek4/index.xml")));
  
             transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

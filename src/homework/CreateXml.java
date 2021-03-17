package homework;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
 

public class CreateXml{
 
    public static void main (String[] args) {
 
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
            Document document = docBuilder.newDocument();
            document.setXmlStandalone(true); 
            
            File path = new File("src/data");
            File[] fileList = path.listFiles();
            int Num = fileList.length;
 
            Element docs = document.createElement("docs");
            document.appendChild(docs);
            
            for(int num = 0; num < Num; num++) {
            	Scanner myReader = new Scanner(fileList[num]);
            	String responseBody = myReader.useDelimiter("\\A").next();
            	 responseBody = responseBody.replace("</p>\r\n            <p>", "");
                 responseBody = responseBody.replaceAll("            <p>", "");
                 responseBody = responseBody.replace("</p>\r\n    ", "");
         
            	Element doc = document.createElement("doc");
                docs.appendChild(doc);
     
                String number = Integer.toString(num);
                doc.setAttribute("id", number);
                num = Integer.parseInt(number);
              
                Element title = document.createElement("title");
                title.appendChild(document.createTextNode(responseBody.substring(responseBody.indexOf("<title>") + 7,responseBody.indexOf("</title>"))));
                doc.appendChild(title);
                
                Element body = document.createElement("body");
                body.appendChild(document.createTextNode(responseBody.substring(responseBody.indexOf("<div id=\"content\">") + 20,responseBody.indexOf("</div>"))));
                doc.appendChild(body);
//              System.out.println(responseBody);  
            }

 
           
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
 
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
            
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(new File("src/homework/collection.xml")));
 
            transformer.transform(source, result);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
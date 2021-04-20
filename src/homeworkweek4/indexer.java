package homeworkweek4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class indexer {
	public void makeHash(String[][] splt, float[][] id) {
		
		try {
		FileOutputStream fileStream = new FileOutputStream("src/homeworkweek4/index.post");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
		
		int n=0;
		for(int i=0;i<5;i++) {
			n += splt[i].length/2;
			for(int j=0;j<splt[i].length/2;j++) {
				int m=0;
				for(int x=1+i;x<5;x++) {
					for(int y=0;y<splt[x].length/2;y++) {
						if(splt[i][j*2]==splt[x][y*2]) {
							m++;
						}
					}
				}
				for(int k=1; k<5;k++) {
					if(k*(k+1)/2 == m) {
						m = k;
					}
				}
				n = n-m;
			}
		}
		
		int z=0;
		String[] k = new String[n];
		for(int i=0;i<5;i++) {
			for(int j=0;j<splt[i].length/2;j++) {
				String a = splt[i][j*2];
				if(a.equals(k[i*5+j]) != true) {
					k[z] = a;
					z++;
				}
			}
		}

		HashMap FoodMap = new HashMap();
		
    	for(int i=0; i<id.length;i++) {
    		FoodMap.put(k[i], id[i][0]+" "+id[i][1]+" "+id[i][2]+" "+id[i][3]+" "+id[i][4]+" "+id[i][5]+" "+id[i][6]+" "+id[i][7]+" "+id[i][8]+" "+id[i][9]);
    	}
        	
		objectOutputStream.writeObject(FoodMap);
		objectOutputStream.close();
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	public String[][] List(String directory) {
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse("src/homeworkweek4/"+ directory);
			doc.getDocumentElement().normalize();

			
			String[] str = new String[5];
			String[][] splt = new String[5][];
			
			NodeList nList = doc.getDocumentElement().getChildNodes();
			String str1 = "";
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
		            int c = Integer.parseInt(eElement.getAttribute("id"));
		            str[c]= eElement.getElementsByTagName("body").item(0).getTextContent().replace("#",":");
				    splt[c] = str[c].split(":");
				 }
			}
			
			return splt;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public float[][] summon(String[][] splt) {
		int n=0;
		for(int i=0;i<5;i++) {
			n += splt[i].length/2;
			for(int j=0;j<splt[i].length/2;j++) {
				int m=0;
				for(int x=1+i;x<5;x++) {
					for(int y=0;y<splt[x].length/2;y++) {
						if(splt[i][j*2]==splt[x][y*2]) {
							m++;
						}
					}
				}
				for(int k=1; k<5;k++) {
					if(k*(k+1)/2 == m) {
						m = k;
					}
				}
				n = n-m;
			}
		}
		
		int z=0;
		String[] k = new String[n];
		for(int i=0;i<5;i++) {
			for(int j=0;j<splt[i].length/2;j++) {
				String a = splt[i][j*2];
				if(a.equals(k[i*5+j]) != true) {
					k[z] = a;
					z++;
				}
			}
		}
		
		float[][] decon = new float[n][10];
        for(int i=0;i<n;i++) {
        	for(int x=0;x<5;x++) {
        		for(int y=0;y<splt[x].length/2;y++) {
        			if(k[i].equals(splt[x][y*2])) {
        				String a =  splt[x][y*2+1];
        				decon[i][x*2+1] = Float.parseFloat(a);
        			}
        		}
        	}
        	float num =0;
        	for(int x=0;x<5;x++) {
        		for(int y=0;y<splt[x].length/2;y++) {
        			if(k[i].equals(splt[x][y*2])) {
                		num++;
                	}
        		}
            	decon[i][x*2] = x;
        	}decon[i][0]= num;
        }
        
		return decon;
	}
	public float[][] returnflo(float[][] decon){
		
		for(int i=0;i<decon.length;i++) {
			for(int j=0;j<5;j++) {
				float a = decon[i][j*2+1];
				float b = decon[i][0];
				float calcu =  (float) (a * Math.log(5/b));
				decon[i][2*j+1] = (float)((double)Math.round(calcu*100)/100);
			}
			decon[i][0] = 0;
		}
    	return decon;
	} 
	public void makeRead() {
		try {
			FileInputStream fileStream = new FileInputStream("src/homeworkweek4/index.post");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
			
			Object object = objectInputStream.readObject();
			objectInputStream.close();
			
			System.out.println("읽어올 객체의 type : " +object.getClass());
			
			HashMap hashMap = (HashMap)object;
			Iterator<String> it = hashMap.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				String value = (String) hashMap.get(key);
				System.out.println(key + ": \t"+ value);
			}
			
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
}

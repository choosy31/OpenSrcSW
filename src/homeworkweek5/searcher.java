package homeworkweek5;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class searcher {

	public void CalcSim(String directory, String str) {
		
		KeywordExtractor ke = new KeywordExtractor();
        KeywordList kl = ke.extractKeyword(str, true);
		float[] similar = new float[5];
		try {
			FileInputStream fileStream = new FileInputStream("src/homeworkweek5/"+directory);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
			
			Object object = objectInputStream.readObject();
			objectInputStream.close();
			
			HashMap hashMap = (HashMap)object;
			Iterator<String> it = hashMap.keySet().iterator();
			
			float[] kweight = new float[kl.size()];
			String[][] idweight = new String[kl.size()][10];
			for(int i=0;i< kl.size();i++) {
				Keyword kwrd = kl.get(i);
				kweight[i] = kwrd.getCnt();
				while(it.hasNext()) {
					String key = it.next();
					String value = (String) hashMap.get(key);
					if(key.equals(kwrd.getString())) {
						idweight[i] = value.split(" ");
					}
					
				}it = hashMap.keySet().iterator();
			}
			for(int i=0;i<5;i++) {
				for(int j=0;j<kl.size();j++) {
					similar[i] += kweight[j]*Float.parseFloat(idweight[j][i*2+1]);
				}
			}
			for(int i=0;i<5;i++) {
				similar[i] = (float)((double)Math.round(similar[i]*100)/100);
			}
			String[] title = new String[5];
			File path = new File("src/homeworkweek5/collection.xml");
			Scanner myReader = new Scanner(path);
			String responseBody = myReader.useDelimiter("\\A").next();
			for(int num = 0; num < 5; num++) {
				title[num] = responseBody.substring(responseBody.indexOf("<title>") + 7 ,responseBody.indexOf("</title>"));
				responseBody = responseBody.replaceAll(responseBody.substring(responseBody.indexOf("<title>"),responseBody.indexOf("</title>")+8),"");
			}
			
			int[] k = {0,1,2,3,4};
			for(int i=0;i<5;i++) {
				for(int j=i+1;j<5;j++) {
					if(similar[i]<similar[j]) {
						float a = similar[j];
						int b = k[j];
						for(int h=0;h<j;h++) {
							k[h+1] = k[h];
							similar[h+1] = similar[h];
						}
						k[0] = b;
						similar[0] = a;
					}
				}
			}
			for(int i=0;i<3;i++) {
				System.out.println("유사도 "+ (i+1)+"위 문서 title: "+ title[k[i]]+"("+similar[i]+")");
			}
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
	
}

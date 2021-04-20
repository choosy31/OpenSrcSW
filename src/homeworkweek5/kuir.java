package homeworkweek5;


import java.io.File;

import org.w3c.dom.Document;

public class kuir {
 
    public static void main (String[] args) {
    	String dirpath1 = args[1];
    	
//    	makeCollection collection = new makeCollection();
//    	Document doc1 = collection.makeDoc();
//    	doc1 = collection.makeBody(doc1, dirpath1);
//    	collection.makeXml(doc1, "collection.xml");
//    	
//    	makeKeyword keyword = new makeKeyword();
//    	
//    	Document doc2 = collection.makeDoc();
//    	doc2 = keyword.kkma(doc2, dirpath1);
//    	keyword.makeXml(doc2, "index.xml");
    	
//    	indexer index = new indexer();
//    	String[][] list = index.list("index.xml");
//    	float[][] appear = index.summon(list);
//    	float[][] perf = index.returnflo(appear);
//    	index.makeHash(list, perf);
//    	index.makeRead();

    	String ques = args[3];
    	searcher search = new searcher();
    	search.Innerproduct(dirpath1,ques);
    
    }
}
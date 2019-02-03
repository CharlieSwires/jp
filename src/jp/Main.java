package jp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import static java.util.stream.Collectors.*;
import java.util.Map;
import static java.util.Map.Entry.*;

public class Main {
	final static String maryLamb = "Mary had a little lamb, little lamb, little lamb "+
			"Mary had a little lamb "+
			"Whose fleece was white as snow. "+

			"And everywhere that Mary went "+
			"Mary went, Mary went, "+
			"Everywhere that Mary went  "+
			"The lamb was sure to go. "+

			"He followed her to school one day, school one day, school one day "+
			"He followed her to school one day "+
			"Which was against the rules. "+

			"It made the children laugh and play, "+
			"laugh and play, laugh and play, "+
			"It made the children laugh and play, "+
			"To see a lamb at school. "+

			"And so the teacher turned it out, "+
			"turned it out, turned it out, "+
			"And so the teacher turned it out, "+
			"But still it lingered near, "+
			"He waited patiently about, "+
			"patiently about, patiently about, "+
			"He waited patiently about, "+
			"Till Mary did appear. "+

			"\"Why does the lamb love Mary so?\" "+
			"love Mary so?\" love Mary so?\" "+
			"\"Why does the lamb love Mary so?\" "+
			"The eager children cried. "+
			"\"Why, Mary loves the lamb, you know,\" "+
			"lamb, you know, lamb, you know,\" "+
			"\"Why, Mary loves the lamb, you know,\" "+
			"The teacher did reply.";

	private List<String> copyStringToListOfWords(String inputString){
		List<String> result = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<inputString.length();i++) {
			char c = inputString.charAt(i);
			if ((c >= 'A' && c <= 'Z')||
					(c >= 'a' && c <= 'z')) {
				sb.append(c);
				if (i == inputString.length()-1) {
					if (sb.length()>0)result.add(sb.toString());
					sb = new StringBuffer();				
				}
			}else {   
				if (sb.length()>0)result.add(sb.toString());
				sb = new StringBuffer();
			}
			
		}
		return result;
	}

	private HashMap<String,Double> calculateProbabilities(Integer ngen, String phrase){
		List<String> mary = copyStringToListOfWords(maryLamb);
		List<String> search = copyStringToListOfWords(phrase);
		MyHashMap mhm = new MyHashMap();
		for (int i =0;i<mary.size();i++) {
			boolean found = true;
			int offset = 0;
			for(String word:search) {
				if ((i+offset+1)>=mary.size()||!word.equals(mary.get(i+offset++))) {
					found = false;
					break;
				}
			}
			if (found) {
				mhm.put(mary.get(i+offset),1);
			}
		}
		int sum = 0;
		for(int value:mhm.values()) {
			sum += value;
		}
		HashMap<String,Double> result = new HashMap<String,Double>();
		for(String key:mhm.keySet()) {
			result.put(key, mhm.get(key)*1.0/sum);
		}

		return result;

	}
	class MyHashMap extends HashMap<String, Integer>{
		@Override
		public Integer put (String key, Integer value) {
			if(this.get(key)==null)super.put(key, value);
			else {
				super.replace(key, this.get(key)+1);
			}
			return value;

		}


	}
	public static void main(String args[]) {
		Main m = new Main();
		System.out.println(m.copyStringToListOfWords(maryLamb));
		System.out.println(m.copyStringToListOfWords(args[1]));
		Map<String,Double> output = m.calculateProbabilities(Integer.parseInt(args[0]), args[1]);
	    // let's sort this map by values first
	    Map<String, Double> sorted = output
	        .entrySet()
	        .stream()
	        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
	        .collect(
	            toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
	                LinkedHashMap::new));
	 
	    System.out.println("result" + sorted);
		
	}
}

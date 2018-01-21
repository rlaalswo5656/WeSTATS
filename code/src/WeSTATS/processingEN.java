/*
   Copyright {2018} {minjae kim}

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package WeSTATS;

import java.io.File;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class processingEN {
	// Integration method
	public static void Stats(String[] adress) throws Exception{
		String resultTXT = inputPDF(adress);
		Map map = packWord(resultTXT);
		map = removeUnneed(map);
		printMap(map);
	}
	// Integration method + Limit the number of outputs
	public static void Stats(String[] adress, int rank) throws Exception{
		String resultTXT = inputPDF(adress);
		Map map = packWord(resultTXT);
		map = removeUnneed(map);
		printMapCut(map,rank);
	}
	// Integration method returning in JSON format
	public static JSONObject StatsIntoJSON(String[] adress) throws Exception{
		String resultTXT = inputPDF(adress);
		Map map = packWord(resultTXT);
		map = removeUnneed(map);
		return AsJson(map);
	}
	
	// Convert one or more pdf to a string 
	public static String inputPDF(String[] adress) throws Exception{
		String resultTXT = "";
		for(int i=0; i<adress.length; i++) {
			resultTXT += PDFtoString(adress[i]); // Convert PDF to String 1 by 1
		}
		return resultTXT;
	}
	//	Convert PDF to String 1 by 1
	public static String PDFtoString(String adr) throws Exception{
		PDDocument doc = PDDocument.load(new File(adr));
		PDFTextStripper textStripper = new PDFTextStripper();
		
		StringWriter textWriter = new StringWriter();
		textStripper.writeText(doc, textWriter);
		doc.close();
		
		return filtEN(textWriter.toString());
	}
	
	// Remove all but English
	public static String filtEN(String TXT) {
		int CodeEnter = 10; // Meaning line break
		int CodeSpace = 32; // Meaning blank
		int CodeEngUpp[] = { 65, 90 };  // A~Z
		int CodeEngLow[] = { 97, 122 };   // a~z
		String replaceTo = "";
		String resultTXT = "";
		int iCharCode = 0;
		boolean Check = true;
		
		for(int n = 0; n < TXT.length(); n++) {
			iCharCode = (int)TXT.charAt(n);
			
			Check = true;
			
			if(iCharCode >= CodeEngUpp[0] && iCharCode <= CodeEngUpp[1]) {      // Change uppercase to lowercase 
				iCharCode += 32;
			}
					
			// English and spaces only
			if(iCharCode != CodeEnter) {
				if(iCharCode != CodeSpace) {
					if(iCharCode < CodeEngUpp[0] || iCharCode > CodeEngUpp[1]) {
						if(iCharCode < CodeEngLow[0] || iCharCode > CodeEngLow[1]) {
							resultTXT += replaceTo;
							Check = false;
						}
					}
				}
			}else {
				iCharCode = CodeSpace;  // Replace line breaks with blanks
			}
			if(Check) {
				resultTXT += (char)iCharCode;
			}
		}
		return resultTXT;
	}
	// Split string into hash map
	public static Map packWord(String resultTXT) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		Pattern pattern = Pattern.compile("\\s[a-zA-Z]{2,}"); // English words with a length of 2 to 30 characters
		Matcher matcher = pattern.matcher(resultTXT);
		while(matcher.find()) {
			String target = matcher.group().replaceAll(" ", ""); // Remove spaces from found strings
			if(map.containsKey(target)) {  // If the found string already exists in the hash table
				int value = map.get(target);
				map.put(target, ++value);
			}else {                       // If the string found is not in the hash table
				map.put(target, 1);
			}
		}
		return map;
	}
	// Sort the hash map and output it to the console 
	public static void printMap(Map map) {
		Iterator it = sortByValue(map).iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			System.out.print(key + "=");
			System.out.println(map.get(key));
		}
	}
	// Sort the hash map and output it as JSON
	public static JSONObject AsJson(Map map) {
		Iterator it = sortByValue(map).iterator();
		JSONObject jsonobject = new JSONObject();
		while(it.hasNext()) {
			String key = (String) it.next();
			jsonobject.put(key, map.get(key));
		}
		return jsonobject;
	}
	//	Sort method
	public static List sortByValue(final Map map) {
		List<String> list = new ArrayList();
		list.addAll(map.keySet());
		
		Collections.sort(list,new Comparator(){
			public int compare(Object o1,Object o2) {
				Object v1 = map.get(o1);
				Object v2 = map.get(o2);
				
				return ((Comparable) v2).compareTo(v1);
			}
		});
		return list;
	}
	// Eliminate unnecessary
	public static Map removeUnneed(Map map) {
		String[] unneed = {"the","to","of","and","in","is","that","for","it","you","as","are","on","with","be","was","they","or","from","their","not","have","this","at","an","we","but","your","more","by","can","he","will","his","our","its","so","were","had","my","if","has","there","her","them","she","into","no","may","us","me","been","those","him","these","dont"};
		for(int i=0; i<unneed.length; i++) {
			map.remove(unneed[i]);
		}
		return map;
	}
	// Only output to the top n 
	public static void printMapCut(Map map, int rank) {
		Iterator it = sortByValue(map).iterator();
		for(int i=0; i<rank; i++) {
			String key = (String) it.next();
			System.out.print(key + "=");
			System.out.println(map.get(key));
		}
	}
}

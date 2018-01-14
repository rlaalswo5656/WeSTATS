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

public class processingEN {
	// 통합 함수 
	public static void weStats(String[] adress) throws Exception{
		String resulutTXT = inputPDF(adress);
		Map map = packWord(resulutTXT);
		printMap(map);
	}
	
	// pdf 복수개를 문자열로 변환 
	public static String inputPDF(String[] adress) throws Exception{
		String resultTXT = "";
		for(int i=0; i<adress.length; i++) {
			resultTXT += PDFtoString(adress[i]);
		}
		return resultTXT;
	}
	// pdf 1개를 문자열로 변환 
	public static String PDFtoString(String adr) throws Exception{
		PDDocument doc = PDDocument.load(new File(adr));
		PDFTextStripper textStripper = new PDFTextStripper();
		
		StringWriter textWriter = new StringWriter();
		textStripper.writeText(doc, textWriter);
		doc.close();
		
		return filtEN(textWriter.toString());
	}
	
	// 문자열중 영어를 제외한 나머지를 전부 제거 후 문자열로 반환 
	public static String filtEN(String TXT) {
		int CodeEnter = 10; // 줄바꿈 
		int CodeSpace = 32; // Spcae
		int CodeEngUpp[] = { 65, 90 };  // A~Z
		int CodeEngLow[] = { 97, 122 };   // a~z
		String replaceTo = "";
		String resultTXT = "";
		int iCharCode = 0;
		boolean Check = true;
		
		for(int n = 0; n < TXT.length(); n++) {
			iCharCode = (int)TXT.charAt(n);
			
			Check = true;
			
			if(iCharCode >= CodeEngUpp[0] && iCharCode <= CodeEngUpp[1]) {      // 대문자를 소문자로 변경 
				iCharCode += 32;
			}
					
			// 스페이스와 영어빼고 나머지 제거 
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
				iCharCode = CodeSpace;  // 줄바꿈을 공백으로 바꿔줌 
			}
			if(Check) {
				resultTXT += (char)iCharCode;
			}
		}
		return resultTXT;
	}
	// 문자열을 영단어 단위로 나눠 해쉬맵으로 반환 
	public static Map packWord(String resultTXT) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		Pattern pattern = Pattern.compile("\\s[a-zA-Z]{2,}"); // 2~30자 길이를 갖는 영단어 정규표현
		Matcher matcher = pattern.matcher(resultTXT);
		while(matcher.find()) {
			String target = matcher.group().replaceAll(" ", ""); // 찾은문자열에서 공백제거 
			if(map.containsKey(target)) {  // 찾은 문자열이 해쉬테이블에 이미 있다면 
				int value = map.get(target);
				map.put(target, ++value);
			}else {                                 // 찾은 문자열이 해쉬테이블에 없다면 
				map.put(target, 1);
			}
		}
		return map;
	}
	// 해쉬맵을 정렬시켜 출력 
	public static void printMap(Map map) {
		Iterator it = sortByValue(map).iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			System.out.println("key = " + key + ", count = " + map.get(key));
		}
	}
	// printMap에 종속. value기준 정렬시키는 역할 
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
}

package WeSTATS;

import java.util.Map;

import WeSTATS.processingEN;

public class main {

	public static void main(String[] args) throws Exception{
		// 분석할 pdf주소를 입력(복수 가능) 
		String[] adress = {"/Users/kimminjae/Downloads/english/2018.pdf", "/Users/kimminjae/Downloads/english/2017.pdf", "/Users/kimminjae/Downloads/english/2016.pdf", "/Users/kimminjae/Downloads/english/2015.pdf", "/Users/kimminjae/Downloads/english/2014.pdf", "/Users/kimminjae/Downloads/english/2013.pdf", "/Users/kimminjae/Downloads/english/2012.pdf", "/Users/kimminjae/Downloads/english/2011.pdf", "/Users/kimminjae/Downloads/english/2010.pdf", "/Users/kimminjae/Downloads/english/2009.pdf", "/Users/kimminjae/Downloads/english/2008.pdf", "/Users/kimminjae/Downloads/english/2007.pdf", "/Users/kimminjae/Downloads/english/2005.pdf"};
		processingEN en = new processingEN();
		
		
		String resultTXT = en.inputPDF(adress); // pdf에서 문자열 추출  
		Map map = en.packWord(resultTXT);  // 영단어를 해쉬맵에 저장 
		en.printMap(map); // 해쉬맵 출력 
		
	}
}

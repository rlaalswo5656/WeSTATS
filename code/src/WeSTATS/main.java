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

import java.util.Map;
import WeSTATS.processingEN;

public class main {

	public static void main(String[] args) throws Exception{
		// Enter the PDF address to be analyzed (multiple)
		String[] adress = {"/Users/kimminjae/Downloads/english/2018.pdf", "/Users/kimminjae/Downloads/english/2017.pdf", "/Users/kimminjae/Downloads/english/2016.pdf", "/Users/kimminjae/Downloads/english/2015.pdf", "/Users/kimminjae/Downloads/english/2014.pdf", "/Users/kimminjae/Downloads/english/2013.pdf", "/Users/kimminjae/Downloads/english/2012.pdf", "/Users/kimminjae/Downloads/english/2011.pdf", "/Users/kimminjae/Downloads/english/2010.pdf", "/Users/kimminjae/Downloads/english/2009.pdf", "/Users/kimminjae/Downloads/english/2008.pdf", "/Users/kimminjae/Downloads/english/2007.pdf", "/Users/kimminjae/Downloads/english/2005.pdf"};
		processingEN en = new processingEN();
		
		// Execute method
		en.Stats(adress);
		
	}
}

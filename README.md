WeSTATS
==============
together. Keyword Pitch Statistics.
![CFR_App](./img/logo.png)

Why
==============
Nowadays, the percentage of last exams is very high in any exam. For example, TOEIC only evaluates business English, frequent spoken words are frequently used. There are many english wordbooks already on the market. But I think it would be better to check it yourself.

Before use
==============
You need to prepare the PDFBox-1.8.13.jar and the json-simple-1.1.1.jar. <br>
They can be downloaded here. <br>
[PDFBox-1.8.13.jar](https://pdfbox.apache.org/download.cgi) <br>
[json-simple-1.1.1.jar](https://code.google.com/archive/p/json-simple/downloads)

Features
==============
* Multiple PDF files can be input.
* You can print directly from the console.
* It can be returned in json format.
* You can limit the number of outputs.
* Exclude unnecessary prepositions and pronouns.

How to use
==============

    //If you want to use only basic functions
    void Stats(String[] PDFadress)
    
    //If you want to limit the number of outputs
    void Stats(String[] PDFadress, int amount)
    
    //If you want to return in JSON format
    JSONObject StatIntoJSON(String[] PDFadress)


Example
==============
Basic Ex


    import WeSTATS.*;
    
    public static void  main(String[] args) throws Exception{
        String adress[] = {"pc1/Users/Documents/English.pdf"};
        processingEN en = new processingEN();
        
        en.Stats(adress);
    }

    
Limit output Ex
    

    import WeSTATS.*;
    
    public static void  main(String[] args) throws Exception{
        String adress[] = {"pc1/Users/Documents/English.pdf"};
        processingEN en = new processingEN();
        
        en.Stats(adress, 100);
    }

    
Return JSON Ex


    import WeSTATS.*;
    
    public static void  main(String[] args) throws Exception{
        String adress[] = {"pc1/Users/Documents/English.pdf"};
        processingEN en = new processingEN();
        
        JSONObject obj = en.StatsIntoJSON(adress);
    }

    
Example Result
==============
![CFR_App](./img/result.png)

License
==============
We used Apache's PDFBox. So you are subject to the **Apache 2.0 license**.
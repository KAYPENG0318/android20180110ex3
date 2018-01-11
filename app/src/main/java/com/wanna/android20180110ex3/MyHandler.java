package com.wanna.android20180110ex3;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Student on 2018/1/10.
 */

public class MyHandler extends DefaultHandler {
    boolean isTitle = false;
    boolean isItem=false;
    boolean isLink = false;
    boolean isDescription = false;
    StringBuilder linkSB = new StringBuilder();
    StringBuilder descSB = new StringBuilder();
    public ArrayList<Mobile01NewsItem> newsItems = new ArrayList<>();
    Mobile01NewsItem item;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        switch(qName)
        {
            case "title":
                isTitle = true;
                break;
            case "item":
                isItem=true;
                item = new Mobile01NewsItem();
                break;
            case "link":
                isLink = true;
                break;
            case "description":
                isDescription = true;
                descSB = new StringBuilder();
                break;

        }


    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        switch(qName)
        {
            case "title":
                isTitle = false;
                break;
            case "item":
                isItem=false;
                newsItems.add(item);
                break;
            case "link":
                isLink = false;
                if(isItem){
                    item.link = linkSB.toString();
                    linkSB = new StringBuilder();
                }
                break;
            case "description":
                isDescription = false;
                if(isItem)
                {
                    String str = descSB.toString();
                    Pattern pattern = Pattern.compile("https.*jpg");
                    Matcher m = pattern.matcher(str);
                    String imgurl = "";

                    if(m.find())
                    {
                        imgurl=m.group(0);
                        Log.d("NET",imgurl);
                    }
                    str = str.replaceAll("<img.*/>","");
                    item.description = str;
                    item.imgurl = imgurl;
                }
                break;

        }



    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if(isTitle && isItem){
            Log.d("NET",new String(ch,start,length));
            item.title = new String(ch,start,length);
        }
        if(isLink && isItem){
            Log.d("NET",new String(ch,start,length));
            linkSB.append(new String(ch,start,length));
        }
        if(isDescription && isItem){

            descSB.append(new String(ch,start,length));


        }

    }
}

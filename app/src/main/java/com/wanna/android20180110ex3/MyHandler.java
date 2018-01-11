package com.wanna.android20180110ex3;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/10.
 */

public class MyHandler extends DefaultHandler {
    boolean isTitle = false;
    boolean isItem=false;
    boolean isLink = false;
    StringBuilder linkSB = new StringBuilder();
    public ArrayList<Mobile01NewsItem> newsItems = new ArrayList<>();
    Mobile01NewsItem item;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(qName.equals("title")){
            isTitle = true;
        }
        if(qName.equals("item")){
            isItem=true;
            item = new Mobile01NewsItem();
        }
        if(qName.equals("link")){
            isLink = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(qName.equals("title")){
            isTitle = false;
        }
        if(qName.equals("item")){
            isItem = false;
            newsItems.add(item);
        }
        if(qName.equals("link")){
            isLink = false;
            if(isItem){
                item.link = linkSB.toString();
                linkSB = new StringBuilder();
            }

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
    }
}

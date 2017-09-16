package android.client.zxing.google.com.wish;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 다음 로컬 검색 api를 통해서 받아온 정보를 파싱하여 arrayList로 반환
 */

public class MyXmlParser {

    public enum TagType { NONE, TITLE, NEWADDRESS, LATITUDE, LONGITUDE};

    public MyXmlParser() {
    }

    public ArrayList<POI> parse(String xml) {

        ArrayList<POI> resultList = new ArrayList();
        POI dbo = null;

        TagType tagType = TagType.NONE;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {
                            dbo = new POI();
                        } else if (parser.getName().equals("title")) {
                            dbo = new POI();
                            tagType = TagType.TITLE;
                        } else if (parser.getName().equals("newAddress")) {
                            tagType = TagType.NEWADDRESS;
                        } else if  (parser.getName().equals("latitude")) {
                            tagType = TagType.LATITUDE;}
                        else if  (parser.getName().equals("longitude")) {
                            tagType = TagType.LONGITUDE;}

                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            resultList.add(dbo);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType) {
                            case TITLE:
                                dbo.setTitle(parser.getText());
                                break;
                            case NEWADDRESS:
                                dbo.setAddress(parser.getText());
                                break;
                            case LATITUDE:
                                dbo.setLatitude(Double.parseDouble(parser.getText()));
                                break;
                            case LONGITUDE:
                                dbo.setLongitude(Double.parseDouble(parser.getText()));
                                break;
                        }
                        tagType = TagType.NONE;
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }


}

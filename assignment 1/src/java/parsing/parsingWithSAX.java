/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.PriorityQueue;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import jdk.internal.org.xml.sax.helpers.DefaultHandler;
import org.kohsuke.rngom.digested.Main;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * @author User
 */
public class parsingWithSAX {
    private int usersCount=1;
    private String currentElement="";
    private PriorityQueue<Integer> friends = new PriorityQueue<>();    
    private ArrayList<users> users = new ArrayList<>();
    private users currentUser = new users();
    public parsingWithSAX(){
        try {
			// Create a SAX parser factory
			SAXParserFactory factory = SAXParserFactory.newInstance(); 
			
			// Obtain a SAX parser
			SAXParser saxParser = factory.newSAXParser();
			
			// XML Stream
			InputStream xmlStream = parsingWithSAX.class.getResourceAsStream("users.xml");
                        InputStream xmlStream2 = parsingWithSAX.class.getResourceAsStream("users.xml");
			
			// Parse the given XML document using the callback handler
                        saxParser.parse(xmlStream, new fillNames()); 
			saxParser.parse(xmlStream2, new MySaxHandler()); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    public static void main(String[] args){
        new parsingWithSAX();
    }
    
    
    
    class MySaxHandler extends org.xml.sax.helpers.DefaultHandler{
                // Callback to handle element start tag
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			 currentElement = qName;
			if (currentElement.equals("user")) {
				System.out.println("User " + usersCount);
				usersCount++;
				System.out.println("\tID:\t" + attributes.getValue("ID"));
			}
                       
		}

		// Callback to handle element end tag
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
                        if(qName.equals("friends")){
                            System.out.println("\tFrinds:");
                            int currFriend=1;
                            currFriend = friends.poll();
                            for(int i=1;i<=7;i++){
                                if(i==currFriend){
                                    System.out.println("friend with user's "+ users.get(i-1).getName());
                                    if(!friends.isEmpty())
                                    currFriend = friends.poll();
                                }
                                else{
                                    System.out.println("not friend with user's ID: "+ i);
                                }
                            }
                        }
			currentElement = "";
		}

		// Callback to handle the character text data inside an element
		@Override
		public void characters(char[] chars, int start, int length) throws SAXException {
			if (currentElement.equals("DOB")) {
				System.out.println("\tDOB:\t" + new String(chars, start, length));
				
			} else if (currentElement.equals("ID")) {
				friends.add(Integer.parseInt(new String(chars,start,length)));
			} else if (currentElement.equals("name")) {
				System.out.println("\tName:\t" + new String(chars, start, length));
			}
		}
        
        
        
    }
    
    class fillNames extends org.xml.sax.helpers.DefaultHandler{
        // Callback to handle element start tag
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    currentElement = qName;
                    if(qName.equals("user")){
                        currentUser.setID(Integer.parseInt(attributes.getValue("ID")));
                        
                    }
                    if(qName.equals("name")){
                        
                    }
		}
                
                
                @Override
		public void characters(char[] chars, int start, int length) throws SAXException {
			if (currentElement.equals("name")) {
                             currentUser.setName(new String(chars,start,length));
                             users.add(currentUser);
                        }
		}
    }
    
}

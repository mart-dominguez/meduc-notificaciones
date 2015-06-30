package ar.gov.santafe.meduc.services.notificaciones.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.xml.sax.SAXException;


public class Queries {
	
	static Properties props ;          
	private final String rutaQuerys = "Queries.xml";
	 
	 	
	
	public  Map<String,Object> paramMap(){
		return new HashMap<String, Object>();
	}
	
	
	
	private void  init() {
		props = new Properties();
		InputStream defConsultas = null;
		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder;
				docBuilder = docBuilderFactory.newDocumentBuilder();
			defConsultas = this.getClass().getClassLoader().getResourceAsStream(rutaQuerys);
			props.loadFromXML(defConsultas);

			defConsultas.close();

			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidPropertiesFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	private  void  load(){
		props = new Properties();
		 
		InputStream is;
		try {
			String f = "Queries.xml";
			URL r = this.getClass().getClassLoader().getResource(f);
			String path = r.getPath();
			is = new FileInputStream(path);
			//load the xml file into properties format
			props.loadFromXML(is);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public  String  get(String name){
		init();
		if (props==null) init();
		String value = props.getProperty(name);

		return value;
	}
	
	
}

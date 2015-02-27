package data.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "keywords")
public class KeywordWrapper {
	
	private List<String> keywords;
	
	@XmlElement(name = "keyword")
	public List<String> getKeywords(){
		return this.keywords;
	}
	
	public void setKeywords(List<String> keywords){
		this.keywords = keywords;
	}

}

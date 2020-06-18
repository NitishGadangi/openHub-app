package net.vidflix.Model.search;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ContentItem{

	@SerializedName("cat_name")
	private String catName;

	@SerializedName("content")
	private List<ContentItem> content;

	@SerializedName("api_url")
	private String apiUrl;

	@SerializedName("is_series")
	private String isSeries;

	@SerializedName("name")
	private String name;

	@SerializedName("poster_url")
	private String posterUrl;

	@SerializedName("id")
	private String id;
	private String categoryType;

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public void setCatName(String catName){
		this.catName = catName;
	}

	public String getCatName(){
		return catName;
	}

	public void setContent(List<ContentItem> content){
		this.content = content;
	}

	public List<ContentItem> getContent(){
		return content;
	}

	public void setApiUrl(String apiUrl){
		this.apiUrl = apiUrl;
	}

	public String getApiUrl(){
		return apiUrl;
	}

	public void setIsSeries(String isSeries){
		this.isSeries = isSeries;
	}

	public String getIsSeries(){
		return isSeries;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPosterUrl(String posterUrl){
		this.posterUrl = posterUrl;
	}

	public String getPosterUrl(){
		return posterUrl;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"ContentItem{" + 
			"cat_name = '" + catName + '\'' + 
			",content = '" + content + '\'' + 
			",api_url = '" + apiUrl + '\'' + 
			",is_series = '" + isSeries + '\'' + 
			",name = '" + name + '\'' + 
			",poster_url = '" + posterUrl + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
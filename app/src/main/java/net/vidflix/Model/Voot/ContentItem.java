package net.vidflix.Model.Voot;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class ContentItem {

	@SerializedName("cat_name")
	private String catName;

	@SerializedName("content")
	private List<ContentItem> content;

	@SerializedName("web_url")
	private String webUrl;

	@SerializedName("api_url")
	private String apiUrl;

	@SerializedName("is_series")
	private String isSeries;

	@SerializedName("name")
	private String name;

	@SerializedName("in_list")
	private String inList;

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

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public List<ContentItem> getContent() {
		return content;
	}

	public void setContent(List<ContentItem> content) {
		this.content = content;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getIsSeries() {
		return isSeries;
	}

	public void setIsSeries(String isSeries) {
		this.isSeries = isSeries;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInList() {
		return inList;
	}

	public void setInList(String inList) {
		this.inList = inList;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
 	public String toString(){
		return 
			"ContentItem{" + 
			"cat_name = '" + catName + '\'' + 
			",content = '" + content + '\'' + 
			",web_url = '" + webUrl + '\'' + 
			",api_url = '" + apiUrl + '\'' + 
			",is_series = '" + isSeries + '\'' + 
			",name = '" + name + '\'' + 
			",in_list = '" + inList + '\'' + 
			",poster_url = '" + posterUrl + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
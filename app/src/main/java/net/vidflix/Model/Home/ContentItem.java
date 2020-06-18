package net.vidflix.Model.Home;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

public class ContentItem{

	@SerializedName("icon_url")
	private String iconUrl;

	@SerializedName("thumbnail")
	private String thumbnail;

	@SerializedName("category_name")
	private String categoryName;

	@SerializedName("web_url")
	private String webUrl;

	@SerializedName("api_url")
	private String apiUrl;

	@SerializedName("type")
	private String type;

	@SerializedName("is_pagination")
	private String isPagination;

	@SerializedName("sub_api")
	private String subApi;
	private String categoryType;

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public void setIconUrl(String iconUrl){
		this.iconUrl = iconUrl;
	}

	public String getIconUrl(){
		return iconUrl;
	}

	public void setThumbnail(String thumbnail){
		this.thumbnail = thumbnail;
	}

	public String getThumbnail(){
		return thumbnail;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setWebUrl(String webUrl){
		this.webUrl = webUrl;
	}

	public String getWebUrl(){
		return webUrl;
	}

	public void setApiUrl(String apiUrl){
		this.apiUrl = apiUrl;
	}

	public String getApiUrl(){
		return apiUrl;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setIsPagination(String isPagination){
		this.isPagination = isPagination;
	}

	public String getIsPagination(){
		return isPagination;
	}

	public void setSubApi(String subApi){
		this.subApi = subApi;
	}

	public String getSubApi(){
		return subApi;
	}

	@NonNull
	@Override
	public String toString() {
		return categoryName;
	}
}
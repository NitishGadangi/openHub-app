package net.vidflix.Model.Home;

import com.google.gson.annotations.SerializedName;

public class SliderItem{

	@SerializedName("img")
	private String img;

	@SerializedName("api_url")
	private String apiUrl;

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setApiUrl(String apiUrl){
		this.apiUrl = apiUrl;
	}

	public String getApiUrl(){
		return apiUrl;
	}
}
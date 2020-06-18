package net.vidflix.Model.Home;

import com.google.gson.annotations.SerializedName;


public class AppVer{

	@SerializedName("latest_v")
	private String latestV;

	public void setLatestV(String latestV){
		this.latestV = latestV;
	}

	public String getLatestV(){
		return latestV;
	}
}
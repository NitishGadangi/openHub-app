package net.vidflix.Model.Home;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomeModel{

	@SerializedName("slider")
	private List<SliderItem> slider;

	@SerializedName("ads")
	private List<String> ads;

	@SerializedName("alert_msg")
	private String alertMsg;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("app_ver")
	private AppVer appVer;

	@SerializedName("changes")
	private Changes changes;

	@SerializedName("versionCode")
	private String versionCode;

	public void setSlider(List<SliderItem> slider){
		this.slider = slider;
	}

	public List<SliderItem> getSlider(){
		return slider;
	}

	public void setAds(List<String> ads){
		this.ads = ads;
	}

	public List<String> getAds(){
		return ads;
	}

	public void setAlertMsg(String alertMsg){
		this.alertMsg = alertMsg;
	}

	public String getAlertMsg(){
		return alertMsg;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setAppVer(AppVer appVer){
		this.appVer = appVer;
	}

	public AppVer getAppVer(){
		return appVer;
	}

	public void setChanges(Changes changes){
		this.changes = changes;
	}

	public Changes getChanges(){
		return changes;
	}

	public void setVersionCode(String versionCode){
		this.versionCode = versionCode;
	}

	public String getVersionCode(){
		return versionCode;
	}
}
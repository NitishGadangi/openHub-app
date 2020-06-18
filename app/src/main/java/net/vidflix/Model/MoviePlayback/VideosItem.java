package net.vidflix.Model.MoviePlayback;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VideosItem implements Serializable {

	@SerializedName("file_url")
	private String fileUrl;

	@SerializedName("subtitles")
	private String subtitles;

	@SerializedName("sub_lang")
	private String subLang;

	@SerializedName("label")
	private String label;

	@SerializedName("license_url")
	private String licenseUrl;
	@SerializedName("is_mpd")
	private String isMpd;

	@SerializedName("url")
	private String url;

	@SerializedName("timestamp")
	long timestamp;

	private String agent;

	public String getIsMpd() {
		return isMpd;
	}

	public void setIsMpd(String isMpd) {
		this.isMpd = isMpd;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public void setFileUrl(String fileUrl){
		this.fileUrl = fileUrl;
	}

	public String getFileUrl(){
		return fileUrl;
	}

	public void setSubtitles(String subtitles){
		this.subtitles = subtitles;
	}

	public String getSubtitles(){
		return subtitles;
	}

	public void setSubLang(String subLang){
		this.subLang = subLang;
	}

	public String getSubLang(){
		return subLang;
	}

	public void setLabel(String label){
		this.label = label;
	}

	public String getLabel(){
		return label;
	}

	public void setLicenseUrl(String licenseUrl){
		this.licenseUrl = licenseUrl;
	}

	public String getLicenseUrl(){
		return licenseUrl;
	}

	@Override
 	public String toString(){
		return 
			"VideosItem{" + 
			"file_url = '" + fileUrl + '\'' + 
			",subtitles = '" + subtitles + '\'' + 
			",sub_lang = '" + subLang + '\'' +
			",label = '" + label + '\'' + 
			",license_url = '" + licenseUrl + '\'' + 
			"}";
		}
}
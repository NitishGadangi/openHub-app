package net.vidflix.Model.Series;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

import net.vidflix.Model.MoviePlayback.VideosItem;

public class EpisodesItem implements Serializable {

	@SerializedName("epi_subtitle")
	private String epiSubtitle;

	@SerializedName("epi_description")
	private String epiDescription;

	@SerializedName("epi_thumb")
	private String epiThumb;

	@SerializedName("epi_name")
	private String epiName;

	@SerializedName("episode_no")
	private String episodeNo;

	@SerializedName("url")
	private List<VideosItem> url;

	public void setEpiSubtitle(String epiSubtitle){
		this.epiSubtitle = epiSubtitle;
	}

	public String getEpiSubtitle(){
		return epiSubtitle;
	}

	public void setEpiDescription(String epiDescription){
		this.epiDescription = epiDescription;
	}

	public String getEpiDescription(){
		return epiDescription;
	}

	public void setEpiThumb(String epiThumb){
		this.epiThumb = epiThumb;
	}

	public String getEpiThumb(){
		return epiThumb;
	}

	public void setEpiName(String epiName){
		this.epiName = epiName;
	}

	public String getEpiName(){
		return epiName;
	}

	public void setEpisodeNo(String episodeNo){
		this.episodeNo = episodeNo;
	}

	public String getEpisodeNo(){
		return episodeNo;
	}

	public List<VideosItem> getUrl() {
		return url;
	}

	public void setUrl(List<VideosItem> url) {
		this.url = url;
	}

	@Override
 	public String toString(){
		return 
			"EpisodesItem{" + 
			"epi_subtitle = '" + epiSubtitle + '\'' + 
			",epi_description = '" + epiDescription + '\'' + 
			",epi_thumb = '" + epiThumb + '\'' + 
			",epi_name = '" + epiName + '\'' + 
			",episode_no = '" + episodeNo + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}
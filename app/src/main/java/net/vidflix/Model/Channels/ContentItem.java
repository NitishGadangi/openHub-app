package net.vidflix.Model.Channels;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Channels")
public class ContentItem{

	@SerializedName("web_url")
	private String webUrl;

	@SerializedName("api_url")
	private String apiUrl;

	@SerializedName("name")
	private String name;

	@SerializedName("poster_url")
	private String posterUrl;

	@SerializedName("id")
	private String id;

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

	@NonNull
	@Override
	public String toString() {
		return name;
	}
}
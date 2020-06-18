package net.vidflix.Model.Series;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SeriesModel implements Serializable {

	@SerializedName("icon_url")
	private String iconUrl;

	@SerializedName("referer")
	private String referer;

	@SerializedName("releaseDate")
	private String releaseDate;

	@SerializedName("is_series")
	private String isSeries;

	@SerializedName("director")
	private String director;

	@SerializedName("more_movies")
	private String moreMovies;

	@SerializedName("description")
	private String description;

	@SerializedName("poster_url")
	private String posterUrl;

	@SerializedName("cast")
	private String cast;

	@SerializedName("live_fetch")
	private String liveFetch;

	@SerializedName("genres")
	private String genres;

	@SerializedName("name")
	private String name;

	@SerializedName("is_live")
	private String isLive;

	@SerializedName("user_agent")
	private String userAgent;

	@SerializedName("license_url")
	private String licenseUrl;

	@SerializedName("episodes_data")
	private String episodesData;

	@SerializedName("timestamp")
	private long timestamp;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void setEpisodesData(String episodesData) {
		this.episodesData = episodesData;
	}

	public String getEpisodesData() {
		return episodesData;
	}

	public void setIconUrl(String iconUrl){
		this.iconUrl = iconUrl;
	}

	public String getIconUrl(){
		return iconUrl;
	}

	public void setReferer(String referer){
		this.referer = referer;
	}

	public String getReferer(){
		return referer;
	}

	public void setIsSeries(String isSeries){
		this.isSeries = isSeries;
	}

	public String getIsSeries(){
		return isSeries;
	}

	public void setDirector(String director){
		this.director = director;
	}

	public String getDirector(){
		return director;
	}

	public void setMoreMovies(String moreMovies){
		this.moreMovies = moreMovies;
	}

	public String getMoreMovies(){
		return moreMovies;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setPosterUrl(String posterUrl){
		this.posterUrl = posterUrl;
	}

	public String getPosterUrl(){
		return posterUrl;
	}

	public void setCast(String cast){
		this.cast = cast;
	}

	public String getCast(){
		return cast;
	}

	public void setLiveFetch(String liveFetch){
		this.liveFetch = liveFetch;
	}

	public String getLiveFetch(){
		return liveFetch;
	}

	public void setReleaseDate(String releaseDate){
		this.releaseDate = releaseDate;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public void setGenres(String genres){
		this.genres = genres;
	}

	public String getGenres(){
		return genres;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setIsLive(String isLive){
		this.isLive = isLive;
	}

	public String getIsLive(){
		return isLive;
	}

	public void setUserAgent(String userAgent){
		this.userAgent = userAgent;
	}

	public String getUserAgent(){
		return userAgent;
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
			"SeriesModel{" + 
			"icon_url = '" + iconUrl + '\'' + 
			",referer = '" + referer + '\'' + 
			",releaseDate = '" + releaseDate + '\'' + 
			",is_series = '" + isSeries + '\'' + 
			",director = '" + director + '\'' + 
			",more_movies = '" + moreMovies + '\'' + 
			",description = '" + description + '\'' + 
			",poster_url = '" + posterUrl + '\'' + 
			",cast = '" + cast + '\'' + 
			",live_fetch = '" + liveFetch + '\'' + 
			",release_date = '" + releaseDate + '\'' + 
			",genres = '" + genres + '\'' + 
			",name = '" + name + '\'' + 
			",is_live = '" + isLive + '\'' + 
			",user_agent = '" + userAgent + '\'' + 
			",license_url = '" + licenseUrl + '\'' + 
			"}";
		}
}
package net.vidflix.Model.Update;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UpdateModel implements Serializable {

	@SerializedName("msg")
	private String msg;

	@SerializedName("apkUrl")
	private String apkUrl;

	@SerializedName("change_logs")
	private List<String> changeLogs;

	@SerializedName("code")
	private String code;

	@SerializedName("dialog_title")
	private String dialogTitle;

	@SerializedName("error")
	private String error;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setApkUrl(String apkUrl){
		this.apkUrl = apkUrl;
	}

	public String getApkUrl(){
		return apkUrl;
	}

	public void setChangeLogs(List<String> changeLogs){
		this.changeLogs = changeLogs;
	}

	public List<String> getChangeLogs(){
		return changeLogs;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setDialogTitle(String dialogTitle){
		this.dialogTitle = dialogTitle;
	}

	public String getDialogTitle(){
		return dialogTitle;
	}

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"UpdateModel{" + 
			"msg = '" + msg + '\'' + 
			",apkUrl = '" + apkUrl + '\'' + 
			",change_logs = '" + changeLogs + '\'' + 
			",code = '" + code + '\'' + 
			",dialog_title = '" + dialogTitle + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}
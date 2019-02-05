package ca.gc.cbsa.mcoe.deltaace.restApi.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Image implements Serializable {
	
	private String imageName;


	//Expected Base64 String representation if image
	private String encodedImage;
	
	private String imageUri;
	
	
	//no-arg constructor
	public Image() {};
	
	public Image(String imageName, String encodedImage){
		this.imageName = imageName;
		this.encodedImage = encodedImage;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getEncodedImage() {
		return encodedImage;
	}

	public void setEncodedImage(String encodedImage) {
		this.encodedImage = encodedImage;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}
	
}
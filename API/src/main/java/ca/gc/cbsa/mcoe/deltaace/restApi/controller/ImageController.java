package ca.gc.cbsa.mcoe.deltaace.restApi.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageCredentials;
import com.microsoft.azure.storage.StorageCredentialsAccountAndKey;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.Image;

/*******
 * 
 * This is required to load the images separately
 * @TODO - Error handling
 *
 */

@RestController
@RequestMapping("/images")
public class ImageController {
	
	@Value("${azure.storage.account.name}")
	private String accountName;
	
	@Value("${azure.storage.account.container.name}")
	private String containerName;
	
	@Value("${azure.storage.account.key}")
	private String accountKey;
	
	
	@PostMapping(value="/add",headers = {"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Image addImage(@RequestBody Image image) {
		// Return new image object with the image url and image name populated. No need to include encodedImage
		Image img = new Image();
		
		try {
			String imageName = image.getImageName();
			String base64EncodedImage = image.getEncodedImage();
			//Validate imageName to ensure not null
			if(imageName != null && imageName.length() > 0 && base64EncodedImage !=null && base64EncodedImage.length() > 0) {
				
			    // Create storagecredentials object by reading the values from the configuration (application.properties)
			    StorageCredentials storageCredentials = new StorageCredentialsAccountAndKey(accountName, accountKey);

			    // Create cloudstorage account by passing the storagecredentials
			    CloudStorageAccount storageAccount = new CloudStorageAccount(storageCredentials, true);

			    // Create the blob client.
			    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

			    // Get reference to the blob container by passing the name by reading the value from the configuration (application.properties)
			    CloudBlobContainer container = blobClient.getContainerReference(containerName);

			    // Get the reference to the block blob from the container
			    CloudBlockBlob blockBlob = container.getBlockBlobReference(imageName);

			    // Convert from Base64String to byte array
			    byte[] imageBytes = Base64.getDecoder().decode(new String(base64EncodedImage).getBytes("UTF-8"));;
			    // Upload the file
			    blockBlob.uploadFromByteArray(imageBytes, 0, imageBytes.length);
			    
			    img.setImageUri(blockBlob.getStorageUri().getPrimaryUri().toString());
			    img.setImageName(imageName);
				
			}
		} catch (URISyntaxException e) {
            System.out.println("Invalid URI provided");
        } catch (StorageException e) {
            System.out.println("Storage Exception");
        } catch (IOException e) {
        	e.printStackTrace();
        	System.exit(-1);
        }

		return img;
	}
	
	
}

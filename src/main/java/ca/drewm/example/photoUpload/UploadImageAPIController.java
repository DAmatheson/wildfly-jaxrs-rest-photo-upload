package ca.drewm.example.photoUpload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("upload")
public class UploadImageAPIController {
	/*
	 * This way doesn't allow access to the original filename of the file being uploaded
	 */
	@POST
	@Path("Basic")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
    public Response uploadBasicImage(@MultipartForm ImageUpload file) {
		try {
			File savedFile = new File("C:\\thing.jpg");
			
			Files.copy(file.getFileInputStream(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.getFileInputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        return Response.
        		status(201).
        		build();
    }
	
	/*
	 * This way allows access to the original filename of the file being uploaded
	 */
	@POST
	@Path("Image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(MultipartFormDataInput input) {
		String currentFileName = "";
		
		Map<String, List<InputPart>> formParts = input.getFormDataMap();
		
		List<InputPart> fileParts = formParts.get("file"); // "file" matches the name attribute of the input/formData
		
		JsonArrayBuilder filePaths = Json.createArrayBuilder();
		
		for (InputPart part : fileParts) {
			MultivaluedMap<String, String> headers = part.getHeaders();
			
			String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
			
			for (String name : contentDispositionHeader) {
				if (name.trim().startsWith("filename")) {
					currentFileName = name.substring(name.indexOf('=') + 1).replaceAll("\"", "");
				}
			}
			
			InputStream inputStream = null;
			
			try {
				inputStream = part.getBody(InputStream.class, null);
				
				File saveFile = File.createTempFile("img_", currentFileName);

				Files.copy(inputStream, saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

				filePaths.add(addFile(currentFileName, saveFile.getPath()));
			} catch (IOException e) {
				System.err.println("Error saving file");
				e.printStackTrace();
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					System.err.println("Error closing input stream");
					e.printStackTrace();
				}
			}
		}
		
		JsonObject result = Json.
				createObjectBuilder().
				add("filePaths", filePaths).
				build();
        
        return Response.
        		status(201).
        		entity(result).
        		build();
    }
	
	private JsonObjectBuilder addFile(String name, String path) {
        return Json.
        		createObjectBuilder().
        		add("name", name).
        		add("path", path);
    }
}

package ca.drewm.example.photoUpload;

import java.io.InputStream;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class ImageUpload {
	@FormParam("file")
    @PartType("application/octet-stream")
    private InputStream fileInputStream;

	public ImageUpload() { }
	
    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    
    public void setFileInputStream(final InputStream filedata) {
        this.fileInputStream = filedata;
    }
}
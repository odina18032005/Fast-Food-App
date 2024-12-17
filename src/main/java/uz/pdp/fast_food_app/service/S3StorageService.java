package uz.pdp.fast_food_app.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.fast_food_app.model.Files;
import uz.pdp.fast_food_app.repository.FilesRepo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3StorageService {
    private final String bucketName = "fast_food";
    private final AmazonS3 s3Client;
    private final FilesRepo imagesRepo;

    public S3StorageService(AmazonS3 s3Client, FilesRepo imagesRepo) {
        this.s3Client = s3Client;
        this.imagesRepo = imagesRepo;
    }

    public String upload(MultipartFile file, String folder) {
        try {
            String originalFilename = file.getOriginalFilename();
            File fileToUpload = convertMultiPartToFile(file);
            String filePath = folder + "/" + originalFilename;
            s3Client.putObject(bucketName, filePath, fileToUpload);
            fileToUpload.delete();
            return filePath;
        } catch (AmazonServiceException e) {
            throw e;
        } catch (IOException e) {
            throw new RuntimeException("File conversion error: " + e.getMessage(), e);
        }
    }

    public Files save(MultipartFile file, String folder) {
        String fileUrl = upload(file, folder);
        Files image = new Files();
        image.setPath(fileUrl);
        Files save = imagesRepo.save(image);
        return save;
    }

    public InputStream download(String keyName) {
        S3Object o = s3Client.getObject(bucketName, keyName);
        S3ObjectInputStream objectContent = o.getObjectContent();
        return objectContent;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public void delete(String path) {
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, path));
            System.out.println("File deleted successfully: " + path);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file: " + path, e);
        }
    }
}


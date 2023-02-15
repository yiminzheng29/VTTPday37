package sg.edu.nus.pafworkshop21.workshop21.services;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
    
    @Autowired
    private AmazonS3 s3Client;

    public String upload(MultipartFile file) throws IOException {
        Map<String, String> userData = new HashMap<>();
        userData.put("name", "test");
        userData.put("uploadTime", new Date().toString());
        userData.put("originalFileName", file.getOriginalFilename());

        // Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userData);

        String key = UUID.randomUUID().toString().substring(0,8);
        System.out.println("myobjects/%s".formatted(key));
        System.out.println(s3Client.doesBucketExistV2("bucketym"));
        System.out.println(s3Client.doesBucketExistV2("bucketym.bucketym"));
        System.out.println(file.getOriginalFilename());

        StringTokenizer tk = new StringTokenizer(file.getOriginalFilename(), ".");
        String filenameExt = "";

        int count = 0;
        while(tk.hasMoreTokens()) {
            if (count==1) {
                filenameExt = tk.nextToken();
                break;
            } else {
                filenameExt = tk.nextToken();
            }
            count++;
        }

        System.out.println("myobjects/%s.%s".formatted(key,filenameExt));
        if (filenameExt.equals("blob")) {
            filenameExt = filenameExt + ".png";
        }
        PutObjectRequest putRequest = new PutObjectRequest("bucketym", // to change to amazonS3 bucket name
            "myobject/%s.%s".formatted(key, filenameExt), file.getInputStream(), metadata);
        // 2nd key needs to be unique
        putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        s3Client.putObject(putRequest);

        return "myobjects/%s.%s".formatted(key, filenameExt);
    }

}

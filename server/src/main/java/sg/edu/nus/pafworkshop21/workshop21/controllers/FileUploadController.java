package sg.edu.nus.pafworkshop21.workshop21.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.pafworkshop21.workshop21.models.Post;
import sg.edu.nus.pafworkshop21.workshop21.services.FileUploadService;
import sg.edu.nus.pafworkshop21.workshop21.services.S3Service;

@Controller
public class FileUploadController {
    
    @Autowired
    private S3Service s3Svc;

    @Autowired
    private FileUploadService flSvc;
    private static final String BASE64_PREFIX_DECODER = "data:image/png;base64,";


    @PostMapping(path = "/upload-ng", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> uploadForAngular (
        @RequestPart MultipartFile imageFile,
        @RequestPart String title,
        @RequestPart String complain) throws SQLException{
        

        String key = "";
        System.out.printf("title: %s", title);
        System.out.printf("complain: %s", complain);

        try {
            key = s3Svc.upload(imageFile);
            flSvc.uploadBlob(imageFile, title, complain);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        JsonObject payload = Json.createObjectBuilder()
            .add("imageKey", key).build();

        return ResponseEntity.ok(payload.toString());
    }

    @PostMapping(path="/upload-tf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) 
    public String uploadTF(@RequestPart MultipartFile myFile, @RequestPart String name, Model model) { //name myfile needs to match exactly with index.html
        
        String key = "";
        System.out.printf("name: %s", name);

        try {
            key = s3Svc.upload(myFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("name", name);
        model.addAttribute("file", myFile);
        model.addAttribute("key", key);

        return "upload";
    }

    @GetMapping(path="/get-image/{postId}")
    public String retrieveImage(@PathVariable Integer postId, Model model) {
        Optional<Post> opt = flSvc.getPostById(postId);
        Post p = opt.get();
        String encodedString = Base64.getEncoder().encodeToString(p.getImage());
        model.addAttribute("title", p.getTitle());
        model.addAttribute("complain", p.getComplain());
        model.addAttribute("file", BASE64_PREFIX_DECODER + encodedString);
        return "blob";
    }
}

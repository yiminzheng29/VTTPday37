package sg.edu.nus.pafworkshop21.workshop21.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.pafworkshop21.workshop21.models.Post;
import sg.edu.nus.pafworkshop21.workshop21.repositories.FileUploadRepository;

@Service
public class FileUploadService {
    
    @Autowired
    private FileUploadRepository flRepo;

    public void uploadBlob(MultipartFile file, String title, String complain) throws IOException, SQLException {
        flRepo.uploadBlob(file, title, complain);
    }

    public Optional<Post> getPostById(Integer postId) {
        return flRepo.getPostById(postId);
    }
}

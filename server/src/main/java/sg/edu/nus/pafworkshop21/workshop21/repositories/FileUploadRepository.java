package sg.edu.nus.pafworkshop21.workshop21.repositories;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;


import sg.edu.nus.pafworkshop21.workshop21.models.Post;

@Repository
public class FileUploadRepository {
    
    private static final String INSERT_POSTS_TBL = "INSERT INTO posts (blobc, title, complain) VALUES(?, ?, ?)";
    
    private static final String SQL_GET_POST_BY_POST_ID = 
       "select id, title, complain, blobc from posts where id=?";

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private DataSource ds;

    public void uploadBlob(MultipartFile file, String title, String complain) throws SQLException, IOException {
        try (Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_POSTS_TBL)) {
                InputStream is = file.getInputStream();
                ps.setBinaryStream(1, is, file.getSize());
                ps.setString(2, title);
                ps.setString(3, complain);
                ps.executeUpdate();
            }
    }

    public Optional<Post> getPostById(Integer postId) {
        return template.query(
            SQL_GET_POST_BY_POST_ID, 
            (ResultSet rs) -> {
                if (!rs.next())
                    return Optional.empty();
            final Post post = Post.populate(rs);
            return Optional.of(post);
            }, postId );
    }
}

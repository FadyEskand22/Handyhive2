package com.handyHive.handyhive;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final String uploadDir = "src/main/resources/static/uploads/";  // ✅ Ensure correct upload path

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post createPost(String caption, MultipartFile imageFile) throws IOException {
        // ✅ Ensure the upload directory exists
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // ✅ Save the image with a unique filename
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        File dest = new File(uploadDir + fileName);
        imageFile.transferTo(dest);

        // ✅ Save post details in database
        Post post = new Post();
        post.setCaption(caption);
        post.setImagePath("/uploads/" + fileName);  // ✅ Correct relative path
        return postRepository.save(post);
    }
}

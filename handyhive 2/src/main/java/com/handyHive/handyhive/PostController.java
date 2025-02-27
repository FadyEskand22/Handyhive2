package com.handyHive.handyhive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // ✅ GET method to return all posts (Fixes the error)
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
public RedirectView createPost(
        @RequestParam("caption") String caption,
        @RequestParam("image") MultipartFile imageFile) {
    try {
        postService.createPost(caption, imageFile);
        return new RedirectView("/all_posts.html");  // ✅ Redirects after upload
    } catch (IOException e) {
        return new RedirectView("/provider_home.html?error=upload_failed");
    }
}

}
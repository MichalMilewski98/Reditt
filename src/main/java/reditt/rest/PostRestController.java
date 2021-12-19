package reditt.rest;

import reditt.model.Post;
import reditt.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostRestController {

    @Autowired
    private final PostServiceImpl postService;

    public PostRestController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/posts")
    public ResponseEntity<List<Post>> getProducts() {
        List<Post> products = postService.getPosts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/post/{id}")
    public ResponseEntity<Post> getProduct(@PathVariable Long id) throws Exception{
        Post product = postService.getPostById(id)
                .orElseThrow(() -> new Exception("Post with id: " + id + " not found"));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping(value = "/post")
    public ResponseEntity<Post> addProduct (Post product) {
        this.postService.addPost(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
}

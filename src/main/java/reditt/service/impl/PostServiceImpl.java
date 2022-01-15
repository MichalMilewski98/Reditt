package reditt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reditt.model.Post;
import reditt.repository.PostRepository;
import reditt.service.PostService;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return this.postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return this.postRepository.findById(id);
    }

    public void addPost(Post product) {
        postRepository.save(product);
    }

}

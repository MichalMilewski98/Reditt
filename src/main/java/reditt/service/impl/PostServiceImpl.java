package reditt.service.impl;

import reditt.model.Post;
import reditt.repository.PostRepository;
import reditt.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

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

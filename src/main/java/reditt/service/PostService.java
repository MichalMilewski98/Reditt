package reditt.service;

import reditt.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PostService {

    List<Post> getPosts();

    Optional<Post> getPostById(Long id);

    void addPost(Post post);
}

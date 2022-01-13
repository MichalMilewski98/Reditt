package reditt.service;

import reditt.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> getPosts();

    Optional<Post> getPostById(Long id);

    void addPost(Post post);
}

package com.jong.backend.service;

import com.jong.backend.dto.CommentRequest;
import com.jong.backend.dto.CommentResponse;
import com.jong.backend.entity.Comment;
import com.jong.backend.entity.Post;
import com.jong.backend.entity.User;
import com.jong.backend.exception.ResourceNotFoundException;
import com.jong.backend.repository.CommentRepository;
import com.jong.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final AuthenticationService authenticationService;
    private final PostRepository postRepository;

    public CommentResponse createComment(Long postId, CommentRequest request){
        User user = authenticationService.getCurrentUser();
        Post post = postRepository.findByIdAndNotDeleted(postId).orElseThrow(()->new ResourceNotFoundException("Post not found"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(user)
                .build();

        comment = commentRepository.save(comment);
        return CommentResponse.fromEntity(comment);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getComments(Long postId, Pageable pageable) {
        authenticationService.getCurrentUser();

        postRepository.findByIdAndNotDeleted(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
        return comments.map(CommentResponse::fromEntity);
    }
}

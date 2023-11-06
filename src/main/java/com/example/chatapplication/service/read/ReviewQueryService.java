package com.example.chatapplication.service.read;

import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.ReviewMovie;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.model.query.CommentQuery;
import com.example.chatapplication.model.query.MovieQuery;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.view.ReviewView;
import com.example.chatapplication.repository.ReviewRepository;
import com.example.chatapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public CommonRes<?> findReviewMovies(MovieQuery query,Long userId){
        List<ReviewMovie> reviewMovies = reviewRepository.findReviewInMoview(query.getMovieId());
        List<Long> userIds = reviewMovies.stream().map(e->e.getUserId()).collect(Collectors.toList());
        List<User> users = userRepository.findAllById(userIds);
        List<ReviewView> reviewViews = reviewMovies.stream().map(e->{
            User user = users.stream().filter(u->u.getId().equals(e.getUserId())).findFirst().orElse(null);
            return ReviewView.builder()
                    .reviewId(e.getId())
                    .userid(e.getUserId())
                    .movieId(e.getMovieId())
                    .avatar(user != null ? user.getAvatar() : null)
                    .content(e.getContent())
                    .children(e.getChildrenCount())
                    .name(user.getNickName() == null ? user.getFullname() : user.getNickName())
                    .parrentId(e.getParrentId())
                    .createdAt(e.getCreatedAt())
                    .byYourSelf(e.getUserId().equals(userId))
                    .build();
        }).collect(Collectors.toList());
        return Utils.createSuccessResponse(reviewViews);
    }

    public CommonRes<?> findReplyReviewMovies(CommentQuery query, Long userId){
        List<ReviewMovie> reviewMovies = reviewRepository.findReplyReviewInMoview(query.getCommentId());
        List<Long> userIds = reviewMovies.stream().map(e->e.getUserId()).collect(Collectors.toList());
        List<User> users = userRepository.findAllById(userIds);
        List<ReviewView> reviewViews = reviewMovies.stream().map(e->{
            User user = users.stream().filter(u->u.getId().equals(e.getUserId())).findFirst().orElse(null);
            return ReviewView.builder()
                    .reviewId(e.getId())
                    .userid(e.getUserId())
                    .movieId(e.getMovieId())
                    .avatar(user != null ? user.getAvatar() : null)
                    .content(e.getContent())
                    .children(e.getChildrenCount())
                    .name(user.getNickName() == null ? user.getFullname() : user.getNickName())
                    .parrentId(e.getParrentId())
                    .createdAt(e.getCreatedAt())
                    .byYourSelf(e.getUserId().equals(userId))
                    .build();
        }).collect(Collectors.toList());
        return Utils.createSuccessResponse(reviewViews);
    }
}

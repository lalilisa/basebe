package com.example.chatapplication.service.write;

import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.*;
import com.example.chatapplication.domain.compositekey.UserMoviesKey;
import com.example.chatapplication.model.command.*;
import com.example.chatapplication.model.query.CommentQuery;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.view.ReviewView;
import com.example.chatapplication.model.view.VoteMovieView;
import com.example.chatapplication.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReviewCommandService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    private final FireBaseNotifiCommandService fireBaseNotifiCommandService;
    private final NotificationsRepository notificationsRepository;

    public CommonRes<?> reviewMovie(Long userId, CreateReviewCommand command) {
        ReviewMovie movie = ReviewMovie.builder()
                .movieId(command.getMoviesId())
                .content(command.getContent())
                .userId(userId)
                .parrentId(null)
                .childrenCount(0)
                .build();
        User user = userRepository.findById(userId).orElse(null);
        return Utils.createSuccessResponse(convertToView(user, reviewRepository.save(movie)));
    }

    public CommonRes<?> updateReviewMovie(Long userId, UpdateReviewCommand command) {
        System.out.println(userId);
        Optional<ReviewMovie> reviewMovieOptional = reviewRepository.findFirstByUserIdAndId(userId, command.getReviewId());
        if (reviewMovieOptional.isEmpty())
            return Utils.createErrorResponse(400, "RV0001", "Đánh giá không tồn tại");
        ReviewMovie reviewMovie = reviewMovieOptional.get();
        reviewMovie.setContent(command.getContent());
        reviewRepository.save(reviewMovie);
        return Utils.createSuccessResponse("SUCCESS");
    }

    public CommonRes<?> deleteReviewMovie(Long userId, DeleteReviewCommand command) {
        Optional<ReviewMovie> reviewMovieOptional = reviewRepository.findFirstByUserIdAndId(userId, command.getReviewId());
        List<ReviewMovie> childrenReview = reviewRepository.findReviewMovieByParrentId(command.getReviewId());
        if (reviewMovieOptional.isEmpty())
            return Utils.createErrorResponse(400, "RV0001", "Đánh giá không tồn tại");
        reviewRepository.delete(reviewMovieOptional.get());
        reviewRepository.deleteAll(childrenReview);
        return Utils.createSuccessResponse("SUCCESS");
    }

    public CommonRes<?> reply(Long userId, CreateReviewCommand command) {
        Optional<ReviewMovie> reviewMovieOptional = reviewRepository.findById(command.getParentId());
        if (reviewMovieOptional.isEmpty())
            return Utils.createErrorResponse(400, "RV0001", "Đánh giá không tồn tại");
        User user = userRepository.findById(userId).orElse(null);
        ReviewMovie parrentReview = reviewMovieOptional.get();
        parrentReview.setChildrenCount(parrentReview.getChildrenCount() == null ? 1 : parrentReview.getChildrenCount() + 1);
        ReviewMovie movie = ReviewMovie.builder()
                .movieId(command.getMoviesId())
                .content(command.getContent())
                .userId(userId)
                .parrentId(command.getParentId())
                .childrenCount(0)
                .build();
        Optional<User> ownParentComment = userRepository.findById(parrentReview.getUserId());
        if (ownParentComment.isPresent()) {
            List<String> fcmToken = new ArrayList<>();
            fcmToken.add(ownParentComment.get().getDeviceUUID());
            String name = user.getNickName() != null ? user.getNickName() : user.getFullname();
            String content = name + " đã phản hồi bình luận của bạn : " + command.getContent();
            String title = "BÌNH LUẬN";
            Map<String, String> data = new HashMap<>();
            data.put("navigation", "Select");
            Notice notice = Notice.builder()
                    .subject(title)
                    .content(content)
                    .data(data)
                    .registrationTokens(fcmToken)
                    .build();
            fireBaseNotifiCommandService.sendNotification(notice);
            notificationsRepository.save(Notifications.builder().userId(ownParentComment.get()
                    .getId()).content(content).title(title).type("NOTIFICATION").build());
        }
        return Utils.createSuccessResponse(convertToView(user, reviewRepository.save(movie)));
    }

    private final UserVoteRepository userVoteRepository;
    private static DecimalFormat format = new DecimalFormat("##.##");
    private final MoviesRepository moviesRepository;
    public CommonRes<?> voteMovie(Long userId, VoteMovieCommand command) {
        UserMoviesKey userMoviesKey = new UserMoviesKey(userId, command.getMovieId());
        UserVote userVote = userVoteRepository.findById(userMoviesKey).orElse(null);
        if (userVote != null) {
            userVote.setVote(command.getVote());
        } else {
            userVote = UserVote.builder().build();
            userVote.setVote(command.getVote());
            userVote.setId(userMoviesKey);
        }
        userVoteRepository.save(userVote);
        Integer sumStar = userVoteRepository.getSumStartVote(command.getMovieId());
        Integer totalVote = userVoteRepository.countByMovieId(command.getMovieId());
        Double rate = Double.parseDouble(format.format(sumStar/totalVote));
        Movies movies= moviesRepository.findById(command.getMovieId()).orElse(null);
        if(movies != null){
            movies.setRate(rate);
            moviesRepository.save(movies);
        }
        return Utils.createSuccessResponse(VoteMovieView.builder().rate(rate).build());
    }

    private ReviewView convertToView(User user, ReviewMovie e) {
        return ReviewView.builder()
                .reviewId(e.getId())
                .userId(e.getUserId())
                .movieId(e.getMovieId())
                .avatar(user != null ? user.getAvatar() : null)
                .content(e.getContent())
                .children(e.getChildrenCount())
                .name(user.getNickName() == null ? user.getFullname() : user.getNickName())
                .parrentId(e.getParrentId())
                .createdAt(e.getCreatedAt())
                .byYourSelf(e.getUserId().equals(user.getId()))
                .build();
    }

    public CommonRes<?> getMyVoteMovie(Long userId, Long movieId) {
        UserMoviesKey userMoviesKey = new UserMoviesKey(userId, movieId);
        UserVote userVote = userVoteRepository.findById(userMoviesKey).orElse(null);
        Integer vote = 0;
        if (userVote != null) {
           vote = userVote.getVote();
        }
        Map<String,Object> res =new HashMap<>();
        res.put("vote",vote);
        return Utils.createSuccessResponse(res);
    }
}

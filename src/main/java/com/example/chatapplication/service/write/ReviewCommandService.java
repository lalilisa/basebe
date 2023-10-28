package com.example.chatapplication.service.write;

import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.ReviewMovie;
import com.example.chatapplication.domain.UserVote;
import com.example.chatapplication.domain.compositekey.UserMoviesKey;
import com.example.chatapplication.model.command.CreateReviewCommand;
import com.example.chatapplication.model.command.DeleteReviewCommand;
import com.example.chatapplication.model.command.UpdateReviewCommand;
import com.example.chatapplication.model.command.VoteMovieCommand;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReviewCommandService {
        private final ReviewRepository reviewRepository;

        public CommonRes<?> reviewMovie(Long userId , CreateReviewCommand command){
                ReviewMovie movie = ReviewMovie.builder()
                        .movieId(command.getMoviesId())
                        .content(command.getContent())
                        .userId(userId)
                        .parrentId(null)
                        .childrenCount(0)
                        .build();
                reviewRepository.save(movie);
                return Utils.createSuccessResponse("SUCCESS");
        }

        public CommonRes<?> updateReviewMovie(Long userId , UpdateReviewCommand command){
                Optional<ReviewMovie> reviewMovieOptional = reviewRepository.findFirstByUserIdAndId(userId,command.getReviewId());
                if(reviewMovieOptional.isEmpty())
                        return Utils.createErrorResponse(400,"RV0001","Đánh giá không tồn tại");
                ReviewMovie reviewMovie = reviewMovieOptional.get();
                reviewMovie.setContent(command.getContent());
                reviewRepository.save(reviewMovie);
                return Utils.createSuccessResponse("SUCCESS");
        }

        public CommonRes<?> deleteReviewMovie(Long userId , DeleteReviewCommand command){
                Optional<ReviewMovie> reviewMovieOptional = reviewRepository.findFirstByUserIdAndId(userId,command.getReviewId());
                if(reviewMovieOptional.isEmpty())
                        return Utils.createErrorResponse(400,"RV0001","Đánh giá không tồn tại");
                reviewRepository.delete(reviewMovieOptional.get());
                return Utils.createSuccessResponse("SUCCESS");
        }

        public CommonRes<?> reply(Long userId , CreateReviewCommand command){
                Optional<ReviewMovie> reviewMovieOptional = reviewRepository.findById(command.getParrentId());
                if(reviewMovieOptional.isEmpty())
                        return Utils.createErrorResponse(400,"RV0001","Đánh giá không tồn tại");
                ReviewMovie parrentReview = reviewMovieOptional.get();
                parrentReview.setChildrenCount(parrentReview.getChildrenCount() == null ? 1 : parrentReview.getChildrenCount() +1);
                ReviewMovie movie = ReviewMovie.builder()
                        .movieId(command.getMoviesId())
                        .content(command.getContent())
                        .userId(userId)
                        .parrentId(command.getParrentId())
                        .childrenCount(0)
                        .build();
                reviewRepository.save(movie);
                return Utils.createSuccessResponse("SUCCESS");
        }

        public CommonRes<?> voteMovie(Long userId , VoteMovieCommand command){
                UserVote userVote = UserVote.builder().build();
                userVote.setVote(command.getVote());
                userVote.setId(new UserMoviesKey(userId,command.getMovieId()));
                return Utils.createSuccessResponse("SUCCESS");
        }
}

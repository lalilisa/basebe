package com.example.chatapplication.service.write;


import com.example.chatapplication.common.BeanUtil;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.config.CloudinaryService;
import com.example.chatapplication.domain.Category;
import com.example.chatapplication.domain.MovieCategory;
import com.example.chatapplication.domain.Movies;
import com.example.chatapplication.domain.SubMovie;
import com.example.chatapplication.domain.compositekey.MovieCategoryKey;
import com.example.chatapplication.model.command.CreateMovieCommand;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.view.CategoryView;
import com.example.chatapplication.model.view.EpisodeView;
import com.example.chatapplication.model.view.MovieView;
import com.example.chatapplication.repository.CategoryRepository;
import com.example.chatapplication.repository.MovieCategoryRepository;
import com.example.chatapplication.repository.MoviesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class MovieCommandService {
    private final MoviesRepository moviesRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;
    private final MovieCategoryRepository movieCategoryRepository;

    public CommonRes<?> activeMovie(Long movieId, Integer active) {
        Optional<Movies> movies = moviesRepository.findById(movieId);
        if (movies.isPresent()) {
            Movies movies1 = movies.get();
            movies1.setActive(active);
            return Utils.createSuccessResponse(moviesRepository.save(movies1));
        }
        return Utils.createErrorResponse(400, "Movie is not exists");
    }

    public CommonRes<?> createMovie(CreateMovieCommand command) {
        String thumnailSrc = cloudinaryService.uploadURl(command.getThumnail());
        Movies movies = Movies.builder()
                .moviesType(command.getMoviesType())
                .name(command.getName())
                .rate((double) 0)
                .description(command.getDescription())
                .active(command.getActive())
                .duration(command.getDuration())
//                .releaseDate(command.getReleaseDate())
                .code(command.getCode())
                .thumnail(thumnailSrc)
                .build();
        Movies newMovie = moviesRepository.save(movies);
        if (command.getCategoriesIds() != null) {
            List<MovieCategory> movieCategories = command.getCategoriesIds().stream().map(e ->
                    new MovieCategory(new MovieCategoryKey(newMovie.getId(), e))
            ).collect(Collectors.toList());
            movieCategoryRepository.saveAll(movieCategories);
        }
        List<Category> categories = categoryRepository.findByMovieId(newMovie.getId());
        return Utils.createSuccessResponse(convertToView(newMovie, categories));
    }

    private MovieView convertToView(Movies movies, List<Category> categories) {
        MovieView movieView = new MovieView();
        BeanUtil.copyProperties(movies, movieView, false);
        List<CategoryView> categoryViews = categories.stream().map(e -> convertToCategoryView(e)).collect(Collectors.toList());
        movieView.setCategories(categoryViews);
        return movieView;
    }

    private MovieView convertToView(Movies movies, List<Category> categories, List<SubMovie> subMovies) {
        MovieView movieView = new MovieView();
        BeanUtil.copyProperties(movies, movieView, false);
        List<CategoryView> categoryViews = categories.stream().map(e -> convertToCategoryView(e)).collect(Collectors.toList());
        movieView.setCategories(categoryViews);
        List<EpisodeView> episodeViews = subMovies.stream().map(e -> EpisodeView.builder()
                .episode(e.getEpisode())
                .publicDate(e.getPublicDate())
                .src(e.getName())
                .name(e.getName())
                .src(e.getSrc())
                .build()).collect(Collectors.toList());
        movieView.setEpisode(episodeViews);
        return movieView;
    }

    private CategoryView convertToCategoryView(Category category) {
        return CategoryView.builder()
                .code(category.getCode())
                .name(category.getName())
                .build();
    }


}

package com.example.chatapplication.service.read;


import com.example.chatapplication.common.BeanUtil;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.Category;
import com.example.chatapplication.domain.Movies;
import com.example.chatapplication.domain.SubMovie;
import com.example.chatapplication.model.pageable.OffsetPageable;
import com.example.chatapplication.model.query.MoviesFilterQuery;
import com.example.chatapplication.model.query.QueryDto;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.response.ResponseListAll;
import com.example.chatapplication.model.response.ResponseMessage;
import com.example.chatapplication.model.view.CategoryView;
import com.example.chatapplication.model.view.EpisodeView;
import com.example.chatapplication.model.view.MovieView;
import com.example.chatapplication.model.view.MoviesHomeView;
import com.example.chatapplication.repository.CategoryRepository;
import com.example.chatapplication.repository.MoviesRepository;
import com.example.chatapplication.repository.SubMovieRepository;
import com.example.chatapplication.service.impl.AbstractJpaDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class MoviesQueryService extends AbstractJpaDAO<Movies> {

    private final MoviesRepository moviesRepository;

    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;

    private final SubMovieRepository subMovieRepository;

    public MoviesQueryService(MoviesRepository moviesRepository,
                              ObjectMapper objectMapper,
                              CategoryRepository categoryRepository,
                              SubMovieRepository subMovieRepository
    ) {
        super(Movies.class);
        this.moviesRepository = moviesRepository;
        this.objectMapper = objectMapper;
        this.categoryRepository = categoryRepository;
        this.subMovieRepository = subMovieRepository;
    }


    public CommonRes<?> findMovies(MoviesFilterQuery query) {
//        moviesRepository.
        Integer year = null;
        if (query.getYear() != null) {
            year = query.getYear();
            query.setYear(null);
        }
        Map<String, Object> queryDb = new HashMap<>();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        queryDb = objectMapper.convertValue(queryDb, Map.class);
        QueryDto queryDto = new QueryDto();
        queryDto.setFilters(queryDb);
        queryDto.setPageNumber(query.pageable().getPageNumber());
        queryDto.setPageSize(query.pageable().getPageSize());
        ResponseListAll<Movies> result = null;
        try {
            result = findsEntity(queryDto);
        } catch (Exception e) {
            log.error("", e);
            return Utils.createErrorResponse(500, e.getMessage());
        }
        Optional<Integer> yearOptional = Optional.ofNullable(year);
        List<Movies> finalList = yearOptional.isPresent() ? result.getData().stream().filter(e -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(e.getReleaseDate());
            return yearOptional.get() == calendar.get(Calendar.YEAR);
        }).collect(Collectors.toList()) : result.getData();
        List<MovieView> movieViews = finalList.stream().map(e -> {
            List<Category> categories = categoryRepository.findByMovieId(e.getId());
            return convertToView(e, categories);
        }).collect(Collectors.toList());
        return Utils.createSuccessResponse(Utils.toPage(movieViews, query.pageable()));
    }


    public CommonRes<?> findDetailMovie(Long id) {
        Optional<Movies> moviesOptional = moviesRepository.findById(id);
        List<Category> categories = categoryRepository.findByMovieId(id);
        List<SubMovie> subMovies = new ArrayList<>();
        if (moviesOptional.isPresent()) {
            Movies movies = moviesOptional.get();
            movies.setView(movies.getView() == null ? 1 : movies.getView() + 1);
            moviesRepository.save(movies);
            subMovies = subMovieRepository.findByMovieIdOrderByEpisode(id);
        }
        return Utils.createSuccessResponse(convertToView(moviesOptional.isEmpty() ? new Movies() : moviesOptional.get(), categories,subMovies));
    }

    private MovieView convertToView(Movies movies, List<Category> categories) {
        MovieView movieView = new MovieView();
        BeanUtil.copyProperties(movies, movieView, false);
        List<CategoryView> categoryViews = categories.stream().map(e -> convertToCategoryView(e)).collect(Collectors.toList());
        movieView.setCategories(categoryViews);
        return movieView;
    }

    private MovieView convertToView(Movies movies, List<Category> categories,List<SubMovie> subMovies) {
        MovieView movieView = new MovieView();
        BeanUtil.copyProperties(movies, movieView, false);
        List<CategoryView> categoryViews = categories.stream().map(e -> convertToCategoryView(e)).collect(Collectors.toList());
        movieView.setCategories(categoryViews);
        List<EpisodeView> episodeViews = subMovies.stream().map(e-> EpisodeView.builder()
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

    public CommonRes<?> getMoviesHome() {
        MoviesHomeView moviesHomeView = new MoviesHomeView();
        List<Movies> anime = moviesRepository.findMoviesByCategoryCode("ANIME", 1, PageRequest.of(0, 15)).getContent();
        List<Movies> movie = moviesRepository.findMoviesByMoviesTypeAndActiveOrderByReleaseDateDesc(com.example.chatapplication.common.Category.MoviesType.MOVIES.name(), 1);
        List<Movies> series = moviesRepository.findMoviesByMoviesTypeAndActiveOrderByReleaseDateDesc(com.example.chatapplication.common.Category.MoviesType.SERIES.name(), 1);
        List<Movies> newest = moviesRepository.findMoviesByActiveOrderByRateDescReleaseDateDesc(1, PageRequest.of(0, 15)).getContent();
        moviesHomeView.setAnime(convertToListMoviewView(anime));
        moviesHomeView.setSeries(convertToListMoviewView(series));
        moviesHomeView.setMovies(convertToListMoviewView(movie));
        moviesHomeView.setNewest(convertToListMoviewView(newest));
        return Utils.createSuccessResponse(moviesHomeView);
    }

    private List<MovieView> convertToListMoviewView(List<Movies> movies) {
        return movies.stream().map(e -> {
            List<Category> categories = categoryRepository.findByMovieId(e.getId());
            return convertToView(e, categories);
        }).collect(Collectors.toList());
    }

    public CommonRes<?> searchMovie(MoviesFilterQuery filter) {
        List<String> categoryCode = filter.getCategories() == null || filter.getCategories().isEmpty() ? new ArrayList<>() : Arrays.asList(filter.getCategories().split(","));
        List<String> movieTypes = filter.getMoviesType() == null || filter.getMoviesType().isEmpty() ? new ArrayList<>() : Arrays.asList(filter.getMoviesType().split(","));
        if (movieTypes.isEmpty()) {
            movieTypes.add(com.example.chatapplication.common.Category.MoviesType.MOVIES.name());
            movieTypes.add(com.example.chatapplication.common.Category.MoviesType.SERIES.name());
        }
        Page<Movies> movies;
        if (!categoryCode.isEmpty()) {
            movies = moviesRepository.searchMovieWithCategory(categoryCode, 1, filter.getName(), movieTypes, filter.pageable());
        } else {
            movies = moviesRepository.searchMovieWithNoCategory(1, filter.getName(), movieTypes, filter.pageable());
        }
        return Utils.createSuccessResponse(convertToListMoviewView(movies.getContent()));
    }

    public List<Movies> getAllMovie(){
        return moviesRepository.findAll();
    }

    public CommonRes<?> deleteMovie(Long id) {
        Optional<Movies> moviesOptional = moviesRepository.findById(id);
        if (moviesOptional.isPresent()) {
            moviesRepository.delete(moviesOptional.get());
            return Utils.createSuccessResponse(ResponseMessage.builder().message("SUCCESS").build());
        }
        return Utils.createSuccessResponse(ResponseMessage.builder().message("FAIL").build());

    }
}

package com.example.chatapplication.service.read;


import com.example.chatapplication.common.BeanUtil;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.Category;
import com.example.chatapplication.domain.Movies;
import com.example.chatapplication.model.query.MoviesQuery;
import com.example.chatapplication.model.query.QueryDto;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.response.ResponseListAll;
import com.example.chatapplication.model.view.CategoryView;
import com.example.chatapplication.model.view.MovieView;
import com.example.chatapplication.repository.CategoryRepository;
import com.example.chatapplication.repository.MoviesRepository;
import com.example.chatapplication.service.impl.AbstractJpaDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class MoviesQueryService extends AbstractJpaDAO<Movies> {

    private final MoviesRepository moviesRepository;

    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;

    public MoviesQueryService(MoviesRepository moviesRepository,
                              ObjectMapper objectMapper,
                              CategoryRepository categoryRepository) {
        super(Movies.class);
        this.moviesRepository = moviesRepository;
        this.objectMapper = objectMapper;
        this.categoryRepository = categoryRepository;
    }


    public CommonRes<?> findMovies(MoviesQuery query) {
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


    public CommonRes<?> findDetailMovie(Long id){
        Optional<Movies> moviesOptional = moviesRepository.findById(id);
        List<Category> categories = categoryRepository.findByMovieId(id);
        return Utils.createSuccessResponse(convertToView(moviesOptional.isEmpty() ? new Movies() : moviesOptional.get(),categories));
    }
    private MovieView convertToView(Movies movies, List<Category> categories) {
        MovieView movieView = new MovieView();
        BeanUtil.copyProperties(movies, new MovieView(), true);
        List<CategoryView> categoryViews = categories.stream().map(e->convertToCategoryView(e)).collect(Collectors.toList());
        movieView.setCategories(categoryViews);
        return movieView;
    }

    private Optional<Category> findOneById(List<Category> categories, Long id) {
        return categories.stream().filter(e -> id == e.getId()).findFirst();
    }

    private CategoryView convertToCategoryView(Category category){
        return CategoryView.builder()
                .code(category.getCode())
                .name(category.getName())
                .build();
    }
}

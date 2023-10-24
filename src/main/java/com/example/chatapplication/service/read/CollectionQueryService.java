package com.example.chatapplication.service.read;


import com.example.chatapplication.common.BeanUtil;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.Category;
import com.example.chatapplication.domain.Movies;
import com.example.chatapplication.model.query.CollectionQuery;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.view.CategoryView;
import com.example.chatapplication.model.view.MovieView;
import com.example.chatapplication.repository.CategoryRepository;
import com.example.chatapplication.repository.MoviesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class CollectionQueryService {

    private final MoviesRepository moviesRepository;
    private final CategoryRepository categoryRepository;

    public CommonRes<?> findMyCollection(String username, CollectionQuery query){
        Page<Movies> collections =  moviesRepository.findCollectionByUsername(username,1,query.pageable());
        Page<MovieView> movieViews = collections.map(e -> {
            List<Category> categories = categoryRepository.findByMovieId(e.getId());
            return convertToView(e, categories);
        });
        return Utils.createSuccessResponse(movieViews);
    }

    private MovieView convertToView(Movies movies, List<Category> categories) {
        MovieView movieView = new MovieView();
        BeanUtil.copyProperties(movies, new MovieView(), true);
        List<CategoryView> categoryViews = categories.stream().map(e->convertToCategoryView(e)).collect(Collectors.toList());
        movieView.setCategories(categoryViews);
        return movieView;
    }


    private CategoryView convertToCategoryView(Category category){
        return CategoryView.builder()
                .code(category.getCode())
                .name(category.getName())
                .build();
    }
}

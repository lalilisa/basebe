package com.example.chatapplication.service.read;

import com.example.chatapplication.domain.Category;
import com.example.chatapplication.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public List<Category> categories(){

        return  categoryRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION,"id"));
    }
}

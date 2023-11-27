package com.example.chatapplication.service.read;

import com.example.chatapplication.domain.Category;
import com.example.chatapplication.model.view.CategoryView;
import com.example.chatapplication.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public List<Category> categories(boolean isAdmin){
        return !isAdmin ?  categoryRepository.findByActiveOrderByName(true) : categoryRepository.findAll();
    }

    public CategoryView categories(Long id){
        return  convertToView(categoryRepository.findById(id).orElse(null));
    }

    public CategoryView convertToView(Category domain) {

        return domain == null ? new CategoryView() : CategoryView.builder()
                .id(domain.getId())
                .name(domain.getName())
                .code(domain.getCode())
                .active(domain.getActive())
                .build();
    }
}

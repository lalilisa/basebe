package com.example.chatapplication.service.write;


import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.Category;
import com.example.chatapplication.model.command.CategoryCommand;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.view.CategoryView;
import com.example.chatapplication.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryCommandService {

    private final CategoryRepository categoryRepository;

    public CommonRes<?> createCategory(CategoryCommand categoryCommand) {
        Category category = Category.builder()
                .active(categoryCommand.getActive())
                .code(categoryCommand.getCode())
                .name(categoryCommand.getName())
                .build();
        return Utils.createSuccessResponse(convertToView(categoryRepository.save(category)));
    }

    public CommonRes<?> updateCategory(Long categoryId, CategoryCommand categoryCommand) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            log.error("CATEGORY IS NOT FOUND");
            return Utils.createErrorResponse(400, "Dạnh mục không tồn tại");
        }
        Category category = categoryOptional.get();
        category.setActive(categoryCommand.getActive() != null ? categoryCommand.getActive() : category.getActive());
        category.setCode(categoryCommand.getCode() != null ? categoryCommand.getCode() : category.getCode());
        category.setName(categoryCommand.getName() != null ? categoryCommand.getName() : category.getName());
        return Utils.createSuccessResponse(convertToView(categoryRepository.save(category)));
    }

    public CommonRes<?> deleteCategory(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            log.error("CATEGORY IS NOT FOUND");
            return Utils.createErrorResponse(400, "Dạnh mục không tồn tại");
        }
        categoryRepository.delete(categoryOptional.get());
        return Utils.createSuccessResponse("SUCCESS");
    }
    public CategoryView convertToView(Category domain) {
        return CategoryView.builder()
                .id(domain.getId())
                .active(domain.getActive())
                .name(domain.getName())
                .code(domain.getCode())
                .build();
    }
}

package kz.kop_flowers.controller;

import kz.kop_flowers.model.dto.CategoryDto;
import kz.kop_flowers.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    @DisplayName("getAllCategories: возвращает список категорий и вызывает сервис")
    void getAllCategories_returnsListAndCallsService() {
        CategoryDto dto1 = CategoryDto.builder().id(1).name("Roses").build();
        CategoryDto dto2 = CategoryDto.builder().id(2).name("Tulips").build();

        when(categoryService.getAllCategories()).thenReturn(List.of(dto1, dto2));

        List<CategoryDto> result = categoryController.getAllCategories();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(CategoryDto::getName)
                .containsExactlyInAnyOrder("Roses", "Tulips");

        verify(categoryService).getAllCategories();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("createCategory: делегирует в сервис и возвращает результат")
    void createCategory_delegatesToService() {
        CategoryDto requestDto = CategoryDto.builder()
                .name("Roses")
                .build();

        CategoryDto responseDto = CategoryDto.builder()
                .id(1)
                .name("Roses")
                .build();

        when(categoryService.createCategory(requestDto)).thenReturn(responseDto);

        CategoryDto result = categoryController.createCategory(requestDto);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Roses");

        verify(categoryService).createCategory(requestDto);
        verifyNoMoreInteractions(categoryService);
    }
}

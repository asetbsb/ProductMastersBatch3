package kz.kop_flowers.service;

import kz.kop_flowers.model.FlowerMapper;
import kz.kop_flowers.model.dto.CategoryDto;
import kz.kop_flowers.model.entity.Category;
import kz.kop_flowers.model.exception.CategoryNotFoundException;
import kz.kop_flowers.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private FlowerMapper mapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("getCategoryById: возвращает категорию, когда она существует")
    void getCategoryById_existingCategory_returnsCategory() {
        Integer id = 1;
        Category category = Category.builder()
                .id(id)
                .name("Roses")
                .build();

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Roses");

        verify(categoryRepository).findById(id);
        verifyNoMoreInteractions(categoryRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("getCategoryById: кидает CategoryNotFoundException, когда категория не найдена")
    void getCategoryById_notExisting_throwsException() {
        Integer id = 999;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getCategoryById(id))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessage("Category is not found");

        verify(categoryRepository).findById(id);
        verifyNoMoreInteractions(categoryRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("getCategoryDtoById: возвращает DTO, когда категория существует")
    void getCategoryDtoById_existingCategory_returnsDto() {
        Integer id = 1;
        Category category = Category.builder()
                .id(id)
                .name("Roses")
                .build();

        CategoryDto categoryDto = CategoryDto.builder()
                .id(id)
                .name("Roses")
                .build();

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(mapper.fromEntityToDto(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.getCategoryDtoById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Roses");

        verify(categoryRepository).findById(id);
        verify(mapper).fromEntityToDto(category);
        verifyNoMoreInteractions(categoryRepository, mapper);
    }

    @Test
    @DisplayName("getCategoryDtoById: кидает CategoryNotFoundException, когда категория не найдена")
    void getCategoryDtoById_notExisting_throwsException() {
        Integer id = 999;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getCategoryDtoById(id))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessage("Category is not found");

        verify(categoryRepository).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("getAllCategories: возвращает список DTO, когда категории есть")
    void getAllCategories_nonEmptyList_returnsDtos() {
        Category category1 = Category.builder().id(1).name("Roses").build();
        Category category2 = Category.builder().id(2).name("Tulips").build();

        CategoryDto dto1 = CategoryDto.builder().id(1).name("Roses").build();
        CategoryDto dto2 = CategoryDto.builder().id(2).name("Tulips").build();

        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));
        when(mapper.fromEntityToDto(category1)).thenReturn(dto1);
        when(mapper.fromEntityToDto(category2)).thenReturn(dto2);

        List<CategoryDto> result = categoryService.getAllCategories();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(CategoryDto::getName)
                .containsExactlyInAnyOrder("Roses", "Tulips");

        verify(categoryRepository).findAll();
        verify(mapper).fromEntityToDto(category1);
        verify(mapper).fromEntityToDto(category2);
        verifyNoMoreInteractions(categoryRepository, mapper);
    }

    @Test
    @DisplayName("getAllCategories: возвращает пустой список, когда категорий нет")
    void getAllCategories_emptyList_returnsEmpty() {
        when(categoryRepository.findAll()).thenReturn(List.of());

        List<CategoryDto> result = categoryService.getAllCategories();

        assertThat(result).isNotNull().isEmpty();

        verify(categoryRepository).findAll();
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("createCategory: сохраняет новую категорию и возвращает DTO")
    void createCategory_validDto_savesAndReturnsDto() {
        CategoryDto requestDto = CategoryDto.builder()
                .name("Roses")
                .build();

        Category savedEntity = Category.builder()
                .id(1)
                .name("Roses")
                .build();

        CategoryDto responseDto = CategoryDto.builder()
                .id(1)
                .name("Roses")
                .build();

        when(categoryRepository.save(any(Category.class))).thenReturn(savedEntity);
        when(mapper.fromEntityToDto(savedEntity)).thenReturn(responseDto);

        CategoryDto result = categoryService.createCategory(requestDto);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Roses");

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(captor.capture());
        Category captured = captor.getValue();
        assertThat(captured.getId()).isNull(); // при создании id ещё нет
        assertThat(captured.getName()).isEqualTo("Roses");

        verify(mapper).fromEntityToDto(savedEntity);
        verifyNoMoreInteractions(categoryRepository, mapper);
    }
}

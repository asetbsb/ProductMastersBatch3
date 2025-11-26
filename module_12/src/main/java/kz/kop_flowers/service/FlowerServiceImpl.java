package kz.kop_flowers.service;

import kz.kop_flowers.model.FlowerMapper;
import kz.kop_flowers.model.dto.FlowerDto;
import kz.kop_flowers.model.entity.Flower;
import kz.kop_flowers.model.exception.FlowerNotFoundException;
import kz.kop_flowers.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowerServiceImpl implements FlowerService {

    private final FlowerRepository flowerRepository;
    private final FlowerMapper mapper;
    private final CategoryService categoryService;

    @Override
    public List<FlowerDto> getAllFlowers() {
        List<Flower> flowers = flowerRepository.findAll();
        return flowers.stream()
                .map(mapper::fromEntityToDto)
                .toList();
    }

    @Override
    public FlowerDto getFlowerById(Integer id) {
        Flower flower = flowerRepository.findById(id)
                .orElseThrow(() -> new FlowerNotFoundException("Flower is not exists"));
        return mapper.fromEntityToDto(flower);
    }

    @Override
    public FlowerDto createFlower(FlowerDto flowerDto) {
        Flower flower = Flower.builder()
                .name(flowerDto.getName())
                .price(flowerDto.getPrice())
                .size(flowerDto.getSize())
                .category(categoryService.getCategoryById(flowerDto.getCategory().getId()))
                .build();
        flower = flowerRepository.save(flower);
        return mapper.fromEntityToDto(flower);
    }
    @Override
    public void deleteFlowerById(Integer id) {
        Flower flower = flowerRepository.findById(id)
                .orElseThrow(() -> new FlowerNotFoundException("Flower is not exists"));
        flowerRepository.delete(flower);
    }
    @Override
    public List<FlowerDto> getFlowersByCategoryId(Integer categoryId) {
        // можно дополнительно проверить, что категория существует:
        // categoryService.getCategoryById(categoryId);
        return flowerRepository.findByCategoryId(categoryId).stream()
                .map(mapper::fromEntityToDto)
                .toList();
    }
    @Override
    public FlowerDto updateFlower(Integer id, FlowerDto flowerDto) {
        Flower existingFlower = flowerRepository.findById(id)
                .orElseThrow(() -> new FlowerNotFoundException("Flower is not exists"));

        existingFlower.setName(flowerDto.getName());
        existingFlower.setPrice(flowerDto.getPrice());
        existingFlower.setSize(flowerDto.getSize());

        if (flowerDto.getCategory() != null && flowerDto.getCategory().getId() != null) {
            existingFlower.setCategory(
                    categoryService.getCategoryById(flowerDto.getCategory().getId())
            );
        }

        existingFlower = flowerRepository.save(existingFlower);
        return mapper.fromEntityToDto(existingFlower);
    }
}

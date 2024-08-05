package rw.global.qt.bloggy.services.serviceImpls;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.global.qt.bloggy.dtos.requests.CreateCategoryDTO;
import rw.global.qt.bloggy.exceptions.ResourceNotFoundException;
import rw.global.qt.bloggy.models.Category;
import rw.global.qt.bloggy.repositories.ICategoryRepository;
import rw.global.qt.bloggy.services.ICategoryService;
import rw.global.qt.bloggy.utils.ExceptionUtils;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    @Override
    public Category createCategory(CreateCategoryDTO category) {
        try{
            Category newCategory = new Category(category.getName(), category.getDescription());
            return categoryRepository.save(newCategory);
        }catch (Exception e){
            e.printStackTrace();
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public Category getCategoryById(UUID id) {
        try {
            return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public Category updateCategory(UUID id, CreateCategoryDTO category) {
        try {
            Category categoryToUpdate = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            categoryToUpdate.setName(category.getName());
            categoryToUpdate.setDescription(category.getDescription());
            return categoryRepository.save(categoryToUpdate);}
        catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }

    }

    @Override
    public void deleteCategory(UUID id) {
        try {
            Category categoryToDelete = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            categoryRepository.delete(categoryToDelete);
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
        }

    }

    @Override
    public Category getCategoryByName(String name) {
        try {
            return categoryRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }
}

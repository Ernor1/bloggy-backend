package rw.global.qt.bloggy.services;

import rw.global.qt.bloggy.dtos.requests.CreateCategoryDTO;
import rw.global.qt.bloggy.models.Category;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    public Category createCategory(CreateCategoryDTO category);
    public Category getCategoryById(UUID id);
    public Category updateCategory(UUID id, CreateCategoryDTO category);
    public void deleteCategory(UUID id);
    public Category getCategoryByName(String name);
    public List<Category> getAllCategories();
    public List<Category> getCategoriesByBlog(UUID blogId);
}

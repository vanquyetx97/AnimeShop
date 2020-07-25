package application.data.service;

import application.data.model.Category;
import application.data.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {



    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void addNewListCategories(List<Category> categoryList) {
        try {
            categoryRepository.save(categoryList);
        } catch (Exception e) {
        }
    }

    public void addNewCategory(Category category) {
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
        }
    }

    public Category findOne(int categoryId) {
        return categoryRepository.findOne(categoryId);
    }

    public boolean updateCategory(Category category) {
        try {
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public List<Category> getListAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public long getTotalCategories(){
        return categoryRepository.getTotalCategories();
    }

    public Page<Category> getListCategoryByCategoryNameContaining(Pageable pageable, String categoryName){
        return categoryRepository.getListCategoryByCategoryNameContaining(pageable,categoryName);
    }
}

package com.db.recipes.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.db.recipes.dto.RecipeDto;

/**
 * @author Geetha
 * 
 * This Class is implemented to test RecipesService Unit Tests.
 *
 */
@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class RecipesServiceTest {

	@Mock
	com.db.recipes.dao.RecipeDao recipeDao;
	
	@InjectMocks
	RecipesService recipesService;
	
	@Mock
	RecipeDto recipeDto;
	
	/**
	 * This is init method to initialize the objects
	 */
	public void init() {
		recipeDto.setRecipe_id(1);
		recipeDto.setRecipe_name("Noodles");
		recipeDto.setRecipe_type("Veg");
		recipeDto.setNum_of_servings(1);
		recipeDto.setIngredients("Wheat,Salt");
		recipeDto.setInstructions("oven");
	}

	/**
	 * This is unit test method for add recipe
	 */
	@Test
	public void testAddRecipe() {		
		Mockito.when(recipeDao.addRecipe(recipeDto)).thenReturn(1);
		int result=recipesService.addRecipe(recipeDto);
		assertEquals(1,result);
	}

	/**
	 * This is unit test method for update recipe
	 */
	@Test
	public void testUpdateRecipe() {		
		Mockito.when(recipeDao.updateRecipe(recipeDto,1)).thenReturn(1);
		int result=recipesService.updateRecipe(recipeDto,1);
		assertEquals(1,result);
	}
	
	/**
	 * This is unit test method for remove recipe
	 */
	@Test
	public void testRemoveRecipe() {		
		Mockito.when(recipeDao.removeRecipe(1)).thenReturn(1);
		int result=recipesService.removeRecipe(1);
		assertEquals(1,result);
	}
	
	/**
	 * This is unit test method for get recipe
	 */
	@Test
	public void testGetRecipe() {		
		Mockito.when(recipeDao.getRecipe(1)).thenReturn(recipeDto);
		recipeDto=recipesService.getRecipe(1);
		assertNotNull(recipeDto);
	}
	
	/**
	 * This is unit test method for get recipe by Filter
	 */
	@Test
	public void testGetRecipeByFilter() {		
		Map<String,String> recipeFilterMap=new HashMap<String,String>();
		 recipeFilterMap.put("recipe_type", "Veg"); 
		 recipeFilterMap.put("ingredients","Wheat");
		List<RecipeDto> mockList = new ArrayList<RecipeDto>();
		mockList.add(recipeDto);		 
		Mockito.when(recipeDao.getRecipeByFilter(recipeFilterMap)).thenReturn(mockList);
		mockList=recipesService.getRecipeByFilter(recipeFilterMap);
		assertEquals(1,mockList.size());
	}
	
}

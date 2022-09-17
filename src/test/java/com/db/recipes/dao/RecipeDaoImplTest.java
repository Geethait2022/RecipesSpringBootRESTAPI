package com.db.recipes.dao;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.db.recipes.dto.RecipeDto;

/**
 * @author Geetha
 * 
 * This Class is used to test RecipeDao unit tests
 *
 */
@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class RecipeDaoImplTest {
	
	@Mock
	RecipeDto recipeDto;
	
	@InjectMocks
	com.db.recipes.dao.RecipeDao recipeDao=new RecipeDaoImpl();
	
	@Mock
	JdbcTemplate jdbcTemplate;

	@Before
	public void init() {
		System.out.println("test....");
		recipeDto.setRecipe_id(1);
		recipeDto.setRecipe_name("Noodles");
		recipeDto.setRecipe_type("Veg");
		recipeDto.setNum_of_servings(1);
		recipeDto.setIngredients("Wheat,Salt");
		recipeDto.setInstructions("oven");
	}

	/**
	 * This method is used to test Add Recipe
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddRecipe() throws Exception{
		when(jdbcTemplate.update(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn(ArgumentMatchers.anyInt());
		assertNotNull(recipeDao.addRecipe(recipeDto));
	}
	
	/**
	 * This method is used to test remove recipe unit testing
	 */
	@Test
	public void testRemoveRecipe() throws Exception{		
		
		Mockito.when(jdbcTemplate.update(ArgumentMatchers.anyString(),ArgumentMatchers.anyLong())).thenReturn(ArgumentMatchers.anyInt());
		assertNotNull(recipeDao.removeRecipe(ArgumentMatchers.anyLong()));
	}
	
	/**
	 * This method is used to test get recipe unit testing
	 */
	@Test
	public void getRecipe() throws Exception{
		
		Mockito.when(jdbcTemplate.queryForObject(ArgumentMatchers.anyString(),new BeanPropertyRowMapper<>(RecipeDto.class),ArgumentMatchers.anyLong())).thenReturn(ArgumentMatchers.any(RecipeDto.class));
		assertNull(recipeDao.getRecipe(ArgumentMatchers.anyLong()));
	}
	
	/**
	 * This method is used to test remove recipe unit testing
	 */
	@Test
	public void testgetRecipeByFilter() throws Exception {
		Mockito.when(jdbcTemplate.query(ArgumentMatchers.anyString(),new BeanPropertyRowMapper<>(RecipeDto.class))).thenReturn(ArgumentMatchers.anyList());
		assertNotNull(recipeDao.getRecipeByFilter(ArgumentMatchers.anyMap()));
	}
}

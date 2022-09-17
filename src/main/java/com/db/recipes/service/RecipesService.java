/**
 * 
 */
package com.db.recipes.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.db.recipes.dao.RecipeDao;
import com.db.recipes.dto.RecipeDto;

/**
 * @author Geetha
 * 
 * This is a Service Class which is used to get input request from controller and pass to back end database.
 * Similarly get the information from database and send response to the controller.
 *
 */
@Service
public class RecipesService {
	
	@Autowired
	RecipeDao recipeDao;
	
	/**
	 * @param recipeDto
	 * @return int
	 */
	public int addRecipe(RecipeDto recipeDto)
	{
		return recipeDao.addRecipe(recipeDto);
	}
	/**
	 * @param recipeDto
	 * @param recipe_id
	 * @return int
	 */
	public int updateRecipe(RecipeDto recipeDto,long recipe_id)
	{
		return recipeDao.updateRecipe(recipeDto,recipe_id);		
		
	}
	/**
	 * @param recipe_id
	 * @return int
	 */
	public int removeRecipe(long recipe_id)
	{
		return recipeDao.removeRecipe(recipe_id);
	}
	

	/**
	 * @param recipe_id
	 * @return RecipeDto
	 */
	public RecipeDto getRecipe(long recipe_id) {
		
		return recipeDao.getRecipe(recipe_id);
	}

	
	/**
	 * @param recipeFilterMap
	 * @return List<RecipeDto>
	 */
	public List<RecipeDto> getRecipeByFilter(Map<String,String> recipeFilterMap) {
		
		return recipeDao.getRecipeByFilter(recipeFilterMap);
	}

}

/**
 * 
 */
package com.db.recipes.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.db.recipes.dto.RecipeDto;

/**
 * @author Geetha
 * 
 * This is a Interface to process Recipes
 *
 */
@Component
public interface RecipeDao {

	/**
	 * @param RecipeDto
	 * @return int
	 */
	public int addRecipe(RecipeDto RecipeDto);

	/**
	 * @param RecipeDto
	 * @param recipe_id
	 * @return int
	 */
	public int updateRecipe(RecipeDto RecipeDto, long recipe_id);

	/**
	 * @param recipe_id
	 * @return int
	 */
	public int removeRecipe(long recipe_id);

	/**
	 * @param recipe_id
	 * @return RecipeDto
	 */
	public RecipeDto getRecipe(long recipe_id);

	/**
	 * @param Map<String,String> recipeFilterMap
	 * @return List<RecipeDto>
	 */
	public List<RecipeDto> getRecipeByFilter(Map<String, String> recipeFilterMap);

}

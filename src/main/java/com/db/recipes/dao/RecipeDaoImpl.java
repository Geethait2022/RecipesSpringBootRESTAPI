/**
 * 
 */
package com.db.recipes.dao;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.db.recipes.dto.RecipeDto;
import lombok.extern.log4j.Log4j2;

/**
 * @author Geetha
 * 
 * This Class is responsible to interact with database and process the requests based on user inputs.
 * It allows to add recipe,delete recipe,update recipe,get recipe and filter recipes  
 *
 */
@Repository
@Log4j2
public class RecipeDaoImpl implements RecipeDao {
	
	private static final String insertRecipeSql="INSERT INTO RECIPE(recipe_name, recipe_type,num_of_servings,ingredients,instructions) VALUES (?,?,?,?,?)";
	private static final String updateRecipeSql="UPDATE RECIPE SET recipe_name=?,recipe_type=?, num_of_servings=?, ingredients=?, instructions=? WHERE recipe_id=?";
	private static final String removeRecipeSql="DELETE FROM RECIPE WHERE recipe_id=?";
	private static final String getRecipeSql="SELECT * FROM RECIPE WHERE recipe_id=?";
	private static final String recipeFilterSql="SELECT * FROM RECIPE WHERE ";
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	RecipeDto recipeDto;
	
	/**
	 * This method will insert recipe data to database
	 * 
	 * @param RecipeDto
	 * @return int
	 */
	@Override
	public int addRecipe(RecipeDto recipeDto) {		
		log.debug("Start->RecipeDaoImpl->addRecipe");
		return jdbcTemplate.update(insertRecipeSql,
				recipeDto.getRecipe_name(),recipeDto.getRecipe_type(),recipeDto.getNum_of_servings(),recipeDto.getIngredients(),recipeDto.getInstructions());
	}
	
	/**
	 * This method will update the recipe information
	 * @param RecipeDto
	 * @param recipe_id
	 * @return int
	 */
	@Override
	public int updateRecipe(RecipeDto recipeDto,long recipe_id) {
		log.debug("Start->RecipeDaoImpl->updateRecipe");
		return jdbcTemplate.update(updateRecipeSql,new Object[] {
				recipeDto.getRecipe_name(),recipeDto.getRecipe_type(),recipeDto.getNum_of_servings(),
				recipeDto.getIngredients(),recipeDto.getInstructions(),recipe_id});
	}

	/**
	 * This method will remove the recipe information from database
	 * @param recipe_id
	 * @return int
	 */
	@Override
	public int removeRecipe(long recipe_id) {		
		log.debug("Start->RecipeDaoImpl->removeRecipe");
		return	jdbcTemplate.update(removeRecipeSql,recipe_id);		
	}

	/**
	 * This method will get the recipe information from database
	 * @param recipe_id
	 * @return RecipeDto
	 */
	@Override
	public RecipeDto getRecipe(long recipe_id) {			
		log.debug("Start->RecipeDaoImpl->getRecipe");	
		return jdbcTemplate.queryForObject(getRecipeSql,new BeanPropertyRowMapper<RecipeDto>(RecipeDto.class),recipe_id);
	}

	/**
	 * This method will get the recipes information based on filter criteria
	 * @param recipeFilterMap
	 * @return List<RecipeDto>
	 */
	@Override
	public List<RecipeDto> getRecipeByFilter(Map<String,String> recipeFilterMap) {
		
        log.debug("Start->RecipeDaoImpl->getRecipeByFilter");      
        StringBuilder sbQuery=new StringBuilder(); 
        int i=0;
        for (Map.Entry<String, String> item : recipeFilterMap.entrySet()) {        	
        	i++;        	
        	if(item.getKey().equalsIgnoreCase("num_of_servings"))
        	{
        		sbQuery.append(item.getKey()+" = "+item.getValue()+" ");
        	} 
        	else if(item.getKey().equalsIgnoreCase("recipe_type"))
        	{
        		sbQuery.append(item.getKey()+" = '"+item.getValue()+"' ");
        	}
        	else if(item.getKey().equalsIgnoreCase("noingredients"))
        	{
        		sbQuery.append("ingredients"+" NOT LIKE '%"+item.getValue()+"%'");        		
        	}
        	else 
        	{
                sbQuery.append(item.getKey()+" LIKE '%"+item.getValue()+"%'");
        	}            
            if(i!=recipeFilterMap.size())
            {
            	sbQuery.append(" AND ");
            }
        }
        log.info("Recipe Filter Query: "+ recipeFilterSql+sbQuery);
        
		return jdbcTemplate.query(recipeFilterSql+sbQuery,new BeanPropertyRowMapper<RecipeDto>(RecipeDto.class));
	}

}

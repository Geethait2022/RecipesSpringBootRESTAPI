/**
 * 
 */
package com.db.recipes.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Geetha
 * 
 * This is Data Transfer Object Class, which is used to transfer data between layers and end user.
 * Lombok feature is used. Hence setters and getters are not written.
 *
 */

@Getter
@Setter
@Component
public class RecipeDto {
	
	/**
	 * This is used for recipe id
	 */
	private int recipe_id;
	/**
	 * This is used for recipe name
	 */
	private String recipe_name;
	
	/**
	 * This is used to determine recipe type like Vegetarian or Non-Vegetarian
	 */
	private String recipe_type;
	
	/**
	 * This is used to determine number of servings for one recipe
	 */
	private int num_of_servings;
	
	
	/**
	 * This is used to give detail information about recipe ingredients 
	 */
	private String ingredients;
	
	/**
	 * This is used to give instructions about the recipe like recipe can be prepared using oven
	 */
	private String instructions;
	

}

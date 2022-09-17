/**
 * 
 */
package com.db.recipes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.db.recipes.dto.RecipeDto;
import com.db.recipes.service.RecipesService;
import lombok.extern.log4j.Log4j2;

/**
 * @author Geetha 
 * 
 * This Class handles HTTP requests with POST,PUT,GET and DELETE methods.
 *
 */
@RestController
@RequestMapping("/recipes")
@Log4j2
public class RecipesController {

	@Autowired
	RecipesService recipesService;

	@Autowired
	RecipeDto recipeDto;

	/**
	 * This method allows user to add recipe to database
	 * 
	 * @param recipeDto
	 * @return ResponseEntity<String>
	 */
	@PostMapping("/addrecipe")
	public ResponseEntity<String> addRecipe(@RequestBody RecipeDto recipeDto) {
		log.info("Start-> RecipesController->addRecipe");

		try {
			int numRows = recipesService.addRecipe(recipeDto);
			return new ResponseEntity<>("Added " + numRows + " Recipe successfully.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Cannot Add Recipe.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This method allows the user to update recipe based on recipe_id
	 * 
	 * @param recipeDto
	 * @param recipe_id
	 * @return ResponseEntity<String>
	 */
	@PutMapping("/updaterecipe/{recipe_id}")
	public ResponseEntity<String> updateRecipe(@RequestBody RecipeDto recipeDto, @PathVariable long recipe_id){
		log.info("Start-> RecipesController->updateRecipe");
		
		try {
			int numRows = recipesService.updateRecipe(recipeDto, recipe_id);
			return new ResponseEntity<>("Updated " + numRows + " Recipe successfully.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Cannot Update Recipe.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This method allows user to remove recipe from database
	 * 
	 * @param recipe_id
	 * @return ResponseEntity<String>
	 */
	@DeleteMapping("/removerecipe/{recipe_id}")
	public ResponseEntity<String> removeRecipe(@PathVariable long recipe_id)  {
		log.info("Start-> RecipesController->removeRecipe");
		try {
			int numRows = recipesService.removeRecipe(recipe_id);
			return new ResponseEntity<>("Removed " + numRows + " Recipe successfully.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Cannot Remove Recipe.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This method allows user to get recipe based on recipe id
	 * 
	 * @param recipe_id
	 * @return ResponseEntity<RecipeDto>
	 */
	@GetMapping("/getrecipe/{recipe_id}")
	public ResponseEntity<RecipeDto>  getRecipe(@PathVariable long recipe_id)  {
		log.info("Start-> RecipesController->getRecipe");		
		try {
			recipeDto = recipesService.getRecipe(recipe_id);
			if (recipeDto==null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		      return new ResponseEntity<>(recipeDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	/**
	 * This method allows user to perform recipe search based on filters
	 * User can filter recipes based on recipe_type, num_of_servings, ingredients, instructions
	 * 
	 * @param recipe_type
	 * @param num_of_servings
	 * @param ingredients
	 * @param instructions
	 * @return ResponseEntity<List<RecipeDto>>
	 */
	@GetMapping("/filterrecipe")
	public ResponseEntity<List<RecipeDto>> getRecipeByFilter(@RequestParam(required = false) String recipe_type,
			@RequestParam(required = false) Integer num_of_servings, @RequestParam(required = false) String ingredients,
			@RequestParam(required = false) String noingredients,@RequestParam(required = false) String instructions) throws Exception {
		log.info("Start-> RecipesController->getRecipeByFilter"+noingredients);
		
		Map<String,String> recipeFilterMap=new HashMap<String,String>();
 		if (recipe_type != null)
 			recipeFilterMap.put("recipe_type", recipe_type);
		if (null != num_of_servings)
			recipeFilterMap.put("num_of_servings", num_of_servings.toString());
		if (ingredients != null)
			recipeFilterMap.put("ingredients", ingredients);
		if (noingredients != null)
			recipeFilterMap.put("noingredients", noingredients);
		if (instructions != null)
			recipeFilterMap.put("instructions", instructions);
		try {
			List<RecipeDto> recipeDtoList;
			recipeDtoList = recipesService.getRecipeByFilter(recipeFilterMap);
				if (recipeDtoList.size()==0) {
				 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
		    return new ResponseEntity<>(recipeDtoList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

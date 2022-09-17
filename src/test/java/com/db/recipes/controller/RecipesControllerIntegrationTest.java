package com.db.recipes.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.db.recipes.dto.RecipeDto;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Geetha
 * 
 * This is Integration Testing class to test RecipesController.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@SpringBootConfiguration
@SpringBootApplication(scanBasePackages={"com.db.recipes"})
public class RecipesControllerIntegrationTest {

	@Autowired
	private RecipeDto recipeDtoAdd;
	
	@Autowired
	private RecipeDto recipeDtoUpdate;

	@Autowired
	private RecipesController recipesController;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper = new ObjectMapper();
	private String objectMapperJson;
	private List<RecipeDto> list = new ArrayList<RecipeDto>();

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.recipesController).build();
		recipeDtoAdd.setRecipe_name("CakeBanana");
		recipeDtoAdd.setRecipe_type("Vegetarian");
		recipeDtoAdd.setNum_of_servings(1);
		recipeDtoAdd.setIngredients("Wheat Flour,Salt,Sugar,Banana");
		recipeDtoAdd.setInstructions("Oven");
		objectMapperJson = objectMapper.writeValueAsString(recipeDtoAdd);
		list.add(recipeDtoAdd);
	}

	/**
	 * This method is used to test add recipe integration testing between Controller,Service and Dao.
	 * If all the layers are passed it will give status as 200 and record will be saved in database.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddRecipe_Success() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(post("/recipes/addrecipe").content(objectMapperJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
    
	/**
	 * This method is used to test update recipe integration testing between Controller,Service and Dao.
	 * If all the layers are passed it will give status as 200 and record will be update in database.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateRecipe_Success() throws Exception {
		recipeDtoUpdate.setRecipe_name("CakeApple");
		recipeDtoUpdate.setRecipe_type("Vegetarian");
		recipeDtoUpdate.setNum_of_servings(1);
		recipeDtoUpdate.setIngredients("Wheat Flour,Salt,Sugar");
		recipeDtoUpdate.setInstructions("Oven");
		objectMapperJson = objectMapper.writeValueAsString(recipeDtoUpdate);
		MvcResult mvcResult = mockMvc.perform(put("/recipes/updaterecipe/{recipe_id}", 4).content(objectMapperJson)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	/**
	 * This method is used to test remove recipe integration testing between Controller,Service and DAO.
	 * If all the layers are passed it will remove the record from database.
	 * @throws Exception
	 */
	@Test
	public void testRemoveRecipe_Success() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(delete("/recipes/removerecipe/{recipe_id}", "3").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	/**
	 * This method is implemented to test get recipe method integration testing.
	 * If all layers like Controller,Service and DAO are passed it will fetch the record based on recipe id.
	 * @throws Exception
	 */
	@Test
	public void testGetRecipe_Success() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(get("/recipes/getrecipe/{recipe_id}", "4").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	/**
	 * This method is implemented to test Recipe Filter Criteria.
	 * Here recipes are filtered based on recipe type in the current scenario.
	 * @throws Exception
	 */
	@Test
	public void testGetRecipeByFilter_Success() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/recipes/filterrecipe").param("recipe_type", "vegetarian")
	            .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

}

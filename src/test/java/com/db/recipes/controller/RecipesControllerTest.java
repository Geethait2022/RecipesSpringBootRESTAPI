package com.db.recipes.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.db.recipes.dto.RecipeDto;
import com.db.recipes.service.RecipesService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Geetha
 * 
 * This class is implemented to test RecipesController Unit Tests
 *
 */
@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class RecipesControllerTest {

	@InjectMocks
	private RecipeDto recipeDto;

	@InjectMocks
	RecipesController recipesController;

	@Mock
	RecipesService recipesService;

	private ObjectMapper objectMapper = new ObjectMapper();
	private String objectMapperJson;
	private List<RecipeDto> mockList = new ArrayList<RecipeDto>();
	private MockMvc mockMvc;	

	
	/**
	 * This method will initial the SpingBootcontext
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(recipesController).build();
		this.mockMvc = standaloneSetup(this.recipesController).build();
		recipeDto.setRecipe_id(1);
		recipeDto.setRecipe_name("Cake");
		recipeDto.setRecipe_type("Veg");
		recipeDto.setNum_of_servings(1);
		recipeDto.setIngredients("Wheat Flour,Salt,Sugar");
		recipeDto.setInstructions("Oven");
		objectMapperJson = objectMapper.writeValueAsString(recipeDto);
		mockList.add(recipeDto);
	}

	/**
	 * This method will mock add recipe objects and return the status 200. Success Scenario Testing
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddRecipe() throws Exception {
		when(recipesService.addRecipe(ArgumentMatchers.any(RecipeDto.class))).thenReturn(ArgumentMatchers.anyInt());
		MvcResult mvcResult = mockMvc
				.perform(post("/recipes/addrecipe").content(objectMapperJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	/**
	 * This method will mock update recipe objects and return the status 500. Negative Scenario Testing
	 * @throws Exception
	 */
	@Test
	public void testUpdateRecipe() throws Exception {
		when(recipesService.updateRecipe(ArgumentMatchers.any(RecipeDto.class), ArgumentMatchers.anyLong()))
				.thenReturn(ArgumentMatchers.anyInt());
		MvcResult mvcResult = mockMvc.perform(put("/recipes/updaterecipe/{recipe_id}", 1).content(objectMapperJson)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError()).andReturn();
		assertEquals(500, mvcResult.getResponse().getStatus());
	}

	/**
	 * This method will mock remove recipe objects and return the status 200. Success Scenario Testing.
	 * @throws Exception
	 */
	@Test
	public void testRemoveRecipe() throws Exception {
		when(recipesService.removeRecipe(ArgumentMatchers.anyInt())).thenReturn(ArgumentMatchers.anyInt());
		MvcResult mvcResult = mockMvc
				.perform(delete("/recipes/removerecipe/{recipe_id}", "3").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	/**
	 * This method will mock the get recipe objects and return the status 404. Negative Scenario Testing.
	 * @throws Exception
	 */
	@Test
	public void testGetRecipe() throws Exception {
		when(recipesService.getRecipe(ArgumentMatchers.anyLong())).thenReturn(ArgumentMatchers.any(RecipeDto.class));
		MvcResult mvcResult = mockMvc
				.perform(get("/recipes/getrecipe/{recipe_id}", "3").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
		assertEquals(404, mvcResult.getResponse().getStatus());
	}

	/**
	 * This method will mock the get recipe by filter objects and return the status 200. Success Scenairo Testing.
	 * @throws Exception
	 */
	@Test
	public void testGetRecipeByFilter() throws Exception {
		when(recipesService.getRecipeByFilter(ArgumentMatchers.anyMap())).thenReturn(mockList);
		MvcResult mvcResult = mockMvc.perform(get("/recipes/filterrecipe").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

}

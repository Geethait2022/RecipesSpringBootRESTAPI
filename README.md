This is SpringBoot REST API project. Implemented with CURL Operations and filter criteria.
Used Java 1.8, Maven, Lombok,Log4j2 and MySQL

Below are REST API endpoint's

Method	 URL
POST:	 http://localhost:8888/recipes/addrecipe

PUT:	     http://localhost:8888/recipes/updaterecipe/{recipe_id}

DELETE:	 http://localhost:8888/recipes/removerecipe/{recipe_id}

GET:	     http://localhost:8888/recipes/getrecipe/{recipe_id}

GET:	     http://localhost:8888/recipes/filterrecipe?recipe_type=vegetarian


This project has unit tests and integration tests and used tools like Mockito and JUnit.


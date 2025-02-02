package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;


import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {
	
	private final RecipeService recipeService;
	
    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";


	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	
	
	@GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
		Recipe recipe = recipeService.findById(Long.valueOf(id));
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }
	
	@GetMapping("recipe/new")
	public String newRecipe(Model model){
		model.addAttribute("recipe", new RecipeCommand());

	    return "recipe/recipeform";
	}
	
	@GetMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model){
	      model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
	      return  "recipe/recipeform";
	}
	
	@PostMapping("recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){
		
		 if(bindingResult.hasErrors()){

	            bindingResult.getAllErrors().forEach(objectError -> {
	                log.debug(objectError.toString());
	            });

	            return RECIPE_RECIPEFORM_URL;
	      }
		
		
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

	    return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
	  
	@GetMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable String id){

		log.debug("Deleting id: " + id);

		recipeService.deleteById(Long.valueOf(id));
	    return "redirect:/";
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {

	    log.error("Handling not found exception");
	    log.error(exception.getMessage());

	    ModelAndView modelAndView = new ModelAndView();

	    modelAndView.setViewName("404error");
	    modelAndView.addObject("exception", exception);

	    return modelAndView;
	}
	
	/**
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleNumberFormatError(Exception exception) {

	    log.error("Handling number format exception");
	    log.error(exception.getMessage());

	    ModelAndView modelAndView = new ModelAndView();

	    modelAndView.setViewName("400error");
	    modelAndView.addObject("exception", exception);

	    return modelAndView;
	}
	*/
}

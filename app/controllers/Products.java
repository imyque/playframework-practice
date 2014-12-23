package controllers;

import interceptors.CatchAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import models.Product;
import models.Tag;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import utils.ExceptionMailer;
import views.html.products.*;

@With(CatchAction.class)
public class Products extends Controller {

	private static final Form<Product> productForm = Form.form(Product.class);

	public static Result list( Integer page ) {

		List<Product> p = new Model.Finder<String, Product>(String.class , Product.class).all();

		return ok(list.render(p)); 

	}

	public static Result show(String ean) {

		Product p =  Product.find.byId(ean) ;

		return TODO; //ok(detail.render(p)); 

	}

	public static Result newProduct() {

		return ok(detail.render(productForm));

	}
	
	
	/* implement remember last page number*/
	public static Result details(String ean) {
		
		final Product product = Product.find.byId(ean);
	    
		if (product == null) {
			
			flash("error", 
					String.format("Product %s does not exist.", ean)
					);	
			
			//return notFound(String.format("Product %s does not exist.", ean));
			return redirect(routes.Products.list(1));
	    }
	    
		Form<Product> filledForm = productForm.fill(product);
	    
		return ok(detail.render(filledForm));
	}
	
	public static Result save() {
		
		Form<Product> boundForm = productForm.bindFromRequest(); 
		
		if(boundForm.hasErrors()) {
		    flash("error", 
		    		"Please correct the form below.");
		    return badRequest(detail.render(boundForm));
		}
		
		Product product = boundForm.get();
		
		
    		//ExceptionMailer.log(product.toString());
    		ExceptionMailer.log( "\tTags id: " + product.tags.size()) ;
    		/*
    		for ( Tag t : product.tags) {
    			ExceptionMailer.log( "\tTags: " + t.name);
    		}
    		*/
	    
    		
    		
		List<Tag> tags = new ArrayList<Tag>();
	    for (Tag tag : product.tags) {
	    	ExceptionMailer.log( "\tTags: " + tag.toString());
	    	if (tag.id != null) {
	    		tags.add(Tag.find.byId(tag.id));
	    		 ;
	    	} 	      
	    }
	    product.tags = tags;
		
	    
	    final Product p = Product.find.byId(product.ean);
		String result = "";
		if ( p == null) {
			product.save();
			result = tags.size() +  " - Successfully added product %s";
		} else {
			product.update();
			result = "Successfully updated product %s";
		}
		
		flash("success", 
				String.format( result, product)
				);
		
		return redirect(routes.Products.list(1));
		  
	}
	
	
	public static Result delete(String ean) {
		
        final Product product = Product.find.byId(ean);
        
        if(product == null) {
        	
        	flash("error", 
					String.format("Product %s does not exist.", ean)
					);	
			
        	//return notFound(String.format("Product %s does not exists.", ean));
			
        } else {
        	
        	product.delete();
        	flash("success", 
					String.format("Successfully updated product %s", product)
					);
        	
        }
        
        
       return redirect(routes.Products.list(1));
     }

}

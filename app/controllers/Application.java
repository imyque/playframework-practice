package controllers;

import java.util.List;

import models.Product;
import models.Tag;
import play.mvc.Controller;
import play.mvc.Result;
import utils.ExceptionMailer;

import com.avaje.ebean.Ebean;


public class Application extends Controller {

    public static Result index() {
    	
    	try {
    		
    	new Product("5010255079763", "Paperclips Large", "Large Plain Pack of 1000").save();
    	new Product("5018206244666", "Giant Paperclips",  "Giant Plain 51mm 100 pack").save();
    	new Product("5018306332812", "Paperclip Giant Plain",  "Giant Plain Pack of 10000").save();
    	new Product("5018306312913", "No Tear Paper Clip", "No Tear Extra Large Pack of 1000").save();
    	new Product("5018206244611", "Zebra Paperclips", "Zebra Length 28mm Assorted 150 Pack").save();
    	
    	new Tag( 1, "lightweight", 
    			Product.find
    			.where()
    				.eq("ean", "5010255079763")
    			.findList()
    		).save() ;
    	
    	new Tag( 2, "metal", 
    			Product.find.findList()
    		).save() ;
    	
    	new Tag( 3, "plastic", 
    			Product.find
    			.where()
    				.like("name", "%Giant%")
    			.findList()
    		).save() ;
    	
    		
    	
    	List<Product> ps = Product.find
			.where()
				//.eq("ean", "5010255079763")
				.like("name", "%Giant%")
			.findList();
    	
    	for (Product p : ps) {
    		ExceptionMailer.log(p.toString());
    		for ( Tag t : p.tags) {
    			ExceptionMailer.log( "\tTags: " + t.name);
    		}
	    }
	    
    	
    	//ExceptionMailer.log( Tag.find.byId(1).toString() );
    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	

        //return ok(index.render("Your new application is ready."));
    	return redirect(routes.Products.list(0)) ;
    }

}

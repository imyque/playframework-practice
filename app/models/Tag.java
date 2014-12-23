package models;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Tag extends Model {

	@Id
	public Integer id;
	
	@Required
	public String name;
	
	@ManyToMany(mappedBy="tags")
	public List<Product> products;
	
	public static Finder<Integer,Tag> find = new Finder<Integer,Tag>( Integer.class, Tag.class); 
	
	public Tag(){
		// Left empty
	}
	
	public Tag(Integer id, String name, Collection<Product> products) {  
		this.id = id;
		this.name = name;
		this.products = new LinkedList<Product>(products);
		for (Product product : products) {
			product.tags.add(this);
		} 
		this.save();
		
	}
	
	public String toString() {
		
		return "Id->" + this.id + "\tName->" + this.name ;
	}
	
	


}

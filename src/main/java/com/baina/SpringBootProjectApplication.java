package com.baina;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baina.db.Address;
import com.baina.db.AddressRepository;
import com.baina.db.CountryCode;
import com.baina.db.CountryCodeRepository;
import com.baina.db.Student;
import com.baina.db.StudentRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@RestController
@CrossOrigin("*")
public class SpringBootProjectApplication {

	private List<Product> products;
	
	@Autowired
	private StudentRepository repo;
	
	@Autowired
	private AddressRepository arepo;
	
	@Autowired
	private CountryCodeRepository crepo;
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}
	
	//@PostConstruct
	public void init2() {
		
		
		/*
		Student st=new Student();
		st.setName("naresh");
		st.setMobile("9490457234");
		//repo.save(st);
		
		CountryCode cCode=new CountryCode();
		cCode.setName("india");
		
		crepo.save(cCode);
		
		
		Address add1=new Address();
		add1.setVillage("vvraopet");
		add1.setDistrict("jagtial");
		add1.setState("telangana");
		add1.setPincode("505331");
		add1.setCode(cCode);
		add1.setStudent(st);
		
		Address add2=new Address();
		add2.setVillage("gachobowli");
		add2.setDistrict("hyderabad");
		add2.setState("telangana");
		add2.setPincode("500001");
		add2.setCode(cCode);
		add2.setStudent(st);
		
		
		
		
		arepo.save(add1);
		//arepo.save(add2);
		*/
		
		//repo.delete(repo.findById(1).get());
		
		//arepo.delete(arepo.findById(1).get());
		
		
	}
	
	@PostConstruct
	public void init() {
		products = new LinkedList<Product>();
		products.add(new Product(1, "iq ooz9", 20000, "vivo"));
		products.add(new Product(2, "se 2nd gen", 40000, "apple"));
		products.add(new Product(3, "note 5 pro plus", 5000, "redme"));
		products.add(new Product(4, "one plus", 30000, "one plus"));
	}
	
	@RequestMapping("/msg")
	public String getMsg() {
		return "welcome thinkpad world.........!";
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProductss(){
		System.out.println("*****"+products);
		return ResponseEntity.ok(products);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id){
		Product product=products.stream().filter(prod->prod.getId()==id).findAny().orElseThrow(()->new IllegalArgumentException("given id not found"));
		products.remove(product);
		return ResponseEntity.ok("succesfully deleted...!");
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateProduct(@RequestBody Product product,@PathVariable int id){
		Product productt=products.stream().filter(prod->prod.getId()==id).findAny().orElseThrow(()->new IllegalArgumentException("given id not found"));
		productt.setCategory(product.getCategory());
		productt.setCost(product.getCost());
		productt.setName(product.getName());
		products.set(products.indexOf(productt), productt);
		return ResponseEntity.ok("succesfully updated...!");
		
	}
	
	@PostMapping("/save")
	public ResponseEntity<String> addProduct(@RequestBody Product product){
		products.add(product);
		return ResponseEntity.ok("product added succesfully...!");
	}

	public List<Product> getProducts() {
		return products;
	}

}

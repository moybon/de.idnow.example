package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.*;
import java.util.*;
import java.util.stream.*;

import model.Company;
import model.Identification;

public class RestController extends Controller {
    
    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	//Do something with the identification
    	if(json == null){
    	    return badRequest("Expecting identification data");
    	}else{
    	    int id = json.findPath("id").intValue();
    	    String name= json.findPath("name").textValue();
    	    long time = json.findPath("time").longValue();
    	    int waiting_time = json.findPath("waiting_time").intValue();
    	    int companyid = json.findPath("companyid").intValue();
    	    
    	    Company company = Company.findById(companyid);
    	    
    	    Identification identification = new Identification(id, name, time, waiting_time, company);
    	    identification.save();
    	}
    	
        return ok();
    }
    
    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	//Do something with the company
    	if(json == null){
    	    return badRequest("Expecting company data");
    	}else{
    	    int id = json.findPath("id").intValue();
    	    String name= json.findPath("name").textValue();
    	    long sla_time = json.findPath("sla_time").longValue();
    	    double sla_percentage = json.findPath("sla_percentage").doubleValue();
    	    double current_sla_percentage = json.findPath("current_sla_percentage").doubleValue();
    	    
    	    Company company = new Company(id, name, sla_time, sla_percentage, current_sla_percentage);
    	    company.save();
    	}
    	
        return ok();
    }

    public Result identifications() {
    	JsonNode identifications = Json.newArray();
		
		//Get the current identification
    	//Compute correct order
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list 
		
		List<Identification> identificationList = Identification.findAll();
		List<Identification> iden = identificationList.stream().filter(
		id -> id.getWaiting_time() < id.getCompany().getSla_time()).collect(Collectors.toList());
		
	
		Collections.sort(iden, (Identification I1, Identification I2) -> 
		{
			if(I1.getWaiting_time() != I2.getWaiting_time()){
				Integer i1 = new Integer(I1.getWaiting_time());
				Integer i2 = new Integer(I2.getWaiting_time());
				return i1.compareTo(i2);
			}else if(I1.getCompany().getCurrent_sla_percentage() != I2.getCompany().getCurrent_sla_percentage()){
				Double d1 = new Double(I1.getCompany().getCurrent_sla_percentage());
				Double d2 = new Double(I2.getCompany().getCurrent_sla_percentage());
				return d1.compareTo(d2);
			}else {
				Double d1 = new Double(I1.getCompany().getSla_percentage());
				Double d2 = new Double(I2.getCompany().getSla_percentage());
				return d1.compareTo(d2);
				}
		});
	
        return ok(identifications);
    }

}

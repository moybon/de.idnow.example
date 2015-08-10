package model;

import play.db.jpa.JPA;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company{
    @Id
    private int id;
    private String name;
    private long sla_time;
    private double sla_percentage;
    private double current_sla_percentage;
    
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<Identification> identifications = new HashSet<>();

    public Company(){
    }
    
    public Company(int id, String name, long sla_time, double sla_percentage, double current_sla_percentage){
        this.id = id;
        this.name = name;
        this.sla_time = sla_time;
        this.sla_percentage = sla_percentage;
        this.current_sla_percentage = current_sla_percentage;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSla_time() {
        return sla_time;
    }

    public void setSla_time(long sla_time) {
        this.sla_time = sla_time;
    }

    public double getSla_percentage() {
        return sla_percentage;
    }

    public void setSla_percentage(double sla_percentage) {
        this.sla_percentage = sla_percentage;
    }

    public double getCurrent_sla_percentage() {
        return this.current_sla_percentage;
    }

    public void setCurrent_sla_percentage(double current_sla_percentage) {
        this.current_sla_percentage = current_sla_percentage;
    }

    public Set<Identification> getIdentifications() {
        return this.identifications;
    }

    public void setIdentification(Set<Identification> identifications) {
        this.identifications = identifications;
    }
	
	public static Company findById(int id) {
        return JPA.em().find(Company.class, id);
    }

    public static List<Company> findAll() {
        return JPA.em().createQuery("select c from Company c", Company.class).getResultList();
    }
	
    public void save() {
        JPA.em().persist(this);
        JPA.em().flush();
    }
}

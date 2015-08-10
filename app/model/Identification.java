package model;

import play.db.jpa.JPA;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;


@Entity
public class Identification{
    @Id
    private int id;

    private String name;
    
    private long time;

    private int waiting_time;
    
    @ManyToOne
    @JoinColumn
    private Company company;
    
    public Identification(){
    }
    
    public Identification(int id, String name, long time, int waiting_time, Company company){
        this.id = id;
        this.name = name;
        this.time= time;
        this.waiting_time = waiting_time;
        this.company = company;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getWaiting_time() {
        return this.waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
	
	public static Identification findById(int id) {
        return JPA.em().find(Identification.class, id);
    }

    public static List<Identification> findAll() {
        return JPA.em().createQuery("select i from Identification i", Identification.class).getResultList();
    }
	
    public void save() {
        JPA.em().persist(this);
        JPA.em().flush();
    }
}

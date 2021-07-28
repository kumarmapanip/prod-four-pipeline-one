package com.sapient.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sapient.app.CustomerSupermarketAppApplication;
import com.sapient.entity.Customer;
import com.sapient.repository.CustomerRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { CustomerSupermarketAppApplication.class })

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
class OfdRepositoryIntegrationTest {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private CustomerRepository repository;
	
    @Test
    public void whenFindByAddress_thenReturnAddress() {
        Customer customer = new Customer(1899,"Badal",977717224L,95,LocalDate.of(2016, 1, 5),"Kuala Lumpur");
        entityManager.persistAndFlush(customer);

        Customer customerDB = repository.findByName(customer.getName());
        	assertThat(customerDB.getAddress()).isEqualTo(customer.getAddress());
        

    
        

    }

    /*@Test
    public void whenInvalidAddress_thenReturnNull() {
    	List<Restaurant> foundList = repository.findByAddress("Invalid Address");
        assertThat(foundList).isEmpty();
    }
    
    @Test
    public void whenFindById_thenReturnRestaurnt() {
    	Restaurant restaurant= new Restaurant(null,"Paradise","Non vegeterian",LocalTime.parse("09:30:00"),
    												LocalTime.parse("22:50:30"),LocalDate.of(2007,12,9),6,"Mangalore",9865325678L);
        entityManager.persistAndFlush(restaurant);

       	Restaurant fromDb = repository.findById(restaurant.getRestaurantId())
        										.orElse(null);
        assertThat(fromDb.getRestaurantId()).isEqualTo(restaurant.getRestaurantId());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
    	Restaurant fromDb = repository.findById(-11).orElse(null);
        assertThat(fromDb).isNull();
    }*/

}

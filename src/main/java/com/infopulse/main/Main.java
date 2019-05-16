package com.infopulse.main;

import com.infopulse.entity.*;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        SessionFactory sessionFactory = (SessionFactory)
                Persistence.createEntityManagerFactory( "org.hibernate.tutorial.jpa" );
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Client client = new Client();
//        ComplexKey complexKey = new ComplexKey();
//        complexKey.setField1(10);
//        complexKey.setField2(30);
//        client.setKey(complexKey);

        Order order1 = new Order();
        order1.setName("order1");
        order1.setClient(client);

        Order order2 = new Order();
        order2.setName("order2");
        order2.setClient(client);

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        client.setOrders(orders);

        Telephone telephone = new Telephone();
        telephone.setPhone("555666777");
        telephone.setClient(client);
        client.setTelephone(telephone);

        client.setName("Kolya");
        client.setSurename("Pupkin");
        Address address = new Address();
        address.setCity("Kiev");
        address.setStreet("Polevya");
        client.setAddress(address);
        entityManager.persist(client);

        //direct many to many
//        Bank bank1 = new Bank();
//        bank1.setName("private");
//        bank1.setClients(Arrays.asList(client));
//        entityManager.persist(bank1);
//
//        Bank bank2 = new Bank();
//        bank2.setName("pravex");
//        bank2.setClients(Arrays.asList(client));
//        entityManager.persist(bank2);
//
//        List<Bank> banks = new ArrayList<>();
//        banks.add(bank1);
//        banks.add(bank2);
//        client.setBanks(banks);
//        entityManager.persist(client);

        //Two constraints one to many

        Bank bank1 = new Bank();
        bank1.setName("private");
        entityManager.persist(bank1);

        Bank bank2 = new Bank();
        bank2.setName("pravex");
        entityManager.persist(bank2);

        ClientBank clientBank1 = new ClientBank();
        clientBank1.setClient(client);
        clientBank1.setBank(bank1);
        client.setClientBanks(Arrays.asList(clientBank1));
        bank1.setClientBanks(Arrays.asList(clientBank1));
        entityManager.persist(clientBank1);
        entityManager.persist(client);
        entityManager.persist(bank1);

        ClientBank clientBank2 = new ClientBank();
        clientBank2.setClient(client);
        clientBank2.setBank(bank2);
        client.setClientBanks(Arrays.asList(clientBank2));
        bank2.setClientBanks(Arrays.asList(clientBank2));
        entityManager.persist(clientBank2);
        entityManager.persist(client);
        entityManager.persist(bank2);

        BadClient badClient = new BadClient();
        badClient.setName("Kolya");
        badClient.setSurename("Pupkin");
        badClient.setSum(300);
        entityManager.persist(badClient);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        List<Order> orderList = entityManager
                .createQuery("select o from Order o join o.client cl where cl.name = 'Kolya'", Order.class).getResultList();
        for (Order o: orderList) {
            System.out.println(o.getName());
        }
        Order o1 = orderList.get(0);
        Client c = o1.getClient();
        c.getOrders().remove(o1);
        o1.setClient(null);
        entityManager.remove(o1);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        List<Result> result = entityManager
                .createQuery("SELECT new com.infopulse.entity.Result(cl.name, o.name) FROM Client cl JOIN cl.orders o", Result.class).getResultList();
        for(Result r: result){
            System.out.println(r.getClientName()+" "+r.getOrderName());
        }
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        List<Statistics> statistics = entityManager
                .createQuery(
                        "SELECT new com.infopulse.entity.Statistics(cl.name, count(cl.name)) " +
                                "FROM Client cl WHERE cl.name = :name GROUP BY cl.name", Statistics.class)
                .setParameter("name", "Kolya")
                .getResultList();

        for(Statistics s:statistics){
            System.out.println(s.getName()+" "+s.getCount());
        }


        entityManager.getTransaction().commit();
        sessionFactory.close();

    }
}
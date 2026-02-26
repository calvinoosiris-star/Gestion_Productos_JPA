package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("productosPU");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Producto producto = new Producto("Mouse Gamer", 34000);
            Producto producto2 = new Producto("Teclado", 21000);
            em.persist(producto);
            em.persist(producto2);

            em.getTransaction().commit();
            System.out.println("Productos creados");

            List<Producto> lista = em.
                    createQuery("SELECT p FROM Producto p", Producto.class).
                    getResultList();
            System.out.println("Listado de productos");
            for (Producto p : lista){
                System.out.println(p.getId()+ "-" + p.getNombre() + "-" + p.getPrecio());
            }
            em.getTransaction().begin();
            Producto productoActualizado = em.find(Producto.class, producto.getId());
            productoActualizado.setPrecio(60000);

            em.getTransaction().commit();
            System.out.println("Productos actualizados");

            em.getTransaction().begin();
            Producto productoEliminado = em.find(Producto.class, producto2.getId());
            em.remove(productoEliminado);

            em.getTransaction().commit();
            System.out.println("Productos eliminados");

        }finally {
            em.close();
            emf.close();
        }




    }
}
package com.tienda;

import com.tienda.dao.PedidoDAOImpl;
import com.tienda.dao.ProductoDAOImpl;
import com.tienda.modelo.Pedido;
import com.tienda.modelo.Producto;
import com.tienda.util.ConexionDB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection con = ConexionDB.getConexion()) {
            ProductoDAOImpl productoDAO = new ProductoDAOImpl(con);
            PedidoDAOImpl pedidoDAO = new PedidoDAOImpl(con);

            int opcion;
            do {
                System.out.println("----- MENÚ -----");
                System.out.println("1. Insertar producto");
                System.out.println("2. Consultar producto por id");
                System.out.println("3. Actualizar stock de producto");
                System.out.println("4. Insertar pedido");
                System.out.println("5. Listado de pedidos");
                System.out.println("6. Salir");
                System.out.print("Elige una opción: ");
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.print("Nombre: ");
                        String nombre = sc.next();
                        System.out.print("Precio: ");
                        BigDecimal precio = sc.nextBigDecimal();
                        System.out.print("Stock: ");
                        int stock = sc.nextInt();
                        Producto nuevo = new Producto(0, nombre, precio, stock);
                        productoDAO.create(nuevo);
                        System.out.println("Producto insertado con id " + nuevo.getIdProducto());
                        break;

                    case 2:
                        System.out.print("Id del producto: ");
                        Producto p = productoDAO.findById(sc.nextInt());
                        if (p == null) {
                            System.out.println("El producto no existe.");
                        } else {
                            System.out.println(p);
                        }
                        break;

                    case 3:
                        System.out.print("Id del producto: ");
                        Producto prod = productoDAO.findById(sc.nextInt());
                        if (prod == null) {
                            System.out.println("El producto no existe.");
                        } else {
                            System.out.print("Nuevo stock: ");
                            prod.setStock(sc.nextInt());
                            productoDAO.update(prod);
                            System.out.println("Stock actualizado.");
                        }
                        break;

                    case 4:
                        System.out.print("Id del producto: ");
                        int idProducto = sc.nextInt();
                        System.out.print("Cantidad: ");
                        int cantidad = sc.nextInt();
                        Pedido pedido = new Pedido();
                        pedido.setFechaPedido(LocalDate.now());
                        pedido.setCantidad(cantidad);
                        pedido.setIdProducto(idProducto);
                        pedidoDAO.create(pedido);
                        break;

                    case 5:
                        List<Pedido> pedidos = pedidoDAO.findAll();
                        for (Pedido ped : pedidos) {
                            System.out.println(ped);
                        }
                        break;

                    case 6:
                        System.out.println("Adiós.");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 6);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
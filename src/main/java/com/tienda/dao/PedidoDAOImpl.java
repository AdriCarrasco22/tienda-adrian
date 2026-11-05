package com.tienda.dao;

import com.tienda.modelo.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO<Pedido> {
    private final Connection connection;

    public PedidoDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Pedido pedido) {
        String sqlInsertar = "INSERT INTO pedidos (fecha_pedido, cantidad, id_producto) VALUES (?, ?, ?)";
        String sqlStock = "UPDATE productos SET stock = stock - ? WHERE id_producto = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sqlInsertar, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(pedido.getFechaPedido()));
            stmt.setInt(2, pedido.getCantidad());
            stmt.setInt(3, pedido.getIdProducto());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setIdPedido(rs.getInt(1));
                }
            }

            try (PreparedStatement stmt2 = connection.prepareStatement(sqlStock)) {
                stmt2.setInt(1, pedido.getCantidad());
                stmt2.setInt(2, pedido.getIdProducto());
                stmt2.executeUpdate();
            }

            System.out.println("Pedido insertado y stock actualizado.");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: el producto con id " + pedido.getIdProducto() + " no existe.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pedido> findAll() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT p.id_pedido, p.fecha_pedido, p.cantidad, p.id_producto, pr.nombre "
                + "FROM pedidos p "
                + "JOIN productos pr ON p.id_producto = pr.id_producto "
                + "ORDER BY p.id_pedido";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pedidos.add(new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getDate("fecha_pedido").toLocalDate(),
                        rs.getInt("cantidad"),
                        rs.getInt("id_producto"),
                        rs.getString("nombre")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }
}
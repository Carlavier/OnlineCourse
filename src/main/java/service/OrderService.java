package service;

import java.sql.Date;
import model.Order;
import static service.UserService.c;
import static service.UserService.pre;
import static service.UserService.rs;
import util.ConnectDB;

public class OrderService {

    public static Order createOrder(double totalPrice, int uid) {
        String createOrderQuery = "insert into [order]( totalPrice, orderTime, isPaid, uid)"
                + "values(?, ?, ?, ?)"
                + "select * from [order] where id=  SCOPE_IDENTITY()";
        try {
            c = ConnectDB.getConnection();
            pre = c.prepareStatement(createOrderQuery);
            pre.setDouble(1, totalPrice);
            pre.setDate(2, new Date(System.currentTimeMillis()));
            pre.setInt(3, 0);
            pre.setInt(4, uid);
            rs = pre.executeQuery();
            if (rs.next()) {
                Order order = new Order(rs.getInt(1), rs.getDouble(2), rs.getDate(3), rs.getInt(4), rs.getInt(5));
                return order;
            }
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
     public static void createOrderItem (int courseId, int orderId) {
        String createOrderItemQuery = "insert into [orderItem]( orderId, productId )"
                + "values(?, ?)";
        try {
            c = ConnectDB.getConnection();
            pre = c.prepareStatement(createOrderItemQuery);
            pre.setInt(1, orderId);
            pre.setInt(2,courseId );
            pre.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
      public static void updatePayStatus (int orderId) {
        String updateOrderQuery = "update [order] set isPaid=1 where id= ? ";
        try {
            c = ConnectDB.getConnection();
            pre = c.prepareStatement(updateOrderQuery);
            pre.setInt(1, orderId);
            pre.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        System.out.println(OrderService.createOrder(1000, 10));
    }
}
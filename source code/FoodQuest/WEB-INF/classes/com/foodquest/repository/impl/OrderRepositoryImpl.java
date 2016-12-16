package com.foodquest.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.foodquest.models.LocationBean;
import com.foodquest.models.OrderBean;
import com.foodquest.models.OrderBean.OrderStatus;
import com.foodquest.models.OrderItem;
import com.foodquest.models.RecipeBean;
import com.foodquest.models.UserBean;
import com.foodquest.models.db.DBConnectionManager;
import com.foodquest.repository.ILocationRepository;
import com.foodquest.repository.IOrderRepository;
import com.foodquest.repository.IRecipeRepository;
import com.foodquest.repository.IUserRepository;
import com.foodquest.utils.lang.AppException;

public class OrderRepositoryImpl implements IOrderRepository {
	private static final Logger LOGGER = Logger.getLogger(OrderRepositoryImpl.class.getName());
	private static IOrderRepository INSTANCE;
	private DBConnectionManager dbConnectionManager;
	private IUserRepository userRepository;
	private ILocationRepository locationRepository;
	private IRecipeRepository recipeRepository;

	private OrderRepositoryImpl() {
		dbConnectionManager = DBConnectionManager.getInstance();
		userRepository = UserRepositoryImpl.getInstance();
		locationRepository = LocationRepositoryImpl.getInstance();
		recipeRepository = RecipeRepositoryImpl.getInstance();
	}

	@Override
	public Integer create(OrderBean order) throws AppException {
		LOGGER.info("Create order :: " + order);
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "insert into customer_order (customer_id, total, status, created_at, updated_at, location_id) values (?,?,?,?,?,?)";

			pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, order.getCustomer().getId());
			pst.setDouble(2, order.getTotal());
			pst.setString(3, order.getStatus().name());
			pst.setLong(4, order.getCreatedAt());
			pst.setLong(5, order.getUpdatedAt());
			pst.setInt(6, order.getDeliveryAddress().getId());
			pst.executeUpdate();
			try (ResultSet rs = pst.getGeneratedKeys()) {
				if (rs.next()) {
					order.setId(rs.getInt(1));
				}
			}
			insertOrderItems(order, conn);
			return order.getId();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public OrderBean findById(int id) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		OrderBean bean = null;
		try {
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement(getSelectQuery("customer_order.id", null));
			pst.setInt(1, id);
			System.out.println(pst);
			ResultSet rs = pst.executeQuery();
			boolean isSet = false;
			while (rs.next()) {
				OrderBean order = formatResultSet(rs);
				if (!isSet) {
					bean = order;
					isSet = true;
				}
				for (OrderItem item : order.getItems()) {
					bean.getItems().add(item);
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
		return bean;
	}

	@Override
	public OrderBean findOrderInCartByUserId(int userId) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		OrderBean bean = null;
		try {
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement(getSelectQuery("customer_order.customer_id", "customer_order.status"));
			pst.setInt(1, userId);
			pst.setString(2, OrderStatus.IN_CART.name());
			ResultSet rs = pst.executeQuery();
			boolean isSet = false;
			while (rs.next()) {
				OrderBean order = formatResultSet(rs);
				if (!isSet) {
					bean = order;
					isSet = true;
				}
				for (OrderItem item : order.getItems()) {
					bean.getItems().add(item);
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
		return bean;
	}

	@Override
	public OrderBean findByCnf(String cnf) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		OrderBean bean = null;
		try {
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement(getSelectQuery("customer_order.confirmation_number", null));
			pst.setString(1, cnf);
			ResultSet rs = pst.executeQuery();
			boolean isSet = false;
			while (rs.next()) {
				OrderBean order = formatResultSet(rs);
				if (!isSet) {
					bean = order;
					isSet = true;
				}
				for (OrderItem item : order.getItems()) {
					bean.getItems().add(item);
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
		return bean;
	}

	@Override
	public List<OrderBean> findAll(OrderStatus status) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		HashMap<Integer, OrderBean> idToOrderMap = new HashMap<>();
		try {
			conn = dbConnectionManager.getConnection();
			String query = "";
			if (status != null) {
				query = getSelectQuery(null, "customer_order.status");
			} else {
				query = getSelectQuery(null, null);
			}
			pst = conn.prepareStatement(query);
			if (status != null) {
				pst.setString(1, status.name());
			}
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				OrderBean order = formatResultSet((rs));
				if (idToOrderMap.containsKey(order.getId())) {
					for (OrderItem item : order.getItems()) {
						idToOrderMap.get(order.getId()).getItems().add(item);
					}
				} else {
					idToOrderMap.put(order.getId(), order);
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
		return new ArrayList<OrderBean>(idToOrderMap.values());
	}

	@Override
	public boolean update(OrderBean order) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "update customer_order set total = ?, status = ?, updated_at = ?, ordered_at = ?, confirmation_number = ?,location_id = ? where id = ?";
			boolean isUpadetd = false;

			pst = conn.prepareStatement(query);
			pst.setDouble(1, order.getTotal());
			pst.setString(2, order.getStatus().name());
			pst.setLong(3, order.getUpdatedAt());
			pst.setLong(4, order.getOrderedAt());
			pst.setString(5, order.getConfirmationNumber());
			pst.setInt(6, order.getDeliveryAddress().getId());
			pst.setInt(7, order.getId());
			int rowsAffected = pst.executeUpdate();

			if (rowsAffected > 0) {
				isUpadetd = insertOrderItems(order, conn);
			}
			isUpadetd = rowsAffected > 0 || isUpadetd == true ? true : false;
			return isUpadetd;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	private Boolean insertOrderItems(OrderBean order, Connection conn) throws AppException {
		String deleteQuery = "delete from order_item where order_id = ?";
		String query = "insert into order_item (order_id, recipe_id, quantity, price) values (?, ?, ?, ?)";
		PreparedStatement delStmt = null;
		PreparedStatement stmt = null;
		try {
			delStmt = conn.prepareStatement(deleteQuery);
			stmt = conn.prepareStatement(query);
			int affectedRows = 0;
			delStmt.setInt(1, order.getId());
			delStmt.executeUpdate();

			for (OrderItem item : order.getItems()) {
				if (item.getRecipe() != null) {
					stmt.setInt(1, order.getId());
					stmt.setInt(2, item.getRecipe().getId());
					stmt.setInt(3, item.getQuantity());
					stmt.setDouble(4, item.getPrice());
					affectedRows += stmt.executeUpdate();
				}
			}

			return affectedRows > 0 ? true : false;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(delStmt);
			CommonDBUtil.close(stmt);
		}

	}

	@Override
	public boolean delete(int id) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null, dpst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "delete from order_item where order_id  = ?";
			String delOrderQuery = "delete from customer_order where id = ?";
			boolean isUpadetd = false;

			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			isUpadetd = pst.execute();

			dpst = conn.prepareStatement(delOrderQuery);
			dpst.setInt(1, id);
			dpst.execute();
			return isUpadetd;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			CommonDBUtil.close(dpst);
			dbConnectionManager.returnConnection(conn);
		}
	}
	
	@Override
	public int getTotalBookingsCount(RecipeBean recipe) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null, dpst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "select sum(quantity) as total from order_item where recipe_id=?";

			pst = conn.prepareStatement(query);
			pst.setInt(1, recipe.getId());
			ResultSet rs = pst.executeQuery();
			
			return rs.next() == true ? rs.getInt("total") : 0;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			CommonDBUtil.close(dpst);
			dbConnectionManager.returnConnection(conn);
		}
	}
	private String getSelectQuery(String clause1, String clause2) {
		String query = "select customer_order.id, customer_order.customer_id, customer_order.total, customer_order.confirmation_number, customer_order.status, "
				+ "customer_order.created_at,customer_order.updated_at, customer_order.ordered_at, customer_order.location_id, order_item.recipe_id, "
				+ "order_item.quantity,order_item.price from customer_order left outer join order_item on customer_order.id = order_item.order_id CONDITION "
				+ "order by customer_order.updated_at desc";
		String cond = "";
		if (clause1 != null) {
			cond = "where " + clause1 + " = ?";
			if (clause2 != null) {
				cond += " and " + clause2 + " =?";
			}
		} else if (clause2 != null) {
			cond = "where " + clause2 + " = ?";
		} else {
			cond = "";
		}
		query = query.replace("CONDITION", cond);
		return query;
	}

	private OrderBean formatResultSet(ResultSet rs) throws AppException, SQLException {
		if (rs == null) {
			return null;
		}

		OrderBean bean = new OrderBean();
		LocationBean location = new LocationBean();
		OrderItem item = null;
		if (CommonDBUtil.findIfColumnExists("customer_order.customer_id", rs)) {
			UserBean user = userRepository.findById(rs.getInt("customer_order.customer_id"));
			bean.setCustomer(user);
		}
		if (CommonDBUtil.findIfColumnExists("customer_order.id", rs)) {
			bean.setId(rs.getInt("customer_order.id"));
		}
		if (CommonDBUtil.findIfColumnExists("customer_order.total", rs)) {
			bean.setTotal(rs.getDouble("customer_order.total"));
		}
		if (CommonDBUtil.findIfColumnExists("customer_order.confirmation_number", rs)) {
			bean.setConfirmationNumber(rs.getString("customer_order.confirmation_number"));
		}
		if (CommonDBUtil.findIfColumnExists("customer_order.status", rs)) {
			bean.setStatus((OrderStatus.valueOf(rs.getString("customer_order.status"))));
		}
		if (CommonDBUtil.findIfColumnExists("customer_order.created_at", rs)) {
			bean.setCreatedAt(rs.getLong("customer_order.created_at"));
		}
		if (CommonDBUtil.findIfColumnExists("customer_order.updated_at", rs)) {
			bean.setUpdatedAt(rs.getLong("updated_at"));
		}

		if (CommonDBUtil.findIfColumnExists("customer_order.ordered_at", rs)) {
			bean.setOrderedAt(rs.getLong("customer_order.ordered_at"));
		}
		if (CommonDBUtil.findIfColumnExists("customer_order.location_id", rs)) {
			location = locationRepository.findById(rs.getInt("customer_order.location_id"));
		}
		if (CommonDBUtil.findIfColumnExists("order_item.recipe_id", rs)) {
			int id = rs.getInt("order_item.recipe_id");
			int qty = rs.getInt("order_item.quantity");
			RecipeBean recipe = recipeRepository.findById(id);
			if (recipe != null) {
				item = new OrderItem(recipe, qty);
				item.setPrice(rs.getDouble("order_item.price"));
			}
		}
		HashSet<OrderItem> set = new HashSet<>();
		if (item != null) {
			set.add(item);
		}
		bean.setItems(set);
		bean.setDeliveryAddress(location);
		return bean;
	}

	public static IOrderRepository getInstance() {
		synchronized (OrderRepositoryImpl.class) {
			if (INSTANCE == null) {
				INSTANCE = new OrderRepositoryImpl();
			}
		}
		return INSTANCE;
	}
}

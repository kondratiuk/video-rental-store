/*
 * License header
 */
package info.kondratiuk.repository;

import info.kondratiuk.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Oleksandr Kondratiuk
 */
public interface OrderRepository extends MongoRepository<Order, String> {
}

/*
 * License header
 */
package info.kondratiuk.repository;

import info.kondratiuk.model.Bonus;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Oleksandr Kondratiuk
 */
public interface BonusRepository extends MongoRepository<Bonus, String> {
}

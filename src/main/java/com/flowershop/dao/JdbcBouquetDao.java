package com.flowershop.dao;

import com.flowershop.exception.DaoException;
import com.flowershop.model.Bouquet;
import com.flowershop.model.viewmodel.BouquetFlower;
import com.flowershop.model.Flower;
import com.flowershop.model.viewmodel.BouquetDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementation of the {@link BouquetDao} interface that uses
 * JDBC for database interaction
 *
 * @see BouquetDao
 */
@Component
public class JdbcBouquetDao implements BouquetDao {

    private final String BOUQUET_SQL_SELECT = "SELECT b.bouquet_id, b.name, b.description, b.base_price, b.image_url FROM bouquets AS b ";
    private final JdbcTemplate jdbcTemplate;

    public JdbcBouquetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Flower getFlowerById(int flowerId) {

        Flower foundFlower = null;
        String sql = "SELECT flower_id, name, color, price FROM flowers WHERE flower_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, flowerId);

            if (results.next()) {
                foundFlower = new Flower(
                        results.getInt("flower_id"),
                        results.getString("name"),
                        results.getString("color"),
                        results.getBigDecimal("price")
                );
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return foundFlower;
    }

    @Override
    public List<Bouquet> getBouquets() {

        List<Bouquet> bouquets = new ArrayList<>();

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(BOUQUET_SQL_SELECT);

            while (results.next()) {
                bouquets.add(mapRowToBouquet(results));
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return bouquets;
    }

    @Override
    public BouquetDetails getBouquetById(int bouquetId) {

        BouquetDetails bouquetDetails = null;
        Bouquet bouquet = null;
        List<BouquetFlower> bouquetFlowers = new ArrayList<>();
        String sql =
                "SELECT b.bouquet_id, b.name, b.description, b.base_price, b.image_url, f.flower_id, bf.quantity, f.name AS flower_name, f.color, f.price " +
                "FROM bouquets AS b " +
                "JOIN bouquet_flowers AS bf ON bf.bouquet_id = b.bouquet_id " +
                "JOIN flowers AS f ON f.flower_id = bf.flower_id " +
                "WHERE b.bouquet_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bouquetId);

            while (results.next()) {

                /*
                 * TODO: Make sure to only set the bouquet once,
                 *  because the row data for the bouquet is the same for all rows
                 */
                if (bouquet == null) {
                    bouquet = mapRowToBouquet(results);
                }
                bouquetFlowers.add(new BouquetFlower(
                        results.getInt("flower_id"),
                        results.getString("flower_name"),
                        results.getString("color"),
                        results.getBigDecimal("price"),
                        results.getInt("quantity")
                ));
            }

            bouquetDetails = new BouquetDetails(bouquet, bouquetFlowers);
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch(Exception e) {
            throw new DaoException("Database error", e);
        }

        return bouquetDetails;
    }

    /**
     * @author {USER}
     * @param bouquet
     * @return
     */
    @Override
    public BouquetDetails addBouquet(BouquetDetails bouquet) {

        Integer bouquetId = null;
        String bouquetFlowersInsertSql = "INSERT INTO bouquet_flowers (bouquet_id, flower_id, quantity) VALUES " +
                "(?, ?, ?);";
        String bouquetInsertSql = "INSERT INTO bouquets (name, description, base_price, image_url) VALUES " +
                "(?, ?, ?, ?) RETURNING bouquet_id;";

        try {
            /*
             * TODO: Must insert into Bouquets table first, so an bouquet_id can
             *  be generated and used for the 2nd insert into Bouquet_Flowers
             */
            bouquetId = jdbcTemplate.queryForObject(bouquetInsertSql, Integer.class,
                    bouquet.getName(),
                    bouquet.getDescription(),
                    bouquet.getBasePrice(),
                    bouquet.getImageUrl()
            );

            /*
             * TODO: Insert into Bouquet_Flowers for as many flowers that are in the
             *  bouquet, which is 3 in the demo request.
             */
            for (BouquetFlower eachBouquetFlower : bouquet.getBouquetFlowers()) {
                jdbcTemplate.update(bouquetFlowersInsertSql, bouquetId, eachBouquetFlower.getFlowerId(), eachBouquetFlower.getQuantity());
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return getBouquetById(bouquetId);
    }

    private Bouquet mapRowToBouquet(SqlRowSet results) {
        return new Bouquet(
                results.getInt("bouquet_id"),
                results.getString("name"),
                results.getString("description"),
                results.getBigDecimal("base_price"),
                results.getString("image_url")
        );
    }
}

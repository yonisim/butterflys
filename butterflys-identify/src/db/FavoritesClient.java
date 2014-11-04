/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
public class FavoritesClient extends MysqlClient {

    private String TABLE_NAME = "favorites";
    private String colButterflyId = "butterfly_id";

    public int insertFavorite(int butterflyID) throws ClassNotFoundException, SQLException, IOException {
        String setClause = colButterflyId + " = '" + butterflyID + "'";
        return insert(TABLE_NAME, setClause);
    }

    public List<Integer> selectFavorite(String whereClause)
            throws ClassNotFoundException, SQLException, IOException {
        List<Integer> favorites = new LinkedList<>();
        select(TABLE_NAME, whereClause);
        while (resultSet.next()) {
            favorites.add(resultSet.getInt(1));
        }
        return favorites;
    }
}

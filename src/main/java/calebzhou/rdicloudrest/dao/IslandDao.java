package calebzhou.rdicloudrest.dao;

import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.Island;
import calebzhou.rdicloudrest.utils.SqlUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IslandDao {
    //是否拥有空岛
    public static boolean checkHasIsland(String pid) throws SQLException {
        return DatabaseConnector.getPreparedStatement("SELECT 1 FROM Island WHERE ownerUuid =? LIMIT 1",pid).executeQuery().next();
    }
    //是否加入他人的空岛
    public static boolean checkJoinedIsland(String pid) throws SQLException {
        return DatabaseConnector.getPreparedStatement("SELECT 1 FROM IslandMember WHERE memberUuid =? LIMIT 1",pid).executeQuery().next();
    }
    //通过空岛id获取空岛对象
    public static Island getIslandById(String islandId) throws SQLException{
        ResultSet rs=DatabaseConnector.getPreparedStatement("select * from Island where islandId=?",islandId).executeQuery();
        rs.next();
        try {
            return SqlUtils.initializeObjectByResultSet(rs,Island.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    //通过玩家id获取空岛
    public static Island getIslandByPlayerUuid(String ownerUuid) throws SQLException{
        ResultSet rs=DatabaseConnector.getPreparedStatement("select * from Island where ownerUuid=?",ownerUuid).executeQuery();
        rs.next();
        try {
            return SqlUtils.initializeObjectByResultSet(rs,Island.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    //通过玩家id获取加入他人的空岛id
    public static String getIslandIdJoined(String memberUuid) throws SQLException{
        ResultSet rs=DatabaseConnector.getPreparedStatement("select islandId from IslandMember where memberUuid=?",memberUuid).executeQuery();
        if(!rs.next())
            return "null";
        return rs.getString("islandId");
    }
    //加入他人的空岛
    public static boolean joinOtherIsland(String uuid, String targetIslandId) throws SQLException {
        return DatabaseConnector.getPreparedStatement("insert into IslandMember values (?,?)",targetIslandId,uuid).executeUpdate()==1;
    }
    public static boolean create(Island island) throws SQLException {
        try {
            return SqlUtils.insertObjectToTable(island,Island.class)==1;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean delete(String iid) throws SQLException {
        DatabaseConnector.getPreparedStatement("delete from IslandMember where islandId=?",iid);
        return DatabaseConnector.getPreparedStatement("delete from Island where islandId=?",iid).executeUpdate()==1 ;
    }
    public static boolean quit(String memberUuid) throws SQLException{
        return DatabaseConnector.getPreparedStatement("delete from IslandMember where memberUuid=?",memberUuid).executeUpdate()==1;
    }
    public static boolean locate(String pid, CoordLocation location) throws SQLException{
        return DatabaseConnector.getPreparedStatement("update Island set location=? where ownerUuid=?",location.toString(),pid).executeUpdate()==1;
    }


}

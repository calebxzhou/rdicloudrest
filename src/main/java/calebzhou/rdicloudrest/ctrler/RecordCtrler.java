package calebzhou.rdicloudrest.ctrler;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.dao.RecordMapper;
import calebzhou.rdicloudrest.model.RecordBlock;
import calebzhou.rdicloudrest.utils.RdiSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordCtrler {
    final RecordMapper mapper;
    public RecordCtrler(RecordMapper mapper) {
        this.mapper = mapper;
    }
    //记录说话、指令
    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public void recordChat(@RequestParam String pid, @RequestParam String cont) {
        try (PreparedStatement ps =
                     DatabaseConnector.getPreparedStatement("insert into RecordChat (pid,cont,ts) values (?,?,?)", pid, cont, Timestamp.valueOf(LocalDateTime.now()))
        ) {
            ps.executeUpdate();
            ps.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //记录死亡
    @RequestMapping(value = "/death", method = RequestMethod.POST)
    public void recordDeath(@RequestParam String pid, @RequestParam String src) {
        try (PreparedStatement ps =
                     DatabaseConnector.getPreparedStatement("insert into RecordDeath (pid,src,ts) values (?,?,?)", pid, src, Timestamp.valueOf(LocalDateTime.now()))
        ) {
            ps.executeUpdate();
            ps.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //记录登入
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void recordLogin(@RequestParam String pid, @RequestParam String ip, @RequestParam String geo) {
        try (PreparedStatement ps =
                     DatabaseConnector.getPreparedStatement("insert into RecordLogin (pid,ip,geo,ts) values (?,?,?,?)", pid, ip, geo, Timestamp.valueOf(LocalDateTime.now()))
        ) {
            ps.executeUpdate();
            ps.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //记录UUID和昵称的对应关系（进入服务器时）
    @RequestMapping(value = "/idname", method = RequestMethod.POST)
    public void recordIdName(@RequestParam String pid, @RequestParam String name) {
        try (PreparedStatement ps =
                     DatabaseConnector.getPreparedStatement("insert into RecordIdName (pid,pname,ts) values (?,?,?)", pid, name,
                             Timestamp.valueOf(LocalDateTime.now()))
        ) {
            ps.executeUpdate();
            ps.getConnection().close();
        } catch (SQLException e) {
            if(!e.getMessage().equals("EXISTS!"))
                throw new RuntimeException(e);
        }
    }

    //记录登出
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void recordLogout(@RequestParam String pid) {
        try (PreparedStatement ps = DatabaseConnector.getPreparedStatement("insert into RecordLogout (pid,ts) values (?,?)", pid, Timestamp.valueOf(LocalDateTime.now()))
        ) {
            ps.executeUpdate();
            ps.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //方块破坏/放置
    @RequestMapping(value = "/block", method = RequestMethod.POST)
    public void recordBlock(@RequestParam(required=false,defaultValue = "00000000-0000-0000-0000-000000000000") String pid,
                            @RequestParam String bid,
                            @RequestParam int act,
                            @RequestParam String world,
                            @RequestParam int x,
                            @RequestParam int y,
                            @RequestParam int z) {
        //ThreadPool.newThread(()->mapper.insertRecordBlock(new RecordBlock(pid,bid,act,world,x,y,z, TimeUtils.getNow())));
        try  {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into RecordBlock (pid,act,bid,world,x,y,z,ts) values (?,?,?,?,?,?,?,?)");
            ps.setString(1, pid);
            ps.setInt(2, act);
            ps.setString(3, bid);
            ps.setString(4, world);
            ps.setInt(5, x);
            ps.setInt(6, y);
            ps.setInt(7, z);
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/block",method = RequestMethod.GET)
    public String queryBlockRecord(@RequestParam String dim,@RequestParam int x,@RequestParam int y,@RequestParam int z){
        List<RecordBlock> recordBlocks = mapper.getRecordBlocksByCoord(dim, x, y, z);
        return RdiSerializer.GSON.toJson(recordBlocks);
    }


}

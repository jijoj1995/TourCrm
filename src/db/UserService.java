package db;

import dto.*;
import main.InventoryConfig;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import service.HibernateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService extends BaseConnection {

    private Logger logger=Logger.getLogger(UserService.class.getName());



    public boolean saveUserData(CoreUserEntity coreUserEntity){

        logger.info("in save user data to db method");
        if (coreUserEntity==null){
            logger.warn("invalid corelead dto data. Returning.");
            return false;
        }
        try{

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(coreUserEntity);
            session.getTransaction().commit();
            session.close();
            // HibernateUtil.shutdown();
            return true;
        }
        catch (Throwable e){
            logger.warn("unable to save user data::: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<CoreUserDto> getAllUsersList(){
        ArrayList<CoreUserDto> coreUserEntities=new ArrayList<>();

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from CoreUserEntity");
        List<CoreUserEntity> results = query.list();
        for(CoreUserEntity coreUserEntity:results){
            CoreUserDto userDto=new CoreUserDto();
            userDto.setCoreUserId(coreUserEntity.getCoreUserId());
            userDto.setFirstName(coreUserEntity.getFirstName());
            userDto.setLastName(coreUserEntity.getLastName());
            userDto.setEmailAddress(coreUserEntity.getEmailAddress());
            userDto.setUserName(coreUserEntity.getUserName());
            userDto.setCoreUserEntity(coreUserEntity);
            coreUserEntities.add(userDto);
        }

        return coreUserEntities;
    }



    public boolean authenticateUser(String userName,String password){
        logger.info("in authenticating user method");
        boolean isSuccessful=false;
        Connection connection=null;
        ResultSet resultSet=null;
        PreparedStatement statement=null;
        Session session=null;
        String queryValue= "from CoreUserEntity where userName=:username and userPassword=:password";
        try{

             session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery(queryValue);
                    query.setString("username",userName);
                    query.setString("password",password);
            List<CoreUserEntity> results = query.list();

           if (results.size()>0){
               isSuccessful=true;
           }

        }
        catch (Throwable ex){
            logger.info("error while fetching user "+ex.getMessage());
        }
        finally {
            if (session!=null){
                session.close();
            }
        }
        return isSuccessful;
    }


    public boolean changeAdminPassword(String newPassword){
        boolean isSuccessful=false;
        String userName="admin";
        logger.info("going to change password for user");
        Statement statement=null;
        Connection connection=null;
        try{
            connection=getDBConnection();
            statement=connection.createStatement();
            statement.execute("update core_user set userPassword='"+newPassword+"' where userName='"+userName+"'");
            isSuccessful=true;
        }catch (Exception e){
            logger.warn("Error while updating user password"+e.getMessage());
        }
        finally {
            sqlCleanup(connection,statement,null);
        }

        return isSuccessful;
    }

    public boolean validateOldPassword(String oldPassword){
        logger.info("going to validate old password for user");
        boolean isSuccessful=false;
        String userName="wadhwa";
        PreparedStatement statement=null;
        Connection connection=null;
        ResultSet resultSet=null;
        try{
            connection=getDBConnection();
            statement=connection.prepareStatement("select * from users where userName=? and password=?");
            statement.setString(1,userName);
            statement.setString(2,oldPassword);
            resultSet=statement.executeQuery();
            if(resultSet.next()){
                isSuccessful=true;
                logger.info("user old password validated successfully");
            }
        }catch (Exception e){
            logger.warn("Error while updating user password"+e.getMessage());
        }
        finally {
            sqlCleanup(connection,statement,null);
        }
        return isSuccessful;
    }

    public void insertAdminLoginData(){
        logger.info("going to insert admin login if not present");
        Session session=null;
        String queryValue= "from CoreUserEntity where userName=:adminuser";
        try{
            createDatabaseIfNotPresent();
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery(queryValue);
            query.setString("adminuser","admin");
            List<CoreUserEntity> results = query.list();
           if (results.size()==0){
               CoreUserEntity coreUserEntity=new CoreUserEntity();
               coreUserEntity.setUserName("admin");
               coreUserEntity.setUserPassword("admin");
               coreUserEntity.setFirstName("admin");
               coreUserEntity.setLastName("admin");
               coreUserEntity.setEmailAddress("admin@gmail.com");
               session.saveOrUpdate(coreUserEntity);
               session.getTransaction().commit();
               session.close();
           }

        }
        catch (Exception e){
            logger.warn("unable to add admin login data because "+e.getMessage());
        }
        finally {
            if (session!=null)session.close();
        }
    }
    public void createDatabaseIfNotPresent(){
        logger.info("creating datbase if not present");
        //  Database credentials
        String ipAddress=InventoryConfig.getInstance().getAppProperties().getProperty("databaseIpAddress");
        String databaseName=InventoryConfig.getInstance().getAppProperties().getProperty("databaseName");
        String USER = InventoryConfig.getInstance().getAppProperties().getProperty("databaseUserName");
        String PASS = InventoryConfig.getInstance().getAppProperties().getProperty("databasePassword");
        String DB_URL = "jdbc:mysql://"+ipAddress+"/";

            Connection conn = null;
            Statement stmt = null;
            try{
                //STEP 2: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                //STEP 3: Open a connection
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                //STEP 4: Execute a query
                stmt = conn.createStatement();

                String sql = "CREATE DATABASE IF NOT EXISTS "+databaseName;
                stmt.executeUpdate(sql);
            }catch(Exception se){
                //Handle errors for JDBC
                se.printStackTrace();
            }

    }

}

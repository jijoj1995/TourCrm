package db;

import constants.InventoryConstants;
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



    public int saveUserData(CoreUserEntity coreUserEntity){

        logger.info("in save user data to db method");
        if (coreUserEntity==null){
            logger.warn("invalid corelead dto data. Returning.");
            return InventoryConstants.userInsertionFailed;
        }
        Session session=null;
        try{

            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(coreUserEntity);
            session.getTransaction().commit();
            session.close();
            // HibernateUtil.shutdown();
            return InventoryConstants.userInsertionSuccess;
        }
        catch (Throwable e){
            logger.warn("unable to save user data::: "+e.getMessage());
            if (e.toString().contains("ConstraintViolationException"))return InventoryConstants.userInsertionConstraintViolation;
            e.printStackTrace();
            return InventoryConstants.userInsertionFailed;
        }
        finally {
            if (session!=null) session.close();
        }
    }

    public boolean deleteUser(CoreUserEntity coreUserEntity){

        logger.info("in delete user data from db method");
        if (coreUserEntity==null){
            logger.warn("invalid corelead dto data. Returning.");
            return false;
        }
        Session session=null;
        try{

            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(coreUserEntity);
            session.getTransaction().commit();
            session.close();
            return true;
        }
        catch (Throwable e){
            logger.warn("unable to delete user::: "+e.getMessage());
            return false;
        }
        finally {
            if (session!=null) session.close();
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



    public CoreUserEntity authenticateUser(String userName,String password){
        logger.info("in authenticating user method");
        CoreUserEntity userEntity=null;
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

               for (CoreUserEntity entity:results) {
                   userEntity=new CoreUserEntity();
                   userEntity.setFirstName(entity.getFirstName());
                   userEntity.setLastName(entity.getLastName());
                   userEntity.setEmailAddress(entity.getEmailAddress());
                   userEntity.setUserPassword(entity.getUserPassword());
                   userEntity.setUserName(entity.getUserName());
                   userEntity.setCoreUserId(entity.getCoreUserId());
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
        return userEntity;
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
        String databaseName=InventoryConfig.getInstance().getAppProperties().getProperty("databaseName");

            Connection conn = null;
            Statement stmt = null;
            try{
                //STEP 2: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                //STEP 3: Open a connection
                //conn = DriverManager.getConnection(DB_URL, USER, PASS);
                conn = BaseConnection.getDBConnection();

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

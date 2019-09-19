package db;

import org.apache.log4j.Logger;

import java.sql.*;

public class UserService extends BaseConnection {

    private Logger logger=Logger.getLogger(UserService.class.getName());

    public boolean changePassword(String newPassword){
        boolean isSuccessful=false;
        String userName="wadhwa";
        logger.info("going to change password for user");
        Statement statement=null;
        Connection connection=null;
        try{
            connection=getDBConnection();
            statement=connection.createStatement();
            statement.execute("update users set password='"+newPassword+"' where userName='"+userName+"'");
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

    public boolean authenticateUser(String userName,String password){
        logger.info("in authenticating user method");
        boolean isSuccessful=false;
        Connection connection=null;
        ResultSet resultSet=null;
        PreparedStatement statement=null;
        String query= "Select * from users where username=? and password=?";
        try{
            connection=getDBConnection();
            statement=connection.prepareStatement(query);
            statement.setString(1,userName);
            statement.setString(2,password);
            resultSet= statement.executeQuery();
            while(resultSet.next()){
                //found user
                isSuccessful=true;
            }
        }
        catch (SQLException ex){
            logger.info("error while fetching user "+ex.getMessage());
        }
        finally {
            sqlCleanup(connection,statement,resultSet);
        }
        return isSuccessful;
    }


}

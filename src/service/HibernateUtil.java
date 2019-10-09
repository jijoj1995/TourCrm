package service;
import dto.*;
import main.InventoryConfig;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil
{
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory()
    {
        try
        {
            if (sessionFactory == null)
            {
                Configuration configuration = new Configuration().configure(HibernateUtil.class.getResource("/resource/hibernate.cfg.xml"));

                //<property name="connection.url">jdbc:mysql://185.224.138.133/u315173730_tourcrm?useSSL=false</property>
                if (Validator.useSpecificDatabasePassword()) configuration.setProperty("hibernate.connection.password",getDatabasePassword());
                    configuration.setProperty("hibernate.connection.url",getDataBaseUrl());
               configuration.setProperty("hibernate.connection.username",getDatabaseUserName());
                    configuration.addAnnotatedClass(CoreUserEntity.class);
                configuration.addAnnotatedClass(CoreLeadNotesEntity.class);
                configuration.addAnnotatedClass(CoreBookingStatusEntity.class);
                configuration.addAnnotatedClass(CoreBookingPromotionEntity.class);
                configuration.addAnnotatedClass(CoreBookingFareEntity.class);
                configuration.addAnnotatedClass(CoreBookingTicketingEntity.class);
                configuration.addAnnotatedClass(CoreBookingItineraryEntity.class);
                configuration.addAnnotatedClass(CoreBookingPricingEntity.class);
                configuration.addAnnotatedClass(CoreBookingPassengerEntity.class);
                configuration.addAnnotatedClass(CoreBookingCommunicationEntity.class);
                configuration.addAnnotatedClass(CoreBookingShippingAddressEntity.class);
                configuration.addAnnotatedClass(CoreBookingBillingAddressEntity.class);
                configuration.addAnnotatedClass(CoreBookingEntity.class);
                configuration.addAnnotatedClass(CoreLeadCommunicationEntity.class);
                configuration.addAnnotatedClass(CoreLeadAirEntity.class);
                configuration.addAnnotatedClass(CoreLeadHolidaysEntity.class);
                configuration.addAnnotatedClass(CoreLeadHotelEntity.class);
                configuration.addAnnotatedClass(CoreLeadRailEntity.class);
                configuration.addAnnotatedClass(CoreLeadEntity.class);
                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
                serviceRegistryBuilder.applySettings(configuration.getProperties());
                ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
            return sessionFactory;
        } catch (Throwable ex)
        {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        if (sessionFactory==null)sessionFactory=buildSessionFactory();
        return sessionFactory;
    }

    public static void shutdown()
    {
        getSessionFactory().close();
    }

    public static void closeCompletehibernateDb(){
        shutdown();
        sessionFactory=null;
    }

    public static String getDataBaseUrl(){
        InventoryConfig inventoryConfig=InventoryConfig.getInstance();
        String ipAddress=inventoryConfig.getAppProperties().getProperty("databaseIpAddress");
        String usePortCheck=inventoryConfig.getAppProperties().getProperty("usePortCheck");
        String portNumber=inventoryConfig.getAppProperties().getProperty("databasePortNumber");
        String databaseName=inventoryConfig.getAppProperties().getProperty("databaseName");
        String databaseUrl="jdbc:mysql://"
                + ipAddress
                +((usePortCheck!=null&&usePortCheck.equals("true")) ? ":"+portNumber : "")
                +"/"
                +databaseName
                +"?useSSL=false";
        return databaseUrl;
    }

    private static String getDatabasePassword(){
        return InventoryConfig.getInstance().getAppProperties().getProperty("databasePassword");
    }
    private static String getDatabaseUserName(){
        return InventoryConfig.getInstance().getAppProperties().getProperty("databaseUserName");
    }
}
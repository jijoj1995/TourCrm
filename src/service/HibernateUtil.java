package service;
import dto.*;
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
        return sessionFactory;
    }

    public static void shutdown()
    {
        getSessionFactory().close();
    }
}
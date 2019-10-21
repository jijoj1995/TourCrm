package db;

import dto.*;
import javafx.collections.ObservableList;
import main.InventoryConfig;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import service.EmailService;
import service.HibernateUtil;

import javax.persistence.EntityGraph;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueryService extends BaseConnection{
    private Logger logger=Logger.getLogger(QueryService.class);

    /*
    method to save/update query as well as booking data
     */
    public boolean saveQueryData(CoreLead coreLead){
        logger.info("in save queryData to db method");
        if (coreLead==null){
            logger.warn("invalid coreLead dto data. Returning.");
            return false;
        }
        Session session=null;
        try{
            logger.info("Converting dto object to entity obj to save in db");
            CoreLeadEntity coreLeadEntity= setValuesFromCoreLeadDto(coreLead);
                                     //add which user going to save query
            coreLeadEntity.setEmployeeName(InventoryConfig.getInstance().getAppProperties().getProperty("currentUser"));
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(coreLeadEntity);
            session.getTransaction().commit();
            return true;
        }
        catch (Throwable e){
            logger.warn("unable to save queryPage data::: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }
    /*
    method to fetch all queries as well as booking
    details from db. entity graph is used to load sub entities efficiently
     */
    public ArrayList<QueriesListDto>getAllQueriesList(){
        logger.info("in getAllQueriesList method");
        String employeeName=InventoryConfig.getInstance().getAppProperties().getProperty("currentUser");
        ArrayList<QueriesListDto> queriesListDtoArrayList=new ArrayList<>();
        Session session =null;
try {
     session = HibernateUtil.getSessionFactory().openSession();
     session.beginTransaction();
     List<CoreLeadEntity> results;
     EntityGraph graph = session.getEntityGraph("post-entity-graph");

    if (employeeName.equals("admin")) {
        results = session.createNamedQuery("CoreLeadEntity.findAll", CoreLeadEntity.class).setHint("javax.persistence.fetchgraph", graph).setMaxResults(getMaxNumberOfQueries()).getResultList();
    }
    else {
       results = session.createNamedQuery("CoreLeadEntity.findBasedOnUser", CoreLeadEntity.class)
               .setParameter("employeeName", employeeName)
               .setHint("javax.persistence.fetchgraph", graph).setMaxResults(getMaxNumberOfQueries()).getResultList();
    }

    for (CoreLeadEntity coreLeadEntity : results) {
        QueriesListDto limitedQueriesListDto = new QueriesListDto();
        limitedQueriesListDto.setBranchCode(coreLeadEntity.getBranchCode());
        limitedQueriesListDto.setCallReason(coreLeadEntity.getCallReason());
        if (coreLeadEntity.getCoreLeadCommunicationEntity() != null) {
            limitedQueriesListDto.setEmail(coreLeadEntity.getCoreLeadCommunicationEntity().getPaxEmailFirst());
        }
        limitedQueriesListDto.setFirstName(coreLeadEntity.getFirstName());
        limitedQueriesListDto.setLastName(coreLeadEntity.getLastName());
        limitedQueriesListDto.setQueryId(coreLeadEntity.getCoreLeadId());
        limitedQueriesListDto.setEmployeeName(coreLeadEntity.getEmployeeName());
        limitedQueriesListDto.setTotalBookingAmount(coreLeadEntity.getTotalBookingAmount());
        limitedQueriesListDto.setCoreLeadDto(setValuesFromCoreLeadEntity(coreLeadEntity));
        queriesListDtoArrayList.add(limitedQueriesListDto);
    }
}
catch (Exception e){
    e.printStackTrace();
}
finally {
    if (session!=null)session.close();
}
        return queriesListDtoArrayList;
    }

    public ArrayList<CoreCustomerDetailsDto>getCustomerDetailsList(){
        ArrayList<CoreCustomerDetailsDto>customerDetailsDtos=new ArrayList<>();

        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try{
            String query="select * from core_lead inner join core_lead_communication using(core_lead_communication_id)";
            connection=getDBConnection();
            statement=connection.prepareStatement(query);
            resultSet=statement.executeQuery();
            while (resultSet.next()){
                customerDetailsDtos.add(new CoreCustomerDetailsDto(null,resultSet.getString("firstName"),resultSet.getString("middleName"),
                        resultSet.getString("lastName"),resultSet.getString("userid"),resultSet.getString("branchCode"),
                        resultSet.getString("channelCode"),resultSet.getString("country"),resultSet.getString("querySource"),
                        resultSet.getString("currencyCode"),resultSet.getString("shift"),resultSet.getString("callReason"),
                        resultSet.getString("lobCode"),resultSet.getString("userid"),resultSet.getString("usaWorkNumber"),
                        resultSet.getString("indiaLandline"),resultSet.getString("paxEmailFirst"),resultSet.getString("paxEmailSecond"),
                        resultSet.getString("indiaMobile"),resultSet.getString("usaHome")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,statement,resultSet);
        }

        return customerDetailsDtos;
    }

    public ArrayList<CoreLeadNotesEntity> getNotesListFromTable(ObservableList<CoreLeadNotesDto> data){
        ArrayList<CoreLeadNotesEntity>notesDtos=new ArrayList<>();

        for (CoreLeadNotesDto dto:data){
           CoreLeadNotesEntity entity=new CoreLeadNotesEntity();
           if (dto.getCoreLeadNotesId()!=0) entity.setCoreLeadNotesId(dto.getCoreLeadNotesId());
           entity.setNotesData(dto.getNotesData());
           notesDtos.add(entity);
        }
        return notesDtos;
    }

    public ArrayList<CoreLeadHotelEntity> getHotelListFromTableData(ObservableList<CoreLeadHotel> hotelObservableList){
        ArrayList<CoreLeadHotelEntity> hotelList=new ArrayList<>();
        for (CoreLeadHotel tableDto:hotelObservableList){
            CoreLeadHotelEntity entity=new CoreLeadHotelEntity();
            entity.setCheckInDate(tableDto.getCheckInDate());
            entity.setCheckoutDate(tableDto.getCheckoutDate());
            entity.setCurrencyCode(tableDto.getCurrencyCode());
            entity.setDestination(tableDto.getDestination());
            entity.setExtraBed(tableDto.getExtraBed());
            entity.setHotelCategory(tableDto.getHotelCategory());
            entity.setHotelPlan(tableDto.getHotelPlan());
            entity.setNumberOfAdult(tableDto.getNumberOfAdult());
            entity.setNumberOfChild(tableDto.getNumberOfChild());
            entity.setNumberOfInfants(tableDto.getNumberOfInfants());
            entity.setNumberOfNights(tableDto.getNumberOfNights());
            entity.setRoomTariff(tableDto.getRoomTariff());
            entity.setStatus(tableDto.getStatus());
            entity.setTotalPax(tableDto.getTotalPax());
            entity.setTotalPrice(tableDto.getTotalPrice());
            entity.setCoreLeadHotelId((tableDto.getCoreLeadHotelId()==0?null:tableDto.getCoreLeadHotelId()));
            hotelList.add(entity);
        }

        return hotelList;
    }

    public ArrayList<CoreLeadAirEntity> getAirListFromTableData(ObservableList<CoreLeadAir> airObservableList){
        ArrayList<CoreLeadAirEntity> airList=new ArrayList<>();
        for (CoreLeadAir tableDto:airObservableList){
            airList.add(new CoreLeadAirEntity((tableDto.getCoreLeadAirId()==0?null:tableDto.getCoreLeadAirId()),  tableDto.getFromDestination(),  tableDto.getToDestination(),  tableDto.getDepartureDate(),  tableDto.getReturnDate(),  tableDto.getAirlinesOffered(),  tableDto.getCurrencyCode(),  tableDto.getNumberOfAdult(),  tableDto.getNumberOfChild(),  tableDto.getNumberOfInfant(),  tableDto.getTotalPax(),  tableDto.getAdultFare(),  tableDto.getChildFare(),  tableDto.getInfantFare(),  tableDto.getTotalPrice(),  tableDto.getTypeOfTravel(),  tableDto.getClassOfTravel(),  tableDto.getStatus()));
        }
        return airList;
    }
    public ArrayList<CoreLeadRailEntity> getRailListFromTableData(ObservableList<CoreLeadRail> railObservableList){
        ArrayList<CoreLeadRailEntity> railList=new ArrayList<>();
        for (CoreLeadRail tableDto:railObservableList){
            railList.add(new CoreLeadRailEntity( (tableDto.getCoreLeadRailId()==0?null:tableDto.getCoreLeadRailId()),  tableDto.getDepartureCity(),  tableDto.getArrivalCity(),  tableDto.getDepartureDate(),  tableDto.getTrainNumber(),  tableDto.getNumberOfAdult(),  tableDto.getNumberOfChild(),  tableDto.getNumberOfInfant(),  tableDto.getTotalPax(),  tableDto.getAdultFare(),  tableDto.getChildFare(),  tableDto.getTotalFare(),  tableDto.getClassOfTravel(),  tableDto.getStatus()));
        }
        return railList;
    }

    private CoreLeadEntity setValuesFromCoreLeadDto( CoreLead coreLeadDto){

        //initialising new core lead entity object
        CoreLeadEntity coreLeadEntity=new CoreLeadEntity();
        coreLeadEntity.setCoreLeadAirEntities(new ArrayList<>());
        coreLeadEntity.setCoreLeadCommunicationEntity(new CoreLeadCommunicationEntity());
        coreLeadEntity.setCoreLeadHolidaysEntity(new CoreLeadHolidaysEntity());
        coreLeadEntity.setCoreLeadHotelEntities(new ArrayList<>());
        coreLeadEntity.setCoreLeadRailEntities(new ArrayList<>());

        //general Details
        coreLeadEntity.setCoreLeadId(coreLeadDto.getCoreLeadId()==0?null:coreLeadDto.getCoreLeadId());
        coreLeadEntity.setFirstName(coreLeadDto.getFirstName());
        coreLeadEntity.setBranchCode(coreLeadDto.getBranchCode());
        coreLeadEntity.setCallReason(coreLeadDto.getCallReason());
        coreLeadEntity.setChannelCode(coreLeadDto.getChannelCode());
        coreLeadEntity.setCurrencyCode(coreLeadDto.getCurrencyCode());
        coreLeadEntity.setLastName(coreLeadDto.getLastName());
        coreLeadEntity.setLobCode(coreLeadDto.getLobCode());
        coreLeadEntity.setMiddleName(coreLeadDto.getMiddleName());
        coreLeadEntity.setQuerySource(coreLeadDto.getQuerySource());
        coreLeadEntity.setShift(coreLeadDto.getShift());
        coreLeadEntity.setCountry(coreLeadDto.getCountry());
        coreLeadEntity.setEmployeeName(coreLeadDto.getEmployeeName());
        coreLeadEntity.setQueryTime(coreLeadDto.getQuerytime());
        coreLeadEntity.setUserId(coreLeadDto.getUserId());
        coreLeadEntity.setTotalBookingAmount(coreLeadDto.getTotalBookingAmount());


        //communication details
        if(coreLeadDto.getCoreLeadCommunication()!=null) {
            coreLeadEntity.getCoreLeadCommunicationEntity().setCoreLeadCommunicationId(coreLeadDto.getCoreLeadCommunication().getCoreLeadCommunicationId()==0? null:coreLeadDto.getCoreLeadCommunication().getCoreLeadCommunicationId());
            coreLeadEntity.getCoreLeadCommunicationEntity().setIndiaLandline(coreLeadDto.getCoreLeadCommunication().getIndiaLandline());
            coreLeadEntity.getCoreLeadCommunicationEntity().setPaxEmailFirst(coreLeadDto.getCoreLeadCommunication().getPaxEmailFirst());
            coreLeadEntity.getCoreLeadCommunicationEntity().setUsaMobile(coreLeadDto.getCoreLeadCommunication().getUsaMobile());
            coreLeadEntity.getCoreLeadCommunicationEntity().setUsaWorkNumber(coreLeadDto.getCoreLeadCommunication().getUsaWorkNumber());
            coreLeadEntity.getCoreLeadCommunicationEntity().setUsaHome(coreLeadDto.getCoreLeadCommunication().getUsaHome());
            coreLeadEntity.getCoreLeadCommunicationEntity().setIndiaMobile(coreLeadDto.getCoreLeadCommunication().getIndiaMobile());
            coreLeadEntity.getCoreLeadCommunicationEntity().setPaxEmailSecond(coreLeadDto.getCoreLeadCommunication().getPaxEmailSecond());
        }

        //airdetails
        if (coreLeadDto.getCoreLeadAirList()!=null){coreLeadEntity.setCoreLeadAirEntities(coreLeadDto.getCoreLeadAirList());}

        //hotelDetails
        if (coreLeadDto.getCoreLeadHotelList()!=null){coreLeadEntity.setCoreLeadHotelEntities(coreLeadDto.getCoreLeadHotelList());}

        //holidays details
        if(coreLeadDto.getCoreLeadHolidays()!=null){
            coreLeadEntity.getCoreLeadHolidaysEntity().setCoreLeadHolidaysId(coreLeadDto.getCoreLeadHolidays().getCoreLeadHolidaysId()==0? null:coreLeadDto.getCoreLeadHolidays().getCoreLeadHolidaysId());
            coreLeadEntity.getCoreLeadHolidaysEntity().setAdultFare(coreLeadDto.getCoreLeadHolidays().getAdultFare());
            coreLeadEntity.getCoreLeadHolidaysEntity().setChildFare(coreLeadDto.getCoreLeadHolidays().getChildFare());
            coreLeadEntity.getCoreLeadHolidaysEntity().setCurrencyCode(coreLeadDto.getCoreLeadHolidays().getCurrencyCode());
            coreLeadEntity.getCoreLeadHolidaysEntity().setDepartureDate(coreLeadDto.getCoreLeadHolidays().getDepartureDate());
            coreLeadEntity.getCoreLeadHolidaysEntity().setFromDestination(coreLeadDto.getCoreLeadHolidays().getFromDestination());
            coreLeadEntity.getCoreLeadHolidaysEntity().setHotelCategory(coreLeadDto.getCoreLeadHolidays().getHotelCategory());
            coreLeadEntity.getCoreLeadHolidaysEntity().setInfantFare(coreLeadDto.getCoreLeadHolidays().getInfantFare());
            coreLeadEntity.getCoreLeadHolidaysEntity().setNumberOfAdult(coreLeadDto.getCoreLeadHolidays().getNumberOfAdult());
            coreLeadEntity.getCoreLeadHolidaysEntity().setNumberOfChild(coreLeadDto.getCoreLeadHolidays().getNumberOfChild());
            coreLeadEntity.getCoreLeadHolidaysEntity().setNumberOfInfant(coreLeadDto.getCoreLeadHolidays().getNumberOfInfant());
            coreLeadEntity.getCoreLeadHolidaysEntity().setNumberOfNights(coreLeadDto.getCoreLeadHolidays().getNumberOfNights());
            coreLeadEntity.getCoreLeadHolidaysEntity().setReturnDate(coreLeadDto.getCoreLeadHolidays().getReturnDate());
            coreLeadEntity.getCoreLeadHolidaysEntity().setStatus(coreLeadDto.getCoreLeadHolidays().getStatus());
            coreLeadEntity.getCoreLeadHolidaysEntity().setToDestination(coreLeadDto.getCoreLeadHolidays().getToDestination());
            coreLeadEntity.getCoreLeadHolidaysEntity().setTotalPrice(coreLeadDto.getCoreLeadHolidays().getTotalPrice());
            coreLeadEntity.getCoreLeadHolidaysEntity().setTravelType(coreLeadDto.getCoreLeadHolidays().getTravelType());
            coreLeadEntity.getCoreLeadHolidaysEntity().setTotalPax(coreLeadDto.getCoreLeadHolidays().getTotalPax());
        }

        //rail Details
        if(coreLeadDto.getCoreLeadRailList()!=null){coreLeadEntity.setCoreLeadRailEntities(coreLeadDto.getCoreLeadRailList());}
        //notes Details
        coreLeadEntity.setCoreLeadsNotesEntities(coreLeadDto.getCoreLeadNotesEntitySet());

        //bookingDetails
        coreLeadEntity.setCorebookingEntity(coreLeadDto.getCoreBookingEntity());
        return coreLeadEntity;
    }


    private CoreLead setValuesFromCoreLeadEntity( CoreLeadEntity coreLeadEntity){

                           //initialising new core lead entity object
        CoreLead coreLeadDto=new CoreLead();
        coreLeadDto.setCoreLeadAirList(new ArrayList<>());
        coreLeadDto.setCoreLeadCommunication(new CoreLeadCommunication());
        coreLeadDto.setCoreLeadHolidays(new CoreLeadHolidays());
        coreLeadDto.setCoreLeadHotelList(new ArrayList<>());
        coreLeadDto.setCoreLeadRailList(new ArrayList<>());
        coreLeadDto.setCoreLeadNotesEntitySet(new ArrayList<>());

                                  //general Details
        coreLeadDto.setCoreLeadId(coreLeadEntity.getCoreLeadId());
        coreLeadDto.setFirstName(coreLeadEntity.getFirstName());
        coreLeadDto.setBranchCode(coreLeadEntity.getBranchCode());
        coreLeadDto.setCallReason(coreLeadEntity.getCallReason());
        coreLeadDto.setChannelCode(coreLeadEntity.getChannelCode());
        coreLeadDto.setCurrencyCode(coreLeadEntity.getCurrencyCode());
        coreLeadDto.setLastName(coreLeadEntity.getLastName());
        coreLeadDto.setLobCode(coreLeadEntity.getLobCode());
        coreLeadDto.setMiddleName(coreLeadEntity.getMiddleName());
        coreLeadDto.setQuerySource(coreLeadEntity.getQuerySource());
        coreLeadDto.setShift(coreLeadEntity.getShift());
        coreLeadDto.setCountry(coreLeadEntity.getCountry());
        coreLeadDto.setEmployeeName(coreLeadEntity.getEmployeeName());
        coreLeadDto.setQuerytime(coreLeadEntity.getQueryTime());
        coreLeadDto.setUserId(coreLeadEntity.getUserId());
        coreLeadDto.setTotalBookingAmount(coreLeadEntity.getTotalBookingAmount());


                              //communication details
        if(coreLeadEntity.getCoreLeadCommunicationEntity()!=null) {
            coreLeadDto.getCoreLeadCommunication().setCoreLeadCommunicationId(coreLeadEntity.getCoreLeadCommunicationEntity().getCoreLeadCommunicationId());
            coreLeadDto.getCoreLeadCommunication().setIndiaLandline(coreLeadEntity.getCoreLeadCommunicationEntity().getIndiaLandline());
            coreLeadDto.getCoreLeadCommunication().setPaxEmailFirst(coreLeadEntity.getCoreLeadCommunicationEntity().getPaxEmailFirst());
            coreLeadDto.getCoreLeadCommunication().setUsaMobile(coreLeadEntity.getCoreLeadCommunicationEntity().getUsaMobile());
            coreLeadDto.getCoreLeadCommunication().setUsaWorkNumber(coreLeadEntity.getCoreLeadCommunicationEntity().getUsaWorkNumber());
            coreLeadDto.getCoreLeadCommunication().setUsaHome(coreLeadEntity.getCoreLeadCommunicationEntity().getUsaHome());
            coreLeadDto.getCoreLeadCommunication().setIndiaMobile(coreLeadEntity.getCoreLeadCommunicationEntity().getIndiaMobile());
            coreLeadDto.getCoreLeadCommunication().setPaxEmailSecond(coreLeadEntity.getCoreLeadCommunicationEntity().getPaxEmailSecond());
        }

                                  //airdetails
        if (coreLeadEntity.getCoreLeadAirEntities()!=null){coreLeadDto.setCoreLeadAirList(coreLeadEntity.getCoreLeadAirEntities());}

        //hotelDetails
        if (coreLeadEntity.getCoreLeadHotelEntities()!=null){coreLeadDto.setCoreLeadHotelList(coreLeadEntity.getCoreLeadHotelEntities());}

        //holidays details
        if(coreLeadEntity.getCoreLeadHolidaysEntity()!=null){
            coreLeadDto.getCoreLeadHolidays().setCoreLeadHolidaysId(coreLeadEntity.getCoreLeadHolidaysEntity().getCoreLeadHolidaysId());
            coreLeadDto.getCoreLeadHolidays().setAdultFare(coreLeadEntity.getCoreLeadHolidaysEntity().getAdultFare());
            coreLeadDto.getCoreLeadHolidays().setChildFare(coreLeadEntity.getCoreLeadHolidaysEntity().getChildFare());
            coreLeadDto.getCoreLeadHolidays().setCurrencyCode(coreLeadEntity.getCoreLeadHolidaysEntity().getCurrencyCode());
            coreLeadDto.getCoreLeadHolidays().setDepartureDate(coreLeadEntity.getCoreLeadHolidaysEntity().getDepartureDate());
            coreLeadDto.getCoreLeadHolidays().setFromDestination(coreLeadEntity.getCoreLeadHolidaysEntity().getFromDestination());
            coreLeadDto.getCoreLeadHolidays().setHotelCategory(coreLeadEntity.getCoreLeadHolidaysEntity().getHotelCategory());
            coreLeadDto.getCoreLeadHolidays().setInfantFare(coreLeadEntity.getCoreLeadHolidaysEntity().getInfantFare());
            coreLeadDto.getCoreLeadHolidays().setNumberOfAdult(coreLeadEntity.getCoreLeadHolidaysEntity().getNumberOfAdult());
            coreLeadDto.getCoreLeadHolidays().setNumberOfChild(coreLeadEntity.getCoreLeadHolidaysEntity().getNumberOfChild());
            coreLeadDto.getCoreLeadHolidays().setNumberOfInfant(coreLeadEntity.getCoreLeadHolidaysEntity().getNumberOfInfant());
            coreLeadDto.getCoreLeadHolidays().setNumberOfNights(coreLeadEntity.getCoreLeadHolidaysEntity().getNumberOfNights());
            coreLeadDto.getCoreLeadHolidays().setReturnDate(coreLeadEntity.getCoreLeadHolidaysEntity().getReturnDate());
            coreLeadDto.getCoreLeadHolidays().setStatus(coreLeadEntity.getCoreLeadHolidaysEntity().getStatus());
            coreLeadDto.getCoreLeadHolidays().setToDestination(coreLeadEntity.getCoreLeadHolidaysEntity().getToDestination());
            coreLeadDto.getCoreLeadHolidays().setTotalPrice(coreLeadEntity.getCoreLeadHolidaysEntity().getTotalPrice());
            coreLeadDto.getCoreLeadHolidays().setTravelType(coreLeadEntity.getCoreLeadHolidaysEntity().getTravelType());
            coreLeadDto.getCoreLeadHolidays().setTotalPax(coreLeadEntity.getCoreLeadHolidaysEntity().getTotalPax());
        }

        //rail Details
        if(coreLeadEntity.getCoreLeadRailEntities()!=null){coreLeadDto.setCoreLeadRailList(coreLeadEntity.getCoreLeadRailEntities());}
        //notes details
        coreLeadDto.setCoreLeadNotesEntitySet(coreLeadEntity.getCoreLeadsNotesEntities());

        //booking details
        coreLeadDto.setCoreBookingEntity(coreLeadEntity.getCorebookingEntity());
        return coreLeadDto;
    }

    public boolean sendEmailNotification(String emailRecipient){

        try{
            new EmailService().generateAndSendEmail(emailRecipient);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    private int getMaxNumberOfQueries(){
        try {
            return Integer.parseInt(InventoryConfig.getInstance().getAppProperties().getProperty("maxNumberOfQueries"));
        }catch (Exception e){
            logger.warn("error while fetching max no. of queries. returning 100 "+e);
        }
        return 100;
    }

    public Double calculateEntireBookingCharges(CoreLead coreLead){
        double airCharges=0d;
        double railCharges=0d;
        double hotelCharges=0d;

        if (coreLead.getCoreLeadAirList()!=null && !coreLead.getCoreLeadAirList().isEmpty()){
           airCharges= calculateAirBookingCharges(coreLead.getCoreLeadAirList());
        }

        if (coreLead.getCoreLeadRailList()!=null && !coreLead.getCoreLeadRailList().isEmpty()){
            railCharges= calculateRailBookingCharges(coreLead.getCoreLeadRailList());
        }
        if (coreLead.getCoreLeadHotelList()!=null && !coreLead.getCoreLeadHotelList().isEmpty()){
            hotelCharges= calculateHotelBookingCharges(coreLead.getCoreLeadHotelList());
        }

        return airCharges+railCharges+hotelCharges;
    }

    private double calculateAirBookingCharges(List<CoreLeadAirEntity> coreLeadAirArrayList){
        double value=0d;
        try {
            for (CoreLeadAirEntity coreLeadAir : coreLeadAirArrayList) {
                value += Double.valueOf(coreLeadAir.getTotalPrice());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    private double calculateRailBookingCharges(List<CoreLeadRailEntity> coreLeadRailArrayList){
        double value=0d;
        try {
            for (CoreLeadRailEntity leadRail : coreLeadRailArrayList) {
                value += Double.valueOf(leadRail.getTotalFare());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    private double calculateHotelBookingCharges(List<CoreLeadHotelEntity> coreLeadHotelEntityList ){
        double value=0d;
        try {
        for (CoreLeadHotelEntity hotelEntity:coreLeadHotelEntityList) {
            value+=Double.valueOf(hotelEntity.getTotalPrice());
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }
}


package db;

import dto.*;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import service.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class QueryService {
    private Logger logger=Logger.getLogger(QueryService.class);

    public boolean saveQueryData(CoreLead coreLead){

        logger.info("in save queryData to db method");
        if (coreLead==null){
            logger.warn("invalid corelead dto data. Returning.");
            return false;
        }
        try{
            logger.warn("Converting dto object to entity obj to save in db");
            CoreLeadEntity coreLeadEntity= setValuesFromCoreLeadDto(coreLead);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(coreLeadEntity);
            session.getTransaction().commit();
           // HibernateUtil.shutdown();
            return true;
        }
        catch (Exception e){
            logger.warn("unable to save queryPage data::: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public CoreLeadEntity setValuesFromCoreLeadDto(CoreLead coreLeadDto){

        //initialising new core lead entity object
        CoreLeadEntity coreLeadEntity=new CoreLeadEntity();
        coreLeadEntity.setCoreLeadAirEntity(new CoreLeadAirEntity());
        coreLeadEntity.setCoreLeadCommunicationEntity(new CoreLeadCommunicationEntity());
        coreLeadEntity.setCoreLeadHolidaysEntity(new CoreLeadHolidaysEntity());
        coreLeadEntity.setCoreLeadHotelEntity(new CoreLeadHotelEntity());
        coreLeadEntity.setCoreLeadRailEntity(new CoreLeadRailEntity());

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


        //communication details
        if(coreLeadDto.getCoreLeadCommunication()!=null) {
            coreLeadEntity.getCoreLeadCommunicationEntity().setCoreLeadCommunicationId(coreLeadDto.getCoreLeadCommunication().getCoreLeadCommunicationId()==0? null:coreLeadDto.getCoreLeadCommunication().getCoreLeadCommunicationId());
            coreLeadEntity.getCoreLeadCommunicationEntity().setLandline(coreLeadDto.getCoreLeadCommunication().getLandline());
            coreLeadEntity.getCoreLeadCommunicationEntity().setPaxEmail(coreLeadDto.getCoreLeadCommunication().getPaxEmail());
            coreLeadEntity.getCoreLeadCommunicationEntity().setUsaMobile(coreLeadDto.getCoreLeadCommunication().getUsaMobile());
            coreLeadEntity.getCoreLeadCommunicationEntity().setUsaWorkNumber(coreLeadDto.getCoreLeadCommunication().getUsaWorkNumber());
        }

        //airdetails
        if (coreLeadDto.getCoreLeadAir()!=null){
            coreLeadEntity.getCoreLeadAirEntity().setCoreLeadAirId(coreLeadDto.getCoreLeadAir().getCoreLeadAirId()==0? null:coreLeadDto.getCoreLeadAir().getCoreLeadAirId());
            coreLeadEntity.getCoreLeadAirEntity().setCheckInDate(coreLeadDto.getCoreLeadAir().getCheckInDate());
            coreLeadEntity.getCoreLeadAirEntity().setCheckoutDate(coreLeadDto.getCoreLeadAir().getCheckoutDate());
            coreLeadEntity.getCoreLeadAirEntity().setCurrencyCode(coreLeadDto.getCoreLeadAir().getCurrencyCode());
            coreLeadEntity.getCoreLeadAirEntity().setDestination(coreLeadDto.getCoreLeadAir().getDestination());
            coreLeadEntity.getCoreLeadAirEntity().setExtraBed(coreLeadDto.getCoreLeadAir().getExtraBed());
            coreLeadEntity.getCoreLeadAirEntity().setHotelCategory(coreLeadDto.getCoreLeadAir().getHotelCategory());
            coreLeadEntity.getCoreLeadAirEntity().setHotelPlan(coreLeadDto.getCoreLeadAir().getHotelPlan());
            coreLeadEntity.getCoreLeadAirEntity().setNumberOfAdult(coreLeadDto.getCoreLeadAir().getNumberOfAdult());
            coreLeadEntity.getCoreLeadAirEntity().setNumberOfChild(coreLeadDto.getCoreLeadAir().getNumberOfChild());
            coreLeadEntity.getCoreLeadAirEntity().setNumberOfInfants(coreLeadDto.getCoreLeadAir().getNumberOfInfants());
            coreLeadEntity.getCoreLeadAirEntity().setNumberOfNights(coreLeadDto.getCoreLeadAir().getNumberOfNights());
            coreLeadEntity.getCoreLeadAirEntity().setRoomTariff(coreLeadDto.getCoreLeadAir().getRoomTariff());
            coreLeadEntity.getCoreLeadAirEntity().setStatus(coreLeadDto.getCoreLeadAir().getStatus());
            coreLeadEntity.getCoreLeadAirEntity().setTotalPax(coreLeadDto.getCoreLeadAir().getTotalPax());
            coreLeadEntity.getCoreLeadAirEntity().setTotalPrice(coreLeadDto.getCoreLeadAir().getTotalPrice());
        }

        //hotelDetails
        if (coreLeadDto.getCoreLeadHotel()!=null){
            coreLeadEntity.getCoreLeadHotelEntity().setCoreLeadHotelId(coreLeadDto.getCoreLeadHotel().getCoreLeadHotelId()==0? null:coreLeadDto.getCoreLeadHotel().getCoreLeadHotelId());
            coreLeadEntity.getCoreLeadHotelEntity().setCheckInDate(coreLeadDto.getCoreLeadHotel().getCheckInDate());
            coreLeadEntity.getCoreLeadHotelEntity().setCheckInDate(coreLeadDto.getCoreLeadHotel().getCheckInDate());
            coreLeadEntity.getCoreLeadHotelEntity().setCheckoutDate(coreLeadDto.getCoreLeadHotel().getCheckoutDate());
            coreLeadEntity.getCoreLeadHotelEntity().setCurrencyCode(coreLeadDto.getCoreLeadHotel().getCurrencyCode());
            coreLeadEntity.getCoreLeadHotelEntity().setDestination(coreLeadDto.getCoreLeadHotel().getDestination());
            coreLeadEntity.getCoreLeadHotelEntity().setExtraBed(coreLeadDto.getCoreLeadHotel().getExtraBed());
            coreLeadEntity.getCoreLeadHotelEntity().setHotelCategory(coreLeadDto.getCoreLeadHotel().getHotelCategory());
            coreLeadEntity.getCoreLeadHotelEntity().setHotelPlan(coreLeadDto.getCoreLeadHotel().getHotelPlan());
            coreLeadEntity.getCoreLeadHotelEntity().setNumberOfAdult(coreLeadDto.getCoreLeadHotel().getNumberOfAdult());
            coreLeadEntity.getCoreLeadHotelEntity().setNumberOfChild(coreLeadDto.getCoreLeadHotel().getNumberOfChild());
            coreLeadEntity.getCoreLeadHotelEntity().setNumberOfInfants(coreLeadDto.getCoreLeadHotel().getNumberOfInfants());
            coreLeadEntity.getCoreLeadHotelEntity().setNumberOfNights(coreLeadDto.getCoreLeadHotel().getNumberOfNights());
            coreLeadEntity.getCoreLeadHotelEntity().setRoomTariff(coreLeadDto.getCoreLeadHotel().getRoomTariff());
            coreLeadEntity.getCoreLeadHotelEntity().setStatus(coreLeadDto.getCoreLeadHotel().getStatus());
            coreLeadEntity.getCoreLeadHotelEntity().setTotalPax(coreLeadDto.getCoreLeadHotel().getTotalPax());
            coreLeadEntity.getCoreLeadHotelEntity().setTotalPrice(coreLeadDto.getCoreLeadHotel().getTotalPrice());
        }

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
        }

        //rail Details
        if(coreLeadDto.getCoreLeadRail()!=null){
            coreLeadEntity.getCoreLeadRailEntity().setCoreLeadRailId(coreLeadDto.getCoreLeadRail().getCoreLeadRailId()==0? null:coreLeadDto.getCoreLeadRail().getCoreLeadRailId());
            coreLeadEntity.getCoreLeadRailEntity().setAdultFare(coreLeadDto.getCoreLeadRail().getAdultFare());
            coreLeadEntity.getCoreLeadRailEntity().setArrivalCity(coreLeadDto.getCoreLeadRail().getArrivalCity());
            coreLeadEntity.getCoreLeadRailEntity().setChildFare(coreLeadDto.getCoreLeadRail().getChildFare());
            coreLeadEntity.getCoreLeadRailEntity().setClassOfTravel(coreLeadDto.getCoreLeadRail().getClassOfTravel());
            coreLeadEntity.getCoreLeadRailEntity().setDepartureCity(coreLeadDto.getCoreLeadRail().getDepartureCity());
            coreLeadEntity.getCoreLeadRailEntity().setDepartureDate(coreLeadDto.getCoreLeadRail().getDepartureDate());
            coreLeadEntity.getCoreLeadRailEntity().setNumberOfAdult(coreLeadDto.getCoreLeadRail().getNumberOfAdult());
            coreLeadEntity.getCoreLeadRailEntity().setNumberOfChild(coreLeadDto.getCoreLeadRail().getNumberOfChild());
            coreLeadEntity.getCoreLeadRailEntity().setNumberOfInfant(coreLeadDto.getCoreLeadRail().getNumberOfInfant());
            coreLeadEntity.getCoreLeadRailEntity().setStatus(coreLeadDto.getCoreLeadRail().getStatus());
            coreLeadEntity.getCoreLeadRailEntity().setTotalFare(coreLeadDto.getCoreLeadRail().getTotalFare());
            coreLeadEntity.getCoreLeadRailEntity().setTotalPax(coreLeadDto.getCoreLeadRail().getTotalPax());
            coreLeadEntity.getCoreLeadRailEntity().setTrainNumber(coreLeadDto.getCoreLeadRail().getTrainNumber());
        }
        return coreLeadEntity;
    }


    public CoreLead setValuesFromCoreLeadEntity(CoreLeadEntity coreLeadEntity){

        //initialising new core lead entity object
        CoreLead coreLeadDto=new CoreLead();
        coreLeadDto.setCoreLeadAir(new CoreLeadAir());
        coreLeadDto.setCoreLeadCommunication(new CoreLeadCommunication());
        coreLeadDto.setCoreLeadHolidays(new CoreLeadHolidays());
        coreLeadDto.setCoreLeadHotel(new CoreLeadHotel());
        coreLeadDto.setCoreLeadRail(new CoreLeadRail());

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


        //communication details
        if(coreLeadEntity.getCoreLeadCommunicationEntity()!=null) {
            coreLeadDto.getCoreLeadCommunication().setCoreLeadCommunicationId(coreLeadEntity.getCoreLeadCommunicationEntity().getCoreLeadCommunicationId());
            coreLeadDto.getCoreLeadCommunication().setLandline(coreLeadEntity.getCoreLeadCommunicationEntity().getLandline());
            coreLeadDto.getCoreLeadCommunication().setPaxEmail(coreLeadEntity.getCoreLeadCommunicationEntity().getPaxEmail());
            coreLeadDto.getCoreLeadCommunication().setUsaMobile(coreLeadEntity.getCoreLeadCommunicationEntity().getUsaMobile());
            coreLeadDto.getCoreLeadCommunication().setUsaWorkNumber(coreLeadEntity.getCoreLeadCommunicationEntity().getUsaWorkNumber());
        }

        //airdetails
        if (coreLeadEntity.getCoreLeadAirEntity()!=null){
            coreLeadDto.getCoreLeadAir().setCoreLeadAirId(coreLeadEntity.getCoreLeadAirEntity().getCoreLeadAirId());
            coreLeadDto.getCoreLeadAir().setCheckInDate(coreLeadEntity.getCoreLeadAirEntity().getCheckInDate());
            coreLeadDto.getCoreLeadAir().setCheckoutDate(coreLeadEntity.getCoreLeadAirEntity().getCheckoutDate());
            coreLeadDto.getCoreLeadAir().setCurrencyCode(coreLeadEntity.getCoreLeadAirEntity().getCurrencyCode());
            coreLeadDto.getCoreLeadAir().setDestination(coreLeadEntity.getCoreLeadAirEntity().getDestination());
            coreLeadDto.getCoreLeadAir().setExtraBed(coreLeadEntity.getCoreLeadAirEntity().getExtraBed());
            coreLeadDto.getCoreLeadAir().setHotelCategory(coreLeadEntity.getCoreLeadAirEntity().getHotelCategory());
            coreLeadDto.getCoreLeadAir().setHotelPlan(coreLeadEntity.getCoreLeadAirEntity().getHotelPlan());
            coreLeadDto.getCoreLeadAir().setNumberOfAdult(coreLeadEntity.getCoreLeadAirEntity().getNumberOfAdult());
            coreLeadDto.getCoreLeadAir().setNumberOfChild(coreLeadEntity.getCoreLeadAirEntity().getNumberOfChild());
            coreLeadDto.getCoreLeadAir().setNumberOfInfants(coreLeadEntity.getCoreLeadAirEntity().getNumberOfInfants());
            coreLeadDto.getCoreLeadAir().setNumberOfNights(coreLeadEntity.getCoreLeadAirEntity().getNumberOfNights());
            coreLeadDto.getCoreLeadAir().setRoomTariff(coreLeadEntity.getCoreLeadAirEntity().getRoomTariff());
            coreLeadDto.getCoreLeadAir().setStatus(coreLeadEntity.getCoreLeadAirEntity().getStatus());
            coreLeadDto.getCoreLeadAir().setTotalPax(coreLeadEntity.getCoreLeadAirEntity().getTotalPax());
            coreLeadDto.getCoreLeadAir().setTotalPrice(coreLeadEntity.getCoreLeadAirEntity().getTotalPrice());
        }

        //hotelDetails
        if (coreLeadEntity.getCoreLeadHotelEntity()!=null){
            coreLeadDto.getCoreLeadHotel().setCoreLeadHotelId(coreLeadEntity.getCoreLeadHotelEntity().getCoreLeadHotelId());
            coreLeadDto.getCoreLeadHotel().setCheckInDate(coreLeadEntity.getCoreLeadHotelEntity().getCheckInDate());
            coreLeadDto.getCoreLeadHotel().setCheckInDate(coreLeadEntity.getCoreLeadHotelEntity().getCheckInDate());
            coreLeadDto.getCoreLeadHotel().setCheckoutDate(coreLeadEntity.getCoreLeadHotelEntity().getCheckoutDate());
            coreLeadDto.getCoreLeadHotel().setCurrencyCode(coreLeadEntity.getCoreLeadHotelEntity().getCurrencyCode());
            coreLeadDto.getCoreLeadHotel().setDestination(coreLeadEntity.getCoreLeadHotelEntity().getDestination());
            coreLeadDto.getCoreLeadHotel().setExtraBed(coreLeadEntity.getCoreLeadHotelEntity().getExtraBed());
            coreLeadDto.getCoreLeadHotel().setHotelCategory(coreLeadEntity.getCoreLeadHotelEntity().getHotelCategory());
            coreLeadDto.getCoreLeadHotel().setHotelPlan(coreLeadEntity.getCoreLeadHotelEntity().getHotelPlan());
            coreLeadDto.getCoreLeadHotel().setNumberOfAdult(coreLeadEntity.getCoreLeadHotelEntity().getNumberOfAdult());
            coreLeadDto.getCoreLeadHotel().setNumberOfChild(coreLeadEntity.getCoreLeadHotelEntity().getNumberOfChild());
            coreLeadDto.getCoreLeadHotel().setNumberOfInfants(coreLeadEntity.getCoreLeadHotelEntity().getNumberOfInfants());
            coreLeadDto.getCoreLeadHotel().setNumberOfNights(coreLeadEntity.getCoreLeadHotelEntity().getNumberOfNights());
            coreLeadDto.getCoreLeadHotel().setRoomTariff(coreLeadEntity.getCoreLeadHotelEntity().getRoomTariff());
            coreLeadDto.getCoreLeadHotel().setStatus(coreLeadEntity.getCoreLeadHotelEntity().getStatus());
            coreLeadDto.getCoreLeadHotel().setTotalPax(coreLeadEntity.getCoreLeadHotelEntity().getTotalPax());
            coreLeadDto.getCoreLeadHotel().setTotalPrice(coreLeadEntity.getCoreLeadHotelEntity().getTotalPrice());
        }

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
        }

        //rail Details
        if(coreLeadEntity.getCoreLeadRailEntity()!=null){
            coreLeadDto.getCoreLeadRail().setCoreLeadRailId(coreLeadEntity.getCoreLeadRailEntity().getCoreLeadRailId());
            coreLeadDto.getCoreLeadRail().setAdultFare(coreLeadEntity.getCoreLeadRailEntity().getAdultFare());
            coreLeadDto.getCoreLeadRail().setArrivalCity(coreLeadEntity.getCoreLeadRailEntity().getArrivalCity());
            coreLeadDto.getCoreLeadRail().setChildFare(coreLeadEntity.getCoreLeadRailEntity().getChildFare());
            coreLeadDto.getCoreLeadRail().setClassOfTravel(coreLeadEntity.getCoreLeadRailEntity().getClassOfTravel());
            coreLeadDto.getCoreLeadRail().setDepartureCity(coreLeadEntity.getCoreLeadRailEntity().getDepartureCity());
            coreLeadDto.getCoreLeadRail().setDepartureDate(coreLeadEntity.getCoreLeadRailEntity().getDepartureDate());
            coreLeadDto.getCoreLeadRail().setNumberOfAdult(coreLeadEntity.getCoreLeadRailEntity().getNumberOfAdult());
            coreLeadDto.getCoreLeadRail().setNumberOfChild(coreLeadEntity.getCoreLeadRailEntity().getNumberOfChild());
            coreLeadDto.getCoreLeadRail().setNumberOfInfant(coreLeadEntity.getCoreLeadRailEntity().getNumberOfInfant());
            coreLeadDto.getCoreLeadRail().setStatus(coreLeadEntity.getCoreLeadRailEntity().getStatus());
            coreLeadDto.getCoreLeadRail().setTotalFare(coreLeadEntity.getCoreLeadRailEntity().getTotalFare());
            coreLeadDto.getCoreLeadRail().setTotalPax(coreLeadEntity.getCoreLeadRailEntity().getTotalPax());
            coreLeadDto.getCoreLeadRail().setTrainNumber(coreLeadEntity.getCoreLeadRailEntity().getTrainNumber());
        }
        return coreLeadDto;
    }

    public ArrayList<QueriesListDto>getAllQueriesList(){
        ArrayList<QueriesListDto> queriesListDtoArrayList=new ArrayList<>();

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from CoreLeadEntity");
        List<CoreLeadEntity> results = query.list();
        for(CoreLeadEntity coreLeadEntity:results){
            QueriesListDto limitedQueriesListDto=new QueriesListDto();
            limitedQueriesListDto.setBranchCode(coreLeadEntity.getBranchCode());
            limitedQueriesListDto.setCallReason(coreLeadEntity.getCallReason());
            limitedQueriesListDto.setEmail(coreLeadEntity.getCoreLeadCommunicationEntity().getPaxEmail());
            limitedQueriesListDto.setFirstname(coreLeadEntity.getFirstName());
            limitedQueriesListDto.setLastName(coreLeadEntity.getLastName());
            limitedQueriesListDto.setQueryId(coreLeadEntity.getCoreLeadId());
            limitedQueriesListDto.setCoreLeadDto(setValuesFromCoreLeadEntity(coreLeadEntity));
            queriesListDtoArrayList.add(limitedQueriesListDto);
        }

        return queriesListDtoArrayList;
    }

}


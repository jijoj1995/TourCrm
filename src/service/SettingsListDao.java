package service;

import org.apache.log4j.Logger;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class SettingsListDao {

    Logger logger=Logger.getLogger(SettingsListDao.class);
    public String getCurrentIpAdddres(){
        try {
            logger.info("fetching current ip address of the system");
            Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
            while (ifaces.hasMoreElements()) {
                NetworkInterface iface = ifaces.nextElement();
                Enumeration<InetAddress> addresses = iface.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        logger.info("fetched ip address==="+addr.getHostAddress());
                        return addr.getHostAddress();
                    }
                }
            }
        }
        catch (Exception e){
            logger.warn("unable to fetch ip addres of the system"+e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
}

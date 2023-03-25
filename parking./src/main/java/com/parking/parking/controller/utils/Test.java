//package com.parking.parking.controller.utils;
//    import com.maxmind.geoip2.DatabaseReader;
//import com.maxmind.geoip2.model.CityResponse;
//import com.maxmind.geoip2.record.Location;
//
//import java.io.File;
//import java.net.InetAddress;
//
//    public class Test {
//
//        public static void main(String[] args) throws Exception {
//            File database = new File("/path/to/GeoIP2-City.mmdb");
//            DatabaseReader reader = new DatabaseReader.Builder(database).build();
//            InetAddress ipAddress = InetAddress.getByName("128.101.101.101");
//            CityResponse response = reader.city(ipAddress);
//            Location location = response.getLocation();
//            System.out.println("Lat: " + location.getLatitude());
//            System.out.println("Lon: " + location.getLongitude());
//        }
//    }
//
//}

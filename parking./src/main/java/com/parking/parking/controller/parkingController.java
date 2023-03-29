package com.parking.parking.controller;

import com.parking.parking.controller.Mpesa.NodeApiEndpoint;
import com.parking.parking.controller.Sms.ApiClient;
import com.parking.parking.controller.utils.Global;
import com.parking.parking.controller.utils.Slot;
import com.parking.parking.models.DbConfig;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;


@RestController
public class parkingController extends Thread{
    Global globalValues = new Global();
    ApiClient apiClient = new ApiClient();
    DbConfig dbConfig = new DbConfig();

    @GetMapping("/")
    public ModelAndView Search(Model model) throws SQLException {
        String[] images={"images/ot1.jpg","images/lot2.jpeg","images/lot3.jpg"};
        ArrayList iframe = new ArrayList();
        ModelAndView mav = new ModelAndView("index");
        List<Slot> slots = new ArrayList<>();
        String query = "select * from places;";
        List<String[]> streets = dbConfig.executeQuery(query);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/parking-lots");
        int i=0;
        for (String[] street : streets) {
            String url = UriComponentsBuilder.fromUriString("/reserve").queryParam("id", street[2]).queryParam("lot-name", street[0]).toUriString();
            slots.add(new Slot(street[0], images[i],"Slots available: "+String.valueOf(42-globalValues.availableSlots()[i]), street[3]+" Street",  url,""+street[4],street[5]));
            iframe.add(street[5]);
            System.out.println(street[4]);
            i+=1;
        }
        model.addAttribute("slots",slots);
        model.addAttribute( "maps",iframe);
        model.addAttribute("images",images);
        return mav;
    }

    @GetMapping("/reserve")
    public ModelAndView Details(@RequestParam("id") String id,Model model){
        ModelAndView mav = new ModelAndView("comfirmation");
        return mav;
    }
    @GetMapping("/search")
    public ModelAndView search(@RequestParam("search") String search,Model model) throws SQLException {
        String[] images={"images/ot1.jpg","images/lot2.jpeg","images/lot3.jpg"};
        ArrayList iframe = new ArrayList();
        ModelAndView mav = new ModelAndView("searchv2");
        List<Slot> slots = new ArrayList<>();

        String query = "select * from places where lotName like'%"+search+"%' or city like'%"+search+"%' or street like'%"+search+"%';";
        List<String[]> streets = dbConfig.executeQuery(query);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/parking-lots");
        int i=0;
        for (String[] street : streets) {
            String url = UriComponentsBuilder.fromUriString("/reserve").queryParam("id", street[2]).queryParam("lot-name", street[0]).toUriString();
            slots.add(new Slot(street[0], images[i],"Slots available: "+String.valueOf(42-globalValues.availableSlots()[i]), street[3]+" Street",  url,""+street[4],street[5]));
            iframe.add(street[5]);
            System.out.println(street[4]);
            i+=1;
        }
        model.addAttribute("slots",slots);
        model.addAttribute( "maps",iframe);
        model.addAttribute("images",images);
        return mav;
    }
    @GetMapping("/sendpayment")
    public ModelAndView tt(@RequestParam("phone") String phone) throws InterruptedException {
        RedirectView redirectView = new RedirectView();
        String url = "http://localhost:6067/stkpush?phone="+phone;
        System.out.println(url);
        NodeApiEndpoint nodeApiEndpoint = new NodeApiEndpoint(url);
        String data = nodeApiEndpoint.getData();
        TimeUnit.SECONDS.sleep(10);

        try {
            JSONObject jsonObj = new JSONObject(data);
            String responseCode = jsonObj.getString("ResponseCode");
            ModelAndView mav = new ModelAndView("reserve");
            return mav;
        }catch (JSONException e){
            ModelAndView mav = new ModelAndView("failed-payment");
            return mav;
        }
        }
    ArrayList<String> details= new ArrayList<>();
    @GetMapping("/reserve-processing")
    public ModelAndView reserve_processing(
            @RequestParam("id") String id,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("vehicle_type") String vehicle_type,
            @RequestParam("parking_duration") String parking_duration,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("license_plate") String license_plate,
            Model model) throws SQLException {
        String paymentId= "";
        ModelAndView mav = new ModelAndView("failed-payment");;
        try {
            System.out.println();
            if(license_plate.charAt(0) !='K'){

            }else {
                String q1 ="INSERT INTO reservations(date_, lotId, plate_number, size_, slot_number, entry_time, departure_time, charge, payment_id, auth) " +
                        "VALUES ('" + date + "', '" + id + "', '" + license_plate.toUpperCase().replace("\\s","") + "', '" + vehicle_type + "', '" + slotPicker().toUpperCase() + "', '" + time + "', '" +
                        calculateDeparture(time,parking_duration) + "', '" + 100*parseInt(parking_duration) + "', '" + paymentId + "', '" + authToken() + "')";
                        try{
                            dbConfig.executeQuery(q1);
                            mav = new ModelAndView("success-reservation");
                                System.out.println("succes");
                        }catch (Exception e){
                            System.out.println(e);
                            mav = new ModelAndView("success-reservation");
                        }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return mav;
    }
    public String slotPicker(){
        return authToken();
    }

    public String calculateDeparture(String entry, String time_){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime entryTime = LocalTime.parse(entry, formatter);
        LocalTime departureTime = entryTime.plusHours(Long.parseLong(time_));
        String departure = departureTime.format(formatter);
        return  departure;
    }
    public String authToken(){
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int LENGTH = 6;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}

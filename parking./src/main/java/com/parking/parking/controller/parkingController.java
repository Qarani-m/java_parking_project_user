package com.parking.parking.controller;

import com.parking.parking.controller.utils.Global;
import com.parking.parking.controller.utils.NodeApiEndpoint;
import com.parking.parking.controller.utils.Slot;
import com.parking.parking.models.DbConfig;
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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Float.parseFloat;




@RestController
public class parkingController {
    Global globalValues = new Global();

    DbConfig dbConfig = new DbConfig();
    @GetMapping("/hello")
    public ModelAndView hello(Model model){
        ModelAndView mav = new ModelAndView("index");
        List<Slot> slots = new ArrayList<>();
        slots.add(new Slot("Street C", "true", "22","url__3","position","k","jj"));
        mav.addObject("slots", slots);
        return mav;
    }
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
        ModelAndView mav = new ModelAndView("reserve");
        String url = UriComponentsBuilder.fromUriString("/reserve-processing").toUriString();

        model.addAttribute("id",id);
        model.addAttribute("url",url   );
        return mav;
    }
    @GetMapping("/reserve-processing")
    public ModelAndView reserve_processing(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("license_plate") String license_plate,
            @RequestParam("vehicle_type") String vehicle_type,
            @RequestParam("parking_duration") String parking_duration,
            @RequestParam("date") String date,
            @RequestParam("time") String timeEntry,
            @RequestParam("terms_and_conditions") String terms_and_conditions,
            Model model) throws SQLException {
        ModelAndView mav = null;
        try {
            selectSlot(id);
            mav = new ModelAndView("comfirmation");














            String auth = authToken();
            String slot = "C1R2S06";
            String departure = calculateDeparture(timeEntry, parking_duration);
            String charge = String.valueOf(50 * parseFloat(parking_duration));
//            String query1 = "INSERT INTO reservations(date_, lotId,plate_number, size_, slot_number, entry_time, departure_time, charge, payment_id, auth) VALUES ('" + date + "' ,'"+id+"' '" + license_plate + "', '" + vehicle_type + "', '" + slot + "', '" + timeEntry + "', '" + departure + "', " + charge + ", '101', '" + auth + "');";
//            String query2 = "insert into slots(slot_id,occupied, reserved)values('" + "C1R2S06" + "', false, true) ;";
//            dbConfig.executeQuery(query1);
//            dbConfig.executeQuery(query2);
        } catch (Exception e) {
            System.out.println(e);
        }
        return mav;
    }

    private void selectSlot(String id) {
        if(id.toLowerCase().startsWith("f3")){
        }
    }

    public static void main(String[] args) {
        generateSequence();

    }
    public static String[] generateSequence() {
        boolean found = false;
        String[] seq ={"C1R2S04","C2R1S02"};
        String[] sequence = new String[200];
        int index = 0;
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 4; j++) {
                for (int k = 1; k <= 12; k++) {
                    sequence[index] = "C" + i + "R" + j + "S" + String.format("%02d", k);













                    index++;
                }
            }
        }
        for (String element : sequence) {
            if (element.equals("C1R2S03")) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println( " exists in the array.");
        } else {
            System.out.println( " does not exist in the array.");
        }

        return sequence;
    }

    @GetMapping("/ttt")
    public RedirectView tt(){
        RedirectView redirectView = new RedirectView();
        NodeApiEndpoint nodeApiEndpoint = new NodeApiEndpoint("http://localhost:6065/stkpush");
        NodeApiEndpoint djangoApiEndpoint = new NodeApiEndpoint("http://localhost:7989/sendSms/");


        String data = nodeApiEndpoint.getData();
        return redirectView;
    }
    public String calculateDeparture(String entry, String time_){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime entryTime = LocalTime.parse(entry, formatter);
        LocalTime departureTime = entryTime.plusHours(Long.parseLong(time_));
        String departure = departureTime.format(formatter);
        System.out.println(departure.getClass());
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
        System.out.println(sb.toString());
        return sb.toString();
    }
}

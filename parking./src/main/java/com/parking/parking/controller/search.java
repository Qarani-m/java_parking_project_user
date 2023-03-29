//package com.parking.parking.controller;
//
//import com.parking.parking.controller.utils.Slot;
//import com.twilio.rest.api.v2010.account.Application;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class search{
//
//    @GetMapping("/")
//    public ModelAndView Search(Model model) throws SQLException {
//        String[] images={"images/ot1.jpg","images/lot2.jpeg","images/lot3.jpg"};
//        ArrayList iframe = new ArrayList();
//        ModelAndView mav = new ModelAndView("index");
//        List<Slot> slots = new ArrayList<>();
//        String query = "select * from places;";
//        List<String[]> streets = dbConfig.executeQuery(query);
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/parking-lots");
//        int i=0;
//        for (String[] street : streets) {
//            String url = UriComponentsBuilder.fromUriString("/reserve").queryParam("id", street[2]).queryParam("lot-name", street[0]).toUriString();
//            slots.add(new Slot(street[0], images[i],"Slots available: "+String.valueOf(42-globalValues.availableSlots()[i]), street[3]+" Street",  url,""+street[4],street[5]));
//            iframe.add(street[5]);
//            System.out.println(street[4]);
//            i+=1;
//        }
//        model.addAttribute("slots",slots);
//        model.addAttribute( "maps",iframe);
//        model.addAttribute("images",images);
//        return mav;
//    }
//}

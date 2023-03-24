package com.parking.parking.controller;

import com.parking.parking.controller.utils.Global;
import com.parking.parking.controller.utils.Slot;
import com.parking.parking.models.DbConfig;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class parkingController {
    Global globalValues = new Global();

    DbConfig dbConfig = new DbConfig();
    @GetMapping("/hello")
    public ModelAndView hello(Model model){
        ModelAndView mav = new ModelAndView("index");
        List<Slot> slots = new ArrayList<>();
        slots.add(new Slot("Street C", "true", "22","url__3","position"));
        mav.addObject("slots", slots);
        return mav;
    }
    @GetMapping("/search")
    public ModelAndView Search(Model model) throws SQLException {
        ModelAndView mav = new ModelAndView("index");
        List<Slot> slots = new ArrayList<>();
        String query = "select * from places;";
        List<String[]> streets = dbConfig.executeQuery(query);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/parking-lots");
int i=0;
        for (String[] street : streets) {
            String url = UriComponentsBuilder.fromUriString("/reserve").queryParam("id", street[2]).queryParam("lot-name", street[0]).toUriString();
            System.out.println(url);
            slots.add(new Slot(street[0], "Slots available: "+String.valueOf(42-globalValues.availableSlots()[i]), street[3]+" Street",  url,"position"));
            System.out.println(street[2]);
            i+=1;
        }
        model.addAttribute("slots",slots);
        return mav;
    }

    @GetMapping("/reserve")
    public ModelAndView Details(@RequestParam("id") String id,@RequestParam("lot-name") String name,Model model){
        ModelAndView mav = new ModelAndView("reserve");
        model.addAttribute("name",name);
        String url = UriComponentsBuilder.fromUriString("/reserve-processing").queryParam("id", id).toUriString();
       return mav;
    }
}

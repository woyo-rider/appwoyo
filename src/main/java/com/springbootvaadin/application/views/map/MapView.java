package com.springbootvaadin.application.views.map;

import com.springbootvaadin.application.views.MainLayout;
import com.vaadin.flow.component.map.Map;
import com.vaadin.flow.component.map.configuration.Coordinate;
import com.vaadin.flow.component.map.configuration.View;
import com.vaadin.flow.component.map.configuration.feature.MarkerFeature;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PageTitle("Map")
@Route(value = "map", layout = MainLayout.class)
@PermitAll
public class MapView extends VerticalLayout {

    private Map map = new Map();

    public MapView() {
        setSizeFull();
        setPadding(false);
        map.getElement().setAttribute("theme", "borderless");
        View view = map.getView();
//        view.setCenter(Coordinate.fromLonLat(10.0, 55.0));
        view.setCenter(Coordinate.fromLonLat(-3.9622642, 42.0395433));
//        view.setZoom(4);
//        addAndExpand(map);

//        map.setCenter(Coordinate.fromLonLat(-3.9622642, 42.0395433));
        add(map);


        // For Vaadin 23.1, use Coordinate.fromLonLat to create coordinates
        // from longitude and latitude
        Coordinate vaadinHqCoordinates = new Coordinate(22.29985, 60.45234);
        Coordinate germanOfficeCoordinates = new Coordinate(13.45489, 52.51390);
        Coordinate usOfficeCoordinates = new Coordinate(-121.92163, 37.36821);

        // Add marker for Vaadin HQ, using default marker image
//        MarkerFeature vaadinHq = new MarkerFeature(vaadinHqCoordinates);
        MarkerFeature vaadinHq = new MarkerFeature(Coordinate.fromLonLat(22.29985, 60.45234));
        map.getFeatureLayer().addFeature(vaadinHq);

        MarkerFeature germanOff = new MarkerFeature(Coordinate.fromLonLat(13.45489, 52.51390));
        map.getFeatureLayer().addFeature(germanOff);

        MarkerFeature usOffice = new MarkerFeature(Coordinate.fromLonLat(-121.92163, 37.36821));
        map.getFeatureLayer().addFeature(usOffice);

//        MarkerFeature car1 = new MarkerFeature(Coordinate.fromLonLat(-3.9354966, 5.2560267));
//        map.getFeatureLayer().addFeature(car1);
//        MarkerFeature car2 = new MarkerFeature(Coordinate.fromLonLat(-3.9329133, 5.255902));
//        map.getFeatureLayer().addFeature(car2);

        /****************

        // Add marker for Vaadin office in Germany, using image from URL
        Icon.Options germanFlagIconOptions = new Icon.Options();
        germanFlagIconOptions.setSrc("images/german_flag.png");
        Icon germanFlagIcon = new Icon(germanFlagIconOptions);
        MarkerFeature germanOffice = new MarkerFeature(germanOfficeCoordinates, germanFlagIcon);
        map.getFeatureLayer().addFeature(germanOffice);

        // Add marker for Vaadin office in the US, using image from a StreamResource
        StreamResource streamResource = new StreamResource("us-flag.png",
                () -> getClass().getResourceAsStream("/META-INF/resources/images/us-flag.png"));
        Icon.Options usFlagIconOptions = new Icon.Options();
        usFlagIconOptions.setImg(streamResource);
        Icon usFlagIcon = new Icon(usFlagIconOptions);
        MarkerFeature usOffice = new MarkerFeature(usOfficeCoordinates, usFlagIcon);
        map.getFeatureLayer().addFeature(usOffice);

         ****************/
    }
}

package com.springbootvaadin.application.views.woyo;

import com.springbootvaadin.application.data.RideData;
import com.springbootvaadin.application.data.RideDetails;
import com.springbootvaadin.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Cancelled Rides")
@Route(value = "cancelledRides", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@CssImport("./styles/views/list-view.css")
public class ListViewCancelledRides extends Div {

    Dialog dialog;
    private GridPro<OngoingRide> grid;
    private GridListDataView<OngoingRide> gridListDataView;
//    ListDataProvider<ListSeries>

    private Grid.Column<OngoingRide> clientColumn;
    private Grid.Column<OngoingRide> DriverColumn;
    private Grid.Column<OngoingRide> Pickup_AddressColumn;
    private Grid.Column<OngoingRide> Drop_AddressColumn;


    private Grid.Column<OngoingRide> Pickup_LatitudeColumn;
    private Grid.Column<OngoingRide> Pickup_LongitudeColumn;
    private Grid.Column<OngoingRide> Drop_LatitudeColumn;
    private Grid.Column<OngoingRide> Drop_LongitudeColumn;

    private Grid.Column<OngoingRide> RequestMadeOnColumn;
    public ListViewCancelledRides() {
        addClassName("list-view");
//        setSizeFull();

        createGrid();
        add(grid);
    }

    private void createGrid() {
        createGridComponent();
        addColumnsToGrid();
        addFiltersToGrid();
    }
    private static Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Add", e -> dialog.close());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }

    private void createGridComponent() {

        dialog = new Dialog();

        dialog.setHeaderTitle("New employee");

//        VerticalLayout dialogLayout = createDialogLayout();
//        dialog.add(dialogLayout);

        Button saveButton = createSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        grid = new GridPro<>();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_ROW_STRIPES);
//        grid.setHeight("100%");

        List<OngoingRide> clients = getClients();
        gridListDataView = grid.setItems(clients);
        VerticalLayout dialogLayout;
        grid.addItemClickListener(event ->

                        createDialogLayout(event)

                //Notification.show(event.getItem().getDrop_Address() + " items selected")
        );
//        grid.addSelectionListener(event -> {
//            Set<OngoingRide> selected = event.getAllSelectedItems();
//            Notification.show(selected.size() + " items selected");
//        });
    }

    private void createDialogLayout(ItemClickEvent<OngoingRide> OR) {

        Label Pickup_AddressField = new Label("Pickup Address");
        Label Drop_AddressField = new Label("Drop Address");
        Label PickUpLatField = new Label("Pickup Latitude");
        Label PickUpLngField = new Label("Pickup Longitude");
        Label DropLatField = new Label("Drop Latitude");
        Label DropLngField = new Label("Drop Longitude");
        Pickup_AddressField.setText("Pickup Address: "+OR.getItem().Pickup_Address);
        Drop_AddressField.setText("Drop Address: "+OR.getItem().Drop_Address);
        PickUpLatField.setText("Pickup Latitude: "+OR.getItem().Pickup_Latitude);
        PickUpLngField.setText("Pickup Longitude: "+OR.getItem().Pickup_Longitude);
        DropLatField.setText("Drop Latitude: "+OR.getItem().Drop_Latitude);
        DropLngField.setText("Drop Longitude: "+OR.getItem().Drop_Longitude);

        VerticalLayout dialogLayout = new VerticalLayout(Pickup_AddressField,
                Drop_AddressField,PickUpLatField,PickUpLngField,DropLatField,DropLngField);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");
        dialog.add(dialogLayout);
        dialog.open();


    }
    private void addColumnsToGrid() {
        createClientColumn();
        createDriverColumn();
        createPickupColumn();
        createDropColumn();
        //createPickup_LatitudeColumn();
        //createPickup_LongitudeColumn();
        //createDrop_LatitudeColumn();
        //createDrop_LongitudeColumn();
        createRequestMadeOnColumn();
    }

    private void createClientColumn() {
        clientColumn = grid.addColumn(new ComponentRenderer<>(client -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(FlexComponent.Alignment.CENTER);

            Span span = new Span();
            span.setClassName("name");
            span.setText(client.getUserName());
            hl.add(span);
            return hl;
        })).setComparator(client -> client.getUserName()).setHeader("Client");
    }
    private void createDriverColumn() {
        DriverColumn = grid.addColumn(new ComponentRenderer<>(client -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(FlexComponent.Alignment.CENTER);

            Span span = new Span();
            span.setClassName("name");
            span.setText(client.getDriverName());
            hl.add(span);
            return hl;
        })).setComparator(client -> client.getDriverName()).setHeader("Driver");
    }
    private String getFormattedPersonBirthday() {


        return "";
    }
    private void createPickupColumn() {



        Pickup_AddressColumn = grid.addColumn(new ComponentRenderer<>(client -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(FlexComponent.Alignment.CENTER);

            Span span = new Span();
            span.setClassName("name");
            span.getElement().setAttribute("alt",client.getPickup_Address());
            span.setText(client.getPickup_Address());
            hl.add(span);
            return hl;
        })).setComparator(client -> client.getPickup_Address()).setHeader("Pickup");
    }

    private void createDropColumn() {
        Drop_AddressColumn = grid.addColumn(new ComponentRenderer<>(client -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(FlexComponent.Alignment.CENTER);

            Span span = new Span();
            span.setClassName("name");
            span.setText(client.getDrop_Address());
            hl.add(span);
            return hl;
        })).setComparator(client -> client.getDrop_Address()).setHeader("Drop");
    }

    private void createPickup_LatitudeColumn() {

        Pickup_LatitudeColumn = grid.addColumn(new ComponentRenderer<>(client -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(FlexComponent.Alignment.CENTER);

            Span span = new Span();
            span.setClassName("name");
            span.setText(client.getPickup_Latitude());
            hl.add(span);
            return hl;
        })).setComparator(client -> client.getPickup_Latitude()).setHeader("Pickup_Latitude");
    }

    private void createPickup_LongitudeColumn() {
        Pickup_LongitudeColumn = grid.addColumn(new ComponentRenderer<>(client -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(FlexComponent.Alignment.CENTER);

            Span span = new Span();
            span.setClassName("name");
            span.setText(client.getPickup_Longitude());
            hl.add(span);
            return hl;
        })).setComparator(client -> client.getPickup_Longitude()).setHeader("Pickup_Longitude");
    }

    private void createDrop_LatitudeColumn() {
        Drop_LatitudeColumn = grid.addColumn(new ComponentRenderer<>(client -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(FlexComponent.Alignment.CENTER);

            Span span = new Span();
            span.setClassName("name");
            span.setText(client.getDrop_Latitude());
            hl.add(span);
            return hl;
        })).setComparator(client -> client.getDrop_Latitude()).setHeader("Drop_Latitude");
    }

    private void createDrop_LongitudeColumn() {
        Drop_LongitudeColumn = grid.addColumn(new ComponentRenderer<>(client -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(FlexComponent.Alignment.CENTER);

            Span span = new Span();
            span.setClassName("name");
            span.setText(client.getDrop_Longitude());
            hl.add(span);
            return hl;
        })).setComparator(client -> client.getDrop_Longitude()).setHeader("Drop_Longitude");
    }

    private void createRequestMadeOnColumn() {
        RequestMadeOnColumn = grid.addColumn(new ComponentRenderer<>(client -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(FlexComponent.Alignment.CENTER);

            Span span = new Span();
            span.setClassName("name");
            String str=client.getRequestMadeOn().toString().split("T")[0];
            span.setText(str);
            hl.add(span);
            return hl;
        })).setComparator(client -> client.getRequestMadeOn()).setHeader("RequestMadeOn");
    }



    private void addFiltersToGrid() {
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField clientFilter = new TextField();
        clientFilter.setPlaceholder("Filter");
        clientFilter.setClearButtonVisible(true);
        clientFilter.setWidth("100%");
        clientFilter.setValueChangeMode(ValueChangeMode.EAGER);
        clientFilter.addValueChangeListener(event -> gridListDataView
                .addFilter(client -> StringUtils.containsIgnoreCase(client.getUserName(), clientFilter.getValue())));
        filterRow.getCell(clientColumn).setComponent(clientFilter);

        TextField DriverFilter = new TextField();
        DriverFilter.setPlaceholder("Filter");
        DriverFilter.setClearButtonVisible(true);
        DriverFilter.setWidth("100%");
        DriverFilter.setValueChangeMode(ValueChangeMode.EAGER);
        DriverFilter.addValueChangeListener(event -> gridListDataView
                .addFilter(client -> StringUtils.containsIgnoreCase(client.getDriverName(), clientFilter.getValue())));
        filterRow.getCell(DriverColumn).setComponent(DriverFilter);

        TextField Pickup_AddressFilter = new TextField();
        Pickup_AddressFilter.setPlaceholder("Filter");
        Pickup_AddressFilter.setClearButtonVisible(true);
        Pickup_AddressFilter.setWidth("100%");
        Pickup_AddressFilter.setValueChangeMode(ValueChangeMode.EAGER);
        Pickup_AddressFilter.addValueChangeListener(event -> gridListDataView
                .addFilter(client -> StringUtils.containsIgnoreCase(client.getPickup_Address(), Pickup_AddressFilter.getValue())));
        filterRow.getCell(Pickup_AddressColumn).setComponent(Pickup_AddressFilter);

        TextField Drop_AddressFilter = new TextField();
        Drop_AddressFilter.setPlaceholder("Filter");
        Drop_AddressFilter.setClearButtonVisible(true);
        Drop_AddressFilter.setWidth("100%");
        Drop_AddressFilter.setValueChangeMode(ValueChangeMode.EAGER);
        Drop_AddressFilter.addValueChangeListener(event -> gridListDataView
                .addFilter(client -> StringUtils.containsIgnoreCase(client.getDrop_Address(), Drop_AddressFilter.getValue())));
        filterRow.getCell(Drop_AddressColumn).setComponent(Drop_AddressFilter);

//        TextField Pickup_LatitudeFilter = new TextField();
//        Pickup_LatitudeFilter.setPlaceholder("Filter");
//        Pickup_LatitudeFilter.setClearButtonVisible(true);
//        Pickup_LatitudeFilter.setWidth("100%");
//        Pickup_LatitudeFilter.setValueChangeMode(ValueChangeMode.EAGER);
//        Pickup_LatitudeFilter.addValueChangeListener(event -> gridListDataView
//                .addFilter(client -> StringUtils.containsIgnoreCase(client.getPickup_Latitude(), Pickup_LatitudeFilter.getValue())));
//        filterRow.getCell(Pickup_LatitudeColumn).setComponent(Pickup_LatitudeFilter);

//        TextField Pickup_LongitudeFilter = new TextField();
//        Pickup_LongitudeFilter.setPlaceholder("Filter");
//        Pickup_LongitudeFilter.setClearButtonVisible(true);
//        Pickup_LongitudeFilter.setWidth("100%");
//        Pickup_LongitudeFilter.setValueChangeMode(ValueChangeMode.EAGER);
//        Pickup_LongitudeFilter.addValueChangeListener(event -> gridListDataView
//                .addFilter(client -> StringUtils.containsIgnoreCase(client.getPickup_Longitude(), Pickup_LongitudeFilter.getValue())));
//        filterRow.getCell(Pickup_LongitudeColumn).setComponent(Pickup_LongitudeFilter);
//
//
//
//        TextField Drop_LatitudeFilter = new TextField();
//        Drop_LatitudeFilter.setPlaceholder("Filter");
//        Drop_LatitudeFilter.setClearButtonVisible(true);
//        Drop_LatitudeFilter.setWidth("100%");
//        Drop_LatitudeFilter.setValueChangeMode(ValueChangeMode.EAGER);
//        Drop_LatitudeFilter.addValueChangeListener(event -> gridListDataView
//                .addFilter(client -> StringUtils.containsIgnoreCase(client.getDrop_Latitude(), Drop_LatitudeFilter.getValue())));
//        filterRow.getCell(Drop_LatitudeColumn).setComponent(Drop_LatitudeFilter);
//
//
//        TextField Drop_LongitudeFilter = new TextField();
//        Drop_LongitudeFilter.setPlaceholder("Filter");
//        Drop_LongitudeFilter.setClearButtonVisible(true);
//        Drop_LongitudeFilter.setWidth("100%");
//        Drop_LongitudeFilter.setValueChangeMode(ValueChangeMode.EAGER);
//        Drop_LongitudeFilter.addValueChangeListener(event -> gridListDataView
//                .addFilter(client -> StringUtils.containsIgnoreCase(client.getDrop_Longitude(), Drop_LongitudeFilter.getValue())));
//        filterRow.getCell(Drop_LongitudeColumn).setComponent(Drop_LongitudeFilter);

        TextField RequestMadeOnFilter = new TextField();
        RequestMadeOnFilter.setPlaceholder("Filter");
        RequestMadeOnFilter.setClearButtonVisible(true);
        RequestMadeOnFilter.setWidth("100%");
        RequestMadeOnFilter.setValueChangeMode(ValueChangeMode.EAGER);


        RequestMadeOnFilter.addValueChangeListener(event -> gridListDataView
                .addFilter(client -> StringUtils.containsIgnoreCase(client.getRequestMadeOn().toString().split("T")[0], RequestMadeOnFilter.getValue())));
        filterRow.getCell(RequestMadeOnColumn).setComponent(RequestMadeOnFilter);

    }

    private boolean areStatusesEqual(Client client, ComboBox<String> statusFilter) {
        String statusFilterValue = statusFilter.getValue();
        if (statusFilterValue != null) {
            return StringUtils.equals(client.getStatus(), statusFilterValue);
        }
        return true;
    }

    private boolean areDatesEqual(Client client, DatePicker dateFilter) {
        LocalDate dateFilterValue = dateFilter.getValue();
        if (dateFilterValue != null) {
            LocalDate clientDate = LocalDate.parse(client.getDate());
            return dateFilterValue.equals(clientDate);
        }
        return true;
    }

    private List<OngoingRide> getClients() {
        RideDetails usr=getRideDetails("4");
        List<OngoingRide> ogr=new ArrayList<>();
        for (RideData i : usr.data) {
            String hh=i.user_name;
            ogr.add(createOngoingRide(i.user_name.toString(),i.driver_name.toString(),i.pickup_location_address.toString(),i.drop_location_address.toString(),Double.toString(i.pickup_latitude),Double.toString(i.pickup_longitude),Double.toString(i.drop_latitude),Double.toString(i.drop_longitude),i.request_made_on )); // code block to be executed
        }
        return ogr;
    }

    private static RideDetails getRideDetails(String StatusID)
    {
        int Cnt=0;
        final String uri = "https://api-live.woyo.co:8020/get_ride_details?city_id=559&status="+StatusID+"&start_date=2022-10-19 20:00:00&token=317a0052444962684779e1424e738c9bff9dd938b1e41bd6e95f4a53f589f500\n";

        RestTemplate restTemplate = new RestTemplate();
        RideDetails usr= restTemplate.getForObject(uri, RideDetails.class);

        return usr;


    }
    private OngoingRide createOngoingRide(String UserName, String DriverName, String Pickup_Address, String Drop_Address, String Pickup_Latitude, String Pickup_Longitude, String Drop_Latitude, String Drop_Longitude,String RequestMadeOn) {
        OngoingRide c = new OngoingRide();
        c.setUserName(UserName);
        c.setDriverName(DriverName);
        c.setPickup_Address(Pickup_Address);
        c.setDrop_Address(Drop_Address);
        c.setPickup_Latitude(Pickup_Latitude);
        c.setPickup_Longitude(Pickup_Longitude);

        c.setDrop_Latitude(Drop_Latitude);
        c.setDrop_Longitude(Drop_Longitude);
        c.setRequestMadeOn(RequestMadeOn);


        return c;
    }
}

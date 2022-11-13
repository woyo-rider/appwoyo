package com.springbootvaadin.application.views.checkoutform;

import com.springbootvaadin.application.backend.Ride;
import com.springbootvaadin.application.data.Person;
import com.springbootvaadin.application.views.MainLayout;
import com.springbootvaadin.application.views.woyo.Client;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@PageTitle("User Management")
@CssImport("./styles/views/list-view.css")
@Route(value = "userManagement", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CheckoutFormView extends Div {
    private GridPro<Ride> grid;
    private GridListDataView<Ride> gridListDataView;
//    ListDataProvider<ListSeries>

    private Grid.Column<Ride> rideId;
    private Grid.Column<Ride> rideDriver;
    private Grid.Column<Ride> clientColumn;
    private Grid.Column<Ride> amountColumn;
    private Grid.Column<Ride> statusColumn;
    private Grid.Column<Ride> dateColumn;

    public CheckoutFormView() {
        addClassName("list-view");
//        setSizeFull();

        createGrid();
        add(grid);
    }

    private void createGrid() {
        createGridComponent();
        addColumnsToGrid();
//        addFiltersToGrid();
    }

    private void createGridComponent() {
        grid = new GridPro<>();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_ROW_STRIPES);
//        grid.setHeight("100%"); grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        List<Ride> rides = getRides();
        gridListDataView = grid.setItems(rides);
    }

    private void addColumnsToGrid() {
        createRideIdColumn();
        createClientColumn();
        createDriverColumn();
//        createAmountColumn(); // not needed here--- commented down
        createStatusColumn();
        createDateColumn();
    }

    private void createRideIdColumn() {
        rideId = grid.addColumn(new ComponentRenderer<>(ride -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(FlexComponent.Alignment.CENTER);
            Span span = new Span();
            span.setText(String.valueOf(ride.getId()));
            hl.add(span);
            return hl;
        })).setComparator(ride -> ride.getStatus()).setHeader("RideId");

    }
    private void createClientColumn() {
        clientColumn = grid.addColumn(new ComponentRenderer<>(ride -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(FlexComponent.Alignment.CENTER);
            Image img = new Image(ride.getClient().getImg(), "");
            Span span = new Span();
            span.setClassName("name");
            span.setText(ride.getClient().getClient());
            hl.add(img, span);
            return hl;
        })).setComparator(ride -> ride.getClient().getClient()).setHeader("Client");
    }
    private void createDriverColumn() {
        rideDriver = grid.addColumn(new ComponentRenderer<>(ride -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(FlexComponent.Alignment.CENTER);
            Span span = new Span();
            span.setText(ride.getDriver().getFirstName());
            hl.add(span);
            return hl;
        })).setComparator(ride -> ride.getStatus()).setHeader("Driver");

    }

//    private void createAmountColumn() {
//        amountColumn = grid
//                .addEditColumn(Ride::getAmount,
//                        new NumberRenderer<>(client -> client.getAmount(), NumberFormat.getCurrencyInstance(Locale.US)))
//                .text((item, newValue) -> item.setAmount(Double.parseDouble(newValue)))
//                .setComparator(client -> client.getAmount()).setHeader("Amount");
//    }

    private void createStatusColumn() {
        statusColumn =
//                grid.addEditColumn(Ride::getStatus, new ComponentRenderer<>(ride -> {
//            Span span = new Span();
//            span.setText(String.valueOf(ride.getStatus()));
//            span.getElement().setAttribute("theme", "badge " + ride.getStatus());
//            return span;
//        }))
//                .select((item, newValue) -> item.setStatus(newValue), Arrays.asList("Pending", "Success", "Error"))
//                .setComparator(ride -> ride.getStatus()).setHeader("Status");

                grid.addColumn(new ComponentRenderer<>(ride -> {
                    HorizontalLayout hl = new HorizontalLayout();
                    hl.setAlignItems(FlexComponent.Alignment.CENTER);
                    Span span = new Span();
                    span.setText(String.valueOf(ride.getStatus()));
                    hl.add(span);
                    return hl;
                })).setComparator(ride -> ride.getStatus()).setHeader("Status");

    }

    private void createDateColumn() {
        dateColumn = grid
                .addColumn(new LocalDateRenderer<>(client -> (client.getDate()),
                        DateTimeFormatter.ofPattern("M/d/yyyy")))
                .setComparator(client -> client.getDate()).setHeader("Date").setWidth("180px").setFlexGrow(0);
    }

    private void addFiltersToGrid() {
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField clientFilter = new TextField();
        clientFilter.setPlaceholder("Filter");
        clientFilter.setClearButtonVisible(true);
        clientFilter.setWidth("100%");
        clientFilter.setValueChangeMode(ValueChangeMode.EAGER);
        clientFilter.addValueChangeListener(event -> gridListDataView
                .addFilter(client -> StringUtils.containsIgnoreCase(client.getClient().getClient(), clientFilter.getValue())));
        filterRow.getCell(clientColumn).setComponent(clientFilter);

//        TextField amountFilter = new TextField();
//        amountFilter.setPlaceholder("Filter");
//        amountFilter.setClearButtonVisible(true);
//        amountFilter.setWidth("100%");
//        amountFilter.setValueChangeMode(ValueChangeMode.EAGER);
//        amountFilter.addValueChangeListener(event -> gridListDataView.addFilter(client -> StringUtils
//                .containsIgnoreCase(Double.toString(client.getAmount()), amountFilter.getValue())));
//        filterRow.getCell(amountColumn).setComponent(amountFilter);

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setItems(Arrays.asList(Ride.Status.ONGOING.getName(), Ride.Status.COMPLETED.getName(), Ride.Status.CANCELLED.getName(), Ride.Status.AUTO_CANCELLED.getName()));
        statusFilter.setPlaceholder("Filter");
        statusFilter.setClearButtonVisible(true);
        statusFilter.setWidth("100%");
        statusFilter.addValueChangeListener(
                event -> gridListDataView.addFilter(client -> areStatusesEqual(client, statusFilter)));
        filterRow.getCell(statusColumn).setComponent(statusFilter);

        DatePicker dateFilter = new DatePicker();
        dateFilter.setPlaceholder("Filter");
        dateFilter.setClearButtonVisible(true);
        dateFilter.setWidth("100%");
        dateFilter.addValueChangeListener(
                event -> gridListDataView.addFilter(client -> areDatesEqual(client, dateFilter)));
        filterRow.getCell(dateColumn).setComponent(dateFilter);
    }

    private boolean areStatusesEqual(Ride client, ComboBox<String> statusFilter) {
        String statusFilterValue = statusFilter.getValue();
        if (statusFilterValue != null) {
            return StringUtils.equals(client.getStatus().getName(), statusFilterValue);
        }
        return true;
    }

    private boolean areDatesEqual(Ride client, DatePicker dateFilter) {
        LocalDate dateFilterValue = dateFilter.getValue();
        if (dateFilterValue != null) {
            LocalDate clientDate = (client.getDate());
            return dateFilterValue.equals(clientDate);
        }
        return true;
    }

    private List<Ride> getRides() {
        return Arrays.asList(
                createRide(123, Ride.Status.ONGOING,createClient(4957, "https://randomuser.me/api/portraits/women/42.jpg", "Amarachi Nkechi", 47427.0,
                        "Success", "2019-05-09"),createDriver(21, "driver21"),LocalDate.of(2019,05,9)),
                createRide(125, Ride.Status.COMPLETED,createClient(675, "https://randomuser.me/api/portraits/women/24.jpg", "Bonelwa Ngqawana", 70503.0,
                        "Success", "2019-05-09"),createDriver(23, "driver23"),LocalDate.of(2019,05,9)),
                createRide(132, Ride.Status.COMPLETED,createClient(6816, "https://randomuser.me/api/portraits/men/42.jpg", "Debashis Bhuiyan", 58931.0,
                        "Success", "2019-05-07"),createDriver(22, "driver22"),LocalDate.of(2019,05,7)),
                createRide(112, Ride.Status.ONGOING,createClient(5144, "https://randomuser.me/api/portraits/women/76.jpg", "Jacqueline Asong", 25053.0,
                        "Pending", "2019-04-25"),createDriver(13, "driver13"),LocalDate.of(2019,04,25)),
                createRide(114, Ride.Status.CANCELLED,createClient(9800, "https://randomuser.me/api/portraits/men/24.jpg", "Kobus van de Vegte", 7319.0,
                        "Pending", "2019-04-22"),createDriver(14, "driver14"),LocalDate.of(2019,04,22)),
                createRide(108, Ride.Status.AUTO_CANCELLED,createClient(3599, "https://randomuser.me/api/portraits/women/94.jpg", "Mattie Blooman", 18441.0,
                        "Error", "2019-04-17"),createDriver(18, "driver18"),LocalDate.of(2019,04,17)),
                createRide(131, Ride.Status.ONGOING,createClient(3989, "https://randomuser.me/api/portraits/men/76.jpg", "Oea Romana", 33376.0, "Pending",
                        "2019-04-17"),createDriver(20, "driver20"),LocalDate.of(2019,04,17)),
                createRide(121, Ride.Status.ONGOING,createClient(1077, "https://randomuser.me/api/portraits/men/94.jpg", "Stephanus Huggins", 75774.0,
                        "Success", "2019-02-26"),createDriver(10, "driver10"),LocalDate.of(2019,02,26)),
                createRide(126, Ride.Status.COMPLETED,createClient(8942, "https://randomuser.me/api/portraits/men/16.jpg", "Torsten Paulsson", 82531.0,
                        "Pending", "2019-02-21"),createDriver(16, "driver16"),LocalDate.of(2019,02,21))
        );
    }

    private Ride createRide(int id, Ride.Status status, Client client,
                            Person driver, LocalDate date) {

        Ride c = new Ride(id, status, client, driver, date);
        return c;
    }
    private Client createClient(int id, String img, String client, double amount, String status, String date) {
        Client c = new Client();
        c.setId(id);
        c.setImg(img);
        c.setClient(client);
        c.setAmount(amount);
        c.setStatus(status);
        c.setDate(date);

        return c;
    }

    private Person createDriver(int id, String fName){
        Person driver = new Person();
        driver.setId(id);
        driver.setFirstName(fName);

        return driver;
    }
}

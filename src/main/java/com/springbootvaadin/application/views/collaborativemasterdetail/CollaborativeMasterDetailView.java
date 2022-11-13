package com.springbootvaadin.application.views.collaborativemasterdetail;

import com.springbootvaadin.application.backend.Payment;
import com.springbootvaadin.application.components.DataSeriesItemWithRadius;
import com.springbootvaadin.application.components.FlexBoxLayout;
import com.springbootvaadin.application.data.DataService;
import com.springbootvaadin.application.data.Person;
import com.springbootvaadin.application.data.entity.SamplePerson;
import com.springbootvaadin.application.data.service.SamplePersonService;
import com.springbootvaadin.application.layout.size.Bottom;
import com.springbootvaadin.application.layout.size.Top;
import com.springbootvaadin.application.layout.size.*;
import com.springbootvaadin.application.util.*;
import com.springbootvaadin.application.util.css.BorderRadius;
import com.springbootvaadin.application.util.css.BoxSizing;
import com.springbootvaadin.application.util.css.Display;
import com.springbootvaadin.application.util.css.Shadow;
import com.springbootvaadin.application.views.MainLayout;
import com.springbootvaadin.application.views.ViewFrame;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Random;

@PageTitle("Statistics")
@Route(value = "Stats/:samplePersonID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class CollaborativeMasterDetailView extends ViewFrame {// } implements BeforeEnterObserver {

    private Grid<SamplePerson> grid = new Grid<>(SamplePerson.class, false);



    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");



    private final SamplePersonService samplePersonService;

    private static final String MAX_WIDTH = "2048px";
    private static final String CLASS_NAME = "dashboard";

    @Autowired
    public CollaborativeMasterDetailView(SamplePersonService samplePersonService) {
        this.samplePersonService = samplePersonService;
//        addClassNames("collaborative-master-detail-view");
        addClassName("Stats");
        setViewContent(createContent());

    }
    private Component createContent() {
        Component driverRegistrations = createDriverRegistration();
        Component transactions = createCarRides();
//        Component docs = createDocs();
//        Component carsData = createSubContent1();
        Component carsData2 = createSubContent2();

        FlexBoxLayout content = new FlexBoxLayout( transactions, carsData2);//, docs);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        content.setPadding(Uniform.M);
        return content;
    }
    private Component createSubContent1(){
        Component carRides = createCarRides2();
        Component carsAreaWise = createCarlocations();

        Row subContent = new Row(carRides, carsAreaWise);
        subContent.addClassName(LumoStyles.Margin.Top.XL);
        UIUtils.setMaxWidth(MAX_WIDTH,subContent);
        subContent.setWidthFull();

        return subContent;
    }
    private Component createSubContent2(){
        Component planets = createPlantes3D();
        Component grid = createGrid();

        Row subContent = new Row(planets); // , grid);
        subContent.addClassName(LumoStyles.Margin.Top.XL);
        UIUtils.setMaxWidth(MAX_WIDTH,subContent);
        subContent.setWidthFull();

        return subContent;
    }

    private FlexBoxLayout createHeader(VaadinIcon icon, String title){
        FlexBoxLayout header = new FlexBoxLayout(
                UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, icon),
                UIUtils.createH3Label(title));
        header.setAlignItems(FlexComponent.Alignment.AUTO);
        header.setMargin(Bottom.L, Horizontal.RESPONSIVE_L);
        header.setSpacing(Right.L);
        return header;
    }

    private Component createCarRides(){
        FlexBoxLayout rides = new FlexBoxLayout(
                createHeader(VaadinIcon.MONEY, " Revenue"), //(VaadinIcon.CAR, " Rides"),
                createAreaChart());
        rides.setBoxSizing(BoxSizing.BORDER_BOX);
        rides.setDisplay(Display.BLOCK);
        rides.setMargin(Top.XL);
        rides.setMaxWidth(MAX_WIDTH);
        rides.setPadding(Horizontal.RESPONSIVE_L);
        rides.setWidthFull();
        return rides;
    }
    private Component createPlantes3D(){
        FlexBoxLayout planets = new FlexBoxLayout(
                createHeader(VaadinIcon.MONEY, " Transactions"),
                create3Dchart());

        planets.addClassName(CLASS_NAME + "__planets");
        planets.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        planets.setPadding(Uniform.M, Left.RESPONSIVE_L); //planets.setPadding(Bottom.XL, Left.RESPONSIVE_L);

        return planets;
    }
    private Component createGrid(){
        GridContent gridContent = new GridContent();

        Div card = new Div(gridContent);
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
        UIUtils.setBorderRadius(BorderRadius.S, card);
        UIUtils.setShadow(Shadow.XS, card);

        FlexBoxLayout gridTable = new FlexBoxLayout(
                createHeader(VaadinIcon.GLOBE_WIRE, " UserDetails"), card
        );

        gridTable.addClassName(CLASS_NAME + "__grid");
        gridTable.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        gridTable.setPadding(Uniform.M, Right.RESPONSIVE_L); //gridTable.setPadding(Bottom.XL, Right.RESPONSIVE_L);
        return gridTable;
    }

    private Component createAreaChart() {

        // Create a bullet chart
        Chart chart = new Chart(ChartType.BULLET);
        chart.setHeight("115px");

// Modify the default configuration
        Configuration conf = chart.getConfiguration();
        conf.setTitle("2022 YTD");
        conf.getChart().setInverted(true);
        conf.getLegend().setEnabled(false);
        conf.getTooltip().setPointFormat(
                "<b>{point.y}</b> (with target at {point.target})");

// Add data
        PlotOptionsBullet options = new PlotOptionsBullet();
        options.setPointPadding(0.25);
        options.setBorderWidth(0);
        options.setColor(SolidColor.BLACK);
        options.getTargetOptions().setWidth("200%");
        DataSeries series = new DataSeries();
        series.add(new DataSeriesItemBullet(275, 250));
        series.setPlotOptions(options);
        conf.addSeries(series);

// Configure the axes
        YAxis yAxis = conf.getyAxis();
        yAxis.setGridLineWidth(0);
        yAxis.setTitle("");
        yAxis.addPlotBand(new PlotBand(0, 150, new SolidColor("#666666")));
        yAxis.addPlotBand(new PlotBand(150, 225, new SolidColor("#999999")));
        yAxis.addPlotBand(new PlotBand(225, 9e9, new SolidColor("#bbbbbb")));
        conf.getxAxis().addCategory(
                "<span style=\"font-size: 13px; font-weight: bold;\">Revenue</span><br/>U.S. $ (1,000s)");

        /*Chart chart1 = new Chart(ChartType.AREASPLINE);
Chart chart = new Chart(ChartType.AREASPLINE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("2022");
        conf.getLegend().setEnabled(false);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("Number of Rides");

        conf.addSeries(new ListSeries(220, 240, 400, 360, 420, 640, 580, 800,
                600, 580, 740, 800));
        */

        FlexBoxLayout card = new FlexBoxLayout(chart); // (chart)
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("175px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
//        return
    }
    private Component createCarRides2(){
        FlexBoxLayout rides = new FlexBoxLayout(
                createHeader(VaadinIcon.CAR, " RidesDaily"),
                createBarChart());
        rides.setBoxSizing(BoxSizing.BORDER_BOX);
        rides.setDisplay(Display.BLOCK);
        rides.setMargin(Top.XL);
        rides.setMaxWidth(MAX_WIDTH);
        rides.setPadding(Uniform.M); //rides.setPadding(Horizontal.RESPONSIVE_L);
        rides.setWidthFull();
        return rides;
    }
    private Component createCarlocations(){
        FlexBoxLayout spots = new FlexBoxLayout(
                createHeader(VaadinIcon.GLOBE, " Rides Range"),
                createPolarChart()); // createPieChart());
        spots.setBoxSizing(BoxSizing.BORDER_BOX);
        spots.setDisplay(Display.BLOCK);
        spots.setMargin(Top.XL);
        spots.setMaxWidth(MAX_WIDTH);
        spots.setPadding(Uniform.M); //spots.setPadding(Horizontal.RESPONSIVE_L);
        spots.setWidthFull();
        return spots;
    }
    private Component createBarChart() { // it is COLUMN type of chart
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("July");
//        conf.getLegend().setEnabled(false);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        conf.addxAxis(xAxis);

        //conf.getyAxis().setTitle("Number of Rides & Hours");

        conf.addSeries(new ListSeries("Number of Rides",15, 12, 16, 14, 8, 10, 13));

        conf.addSeries(new ListSeries("Max Duration  (Hr)", gRndmInt(), gRndmInt(), gRndmInt(), gRndmInt(), gRndmInt(), gRndmInt(), gRndmInt()));
//        conf.addSeries(new ListSeries("Max Duration (Hr)", 2,5,3,4,1,6,3));

        conf.getLabels();

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
//        card.setWidth("300px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }
    public int gRndmInt() { // fullName as => getRandomNumberUsingNextInt(int min, int max)
        int min=1,  max=10;
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private Component createPieChart(){
        Chart chart = new Chart(ChartType.PIE);
        Configuration conf = chart.getConfiguration();

        PlotOptionsPie options = new PlotOptionsPie();
        options.setInnerSize("0");
        options.setSize("75%");  // Default
        options.setCenter("50%", "50%"); // Default
        conf.setPlotOptions(options);

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Area1", 4900));
        series.add(new DataSeriesItem("Area2", 12100));

        conf.addSeries(series);

        // Slice one sector out
        DataSeriesItem earth = new DataSeriesItem("Area3", 12800);
        earth.setSliced(true);
        series.add(earth);

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
//        card.setWidth("300px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }
    private Component create3Dchart(){

        Chart chart = new Chart(ChartType.SPLINE);

        Configuration configuration = chart.getConfiguration();
        configuration.getTitle().setText("Transactions");
        configuration.getxAxis().setType(AxisType.DATETIME);

// A data series to annotate with flags
        DataSeries dataSeries = new DataSeries();
        dataSeries.setId("dataseries");
        dataSeries.addData(new Number[][] { { 1434499200000l, 0.8821 },
                { 1434585600000l, 0.8802 }, { 1434672000000l, 0.8808 },
                { 1434844800000l, 0.8794 }, { 1434931200000l, 0.8818 },
                { 1435017600000l, 0.8952 }, { 1435104000000l, 0.8924 },
                { 1435190400000l, 0.8925 }, { 1435276800000l, 0.8955 } });

// Flags on the data series
        DataSeries flagsOnSeries = new DataSeries();
        flagsOnSeries.setName("Flags on series");
        PlotOptionsFlags plotOptionsFlags = new PlotOptionsFlags();
        plotOptionsFlags.setShape(FlagShape.SQUAREPIN);
        plotOptionsFlags.setOnSeries("dataseries");
        flagsOnSeries.setPlotOptions(plotOptionsFlags);
        flagsOnSeries.add(new FlagItem(1434585600000l, "First Series Flag",
                "First Series Flag Tooltip Text"));
        flagsOnSeries.add(new FlagItem(1435017600000l, "Second Series Flag"));

// Flags on the X axis
        DataSeries flagsOnAxis = new DataSeries();
        flagsOnAxis.setPlotOptions(new PlotOptionsFlags());
        flagsOnAxis.setName("Flags on axis");
        flagsOnAxis.add(new FlagItem(1434844800000l, "First Axis Flag",
                "First Axis Flag Tooltip Text"));
        flagsOnAxis.add(new FlagItem(1435190400000l, "Second Axis Flag"));

        configuration.setSeries(dataSeries, flagsOnSeries, flagsOnAxis);


       /*  Chart chart = new Chart(ChartType.PIE);
        Chart chart1 = new Chart(ChartType.PIE);
       Configuration conf = chart.getConfiguration();
//... other chart configuration ...

// In 3D!
        Options3d options3d = new Options3d();
        options3d.setEnabled(true);
        options3d.setAlpha(45);//10
        options3d.setBeta(01);//30
        options3d.setDepth(135); // Default is 100
        options3d.setViewDistance(100); // Default
        conf.getChart().setOptions3d(options3d);

        // Set some plot options
        PlotOptionsPie options = new PlotOptionsPie();
//... Other plot options for the chart ...
        options.setInnerSize("0");
        options.setSize("75%");  // Default
        options.setCenter("50%", "50%"); // Default

        options.setDepth(45); // Our pie is quite thick

        conf.setPlotOptions(options);

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Monday", 4900));
        series.add(new DataSeriesItem("Tuesday", 12100));
        series.add(new DataSeriesItem("Wednesday", 12800));
        series.add(new DataSeriesItem("Thursday", 6800));
        series.add(new DataSeriesItem("Friday", 143000));
        series.add(new DataSeriesItem("Saturday", 120500));
        series.add(new DataSeriesItem("Sunday", 51000));
//        series.add(new DataSeriesItem("Neptune", 49300));

        conf.addSeries(series);
        */

//        FlexBoxLayout card = new FlexBoxLayout(chart);
//        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
//        card.setBorderRadius(BorderRadius.S);
//        card.setBoxSizing(BoxSizing.BORDER_BOX);
//        card.setHeight("300px");
//        card.setWidth("300px");
//        card.setPadding(Uniform.M);
//        card.setShadow(Shadow.XS);

        Div card = new Div(chart);
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
        UIUtils.setBorderRadius(BorderRadius.S, card);
        UIUtils.setShadow(Shadow.XS, card);
        return card;
    }
    private Component createDriverRegistration() {
        FlexBoxLayout driverRegistrations = new FlexBoxLayout(
                createHeader(VaadinIcon.MALE, " Drivers"),
                createDriverRegistrationsCharts());
        driverRegistrations.setBoxSizing(BoxSizing.BORDER_BOX);
        driverRegistrations.setDisplay(Display.BLOCK);
        driverRegistrations.setMargin(Top.L);
        driverRegistrations.setMaxWidth(MAX_WIDTH);
        driverRegistrations.setPadding(Horizontal.RESPONSIVE_L);
        driverRegistrations.setWidthFull();
        return driverRegistrations;
    }
    private Component createDriverRegistrationsCharts() {
        Row charts = new Row();
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, charts);
        UIUtils.setBorderRadius(BorderRadius.S, charts);
        UIUtils.setShadow(Shadow.XS, charts);

        for (Payment.Status status : Payment.Status.values()) {
            charts.add(createPaymentChart(status));
        }

        return charts;
    }

    private Component createPolarChart(){
        // Create a chart of some type
        Chart chart = new Chart(ChartType.LINE);

        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.getChart().setPolar(true);

        // Create the range series
        // Source: http://ilmatieteenlaitos.fi/lampotilaennatyksia
        ListSeries series = new ListSeries("Max Rides",
                10.9, 11.8, 17.5, 25.5, 31.0, 33.8,
                37.2, 33.8, 28.8, 19.4, 14.1, 10.8);
        conf.addSeries(series);


        // Set the category labels on the X axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setCategories("Jan", "Feb", "Mar",
                "Apr", "May", "Jun", "Jul", "Aug", "Sep",
                "Oct", "Nov", "Dec");
        xaxis.setTickmarkPlacement(TickmarkPlacement.ON);
        xaxis.setLineWidth(0);
        conf.addxAxis(xaxis);

        // Configure the Y axis
        YAxis yaxis = new YAxis();
        yaxis.setGridLineInterpolation("polygon"); // Webby look
        yaxis.setMin(0);
        yaxis.setTickInterval(10);
        yaxis.getLabels().setStep(1);
        conf.addyAxis(yaxis);

        // Define the sector of the polar projection
        Pane pane = new Pane(0, 360); // Full circle
        conf.addPane(pane);

// Define the X axis and set its value range
        XAxis axis = new XAxis();
        axis.setMin(0);
        axis.setMax(360);

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
//        card.setWidth("300px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }
    private Component createPaymentChart(Payment.Status status) {
        int value;
        String color;

        switch (status) {
            case PENDING:
                value = 24;
                color = "#EC9800";
                break;

            case SUBMITTED:
                value = 40;
                color = "#00FF00";
                break;

            case CONFIRMED:
                value = 32;
                color = "#000000";
                break;

            default:
                value = 4;
                color = "#ff0000";
                break;
        }

        FlexBoxLayout textContainer = new FlexBoxLayout(
                UIUtils.createH2Label(Integer.toString(value)),
                UIUtils.createLabel(FontSize.S, "%"));
        textContainer.setAlignItems(FlexComponent.Alignment.BASELINE);
        textContainer.setPosition(com.springbootvaadin.application.util.css.Position.ABSOLUTE);
        textContainer.setSpacing(Right.XS);

        Chart chart = createProgressChart(status, value, color);

        FlexBoxLayout chartContainer = new FlexBoxLayout(chart, textContainer);
        chartContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        chartContainer
                .setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        chartContainer.setPosition(com.springbootvaadin.application.util.css.Position.RELATIVE);
        chartContainer.setHeight("120px");
        chartContainer.setWidth("120px");

        FlexBoxLayout paymentChart = new FlexBoxLayout(
                new Label(status.getName()), chartContainer);
        paymentChart.addClassName("dashboard" + "__analytics-chart");//(CLASS_NAME + "__payment-chart");
        paymentChart.setAlignItems(FlexComponent.Alignment.CENTER);
        paymentChart.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        paymentChart.setPadding(Bottom.S, Top.M);
        return paymentChart;
//        return new FlexBoxLayout();
    }

    private Chart createProgressChart(Payment.Status status, int value, String color) {
        Chart chart = new Chart();
        Chart chart1 = new Chart();
        chart.addClassName(status.getName().toLowerCase());
        chart.setSizeFull();

        Configuration configuration = chart.getConfiguration();
        configuration.getChart().setType(ChartType.SOLIDGAUGE);
        configuration.setTitle("");
        configuration.getTooltip().setEnabled(false);

        configuration.getyAxis().setMin(0);
        configuration.getyAxis().setMax(100);
        configuration.getyAxis().getLabels().setEnabled(false);

        PlotOptionsSolidgauge opt = new PlotOptionsSolidgauge();
        opt.getDataLabels().setEnabled(false);
        configuration.setPlotOptions(opt);

        DataSeriesItemWithRadius point = new DataSeriesItemWithRadius();
        point.setY(value);
        point.setInnerRadius("100%");
        point.setRadius("110%");
        point.setColor(new SolidColor(color));
        configuration.setSeries(new DataSeries(point));

        Pane pane = configuration.getPane();
        pane.setStartAngle(0);
        pane.setEndAngle(360);

        Background background = new Background();
        background.setShape(BackgroundShape.ARC);
        background.setInnerRadius("100%");
        background.setOuterRadius("110%");

        pane.setBackground(background);

//        background.setBackgroundColor(new SolidColor("#EC9800"));

        return chart1;
    }



    @Route("grid-content")
    public static class GridContent extends Div {

        public GridContent() {
            // tag::snippet1[]
            Grid<Person> grid = new Grid<>(Person.class, false);
            grid.setSelectionMode(Grid.SelectionMode.MULTI);
            grid.addColumn(createEmployeeRenderer()).setHeader("User")
                    .setAutoWidth(true).setFlexGrow(0);
//            grid.addColumn(Person::getProfession).setHeader("Profession").setAutoWidth(true);
            grid.addColumn(createStatusComponentRenderer()).setHeader("Status")
                    .setAutoWidth(true);
            // end::snippet1[]

            List<Person> people = DataService.getPeople();
            grid.setItems(people);

            add(grid);

        }

        private static Renderer<Person> createEmployeeRenderer() {
            return LitRenderer.<Person>of(
                            "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                    + "<vaadin-avatar img=\"${item.pictureUrl}\" name=\"${item.fullName}\" alt=\"User avatar\"></vaadin-avatar>"
                                    + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                    + "    <span> ${item.fullName} </span>"
                                    + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                    + "      ${item.email}" + "    </span>"
                                    + "  </vaadin-vertical-layout>"
                                    + "</vaadin-horizontal-layout>")
                    .withProperty("pictureUrl", Person::getPictureUrl)
                    .withProperty("fullName", Person::getFullName)
                    .withProperty("email", Person::getEmail);
        }

        private static final SerializableBiConsumer<Span, Person> statusComponentUpdater = (span, person) -> {
            boolean isAvailable = "Available".equals(person.getStatus());
            String theme = String
                    .format("badge %s", isAvailable ? "success" : "error");
            span.getElement().setAttribute("theme", theme);
            span.setText(person.getStatus());
        };

        private static ComponentRenderer<Span, Person> createStatusComponentRenderer() {
            return new ComponentRenderer<>(Span::new, statusComponentUpdater);
        }

    }

    // UserInfo is used by Collaboration Engine and is used to share details
        // of users to each other to able collaboration. Replace this with
        // information about the actual user that is logged, providing a user
        // identifier, and the user's real name. You can also provide the users
        // avatar by passing an url to the image as a third parameter, or by
        // configuring an `ImageProvider` to `avatarGroup`.
//        UserInfo userInfo = new UserInfo(UUID.randomUUID().toString(), "Steve Lange");
//
//        // Create UI
//        SplitLayout splitLayout = new SplitLayout();
//
//        avatarGroup = new CollaborationAvatarGroup(userInfo, null);
//        avatarGroup.getStyle().set("visibility", "hidden");
//
//        createGridLayout(splitLayout);
//        createEditorLayout(splitLayout);
//
//        add(splitLayout);
//
//        // Configure Grid
//        grid.addColumn("firstName").setAutoWidth(true);
//        grid.addColumn("lastName").setAutoWidth(true);
//        grid.addColumn("email").setAutoWidth(true);
//        grid.addColumn("phone").setAutoWidth(true);
//        grid.addColumn("dateOfBirth").setAutoWidth(true);
//        grid.addColumn("occupation").setAutoWidth(true);
//        LitRenderer<SamplePerson> importantRenderer = LitRenderer.<SamplePerson>of(
//                "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
//                .withProperty("icon", important -> important.isImportant() ? "check" : "minus").withProperty("color",
//                        important -> important.isImportant()
//                                ? "var(--lumo-primary-text-color)"
//                                : "var(--lumo-disabled-text-color)");
//
//        grid.addColumn(importantRenderer).setHeader("Important").setAutoWidth(true);
//
//        grid.setItems(query -> samplePersonService.list(
//                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
//                .stream());
//        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
//
//        // when a row is selected or deselected, populate form
//        grid.asSingleSelect().addValueChangeListener(event -> {
//            if (event.getValue() != null) {
//                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
//            } else {
//                clearForm();
//                UI.getCurrent().navigate(CollaborativeMasterDetailView.class);
//            }
//        });
//
//        // Configure Form
//        binder = new CollaborationBinder<>(SamplePerson.class, userInfo);
//
//        // Bind fields. This is where you'd define e.g. validation rules
//
//        binder.bindInstanceFields(this);
//
//        cancel.addClickListener(e -> {
//            clearForm();
//            refreshGrid();
//        });
//
//        save.addClickListener(e -> {
//            try {
//                if (this.samplePerson == null) {
//                    this.samplePerson = new SamplePerson();
//                }
//                binder.writeBean(this.samplePerson);
//
//                samplePersonService.update(this.samplePerson);
//                clearForm();
//                refreshGrid();
//                Notification.show("SamplePerson details stored.");
//                UI.getCurrent().navigate(CollaborativeMasterDetailView.class);
//            } catch (ValidationException validationException) {
//                Notification.show("An exception happened while trying to store the samplePerson details.");
//            }
//        });
//    }
//
//    @Override
//    public void beforeEnter(BeforeEnterEvent event) {
//        Optional<UUID> samplePersonId = event.getRouteParameters().get(SAMPLEPERSON_ID).map(UUID::fromString);
//        if (samplePersonId.isPresent()) {
//            Optional<SamplePerson> samplePersonFromBackend = samplePersonService.get(samplePersonId.get());
//            if (samplePersonFromBackend.isPresent()) {
//                populateForm(samplePersonFromBackend.get());
//            } else {
//                Notification.show(
//                        String.format("The requested samplePerson was not found, ID = %d", samplePersonId.get()), 3000,
//                        Notification.Position.BOTTOM_START);
//                // when a row is selected but the data is no longer available,
//                // refresh grid
//                refreshGrid();
//                event.forwardTo(CollaborativeMasterDetailView.class);
//            }
//        }
//    }
//
//    private void createEditorLayout(SplitLayout splitLayout) {
//        Div editorLayoutDiv = new Div();
//        editorLayoutDiv.setClassName("editor-layout");
//
//        Div editorDiv = new Div();
//        editorDiv.setClassName("editor");
//        editorLayoutDiv.add(editorDiv);
//
//        FormLayout formLayout = new FormLayout();
//        firstName = new TextField("First Name");
//        lastName = new TextField("Last Name");
//        email = new TextField("Email");
//        phone = new TextField("Phone");
//        dateOfBirth = new DatePicker("Date Of Birth");
//        occupation = new TextField("Occupation");
//        important = new Checkbox("Important");
//        Component[] fields = new Component[]{firstName, lastName, email, phone, dateOfBirth, occupation, important};
//
//        formLayout.add(fields);
//        editorDiv.add(avatarGroup, formLayout);
//        createButtonLayout(editorLayoutDiv);
//
//        splitLayout.addToSecondary(editorLayoutDiv);
//    }
//
//    private void createButtonLayout(Div editorLayoutDiv) {
//        HorizontalLayout buttonLayout = new HorizontalLayout();
//        buttonLayout.setClassName("button-layout");
//        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        buttonLayout.add(save, cancel);
//        editorLayoutDiv.add(buttonLayout);
//    }
//
//    private void createGridLayout(SplitLayout splitLayout) {
//        Div wrapper = new Div();
//        wrapper.setClassName("grid-wrapper");
//        splitLayout.addToPrimary(wrapper);
//        wrapper.add(grid);
//    }
//
//    private void refreshGrid() {
//        grid.select(null);
//        grid.getLazyDataView().refreshAll();
//    }
//
//    private void clearForm() {
//        populateForm(null);
//    }
//
//    private void populateForm(SamplePerson value) {
//        this.samplePerson = value;
//        String topic = null;
//        if (this.samplePerson != null && this.samplePerson.getId() != null) {
//            topic = "samplePerson/" + this.samplePerson.getId();
//            avatarGroup.getStyle().set("visibility", "visible");
//        } else {
//            avatarGroup.getStyle().set("visibility", "hidden");
//        }
//        binder.setTopic(topic, () -> this.samplePerson);
//        avatarGroup.setTopic(topic);
//
//    }
}
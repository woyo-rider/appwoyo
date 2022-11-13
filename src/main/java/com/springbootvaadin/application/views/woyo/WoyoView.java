package com.springbootvaadin.application.views.woyo;

import com.springbootvaadin.application.backend.Payment;
import com.springbootvaadin.application.components.DataSeriesItemWithRadius;
import com.springbootvaadin.application.components.FlexBoxLayout;
import com.springbootvaadin.application.data.*;
import com.springbootvaadin.application.layout.size.Bottom;
import com.springbootvaadin.application.layout.size.Top;
import com.springbootvaadin.application.layout.size.*;
import com.springbootvaadin.application.util.*;
import com.springbootvaadin.application.util.css.BoxSizing;
import com.springbootvaadin.application.util.css.Display;
import com.springbootvaadin.application.util.css.Shadow;
import com.springbootvaadin.application.views.MainLayout;
import com.springbootvaadin.application.views.ViewFrame;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.PermitAll;
import java.time.Month;
import java.util.List;
import java.util.Random;


@CssImport("./styles/views/statistics.css")
@PageTitle("Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@CssImport("./styles/lumo/margin.css")
//@AnonymousAllowed
@PermitAll

public class WoyoView extends ViewFrame {

    private static final String MAX_WIDTH = "2048px";
    private static final String CLASS_NAME = "dashboard";
    private TextField name;
    private Button sayHello;
    Div content0 = new Div();

    Tab taxiStatistics = new Tab("Taxi Statistics");
    Tab creditStatistics = new Tab("Credit Statistics");
    Month selectedMonth= Month.JANUARY ;


//    private GridPro<Client> grid;
//    private GridListDataView<Client> gridListDataView;
////    ListDataProvider<ListSeries>
//
//    private Grid.Column<Client> clientColumn;
//    private Grid.Column<Client> amountColumn;
//    private Grid.Column<Client> statusColumn;
//    private Grid.Column<Client> dateColumn;
    public WoyoView() {


        addClassName("basic-board");

        Tabs tabs = new Tabs(taxiStatistics, creditStatistics);
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
//        add(tabs);

        tabs.addSelectedChangeListener(event ->
                setContent(event.getSelectedTab())
        );

//        content0.setSpacing();
//        setContent(tabs.getSelectedTab());

//        content0.add(AboutView_v1());
        content0.add(createContent());
        setViewContent(content0);

    }

    private void setContent(Tab tab) {
        content0.removeAll();

        if (tab.equals(taxiStatistics)) {
            content0.add(createContent());
//            content0.add(new ListView());
        } else if (tab.equals(creditStatistics)) {
            content0.add(new CreditWoyoView().createContent());
        }
//        else {
//            content0.add(createShippingAddressSection());//createPaymentInformationSection());
//        }
    }

    private Component createContent() {

        Component driverRegistrations = createDriverRegistration();
        Component transactions = createCarRides();
//        Component docs = createDocs();
//        Component carsData = createSubContent1();
//        Component carsData2 = createSubContent2();
        Div appcontent = new Div();
        appcontent.addClassNames("app-content content");
        Div contentoverlay = new Div();
        appcontent.add(contentoverlay);
        contentoverlay.addClassNames("content-overlay");
        Div contentwrapper = new Div();
        contentwrapper.addClassNames("content-wrapper");
        appcontent.add(contentwrapper);
        Div content = new Div(driverRegistrations
                , transactions
//                , carsData
//                , carsData2
        ); //, docs);
        content.addClassNames("content-body");
        contentwrapper.add(content);
//        content.setBackgroundColor(String.valueOf(new SolidColor("00F28D")));
        return appcontent;
    }
    private Component createSubContent1(){
        Component carRides = createCarRides2();
//        Component carsAreaWise = createCarlocations();
        Component grid = createGrid();

        Row subContent = new Row(carRides, grid);
        subContent.addClassName(LumoStyles.Margin.Top.XL);
        UIUtils.setMaxWidth(MAX_WIDTH,subContent);
        subContent.setWidthFull();

        return subContent;
    }
    private Component createSubContent2(){
        Component planets = createPlantes3D();
//        Component grid = createGrid();
        Component carsAreaWise = createCarlocations();

        Row subContent = new Row(planets, carsAreaWise);
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

    private Component createDriverRegistration() {

        Icon icn = new Icon(VaadinIcon.TAXI);
//      icn = UIUtils.createIcon(IconSize.M, TextColor.SECONDARY, VaadinIcon.MALE);

        H3 header = new H3("Woyo Rider"); //"Statistics"
        header.getElement().setAttribute("style","margin-left:30px");
        header.addClassNames(LumoUtility.Display.INLINE);
        Hr hr1=new Hr();

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(//icn,
                header,hr1);
        layout.addClassName(LumoUtility.Display.INLINE);

        FlexBoxLayout driverRegistrations = new FlexBoxLayout(
                layout,
                createRow()
//                AboutView_v1()
        );

        driverRegistrations.setBoxSizing(BoxSizing.BORDER_BOX);
//        driverRegistrations.addClassName("rounded_border");
        driverRegistrations.setDisplay(Display.BLOCK);
        driverRegistrations.setMargin(Top.L);
        driverRegistrations.setMaxWidth(MAX_WIDTH);
        driverRegistrations.setPadding(Uniform.M);
        driverRegistrations.setWidthFull();
        driverRegistrations.setAlignItems(FlexComponent.Alignment.CENTER);


        Component carRides01 = driverRegistrations;

        Row subContent = new Row(carRides01);
        subContent.addClassName(LumoStyles.Margin.Top.XL);
        UIUtils.setMaxWidth(MAX_WIDTH,subContent);
        subContent.setWidthFull();


        return carRides01;
    }
    private Component createCarRides(){

        Icon icn = new Icon(VaadinIcon.CAR);
//        icn = UIUtils.createIcon(IconSize.M, TextColor.SECONDARY, VaadinIcon.MALE);
        H3 header = new H3("Woyo Pay");  //Total Rides
        header.addClassNames(LumoUtility.Display.INLINE);
        header.getElement().setAttribute("style","margin-left:30px");

        Hr hr1=new Hr();

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(header,hr1);
        layout.addClassName(LumoUtility.Display.INLINE);

        ListView listView =new ListView();

//        Div div =new Div(listView);
//        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, div);
////        UIUtils.setBorderRadius(BorderRadius.M, card);
//        div.addClassName("rounded_border");
//        UIUtils.setShadow(Shadow.XS, div);
//        div.setHeight("auto");

        FlexBoxLayout driverRegistrations = new FlexBoxLayout(
                layout,
                createRow2()
//                AboutView_v1()
        );

        driverRegistrations.setBoxSizing(BoxSizing.BORDER_BOX);
//        driverRegistrations.addClassName("rounded_border");
        driverRegistrations.setDisplay(Display.BLOCK);
        driverRegistrations.setMargin(Top.L);
        driverRegistrations.setMaxWidth(MAX_WIDTH);
        driverRegistrations.setPadding(Uniform.M);
        driverRegistrations.setWidthFull();
        driverRegistrations.setAlignItems(FlexComponent.Alignment.CENTER);


        Component carRides0 = driverRegistrations;

        Row subContent = new Row(carRides0);
        subContent.addClassName(LumoStyles.Margin.Top.XL);
        UIUtils.setMaxWidth(MAX_WIDTH,subContent);
        subContent.setWidthFull();

        return carRides0;
    }
    private Component createPlantes3D(){
        Icon icn = new Icon(VaadinIcon.MONEY);
//        icn = UIUtils.createIcon(IconSize.M, TextColor.SECONDARY, VaadinIcon.MALE);
        H3 header = new H3("Transactions");
        header.addClassNames(LumoUtility.Display.INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(icn, header);
        layout.addClassName(LumoUtility.Display.INLINE);


        FlexBoxLayout planets = new FlexBoxLayout(
//                createHeader(VaadinIcon.MONEY, " Transactions"),
                layout,
                create3Dchart());

        planets.setBoxSizing(BoxSizing.BORDER_BOX);
        planets.setDisplay(Display.BLOCK);
//        planets.setMargin(Top.XL);
        planets.setMaxWidth(MAX_WIDTH);
        planets.setPadding(Uniform.M); //rides.setPadding(Horizontal.RESPONSIVE_L);
        planets.setWidthFull();


        return planets;
    }
    private Component createGrid(){

        Icon icn = new Icon(VaadinIcon.GLOBE_WIRE);
//        icn = UIUtils.createIcon(IconSize.M, TextColor.SECONDARY, VaadinIcon.MALE);
        H3 header = new H3("Cancelled Rides");
        header.addClassNames(LumoUtility.Display.INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(icn, header);
        layout.addClassName(LumoUtility.Display.INLINE);

        GridContent gridContent = new GridContent();

        Div card = new Div(gridContent);
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
//        UIUtils.setBorderRadius(BorderRadius.M, card);
        card.addClassName("rounded_border");
        UIUtils.setShadow(Shadow.XS, card);

        FlexBoxLayout gridTable = new FlexBoxLayout(
//          createHeader(VaadinIcon.GLOBE_WIRE, " UserDetails"),
            layout,
            card
        );

//        gridTable.addClassName(CLASS_NAME + "__grid");
//        gridTable.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
//        gridTable.addClassName("rounded_border");

        gridTable.setBoxSizing(BoxSizing.BORDER_BOX);
        gridTable.setDisplay(Display.BLOCK);
//        gridTable.setMargin(Top.XL);
        gridTable.setMaxWidth(MAX_WIDTH);
        gridTable.setPadding(Uniform.M); //rides.setPadding(Horizontal.RESPONSIVE_L);
        gridTable.setWidthFull();

        return gridTable;
    }

    private Component createAreaChart() {
//        AtomicInteger month = new AtomicInteger();
//           Month.of(month.get()+0);
//
//        Binder<Month> binder = new Binder<>(Month.class);
//        binder.setBean(selectedMonth);
//
//        Chart chart = new Chart(ChartType.AREASPLINE);
//        DatePicker datePicker = new DatePicker("Start Year");
//        datePicker.addValueChangeListener(event -> {
////                    month.set(event.getValue().getMonthValue()),
////                      selectedMonth.plus(event.getValue().getMonthValue()
////                                    );
//                }
//        );

//        binder.forField(datePicker).bind(String.valueOf(Month.of(month.get()+1)));

//        selectedMonth= Month.of(month.get()+1);
//        selectedMonth.
//                Month.of(month.get());
//        System.out.println(
//                "=============== month = "+ month.get()
//                        + "======== selectd = "+selectedMonth
//        );
//        +datePicker.getValue().getMonthValue());


//        Configuration conf = chart.getConfiguration();
//        conf.setTitle(
//                selectedMonth+
//                        " - Monthwise");
//        conf.getLegend().setEnabled(false);
//
//        XAxis xAxis = new XAxis();
//        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
//                "Aug", "Sep", "Oct", "Nov", "Dec");
//        conf.addxAxis(xAxis);
//
//        conf.getyAxis().setTitle("Number of Rides");
//
//        conf.addSeries(new ListSeries(
//                gRndmInt(200,800),gRndmInt(200,800),gRndmInt(200,800),gRndmInt(200,800),gRndmInt(200,800),gRndmInt(200,800),
//                gRndmInt(200,800),gRndmInt(200,800),gRndmInt(200,800),gRndmInt(200,800),gRndmInt(200,800),gRndmInt(200,800)
////                220, 240, 400, 360, 420, 640, 580, 800,600, 580, 740, 800
//        ));


//        createGridComponent();
//        addColumnsToGrid();
//        addFiltersToGrid();
//        div.add(grid);

//        ListView listView = new ListView();
//        div.add(listView);

        FlexBoxLayout card = new FlexBoxLayout();
//        card.add(div);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
//        card.setBorderRadius(BorderRadius.S);
        card.addClassName("rounded_border");
        card.setBoxSizing(BoxSizing.BORDER_BOX);
//        card.setHeight("400px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

    private Component createStackedColumn(){

        Icon icn = new Icon(VaadinIcon.CAR);
//        icn = UIUtils.createIcon(IconSize.M, TextColor.SECONDARY, VaadinIcon.MALE);
        H3 header = new H3("RidesDaily");
        header.addClassNames(LumoUtility.Display.INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(icn, header);
        layout.addClassName(LumoUtility.Display.INLINE);


        FlexBoxLayout rides = new FlexBoxLayout(
//                createHeader(VaadinIcon.CAR, " RidesDaily"),
                layout,
                createBarChart());
        rides.setBoxSizing(BoxSizing.BORDER_BOX);
        rides.setDisplay(Display.BLOCK);
//        rides.setMargin(Top.XL);
        rides.setMaxWidth(MAX_WIDTH);
        rides.setPadding(Uniform.M); //rides.setPadding(Horizontal.RESPONSIVE_L);
        rides.setWidthFull();
        return rides;
    }
    private Component createCarRides2(){

        Icon icn = new Icon(VaadinIcon.CAR);
//        icn = UIUtils.createIcon(IconSize.M, TextColor.SECONDARY, VaadinIcon.MALE);
        H3 header = new H3("RidesDaily");
        header.addClassNames(LumoUtility.Display.INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(icn, header);
        layout.addClassName(LumoUtility.Display.INLINE);



        FlexBoxLayout rides = new FlexBoxLayout(
//                createHeader(VaadinIcon.CAR, " RidesDaily"),
                layout,
                createBarChart());
        rides.setBoxSizing(BoxSizing.BORDER_BOX);
        rides.setDisplay(Display.BLOCK);
//        rides.setMargin(Top.XL);
        rides.setMaxWidth(MAX_WIDTH);
        rides.setPadding(Uniform.M); //rides.setPadding(Horizontal.RESPONSIVE_L);
        rides.setWidthFull();
        return rides;
    }
    private Component createCarlocations(){
        Icon icn = new Icon(VaadinIcon.GLOBE);
//        icn = UIUtils.createIcon(IconSize.M, TextColor.SECONDARY, VaadinIcon.MALE);
        H3 header = new H3("Rides Range");
        header.addClassNames(LumoUtility.Display.INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(icn, header);
        layout.addClassName(LumoUtility.Display.INLINE);


        FlexBoxLayout spots = new FlexBoxLayout(
//                createHeader(VaadinIcon.GLOBE, " Rides Range"),
                layout,
                createPolarChart()); // createPieChart());
        spots.setBoxSizing(BoxSizing.BORDER_BOX);
        spots.setDisplay(Display.BLOCK);
//        spots.setMargin(Top.XL);
        spots.setMaxWidth(MAX_WIDTH);
        spots.setPadding(Uniform.M); //spots.setPadding(Horizontal.RESPONSIVE_L);
        spots.setWidthFull();
        return spots;
    }

    private Component createStackedColumnChart() { // it is COLUMN type of chart
        Div dvHeader =new Div();
        Div dvbdy =new Div();
        dvHeader.addClassNames("card-header card-header-transparent py-20");
        dvbdy.addClassNames("widget-content tab-content bg-white p-20");
        dvHeader.add(createHeader("Monthly Rides Statistics",""));
        Chart chart = new Chart(ChartType.COLUMN);
        Configuration conf = chart.getConfiguration();
        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Wed", "Mar", "Apr");
        conf.addxAxis(xAxis);
        conf.getyAxis().setTitle("");
        conf.addSeries(new ListSeries("Completed",15, 12, 16, 14, 8));

        conf.addSeries(new ListSeries("Cancelled", gRndmInt(1,10), gRndmInt(1,10), gRndmInt(1,10), gRndmInt(1,10), gRndmInt(1,10)));
        conf.addSeries(new ListSeries("Auto Cancelled", 2,5,3,4,1));

//        conf.getLabels();

        PlotOptionsColumn plotOptionsColumn = new PlotOptionsColumn();
        plotOptionsColumn.setStacking(Stacking.NORMAL);
        conf.setPlotOptions(plotOptionsColumn);


//        PlotOptionsColumn plotOptionsColumn = conf.getPlotOptions();



        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
//        card.setBorderRadius(BorderRadius.S);
        card.addClassName("rounded_border");
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
//        card.setWidth("300px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        dvbdy.add(chart);
        // Add it all together
        Div viewStacked = new Div(dvHeader, dvbdy);

        viewStacked.addClassNames("card", "card-shadow");

        return viewStacked;
    }
    private Component createStackedColumnChart2() { // it is COLUMN type of chart

        Div dvHeader =new Div();
        Div dvbdy =new Div();

        //widget-content tab-content bg-white p-20
        dvHeader.addClassNames("card-header card-header-transparent py-20");
        dvbdy.addClassNames("widget-content tab-content bg-white p-20");
        //HorizontalLayout header = createHeader("Total Rides", "");
        dvHeader.add(createHeader("Monthly Credit Statistics", ""));


        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
//        conf.setTitle("August-Weekwise");
//        conf.getLegend().setEnabled(false);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Wed", "Mar", "Apr");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("");

        conf.addSeries(new ListSeries("Ongoing Rides", gRndmInt(1,10), gRndmInt(1,10), gRndmInt(1,10), gRndmInt(1,10), gRndmInt(1,10)));
        conf.addSeries(new ListSeries("Completed Rides",15, 12, 16, 14, 8));
        conf.addSeries(new ListSeries("Unpaid Cancelled", 2,5,3,4,1));

        conf.getLabels();

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
//        card.setBorderRadius(BorderRadius.S);
        card.addClassName("rounded_border");
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
//        card.setWidth("300px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        dvbdy.add(chart);
        // Add it all together
        Div viewStacked = new Div(dvHeader, dvbdy);
        return viewStacked;
    }
    private Component createBarChart() { // it is COLUMN type of chart
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("August-Weekwise");
//        conf.getLegend().setEnabled(false);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        conf.addxAxis(xAxis);

        //conf.getyAxis().setTitle("Number of Rides & Hours");

        conf.addSeries(new ListSeries("Completed Rides",15, 12, 16, 14, 8, 10, 13));

        conf.addSeries(new ListSeries("Ongoing Rides", gRndmInt(1,10), gRndmInt(1,10), gRndmInt(1,10), gRndmInt(1,10), gRndmInt(1,10), gRndmInt(1,10), gRndmInt(1,10)));
//        conf.addSeries(new ListSeries("Max Duration (Hr)", 2,5,3,4,1,6,3));

        conf.getLabels();

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
//        card.setBorderRadius(BorderRadius.S);
        card.addClassName("rounded_border");
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
//        card.setWidth("300px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }
    public int gRndmInt(int min, int max ) { // fullName as => getRandomNumberUsingNextInt(int min, int max)
//        int min=1,  max=10;
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
//        card.setBorderRadius(BorderRadius.S);
        card.addClassName("rounded_border");
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
//        card.setWidth("300px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }
    private Component create3Dchart(){
        Chart chart = new Chart(ChartType.PIE);
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

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
//        card.setBorderRadius(BorderRadius.S);
        card.addClassName("rounded_border");
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
//        card.setWidth("300px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);

//        Div card = new Div(chart);
//        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
//        UIUtils.setBorderRadius(BorderRadius.S, card);
//        UIUtils.setShadow(Shadow.XS, card);



        return card;
    }

    private static int getDriverDetails()
    {
        final String uri = "https://api-live.woyo.co:8020/get_driver_details_panel?city=559&vehicle_type=0&category=0&limit=50000&token=317a0052444962684779e1424e738c9bff9dd938b1e41bd6e95f4a53f589f500";

        RestTemplate restTemplate = new RestTemplate();
        DriverDetails usr= restTemplate.getForObject(uri, DriverDetails.class);
        return usr.count;


    }
    private static int getUserDetails()
    {
        final String uri = "https://api-live.woyo.co:8020/customer_details?token=317a0052444962684779e1424e738c9bff9dd938b1e41bd6e95f4a53f589f500&start_date=2022-11-01 16:59:02&end_date=2022-11-03 16:59:02&sEcho=1&iColumns=9&sColumns=,,,,,,,,&iDisplayStart=0&iDisplayLength=50&mDataProp_0=&sSearch_0=&bRegex_0=false&bSearchable_0=true&bSortable_0=false&mDataProp_1=user_name&sSearch_1=&bRegex_1=false&bSearchable_1=true&bSortable_1=false&mDataProp_2=&sSearch_2=&bRegex_2=false&bSearchable_2=true&bSortable_2=false&mDataProp_3=user_email&sSearch_3=&bRegex_3=false&bSearchable_3=true&bSortable_3=false&mDataProp_4=&sSearch_4=\t&bRegex_4= false&bSearchable_4=true&bSortable_4=true&mDataProp_5=&sSearch_5=&bRegex_5=false&bSearchable_5=true&bSortable_5=false&mDataProp_6=total_rides_as_user&sSearch_6=&bRegex_6=false&bSearchable_6=true&bSortable_6=true&mDataProp_7=&sSearch_7=&bRegex_7=false&bSearchable_7=true&bSortable_7=false&mDataProp_8=&sSearch_8=&bRegex_8=false&bSearchable_8=true&bSortable_8=false&sSearch=&bRegex=false&iSortCol_0=4&sSortDir_0=desc&iSortingCols=1";

        RestTemplate restTemplate = new RestTemplate();
        Userdtl usr= restTemplate.getForObject(uri, Userdtl.class);
        return usr.iTotalRecords;


    }
    private static int getRideDetails(String StatusID)
    {
        int Cnt=0;
        final String uri = "https://api-live.woyo.co:8020/get_ride_details?city_id=559&status="+StatusID+"&start_date=2022-10-19 20:00:00&token=317a0052444962684779e1424e738c9bff9dd938b1e41bd6e95f4a53f589f500\n";

        RestTemplate restTemplate = new RestTemplate();
        RideDetails usr= restTemplate.getForObject(uri, RideDetails.class);
        if(usr.data!=null)
        {
            if(usr.data.stream().count() > 0)
            {
                long ff=usr.data.stream().count();
                Cnt = (int)ff;
            }
        }
        return Cnt;


    }


    private Component createRow(){

       String DriverDtailsCount= Integer.toString(getDriverDetails());
        String UserDetailsCount= Integer.toString(getUserDetails());
        int OngoingCnt= getRideDetails("1");
        int CompletedCnt= getRideDetails("2");
        int CancelledCnt= getRideDetails("4");
        int TotalCount=OngoingCnt+CompletedCnt+CancelledCnt;
        String RideCount= Integer.toString(TotalCount);

        Div rw = new Div();
        rw.addClassNames("row match-height");
        Div rw1 = new Div();
        rw1.addClassNames("row match-height");




        Component card11=createHighlight2("Total Drivers","Drivers today", 15.0, DriverDtailsCount.toString());
        Component card12=createHighlight2("Total Users","Users today", 25.0, UserDetailsCount.toString());
        Component card13=createHighlight2("Total Revenue", "Revenue today", 25.0, "10");
        Component card22=createHighlight2("Total Rides","Rides today", 25.0, RideCount.toString());

        Div flexCard1 = new Div(card13);

        flexCard1.addClassNames("col-xl-3", "col-lg-6", "col-12","pt-3");

        Div flexCard2 = new Div(card22);

        flexCard2.addClassNames("col-xl-3", "col-lg-6", "col-12","pt-3");

        Div flexCard3 = new Div(card11);

        flexCard3.addClassNames("col-xl-3", "col-lg-6", "col-12","pt-3");

        Div flexCard4 = new Div(card12);

        flexCard4.addClassNames("col-xl-3", "col-lg-6", "col-12","pt-3");



        rw.add(flexCard1);
        rw.add(flexCard2);
        rw.add(flexCard3);
        rw.add(flexCard4);


        Div flexChart1 = new Div(createResponseTimes());
        flexChart1.addClassNames("col-xl-4", "col-lg-6", "col-12");

        Div flexChart2 = new Div(createStackedColumnChart());
        flexChart2.addClassNames("col-xl-4", "col-lg-6", "col-12");

        Div flexChart3 = new Div(createViewEvents());
        flexChart3.addClassNames("col-xl-4", "col-lg-6", "col-12");

        rw1.add(flexChart1);
        rw1.add(flexChart2);
        rw1.add(flexChart3);

        Board board = new Board();

        board.addRow(rw);
        board.addRow(rw1);





        return board;
    }
    private Component createRow2(){
        Div rw = new Div();
        rw.addClassNames("row match-height");
        Div rw1 = new Div();
        rw1.addClassNames("row match-height");


//      link.add(vaadinIcon);

        Component card13=createHighlight2("Total Revenue", "Revenue today", 15.0, "10M");
        Component card22=createHighlight2("Total Credit","Creditsx today", 15.0, "25K");
        Component card11=createHighlight2("Total Drivers","Drivers today", 15.0, "5K");
        Component card12=createHighlight2("Total Users","Users today", 25.0, "500K");

        Div flexCard1 = new Div(card13);

        flexCard1.addClassNames("col-xl-3", "col-lg-6", "col-12","pt-3");
        Div flexCard2 = new Div(card22);
        flexCard2.addClassNames("col-xl-3", "col-lg-6", "col-12","pt-3");

        Div flexCard3 = new Div(card11);
        flexCard3.addClassNames("col-xl-3", "col-lg-6", "col-12","pt-3");

        Div flexCard4 = new Div(card12);
        flexCard4.addClassNames("col-xl-3", "col-lg-6", "col-12","pt-3");


        rw.add(flexCard3);
        rw.add(flexCard4);
        rw.add(flexCard1);
        rw.add(flexCard2);

        Board board = new Board();



        Div flexChart1 = new Div(createResponseTimes2());
        flexChart1.addClassNames("col-xl-4", "col-lg-6", "col-12");

        Div flexChart2 = new Div(createStackedColumnChart2());
        flexChart2.addClassNames("col-xl-4", "col-lg-6", "col-12");

        Div flexChart3 = new Div(createViewEvents2());
        flexChart3.addClassNames("col-xl-4", "col-lg-6", "col-12");

//        board.addRow(createResponseTimes(), createStackedColumnChart(), createViewEvents());
        rw1.add(flexChart1);
        rw1.add(flexChart2);
        rw1.add(flexChart3);
        board.addRow(rw);
        board.addRow(rw1);
        return board;
    }
    private Component createHighlight(Icon tileIcon, String title, String value, Double percentage) {


        Div dvCardContent=new Div();
        dvCardContent.addClassNames("card-content");
        Div dvcardbody=new Div();
        dvcardbody.addClassNames("card-body");
        Div dvmediadflex=new Div();
        dvmediadflex.addClassNames("media d-flex");
        Div dvmediadbody=new Div();
        dvmediadbody.addClassNames("media-body text-left");
        H3 info = new H3(value);
        info.addClassName("info");
        H6 Products = new H6(title);
        dvmediadbody.getElement().appendChild(info.getElement());
        dvmediadbody.getElement().appendChild(Products.getElement());
        Div dvIconbody=new Div();
        Div dvIconCont=new Div();
        dvIconCont.addClassNames("icon-basket-loaded info font-large-2 float-right");
        dvIconbody.getElement().appendChild(dvIconCont.getElement());
        dvmediadflex.getElement().appendChild(dvmediadbody.getElement());
        dvmediadflex.getElement().appendChild(dvIconbody.getElement());
        Div dvprogress=new Div();
        dvprogress.addClassNames("progress progress-sm mt-1 mb-0 box-shadow-2");
        Div dvprogressbar=new Div();
        dvprogressbar.addClassNames("progress-bar bg-gradient-x-info");
        dvprogressbar.getElement().setAttribute("role","progress-bar bg-gradient-x-info");
        dvprogressbar.getElement().setAttribute("style","width: 80%");
        dvprogressbar.getElement().setAttribute("aria-valuenow","80");
        dvprogressbar.getElement().setAttribute("aria-valuemin","0");
        dvprogressbar.getElement().setAttribute("aria-valuemax","100");
        dvprogress.getElement().appendChild(dvprogressbar.getElement());

        dvcardbody.getElement().appendChild(dvmediadflex.getElement());
        dvcardbody.getElement().appendChild(dvprogress.getElement());
        dvCardContent.getElement().appendChild(dvcardbody.getElement());
        Div layout = new Div();
        layout.addClassNames("card", "pull-up");
        layout.getElement().setAttribute("style","border-radius:0.45rem");
        layout.getElement().appendChild(dvCardContent.getElement());

        return layout;
    }
    private Component createHighlight1(String title, String subtitle, String currentValue, String value) {




        Div dvCardContent=new Div();
        dvCardContent.addClassNames("card-content");
        Div dvcardbody=new Div();
        dvcardbody.addClassNames("card-body");
        Div dvmediadflex=new Div();
        dvmediadflex.addClassNames("media d-flex");
        Div dvmediadbody=new Div();
        dvmediadbody.addClassNames("media-body text-left");
        H6 info = new H6(subtitle);
        info.addClassName("info");
        H6 Products = new H6(title);
        dvmediadbody.getElement().appendChild(info.getElement());
        dvmediadbody.getElement().appendChild(Products.getElement());
        Div dvIconbody=new Div();
        Div dvIconCont=new Div();
        dvIconCont.addClassNames("icon-basket-loaded info font-large-2 float-right");
        dvIconbody.getElement().appendChild(dvIconCont.getElement());
        dvmediadflex.getElement().appendChild(dvmediadbody.getElement());
        dvmediadflex.getElement().appendChild(dvIconbody.getElement());
        Div dvprogress=new Div();
        dvprogress.addClassNames("progress progress-sm mt-1 mb-0 box-shadow-2");
        Div dvprogressbar=new Div();
        dvprogressbar.addClassNames("progress-bar bg-gradient-x-info");
        dvprogressbar.getElement().setAttribute("role","progress-bar bg-gradient-x-info");
        dvprogressbar.getElement().setAttribute("style","width: 80%");
        dvprogressbar.getElement().setAttribute("aria-valuenow","80");
        dvprogressbar.getElement().setAttribute("aria-valuemin","0");
        dvprogressbar.getElement().setAttribute("aria-valuemax","100");
        dvprogress.getElement().appendChild(dvprogressbar.getElement());

        dvcardbody.getElement().appendChild(dvmediadflex.getElement());
        dvcardbody.getElement().appendChild(dvprogress.getElement());
        dvCardContent.getElement().appendChild(dvcardbody.getElement());
        Div layout = new Div();
        layout.addClassNames("card", "pull-up");
        layout.getElement().setAttribute("style","border-radius:0.45rem");
        layout.getElement().appendChild(dvCardContent.getElement());

        return layout;
    }
    private Component createHighlight2(String title, String subtitle, Double percentage, String value) {

        Div dvCardContent=new Div();
        dvCardContent.addClassNames("card-content");
        Div dvcardbody=new Div();
        dvcardbody.addClassNames("card-body");
        H4 hdn = new H4(title);
        hdn.getStyle().clear();
        hdn.getElement().setAttribute("style","margin-bottom: 2.5rem;");

        dvcardbody.getElement().appendChild(hdn.getElement());
        Div dvmediadflex=new Div();
        dvmediadflex.addClassNames("media d-flex");
        Div dvmediadbody=new Div();
        dvmediadbody.addClassNames("media-body text-left");
        H3 info = new H3(value);
        info.addClassName("info");
        Paragraph Products = new Paragraph(subtitle);
        Products.getElement().setAttribute("style","margin-bottom: 0.5rem;");
        dvmediadbody.getElement().appendChild(info.getElement());
        dvmediadbody.getElement().appendChild(Products.getElement());
        Div dvIconbody=new Div();
        Div dvIconCont=new Div();
        //Span per =new Span(percentage.toString()+"%");
        //dvIconCont.getElement().appendChild(per.getElement());
        dvIconCont.addClassNames("icon-basket-loaded info font-large-2 float-right");
        //percentage.toString()+"%"
        dvIconbody.getElement().appendChild(dvIconCont.getElement());
        dvmediadflex.getElement().appendChild(dvmediadbody.getElement());
        dvmediadflex.getElement().appendChild(dvIconbody.getElement());
        Div dvprogress=new Div();
        dvprogress.addClassNames("progress progress-sm mt-1 mb-0 box-shadow-2");
        Div dvprogressbar=new Div();
        dvprogressbar.addClassNames("progress-bar bg-gradient-x-info");
        dvprogressbar.getElement().setAttribute("role","progress-bar bg-gradient-x-info");
        dvprogressbar.getElement().setAttribute("style","width: 80%");
        dvprogressbar.getElement().setAttribute("aria-valuenow","80");
        dvprogressbar.getElement().setAttribute("aria-valuemin","0");
        dvprogressbar.getElement().setAttribute("aria-valuemax","100");
        dvprogress.getElement().appendChild(dvprogressbar.getElement());

        dvcardbody.getElement().appendChild(dvmediadflex.getElement());
        dvcardbody.getElement().appendChild(dvprogress.getElement());
        dvCardContent.getElement().appendChild(dvcardbody.getElement());
        Div layout = new Div();
        layout.addClassNames("card", "pull-up");
        layout.getElement().setAttribute("style","border-radius:0.45rem");
        layout.getElement().appendChild(dvCardContent.getElement());

        return layout;
    }

    private Component createViewEvents() {
        Div dvHeader =new Div();
        Div dvbdy =new Div();

        //widget-content tab-content bg-white p-20
        dvHeader.addClassNames("card-header card-header-transparent py-20");
        dvbdy.addClassNames("widget-content tab-content bg-white p-20");
        //HorizontalLayout header = createHeader("Total Rides", "");

        // Header
        Select year = new Select();
        year.setItems("2020", "2021", "2022");
        year.setValue("2022");
        year.setWidth("100px");
Div hdr =new Div();
hdr.add(createHeader1("Revenue Evolution",""));
        hdr.getElement().setAttribute("style","width:70%;display:inline-block;");
        dvHeader.add( hdr);
        Div hdr1 =new Div();
        hdr1.getElement().setAttribute("style","width:30%;display:inline-block;");

        hdr1.add(year);
        dvHeader.add(hdr1);
        // Chart
        Chart chart = new Chart(ChartType.AREASPLINE);
        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("");

        PlotOptionsAreaspline plotOptions = new PlotOptionsAreaspline();
        plotOptions.setPointPlacement(PointPlacement.ON);
        plotOptions.setMarker(new Marker(false));
        conf.addPlotOptions(plotOptions);

        year.addValueChangeListener(event -> {
            chart.drawChart(true);
            conf.setSeries(
                    new ListSeries("Berlin", gRndmInt(190,280), gRndmInt(280,350), gRndmInt(350,430), gRndmInt(430,510), gRndmInt(510,590), gRndmInt(600,680), gRndmInt(680,760), gRndmInt(760,840), gRndmInt(840,920), gRndmInt(920,1000), gRndmInt(1000,1080), gRndmInt(1100,1250)),
            new ListSeries("London", gRndmInt(140,200), gRndmInt(200,260), gRndmInt(260,320), gRndmInt(320,380), gRndmInt(380,440), gRndmInt(440,500), gRndmInt(500,560), gRndmInt(560,620), gRndmInt(620,680), gRndmInt(680,740), gRndmInt(740,800), gRndmInt(800,900)),
            new ListSeries("New York", gRndmInt(65,110), gRndmInt(110,160), gRndmInt(160,210), gRndmInt(210,260), gRndmInt(260,310), gRndmInt(310,360), gRndmInt(360,410), gRndmInt(410,460), gRndmInt(460,510), gRndmInt(510,560), gRndmInt(560,610), gRndmInt(610,650)),
            new ListSeries("Tokyo", gRndmInt(0,40), gRndmInt(40,80), gRndmInt(80,120), gRndmInt(120,160), gRndmInt(160,200), gRndmInt(200,240), gRndmInt(240,280), gRndmInt(280,320), gRndmInt(320,360), gRndmInt(360,400), gRndmInt(400,440), gRndmInt(440,475))
            );

        });
        conf.addSeries(new ListSeries("Berlin", gRndmInt(190,280), gRndmInt(280,350), gRndmInt(350,430), gRndmInt(430,510), gRndmInt(510,590), gRndmInt(600,680), gRndmInt(680,760), gRndmInt(760,840), gRndmInt(840,920), gRndmInt(920,1000), gRndmInt(1000,1080), gRndmInt(1100,1250)));
        conf.addSeries(new ListSeries("London", gRndmInt(140,200), gRndmInt(200,260), gRndmInt(260,320), gRndmInt(320,380), gRndmInt(380,440), gRndmInt(440,500), gRndmInt(500,560), gRndmInt(560,620), gRndmInt(620,680), gRndmInt(680,740), gRndmInt(740,800), gRndmInt(800,900)));
        conf.addSeries(new ListSeries("New York", gRndmInt(65,110), gRndmInt(110,160), gRndmInt(160,210), gRndmInt(210,260), gRndmInt(260,310), gRndmInt(310,360), gRndmInt(360,410), gRndmInt(410,460), gRndmInt(460,510), gRndmInt(510,560), gRndmInt(560,610), gRndmInt(610,650)));
        conf.addSeries(new ListSeries("Tokyo", gRndmInt(0,40), gRndmInt(40,80), gRndmInt(80,120), gRndmInt(120,160), gRndmInt(160,200), gRndmInt(200,240), gRndmInt(240,280), gRndmInt(280,320), gRndmInt(320,360), gRndmInt(360,400), gRndmInt(400,440), gRndmInt(440,475)));
        dvbdy.add(chart);
        // Add it all together
        Div viewEvents = new Div(dvHeader, dvbdy);
        viewEvents.addClassNames("card", "card-shadow");
        return viewEvents;
    }
    private Component createViewEvents2() {
        Div dvHeader =new Div();
        Div dvbdy =new Div();

        //widget-content tab-content bg-white p-20
        dvHeader.addClassNames("card-header card-header-transparent py-20");
        dvbdy.addClassNames("widget-content tab-content bg-white p-20");
        //HorizontalLayout header = createHeader("Total Rides", "");

        // Header
        Select year = new Select();
        year.setItems("2020", "2021", "2022");
        year.setValue("2022");
        year.setWidth("100px");
        Div hdr =new Div();
        hdr.add(createHeader1("Revenue Evolution",""));
        hdr.getElement().setAttribute("style","width:70%;display:inline-block;");
        dvHeader.add( hdr);
        Div hdr1 =new Div();
        hdr1.getElement().setAttribute("style","width:30%;display:inline-block;");

        hdr1.add(year);
        dvHeader.add(hdr1);



        // Chart
        Chart chart = new Chart(ChartType.AREASPLINE);
        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("");

        PlotOptionsAreaspline plotOptions = new PlotOptionsAreaspline();
        plotOptions.setPointPlacement(PointPlacement.ON);
        plotOptions.setMarker(new Marker(false));
        conf.addPlotOptions(plotOptions);

        year.addValueChangeListener(event -> {
            chart.drawChart(true);
            conf.setSeries(
                    new ListSeries("Berlin", gRndmInt(1000,1100), gRndmInt(920,1000), gRndmInt(840,920), gRndmInt(760,840), gRndmInt(680,760), gRndmInt(600,680), gRndmInt(680,760), gRndmInt(760,840), gRndmInt(840,920), gRndmInt(920,1000), gRndmInt(1000,1080), gRndmInt(1100,1250)),
                    new ListSeries("London", gRndmInt(740,800), gRndmInt(680,740), gRndmInt(620,680), gRndmInt(560,620), gRndmInt(500,560), gRndmInt(440,500), gRndmInt(500,560), gRndmInt(560,620), gRndmInt(620,680), gRndmInt(680,740), gRndmInt(740,800), gRndmInt(800,900)),
                    new ListSeries("New York", gRndmInt(560,610), gRndmInt(510,560), gRndmInt(460,510), gRndmInt(410,460), gRndmInt(360,410), gRndmInt(310,360), gRndmInt(360,410), gRndmInt(410,460), gRndmInt(460,510), gRndmInt(510,560), gRndmInt(560,610), gRndmInt(610,650)),
                    new ListSeries("Tokyo", gRndmInt(400,440), gRndmInt(360,400), gRndmInt(320,360), gRndmInt(120,160), gRndmInt(240,280), gRndmInt(200,240), gRndmInt(240,280), gRndmInt(280,320), gRndmInt(320,360), gRndmInt(360,400), gRndmInt(400,440), gRndmInt(440,475))
            );

        });
        conf.addSeries(new ListSeries("Berlin", gRndmInt(1000,1100), gRndmInt(920,1000), gRndmInt(840,920), gRndmInt(760,840), gRndmInt(680,760), gRndmInt(600,680), gRndmInt(680,760), gRndmInt(760,840), gRndmInt(840,920), gRndmInt(920,1000), gRndmInt(1000,1080), gRndmInt(1100,1250)));
        conf.addSeries(new ListSeries("London", gRndmInt(740,800), gRndmInt(680,740), gRndmInt(620,680), gRndmInt(560,620), gRndmInt(500,560), gRndmInt(440,500), gRndmInt(500,560), gRndmInt(560,620), gRndmInt(620,680), gRndmInt(680,740), gRndmInt(740,800), gRndmInt(800,900)));
        conf.addSeries(new ListSeries("New York", gRndmInt(560,610), gRndmInt(510,560), gRndmInt(460,510), gRndmInt(410,460), gRndmInt(360,410), gRndmInt(310,360), gRndmInt(360,410), gRndmInt(410,460), gRndmInt(460,510), gRndmInt(510,560), gRndmInt(560,610), gRndmInt(610,650)));
        conf.addSeries(new ListSeries("Tokyo", gRndmInt(400,440), gRndmInt(360,400), gRndmInt(320,360), gRndmInt(280,320), gRndmInt(240,280), gRndmInt(200,240), gRndmInt(240,280), gRndmInt(280,320), gRndmInt(320,360), gRndmInt(360,400), gRndmInt(400,440), gRndmInt(440,475)));
        dvbdy.add(chart);
        // Add it all together
        Div viewEvents = new Div(dvHeader, dvbdy);

        return viewEvents;
    }

    private Component createServiceHealth() {
        // Header
        HorizontalLayout header = createHeader("Total Credits", "Cash-In / Cash-Out");

        // Grid
        Grid<ServiceHealth> grid = new Grid();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setAllRowsVisible(true);

        grid.addColumn(new ComponentRenderer<>(serviceHealth -> {
            Span status = new Span();
            String statusText = getStatusDisplayName(serviceHealth);
            status.getElement().setAttribute("aria-label", "Status: " + statusText);
            status.getElement().setAttribute("title", "Status: " + statusText);
            status.getElement().getThemeList().add(getStatusTheme(serviceHealth));
            return status;
        })).setHeader("").setFlexGrow(0).setAutoWidth(true);
        grid.addColumn(ServiceHealth::getCity).setHeader("City").setFlexGrow(1);
        grid.addColumn(ServiceHealth::getInput).setHeader("Cash-In").setAutoWidth(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(ServiceHealth::getOutput).setHeader("Cash-Out").setAutoWidth(true)
                .setTextAlign(ColumnTextAlign.END);

        grid.setItems(new ServiceHealth(ServiceHealth.Status.EXCELLENT, "Münster", 324, 1540),
                new ServiceHealth(ServiceHealth.Status.OK, "Cluj-Napoca", 311, 1320),
                new ServiceHealth(ServiceHealth.Status.FAILING, "Ciudad Victoria", 300, 1219));

        // Add it all together
        VerticalLayout serviceHealth = new VerticalLayout(header, grid);
        serviceHealth.addClassName("p-l");
        serviceHealth.setPadding(false);
        serviceHealth.setSpacing(false);
        serviceHealth.getElement().getThemeList().add("spacing-l");
        return serviceHealth;
    }

    private Component createResponseTimes() {
        int OngoingCnt= getRideDetails("1");
        int CompletedCnt= getRideDetails("2");
        int CancelledCnt= getRideDetails("4");
        Div dvHeader =new Div();
        Div dvbdy =new Div();

        //widget-content tab-content bg-white p-20
        dvHeader.addClassNames("card-header card-header-transparent py-20");
        dvbdy.addClassNames("widget-content tab-content bg-white p-20");
        //HorizontalLayout header = createHeader("Total Rides", "");
        dvHeader.add( createHeader("Total Rides", ""));
        // Chart
        Chart chart = new Chart(ChartType.PIE);
        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
//        chart.setThemeName("gradient");


        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Ongoing Rides", OngoingCnt));
        series.add(new DataSeriesItem("Completed Rides", CompletedCnt));
        //series.add(new DataSeriesItem("Failed Rides", 7.5));
        series.add(new DataSeriesItem("Cancelled Rides", CancelledCnt));
//        series.add(new DataSeriesItem("Sector 5", 12.5));
//        series.add(new DataSeriesItem("Sector 6", 12.5));
        conf.addSeries(series);

        conf.setLegend(conf.getLegend());
        PlotOptionsPie plotOptionsPie = new PlotOptionsPie();
        plotOptionsPie.setShowInLegend(true);

        conf.setPlotOptions(plotOptionsPie);
        dvbdy.add(chart);
        // Add it all together
        Div serviceHealth = new Div(dvHeader, dvbdy);

        serviceHealth.addClassNames("card", "card-shadow");
        return serviceHealth;
    }
    private Component createResponseTimes2() {
        Div dvHeader =new Div();
        Div dvbdy =new Div();

        //widget-content tab-content bg-white p-20
        dvHeader.addClassNames("card-header card-header-transparent py-20");
        dvbdy.addClassNames("widget-content tab-content bg-white p-20");
        //HorizontalLayout header = createHeader("Total Rides", "");
        dvHeader.add( createHeader("Daily Credit", ""));


        // Chart
        Chart chart = new Chart(ChartType.PIE);
        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        //chart.setThemeName("gradient");


        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Ongoing Credit", 60));
        series.add(new DataSeriesItem("Completed Credit", 25));
        series.add(new DataSeriesItem("Unpaid Credit", 7.5));
        series.add(new DataSeriesItem("Expired Credit", 7.5));
//        series.add(new DataSeriesItem("Sector 5", 12.5));
//        series.add(new DataSeriesItem("Sector 6", 12.5));
        conf.addSeries(series);

        conf.setLegend(conf.getLegend());
        PlotOptionsPie plotOptionsPie = new PlotOptionsPie();
        plotOptionsPie.setShowInLegend(true);
        plotOptionsPie.setInnerSize("80%");
        plotOptionsPie.setSize("75%");  // Default
        plotOptionsPie.setCenter("50%", "50%"); // Default

        conf.setPlotOptions(plotOptionsPie);
        dvbdy.add(chart);
        // Add it all together
        Div serviceHealth = new Div(dvHeader, dvbdy);
        serviceHealth.addClassNames("card", "card-shadow");
        return serviceHealth;
    }

    private Component createHeader1(String title, String subtitle) {
        H4 h2 = new H4(title);
        h2.addClassNames("text-xl", "m-0");

        Span span = new Span(subtitle);
        span.addClassNames("text-secondary", "text-xs");

        Div column = new Div(h2, span);


        Div header = new Div(column);

    return header;
    }
    private HorizontalLayout createHeader(String title, String subtitle) {
        H4 h2 = new H4(title);
        h2.addClassNames("text-xl", "m-0");

        Span span = new Span(subtitle);
        span.addClassNames("text-secondary", "text-xs");

        VerticalLayout column = new VerticalLayout(h2, span);
        column.setPadding(false);
        column.setSpacing(false);

        HorizontalLayout header = new HorizontalLayout(column);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setSpacing(false);
        header.setWidthFull();
        return header;
    }

    private String getStatusDisplayName(ServiceHealth serviceHealth) {
        ServiceHealth.Status status = serviceHealth.getStatus();
        if (status == ServiceHealth.Status.OK) {
            return "Ok";
        } else if (status == ServiceHealth.Status.FAILING) {
            return "Failing";
        } else if (status == ServiceHealth.Status.EXCELLENT) {
            return "Excellent";
        } else {
            return status.toString();
        }
    }

    private String getStatusTheme(ServiceHealth serviceHealth) {
        ServiceHealth.Status status = serviceHealth.getStatus();
        String theme = "badge primary small";
        if (status == ServiceHealth.Status.EXCELLENT) {
            theme += " success";
        } else if (status == ServiceHealth.Status.FAILING) {
            theme += " error";
        }
        return theme;
    }


    /* Active Drivers */
    private Component createDriverRegistrationsCharts() { // Active Drivers

            Chart chart = new Chart(ChartType.GAUGE);

            Configuration conf = chart.getConfiguration();
            conf.setTitle("Active Drivers");
            conf.getPane().setStartAngle(-135);
            conf.getPane().setEndAngle(135);

            YAxis yaxis = new YAxis();
            yaxis.setTitle("km/h");

// The limits are mandatory
            yaxis.setMin(0);
            yaxis.setMax(100);

// Other configuration
            yaxis.getLabels().setStep(1);
            yaxis.setTickInterval(10);
            yaxis.setTickLength(10);
            yaxis.setTickWidth(1);
            yaxis.setMinorTickInterval("auto");
            yaxis.setMinorTickLength(5);
            yaxis.setMinorTickWidth(1);

            PlotBand green = new PlotBand(0, 60, new SolidColor("#057f01"));
            green.setClassName("green");

            PlotBand yellow = new PlotBand(60, 80, new SolidColor("#fcfd07"));
            yellow.setClassName("yellow");

            PlotBand red = new PlotBand(80, 100, new SolidColor("#ff0001"));
            red.setClassName("red");

            yaxis.setPlotBands(green, yellow, red);

            conf.addyAxis(yaxis);

            ListSeries series = new ListSeries("Speed", 80);
            conf.addSeries(series);


        FlexBoxLayout gauge = new FlexBoxLayout(
//                new Label(status.getName()), chartContainer
                chart
        );
//        paymentChart.addClassName("dashboard" + "__analytics-chart");//(CLASS_NAME + "__payment-chart");
        gauge.setAlignItems(FlexComponent.Alignment.CENTER);
        gauge.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        gauge.setPadding(Bottom.S, Top.M);

            return gauge;
    }


    /* Active Users */
    private Component createDriverRegistrationsCharts2() { // Active Users

        Chart chart = new Chart(ChartType.GAUGE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Active Users");
        conf.getPane().setStartAngle(-135);
        conf.getPane().setEndAngle(135);

        YAxis yaxis = new YAxis();
        yaxis.setTitle("km/h");

// The limits are mandatory
        yaxis.setMin(0);
        yaxis.setMax(100);

// Other configuration
        yaxis.getLabels().setStep(1);
        yaxis.setTickInterval(10);
        yaxis.setTickLength(10);
        yaxis.setTickWidth(1);
        yaxis.setMinorTickInterval("auto");
        yaxis.setMinorTickLength(5);
        yaxis.setMinorTickWidth(1);

        PlotBand green = new PlotBand(0, 60, new SolidColor("#057f01"));
        green.setClassName("green");

        PlotBand yellow = new PlotBand(60, 80, new SolidColor("#fcfd07"));
        yellow.setClassName("yellow");

        PlotBand red = new PlotBand(80, 100, new SolidColor("#ff0001"));
        red.setClassName("red");

        yaxis.setPlotBands(green, yellow, red);

        conf.addyAxis(yaxis);

        ListSeries series = new ListSeries("Speed", 80);
        conf.addSeries(series);

        FlexBoxLayout gauge = new FlexBoxLayout(
//                new Label(status.getName()), chartContainer
                chart
        );
//        paymentChart.addClassName("dashboard" + "__analytics-chart");//(CLASS_NAME + "__payment-chart");
        gauge.setAlignItems(FlexComponent.Alignment.CENTER);
        gauge.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        gauge.setPadding(Bottom.S, Top.M);

        return gauge;
    }


    /* Total Earnings */
    private Component createDriverRegistrationsCharts3() { // Total Earnings

        Chart chart = new Chart(ChartType.GAUGE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Total Earnings");
        conf.getPane().setStartAngle(-135);
        conf.getPane().setEndAngle(135);

        YAxis yaxis = new YAxis();
        yaxis.setTitle("km/h");

// The limits are mandatory
        yaxis.setMin(0);
        yaxis.setMax(100);

// Other configuration
        yaxis.getLabels().setStep(1);
        yaxis.setTickInterval(10);
        yaxis.setTickLength(10);
        yaxis.setTickWidth(1);
        yaxis.setMinorTickInterval("auto");
        yaxis.setMinorTickLength(5);
        yaxis.setMinorTickWidth(1);

        PlotBand green = new PlotBand(0, 60, new SolidColor("#057f01"));
        green.setClassName("green");

        PlotBand yellow = new PlotBand(60, 80, new SolidColor("#fcfd07"));
        yellow.setClassName("yellow");

        PlotBand red = new PlotBand(80, 100, new SolidColor("#ff0001"));
        red.setClassName("red");

        yaxis.setPlotBands(green, yellow, red);

        conf.addyAxis(yaxis);

        ListSeries series = new ListSeries("Speed", 80);
        conf.addSeries(series);


        FlexBoxLayout gauge = new FlexBoxLayout(
//                new Label(status.getName()), chartContainer
                chart
        );
//        paymentChart.addClassName("dashboard" + "__analytics-chart");//(CLASS_NAME + "__payment-chart");
        gauge.setAlignItems(FlexComponent.Alignment.CENTER);
        gauge.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        gauge.setPadding(Bottom.S, Top.M);

        return gauge;
    }


    /* Total Discount */
    private Component createDriverRegistrationsCharts4() { // Active Drivers

        Chart chart = new Chart(ChartType.GAUGE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Total Discount");
        conf.getPane().setStartAngle(-135);
        conf.getPane().setEndAngle(135);

        YAxis yaxis = new YAxis();
        yaxis.setTitle("km/h");

// The limits are mandatory
        yaxis.setMin(0);
        yaxis.setMax(100);

// Other configuration
        yaxis.getLabels().setStep(1);
        yaxis.setTickInterval(10);
        yaxis.setTickLength(10);
        yaxis.setTickWidth(1);
        yaxis.setMinorTickInterval("auto");
        yaxis.setMinorTickLength(5);
        yaxis.setMinorTickWidth(1);

        PlotBand green = new PlotBand(0, 60, new SolidColor("#057f01"));
        green.setClassName("green");

        PlotBand yellow = new PlotBand(60, 80, new SolidColor("#fcfd07"));
        yellow.setClassName("yellow");

        PlotBand red = new PlotBand(80, 100, new SolidColor("#ff0001"));
        red.setClassName("red");

        yaxis.setPlotBands(green, yellow, red);

        conf.addyAxis(yaxis);

        ListSeries series = new ListSeries("Speed", 80);
        conf.addSeries(series);

        FlexBoxLayout gauge = new FlexBoxLayout(
//                new Label(status.getName()), chartContainer
                chart
        );
//        paymentChart.addClassName("dashboard" + "__analytics-chart");//(CLASS_NAME + "__payment-chart");
        gauge.setAlignItems(FlexComponent.Alignment.CENTER);
        gauge.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        gauge.setPadding(Bottom.S, Top.M);

        return gauge;
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
//        card.setBorderRadius(BorderRadius.S);
        card.addClassName("rounded_border");
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
    }

    private Chart createProgressChart(Payment.Status status, int value, String color) {
        Chart chart = new Chart();
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
//        background.setBackgroundColor(new SolidColor("562E91"));
        background.setShape(BackgroundShape.ARC);
        background.setInnerRadius("100%");
        background.setOuterRadius("110%");

        pane.setBackground(background);

//        background.setBackgroundColor(new SolidColor("#EC9800"));

        return chart;
    }
    private Chart createProgressChart2(int value, String color) {
        Chart chart = new Chart();
//        chart.addClassName(status.getName().toLowerCase());
        chart.setSizeFull();

        Configuration configuration = chart.getConfiguration();
        configuration.getChart().setType(ChartType.SOLIDGAUGE);
        configuration.setTitle("");
        configuration.getTooltip().setEnabled(false);

        configuration.getyAxis().setMin(0);
        configuration.getyAxis().setMax(100);
        configuration.getyAxis().getLabels().setEnabled(false);
        configuration.getyAxis().setTickInterval(0);
        configuration.getyAxis().setTickLength(0);
        configuration.getyAxis().setTickWidth(0);


        PlotOptionsSolidgauge opt = new PlotOptionsSolidgauge();
        opt.getDataLabels().setEnabled(false);
        configuration.setPlotOptions(opt);

        DataSeriesItemWithRadius point = new DataSeriesItemWithRadius();
        point.setY(value);
        point.setInnerRadius("100%");
        point.setRadius("120%");
        point.setColor(new SolidColor(color));
        configuration.setSeries(new DataSeries(point));

        Pane pane = configuration.getPane();
        pane.setStartAngle(0);
//        pane.setEndAngle(300);
        pane.setEndAngle(gRndmInt(250,345));

        Background background = new Background();
        background.setBackgroundColor(new SolidColor("#EC9800")); //color
        background.setShape(BackgroundShape.ARC);
        background.setInnerRadius("100%");
        background.setOuterRadius("110%");

        pane.setBackground(background);

//        background.setBackgroundColor(new SolidColor("#EC9800"));

        return chart;
    }


    @Route("grid-content")
    public class GridContent extends Div {

        public GridContent() {
            // tag::snippet1[]
            Grid<Person> grid = new Grid<>(Person.class, false);
            grid.setSelectionMode(Grid.SelectionMode.MULTI);
            grid.addColumn(createEmployeeRenderer()).setHeader("User")
                    .setAutoWidth(true).setFlexGrow(0);
            grid.addColumn(createStatusComponentRenderer()).setHeader("Status")
                    .setAutoWidth(true);
            grid.addColumn(Person::getProfession).setHeader("Reason").setAutoWidth(true);
            // end::snippet1[]

            List<Person> people = DataService.getPeople();
            grid.setItems(people);
            grid.addClassName("rounded_border");

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

}

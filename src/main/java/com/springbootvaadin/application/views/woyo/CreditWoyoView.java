package com.springbootvaadin.application.views.woyo;

import com.springbootvaadin.application.backend.Payment;
import com.springbootvaadin.application.components.DataSeriesItemWithRadius;
import com.springbootvaadin.application.components.FlexBoxLayout;
import com.springbootvaadin.application.data.DataService;
import com.springbootvaadin.application.data.Person;
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
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
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
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Random;

@CssImport("./styles/views/statistics.css")
@PageTitle("Credit Statistics")
@Route(value = "creditStats", layout = MainLayout.class)
//@RouteAlias(value = "", layout = MainLayout.class)
@CssImport("./styles/lumo/margin.css")
//@AnonymousAllowed
@RolesAllowed("ADMIN")

public class CreditWoyoView extends ViewFrame {

    private static final String MAX_WIDTH = "2048px";
    private static final String CLASS_NAME = "dashboard";
    private TextField name;
    private Button sayHello;
    FlexBoxLayout content0 = new FlexBoxLayout();

    Tab taxiStatistics = new Tab("Taxi Statistics");
    Tab creditStatistics = new Tab("Credit Statistics");


    public CreditWoyoView() {


        addClassName("basic-board");

        Tabs tabs = new Tabs(taxiStatistics, creditStatistics);
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
//        add(tabs);

        tabs.addSelectedChangeListener(event ->
                setContent(event.getSelectedTab())
        );

//        content0.setSpacing();
//        setContent(tabs.getSelectedTab());

//        setViewContent(tabs, content0);
        content0.add(createContent());
        setViewContent(content0);

    }

    private void setContent(Tab tab) {
        content0.removeAll();

        if (tab.equals(taxiStatistics)) {
            content0.add(createContent());
        } else if (tab.equals(creditStatistics)) {
            content0.add(createContent());
        }
//        else {
//            content0.add(createShippingAddressSection());//createPaymentInformationSection());
//        }
    }

    public Component createContent() {

        Component driverRegistrations = createDriverRegistration();
        Component transactions = createCarRides();
//        Component docs = createDocs();
        Component carsData = createSubContent1();
        Component carsData2 = createSubContent2();

        FlexBoxLayout content = new FlexBoxLayout(
                driverRegistrations
//                transactions,
//                carsData
//                carsData2
        ); //, docs);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        content.setPadding(Uniform.M);
        content.setWidthFull();
//        content.setBackgroundColor(String.valueOf(new SolidColor("00F28D")));
        return content;
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

    private Component createCarRides(){

        Icon icn = new Icon(VaadinIcon.CAR);
//        icn = UIUtils.createIcon(IconSize.M, TextColor.SECONDARY, VaadinIcon.MALE);
        H3 header = new H3("Total Rides");
        header.addClassNames(LumoUtility.Display.INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(icn, header);
        layout.addClassName(LumoUtility.Display.INLINE);


        FlexBoxLayout rides = new FlexBoxLayout(
//                createHeader(VaadinIcon.CAR, " Rides"),
                layout,
                createAreaChart());
        rides.setBoxSizing(BoxSizing.BORDER_BOX);
        rides.setDisplay(Display.BLOCK);
//        rides.setMargin(Top.XL);
        rides.setMaxWidth(MAX_WIDTH);
        rides.setPadding(Uniform.M);
        rides.setWidthFull();

        Component carRides0 = rides;

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
//                createHeader(VaadinIcon.GLOBE_WIRE, " UserDetails"),
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
        Chart chart = new Chart(ChartType.AREASPLINE);
        DatePicker datePicker = new DatePicker("Start Year");
//        add(datePicker);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("August - Monthwise");
        conf.getLegend().setEnabled(false);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("Number of Rides");

        conf.addSeries(new ListSeries(220, 240, 400, 360, 420, 640, 580, 800,
                600, 580, 740, 800));

        FlexBoxLayout card = new FlexBoxLayout();
        card.add(datePicker);
        card.add(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
//        card.setBorderRadius(BorderRadius.S);
        card.addClassName("rounded_border");
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

    private Component createCarRides2(){

        Icon icn = new Icon(VaadinIcon.CAR);
//        icn = UIUtils.createIcon(IconSize.M, TextColor.SECONDARY, VaadinIcon.MALE);
        H3 header = new H3("Credits");
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
    private Component createBarChart() { // it is COLUMN type of chart
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("September-Weekwise");
//        conf.getLegend().setEnabled(false);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        conf.addxAxis(xAxis);

        //conf.getyAxis().setTitle("Number of Rides & Hours");

        conf.addSeries(new ListSeries("Completed Credit",15, 12, 16, 14, 8, 10, 13));

        conf.addSeries(new ListSeries("Ongoing Credit", gRndmInt(), gRndmInt(), gRndmInt(), gRndmInt(), gRndmInt(), gRndmInt(), gRndmInt()));
//        conf.addSeries(new ListSeries("Max Duration (Hr)", 2,5,3,4,1,6,3));
        conf.addSeries(new ListSeries("Unpaid Credit", gRndmInt(), gRndmInt(), gRndmInt(), gRndmInt(), gRndmInt(), gRndmInt(), gRndmInt()));
//

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
    private Component createDriverRegistration() {

        Icon icn = new Icon(VaadinIcon.MONEY_EXCHANGE);
//      icn = UIUtils.createIcon(IconSize.M, TextColor.SECONDARY, VaadinIcon.MALE);

        H3 header = new H3("Credit Statistics");
        header.addClassNames(LumoUtility.Display.INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(icn, header);
        layout.addClassName(LumoUtility.Display.INLINE);

        FlexBoxLayout driverRegistrations = new FlexBoxLayout(
                layout,
                createRow());

        driverRegistrations.setBoxSizing(BoxSizing.BORDER_BOX);
        driverRegistrations.addClassName("rounded_border");
        driverRegistrations.setDisplay(Display.BLOCK);
        driverRegistrations.setMargin(Top.L);
        driverRegistrations.setMaxWidth(MAX_WIDTH);
        driverRegistrations.setPadding(Uniform.M);
        driverRegistrations.setWidthFull();


        Component carRides01 = driverRegistrations;

        Row subContent = new Row(carRides01);
        subContent.addClassName(LumoStyles.Margin.Top.XL);
        UIUtils.setMaxWidth(MAX_WIDTH,subContent);
        subContent.setWidthFull();


        return carRides01;
    }

    private Component createRow(){

        RouterLink link11 = new RouterLink();
        link11.setRoute(ListView.class);
        RouterLink link12 = new RouterLink();
        link12.setRoute(ListView.class);
        RouterLink link13 = new RouterLink();
        link13.setRoute(ListView.class);
        RouterLink link14 = new RouterLink();
        link14.setRoute(ListView.class);
        RouterLink link21 = new RouterLink();
        link21.setRoute(ListView.class);
        RouterLink link22 = new RouterLink();
        link22.setRoute(ListView.class);
        RouterLink link23 = new RouterLink();
        link23.setRoute(ListView.class);
        RouterLink link24 = new RouterLink();
        link24.setRoute(ListView.class);

//        link.add(vaadinIcon);

        Component card11=createHighlight(VaadinIcon.USER.create(),"Active Users", "745", 33.7);
        Component card12=createHighlight(VaadinIcon.USER_CARD.create(),"Active Driers", "54.6k", -112.45);
        Component card13=createHighlight(VaadinIcon.COIN_PILES.create(),"Total Earning", "18620", 3.9);
        Component card14=createHighlight(VaadinIcon.COINS.create(),"Total Credit", "5754", 0.0);

        Component card21=createHighlight(VaadinIcon.PIGGY_BANK_COIN.create(),"Ongoing Credit", "750", -14.0);
        Component card22=createHighlight(VaadinIcon.CHECK_CIRCLE_O.create(),"Completed Credit", "3245", 5.3);
        Component card23=createHighlight(VaadinIcon.CLOSE.create(),"Unpaid Credit", "453", 2.1);
        Component card24=createHighlight(VaadinIcon.CLOSE_CIRCLE_O.create(),"Auto Cancelled", "14", 2.8);

        link11.add(card11);
        link12.add(card12);
        link13.add(card13);
        link14.add(card14);
        link21.add(card21);
        link22.add(card22);
        link23.add(card23);
        link24.add(card24);

        Board board = new Board();
        board.addRow(
                link11,
                link12,
                link13,
                link14
        );
        board.addRow(
                link21,
                link22,
                link23
//                link24
        );
        board.addRow(createViewEvents());

        FlexBoxLayout gaugeRow = new FlexBoxLayout(
//                new Label(status.getName()), chartContainer
                board
        );
//        paymentChart.addClassName("dashboard" + "__analytics-chart");//(CLASS_NAME + "__payment-chart");
        gaugeRow.setAlignItems(FlexComponent.Alignment.CENTER);
        gaugeRow.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        gaugeRow.setBorderRadius(BorderRadius.M);
        gaugeRow.setShadow(Shadow.XS);

        return gaugeRow;
    }
    private Component createHighlight(Icon tileIcon, String title, String value, Double percentage) {
        VaadinIcon icon = VaadinIcon.ARROW_UP;
        String prefix = "";
        String theme = "badge";

        if (percentage == 0) {
            prefix = "±";
        } else if (percentage > 0) {
            prefix = "+";
            theme += " success";
        } else if (percentage < 0) {
            icon = VaadinIcon.ARROW_DOWN;
            theme += " error";
        }

        H2 h2 = new H2(title);
        h2.addClassNames("font-normal", "m-0", "text-secondary", "text-xs");

        Span span = new Span(value);
        span.addClassNames("font-semibold", "text-3xl");

        Icon i = icon.create();
        i.addClassNames("box-border", "p-xs");

        Span badge = new Span(i, new Span(prefix + percentage.toString()));
        badge.getElement().getThemeList().add(theme);

        tileIcon.addClassNames("box-border", "p-xs");
        HorizontalLayout iconH2 = new HorizontalLayout(tileIcon, h2);
        iconH2.setSpacing(false);
        iconH2.setAlignItems(FlexComponent.Alignment.BASELINE);

        VerticalLayout layout = new VerticalLayout(iconH2, span, badge);
        layout.addClassName("p-l");
        layout.setPadding(false);
        layout.setSpacing(false);
        return layout;
    }
    private Component createViewEvents() {
        // Header
        com.vaadin.flow.component.select.Select year = new Select();
        year.setItems("2021", "2022");
        year.setValue("2022");
        year.setWidth("100px");

        HorizontalLayout header = createHeader("Credits", "--");
        header.add(year);

        // Chart
        Chart chart = new Chart(ChartType.AREASPLINE);
        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("Values");

        PlotOptionsAreaspline plotOptions = new PlotOptionsAreaspline();
        plotOptions.setPointPlacement(PointPlacement.ON);
        plotOptions.setMarker(new Marker(false));
        conf.addPlotOptions(plotOptions);

        year.addValueChangeListener(event -> {
                    chart.drawChart(true);
            chart.getConfiguration().setSeries(
//                    new ListSeries("Berlin", gRndmInt(190,280), gRndmInt(280,350), gRndmInt(350,430), gRndmInt(430,510), gRndmInt(510,590), gRndmInt(600,680), gRndmInt(680,760), gRndmInt(760,840), gRndmInt(840,920), gRndmInt(920,1000), gRndmInt(1000,1080), gRndmInt(1100,1250)),
//                    new ListSeries("London", gRndmInt(140,200), gRndmInt(200,260), gRndmInt(260,320), gRndmInt(320,380), gRndmInt(380,440), gRndmInt(440,500), gRndmInt(500,560), gRndmInt(560,620), gRndmInt(620,680), gRndmInt(680,740), gRndmInt(740,800), gRndmInt(800,900)),
//                    new ListSeries("New York", gRndmInt(65,110), gRndmInt(110,160), gRndmInt(160,210), gRndmInt(210,260), gRndmInt(260,310), gRndmInt(310,360), gRndmInt(360,410), gRndmInt(410,460), gRndmInt(460,510), gRndmInt(510,560), gRndmInt(560,610), gRndmInt(610,650)),
//                    new ListSeries("Tokyo", gRndmInt(0,40), gRndmInt(40,80), gRndmInt(80,120), gRndmInt(120,160), gRndmInt(160,200), gRndmInt(200,240), gRndmInt(240,280), gRndmInt(280,320), gRndmInt(320,360), gRndmInt(360,400), gRndmInt(400,440), gRndmInt(440,475))
                    new ListSeries("Ongoing Credit", gRndmInt(190,280), gRndmInt(280,350), gRndmInt(350,430), gRndmInt(430,510), gRndmInt(510,590), gRndmInt(600,680), gRndmInt(680,760), gRndmInt(760,840), gRndmInt(840,920), gRndmInt(920,1000), gRndmInt(1000,1080), gRndmInt(1100,1250)),
                    new ListSeries("Completed Credit", gRndmInt(140,200), gRndmInt(200,260), gRndmInt(260,320), gRndmInt(320,380), gRndmInt(380,440), gRndmInt(440,500), gRndmInt(500,560), gRndmInt(560,620), gRndmInt(620,680), gRndmInt(680,740), gRndmInt(740,800), gRndmInt(800,900)),
                    new ListSeries("Unpaid Credit", gRndmInt(65,110), gRndmInt(110,160), gRndmInt(160,210), gRndmInt(210,260), gRndmInt(260,310), gRndmInt(310,360), gRndmInt(360,410), gRndmInt(410,460), gRndmInt(460,510), gRndmInt(510,560), gRndmInt(560,610), gRndmInt(610,650))
            );
        });
        conf.addSeries(new ListSeries("Ongoing Credit", gRndmInt(190,280), gRndmInt(280,350), gRndmInt(350,430), gRndmInt(430,510), gRndmInt(510,590), gRndmInt(600,680), gRndmInt(680,760), gRndmInt(760,840), gRndmInt(840,920), gRndmInt(920,1000), gRndmInt(1000,1080), gRndmInt(1100,1250)));
        conf.addSeries(new ListSeries("Completed Credit", gRndmInt(140,200), gRndmInt(200,260), gRndmInt(260,320), gRndmInt(320,380), gRndmInt(380,440), gRndmInt(440,500), gRndmInt(500,560), gRndmInt(560,620), gRndmInt(620,680), gRndmInt(680,740), gRndmInt(740,800), gRndmInt(800,900)));
        conf.addSeries(new ListSeries("Unpaid Credit", gRndmInt(65,110), gRndmInt(110,160), gRndmInt(160,210), gRndmInt(210,260), gRndmInt(260,310), gRndmInt(310,360), gRndmInt(360,410), gRndmInt(410,460), gRndmInt(460,510), gRndmInt(510,560), gRndmInt(560,610), gRndmInt(610,650)));
//        //conf.addSeries(new ListSeries("Tokyo", gRndmInt(0,40), gRndmInt(40,80), gRndmInt(80,120), gRndmInt(120,160), gRndmInt(160,200), gRndmInt(200,240), gRndmInt(240,280), gRndmInt(280,320), gRndmInt(320,360), gRndmInt(360,400), gRndmInt(400,440), gRndmInt(440,475)));

        // Add it all together
        VerticalLayout viewEvents = new VerticalLayout(header, chart);
        viewEvents.addClassName("p-l");
        viewEvents.setPadding(false);
        viewEvents.setSpacing(false);
        viewEvents.getElement().getThemeList().add("spacing-l");

        System.out.println("------------- elements : "
                +viewEvents.getElement().getChild(1).getComponent().get().getElement());
        return viewEvents;
    }
    private HorizontalLayout createHeader(String title, String subtitle) {
        H2 h2 = new H2(title);
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
    public int gRndmInt(int min, int max ) { // fullName as => getRandomNumberUsingNextInt(int min, int max)
//        int min=1,  max=10;
        Random random = new Random();
        return random.nextInt(max - min) + min;
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


    /* Total Credit */
    private Component createDriverRegistrationsCharts4() { // Total Credit

        Chart chart = new Chart(ChartType.GAUGE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Total Credit");
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



    @Route("grid-content")
    public class GridContent extends Div {

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

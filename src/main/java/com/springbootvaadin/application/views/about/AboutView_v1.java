
package com.springbootvaadin.application.views.about;

import com.springbootvaadin.application.views.MainLayout;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.pekkam.Canvas;


@PageTitle("About_v1")
@Route(value = "about_v1", layout = MainLayout.class)
@AnonymousAllowed
//@JavaScript("https://code.jquery.com/jquery-3.5.1.slim.min.js")
//@JavaScript("https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js")
//@CssImport("./themes/appwoyo/views/bootstrap.min.css")
//@CssImport("./themes/app-assets/vendors/css/vendors.min.css")
//@CssImport("./themes/app-assets/vendors/css/weather-icons/climacons.min.css")
//@CssImport("./themes/app-assets/fonts/meteocons/style.min.css")
//@CssImport("./themes/app-assets/vendors/css/charts/morris.css")
//@CssImport("./themes/app-assets/vendors/css/charts/chartist.css")
//@CssImport("./themes/app-assets/vendors/css/charts/chartist-plugin-tooltip.css")
//@CssImport("./themes/app-assets/css/bootstrap.min.css")
//@CssImport("./themes/app-assets/css/bootstrap-extended.min.css")
//@CssImport("./themes/app-assets/css/colors.min.css")
//@CssImport("./themes/app-assets/css/components.min.css")
//@CssImport("./themes/app-assets/css/core/menu/menu-types/vertical-menu-modern.css")
//@CssImport("./themes/app-assets/css/core/colors/palette-gradient.min.css")
//@CssImport("./themes/app-assets/fonts/simple-line-icons/style.min.css")
//@CssImport("./themes/app-assets/css/core/colors/palette-gradient.min.css")
//@CssImport("./themes/app-assets/css/pages/timeline.min.css")
//@CssImport("./themes/app-assets/css/pages/dashboard-ecommerce.min.css")
//@JavaScript("./themes/app-assets/vendors/js/vendors.min.js")
//@JavaScript("./themes/app-assets/vendors/js/charts/chartist.min.js")
//@JavaScript("./themes/app-assets/vendors/js/charts/chartist-plugin-tooltip.min.js")
//@JavaScript("./themes/app-assets/vendors/js/charts/raphael-min.js")
//@JavaScript("./themes/app-assets/vendors/js/charts/morris.min.js")
//@JavaScript("./themes/app-assets/vendors/js/timeline/horizontal-timeline.js")
//@JavaScript("./themes/app-assets/js/core/app-menu.min.js")
//@JavaScript("./themes/app-assets/js/core/app.min.js")
//@JavaScript("./themes/app-assets/js/scripts/customizer.min.js")
//@JavaScript("./themes/app-assets/js/scripts/footer.min.js")
//@JavaScript("./themes/app-assets/js/scripts/pages/dashboard-ecommerce.min.js")

//pie and doughut js

//@JavaScript("./themes/app-assets/js/scripts/charts/chartjs/pie-doughnut/pie.js")
//@JavaScript("./themes/app-assets/js/scripts/charts/chartjs/pie-doughnut/pie-simple.js")
//@JavaScript("./themes/app-assets/js/scripts/charts/chartjs/pie-doughnut/doughnut.js")
//@JavaScript("./themes/app-assets/js/scripts/charts/chartjs/pie-doughnut/doughnut-simple.js")

@JavaScript("https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js")

public class AboutView_v1 extends Div {


    public AboutView_v1() {
        Div divpr1 = new Div();
        Div divpr2 = new Div();
        Div divpr3 = new Div();
        Div divpr4 = new Div();
        Div divpr5 = new Div();
        Div div = new Div();
        div.addClassName("row");
        add(divpr1);
        divpr1.addClassNames("app-content", "content");
        divpr2.addClassName("content-overlay");
        divpr3.addClassName("content-wrapper");
        divpr4.addClassNames("content-header", "row");
        divpr5.addClassName("content-body");
        divpr1.add(divpr2);
        divpr1.add(divpr3);
        divpr3.add(divpr4);
        divpr3.add(divpr5);
        divpr5.add(div);
        div.add(block1());
        div.add(block2());
        div.add(block3());
        div.add(block4());
        divpr5.add(block5());


    }
    public Div block1() {




        Div div1 = new Div();
        Div div2 = new Div();
        Div div3 = new Div();
        Div div4 = new Div();
        Div div5 = new Div();
        Div div6 = new Div();
        Div div7 = new Div();
        Div div8 = new Div();
        Div div9 = new Div();
        Div div10 = new Div();
        Div div11 = new Div();
        Div div12 = new Div();
        H3 h31 = new H3("850");
        H6 h61 = new H6("Products Sold");


        div1.addClassNames("col-xl-3", "col-lg-6", "col-12");
        div2.addClassNames("card", "pull-up");
        div3.addClassName("card-content");
        div4.addClassName("card-body");
        div5.addClassNames("media", "d-flex");
        div6.addClassNames("media-body", "text-left");
        div8.addClassNames("icon-basket-loaded", "info", "font-large-2", "float-right");
        div9.addClassNames("progress progress-sm", "mt-1", "mb-0", "box-shadow-2");
        div10.addClassNames("progress-bar", "bg-gradient-x-info");
        div11.addClassNames("progress progress-sm", "mt-1", "mb-0", "box-shadow-2");
        div12.addClassNames("progress-bar", "progress-bar", "bg-gradient-x-info");
        div12.getStyle().set("width", "80%");
        h31.addClassName("info");




        div1.add(div2);
        div2.add(div3);
        div3.add(div4);
        div4.add(div5);

        div4.add(div11);
        div11.add(div12);

        div5.add(div6);

        div5.add(div7);

        div6.add(h31);
        div6.add(h61);
        div7.add(div8);
        div9.add(div10);

       return div1;
    }
    public Div block2() {




        Div div1 = new Div();
        Div div2 = new Div();
        Div div3 = new Div();
        Div div4 = new Div();
        Div div5 = new Div();
        Div div6 = new Div();
        Div div7 = new Div();
        Div div8 = new Div();
        Div div9 = new Div();
        Div div10 = new Div();
        Div div11 = new Div();
        Div div12 = new Div();
        H3 h31 = new H3("851");
        H6 h61 = new H6("Products Sold1");


        div1.addClassNames("col-xl-3", "col-lg-6", "col-12");
        div2.addClassNames("card", "pull-up");
        div3.addClassName("card-content");
        div4.addClassName("card-body");
        div5.addClassNames("media", "d-flex");
        div6.addClassNames("media-body", "text-left");
        div8.addClassNames("icon-basket-loaded", "info", "font-large-2", "float-right");
        div9.addClassNames("progress progress-sm", "mt-1", "mb-0", "box-shadow-2");
        div10.addClassNames("progress-bar", "bg-gradient-x-info");
        div11.addClassNames("progress progress-sm", "mt-1", "mb-0", "box-shadow-2");
        div12.addClassNames("progress-bar", "progress-bar", "bg-gradient-x-info");
        div12.getStyle().set("width", "80%");
        h31.addClassName("info");




        div1.add(div2);
        div2.add(div3);
        div3.add(div4);
        div4.add(div5);

        div4.add(div11);
        div11.add(div12);

        div5.add(div6);

        div5.add(div7);

        div6.add(h31);
        div6.add(h61);
        div7.add(div8);
        div9.add(div10);

        return div1;
    }
    public Div block3() {




        Div div1 = new Div();
        Div div2 = new Div();
        Div div3 = new Div();
        Div div4 = new Div();
        Div div5 = new Div();
        Div div6 = new Div();
        Div div7 = new Div();
        Div div8 = new Div();
        Div div9 = new Div();
        Div div10 = new Div();
        Div div11 = new Div();
        Div div12 = new Div();
        H3 h31 = new H3("852");
        H6 h61 = new H6("Products Sold1");


        div1.addClassNames("col-xl-3", "col-lg-6", "col-12");
        div2.addClassNames("card", "pull-up");
        div3.addClassName("card-content");
        div4.addClassName("card-body");
        div5.addClassNames("media", "d-flex");
        div6.addClassNames("media-body", "text-left");
        div8.addClassNames("icon-basket-loaded", "info", "font-large-2", "float-right");
        div9.addClassNames("progress progress-sm", "mt-1", "mb-0", "box-shadow-2");
        div10.addClassNames("progress-bar", "bg-gradient-x-info");
        div11.addClassNames("progress progress-sm", "mt-1", "mb-0", "box-shadow-2");
        div12.addClassNames("progress-bar", "progress-bar", "bg-gradient-x-info");
        div12.getStyle().set("width", "80%");
        h31.addClassName("info");




        div1.add(div2);
        div2.add(div3);
        div3.add(div4);
        div4.add(div5);

        div4.add(div11);
        div11.add(div12);

        div5.add(div6);

        div5.add(div7);

        div6.add(h31);
        div6.add(h61);
        div7.add(div8);
        div9.add(div10);

        return div1;
    }

    public Div block4() {




        Div div1 = new Div();
        Div div2 = new Div();
        Div div3 = new Div();
        Div div4 = new Div();
        Div div5 = new Div();
        Div div6 = new Div();
        Div div7 = new Div();
        Div div8 = new Div();
        Div div9 = new Div();
        Div div10 = new Div();
        Div div11 = new Div();
        Div div12 = new Div();
        H3 h31 = new H3("853");
        H6 h61 = new H6("Products Sold1");


        div1.addClassNames("col-xl-3", "col-lg-6", "col-12");
        div2.addClassNames("card", "pull-up");
        div3.addClassName("card-content");
        div4.addClassName("card-body");
        div5.addClassNames("media", "d-flex");
        div6.addClassNames("media-body", "text-left");
        div8.addClassNames("icon-basket-loaded", "info", "font-large-2", "float-right");
        div9.addClassNames("progress progress-sm", "mt-1", "mb-0", "box-shadow-2");
        div10.addClassNames("progress-bar", "bg-gradient-x-info");
        div11.addClassNames("progress progress-sm", "mt-1", "mb-0", "box-shadow-2");
        div12.addClassNames("progress-bar", "progress-bar", "bg-gradient-x-info");
        div12.getStyle().set("width", "80%");
        h31.addClassName("info");




        div1.add(div2);
        div2.add(div3);
        div3.add(div4);
        div4.add(div5);

        div4.add(div11);
        div11.add(div12);

        div5.add(div6);

        div5.add(div7);

        div6.add(h31);
        div6.add(h61);
        div7.add(div8);
        div9.add(div10);

        return div1;
    }

    public Div block5() {
        Div chartrow = new Div();
        Div chartdv1 = new Div();
        Div chartdv2 = new Div();
        Div chartdv3 = new Div();

        Div chartdv4 = new Div();
        Div chartdv5 = new Div();
        Canvas canvas1 = new Canvas(400,700);




        Div chartsubdv1 = new Div();
        H4 chartH41 = new H4("Simple Pie Chart");

        Anchor chartanch1 = new Anchor("#");
        Div anchricn = new Div();
        chartanch1.addClassName("heading-elements-toggle");
        anchricn.addClassNames("la", "la-ellipsis-v", "font-medium-3");
        chartrow.addClassName("row");
        chartdv1.addClassNames("col-md-6", "col-sm-12");
        chartdv2.addClassName("card");
        chartdv3.addClassName("card-header");

        chartH41.addClassName("card-title");
        chartsubdv1.addClassName("heading-elements");

        chartdv4.addClassNames("card-content", "collapse show");
        chartdv5.addClassName("card-body");
        canvas1.addClassName("chartjs-render-monitor");
        canvas1.setId("simple-pie-chart");

        chartrow.add(chartdv1);
        chartdv1.add(chartdv2);
        chartdv2.add(chartdv3);
        chartdv3.add(chartH41, chartanch1, chartsubdv1);
        chartanch1.add(anchricn);

        chartdv3.add(chartdv4);
        chartdv4.add(chartdv5);
        chartdv5.add(canvas1);
        chartdv4.add();

        return chartrow;
    }
}


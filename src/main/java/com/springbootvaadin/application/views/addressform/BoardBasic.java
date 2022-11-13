package com.springbootvaadin.application.views.addressform;

import com.springbootvaadin.application.views.MainLayout;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PageTitle("Board")
@Route(value = "board-basic", layout = MainLayout.class)
@PermitAll
public class BoardBasic extends Div {

  public BoardBasic() {
    // tag::snippet[]
    Board board = new Board();
    board.addRow(
            new ExampleIndicator("Current users", "745", "+33.7"),
            new ExampleIndicator("View events", "54.6k", "-112.45"),
            new ExampleIndicator("Conversion rate", "18%", "+3.9"),
            new ExampleIndicator("Custom metric", "-123.45")
    );
    board.addRow(new ExampleChart());
    // end::snippet[]

    add(board);
    addClassName("basic-board");
  }

}
